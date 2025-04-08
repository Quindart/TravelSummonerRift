package vn.edu.iuh.fit.userservice.entities;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.edu.iuh.fit.userservice.enums.Role;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String userId;

    String email;
    String phone;
    String username;
    String password;
    String fullName;
    String avatarUrl;

    Date birthday;

    Integer gender; //1: nam, 0 nữ

    @Enumerated(EnumType.STRING)
    Role role;
}
