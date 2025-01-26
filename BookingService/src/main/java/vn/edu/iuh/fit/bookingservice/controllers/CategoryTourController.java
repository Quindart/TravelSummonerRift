package vn.edu.iuh.fit.bookingservice.controllers;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import vn.edu.iuh.fit.bookingservice.dtos.requests.CategoryTourRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.CategoryTourResponse;
import vn.edu.iuh.fit.bookingservice.exception.MessageResponse;
import vn.edu.iuh.fit.bookingservice.exception.SuccessEntityResponse;
import vn.edu.iuh.fit.bookingservice.services.CategoryTourService;

@RestController
@RequestMapping("/api/category-tours")
public class CategoryTourController {
    @Autowired
    CategoryTourService categoryTourService;

    @GetMapping
    public ResponseEntity<MessageResponse<List<CategoryTourResponse>>> getAllCategoryTours() {
        return SuccessEntityResponse.FoundResponse("Đã tìm thấy", categoryTourService.getAllCategoryTours());
    }

    @GetMapping("/{categoryTourId}")
    public ResponseEntity<MessageResponse<CategoryTourResponse>> getCategoryTourById(
            @PathVariable String categoryTourId) {
        return SuccessEntityResponse.FoundResponse(
                "Đã tìm thấy", categoryTourService.getCategoryTourById(categoryTourId));
    }

    @PostMapping
    public ResponseEntity<MessageResponse<CategoryTourResponse>> createCategoryTour(
            @RequestBody @Valid CategoryTourRequest categoryTourRequest) {
        return SuccessEntityResponse.CreateResponse(
                "Tạo Loại tour thành kông", categoryTourService.createCategoryTour(categoryTourRequest));
    }

    @PutMapping("/{categoryTourId}")
    public ResponseEntity<MessageResponse<CategoryTourResponse>> updateCategoryTour(
            @PathVariable String categoryTourId, @RequestBody @Valid CategoryTourRequest categoryTourRequest) {
        return SuccessEntityResponse.OkResponse(
                "Cập nhật loại tour thành kông",
                categoryTourService.updateCategoryTour(categoryTourId, categoryTourRequest));
    }

    @DeleteMapping("/{categoryTourId}")
    public ResponseEntity<MessageResponse<Void>> deleteCategoryTour(@PathVariable String categoryTourId) {
        categoryTourService.deleteCategoryTour(categoryTourId);
        return SuccessEntityResponse.OkResponse("Xóa tour thành kông", null);
    }
}
