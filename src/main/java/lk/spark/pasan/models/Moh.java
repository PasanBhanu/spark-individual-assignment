package lk.spark.pasan.models;

import lk.spark.pasan.enums.Role;

/**
 * Moh User
 */
public class Moh extends User {
    public Moh(String name, String email, String password, Role role) {
        super(name, email, password, role);
    }
}
