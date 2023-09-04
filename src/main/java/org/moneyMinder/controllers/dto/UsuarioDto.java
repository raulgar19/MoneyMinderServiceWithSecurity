package org.moneyMinder.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.moneyMinder.entity.Usuario;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {
    @NotNull
    private Integer id;
    @NotBlank
    private String nombre;
    @NotBlank
    private String apellido;
    @NotBlank
    private String correo;
    @NotBlank
    private String pass;
    @NotNull
    private CarteraDto cartera;

    public static Usuario toEntity(UsuarioDto usuarioDto){
        return new Usuario(
                usuarioDto.getId(),
                usuarioDto.getNombre(),
                usuarioDto.getApellido(),
                usuarioDto.getCorreo(),
                usuarioDto.getPass(),
                CarteraDto.toEntity(usuarioDto.getCartera()),
                new ArrayList<>(),
                new ArrayList<>());
    }

    public static UsuarioDto toDto(Usuario usuario){
        return new UsuarioDto(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getCorreo(),
                usuario.getPass(),
                CarteraDto.toDto(usuario.getCartera()));
    }
}