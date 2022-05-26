package com.parkingspot.parkingspot.services;

import com.parkingspot.parkingspot.models.ParkingSpotModel;
import com.parkingspot.parkingspot.repositories.ParkingSpotRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingSpotService {

    final
    ParkingSpotRepository parkingSpotRepository;

    public ParkingSpotService(ParkingSpotRepository parkingSpotRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
    }

    @Transactional
    public ParkingSpotModel save(ParkingSpotModel parkingSpotModel) {
        return parkingSpotRepository.save(parkingSpotModel);
    }

    public boolean existsByParkingNumber(String parkingNumber) {
        return parkingSpotRepository.existsByParkingNumber(parkingNumber);
    }

    public boolean existsByOwner(String owner) {
        return parkingSpotRepository.existsByOwner(owner);
    }

    public boolean existsByCarNumber(String carNumber) {
        return parkingSpotRepository.existsByCarNumber(carNumber);
    }

    public Page<ParkingSpotModel> findAll(Pageable paginacao) {
        return parkingSpotRepository.findAll(paginacao);
    }

    public List<ParkingSpotModel> findAll() {
        return parkingSpotRepository.findAll();
    }

    public Optional<ParkingSpotModel> findById(Long id) {
        return parkingSpotRepository.findById(id);
    }

    @Transactional
    public void delete(ParkingSpotModel parkingSpotModel) {
        parkingSpotRepository.delete(parkingSpotModel);
    }

}
