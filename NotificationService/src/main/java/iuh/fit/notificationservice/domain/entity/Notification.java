package iuh.fit.notificationservice.domain.entity;


import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Notification extends BaseEntity {
    private String toEmail;
    private String data;
}