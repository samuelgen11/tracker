package com.perigea.tracker.timesheet.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perigea.tracker.timesheet.dto.TimeSheetDataDto;
import com.perigea.tracker.timesheet.dto.TimeSheetInputDto;
import com.perigea.tracker.timesheet.dto.TimeSheetResponseDto;
import com.perigea.tracker.timesheet.entity.Commessa;
import com.perigea.tracker.timesheet.entity.Festivita;
import com.perigea.tracker.timesheet.entity.TimeSheet;
import com.perigea.tracker.timesheet.entity.TimeSheetData;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.entity.keys.TimeSheetDataKey;
import com.perigea.tracker.timesheet.entity.keys.TimeSheetMensileKey;
import com.perigea.tracker.timesheet.exception.FestivitaException;
import com.perigea.tracker.timesheet.exception.TimeSheetException;
import com.perigea.tracker.timesheet.mapstruct.DtoEntityMapper;
import com.perigea.tracker.timesheet.repository.CommessaRepository;
import com.perigea.tracker.timesheet.repository.FestivitaRepository;
import com.perigea.tracker.timesheet.repository.TimeSheetDataRepository;
import com.perigea.tracker.timesheet.repository.TimeSheetRepository;
import com.perigea.tracker.timesheet.repository.UtenteRepository;

@Service
public class TimesheetService {

	@Autowired
	private Logger logger;

	@Autowired
	private TimeSheetDataRepository timesheetRepository;

	@Autowired
	private FestivitaRepository festivitaRepository;

	@Autowired
	private UtenteRepository utenteRepository;

	@Autowired
	private CommessaRepository commessaRepo;

	@Autowired
	private TimeSheetRepository mensileRepo;

	public TimeSheetResponseDto createTimeSheet(List<TimeSheetDataDto> list, TimeSheetInputDto timeDto) {
		TimeSheet timeSheet = DtoEntityMapper.INSTANCE.fromDtoToEntityMensile(timeDto);
		Utente utente = utenteRepository.findByCodicePersona(timeDto.getCodicePersona());
		timeSheet.setUtenteTimesheet(utente);
		utente.addTimeSheet(timeSheet);
		TimeSheetMensileKey id = new TimeSheetMensileKey(timeDto.getAnnoDiRiferimento(), timeDto.getMeseDiRiferimento(),
				timeDto.getCodicePersona());
		timeSheet.setId(id);
//		mensileRepo.save(timeSheet);
		List<TimeSheetDataDto> dataList = new ArrayList<TimeSheetDataDto>();
		Integer oreTot = 0;
		for (TimeSheetDataDto dataDto : list) {
			Commessa commessa = commessaRepo.findByCodiceCommessa(dataDto.getCodiceCommessa());
			TimeSheetData data = DtoEntityMapper.INSTANCE.fromDtoToEntityTimeSheet(dataDto);
			TimeSheetDataKey dataKey = new TimeSheetDataKey(dataDto.getAnnoDiRiferimento(),
					dataDto.getMeseDiRiferimento(), dataDto.getGiornoDiRiferimento(),
					dataDto.getCodicePersona(), dataDto.getCodiceCommessa());
			data.setId(dataKey);
			data.setCommessaTimesheet(commessa);
			data.setTimeSheet(timeSheet);
			timeSheet.addTimeSheet(data);
//			TimeSheetDataDto data = createTimeSheetData(dataDto);
//			oreTot = oreTot + data.getOre();
//			dataList.add(data);
		}
		
		timeSheet.setOreTotali(oreTot);
		mensileRepo.save(timeSheet);
		TimeSheetResponseDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoMensile(timeSheet);
		dto.setDataList(dataList);
		return dto;
	}

	public TimeSheetDataDto createTimeSheetData(TimeSheetDataDto timeSheetParam) {
		try {
//			TimeSheet timeSheet = DtoEntityMapper.INSTANCE.fromDtoToEntityMensile(timeDto);
			Commessa commessa = commessaRepo.findByCodiceCommessa(timeSheetParam.getCodiceCommessa());
			TimeSheetData data = DtoEntityMapper.INSTANCE.fromDtoToEntityTimeSheet(timeSheetParam);
			giornoDiRiferimento(timeSheetParam);
			data.setCommessaTimesheet(commessa);
			
			TimeSheetDataKey id = new TimeSheetDataKey(timeSheetParam.getAnnoDiRiferimento(),
					timeSheetParam.getMeseDiRiferimento(), timeSheetParam.getGiornoDiRiferimento(),
					timeSheetParam.getCodicePersona(), timeSheetParam.getCodiceCommessa());
			data.setId(id);
//			data.setTimeSheet(timeSheet);
//			timeSheet.addTimeSheet(data);
			timesheetRepository.save(data);
			logger.info("TimeSheet creato e aggiunto a database");
			TimeSheetDataDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoTimeSheet(data);
			return dto;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new TimeSheetException("Timesheet non creato " + ex.getMessage());

		}
	}

//
//	public TimeSheetDataDto editTimeSheet(TimeSheetDataDto timeSheetParam, Commessa commessa, Utente utente) {
//		try {
//			TimeSheetData timeSheetEntity=timesheetRepository.findByUtenteTimesheet(utente);
//			if(timeSheetEntity != null) {
//				timesheetRepository.delete(timeSheetEntity);
//				timeSheetEntity = DtoEntityMapper.INSTANCE.fromDtoToEntityTimeSheet(timeSheetParam);
//				timeSheetEntity.setUtenteTimesheet(utente);
//				timeSheetEntity.setCommessaTimesheet(commessa);
//				timesheetRepository.save(timeSheetEntity);
//				logger.info("Timesheet modificato");
//			}
//			TimeSheetDataDto dto = DtoEntityMapper.INSTANCE.fromEntityToDtoTimeSheet(timeSheetEntity);
//			return dto;
//		}catch(Exception ex) {
//			throw new EntityNotFoundException("Timesheet non trovato "+ ex.getMessage());	
//		}
//	}
//	
	public void editStatusTimeSheet(TimeSheetDataDto timeSheetParam) {
		// if(mapEditUser.containsKey(key)) {
		// TimeSheet
		// timeSheetEntity=timeSheetRepo.findByCodicePersona(timeSheetParam.getCodiceCommessa());
		// if(timeSheetEntity != null) {
		// timeSheetEntity.setStatoType(timeSheetParam.getStatoType().toString());
		// timeSheetRepo.save(timeSheetEntity);
		// } else {
		// LOGGER.info("CodicePersona non trovato");
		// }
		// } else {
		// LOGGER.info("CreateUser non trovato");
		// }
	}

	public void giornoDiRiferimento(TimeSheetDataDto timeSheetParam) {
		List<Festivita> festivi = festivitaRepository.findAll();
		LocalDate data = LocalDate.of(timeSheetParam.getAnnoDiRiferimento(), timeSheetParam.getMeseDiRiferimento(),
				timeSheetParam.getGiornoDiRiferimento());
		for (Festivita f : festivi) {
			if (f.getData().isEqual(data) || data.getDayOfWeek() == DayOfWeek.SUNDAY
					|| data.getDayOfWeek() == DayOfWeek.SATURDAY) {
				throw new FestivitaException("Il giorno inserito non è corretto");
			}
			logger.info("Il giorno inserito è corretto");
		}
	}
}