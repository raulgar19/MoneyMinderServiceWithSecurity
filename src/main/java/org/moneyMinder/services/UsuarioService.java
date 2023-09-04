package org.moneyMinder.services;

import org.moneyMinder.dao.UsuarioDAO;
import org.moneyMinder.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UsuarioService {
    @Autowired
    private UsuarioDAO usuarioDAO;

    public Usuario saveUsuario(Usuario usuario) {
        usuario.setId(null);
        return usuarioDAO.save(usuario);
    }

    public Optional<Usuario> getUsuario(int id) {
        return usuarioDAO.findById(id);
    }

    public Usuario getUsuarioByCorreoAndPass(String correo, String pass) {
        return usuarioDAO.findByCorreoAndPass(correo, pass);
    }

    public Usuario getUsuarioByCorreo(String correo) {
        return usuarioDAO.findByCorreo(correo);
    }

    public Usuario updateUsuario(Usuario usuario, int id) {
        usuario.setId(id);
        usuarioDAO.save(usuario);
        return usuario;
    }

    public boolean deleteUsuario(int id) {
        if (usuarioDAO.existsById(id)) {
            usuarioDAO.deleteById(id);
            return true;
        }
        else {
            return false;
        }
    }
}