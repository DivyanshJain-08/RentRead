package com.crio.RentRead.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crio.RentRead.Model.AvailabilityStatus;
import com.crio.RentRead.Model.Rental;
import com.crio.RentRead.Model.User;

public interface RentalRepository extends JpaRepository<Rental, Long>
{   
    long countByUserAndStatus(User user, AvailabilityStatus status);
    List<Rental> findByUserAndStatus(User user, AvailabilityStatus status);
}