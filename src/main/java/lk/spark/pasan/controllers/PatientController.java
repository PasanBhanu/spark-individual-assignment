package lk.spark.pasan.controllers;

import com.google.gson.JsonObject;
import lk.spark.pasan.enums.PatientStatus;
import lk.spark.pasan.enums.Role;
import lk.spark.pasan.helpers.Database;
import lk.spark.pasan.helpers.DbFunctions;
import lk.spark.pasan.helpers.Http;
import lk.spark.pasan.models.Hospital;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.sql.Date;
import java.util.*;

@WebServlet(name = "PatientController")
public class PatientController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set response content type
        resp.setContentType("text/html");

        // Actual logic goes here.
        PrintWriter out = resp.getWriter();


        this.allocateBeds();

        out.println("<h1>Test</h1>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String cPassword = req.getParameter("c_password");
        int geolocationX = Integer.parseInt(req.getParameter("geolocation_x"));
        int geolocationY = Integer.parseInt(req.getParameter("geolocation_y"));
        String contactNumber = req.getParameter("contact_number");
        int userId = 0;
        int patinetId = 0;

        try {
            Connection connection = Database.open();
            PreparedStatement statement;
            ResultSet resultSet;

            statement = connection.prepareStatement("SELECT COUNT(*) FROM users WHERE email=?");
            statement.setString(1, email);
            if (DbFunctions.count(statement.executeQuery()) == 0) {
                statement = connection.prepareStatement("INSERT INTO users (name, email, password, role) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, name);
                statement.setString(2, email);
                statement.setString(3, Base64.getEncoder().encodeToString(password.getBytes()));
                statement.setInt(4, Role.USER.getRole());
                statement.executeUpdate();
                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    userId = resultSet.getInt(1);
                }

                PatientStatus patientStatus = PatientStatus.IN_QUEUE;

                java.util.Date date = new java.util.Date();

                statement = connection.prepareStatement("INSERT INTO patients (user_id, geolocation_x, geolocation_y, contact_number, status, register_date) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, userId);
                statement.setInt(2, geolocationX);
                statement.setInt(3, geolocationY);
                statement.setString(4, contactNumber);
                statement.setInt(5, patientStatus.getStatus());
                statement.setDate(6, new java.sql.Date(new java.util.Date().getTime()));
                statement.executeUpdate();

                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    patinetId = resultSet.getInt(1);
                }

                this.allocateBeds();

                resp = Http.setResponse(resp, 200);

                JsonObject json = new JsonObject();
                json.addProperty("patient_id", patinetId);

                Http.getWriter(resp.getWriter(), "success", "User added", json, null).flush();
            } else {
                resp = Http.setResponse(resp, 400);

                JsonObject json = new JsonObject();
                json.addProperty("email", "Email already in the system");

                Http.getWriter(resp.getWriter(), "error", "Invalid data", null, json).flush();
            }
        } catch (Exception e) {
            resp.sendError(500, "Database Connection Failed");
        }

    }

    /**
     * Check for patients is the queue and add to hospital
     */
    private void allocateBeds() {
        try {
            Connection connection = Database.open();
            PreparedStatement statement;
            ResultSet resultSet;

            ArrayList<Hospital> hospitals = new ArrayList<Hospital>();
            statement = connection.prepareStatement("SELECT * FROM hospitals");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                hospitals.add(new Hospital(resultSet.getInt("id"), resultSet.getInt("user_id"), resultSet.getInt("district"), resultSet.getInt("geolocation_x"), resultSet.getInt("geolocation_y")));
            }

            statement = connection.prepareStatement("SELECT * FROM patients WHERE status=0 ORDER BY id DESC");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                HashMap<Integer, Double> hospitalDistances = new HashMap<Integer, Double>();

                for (int i = 0; i < hospitals.size(); i++) {
                    Hospital hospital = hospitals.get(i);

                    if (hospital.getAvailableBedsCount() > 0) {
                        hospitalDistances.put(hospital.getId(), hospital.getDistanceToHospital(resultSet.getInt("geolocation_x"), resultSet.getInt("geolocation_y")));
                    }
                }

                Map.Entry<Integer, Double> min = null;
                for (Map.Entry<Integer, Double> entry : hospitalDistances.entrySet()) {
                    if (min == null || min.getValue() > entry.getValue()) {
                        min = entry;
                    }
                }

                if (min != null){
                    UUID uuid = UUID.randomUUID();

                    statement = connection.prepareStatement("UPDATE patients SET serial_no=?, admission_date=?, status=? WHERE id=?");
                    statement.setString(1, uuid.toString());
                    statement.setDate(2, new java.sql.Date(new java.util.Date().getTime()));
                    statement.setInt(3, PatientStatus.ADMITTED.getStatus());
                    statement.setInt(4, resultSet.getInt("id"));
                    statement.executeUpdate();

                    statement = connection.prepareStatement("UPDATE beds SET serial_no=? WHERE hospital_id=? and serial_no IS NULL LIMIT 1");
                    statement.setString(1, uuid.toString());
                    statement.setInt(2, min.getKey());
                    statement.executeUpdate();
                }

                connection.close();
            }
        } catch (Exception exception) {

        }
    }
}
