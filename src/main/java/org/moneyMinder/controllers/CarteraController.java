package org.moneyMinder.controllers;

import org.moneyMinder.controllers.dto.CarteraDto;
import org.moneyMinder.entity.Cartera;
import org.moneyMinder.services.CarteraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class CarteraController {
    @Autowired
    private CarteraService carteraService;

    @PostMapping("/carteras")
    public ResponseEntity<CarteraDto> createCartera(@RequestBody CarteraDto carteraDto) {
        Cartera cartera = CarteraDto.toEntity(carteraDto);
        carteraService.saveCartera(cartera);
        return ResponseEntity.ok(carteraDto);
    }

    @GetMapping("/carteras")
    public ResponseEntity<CarteraDto> getCarteraByNumCuenta(@RequestParam(name = "numCuenta") String numCuenta){
        Cartera cartera = carteraService.getCarteraByNumCuenta(numCuenta);
        CarteraDto carteraDto = CarteraDto.toDto(cartera);
        return ResponseEntity.ok(carteraDto);
    }

    @DeleteMapping("/carteras/{id}")
    public ResponseEntity<Void> deleteCartera (@PathVariable("id") int id){
        Optional<Cartera> carteraOptional = carteraService.getCartera(id);
        if (carteraOptional.isPresent()) {
            carteraService.deleteCartera(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}