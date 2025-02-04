package vn.edu.iuh.fit.bookingservice.configs;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.datafaker.Faker;
import vn.edu.iuh.fit.bookingservice.entities.CategoryTour;
import vn.edu.iuh.fit.bookingservice.entities.Tour;
import vn.edu.iuh.fit.bookingservice.repositories.CategoryTourRepository;
import vn.edu.iuh.fit.bookingservice.repositories.TourRepository;

@Configuration
public class ApplicationConfig {
    private Faker faker = new Faker();

    @Bean
    ApplicationRunner categoryTourSeeder(CategoryTourRepository categoryTourRepository, TourRepository tourRepository) {
        return args -> {
            for (int i = 0; i < 10; i++) {
                CategoryTour categoryTour = CategoryTour.builder()
                        .name(faker.book().title())
                        .description(faker.lorem().sentence())
                        .image(faker.internet().image())
                        .build();
                categoryTourRepository.save(categoryTour);
                Tour tour = Tour.builder()
                        .name(faker.book().title())
                        .description(faker.lorem().sentence())
                        .price(faker.number().randomDouble(2, 100, 1000))
                        .categoryTour(categoryTour)
                        .duration(faker.number().numberBetween(1, 10)+"days")
                        .thumbnail(faker.internet().image())
                        .build();
                tourRepository.save(tour);
            }

        };
    }
}
