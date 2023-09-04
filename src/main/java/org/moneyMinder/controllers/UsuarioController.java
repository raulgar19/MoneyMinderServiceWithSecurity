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

    @PostMapping("/usuarios")
    public ResponseEntity<UsuarioDto> createUser(@RequestBody UsuarioDto userDto) {
        Usuario usuario = UsuarioDto.toEntity(userDto);
        usuarioService.saveUsuario(usuario);
        return ResponseEntity.ok(userDto);
    }


    @GetMapping("/usuarios/correoAndPass")
    public ResponseEntity<UsuarioDto> getUsuarioByCorreoAndPass(@RequestParam(name = "correo") String correo, @RequestParam(name = "pass") String pass){
        Usuario usuario = usuarioService.getUsuarioByCorreoAndPass(correo, pass);
        UsuarioDto usuarioDto = UsuarioDto.toDto(usuario);
        return ResponseEntity.ok(usuarioDto);
    }

    @GetMapping("/usuarios/{correo}")
    public ResponseEntity<UsuarioDto> getUsuarioByCorreo(@PathVariable String correo){
        Usuario usuario = usuarioService.getUsuarioByCorreo(correo);
        UsuarioDto usuarioDto = UsuarioDto.toDto(usuario);
        return ResponseEntity.ok(usuarioDto);
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioDto> updateUsuario(@PathVariable("id") int id, @Valid @RequestBody UsuarioDto usuarioDto){
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

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable("id") int id){
        if (usuarioService.deleteUsuario(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}