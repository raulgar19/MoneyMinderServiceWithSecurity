package org.moneyMinder.controllers;

import org.moneyMinder.controllers.dto.GastoDto;
import org.moneyMinder.entity.Gasto;
import org.moneyMinder.entity.Usuario;
import org.moneyMinder.services.CarteraService;
import org.moneyMinder.services.GastoService;
import org.moneyMinder.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class GastoController {
    @Autowired
    private GastoService gastoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CarteraService carteraService;

    @PostMapping("/gastos")
    public ResponseEntity<GastoDto> createGasto(@Valid @RequestBody GastoDto gastoDto) {
        Gasto gasto = GastoDto.toEntity(gastoDto);
        gastoService.saveGasto(gasto);
        Integer usuarioId = gastoDto.getUsuario().getId();
        BigDecimal cantidad = gasto.getCantidad();
        carteraService.updateDineroByGastoAndUser(usuarioId, cantidad);
        GastoDto newGastoDto = GastoDto.toDto(gasto);
        return ResponseEntity.ok(newGastoDto);
    }

    @GetMapping("/usuarios/{id}/gastos")
    public ResponseEntity<List<GastoDto>> getGastosByUserId(@PathVariable("id") Integer usuarioId) {
        Optional<Usuario> usuarioOptional = usuarioService.getUsuario(usuarioId);
        if (usuarioOptional.isPresent()) {
            List<Gasto> gastos = gastoService.getGastos(usuarioId);
            List<GastoDto> gastosDto = gastos
                    .stream()
                    .map(GastoDto::toDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(gastosDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/usuarios/{id}/gastos/cantTotal")
    public BigDecimal getCantidadTotalByUsuarioId(@PathVariable("id") Integer usuarioId) {
        BigDecimal cantidadTotal = gastoService.getCantidadTotalByUsuarioId(usuarioId);
        return cantidadTotal;
    }

    @GetMapping("/usuarios/{id}/gastos/categoria/{categoria}/cantTotal")
    public BigDecimal getCantidadTotalByUsuarioId(@PathVariable("id") Integer usuarioId, @PathVariable("categoria") String nombreSector) {
        BigDecimal cantidadTotal = gastoService.getCantidadTotalByUsuarioIdAndSector(usuarioId, nombreSector);
        return cantidadTotal;
    }

    @DeleteMapping("/gastos/{id}")
    public ResponseEntity<Void> deleteGastos(@PathVariable("id") Integer id) {
        gastoService.deleteGastos(id);
        return ResponseEntity.ok().build();
    }
}