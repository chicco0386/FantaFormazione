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
 * Calendario generated by hbm2java
 */
@Entity
@Table(name = "calendario", catalog = "fanta_formazione")
public class Calendario implements java.io.Serializable {

	private CalendarioId id;
	private Squadre squadreByIdSquadraFuoriCasa;
	private Giornate giornate;
	private Squadre squadreByIdSquadraCasa;

	public Calendario() {
	}

	public Calendario(CalendarioId id, Squadre squadreByIdSquadraFuoriCasa, Giornate giornate, Squadre squadreByIdSquadraCasa) {
		this.id = id;
		this.squadreByIdSquadraFuoriCasa = squadreByIdSquadraFuoriCasa;
		this.giornate = giornate;
		this.squadreByIdSquadraCasa = squadreByIdSquadraCasa;
	}

	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "idGiornata", column = @Column(name = "id_giornata", nullable = false)), @AttributeOverride(name = "idSquadraCasa", column = @Column(name = "id_squadra_casa", nullable = false)), @AttributeOverride(name = "idSquadraFuoriCasa", column = @Column(name = "id_squadra_fuori_casa", nullable = false)) })
	@NotNull
	public CalendarioId getId() {
		return this.id;
	}

	public void setId(CalendarioId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_squadra_fuori_casa", nullable = false, insertable = false, updatable = false)
	@NotNull
	public Squadre getSquadreByIdSquadraFuoriCasa() {
		return this.squadreByIdSquadraFuoriCasa;
	}

	public void setSquadreByIdSquadraFuoriCasa(Squadre squadreByIdSquadraFuoriCasa) {
		this.squadreByIdSquadraFuoriCasa = squadreByIdSquadraFuoriCasa;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_squadra_casa", nullable = false, insertable = false, updatable = false)
	@NotNull
	public Squadre getSquadreByIdSquadraCasa() {
		return this.squadreByIdSquadraCasa;
	}

	public void setSquadreByIdSquadraCasa(Squadre squadreByIdSquadraCasa) {
		this.squadreByIdSquadraCasa = squadreByIdSquadraCasa;
	}

}
