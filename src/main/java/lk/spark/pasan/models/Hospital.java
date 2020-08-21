package lk.spark.pasan.models;

public class Hospital {
    private int id;
    private int user_id;
    private int district;
    private int geolocationX;
    private int geolocationY;

    public Doctor director;

    public Bed[] beds;
}
