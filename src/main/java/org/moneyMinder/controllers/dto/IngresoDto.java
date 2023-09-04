package org.moneyMinder.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.moneyMinder.entity.Ingreso;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IngresoDto {
    @NotNull
    private Integer id;
    @NotNull
    @Positive
    private BigDecimal cantidad;
    @NotNull
    private Timestamp fecha;
    @NotNull
    private UsuarioDto usuario;

    public static Ingreso toEntity(IngresoDto ingresoDto){
        return new Ingreso(
                ingresoDto.getId(),
                ingresoDto.getCantidad(),
                ingresoDto.getFecha(),
                UsuarioDto.toEntity(ingresoDto.getUsuario()));
    }

    public static IngresoDto toDto(Ingreso ingreso){
        return new IngresoDto(
                ingreso.getId(),
                ingreso.getCantidad(),
                ingreso.getFecha(),
                UsuarioDto.toDto(ingreso.getUsuario()));
    }
}