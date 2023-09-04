package org.moneyMinder.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.moneyMinder.entity.Gasto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GastoDto {
    @NotNull
    private Integer id;
    @NotNull
    @Positive
    private BigDecimal cantidad;
    @NotNull
    private Timestamp fecha;
    @NotNull
    private SectorDto sector;
    @NotNull
    private UsuarioDto usuario;

    public static Gasto toEntity(GastoDto gastoDto){
        return new Gasto(
                gastoDto.getId(),
                gastoDto.getCantidad(),
                gastoDto.getFecha(),
                SectorDto.toEntity(gastoDto.getSector()),
                UsuarioDto.toEntity(gastoDto.getUsuario()));
    }

    public static GastoDto toDto(Gasto gasto){
        return new GastoDto(
                gasto.getId(),
                gasto.getCantidad(),
                gasto.getFecha(),
                SectorDto.toDto(gasto.getSector()),
                UsuarioDto.toDto(gasto.getUsuario()));
    }
}