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

    /**
     * Crea un nuevo gasto y actualiza la cantidad en la cartera del usuario correspondiente.
     *
     * @param gastoDto El DTO del gasto que se va a crear y asociar a un usuario.
     * @return ResponseEntity que contiene el DTO del gasto creado con el estado HTTP correspondiente.
     */
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

    /**
     * Obtiene una lista de gastos por ID de usuario.
     *
     * @param usuarioId El ID del usuario para el cual se obtienen los gastos.
     * @return ResponseEntity que contiene una lista de DTO de gastos con el estado HTTP correspondiente.
     */
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

    /**
     * Obtiene la cantidad total de gastos por ID de usuario.
     *
     * @param usuarioId El ID del usuario para el cual se obtiene la cantidad total de gastos.
     * @return La cantidad total de gastos como un BigDecimal.
     */
    @GetMapping("/usuarios/{id}/gastos/cantTotal")
    public BigDecimal getCantidadTotalByUsuarioId(@PathVariable("id") Integer usuarioId) {
        BigDecimal cantidadTotal = gastoService.getCantidadTotalByUsuarioId(usuarioId);
        return cantidadTotal;
    }

    /**
     * Obtiene la cantidad total de gastos por ID de usuario y categoría.
     *
     * @param usuarioId El ID del usuario para el cual se obtiene la cantidad total de gastos.
     * @param nombreSector El nombre de la categoría de gastos.
     * @return La cantidad total de gastos en la categoría especificada como un BigDecimal.
     */
    @GetMapping("/usuarios/{id}/gastos/categoria/{categoria}/cantTotal")
    public BigDecimal getCantidadTotalByUsuarioIdAndCategoria(
            @PathVariable("id") Integer usuarioId,
            @PathVariable("categoria") String nombreSector) {
        BigDecimal cantidadTotal = gastoService.getCantidadTotalByUsuarioIdAndSector(usuarioId, nombreSector);
        return cantidadTotal;
    }

    /**
     * Elimina un gasto por su ID.
     *
     * @param id El ID del gasto que se va a eliminar.
     * @return ResponseEntity con el estado HTTP correspondiente (OK si el gasto se eliminó con éxito).
     */
    @DeleteMapping("/gastos/{id}")
    public ResponseEntity<Void> deleteGastos(@PathVariable("id") Integer id) {
        gastoService.deleteGastos(id);
        return ResponseEntity.ok().build();
    }
}
