package com.perigea.tracker.timesheet.dto.wrapper;

import java.util.ArrayList;
import java.util.List;

import com.perigea.tracker.timesheet.dto.TimeSheetDataDto;
import com.perigea.tracker.timesheet.dto.TimeSheetInputDto;

import lombok.Data;

@Data
public class TimeSheetDataWrapper {
	private List<TimeSheetDataDto> list = new ArrayList<>(25);
	private TimeSheetInputDto dto;
}
