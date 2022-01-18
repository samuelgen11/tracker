package com.perigea.tracker.timesheet.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perigea.tracker.timesheet.dto.GenericWrapperResponse;
import com.perigea.tracker.timesheet.dto.TimeSheetDataDto;
import com.perigea.tracker.timesheet.dto.TimeSheetResponseDto;
import com.perigea.tracker.timesheet.dto.wrapper.TimeSheetDataWrapper;
import com.perigea.tracker.timesheet.service.TimesheetService;



@RestController
@RequestMapping("/timesheet")
public class TimesheetController {

	@Autowired
	private TimesheetService timeSheetService;

	// Metodo per creare un timesheet
	@PostMapping(value = "/create-timesheet")
	public ResponseEntity<GenericWrapperResponse<TimeSheetResponseDto>> createTimeSheet(@RequestBody TimeSheetDataWrapper wrapper) {
		TimeSheetResponseDto dto = timeSheetService.createTimeSheet(wrapper.getList(),wrapper.getDto());
		GenericWrapperResponse<TimeSheetResponseDto> genericDto = GenericWrapperResponse.<TimeSheetResponseDto>builder()
				.dataRichiesta(new Date()).risultato(dto).build();
		return ResponseEntity.ok(genericDto);
	}

}