package org.moneyMinder.services;

import org.moneyMinder.dao.CarteraDAO;
import org.moneyMinder.entity.Cartera;
import org.moneyMinder.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CarteraService {
    @Autowired
    private CarteraDAO carteraDAO;
    @Autowired
    private UsuarioService usuarioService;

    public Cartera saveCartera(Cartera cartera) {
        cartera.setId(null);
        carteraDAO.save(cartera);
        return cartera;
    }

    public Optional<Cartera> getCartera(@NotNull @Positive Integer id) {
        Optional<Cartera> cartera = carteraDAO.findById(id);
        return cartera;
    }

    public Cartera getCarteraByNumCuenta(String numCuenta) {
        return carteraDAO.findByNumCuenta(numCuenta);
    }

    public void updateDineroByGastoAndUser(Integer usuarioId, BigDecimal cantidad) {
        Cartera cartera = obtenerCarteraPorUsuarioId(usuarioId);
        BigDecimal dineroActual = cartera.getDinero();
        BigDecimal nuevoDinero = dineroActual.subtract(cantidad);
        cartera.setDinero(nuevoDinero);
        carteraDAO.save(cartera);
    }

    private Cartera obtenerCarteraPorUsuarioId(Integer usuarioId) {
        Optional<Usuario> usuario = usuarioService.getUsuario(usuarioId);
        return usuario.get().getCartera();
    }

    public void updateDineroByIngresoAndUser(Integer usuarioId, BigDecimal cantidad) {
        Cartera cartera = obtenerCarteraPorUsuarioId(usuarioId);
        BigDecimal dineroActual = cartera.getDinero();
        BigDecimal nuevoDinero = dineroActual.add(cantidad);
        cartera.setDinero(nuevoDinero);
        carteraDAO.save(cartera);
    }

    public boolean deleteCartera(int id) {
        if (carteraDAO.existsById(id)) {
            carteraDAO.deleteById(id);
            return true;
        }
        else {
            return false;
        }
    }
}