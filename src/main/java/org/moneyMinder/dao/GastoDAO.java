package org.moneyMinder.dao;

import org.moneyMinder.entity.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface GastoDAO extends JpaRepository<Gasto, Integer> {
    List<Gasto> findAllByUsuarioId(Integer userId);

    @Query("SELECT COALESCE(SUM(g.cantidad), 0) FROM Gasto g WHERE g.usuario.id = ?1")
    BigDecimal sumCantidadByUsuarioId(Integer usuarioId);

    @Query("SELECT COALESCE(SUM(g.cantidad), 0) FROM Gasto g WHERE g.usuario.id = ?1 AND g.sector.nombre = ?2")
    BigDecimal sumCantidadByUsuarioIdAndSector(Integer usuarioId, String nombreSector);
}