package lk.spark.pasan.controllers;

import com.google.gson.JsonObject;
import lk.spark.pasan.enums.PatientStatus;
import lk.spark.pasan.enums.Role;
import lk.spark.pasan.helpers.Database;
import lk.spark.pasan.helpers.DbFunctions;
import lk.spark.pasan.helpers.Http;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Base64;
import java.util.UUID;

@WebServlet(name = "PatientController")
public class PatientController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set response content type
        resp.setContentType("text/html");

        // Actual logic goes here.
        PrintWriter out = resp.getWriter();
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

        try {
            Connection connection = Database.open();
            PreparedStatement statement;

            statement = connection.prepareStatement("SELECT COUNT(*) FROM users WHERE email=?");
            statement.setString(1, email);
            if (DbFunctions.count(statement.executeQuery()) == 0) {
                statement = connection.prepareStatement("INSERT INTO users (name, email, password, role) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, name);
                statement.setString(2, email);
                statement.setString(3, Base64.getEncoder().encodeToString(password.getBytes()));
                statement.setInt(4, Role.USER.getRole());
                statement.executeUpdate();
                ResultSet resultSet = statement.getGeneratedKeys();
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

                UUID uuid = UUID.randomUUID();

//                statement = connection.prepareStatement("INSERT INTO patients (user_id, serial_no, geolocation_x, geolocation_y, contact_number, status, register_date, admission_date) VALUES (?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
//                statement.setInt(1, userId);
//                statement.setString(2, uuid.toString());
//                statement.setInt(3, geolocationX);
//                statement.setInt(4, geolocationY);
//                statement.setString(5, contactNumber);
//                statement.setInt(6, patientStatus.getStatus());
//                statement.setDate(7, new java.sql.Date(new java.util.Date().getTime()));
//                statement.setDate(8, new java.sql.Date(new java.util.Date().getTime()));
//                statement.executeUpdate();

                statement.close();

                resp = Http.setResponse(resp, 200);

                JsonObject json = new JsonObject();
                json.addProperty("serial_no", uuid.toString());

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
}
