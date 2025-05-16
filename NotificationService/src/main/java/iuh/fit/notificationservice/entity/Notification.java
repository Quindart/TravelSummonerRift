package iuh.fit.notificationservice.entity;


import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Notification {
    private String email;
    private String data;
}