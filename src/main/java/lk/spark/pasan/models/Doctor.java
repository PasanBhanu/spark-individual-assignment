package lk.spark.pasan.models;

import lk.spark.pasan.enums.Role;

/**
 * Doctors
 */
public class Doctor extends User {
    private int hospitalId;
    private int id;

    public Doctor(int id, String name, String email, int hospitalId) {
        super(name, email, Role.DOCTOR);
        this.hospitalId = hospitalId;
    }


}

