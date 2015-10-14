package it.zeze.entity;

// Generated 27-set-2011 15.46.52 by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.validator.NotNull;

/**
 * Formazioni generated by hbm2java
 */
@Entity
@Table(name = "formazioni", catalog = "fanta_formazione")
public class Formazioni implements java.io.Serializable {

	private FormazioniId id;
	private UtentiFormazioni utentiFormazioni;
	private Giocatori giocatori;
	private Giornate giornate;

	public Formazioni() {
	}

	public Formazioni(FormazioniId id, UtentiFormazioni utentiFormazioni, Giocatori giocatori, Giornate giornate) {
		this.id = id;
		this.utentiFormazioni = utentiFormazioni;
		this.giocatori = giocatori;
		this.giornate = giornate;
	}

	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "idGiocatore", column = @Column(name = "id_giocatore", nullable = false)), @AttributeOverride(name = "idGiornata", column = @Column(name = "id_giornata", nullable = false)), @AttributeOverride(name = "idUtentiFormazioni", column = @Column(name = "id_utenti_formazioni", nullable = false)), @AttributeOverride(name = "probTitolare", column = @Column(name = "prob_titolare", nullable = false)), @AttributeOverride(name = "probPanchina", column = @Column(name = "prob_panchina", nullable = false)), @AttributeOverride(name = "note", column = @Column(name = "note", length = 100)) })
	@NotNull
	public FormazioniId getId() {
		return this.id;
	}

	public void setId(FormazioniId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_utenti_formazioni", nullable = false, insertable = false, updatable = false)
	@NotNull
	public UtentiFormazioni getUtentiFormazioni() {
		return this.utentiFormazioni;
	}

	public void setUtentiFormazioni(UtentiFormazioni utentiFormazioni) {
		this.utentiFormazioni = utentiFormazioni;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_giocatore", nullable = false, insertable = false, updatable = false)
	@NotNull
	public Giocatori getGiocatori() {
		return this.giocatori;
	}

	public void setGiocatori(Giocatori giocatori) {
		this.giocatori = giocatori;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_giornata", nullable = false, insertable = false, updatable = false)
	@NotNull
	public Giornate getGiornate() {
		return this.giornate;
	}

	public void setGiornate(Giornate giornate) {
		this.giornate = giornate;
	}

}
