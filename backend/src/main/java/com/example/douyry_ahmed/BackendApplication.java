package com.example.douyry_ahmed;

import com.example.douyry_ahmed.dtos.AgencyDto;
import com.example.douyry_ahmed.dtos.RentalDto;
import com.example.douyry_ahmed.dtos.VehicleDto;
import com.example.douyry_ahmed.entities.AppUser;
import com.example.douyry_ahmed.enums.UserRole;
import com.example.douyry_ahmed.repositories.AppUserRepository;
import com.example.douyry_ahmed.services.LocationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    CommandLineRunner seed(LocationService locationService,
                           AppUserRepository appUserRepository,
                           PasswordEncoder passwordEncoder) {
        return args -> {
            if (appUserRepository.count() == 0) {
                appUserRepository.save(AppUser.builder().username("client").password(passwordEncoder.encode("1234")).role(UserRole.ROLE_CLIENT).build());
                appUserRepository.save(AppUser.builder().username("employe").password(passwordEncoder.encode("1234")).role(UserRole.ROLE_EMPLOYE).build());
                appUserRepository.save(AppUser.builder().username("admin").password(passwordEncoder.encode("1234")).role(UserRole.ROLE_ADMIN).build());
            }

            for (AppUser user : appUserRepository.findAll()) {
                if (!isSeedDevAccount(user.getUsername())) {
                    continue;
                }
                boolean changed = false;
                if (user.getRole() == null) {
                    user.setRole(roleForKnownUsername(user.getUsername()));
                    changed = true;
                }
                if (!looksLikeBcrypt(user.getPassword())) {
                    user.setPassword(passwordEncoder.encode("1234"));
                    changed = true;
                }
                if (changed) {
                    appUserRepository.save(user);
                }
            }

            if (locationService.listAgencies().isEmpty()) {
                AgencyDto a1 = new AgencyDto();
                a1.setNom("Agence Casa Centre");
                a1.setAdresse("Bd Zerktouni");
                a1.setVille("Casablanca");
                a1.setTelephone("+212500000001");
                AgencyDto saved = locationService.saveAgency(a1);

                VehicleDto car = new VehicleDto();
                car.setMarque("Renault");
                car.setModele("Clio");
                car.setMatricule("12345-A-1");
                car.setPrixParJour(new BigDecimal("250"));
                car.setDateMiseEnService(LocalDate.of(2023, 1, 15));
                car.setNombrePortes(5);
                car.setTypeCarburant("ESSENCE");
                car.setBoiteVitesse("MANUELLE");
                locationService.saveCar(saved.getId(), car);

                VehicleDto moto = new VehicleDto();
                moto.setMarque("Yamaha");
                moto.setModele("NMAX");
                moto.setMatricule("67890-B-2");
                moto.setPrixParJour(new BigDecimal("120"));
                moto.setDateMiseEnService(LocalDate.of(2024, 6, 1));
                moto.setCylindree(155);
                moto.setTypeMoto("SCOOTER");
                moto.setCasqueInclus(true);
                VehicleDto savedMoto = locationService.saveMoto(saved.getId(), moto);

                RentalDto rent = new RentalDto();
                rent.setDateDebut(LocalDate.now().minusDays(5));
                rent.setDateFin(LocalDate.now().minusDays(1));
                rent.setLocataire("Client Demo");
                RentalDto savedRent = locationService.createRental(savedMoto.getId(), rent);
                locationService.closeRental(savedRent.getId());
            }
        };
    }

    private static boolean isSeedDevAccount(String username) {
        if (username == null) {
            return false;
        }
        return switch (username.toLowerCase()) {
            case "admin", "employe", "client" -> true;
            default -> false;
        };
    }

    private static boolean looksLikeBcrypt(String encodedPassword) {
        if (encodedPassword == null || encodedPassword.length() < 60) {
            return false;
        }
        return encodedPassword.startsWith("$2a$")
                || encodedPassword.startsWith("$2b$")
                || encodedPassword.startsWith("$2y$");
    }

    /** Comptes créés à la main ou migrés sans colonne {@code role}. */
    private static UserRole roleForKnownUsername(String username) {
        if (username == null) {
            return UserRole.ROLE_CLIENT;
        }
        return switch (username.toLowerCase()) {
            case "admin" -> UserRole.ROLE_ADMIN;
            case "employe" -> UserRole.ROLE_EMPLOYE;
            case "client" -> UserRole.ROLE_CLIENT;
            default -> UserRole.ROLE_CLIENT;
        };
    }
}
