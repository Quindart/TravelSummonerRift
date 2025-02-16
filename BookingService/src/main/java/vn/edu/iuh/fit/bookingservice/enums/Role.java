package vn.edu.iuh.fit.bookingservice.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Role {
    ADMIN,
    USER;

    @JsonCreator
    public static Role fromString(String role) {
        return Role.valueOf(role.toUpperCase());
    }
}
