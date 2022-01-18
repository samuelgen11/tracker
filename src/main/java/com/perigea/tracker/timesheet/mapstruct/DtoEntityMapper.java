package com.perigea.tracker.timesheet.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.perigea.tracker.timesheet.dto.AnagraficaClienteDto;
import com.perigea.tracker.timesheet.dto.AnagraficaDipendenteInputDto;
import com.perigea.tracker.timesheet.dto.AnagraficaDipendenteResponseDto;
import com.perigea.tracker.timesheet.dto.CommessaDto;
import com.perigea.tracker.timesheet.dto.CommessaFatturabileDto;
import com.perigea.tracker.timesheet.dto.CommessaNonFatturabileDto;
import com.perigea.tracker.timesheet.dto.DipendenteCommessaDto;
import com.perigea.tracker.timesheet.dto.FestivitaDto;
import com.perigea.tracker.timesheet.dto.OrdineCommessaDto;
import com.perigea.tracker.timesheet.dto.RuoloDto;
import com.perigea.tracker.timesheet.dto.TimeSheetDataDto;
import com.perigea.tracker.timesheet.dto.TimeSheetInputDto;
import com.perigea.tracker.timesheet.dto.TimeSheetResponseDto;
import com.perigea.tracker.timesheet.dto.UtentePostDto;
import com.perigea.tracker.timesheet.dto.UtenteViewDto;
import com.perigea.tracker.timesheet.entity.AnagraficaCliente;
import com.perigea.tracker.timesheet.entity.AnagraficaDipendente;
import com.perigea.tracker.timesheet.entity.Commessa;
import com.perigea.tracker.timesheet.entity.CommessaFatturabile;
import com.perigea.tracker.timesheet.entity.CommessaNonFatturabile;
import com.perigea.tracker.timesheet.entity.DipendenteCommessa;
import com.perigea.tracker.timesheet.entity.Festivita;
import com.perigea.tracker.timesheet.entity.OrdineCommessa;
import com.perigea.tracker.timesheet.entity.Ruolo;
import com.perigea.tracker.timesheet.entity.TimeSheetData;
import com.perigea.tracker.timesheet.entity.TimeSheet;
import com.perigea.tracker.timesheet.entity.Utente;









@Mapper
public interface DtoEntityMapper {
	
	DtoEntityMapper INSTANCE = Mappers.getMapper(DtoEntityMapper.class);

	Utente fromDtoToEntityUtente(UtentePostDto dto);

	UtentePostDto fromEntityToDtoUtente(Utente entity);
	
	UtenteViewDto fromEntityToUtenteViewDto(Utente entity);
	
	AnagraficaCliente fromDtoToEntityAnagraficaCliente(AnagraficaClienteDto dto);

	AnagraficaClienteDto fromEntityToDtoAnagraficaCliente(AnagraficaCliente entity);
	
	AnagraficaDipendente fromDtoToEntityAnagraficaDipendente(AnagraficaDipendenteInputDto dto);
	
	AnagraficaDipendenteInputDto fromEntityToDtoAnagraficaDipendente(AnagraficaDipendente entity);
	
	AnagraficaDipendenteResponseDto fromEntityToDtoAnagraficaDipendenteView(AnagraficaDipendente entity);
	
	Ruolo fromDtoToEntityRuoli(RuoloDto dto);
	
	RuoloDto fromEntityToDtoRuoli(Ruolo entity);
	
	Commessa fromDtoToEntityCommessa(CommessaDto dto);
	
	CommessaDto fromEntityToDtoCommessa(Commessa entity);
	
	CommessaFatturabile fromDtoToEntityCommessaFatturabile(CommessaFatturabileDto dto);
	
	CommessaFatturabileDto fromEntityToDtoCommessaFatturabile(CommessaFatturabile entity);
	
	CommessaNonFatturabile fromDtoToEntityCommessaNonFatturabile(CommessaNonFatturabileDto dto);
	
	CommessaNonFatturabileDto fromEntityToDtoCommessaNonFatturabile(CommessaNonFatturabile entity);
	
	OrdineCommessa fromDtoToEntityOrdineCommessa(OrdineCommessaDto dto);
	
	@Mapping(target = "codiceCommessa", source="id.codiceCommessa")
	@Mapping(target = "numeroOrdineCliente", source="id.numeroOrdineCliente")
	@Mapping(target = "ragioneSocialeCliente", source="id.ragioneSocialeCliente")
	OrdineCommessaDto fromEntityToDtoOrdineCommessa(OrdineCommessa entity);
	
	TimeSheetData fromDtoToEntityTimeSheet(TimeSheetDataDto dto);
	
	TimeSheetDataDto fromEntityToDtoTimeSheet(TimeSheetData entity);
	
	TimeSheet fromDtoToEntityMensile(TimeSheetInputDto dto);
	
	TimeSheetResponseDto fromEntityToDtoMensile(TimeSheet entity);
	
	DipendenteCommessa fromDtoToEntityRelazioneDipendenteCommessa(DipendenteCommessaDto dto);
	
	Festivita FromDtoToEntityFestivita(FestivitaDto dto);
	
	FestivitaDto FromEntityToDtoFestivita(Festivita entity);
}
