package com.a1.insecureswe.repository;

import com.a1.insecureswe.model.Vaccinee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VaccineeRepository extends JpaRepository<Vaccinee, Long> {
}
