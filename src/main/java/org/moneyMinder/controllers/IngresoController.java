package org.moneyMinder.controllers;

import org.moneyMinder.controllers.dto.GastoDto;
import org.moneyMinder.controllers.dto.IngresoDto;
import org.moneyMinder.entity.Ingreso;
import org.moneyMinder.entity.Usuario;
import org.moneyMinder.services.CarteraService;
import org.moneyMinder.services.IngresoService;
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
public class IngresoController {
    @Autowired
    private IngresoService ingresoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CarteraService carteraService;

    /**
     * Crea un nuevo ingreso y actualiza la cantidad en la cartera del usuario correspondiente.
     *
     * @param ingresoDto El DTO del ingreso que se va a crear y asociar a un usuario.
     * @return ResponseEntity que contiene el DTO del ingreso creado con el estado HTTP correspondiente.
     */
    @PostMapping("/ingresos")
    public ResponseEntity<IngresoDto> createIngreso(@Valid @RequestBody IngresoDto ingresoDto) {
        Ingreso ingreso = IngresoDto.toEntity(ingresoDto);
        ingreso.setId(null);
        Ingreso newIngreso = ingresoService.saveIngreso(ingreso);
        Integer usuarioId = ingresoDto.getUsuario().getId();
        BigDecimal cantidad = newIngreso.getCantidad();
        carteraService.updateDineroByIngresoAndUser(usuarioId, cantidad);
        IngresoDto newIngresoDto = IngresoDto.toDto(newIngreso);
        return ResponseEntity.ok(newIngresoDto);
    }

    /**
     * Obtiene una lista de ingresos por ID de usuario.
     *
     * @param usuarioId El ID del usuario para el cual se obtienen los ingresos.
     * @return ResponseEntity que contiene una lista de DTO de ingresos con el estado HTTP correspondiente.
     */
    @GetMapping("/usuarios/{id}/ingresos")
    public ResponseEntity<List<IngresoDto>> getIngresosByUserId(@PathVariable("id") Integer usuarioId) {
        Optional<Usuario> usuarioOptional = usuarioService.getUsuario(usuarioId);
        if (usuarioOptional.isPresent()) {
            List<Ingreso> ingresos = ingresoService.getIngresos(usuarioId);
            List<IngresoDto> ingresosDto = ingresos
                    .stream()
                    .map(IngresoDto::toDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(ingresosDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtiene la cantidad total de ingresos por ID de usuario.
     *
     * @param usuarioId El ID del usuario para el cual se obtiene la cantidad total de ingresos.
     * @return La cantidad total de ingresos como un BigDecimal.
     */
    @GetMapping("/usuarios/{id}/totalCan")
    public BigDecimal getCantidadTotalByUsuarioId(@PathVariable("id") Integer usuarioId) {
        BigDecimal cantidadTotal = ingresoService.getCantidadTotalByUsuarioId(usuarioId);
        return cantidadTotal;
    }

    /**
     * Elimina un ingreso por su ID.
     *
     * @param id El ID del ingreso que se va a eliminar.
     * @return ResponseEntity con el estado HTTP correspondiente (OK si el ingreso se eliminó con éxito).
     */
    @DeleteMapping("/ingresos/{id}")
    public ResponseEntity<Void> deleteIngresos(@PathVariable("id") Integer id) {
        ingresoService.deleteIngresos(id);
        return ResponseEntity.ok().build();
    }
}
