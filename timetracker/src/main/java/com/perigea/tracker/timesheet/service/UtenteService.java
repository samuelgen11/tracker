package com.perigea.tracker.timesheet.service;

import java.util.Date;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perigea.tracker.timesheet.dto.RelazioneDipendenteCommessaDto;
import com.perigea.tracker.timesheet.dto.RuoliDto;
import com.perigea.tracker.timesheet.dto.TimeSheetDto;
import com.perigea.tracker.timesheet.dto.UtentePostDto;
import com.perigea.tracker.timesheet.dto.UtenteViewDto;
import com.perigea.tracker.timesheet.entity.RelazioneDipendenteCommessa;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.exception.UtenteException;
import com.perigea.tracker.timesheet.mapstruct.DtoEntityMapper;
import com.perigea.tracker.timesheet.repository.RelazioneDipendenteCommessaRepository;
import com.perigea.tracker.timesheet.repository.UtenteRepository;
import com.perigea.tracker.timesheet.utility.TSUtils;



@Service
public class UtenteService {

	@Autowired
	private Logger logger;

	@Autowired
	private UtenteRepository utenteRepo;

	@Autowired
	private RelazioneDipendenteCommessaRepository relazioneDipComRepo;

	// Metodo per creare un nuovo utente per poi inserirlo a database
	public UtenteViewDto createUtente(UtentePostDto utenteDto, String codiceResponsabile) {
		try {
			Utente utente = DtoEntityMapper.INSTANCE.fromDtoToEntityUtente(utenteDto);
			utente.setCodicePersona(TSUtils.UUDI());
			Utente responsabile = utenteRepo.findByCodicePersona(codiceResponsabile);
			utente.setResponsabile(responsabile);
			responsabile.addDipendente(utente);
			logger.info("Utente creato");
			utenteRepo.save(utente);
			logger.info("Utente salvato a database");
			UtenteViewDto responsabileDto = DtoEntityMapper.INSTANCE.fromEntityToUtenteViewDto(responsabile);
			UtenteViewDto dto = DtoEntityMapper.INSTANCE.fromEntityToUtenteViewDto(utente);
			dto.setResponsabileDto(responsabileDto);
			return dto;
		} catch (Exception ex) {
			throw new UtenteException(ex.getMessage());
		}
	}

	// Metodo per leggere i dati di un determinato utente
	public UtenteViewDto readUtente(String codicePersona) {
		try {
			Utente entity = utenteRepo.findByCodicePersona(codicePersona);
			Utente responsabile = entity.getResponsabile();
			UtenteViewDto respDto = DtoEntityMapper.INSTANCE.fromEntityToUtenteViewDto(responsabile);
			UtenteViewDto dto = DtoEntityMapper.INSTANCE.fromEntityToUtenteViewDto(entity);
			dto.setResponsabileDto(respDto);
			return dto;
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}

	// Metodo per aggiornare i dati di un utente
	public UtenteViewDto updateUtente(UtentePostDto utenteDto, String codiceResponsabile) {
		try {
			Utente entity = utenteRepo.findByCodicePersona(utenteDto.getCodicePersona());
			Utente responsabile = utenteRepo.findByCodicePersona(codiceResponsabile);
			if (entity != null) {
				entity = DtoEntityMapper.INSTANCE.fromDtoToEntityUtente(utenteDto);
				entity.setResponsabile(responsabile);
				responsabile.addDipendente(entity);
				utenteRepo.save(entity);
			}
			UtenteViewDto responsabileDto = DtoEntityMapper.INSTANCE.fromEntityToUtenteViewDto(responsabile);
			UtenteViewDto dto = DtoEntityMapper.INSTANCE.fromEntityToUtenteViewDto(entity);
			dto.setResponsabileDto(responsabileDto);
			return dto;
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}

	// Metodo per eliminare un utente da database
	public UtenteViewDto deleteUtente(String codicePersona) {
		try {
			Utente entity = utenteRepo.findByCodicePersona(codicePersona);
			Utente responsabile = entity.getResponsabile();
			UtenteViewDto respDto = DtoEntityMapper.INSTANCE.fromEntityToUtenteViewDto(responsabile);
			UtenteViewDto dto = DtoEntityMapper.INSTANCE.fromEntityToUtenteViewDto(entity);
			dto.setResponsabileDto(respDto);
			if (entity != null) {
				utenteRepo.delete(entity);
			}
			return dto;
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}

	// Metodo per aggiornare lo stato (attivo/cessato) di un utente
	public UtenteViewDto editStatusUser(UtentePostDto utenteDto) {
		try {
			Utente entity = utenteRepo.findByCodicePersona(utenteDto.getCodicePersona());
			if (entity != null) {
				entity.setStatoUtenteType(utenteDto.getStatoUtenteType());
				entity.setLastUpdateUser("");
				utenteRepo.save(entity);
			}
			Utente responsabile = entity.getResponsabile();
			UtenteViewDto respDto = DtoEntityMapper.INSTANCE.fromEntityToUtenteViewDto(responsabile);
			UtenteViewDto dto = DtoEntityMapper.INSTANCE.fromEntityToUtenteViewDto(entity);
			dto.setResponsabileDto(respDto);
			return dto;
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}

	public void editRoleUser(RuoliDto roleParam, UtentePostDto userParam) {
		// if(mapEditUser.containsKey(key)) {
		// List<UtenteEntity>
		// entity=userRepo.findByRuoloType(roleParam.getRuoloType().toString());
		// for(UtenteEntity u: entity) {
		// if(u.getCodicePersona().equalsIgnoreCase(userParam.getCodicePersona())) {
		// u.setStatoUtenteType(userParam.getStatoUtenteType().toString());
		// }
		// }
		// } else {
		// LOGGER.info("CreateUser non trovato");
		// }
	}

	// -------------------------------------------------------------------------------------------
	public void editStatusTimeSheet(TimeSheetDto timeSheetParam) {
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

	public void createRelazioneDipendenteCommessa(RelazioneDipendenteCommessaDto dtoParam) {
		try {
			RelazioneDipendenteCommessa entity = DtoEntityMapper.INSTANCE
					.fromDtoToEntityRelazioneDipendenteCommessa(dtoParam);
			Date date = new Date();
			entity.setDataInizioAllocazione(dtoParam.getDataInizioAllocazione());
			entity.setDataFineAllocazione(dtoParam.getDataFineAllocazione());
			entity.setGiorniPrevisti(dtoParam.getGiorniPrevisti());
			entity.setGiorniErogati(dtoParam.getGiorniErogati());
			entity.setGiorniResidui(dtoParam.getGiorniResidui());
			entity.setImportoPrevisto(dtoParam.getImportoPrevisto());
			entity.setImportoErogato(dtoParam.getImportoErogato());
			entity.setImportoResiduo(dtoParam.getImportoResiduo());
			entity.setTariffa(dtoParam.getTariffa());
			entity.setCreateTimestamp(date);
			entity.setLastUpdateTimestamp(date);
			entity.setCreateUser("");
			entity.setLastUpdateUser("");
			relazioneDipComRepo.save(entity);
			logger.info("Relazione Dipendente-commessa creata e salvata a database");
		} catch (Exception ex) {
			throw new UtenteException(ex.getMessage());
		}
	}

}
