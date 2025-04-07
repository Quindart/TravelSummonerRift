package vn.edu.iuh.fit.userservice.controllers;

import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.userservice.dtos.requests.ReviewRequest;
import vn.edu.iuh.fit.userservice.dtos.responses.ReviewResponse;
import vn.edu.iuh.fit.userservice.exception.MessageResponse;

@RestController
@RequestMapping("reviews")
public class ReviewController {

    @PostMapping("review")
    public MessageResponse<ReviewResponse> createReview(@RequestBody ReviewRequest reviewRequest) {

    }
}
