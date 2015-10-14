package it.zeze.bo;

import java.math.BigDecimal;
import java.util.Comparator;

public class Statistiche implements Comparable<Statistiche> {

	private String squadra;
	private String nomeGiocatore;
	private String ruolo;

	private int goalFatto;
	private int goalSuRigore;
	private int goalSubito;
	private int rigoreParato;
	private int rigoreSbagliato;
	private int autorete;
	private int assist;
	private int ammonizioni;
	private int espulsioni;

	private BigDecimal voto;
	private BigDecimal votoFM;

	public boolean isStatisticheGiocatore(String squadra, String nomeG, String ruolo) {
		if (this.squadra.equalsIgnoreCase(squadra) && this.nomeGiocatore.equalsIgnoreCase(nomeG) && this.ruolo.equalsIgnoreCase(ruolo)) {
			return true;
		}
		return false;
	}

	public String getSquadra() {
		return squadra;
	}

	public void setSquadra(String squadra) {
		this.squadra = squadra;
	}

	public String getNomeGiocatore() {
		return nomeGiocatore;
	}

	public void setNomeGiocatore(String nomeGiocatore) {
		this.nomeGiocatore = nomeGiocatore;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public int getGoalFatto() {
		return goalFatto;
	}

	public void setGoalFatto(int goalFatto) {
		this.goalFatto = goalFatto;
	}

	public int getGoalSuRigore() {
		return goalSuRigore;
	}

	public void setGoalSuRigore(int goalSuRigore) {
		this.goalSuRigore = goalSuRigore;
	}

	public int getGoalSubito() {
		return goalSubito;
	}

	public void setGoalSubito(int goalSubito) {
		this.goalSubito = goalSubito;
	}

	public int getRigoreParato() {
		return rigoreParato;
	}

	public void setRigoreParato(int rigoreParato) {
		this.rigoreParato = rigoreParato;
	}

	public int getRigoreSbagliato() {
		return rigoreSbagliato;
	}

	public void setRigoreSbagliato(int rigoreSbagliato) {
		this.rigoreSbagliato = rigoreSbagliato;
	}

	public int getAutorete() {
		return autorete;
	}

	public void setAutorete(int autorete) {
		this.autorete = autorete;
	}

	public int getAssist() {
		return assist;
	}

	public void setAssist(int assist) {
		this.assist = assist;
	}

	public int getAmmonizioni() {
		return ammonizioni;
	}

	public void setAmmonizioni(int ammonizioni) {
		this.ammonizioni = ammonizioni;
	}

	public int getEspulsioni() {
		return espulsioni;
	}

	public void setEspulsioni(int espulsioni) {
		this.espulsioni = espulsioni;
	}

	public BigDecimal getVoto() {
		return voto;
	}

	public void setVoto(BigDecimal voto) {
		this.voto = voto;
	}

	public BigDecimal getVotoFM() {
		return votoFM;
	}

	public void setVotoFM(BigDecimal votoFM) {
		this.votoFM = votoFM;
	}

	public int compareTo(Statistiche o) {
		return this.votoFM.compareTo(o.getVotoFM());
	}

}

class ComparatorStatisticheByVotoFM implements Comparator<Statistiche> {
	public int compare(Statistiche o1, Statistiche o2) {
		return o1.getVotoFM().compareTo(o2.getVotoFM());
	}
}

class ComparatorStatisticheByVoto implements Comparator<Statistiche> {
	public int compare(Statistiche o1, Statistiche o2) {
		return o1.getVoto().compareTo(o2.getVoto());
	}
}
