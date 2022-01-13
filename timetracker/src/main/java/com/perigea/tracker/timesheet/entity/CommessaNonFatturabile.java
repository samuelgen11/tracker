package com.perigea.tracker.timesheet.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "commessa_non_fatturabile")
@Data
@EqualsAndHashCode(callSuper = true)
public class CommessaNonFatturabile extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2256677862368637963L;

	@Id
	@Column(name = "codice_commessa", nullable = false)
	private String codiceCommessa;

	@MapsId
	@OneToOne
	@JoinColumn(name = "codice_commessa", nullable = false)
	private Commessa commessaNonFatturabile;

	@Column(name = "descrizione")
	private String descrizione;

	public Commessa getCommessaNoFatt() {
		return commessaNonFatturabile;
	}

	public void setCommessaNonFatturabile(Commessa commessaNonFatturabile) {
		this.commessaNonFatturabile = commessaNonFatturabile;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getCodiceCommessa() {
		return codiceCommessa;
	}

	public void setCodiceCommessa(String codiceCommessa) {
		this.codiceCommessa = codiceCommessa;
	}

	public Commessa getCommessaNonFatturabile() {
		return commessaNonFatturabile;
	}

}