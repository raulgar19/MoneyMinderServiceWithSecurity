package org.moneyMinder.controllers;

import org.moneyMinder.controllers.dto.UsuarioDto;
import org.moneyMinder.entity.Usuario;
import org.moneyMinder.services.CarteraService;
import org.moneyMinder.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    /**
     * Crea un nuevo usuario.
     *
     * @param userDto El DTO del usuario que se va a crear.
     * @return ResponseEntity que contiene el DTO del usuario creado con el estado HTTP correspondiente.
     */
    @PostMapping("/usuarios")
    public ResponseEntity<UsuarioDto> createUser(@RequestBody UsuarioDto userDto) {
        Usuario usuario = UsuarioDto.toEntity(userDto);
        usuarioService.saveUsuario(usuario);
        return ResponseEntity.ok(userDto);
    }

    /**
     * Obtiene un usuario por correo y contraseña.
     *
     * @param correo El correo del usuario.
     * @param pass La contraseña del usuario.
     * @return ResponseEntity que contiene el DTO del usuario encontrado con el estado HTTP correspondiente.
     */
    @GetMapping("/usuarios/correoAndPass")
    public ResponseEntity<UsuarioDto> getUsuarioByCorreoAndPass(
            @RequestParam(name = "correo") String correo,
            @RequestParam(name = "pass") String pass) {
        Usuario usuario = usuarioService.getUsuarioByCorreoAndPass(correo, pass);
        UsuarioDto usuarioDto = UsuarioDto.toDto(usuario);
        return ResponseEntity.ok(usuarioDto);
    }

    /**
     * Obtiene un usuario por su correo.
     *
     * @param correo El correo del usuario que se desea obtener.
     * @return ResponseEntity que contiene el DTO del usuario encontrado con el estado HTTP correspondiente.
     */
    @GetMapping("/usuarios/{correo}")
    public ResponseEntity<UsuarioDto> getUsuarioByCorreo(@PathVariable String correo) {
        Usuario usuario = usuarioService.getUsuarioByCorreo(correo);
        UsuarioDto usuarioDto = UsuarioDto.toDto(usuario);
        return ResponseEntity.ok(usuarioDto);
    }

    /**
     * Actualiza un usuario por su ID.
     *
     * @param id El ID del usuario que se va a actualizar.
     * @param usuarioDto El DTO con los nuevos datos del usuario.
     * @return ResponseEntity que contiene el DTO del usuario actualizado con el estado HTTP correspondiente.
     */
    @PutMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioDto> updateUsuario(
            @PathVariable("id") int id,
            @Valid @RequestBody UsuarioDto usuarioDto) {
        Optional<Usuario> usuarioOptional = usuarioService.getUsuario(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setNombre(usuarioDto.getNombre());
            usuario.setApellido(usuarioDto.getApellido());
            usuario.setCorreo(usuarioDto.getCorreo());
            usuario.setPass(usuarioDto.getPass());
            Usuario newUsuario = usuarioService.updateUsuario(usuario, id);
            UsuarioDto newUsuarioDto = UsuarioDto.toDto(newUsuario);
            return ResponseEntity.ok(newUsuarioDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param id El ID del usuario que se va a eliminar.
     * @return ResponseEntity con el estado HTTP correspondiente (OK si el usuario se eliminó con éxito,
     *         NOT_FOUND si no se encontró el usuario con el ID especificado).
     */
    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable("id") int id) {
        if (usuarioService.deleteUsuario(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
