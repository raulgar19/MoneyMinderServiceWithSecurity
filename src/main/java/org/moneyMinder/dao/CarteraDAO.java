package org.moneyMinder.dao;

import org.moneyMinder.entity.Cartera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarteraDAO extends JpaRepository<Cartera, Integer> {
    Cartera findByNumCuenta(String numCuenta);
}