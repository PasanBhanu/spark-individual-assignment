package lk.spark.pasan.models;

import lk.spark.pasan.enums.DeceaseLevel;
import lk.spark.pasan.enums.PatientStatus;

import java.util.Date;

public class Patient {
    private int id;
    private int userId;
    private String serialNo;
    private int geolocationX;
    private int geolocationY;
    private String contactNumber;
    private DeceaseLevel deceaseLevel;
    private PatientStatus patientStatus;
    private Date registerDate;
    private Date admissionDate;
    private Date dischargedDate;

    public User user;
}
