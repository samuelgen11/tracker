package com.perigea.tracker.timesheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.TimeSheet;
import com.perigea.tracker.timesheet.entity.keys.TimeSheetMensileKey;

@Repository
public interface TimeSheetRepository extends JpaRepository<TimeSheet, TimeSheetMensileKey> {
	
}
