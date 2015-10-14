package it.zeze.entity;

// Generated 27-set-2011 15.46.52 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ProbabiliFormazioniGazzettaId generated by hbm2java
 */
@Embeddable
public class ProbabiliFormazioniGazzettaId implements java.io.Serializable {

	private int idGiocatore;
	private int idGiornata;
	private Boolean titolare;
	private Boolean panchina;

	public ProbabiliFormazioniGazzettaId() {
	}

	public ProbabiliFormazioniGazzettaId(int idGiocatore, int idGiornata) {
		this.idGiocatore = idGiocatore;
		this.idGiornata = idGiornata;
	}

	public ProbabiliFormazioniGazzettaId(int idGiocatore, int idGiornata, Boolean titolare, Boolean panchina) {
		this.idGiocatore = idGiocatore;
		this.idGiornata = idGiornata;
		this.titolare = titolare;
		this.panchina = panchina;
	}

	@Column(name = "id_giocatore", nullable = false)
	public int getIdGiocatore() {
		return this.idGiocatore;
	}

	public void setIdGiocatore(int idGiocatore) {
		this.idGiocatore = idGiocatore;
	}

	@Column(name = "id_giornata", nullable = false)
	public int getIdGiornata() {
		return this.idGiornata;
	}

	public void setIdGiornata(int idGiornata) {
		this.idGiornata = idGiornata;
	}

	@Column(name = "titolare")
	public Boolean getTitolare() {
		return this.titolare;
	}

	public void setTitolare(Boolean titolare) {
		this.titolare = titolare;
	}

	@Column(name = "panchina")
	public Boolean getPanchina() {
		return this.panchina;
	}

	public void setPanchina(Boolean panchina) {
		this.panchina = panchina;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ProbabiliFormazioniGazzettaId))
			return false;
		ProbabiliFormazioniGazzettaId castOther = (ProbabiliFormazioniGazzettaId) other;

		return (this.getIdGiocatore() == castOther.getIdGiocatore()) && (this.getIdGiornata() == castOther.getIdGiornata()) && ((this.getTitolare() == castOther.getTitolare()) || (this.getTitolare() != null && castOther.getTitolare() != null && this.getTitolare().equals(castOther.getTitolare()))) && ((this.getPanchina() == castOther.getPanchina()) || (this.getPanchina() != null && castOther.getPanchina() != null && this.getPanchina().equals(castOther.getPanchina())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getIdGiocatore();
		result = 37 * result + this.getIdGiornata();
		result = 37 * result + (getTitolare() == null ? 0 : this.getTitolare().hashCode());
		result = 37 * result + (getPanchina() == null ? 0 : this.getPanchina().hashCode());
		return result;
	}

}
