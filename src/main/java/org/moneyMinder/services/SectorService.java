package org.moneyMinder.services;

import org.moneyMinder.dao.SectorDAO;
import org.moneyMinder.entity.Sector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SectorService {
    @Autowired
    private SectorDAO sectorDAO;

    public Optional<Sector> getSectorByName(String nombre) {
        Optional<Sector> sector = sectorDAO.findByNombre(nombre);
        return sector;
    }
}