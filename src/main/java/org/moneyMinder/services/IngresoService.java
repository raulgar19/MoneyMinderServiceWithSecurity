package org.moneyMinder.services;

import org.moneyMinder.controllers.dto.IngresoDto;
import org.moneyMinder.dao.IngresoDAO;
import org.moneyMinder.entity.Ingreso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IngresoService {
    @Autowired
    private IngresoDAO ingresoDAO;
    public Ingreso saveIngreso(Ingreso ingreso) {
        ingresoDAO.save(ingreso);
        return ingreso;
    }

    public List<Ingreso> getIngresos(Integer userId) {
        return ingresoDAO.findAllByUsuarioId(userId);
    }
    public Optional<Ingreso> getIngreso(Integer id) {
        Optional<Ingreso> ingreso = ingresoDAO.findById(id);
        return ingreso;
    }

    public BigDecimal getCantidadTotalByUsuarioId(Integer userId) {
        BigDecimal cantidadTotal = ingresoDAO.sumCantidadByUsuarioId(userId);
        return cantidadTotal != null ? cantidadTotal : BigDecimal.ZERO;
    }

    public void deleteIngresos(Integer id) {
        ingresoDAO.deleteById(id);
    }
}