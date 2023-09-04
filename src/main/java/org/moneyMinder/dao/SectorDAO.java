package org.moneyMinder.dao;

import org.moneyMinder.entity.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SectorDAO extends JpaRepository<Sector, Integer> {
    Optional<Sector> findByNombre(String nombre);
}