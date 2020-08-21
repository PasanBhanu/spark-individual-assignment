package lk.spark.pasan.models;

import lk.spark.pasan.enums.Role;

/**
 * Doctors
 */
public class Doctor extends User {
    private int hospitalId;

    public Doctor(String name, String email, String password, Role role, int hospitalId) {
        super(name, email, password, role);
        this.hospitalId = hospitalId;
    }


}

