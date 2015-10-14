package it.zeze.entity;

// Generated 27-set-2011 15.46.52 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.validator.Length;

/**
 * Squadre generated by hbm2java
 */
@Entity
@Table(name = "squadre", catalog = "fanta_formazione")
public class Squadre implements java.io.Serializable {

	private Integer id;
	private String nome;
	private Set<Giocatori> giocatoris = new HashSet<Giocatori>(0);
	private Set<Calendario> calendariosForIdSquadraCasa = new HashSet<Calendario>(0);
	private Set<Calendario> calendariosForIdSquadraFuoriCasa = new HashSet<Calendario>(0);

	public Squadre() {
	}

	public Squadre(String nome, Set<Giocatori> giocatoris, Set<Calendario> calendariosForIdSquadraCasa, Set<Calendario> calendariosForIdSquadraFuoriCasa) {
		this.nome = nome;
		this.giocatoris = giocatoris;
		this.calendariosForIdSquadraCasa = calendariosForIdSquadraCasa;
		this.calendariosForIdSquadraFuoriCasa = calendariosForIdSquadraFuoriCasa;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "nome", length = 45)
	@Length(max = 45)
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "squadre")
	public Set<Giocatori> getGiocatoris() {
		return this.giocatoris;
	}

	public void setGiocatoris(Set<Giocatori> giocatoris) {
		this.giocatoris = giocatoris;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "squadreByIdSquadraCasa")
	public Set<Calendario> getCalendariosForIdSquadraCasa() {
		return this.calendariosForIdSquadraCasa;
	}

	public void setCalendariosForIdSquadraCasa(Set<Calendario> calendariosForIdSquadraCasa) {
		this.calendariosForIdSquadraCasa = calendariosForIdSquadraCasa;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "squadreByIdSquadraFuoriCasa")
	public Set<Calendario> getCalendariosForIdSquadraFuoriCasa() {
		return this.calendariosForIdSquadraFuoriCasa;
	}

	public void setCalendariosForIdSquadraFuoriCasa(Set<Calendario> calendariosForIdSquadraFuoriCasa) {
		this.calendariosForIdSquadraFuoriCasa = calendariosForIdSquadraFuoriCasa;
	}

}
