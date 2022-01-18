
package com.perigea.tracker.timesheet.entity;


import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.perigea.tracker.timesheet.entity.keys.TimeSheetDataKey;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "time_sheet_data")
@EqualsAndHashCode(callSuper=true)
public class TimeSheetData extends BaseEntity {

	private static final long serialVersionUID = -3241359472237290256L;

	@EmbeddedId
	private TimeSheetDataKey id;
	
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "codice_persona", referencedColumnName = "codice_persona", insertable = false, updatable = false),
		@JoinColumn(name = "anno_di_riferimento", referencedColumnName = "anno_di_riferimento", insertable = false, updatable = false),
		@JoinColumn(name = "mese_di_riferimento",referencedColumnName = "mese_di_riferimento", insertable = false, updatable = false)
	})
	private TimeSheet timeSheet;
	
	@ManyToOne
	@JoinColumn(name = "codice_commessa", referencedColumnName = "codice_commessa", nullable = false, insertable=false, updatable=false)
	private Commessa commessaTimesheet;

	@Column(name = "ore")
	private Integer ore;

	@Column(name = "trasferta")
	private Boolean trasferta;

		
}