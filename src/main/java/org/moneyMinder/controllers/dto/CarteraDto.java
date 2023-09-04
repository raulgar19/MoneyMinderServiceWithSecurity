package org.moneyMinder.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.moneyMinder.entity.Cartera;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarteraDto {
    @NotNull
    private Integer id;
    @NotBlank
    private String numCuenta;
    @NotNull
    @Positive
    private BigDecimal dinero;

    public static Cartera toEntity(CarteraDto carteraDto){
        return new Cartera(
                carteraDto.getId(),
                carteraDto.getNumCuenta(),
                carteraDto.getDinero(),
                null);
    }

    public static CarteraDto toDto(Cartera cartera){
        return new CarteraDto(
                cartera.getId(),
                cartera.getNumCuenta(),
                cartera.getDinero());
    }
}