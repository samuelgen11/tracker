package com.perigea.tracker.timesheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.Utente;

@Repository
public interface UtenteRepository extends JpaRepository<Utente,Long> {
	
	public Utente findByCodicePersona(String codicePersona);

} 
