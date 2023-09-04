package org.moneyMinder.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.moneyMinder.entity.Sector;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SectorDto {
    @NotNull
    private Integer id;
    @NotBlank
    private String nombre;
    public static Sector toEntity(SectorDto sectorDto) {
        return new Sector(
                sectorDto.getId(),
                sectorDto.getNombre(),
                new ArrayList<>());
    }

    public static SectorDto toDto(Sector sector) {
        return new SectorDto(
                sector.getId(),
                sector.getNombre());
    }
}