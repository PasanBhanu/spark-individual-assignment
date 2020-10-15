package lk.spark.pasan.controllers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lk.spark.pasan.enums.DeceaseLevel;
import lk.spark.pasan.enums.HttpStatus;
import lk.spark.pasan.enums.PatientStatus;
import lk.spark.pasan.helpers.Database;
import lk.spark.pasan.helpers.Http;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Generate stats for display
 */
@WebServlet(name = "StatController")
public class StatController extends HttpServlet {

    /**
     * Get COVID stats
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonArray errorArray;

        try {
            Connection connection = Database.open();
            PreparedStatement statement;
            ResultSet resultSet;
            JsonObject data = new JsonObject();

            JsonObject districtWisePatients = new JsonObject();
            statement = connection.prepareStatement("SELECT district, COUNT(*) as count FROM patients WHERE status=1 GROUP BY district");
            resultSet = statement.executeQuery();

            JsonArray label = new JsonArray();
            JsonArray dataPoints = new JsonArray();

            while (resultSet.next()) {
                label.add("District " + resultSet.getInt("district"));
                dataPoints.add(resultSet.getInt("count"));
            }
            districtWisePatients.add("label", label);
            districtWisePatients.add("data", dataPoints);
            data.add("district", districtWisePatients);

            JsonObject dailyStats = new JsonObject();
            statement = connection.prepareStatement("SELECT status, COUNT(*) as count FROM patients WHERE register_date=? or admission_date=? or discharged_date=? GROUP BY status");
            statement.setDate(1, new java.sql.Date(new java.util.Date().getTime()));
            statement.setDate(2, new java.sql.Date(new java.util.Date().getTime()));
            statement.setDate(3, new java.sql.Date(new java.util.Date().getTime()));
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                dailyStats.addProperty(resultSet.getString("status"), resultSet.getInt("count"));
            }
            data.add("daily_status", dailyStats);

            JsonObject totalStats = new JsonObject();
            statement = connection.prepareStatement("SELECT status, COUNT(*) as count FROM patients GROUP BY status ORDER BY status ASC");
            resultSet = statement.executeQuery();

            label = new JsonArray();
            dataPoints = new JsonArray();

            while (resultSet.next()) {
                dataPoints.add(resultSet.getInt("count"));
            }
            label.add("In Queue");
            label.add("Hospitalised");
            label.add("Discharged");
            totalStats.add("label", label);
            totalStats.add("data", dataPoints);

            data.add("total_status", totalStats);

            JsonObject dayVsPatients = new JsonObject();
            statement = connection.prepareStatement("SELECT register_date, COUNT(*) as count FROM patients GROUP BY register_date ORDER BY register_date DESC LIMIT 30");
            resultSet = statement.executeQuery();

            label = new JsonArray();
            dataPoints = new JsonArray();

            while (resultSet.next()) {
                label.add(resultSet.getString("register_date"));
                dataPoints.add(resultSet.getInt("count"));
            }

            dayVsPatients.add("label", label);
            dayVsPatients.add("data", dataPoints);

            data.add("days", dayVsPatients);

            Http.setResponse(resp, 200);
            Http.getWriter(resp.getWriter(), HttpStatus.SUCCESS.getStatus(), "loaded", data, null).flush();

            connection.close();
        } catch (Exception exception) {
            errorArray = new JsonArray();
            errorArray.add("Database connection failed");

            resp = Http.setResponse(resp, 500);
            Http.getWriter(resp.getWriter(), HttpStatus.ERROR.getStatus(), "server error", null, errorArray).flush();
        }


    }
}
