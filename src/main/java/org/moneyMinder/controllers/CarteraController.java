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

    /**
     * Crea una nueva cartera.
     *
     * @param carteraDto La representación en DTO de la cartera que se va a crear.
     * @return ResponseEntity que contiene el DTO de la cartera creada con el estado HTTP correspondiente.
     */
    @PostMapping("/carteras")
    public ResponseEntity<CarteraDto> createCartera(@RequestBody CarteraDto carteraDto) {
        Cartera cartera = CarteraDto.toEntity(carteraDto);
        carteraService.saveCartera(cartera);
        return ResponseEntity.ok(carteraDto);
    }

    /**
     * Obtiene una cartera por número de cuenta.
     *
     * @param numCuenta El número de cuenta de la cartera que se desea obtener.
     * @return ResponseEntity que contiene el DTO de la cartera encontrada con el estado HTTP correspondiente.
     */
    @GetMapping("/carteras")
    public ResponseEntity<CarteraDto> getCarteraByNumCuenta(@RequestParam(name = "numCuenta") String numCuenta) {
        Cartera cartera = carteraService.getCarteraByNumCuenta(numCuenta);
        CarteraDto carteraDto = CarteraDto.toDto(cartera);
        return ResponseEntity.ok(carteraDto);
    }

    /**
     * Elimina una cartera por su ID.
     *
     * @param id El ID de la cartera que se va a eliminar.
     * @return ResponseEntity con el estado HTTP correspondiente (OK si la cartera se eliminó con éxito,
     *         NOT_FOUND si no se encontró la cartera con el ID especificado).
     */
    @DeleteMapping("/carteras/{id}")
    public ResponseEntity<Void> deleteCartera(@PathVariable("id") int id) {
        Optional<Cartera> carteraOptional = carteraService.getCartera(id);
        if (carteraOptional.isPresent()) {
            carteraService.deleteCartera(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
