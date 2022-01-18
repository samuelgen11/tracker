package com.perigea.tracker.timesheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perigea.tracker.timesheet.entity.TimeSheetData;
import com.perigea.tracker.timesheet.entity.keys.TimeSheetDataKey;

@Repository
public interface TimeSheetDataRepository extends JpaRepository<TimeSheetData, TimeSheetDataKey> {
	
	
	
	
} 
