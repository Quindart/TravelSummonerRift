package vn.edu.iuh.fit.bookingservice.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.edu.iuh.fit.bookingservice.dtos.requests.CategoryTourRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.CategoryTourResponse;
import vn.edu.iuh.fit.bookingservice.entities.CategoryTour;
import vn.edu.iuh.fit.bookingservice.exception.errors.InternalServerErrorException;
import vn.edu.iuh.fit.bookingservice.exception.errors.NotFoundException;
import vn.edu.iuh.fit.bookingservice.mapper.CategoryTourMapper;
import vn.edu.iuh.fit.bookingservice.repositories.CategoryTourRepository;
import vn.edu.iuh.fit.bookingservice.services.CategoryTourService;

@Service
public class CategoryTourServiceImpl implements CategoryTourService {
    @Autowired
    private CategoryTourRepository categoryTourRepository;

    @Autowired
    private CategoryTourMapper categoryTourMapper;

    @Override
    public List<CategoryTourResponse> getAllCategoryTours() {
        List<CategoryTourResponse> categoryTourResponses = categoryTourRepository.findAll().stream()
                .map(categoryTourMapper::toCategoryTourResponse)
                .collect(Collectors.toList());
        return categoryTourResponses;
    }

    @Override
    public CategoryTourResponse getCategoryTourById(String categoryTourId) {
        CategoryTour categoryTour = categoryTourRepository
                .findById(categoryTourId)
                .orElseThrow(() -> new NotFoundException("Hong tìm thấy loại tour"));

        return categoryTourMapper.toCategoryTourResponse(categoryTour);
    }

    @Override
    public CategoryTourResponse createCategoryTour(CategoryTourRequest categoryTourRequest) {
        CategoryTour categoryTour = categoryTourMapper.toCategoryTour(categoryTourRequest);
        categoryTour = categoryTourRepository.save(categoryTour);
        if (categoryTour == null) {
            throw new InternalServerErrorException("Tạo loại tour thất bại");
        }
        return categoryTourMapper.toCategoryTourResponse(categoryTour);
    }

    @Override
    public CategoryTourResponse updateCategoryTour(String categoryTourId, CategoryTourRequest categoryTourRequest) {
        CategoryTour categoryTour = categoryTourRepository
                .findById(categoryTourId)
                .orElseThrow(() -> new NotFoundException("Hong tìm thấy loại tour"));

        categoryTour.setName(categoryTourRequest.getName());
        categoryTour.setDescription(categoryTourRequest.getDescription());
        categoryTour.setImage(categoryTourRequest.getImage());

        CategoryTour updatedCategoryTour = categoryTourRepository.save(categoryTour);

        return categoryTourMapper.toCategoryTourResponse(updatedCategoryTour);
    }

    @Override
    public void deleteCategoryTour(String categoryTourId) {
        CategoryTour categoryTour = categoryTourRepository
                .findById(categoryTourId)
                .orElseThrow(() -> new NotFoundException("Hong tìm thấy loại tour"));

        categoryTour.setActive(false);

        categoryTourRepository.save(categoryTour);
    }
}
