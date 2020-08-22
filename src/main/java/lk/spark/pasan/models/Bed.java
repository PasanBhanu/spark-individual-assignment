package lk.spark.pasan.models;

public class Bed {
    private int id;
    private int hospitalId;
    private String serialNo;

    public Hospital hospital;

    public Patient patient;

    public Bed(int id, int hospitalId, String serialNo) {
        this.id = id;
        this.hospitalId = hospitalId;
        this.serialNo = serialNo;
    }
}
