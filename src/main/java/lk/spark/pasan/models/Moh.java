package lk.spark.pasan.models;

import lk.spark.pasan.enums.Role;
import lk.spark.pasan.interfaces.DatabaseModel;

/**
 * Moh User
 */
public class Moh extends User implements DatabaseModel {

    /**
     * Crate MoH user
     *
     * @param name
     * @param email
     * @param password
     * @param role
     */
    public Moh(String name, String email, String password, Role role) {
        super(name, email, role);
    }
}
