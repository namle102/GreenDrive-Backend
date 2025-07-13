package com.greendrive.backend.config;

import com.greendrive.backend.model.Review;
import com.greendrive.backend.model.User;
import com.greendrive.backend.model.Vehicle;
import com.greendrive.backend.model.enums.Role;
import com.greendrive.backend.repository.ReviewRepository;
import com.greendrive.backend.repository.UserRepository;
import com.greendrive.backend.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;
    private final VehicleRepository vehicleRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
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
        userRepository.deleteAll();

        // Reset auto-increment counters
        jdbcTemplate.execute("ALTER TABLE review AUTO_INCREMENT = 1");
        jdbcTemplate.execute("ALTER TABLE vehicle AUTO_INCREMENT = 1");
        jdbcTemplate.execute("ALTER TABLE user AUTO_INCREMENT = 1");

        // Create users first so we can assign reviews to them
        List<User> users = createUsers();

        for (int i = 0; i < 50; i++) {
            String[] makeModel = makeModelShape.get(random.nextInt(makeModelShape.size()));
            String brand = makeModel[0];
            String model = makeModel[1];
            String shape = makeModel[2];

            Vehicle v = new Vehicle();
            v.setBrand(brand);
            v.setModel(model);
            v.setShape(shape);
            v.setExteriorColor(colors.get(random.nextInt(colors.size())));
            v.setInteriorColor(colors.get(random.nextInt(colors.size())));
            v.setInteriorMaterial("Leather");
            v.setYear(random.nextInt(2024 - 2020 + 1) + 2020);
            v.setMileage(random.nextInt(60001));
            v.setDescription(descriptions.get(random.nextInt(descriptions.size())));
            v.setNewVehicle(random.nextBoolean());
            v.setAccident(random.nextBoolean());
            v.setHotDeal(random.nextBoolean());
            v.setImageUrls(Arrays.asList(imageSamples.get(random.nextInt(imageSamples.size()))));
            v.setPrice(Math.round((random.nextDouble() * (80000 - 30000) + 30000) * 100.0) / 100.0);
            v.setQuantity(3 + random.nextInt(5)); // 3–7 in stock

            vehicleRepository.save(v);

            // Generate 2–3 reviews per vehicle
            int numberOfReviews = 2 + random.nextInt(2);
            for (int j = 0; j < numberOfReviews; j++) {
                Review review = new Review();
                review.setRating(3 + random.nextInt(3)); // 3–5 stars
                review.setComment(comments.get(random.nextInt(comments.size())));
                review.setVehicle(v);
                review.setUser(users.get(random.nextInt(users.size())));
                reviewRepository.save(review);
            }
        }
    }

    private List<User> createUsers() {
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            User user = new User();
            user.setUsername("user" + i);
            user.setEmail("user" + i + "@example.com");
            user.setPassword(passwordEncoder.encode("password" + i));
            user.setFirstName("First" + i);
            user.setLastName("Last" + i);
            user.setRole(i == 1 ? Role.ADMIN : Role.USER);
            users.add(userRepository.save(user));
        }
        return users;
    }
}
