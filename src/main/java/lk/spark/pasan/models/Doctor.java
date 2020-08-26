package lk.spark.pasan.models;

import lk.spark.pasan.enums.Role;
import lk.spark.pasan.interfaces.DatabaseModel;

/**
 * Doctors
 */
public class Doctor extends User implements DatabaseModel {
    private int hospitalId;
    private int id;

    /**
     * Create doctor
     *
     * @param id
     * @param name
     * @param email
     * @param hospitalId
     */
    public Doctor(int id, String name, String email, int hospitalId) {
        super(name, email, Role.DOCTOR);
        this.hospitalId = hospitalId;
    }
}

