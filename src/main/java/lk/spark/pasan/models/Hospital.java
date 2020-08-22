package lk.spark.pasan.models;

import lk.spark.pasan.helpers.Database;
import lk.spark.pasan.helpers.DbFunctions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Hospital {
    protected final int BED_COUNT = 10;
    private int id;
    private int userId;
    private int district;
    private int geolocationX;
    private int geolocationY;

    public Doctor director;

    public ArrayList<Bed> beds;

    public Hospital(int id, int userId, int district, int geolocationX, int geolocationY) {
        this.id = id;
        this.userId = userId;
        this.district = district;
        this.geolocationX = geolocationX;
        this.geolocationY = geolocationY;
    }

    public int getId() {
        return id;
    }

    /**
     * Load all the relations
     */
    public void loadModel(){
        try{
            Connection connection = Database.open();
            PreparedStatement statement;
            ResultSet resultSet;

            this.beds = new ArrayList<Bed>();

            statement = connection.prepareStatement("SELECT * FROM beds WHERE hospital_id=?");
            statement.setInt(1, this.id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                this.beds.add(new Bed(resultSet.getInt("id"), resultSet.getInt("hospital_id"), resultSet.getString("serial_no")));
            }

            statement = connection.prepareStatement("SELECT * FROM users INNER JOIN doctors ON users.id=doctors.user_id WHERE id=? LIMIT 1");
            statement.setInt(1, this.userId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                this.director = new Doctor(resultSet.getInt("doctors.id"), resultSet.getString("name"), resultSet.getString("email"), resultSet.getInt("hospital_id"));
            }

            connection.close();
        }catch (Exception exception){

        }
    }

    /**
     * Get available beds
     * @return bed count
     */
    public int getAvailableBedsCount(){
        try{
            Connection connection = Database.open();
            PreparedStatement statement;
            ResultSet resultSet;

            this.beds = new ArrayList<Bed>();

            statement = connection.prepareStatement("SELECT COUNT(*) FROM beds WHERE hospital_id=? and serial_no IS NULL");
            statement.setInt(1, this.id);

            int count = DbFunctions.count(statement.executeQuery());
            connection.close();

            return count;
        }catch (Exception exception){
            return 0;
        }
    }

    /**
     * Get the distance to a hospital from a given coordinates
     * @param geolocationX
     * @param geolocationY
     * @return
     */
    public double getDistanceToHospital(int geolocationX, int geolocationY){
        int xDifference = Math.abs(this.geolocationX - geolocationX);
        int yDifference = Math.abs(this.geolocationY - geolocationY);

        double distance = Math.sqrt(Math.pow(xDifference, 2) + Math.pow(yDifference, 2));

        return distance;
    }
}
