package com.parkingspot.parkingspot.controllers;

import com.parkingspot.parkingspot.models.ParkingSpotModel;
import com.parkingspot.parkingspot.services.ParkingSpotService;
import com.parkingspot.parkingspot.dtos.ParkingSpotDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins="*", maxAge = 3600)
@RequestMapping("parking-spot")
public class ParkingSpotRestController {
    final ParkingSpotService parkingSpotService;

    public ParkingSpotRestController(ParkingSpotService parkingSpotService) {
        this.parkingSpotService = parkingSpotService;
    }

    /*
    *   Endereço: [POST] http://localhost:8080/parking-spot
        Body (json):
        {
	        "parkingNumber" : 2,
            "carNumber" : "ISS8006",
  	        "owner" : "Maria",
            "localization" : "Andar 1"
         }
    *
    */
    @PostMapping
    public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotDTO parkingSpotDTO){

        if(parkingSpotService.existsByParkingNumber(parkingSpotDTO.getParkingNumber())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe um carro cadastrado nesta vaga");
        }

        if(parkingSpotService.existsByOwner(parkingSpotDTO.getOwner())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe um carro cadastrado deste proprietário");
        }

        if(parkingSpotService.existsByCarNumber(parkingSpotDTO.getCarNumber())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Este veículo já está cadastrado");
        }

        ParkingSpotModel parkingSpotModel = new ParkingSpotModel();
        BeanUtils.copyProperties(parkingSpotDTO, parkingSpotModel);
        parkingSpotModel.setDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parkingSpotModel));
    }

    @GetMapping
    public ResponseEntity<List<ParkingSpotModel>> getAllParkingSpot(){
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Object> getParkingSpotById(@PathVariable(value = "id") Long id){
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        if (!parkingSpotModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot Não encontrado.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotModelOptional.get());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteParkingSpot(@PathVariable(value = "id") Long id){
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        if (!parkingSpotModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot Não encontrado.");
        }
        parkingSpotService.delete(parkingSpotModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Parking Spot deletado com sucesso.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateParkingSpot(@PathVariable(value = "id") Long id,
                                                    @RequestBody @Valid ParkingSpotDTO parkingSpotDto){
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        if (!parkingSpotModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot não encontrado.");
        }
        var parkingSpotModel = new ParkingSpotModel();
        BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);
        parkingSpotModel.setId(parkingSpotModelOptional.get().getId());
        parkingSpotModel.setDate(parkingSpotModelOptional.get().getDate());
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.save(parkingSpotModel));
    }

}
