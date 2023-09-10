package org.moneyMinder.controllers;

import org.moneyMinder.controllers.dto.SectorDto;
import org.moneyMinder.entity.Sector;
import org.moneyMinder.services.SectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class SectorController {
    @Autowired
    private SectorService sectorService; // No es necesario inicializarlo aquí

    /**
     * Obtiene un sector por nombre.
     *
     * @param nombre El nombre del sector que se desea obtener.
     * @return ResponseEntity que contiene el DTO del sector encontrado con el estado HTTP correspondiente.
     */
    @GetMapping("/sectores/nombre/{nombre}")
    public ResponseEntity<SectorDto> getSectorByName(@PathVariable("nombre") String nombre){
        Optional<Sector> sectorOptional = sectorService.getSectorByName(nombre);
        if (sectorOptional.isPresent()) {
            Sector sector = sectorOptional.get();
            SectorDto sectorDto = SectorDto.toDto(sector);
            return ResponseEntity.ok(sectorDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
