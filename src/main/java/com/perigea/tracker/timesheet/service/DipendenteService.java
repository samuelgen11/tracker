package com.perigea.tracker.timesheet.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perigea.tracker.timesheet.dto.AnagraficaDipendenteInputDto;
import com.perigea.tracker.timesheet.dto.AnagraficaDipendenteResponseDto;
import com.perigea.tracker.timesheet.dto.RuoloDto;
import com.perigea.tracker.timesheet.dto.UtentePostDto;
import com.perigea.tracker.timesheet.dto.UtenteViewDto;
import com.perigea.tracker.timesheet.entity.AnagraficaDipendente;
import com.perigea.tracker.timesheet.entity.Utente;
import com.perigea.tracker.timesheet.exception.EntityNotFoundException;
import com.perigea.tracker.timesheet.exception.UtenteException;
import com.perigea.tracker.timesheet.mapstruct.DtoEntityMapper;
import com.perigea.tracker.timesheet.repository.AnagraficaDipendenteRepository;
import com.perigea.tracker.timesheet.repository.UtenteRepository;
import com.perigea.tracker.timesheet.utility.TSUtils;


@Service
public class DipendenteService {

	@Autowired
	private Logger logger;

	@Autowired
	private AnagraficaDipendenteRepository dipendenteRepository;

	@Autowired
	private UtenteRepository utenteRepository;

	// metodo per creare un dipendente
	public AnagraficaDipendenteResponseDto createDipendente(AnagraficaDipendenteInputDto dipendenteDto, String codiceResponsabile) {
		try {
			Utente utente = DtoEntityMapper.INSTANCE.fromDtoToEntityUtente(dipendenteDto.getUtenteDto());
			utente.setCodicePersona(TSUtils.uuid());
			Utente responsabile = utenteRepository.findByCodicePersona(codiceResponsabile);
			utente.setResponsabile(responsabile);
			if (responsabile != null) {
				responsabile.addDipendente(utente);
			}
			AnagraficaDipendente dipendente = DtoEntityMapper.INSTANCE.fromDtoToEntityAnagraficaDipendente(dipendenteDto);
			dipendente.setUtenteDipendente(utente);
			utente.setDipendente(dipendente);
			utenteRepository.save(utente);
			UtenteViewDto responsabileDto = DtoEntityMapper.INSTANCE.fromEntityToUtenteViewDto(responsabile);
			UtenteViewDto utenteResponseDto = DtoEntityMapper.INSTANCE.fromEntityToUtenteViewDto(utente);
			utenteResponseDto.setResponsabileDto(responsabileDto);
			AnagraficaDipendenteResponseDto anagraficaResponseDto = DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaDipendenteView(dipendente);
			anagraficaResponseDto.setUtenteDto(utenteResponseDto);
			logger.info("done");
			return anagraficaResponseDto;
		} catch (Exception ex) {
			throw new UtenteException(ex.getMessage());
		}
	}

	// Metodo per leggere i dati di un determinato dipendente
	public AnagraficaDipendenteResponseDto readDipendente(String dipendenteParam) {
		try {
			AnagraficaDipendente anagDipendente = dipendenteRepository.findByCodicePersona(dipendenteParam);
			UtenteViewDto utenteResponseDto = DtoEntityMapper.INSTANCE.fromEntityToUtenteViewDto(anagDipendente.getUtenteDipendente());
			AnagraficaDipendenteResponseDto anagraficaResponseDto = DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaDipendenteView(anagDipendente);
			anagraficaResponseDto.setUtenteDto(utenteResponseDto);
			return DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaDipendenteView(anagDipendente);
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}

	// Metodo per aggiornare i dati di un dipendente
	public AnagraficaDipendenteResponseDto updateDipendente(AnagraficaDipendenteInputDto dipendenteParam) {
		try {
			AnagraficaDipendente anagDipendente = dipendenteRepository.findByCodicePersona(dipendenteParam.getUtenteDto().getCodicePersona());
			if (anagDipendente != null) {
				anagDipendente = DtoEntityMapper.INSTANCE.fromDtoToEntityAnagraficaDipendente(dipendenteParam);
				dipendenteRepository.save(anagDipendente);
			}
			
			UtenteViewDto utenteResponseDto = DtoEntityMapper.INSTANCE.fromEntityToUtenteViewDto(anagDipendente.getUtenteDipendente());
			AnagraficaDipendenteResponseDto anagraficaResponseDto = DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaDipendenteView(anagDipendente);
			anagraficaResponseDto.setUtenteDto(utenteResponseDto);
			return anagraficaResponseDto;
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}

	// Metodo per eliminare un dipendente da database
	public AnagraficaDipendenteResponseDto deleteDipendente(String id) {
		try {
			AnagraficaDipendente anagDipendente = dipendenteRepository.findByCodicePersona(id);
			if (anagDipendente != null) {
				utenteRepository.delete(anagDipendente.getUtenteDipendente());
				dipendenteRepository.delete(anagDipendente);
			}
			AnagraficaDipendenteResponseDto anagraficaResponseDto = DtoEntityMapper.INSTANCE.fromEntityToDtoAnagraficaDipendenteView(anagDipendente);
			UtenteViewDto utenteResponseDto = DtoEntityMapper.INSTANCE.fromEntityToUtenteViewDto(anagDipendente.getUtenteDipendente());
			anagraficaResponseDto.setUtenteDto(utenteResponseDto);
			return anagraficaResponseDto;
		} catch (Exception ex) {
			throw new EntityNotFoundException(ex.getMessage());
		}
	}
	
	// Metodo per aggiornare lo stato (attivo/cessato) di un utente
		public UtenteViewDto editStatusUser(UtentePostDto utenteDto) {
			try {
				Utente entity = utenteRepository.findByCodicePersona(utenteDto.getCodicePersona());
				if (entity != null) {
					entity.setStatoUtenteType(utenteDto.getStatoUtenteType());
					entity.setLastUpdateUser("");
					utenteRepository.save(entity);
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
		
//		public Utente createUtente(UtentePostDto utenteDto, String codiceResponsabile) {
//			try {
//				Utente utente = DtoEntityMapper.INSTANCE.fromDtoToEntityUtente(utenteDto);
//				utente.setCodicePersona(TSUtils.uuid());
//				Utente responsabile = utenteRepository.findByCodicePersona(codiceResponsabile);
//				utente.setResponsabile(responsabile);
//				if (responsabile != null) {
//					responsabile.addDipendente(utente);
//				}
//				logger.info("Utente creato");
//				utenteRepository.save(utente);
//				logger.info("Utente salvato a database");
//				UtenteViewDto responsabileDto = DtoEntityMapper.INSTANCE.fromEntityToUtenteViewDto(responsabile);
//				UtenteViewDto dto = DtoEntityMapper.INSTANCE.fromEntityToUtenteViewDto(utente);
//				dto.setResponsabileDto(responsabileDto);
//				return utente;
//			} catch (Exception ex) {
//				throw new UtenteException(ex.getMessage());
//			}
//		}
		
		public void editRoleUser(RuoloDto roleParam, UtentePostDto userParam) {
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

}