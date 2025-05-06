package vn.edu.iuh.fit.userservice.dtos.requests;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GoogleInfoRequest {
    private String email;
    private String picture;
    private  String name;

}
