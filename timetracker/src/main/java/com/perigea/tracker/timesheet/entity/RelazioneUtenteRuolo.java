package com.perigea.tracker.timesheet.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.perigea.tracker.timesheet.entity.keys.RelazioneIdUtenteRuoloKey;

import lombok.Data;

@Entity
@Table(name = "utente_ruolo")
@Data
public class RelazioneUtenteRuolo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1739383358933071697L;

	@EmbeddedId
	private RelazioneIdUtenteRuoloKey id;

	@ManyToOne
	@JoinColumn(name = "codice_persona", referencedColumnName = "codice_persona", nullable = false, insertable = false, updatable = false)
	private Utente utente;

	@ManyToOne
	@JoinColumn(name = "ruolo", referencedColumnName = "ruolo", nullable = false, insertable = false, updatable = false)
	private Ruoli ruolo;

}
