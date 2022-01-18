package com.perigea.tracker.timesheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.UtenteRuoli;
import com.perigea.tracker.timesheet.entity.keys.UtenteRuoloKey;

@Repository
public interface UtenteRuoloRepository extends JpaRepository<UtenteRuoli, UtenteRuoloKey> {

}