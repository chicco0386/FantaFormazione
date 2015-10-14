package it.zeze.bo;

import it.zeze.html.cleaner.HtmlCleanerUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import com.inamik.utils.SimpleTableFormatter;
import com.inamik.utils.TableFormatter;

public class ListaGiocatori {

	private String provenienza = "";
	private List<Giocatore> listaGiocatori = new ArrayList<Giocatore>();

	/*
	 * Mi vado a ricavare tutti i nome che hanno attributo class="player", poi
	 * ognuno di questi vado a prendere i tag con class='name' e class='namesub'
	 * e per ognuno di questi faccio l'unmashall a oggetto giocatore e lo
	 * inserisco nella lista. Per un esempio dei tag XML vedere cartella
	 * resources "XMLPlayers"
	 */
	public void unmarshallFromHtmlFileFantaGazzetta(String filePath) throws IOException, XPatherException {
		this.provenienza = "FantaGazzetta";
		List<TagNode> listPlayersNode = HtmlCleanerUtil.getListOfElementsByAttributeFromFile(filePath, "class", "player");
		Iterator<TagNode> itPlayer = listPlayersNode.iterator();
		TagNode currentPlayerNode = null;
		List<TagNode> listPlayersNameNode = null;
		Iterator<TagNode> itName = null;
		while (itPlayer.hasNext()) {
			currentPlayerNode = itPlayer.next();
			listPlayersNameNode = HtmlCleanerUtil.getListOfElementsByAttributeFromElement(currentPlayerNode, "class", "name");
			itName = listPlayersNameNode.iterator();
			while (itName.hasNext()) {
				Giocatore giocatoreToInsert = new Giocatore();
				giocatoreToInsert.unmarshallFromElementFantaGazzetta(itName.next());
				giocatoreToInsert.setIn(true);
				add(giocatoreToInsert);
			}
			listPlayersNameNode = HtmlCleanerUtil.getListOfElementsByAttributeFromElement(currentPlayerNode, "class", "namesub");
			itName = listPlayersNameNode.iterator();
			while (itName.hasNext()) {
				Giocatore giocatoreToInsert = new Giocatore();
				giocatoreToInsert.unmarshallFromElementFantaGazzetta(itName.next());
				giocatoreToInsert.setInSub(true);
				add(giocatoreToInsert);
			}
			Collections.sort(listaGiocatori);
		}
	}

	public void unmarshallFromHtmlFileGazzetta(String filePath) throws IOException, XPatherException {
		this.provenienza = "Gazzetta";
		List<TagNode> listPlayersName = HtmlCleanerUtil.getListOfElementsByAttributeFromFile(filePath, "class", "col2");
		Iterator<TagNode> itPlayer = listPlayersName.iterator();
		while (itPlayer.hasNext()) {
			Giocatore giocatoreToInsert = new Giocatore();
			giocatoreToInsert.unmarshallFromElementGazzetta(itPlayer.next());
			add(giocatoreToInsert);
		}
		Collections.sort(listaGiocatori);
	}

	public void unmarshallFromHtmlFileSky(String filePath) throws IOException, XPatherException {
		this.provenienza = "Sky";
		// Titolari
		List<TagNode> listTeamInNode = HtmlCleanerUtil.getListOfElementsByAttributeFromFile(filePath, "id", "team_list");
		Iterator<TagNode> itTeamIn = listTeamInNode.iterator();
		List<TagNode> listPlayersNameNode = null;
		Iterator<TagNode> itName = null;
		while (itTeamIn.hasNext()) {
			listPlayersNameNode = HtmlCleanerUtil.getListOfElementsByXPathFromElement(itTeamIn.next(), "//div");
			itName = listPlayersNameNode.iterator();
			while (itName.hasNext()) {
				Giocatore giocatoreToInsert = new Giocatore();
				giocatoreToInsert.unmarshallFromElementSky(itName.next());
				giocatoreToInsert.setIn(true);
				add(giocatoreToInsert);
			}
		}
		// Panchina
		List<TagNode> listTeamPanchinaNode = HtmlCleanerUtil.getListOfElementsByAttributeFromFile(filePath, "class", "team_purchases");
		Iterator<TagNode> itTeamPanchina = listTeamPanchinaNode.iterator();
		TagNode currentPanchina = null;
		String tagPanchina = "";
		String panchina = "";
		String nome = "";
		while (itTeamPanchina.hasNext()) {
			currentPanchina = itTeamPanchina.next();
			tagPanchina = currentPanchina.getText().toString();
			panchina = StringUtils.substringAfterLast(tagPanchina, "Panchina:");
			StringTokenizer pancaTokenizer = new StringTokenizer(panchina, ",");
			while (pancaTokenizer.hasMoreElements()) {
				nome = (String) pancaTokenizer.nextElement();
				nome = nome.trim();
				nome = StringUtils.removeEnd(nome, ".");
				Giocatore giocatoreToInsert = new Giocatore();
				giocatoreToInsert.setNome(nome);
				giocatoreToInsert.setInSub(true);
				add(giocatoreToInsert);
			}
		}
		Collections.sort(listaGiocatori);
	}

	public void unmarshallFormazioneFromHtmlFileFantaGazzetta(String filePath) throws IOException, XPatherException {
		this.provenienza = "Formazione Fanta Gazzetta";
		List<TagNode> listPlayersPerRuoloName = HtmlCleanerUtil.getListOfElementsByXPathFromFile(filePath, "//div[@id='principale']/div[@class='rosa']/div");
		// Recupero info per ogni giocatore
		TagNode currentNodeGiocatore;
		TagNode currentNodeNomeGiocatore;
		TagNode currentNodePerRecuperareSquadra;
		for (int i = 0; i < listPlayersPerRuoloName.size(); i++) {
			currentNodeGiocatore = listPlayersPerRuoloName.get(i);
			currentNodeNomeGiocatore = currentNodeGiocatore.findElementByAttValue("class", "nome", false, true);
			currentNodePerRecuperareSquadra = currentNodeGiocatore.findElementByAttValue("style", "font-size:0.9em", false, false);
			currentNodePerRecuperareSquadra = currentNodePerRecuperareSquadra.findElementByName("b", false);
			Giocatore giocatoreToInsert = new Giocatore();
			giocatoreToInsert.setNome(currentNodeNomeGiocatore.getText().toString());
			giocatoreToInsert.setSquadra(currentNodePerRecuperareSquadra.getText().toString());
			giocatoreToInsert.setRuolo(currentNodeGiocatore.getAttributeByName("class"));
			add(giocatoreToInsert);
		}
		Collections.sort(listaGiocatori);
	}

	public void scriviSuFile(String filePath) throws FileNotFoundException {
		PrintStream file = null;
		try {
			file = new PrintStream(filePath);
			file.println("Provenienza: " + this.provenienza);
			Giocatore currentGiocatore = null;
			for (int i = 0; i < this.listaGiocatori.size(); i++) {
				currentGiocatore = this.listaGiocatori.get(i);
				file.println(currentGiocatore);
			}
		} finally {
			IOUtils.closeQuietly(file);
		}
	}

	public void scriviSuFileTabella(String filePath) throws FileNotFoundException {
		PrintStream file = null;
		try {
			file = new PrintStream(filePath);
			file.println("Provenienza: " + this.provenienza);
			String[] tabella = this.tableFormatter().getFormattedTable();
			for (int i = 0, size = tabella.length; i < size; i++) {
				file.println(tabella[i]);
			}
		} finally {
			IOUtils.closeQuietly(file);
		}
	}

	public TableFormatter tableFormatter() {
		TableFormatter tf = new SimpleTableFormatter(true);
		tf.nextRow().nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine("Nome");
		tf.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine("Squadra");
		tf.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine("Ruolo");
		tf.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine("Titolare");
		tf.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine("Panchina");
		tf.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine("Prob Impiego Tit");
		tf.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine("Prob Impiego Panc");
		tf.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine("Avversario");
		tf.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine("MV");
		tf.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine("MV FG");
		Giocatore currentGiocatore;
		for (int i = 0; i < listaGiocatori.size(); i++) {
			currentGiocatore = listaGiocatori.get(i);
			tf.nextRow().nextCell().addLine(currentGiocatore.getNome());
			tf.nextCell().addLine(currentGiocatore.getSquadra());
			tf.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine(currentGiocatore.getRuolo());
			if (currentGiocatore.isIn()) {
				tf.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine("X");
			} else {
				tf.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine(" ");
			}
			if (currentGiocatore.isInSub()) {
				tf.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine("X");
			} else {
				tf.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine(" ");
			}
			tf.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine(String.valueOf(currentGiocatore.getProbabilitaImpiegoTitolare()));
			tf.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine(String.valueOf(currentGiocatore.getProbabilitaImpiegoPanchina()));
			tf.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine(currentGiocatore.getAvversario());
			if (currentGiocatore.getStatisticheGiocatore() != null) {
				tf.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine(currentGiocatore.getStatisticheGiocatore().getVoto().toPlainString());
				tf.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine(currentGiocatore.getStatisticheGiocatore().getVotoFM().toPlainString());
			} else {
				tf.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine("NP");
				tf.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine("NP");
			}
		}
		return tf;
	}

	/*
	 * Controllo quanti nomi duplicati ci sono in una lista di giocatori
	 */
	public void contaDuplicati() {
		Giocatore currentGiocatore = null;
		int frequenza = 0;
		List<Giocatore> listaDiAppoggio = listaGiocatori;
		for (int i = 0; i < listaDiAppoggio.size(); i++) {
			currentGiocatore = listaDiAppoggio.get(i);
			frequenza = Collections.frequency(listaDiAppoggio, currentGiocatore);
			if (frequenza > 1) {
				System.out.println("Duplicato: " + currentGiocatore);
				listaDiAppoggio.remove(i);
			}
		}
	}

	public void add(Giocatore giocatoreToInsert) {
		listaGiocatori.add(giocatoreToInsert);
	}

	public Giocatore get(int i) {
		return listaGiocatori.get(i);
	}

	public int size() {
		return listaGiocatori.size();
	}

	public boolean contains(Giocatore toCompare) {
		return listaGiocatori.contains(toCompare);
	}

	public int indexOf(Giocatore giocatore) {
		int index = -1;
		for (int i = 0; i < listaGiocatori.size(); i++) {
			if (listaGiocatori.get(i).equals(giocatore)) {
				index = i;
			}
		}
		return index;
		// return listaGiocatori.indexOf(giocatore);
	}

	public void setListaGiocatori(List<Giocatore> listaGiocatori) {
		this.listaGiocatori = listaGiocatori;
	}

	public List<Giocatore> getListaGiocatori() {
		return listaGiocatori;
	}

	public void setProvenienza(String provenienza) {
		this.provenienza = provenienza;
	}

	public String getProvenienza() {
		return provenienza;
	}

	public void unmarshallFromHtmlFileFantaGazzettaConSquadre(String filePath) throws IOException, XPatherException {
		this.provenienza = "FantaGazzetta";
		List<TagNode> listMatchsNode = HtmlCleanerUtil.getListOfElementsByAttributeFromFile(filePath, "class", "score-probabili");
		List<TagNode> listPlayersNode = HtmlCleanerUtil.getListOfElementsByAttributeFromFile(filePath, "class", "player");
		// Tratto la singola partita, alla prima partita nella lista associo la
		// prima coppia delle formazioni nella relativa lista
		TagNode currentMatchNode = null;
		String squadraIn = "";
		String squadraOut = "";
		List<TagNode> listPlayersNameInNode = null;
		List<TagNode> listPlayersNameOutNode = null;
		TagNode currentPlayerNode = null;
		for (int i = 0; i < listMatchsNode.size(); i++) {
			currentMatchNode = listMatchsNode.get(i);
			squadraIn = currentMatchNode.findElementByAttValue("class", "team-in-p", false, true).getText().toString();
			squadraOut = currentMatchNode.findElementByAttValue("class", "team-out-p", false, true).getText().toString();

			currentPlayerNode = listPlayersNode.get(i);
			// Prendo i giocatori in casa titolari
			listPlayersNameInNode = HtmlCleanerUtil.getListOfElementsByXPathFromElement(currentPlayerNode, "//div[@class='in']//div[@class='name']");
			for (int y = 0; y < listPlayersNameInNode.size(); y++) {
				Giocatore giocatoreToInsert = new Giocatore();
				giocatoreToInsert.unmarshallFromElementFantaGazzetta(listPlayersNameInNode.get(y));
				giocatoreToInsert.setSquadra(squadraIn);
				giocatoreToInsert.setIn(true);
				giocatoreToInsert.setProbabilitaImpiegoTitolare(1);
				add(giocatoreToInsert);
			}
			// Prendo i giocatori in casa pachina
			listPlayersNameInNode = HtmlCleanerUtil.getListOfElementsByXPathFromElement(currentPlayerNode, "//div[@class='in']//div[@class='namesub']");
			for (int y = 0; y < listPlayersNameInNode.size(); y++) {
				Giocatore giocatoreToInsert = new Giocatore();
				giocatoreToInsert.unmarshallFromElementFantaGazzetta(listPlayersNameInNode.get(y));
				giocatoreToInsert.setSquadra(squadraIn);
				giocatoreToInsert.setInSub(true);
				giocatoreToInsert.setProbabilitaImpiegoPanchina(1);
				add(giocatoreToInsert);
			}

			// Prendo i giocatori fuori casa
			listPlayersNameOutNode = HtmlCleanerUtil.getListOfElementsByXPathFromElement(currentPlayerNode, "//div[@class='out']//div[@class='name']");
			for (int y = 0; y < listPlayersNameOutNode.size(); y++) {
				Giocatore giocatoreToInsert = new Giocatore();
				giocatoreToInsert.unmarshallFromElementFantaGazzetta(listPlayersNameOutNode.get(y));
				giocatoreToInsert.setSquadra(squadraOut);
				giocatoreToInsert.setIn(true);
				giocatoreToInsert.setProbabilitaImpiegoTitolare(1);
				add(giocatoreToInsert);
			}
			// Prendo i giocatori in casa pachina
			listPlayersNameOutNode = HtmlCleanerUtil.getListOfElementsByXPathFromElement(currentPlayerNode, "//div[@class='out']//div[@class='namesub']");
			for (int y = 0; y < listPlayersNameOutNode.size(); y++) {
				Giocatore giocatoreToInsert = new Giocatore();
				giocatoreToInsert.unmarshallFromElementFantaGazzetta(listPlayersNameOutNode.get(y));
				giocatoreToInsert.setSquadra(squadraOut);
				giocatoreToInsert.setInSub(true);
				giocatoreToInsert.setProbabilitaImpiegoPanchina(1);
				add(giocatoreToInsert);
			}

		}
		Collections.sort(listaGiocatori);
	}

	public void unmarshallFromHtmlFileGazzettaConSquadre(String filePath) throws IOException, XPatherException {
		this.provenienza = "Gazzetta";
		List<TagNode> listMatchsIn = HtmlCleanerUtil.getListOfElementsByXPathFromFile(filePath, "//ul[@class='left campo']");
		List<TagNode> listMatchsOut = HtmlCleanerUtil.getListOfElementsByXPathFromFile(filePath, "//ul[@class='right campo']");
		TagNode tagNomeSquadra;
		String nomeSquadra;
		TagNode currentMatch;
		List<TagNode> listPlayersName;
		// Squadre casa
		for (int i = 0; i < listMatchsIn.size(); i++) {
			currentMatch = listMatchsIn.get(i);
			tagNomeSquadra = HtmlCleanerUtil.getListOfElementsByAttributeFromElement(currentMatch, "class", "title").get(0);
			tagNomeSquadra = tagNomeSquadra.findElementByName("span", false);
			nomeSquadra = tagNomeSquadra.getText().toString();
			listPlayersName = HtmlCleanerUtil.getListOfElementsByAttributeFromElement(currentMatch, "class", "col2");
			Iterator<TagNode> itPlayer = listPlayersName.iterator();
			while (itPlayer.hasNext()) {
				Giocatore giocatoreToInsert = new Giocatore();
				giocatoreToInsert.unmarshallFromElementGazzetta(itPlayer.next());
				giocatoreToInsert.setSquadra(nomeSquadra);
				add(giocatoreToInsert);
			}
		}
		// Squadre fuori casa
		for (int i = 0; i < listMatchsOut.size(); i++) {
			currentMatch = listMatchsOut.get(i);
			tagNomeSquadra = HtmlCleanerUtil.getListOfElementsByAttributeFromElement(currentMatch, "class", "title").get(0);
			tagNomeSquadra = tagNomeSquadra.findElementByName("span", false);
			nomeSquadra = tagNomeSquadra.getText().toString();
			listPlayersName = HtmlCleanerUtil.getListOfElementsByAttributeFromElement(currentMatch, "class", "col2");
			Iterator<TagNode> itPlayer = listPlayersName.iterator();
			while (itPlayer.hasNext()) {
				Giocatore giocatoreToInsert = new Giocatore();
				giocatoreToInsert.unmarshallFromElementGazzetta(itPlayer.next());
				giocatoreToInsert.setSquadra(nomeSquadra);
				add(giocatoreToInsert);
			}
		}
		Collections.sort(listaGiocatori);
	}

	public void pulisciCaratteriSpeciali() {
		for (int i = 0; i < listaGiocatori.size(); i++) {
			listaGiocatori.get(i).pulisciCaratteriSpeciali();
		}
	}

	/*
	 * Aggiorno la lista aggiungendo l'avversario e le statistiche del giocatore
	 */
	public void aggiornaListaGiocatori(ListaPartite listaPartite, ListaStatistiche listaStatistiche) {
		Giocatore currentGiocatore;
		for (int i = 0; i < listaGiocatori.size(); i++) {
			currentGiocatore = listaGiocatori.get(i);
			if (listaPartite.isGiocatoreInCasa(currentGiocatore.getSquadra())) {
				currentGiocatore.setSquadra(currentGiocatore.getSquadra().toUpperCase());
			} else {
				currentGiocatore.setSquadra(currentGiocatore.getSquadra().toLowerCase());
			}
			currentGiocatore.setAvversario(listaPartite.getSquadraAvversaria(currentGiocatore.getSquadra()));
			currentGiocatore.setStatisticheGiocatore(listaStatistiche.getStatisticheGiocatore(currentGiocatore));
		}
	}

}
