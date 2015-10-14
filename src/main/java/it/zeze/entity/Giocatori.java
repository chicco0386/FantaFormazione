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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

/**
 * Giocatori generated by hbm2java
 */
@Entity
@Table(name = "giocatori", catalog = "fanta_formazione", uniqueConstraints = @UniqueConstraint(columnNames = { "id", "nome" }))
public class Giocatori implements java.io.Serializable {

	private Integer id;
	private Squadre squadre;
	private String nome;
	private String ruolo;
	private Set<ProbabiliFormazioniFg> probabiliFormazioniFgs = new HashSet<ProbabiliFormazioniFg>(0);
	private Set<ProbabiliFormazioniGazzetta> probabiliFormazioniGazzettas = new HashSet<ProbabiliFormazioniGazzetta>(0);
	private Set<Formazioni> formazionis = new HashSet<Formazioni>(0);
	private Set<Statistiche> statistiches = new HashSet<Statistiche>(0);

	public Giocatori() {
	}

	public Giocatori(Squadre squadre, String nome, String ruolo) {
		this.squadre = squadre;
		this.nome = nome;
		this.ruolo = ruolo;
	}

	public Giocatori(Squadre squadre, String nome, String ruolo, Set<ProbabiliFormazioniFg> probabiliFormazioniFgs, Set<ProbabiliFormazioniGazzetta> probabiliFormazioniGazzettas, Set<Formazioni> formazionis, Set<Statistiche> statistiches) {
		this.squadre = squadre;
		this.nome = nome;
		this.ruolo = ruolo;
		this.probabiliFormazioniFgs = probabiliFormazioniFgs;
		this.probabiliFormazioniGazzettas = probabiliFormazioniGazzettas;
		this.formazionis = formazionis;
		this.statistiches = statistiches;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_squadra", nullable = false)
	@NotNull
	public Squadre getSquadre() {
		return this.squadre;
	}

	public void setSquadre(Squadre squadre) {
		this.squadre = squadre;
	}

	@Column(name = "nome", nullable = false, length = 45)
	@NotNull
	@Length(max = 45)
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "ruolo", nullable = false, length = 1)
	@NotNull
	@Length(max = 1)
	public String getRuolo() {
		return this.ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "giocatori")
	public Set<ProbabiliFormazioniFg> getProbabiliFormazioniFgs() {
		return this.probabiliFormazioniFgs;
	}

	public void setProbabiliFormazioniFgs(Set<ProbabiliFormazioniFg> probabiliFormazioniFgs) {
		this.probabiliFormazioniFgs = probabiliFormazioniFgs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "giocatori")
	public Set<ProbabiliFormazioniGazzetta> getProbabiliFormazioniGazzettas() {
		return this.probabiliFormazioniGazzettas;
	}

	public void setProbabiliFormazioniGazzettas(Set<ProbabiliFormazioniGazzetta> probabiliFormazioniGazzettas) {
		this.probabiliFormazioniGazzettas = probabiliFormazioniGazzettas;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "giocatori")
	public Set<Formazioni> getFormazionis() {
		return this.formazionis;
	}

	public void setFormazionis(Set<Formazioni> formazionis) {
		this.formazionis = formazionis;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "giocatori")
	public Set<Statistiche> getStatistiches() {
		return this.statistiches;
	}

	public void setStatistiches(Set<Statistiche> statistiches) {
		this.statistiches = statistiches;
	}

}
