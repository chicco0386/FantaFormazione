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
 * Statistiche generated by hbm2java
 */
@Entity
@Table(name = "statistiche", catalog = "fanta_formazione")
public class Statistiche implements java.io.Serializable {

	private StatisticheId id;
	private Giocatori giocatori;
	private Giornate giornate;

	public Statistiche() {
	}

	public Statistiche(StatisticheId id, Giocatori giocatori, Giornate giornate) {
		this.id = id;
		this.giocatori = giocatori;
		this.giornate = giornate;
	}

	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "idGiocatore", column = @Column(name = "id_giocatore", nullable = false)), @AttributeOverride(name = "idGiornata", column = @Column(name = "id_giornata", nullable = false)), @AttributeOverride(name = "mediaVoto", column = @Column(name = "media_voto", nullable = false, precision = 2, scale = 0)), @AttributeOverride(name = "mediaVotoFm", column = @Column(name = "media_voto_fm", nullable = false, precision = 2, scale = 0)), @AttributeOverride(name = "goalFatti", column = @Column(name = "goal_fatti", nullable = false)), @AttributeOverride(name = "goalRigore", column = @Column(name = "goal_rigore", nullable = false)), @AttributeOverride(name = "goalSubiti", column = @Column(name = "goal_subiti", nullable = false)), @AttributeOverride(name = "rigoriParati", column = @Column(name = "rigori_parati", nullable = false)), @AttributeOverride(name = "rigoriSbagliati", column = @Column(name = "rigori_sbagliati", nullable = false)), @AttributeOverride(name = "autoreti", column = @Column(name = "autoreti", nullable = false)), @AttributeOverride(name = "assist", column = @Column(name = "assist", nullable = false)), @AttributeOverride(name = "ammonizioni", column = @Column(name = "ammonizioni", nullable = false)), @AttributeOverride(name = "espulsioni", column = @Column(name = "espulsioni", nullable = false)) })
	@NotNull
	public StatisticheId getId() {
		return this.id;
	}

	public void setId(StatisticheId id) {
		this.id = id;
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
