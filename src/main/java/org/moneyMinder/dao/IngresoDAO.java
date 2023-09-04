package org.moneyMinder.dao;

import org.moneyMinder.entity.Ingreso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface IngresoDAO extends JpaRepository<Ingreso, Integer> {
    List<Ingreso> findAllByUsuarioId(Integer userId);

    @Query("SELECT COALESCE(SUM(i.cantidad), 0) FROM Ingreso i WHERE i.usuario.id = ?1")
    BigDecimal sumCantidadByUsuarioId(Integer userId);
}