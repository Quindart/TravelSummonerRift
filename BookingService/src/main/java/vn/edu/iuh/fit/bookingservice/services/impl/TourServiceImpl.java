package vn.edu.iuh.fit.bookingservice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.bookingservice.dtos.requests.TourDestinationRequest;
import vn.edu.iuh.fit.bookingservice.dtos.requests.TourImageRequest;
import vn.edu.iuh.fit.bookingservice.configs.TourSpecification;
import vn.edu.iuh.fit.bookingservice.dtos.requests.TourRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourDestinationResponse;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourImageResponse;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourResponse;
import vn.edu.iuh.fit.bookingservice.dtos.responses.*;
import vn.edu.iuh.fit.bookingservice.entities.Tour;
import vn.edu.iuh.fit.bookingservice.entities.TourDestination;
import vn.edu.iuh.fit.bookingservice.entities.TourImage;
import vn.edu.iuh.fit.bookingservice.entities.TourImage;
import vn.edu.iuh.fit.bookingservice.entities.TourSchedule;
import vn.edu.iuh.fit.bookingservice.exception.errors.InternalServerErrorException;
import vn.edu.iuh.fit.bookingservice.exception.errors.NotFoundException;
import vn.edu.iuh.fit.bookingservice.mapper.TourDestinationMapper;
import vn.edu.iuh.fit.bookingservice.mapper.TourImageMapper;
import vn.edu.iuh.fit.bookingservice.mapper.TourMapper;
import vn.edu.iuh.fit.bookingservice.mapper.TourScheduleMapper;
import vn.edu.iuh.fit.bookingservice.repositories.TourDestinationRepository;
import vn.edu.iuh.fit.bookingservice.repositories.TourImageRepository;
import vn.edu.iuh.fit.bookingservice.repositories.TourRepository;
import vn.edu.iuh.fit.bookingservice.repositories.TourScheduleRepository;
import vn.edu.iuh.fit.bookingservice.services.TourImageService;
import vn.edu.iuh.fit.bookingservice.services.TourScheduleService;
import vn.edu.iuh.fit.bookingservice.services.TourService;

import java.util.ArrayList;
import java.util.List;

@Service
public class TourServiceImpl implements TourService {
    @Autowired
    private TourRepository tourRepository;
    @Autowired
    private TourMapper tourMapper;
    @Autowired
    private TourDestinationRepository tourDestinationRepository;
    @Autowired
    private TourImageService tourImageService;
    @Autowired
    private TourDestinationMapper tourDestinationMapper;
    @Autowired
    private TourImageRepository tourImageRepository;
    @Autowired
    private TourImageMapper tourImageMapper;
    @Autowired
    private TourScheduleRepository tourScheduleRepository;
    @Autowired
    private TourScheduleMapper tourScheduleMapper;

    @Override
    public List<TourResponse> getAllTours() {
        List<TourResponse> tourResponses =  tourRepository.findAll().stream()
                                .map(tourMapper::toTourResponse)
                                .toList();
        for (TourResponse tourResponse : tourResponses) {
            List<TourDestinationResponse> tourDestinationResponses = tourDestinationRepository.findByTour_TourId(tourResponse.getTourId())
                    .stream().map(tourDestinationMapper::toTourDestinationResponse)
                    .toList();
            List<TourImageResponse> tourImageResponses = tourImageRepository.findByTour_TourId(tourResponse.getTourId())
                    .stream().map(tourImageMapper::toTourImageResponse)
                    .toList();
            tourResponse.setTourDestinationResponses(tourDestinationResponses);
            tourResponse.setTourImageResponses(tourImageResponses);
        }
        return tourResponses;
    }

    @Override
    public TourResponse getTourById(String tourId) {
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy tour với ID: " + tourId));
        TourResponse tourResponse = tourMapper.toTourResponse(tour);
        List<TourDestinationResponse> tourDestinationResponses = tourDestinationRepository.findByTour_TourId(tourId)
                .stream().map(tourDestinationMapper::toTourDestinationResponse)
                .toList();
        List<TourImageResponse> tourImageResponses = tourImageRepository.findByTour_TourId(tourId)
                .stream().map(tourImageMapper::toTourImageResponse)
                .toList();

        List<TourScheduleResponse> tourScheduleResponses = tourScheduleRepository.findTourScheduleByTour_TourId(tourId)
                        .stream()
                        .map(tourScheduleMapper::entityToResponse)
                        .toList();

        tourResponse.setTourDestinationResponses(tourDestinationResponses);
        tourResponse.setTourImageResponses(tourImageResponses);
        tourResponse.setTourScheduleResponses(tourScheduleResponses);
        return tourResponse;
    }

    @Override
    public TourResponse createTour(TourRequest tourRequest) {
        if (tourRequest.getName() == null || tourRequest.getName().isEmpty()) {
            throw new IllegalArgumentException("Tên tour không được để trống");
        }

        try{
            Tour tour = tourMapper.toTour(tourRequest);

            Tour savedTour = tourRepository.save(tour);

            List<TourDestinationResponse> tourDestinationResponses = new ArrayList<>();
            for(TourDestinationRequest tourDestinationRequest : tourRequest.getTourDestinationRequests()) {
                TourDestination tourDestination = TourDestination.builder()
                        .name(tourDestinationRequest.getName())
                        .description(tourDestinationRequest.getDescription())
                        .tour(savedTour)
                        .build();
                tourDestinationRepository.save(tourDestination);
                tourDestinationResponses.add(tourDestinationMapper.toTourDestinationResponse(tourDestination));
            }

            List<TourImageResponse> tourImageResponses = new ArrayList<>();
            int i = 1;
            for (TourImageRequest tourImageRequest : tourRequest.getTourImageRequests()) {
                tourImageRequest.setTourId(savedTour.getTourId());
                tourImageRequest.setOrderIndex(i++);
                TourImageResponse tourImage = tourImageService.saveTourImage(tourImageRequest);
                tourImageResponses.add(tourImage);
            }

            TourResponse tourResponse = tourMapper.toTourResponse(savedTour);
            tourResponse.setTourDestinationResponses(tourDestinationResponses);
            tourResponse.setTourImageResponses(tourImageResponses);
            return tourResponse;
        } catch (Exception e) {
            throw new InternalServerErrorException("Lỗi khi tạo tour: " + e.getMessage());
        }

    }

    @Override
    public TourResponse updateTour(String tourId, TourRequest tourRequest) {
        Tour updatedTour = tourRepository.findById(tourId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy tour với ID: " + tourId));

        updatedTour.setName(tourRequest.getName());
        updatedTour.setDescription(tourRequest.getDescription());
        updatedTour.setDuration(tourRequest.getDuration());
        updatedTour.setPrice(tourRequest.getPrice());


        tourRepository.save(updatedTour);

        return getTourById(tourId);
    }

    @Override
    public void deleteTour(String tourId) {
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy tour với ID: " + tourId));

        tour.setActive(false);
        tourRepository.save(tour);
    }

    @Override
    public List<TourResponse> searchTours(String tourName, String category, Double minPrice, Double maxPrice, String city, String destination) {
        Specification<Tour> spec = TourSpecification.filterTours(tourName, category, minPrice, maxPrice, city, destination);
        return tourRepository.findAll(spec).stream()
                .map(tourMapper::toTourResponse)
                .toList();
    }

    @Override
    public List<TourOverviewResponse> getAllTourOverviews() {
        return tourRepository.findAll().stream()
                .map(tourMapper::toTourOverviewResponse)
                .toList();
    }

    @Override
    public TourScheduleDetailResponse getTourScheduleDetail(String tourId, String scheduleId) {
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy tour với ID: " + tourId));
        TourSchedule tourSchedule = tour.getTourSchedules().stream()
                .filter(schedule -> schedule.getTourScheduleId().equals(scheduleId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Không tìm thấy lịch trình với ID: " + scheduleId));
        return tourMapper.toTourScheduleDetailResponse(tourSchedule);
    }

    @Override
    public List<TourImageResponse> getTourImages(String tourId) {
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy tour với ID: " + tourId));
        return tour.getTourImages().stream()
                .map(tourMapper::toTourImageResponse)
                .toList();
    }

    @Override
    public List<TourNoteResponse> getTourNotes(String tourId) {
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy tour với ID: " + tourId));
        return tour.getTourNotes().stream()
                .map(tourMapper::toTourNoteResponse)
                .toList();
    }

    @Override
    public List<TourResponse> searchToursByKeyword(String keyword) {
        Specification<Tour> spec = (root, query, builder) -> {
            String likeKeyword = "%" + keyword.toLowerCase() + "%";
            String accents = "áàảãạâấầẩẫậăắằẳẵặđéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵÁÀẢÃẠÂẤẦẨẪẬĂẮẰẲẴẶĐÉÈẺẼẸÊẾỀỂỄỆÍÌỈĨỊÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴ";
            String noAccents = "aaaaaaaaaaaaaaaaadeeeeeeeeeeeiiiiiooooooooooooooooouuuuuuuuuuuyyyyyAAAAAAAAAAAAAAAAADEEEEEEEEEEEIIIIIOOOOOOOOOOOOOOOOOUUUUUUUUUUUYYYYY";
            
            return builder.or(
                    builder.like(
                        builder.function("translate", String.class, 
                            builder.lower(root.get("name")), 
                            builder.literal(accents), 
                            builder.literal(noAccents)
                        ), 
                        builder.function("translate", String.class, 
                            builder.literal(likeKeyword), 
                            builder.literal(accents), 
                            builder.literal(noAccents)
                        )
                    ),
                    builder.like(
                        builder.function("translate", String.class, 
                            builder.lower(root.get("description")), 
                            builder.literal(accents), 
                            builder.literal(noAccents)
                        ), 
                        builder.function("translate", String.class, 
                            builder.literal(likeKeyword), 
                            builder.literal(accents), 
                            builder.literal(noAccents)
                        )
                    ),
                    builder.like(
                        builder.function("translate", String.class, 
                            builder.lower(root.get("duration")), 
                            builder.literal(accents), 
                            builder.literal(noAccents)
                        ), 
                        builder.function("translate", String.class, 
                            builder.literal(likeKeyword), 
                            builder.literal(accents), 
                            builder.literal(noAccents)
                        )
                    )
            );
        };
        
        List<Tour> tours = tourRepository.findAll(spec);
        if (tours.isEmpty()) {
            throw new NotFoundException("Không tìm thấy tour nào với từ khóa: " + keyword);
        }
        return tours.stream()
                .map(tourMapper::toTourResponse)
                .toList();
    }


    @Override
    public List<TourOverviewResponse> getToursByCategory(String categoryID) {
        List<Tour> ls = tourRepository.findToursByCategoryTour_CategoryTourId(categoryID);
        if (ls.isEmpty()) {
            throw new NotFoundException("Chưa tìm thấy Tour nào trong Categorie: " + categoryID);
        }
        return ls.stream()
                .map(tourMapper::toTourOverviewResponse)
                .toList();

    }
}
