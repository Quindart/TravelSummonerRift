package vn.edu.iuh.fit.bookingservice.services;

import java.util.List;

import vn.edu.iuh.fit.bookingservice.dtos.requests.CategoryTourRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.CategoryTourResponse;

public interface CategoryTourService {
    List<CategoryTourResponse> getAllCategoryTours();

    CategoryTourResponse getCategoryTourById(String categoryTourId);

    CategoryTourResponse createCategoryTour(CategoryTourRequest categoryTourRequest);

    CategoryTourResponse updateCategoryTour(String categoryTourId, CategoryTourRequest categoryTourRequest);

    void deleteCategoryTour(String categoryTourId);
}
