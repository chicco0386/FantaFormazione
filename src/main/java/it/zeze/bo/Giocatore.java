package it.zeze.bo;

import it.zeze.html.cleaner.HtmlCleanerUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

public class Giocatore implements Comparable<Giocatore> {
	
	private static final Map<String, String> MAP_CARATTERI_SPECIALI = new HashMap<String, String>();
	
	static {
		MAP_CARATTERI_SPECIALI.put("�", "o'");
		MAP_CARATTERI_SPECIALI.put("�", "a'");
		MAP_CARATTERI_SPECIALI.put("�", "e'");
		MAP_CARATTERI_SPECIALI.put("�", "e'");
		MAP_CARATTERI_SPECIALI.put("�", "u'");
	}

	private String nome = "";
	private String squadra = "";
	private String ruolo = "";
	private boolean in = false;
	private boolean inSub = false;
	private int probabilitaImpiegoTitolare = 0;
	private int probabilitaImpiegoPanchina = 0;
	private String note = "";
	private String avversario="";
	private Statistiche statisticheGiocatore;
	
	public Giocatore(){}
	
	public Giocatore(Giocatore toCopy){
		this.nome = toCopy.getNome();
		this.squadra  =toCopy.getSquadra();
		this.ruolo = toCopy.getRuolo();
		this.in = toCopy.isIn();
		this.inSub = toCopy.isInSub();
		this.probabilitaImpiegoTitolare = toCopy.getProbabilitaImpiegoTitolare();
		this.probabilitaImpiegoPanchina = toCopy.getProbabilitaImpiegoPanchina();
		this.note = toCopy.getNote();
	}

	public void unmarshallFromElementFantaGazzetta(TagNode nameNode) throws IOException, XPatherException {
		TagNode playerName = nameNode.findElementByName("a", false);
		nome = playerName.getText().toString();

		TagNode playerRole = nameNode.findElementByName("span", false);
		ruolo = playerRole.getText().toString();
	}

	/*
	 * Ricevo il tag <span class="col2">Morimoto</span>
	 */
	public void unmarshallFromElementGazzetta(TagNode giocatoreNode) throws IOException, XPatherException {
		nome = giocatoreNode.getText().toString();

		if (!HtmlCleanerUtil.nodeContainsAttribute(giocatoreNode.getParent(), "class", "bottom")) {
			in = true;
			probabilitaImpiegoTitolare = 1;
		} else {
			inSub = true;
			probabilitaImpiegoPanchina = 1;
		}
	}

	public void unmarshallFromElementSky(TagNode giocatoreNode) throws IOException, XPatherException {
		TagNode playerName = giocatoreNode.findElementByName("span", false);
		nome = playerName.getText().toString();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setSquadra(String squadra) {
		this.squadra = squadra;
	}

	public String getSquadra() {
		return squadra;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public void setIn(boolean in) {
		this.in = in;
	}

	public boolean isIn() {
		return in;
	}

	public void setInSub(boolean inSub) {
		this.inSub = inSub;
	}

	public boolean isInSub() {
		return inSub;
	}

	public int getProbabilitaImpiegoTitolare() {
		return probabilitaImpiegoTitolare;
	}

	public void setProbabilitaImpiegoTitolare(int probabilitaImpiego) {
		this.probabilitaImpiegoTitolare = probabilitaImpiego;
	}

	public void setProbabilitaImpiegoPanchina(int probabilitaImpiegoPanchina) {
		this.probabilitaImpiegoPanchina = probabilitaImpiegoPanchina;
	}

	public int getProbabilitaImpiegoPanchina() {
		return probabilitaImpiegoPanchina;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNote() {
		return note;
	}

	public String getAvversario() {
		return avversario;
	}

	public void setAvversario(String avversario) {
		this.avversario = avversario;
	}

	public Statistiche getStatisticheGiocatore() {
		return statisticheGiocatore;
	}

	public void setStatisticheGiocatore(Statistiche statisticheGiocatore) {
		this.statisticheGiocatore = statisticheGiocatore;
	}

	public boolean equalsNomeSquadra(Object toCompare) {
		boolean equals = false;
		if (toCompare != null && toCompare instanceof Giocatore) {
			Giocatore giocatoreToCompare = (Giocatore) toCompare;
			boolean containsNome = false;
			if (giocatoreToCompare.nome.length() < this.nome.length()) {
				containsNome = StringUtils.containsIgnoreCase(this.nome, giocatoreToCompare.nome);
			} else {
				containsNome = StringUtils.containsIgnoreCase(giocatoreToCompare.nome, this.nome);
			}
			boolean containsSquadra = false;
			if (giocatoreToCompare.squadra.length() < this.squadra.length()) {
				containsSquadra = StringUtils.containsIgnoreCase(this.squadra, giocatoreToCompare.squadra);
			} else {
				containsSquadra = StringUtils.containsIgnoreCase(giocatoreToCompare.squadra, this.squadra);
			}
			if ((giocatoreToCompare.getNome().equalsIgnoreCase(this.getNome()) || containsNome) && (giocatoreToCompare.getSquadra().equalsIgnoreCase(this.getSquadra()) || containsSquadra)) {
				equals = true;
			}
		}
		return equals;
	}

	@Override
	public boolean equals(Object toCompare) {
		boolean equals = false;
		if (toCompare != null && toCompare instanceof Giocatore) {
			boolean contains = this.equalsNomeSquadra(toCompare);
			if (contains) {
				equals = true;
			}
		}
		return equals;
	}

	@Override
	public String toString() {
		return nome + " " + squadra + " " + ruolo + " " + " " + in + " " + inSub + " " + probabilitaImpiegoTitolare + " " + note;
	}

	public int compareTo(Giocatore o) {
		int compare = this.ruolo.compareToIgnoreCase(o.getRuolo());
		if (compare!=0){
			return compare;
		} else {
			compare =this.nome.compareToIgnoreCase(o.getNome());
			if (compare!=0){
				return compare;
			}
		} 
		return compare;
	}

	public void pulisciCaratteriSpeciali() {
		Iterator<Map.Entry<String, String>> it = MAP_CARATTERI_SPECIALI.entrySet().iterator();
		Map.Entry<String, String> current;
		String currentKey;
		String currentValue;
		while (it.hasNext()){
			current = it.next();
			currentKey = current.getKey();
			currentValue = current.getValue();
			nome = StringUtils.replace(nome.toLowerCase(), currentKey.toLowerCase(), currentValue).toUpperCase();
		} 
	}
}
