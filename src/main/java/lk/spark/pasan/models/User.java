package lk.spark.pasan.models;

import lk.spark.pasan.enums.Role;

/**
 * User of NCMS
 */
public class User {
    private int id;
    private String name;
    private String email;
    private Role role;

    /**
     * Create new user
     * @param name
     * @param email
     * @param role
     */
    public User(String name, String email, Role role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

    /**
     * Get user id
     * @return
     */
    public int getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }


}
