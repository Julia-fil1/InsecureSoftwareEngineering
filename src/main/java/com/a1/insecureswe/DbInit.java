package com.a1.insecureswe;

import com.a1.insecureswe.model.Vaccinee;
import com.a1.insecureswe.repository.VaccineeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class DbInit implements CommandLineRunner {

    private VaccineeRepository vaccineeRepository;

    public DbInit(VaccineeRepository vaccineeRepository) {
        this.vaccineeRepository = vaccineeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        this.vaccineeRepository.deleteAll();

        Vaccinee vaccinee1 = new Vaccinee();

        vaccinee1.setId(1L);
        vaccinee1.setUsername("john");
        vaccinee1.setPassword("password");
        vaccinee1.setName("John");
        vaccinee1.setSurname("Doe");
        vaccinee1.setDob(LocalDate.of(1985, 10, 25));
        vaccinee1.setPpsNumber("1234567C");
        vaccinee1.setAddress("10 Main Street, Dundrum, D14 A1B3");
        vaccinee1.setPhoneNumber("087123456");
        vaccinee1.setEmail("john.doe@gmail.com");
        vaccinee1.setNationality("Irish");

        List<Vaccinee> vaccinees = Arrays.asList(vaccinee1);

        this.vaccineeRepository.saveAll(vaccinees);
    }
}
