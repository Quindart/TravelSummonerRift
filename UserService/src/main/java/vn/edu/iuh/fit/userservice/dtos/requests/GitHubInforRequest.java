package vn.edu.iuh.fit.userservice.dtos.requests;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GitHubInforRequest {
    private String login;
    private String id;
    private String avatar_url;

}
