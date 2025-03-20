package vn.edu.iuh.fit.bookingservice.dtos.requests;

import lombok.Data;

@Data
public class DestinationRequest {
    private String name;
    private String description;
    private String image;
    private String address;
    private String city;
    private String district;
    private String country;
    private String cityId;
    private String districtId;
}
