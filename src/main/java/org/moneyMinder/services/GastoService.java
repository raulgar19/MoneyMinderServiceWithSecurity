package org.moneyMinder.services;

import org.moneyMinder.controllers.dto.GastoDto;
import org.moneyMinder.dao.GastoDAO;
import org.moneyMinder.entity.Gasto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GastoService {
    @Autowired
    private GastoDAO gastoDAO;

    public Gasto saveGasto(Gasto gasto) {
        gasto.setId(null);
        return gastoDAO.save(gasto);
    }
    public List<Gasto> getGastos(Integer userId) {
        return gastoDAO.findAllByUsuarioId(userId);
    }

    public BigDecimal getCantidadTotalByUsuarioId(Integer usuarioId) {
        BigDecimal cantidadTotal = gastoDAO.sumCantidadByUsuarioId(usuarioId);
        return cantidadTotal != null ? cantidadTotal : BigDecimal.ZERO;
    }

    public BigDecimal getCantidadTotalByUsuarioIdAndSector(Integer usuarioId, String nombreSector) {
        BigDecimal cantidadTotal = gastoDAO.sumCantidadByUsuarioIdAndSector(usuarioId, nombreSector);
        return cantidadTotal != null ? cantidadTotal : BigDecimal.ZERO;
    }

    public void deleteGastos(Integer id) {
        gastoDAO.deleteById(id);
    }
}