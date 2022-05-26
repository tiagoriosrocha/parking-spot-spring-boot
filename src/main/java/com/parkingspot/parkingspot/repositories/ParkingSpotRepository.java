package com.parkingspot.parkingspot.repositories;

import com.parkingspot.parkingspot.models.ParkingSpotModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpotModel, Long> {
    boolean existsByParkingNumber(String parkingNumber);
    boolean existsByOwner(String owner);
    boolean existsByCarNumber(String carNumber);
    Page<ParkingSpotModel> findAll(Pageable paginacao);
}
