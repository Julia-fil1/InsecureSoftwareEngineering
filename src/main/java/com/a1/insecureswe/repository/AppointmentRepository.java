package com.a1.insecureswe.repository;

import com.a1.insecureswe.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query(value = "SELECT appointment_time FROM appointments v WHERE v.location = :location AND v.appointment_date = :date", nativeQuery = true)
    List<String> findTakenTimesFor(@Param("location") String location, @Param("date") LocalDate date);
}
