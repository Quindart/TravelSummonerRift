package vn.edu.iuh.fit.bookingservice.configs;

import net.datafaker.Faker;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.edu.iuh.fit.bookingservice.entities.CategoryTour;
import vn.edu.iuh.fit.bookingservice.repositories.CategoryTourRepository;

import java.util.List;

@Configuration
public class ApplicationConfig {
    private Faker faker = new Faker();

    @Bean
    ApplicationRunner categoryTourSeeder(CategoryTourRepository categoryTourRepository) {
        return args -> {
            for (int i = 0; i < 10; i++) {
                CategoryTour categoryTour = CategoryTour.builder()
                        .name(faker.book().title())
                        .description(faker.lorem().sentence())
                        .image(faker.internet().image())
                        .build();
                categoryTourRepository.save(categoryTour);
            }
        };
    }
}
