package com.perigea.tracker.timesheet.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.perigea.tracker.timesheet.enums.RuoloType;

import lombok.Data;

@Data
@Entity
@Table(name = "ruoli")
public class Ruolo implements Serializable {

	private static final long serialVersionUID = -870008112526011657L;

	@Id
	@Column(name = "ruolo")
	@Enumerated(EnumType.STRING)
	private RuoloType tipo;

	@Column(name = "descrizione_ruolo")
	private String descrizione;

	@OneToMany(mappedBy = "ruolo")
	private List<UtenteRuoli> ruoli = new ArrayList<>();

}