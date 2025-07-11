package com.greendrive.backend.config;

import com.greendrive.backend.model.Review;
import com.greendrive.backend.model.Vehicle;
import com.greendrive.backend.repository.ReviewRepository;
import com.greendrive.backend.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final VehicleRepository vehicleRepository;
    private final ReviewRepository reviewRepository;
    private final Random random = new Random();

    private final List<String[]> imageSamples = List.of(
            new String[]{
                    "https://images.pexels.com/photos/14648100/pexels-photo-14648100.jpeg",
                    "https://images.pexels.com/photos/12561394/pexels-photo-12561394.jpeg"
            },
            new String[]{
                    "https://images.pexels.com/photos/21528466/pexels-photo-21528466.jpeg",
                    "https://images.pexels.com/photos/244818/pexels-photo-244818.jpeg"
            },
            new String[]{
                    "https://images.pexels.com/photos/10346859/pexels-photo-10346859.jpeg",
                    "https://images.pexels.com/photos/3635688/pexels-photo-3635688.jpeg"
            }
    );

    private final List<String[]> makeModelShape = List.of(
            new String[]{"Tesla", "Model 3", "Sedan"},
            new String[]{"BMW", "3 Series", "Sedan"},
            new String[]{"VinFast", "VF 8", "SUV"},
            new String[]{"Toyota", "Corolla", "Sedan"},
            new String[]{"Hyundai", "Ioniq 5", "SUV"},
            new String[]{"Ford", "Mustang Mach-E", "SUV"},
            new String[]{"Chevrolet", "Bolt", "Hatchback"}
    );

    private final List<String> colors = List.of("Black", "White", "Gray", "Red", "Blue");

    private final List<String> descriptions = List.of(
            "Great condition, well-maintained EV with excellent range.",
            "Low mileage and perfect for city driving.",
            "Spacious and modern, with full autopilot features.",
            "Certified pre-owned, recently serviced, clean interior.",
            "Sporty design with impressive acceleration."
    );

    private final List<String> reviewers = List.of(
            "Alice", "Bob", "Charlie", "David", "Emma", "Frank", "Grace", "Helen"
    );

    private final List<String> comments = List.of(
            "Amazing car!",
            "Smooth ride and great range.",
            "Not worth the price.",
            "Would definitely recommend.",
            "Interior feels cheap.",
            "Battery life is decent.",
            "Drives like a dream.",
            "Exterior design could be better."
    );

    @Override
    public void run(String... args) {
        reviewRepository.deleteAll();
        vehicleRepository.deleteAll();

        for (int i = 0; i < 50; i++) {
            String[] makeModel = makeModelShape.get(random.nextInt(makeModelShape.size()));
            String make = makeModel[0];
            String model = makeModel[1];
            String shape = makeModel[2];

            Vehicle v = new Vehicle();
            v.setVin("DUMMYVIN" + String.format("%05d", i));
            v.setMake(make);
            v.setModel(model);
            v.setShape(shape);
            v.setColor(colors.get(random.nextInt(colors.size())));
            v.setYear(random.nextInt(2024 - 2020 + 1) + 2020);
            v.setMileage(random.nextInt(60001));
            v.setDescription(descriptions.get(random.nextInt(descriptions.size())));
            v.setNewVehicle(random.nextBoolean());
            v.setAccident(random.nextBoolean());
            v.setHotDeal(random.nextBoolean());
            v.setImageUrls(Arrays.asList(imageSamples.get(random.nextInt(imageSamples.size()))));
            v.setPrice(Math.round((random.nextDouble() * (80000 - 30000) + 30000) * 100.0) / 100.0);

            vehicleRepository.save(v);

            int numberOfReviews = 2 + random.nextInt(2); // 2–3 reviews
            for (int j = 0; j < numberOfReviews; j++) {
                Review review = new Review();
                review.setRating(3 + random.nextInt(3)); // 3–5 stars
                review.setComment(comments.get(random.nextInt(comments.size())));
                review.setReviewer(reviewers.get(random.nextInt(reviewers.size())));
                review.setVehicle(v);

                reviewRepository.save(review);
            }
        }
    }
}
