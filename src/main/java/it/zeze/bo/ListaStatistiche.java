package it.zeze.bo;

import it.zeze.html.cleaner.HtmlCleanerUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import com.inamik.utils.SimpleTableFormatter;
import com.inamik.utils.TableFormatter;

public class ListaStatistiche {

	private List<Statistiche> listaStatistiche = new ArrayList<Statistiche>();

	public void unmarshallFromFileVotiFantaGazzetta(String filePath, boolean ordinaPerVotoFM, boolean ordinaPerVoto) throws IOException, XPatherException {
		List<TagNode> listaTabelleVotiPerSquadra = HtmlCleanerUtil.getListOfElementsByXPathFromFile(filePath, "//div[@id='allvotes']");
		TagNode currentNodeSquadra;
		String currentNomeSquadra;
		List<TagNode> listaGiocatori;
		TagNode currentNodeGiocatore;
		TagNode currentNodeGiocatoreNome;
		String currentGiocatoreNome;
		TagNode currentNodeGiocatoreRuolo;
		String currentGiocatoreRuolo;
		List<TagNode> listaColonneGiocatore;
		TagNode currentColonnaGiocatore;
		int currentGoalFatto;
		int currentGoalSuRigore;
		int currentGoalSubito;
		int currentRigoreParato;
		int currentRigoreSbagliato;
		int currentAutorete;
		int currentAssist;
		List<TagNode> listaNodeMedieVoti;
		TagNode currentNodeMediaVoto;
		BigDecimal currentMediaVoto;
		List<TagNode> listaNodeMedieFantaVoti;
		TagNode currentNodeMediaFantaVoto;
		BigDecimal currentMediaFantaVoto;
		for (int i = 0; i < listaTabelleVotiPerSquadra.size(); i++) {
			currentNodeSquadra = listaTabelleVotiPerSquadra.get(i);
			// Recupero il nome della squadra
			currentNodeSquadra = HtmlCleanerUtil.getListOfElementsByXPathFromElement(currentNodeSquadra, "/div[@id]").get(0);
			currentNomeSquadra = currentNodeSquadra.getAttributeByName("id");

			System.out.println(i + 1 + ") Elabora la squadra [" + currentNomeSquadra.toUpperCase() + "]");

			// Per ogni giocatore prendo i voti delle tre redazioni e ne faccio
			// la media
			listaGiocatori = HtmlCleanerUtil.getListOfElementsByXPathFromElement(currentNodeSquadra, "/table/tbody/tr[@class='P']");
			for (int y = 0; y < listaGiocatori.size(); y++) {
				currentMediaVoto = new BigDecimal(0);
				currentMediaFantaVoto = new BigDecimal(0);
				currentNodeGiocatore = listaGiocatori.get(y);
				currentNodeGiocatoreNome = currentNodeGiocatore.findElementByAttValue("class", "n", false, false);
				currentNodeGiocatoreNome = currentNodeGiocatoreNome.findElementByName("a", false);
				currentGiocatoreNome = currentNodeGiocatoreNome.getText().toString();
				currentNodeGiocatoreRuolo = currentNodeGiocatore.findElementByAttValue("class", "r", false, false);
				currentGiocatoreRuolo = currentNodeGiocatoreRuolo.getText().toString();
				listaColonneGiocatore = currentNodeGiocatore.getElementListByName("td", true);
				currentColonnaGiocatore = listaColonneGiocatore.get(19);
				if (currentColonnaGiocatore.getText().toString().contains("-")) {
					currentGoalFatto = 0;
				} else {
					currentGoalFatto = Integer.parseInt(currentColonnaGiocatore.getText().toString());
				}
				currentColonnaGiocatore = listaColonneGiocatore.get(20);
				if (currentColonnaGiocatore.getText().toString().contains("-")) {
					currentGoalSuRigore = 0;
				} else {
					currentGoalSuRigore = Integer.parseInt(currentColonnaGiocatore.getText().toString());
				}
				currentColonnaGiocatore = listaColonneGiocatore.get(21);
				if (currentColonnaGiocatore.getText().toString().contains("-")) {
					currentGoalSubito = 0;
				} else {
					currentGoalSubito = Integer.parseInt(currentColonnaGiocatore.getText().toString());
				}
				currentColonnaGiocatore = listaColonneGiocatore.get(22);
				if (currentColonnaGiocatore.getText().toString().contains("-")) {
					currentRigoreParato = 0;
				} else {
					currentRigoreParato = Integer.parseInt(currentColonnaGiocatore.getText().toString());
				}
				currentColonnaGiocatore = listaColonneGiocatore.get(23);
				if (currentColonnaGiocatore.getText().toString().contains("-")) {
					currentRigoreSbagliato = 0;
				} else {
					currentRigoreSbagliato = Integer.parseInt(currentColonnaGiocatore.getText().toString());
				}
				currentColonnaGiocatore = listaColonneGiocatore.get(24);
				if (currentColonnaGiocatore.getText().toString().contains("-")) {
					currentAutorete = 0;
				} else {
					currentAutorete = Integer.parseInt(currentColonnaGiocatore.getText().toString());
				}
				currentColonnaGiocatore = listaColonneGiocatore.get(25);
				if (currentColonnaGiocatore.getText().toString().contains("-")) {
					currentAssist = 0;
				} else {
					currentAssist = Integer.parseInt(currentColonnaGiocatore.getText().toString());
				}

				listaNodeMedieVoti = HtmlCleanerUtil.getListOfElementsByXPathFromElement(currentNodeGiocatore, "/td[@class='mv']");
				for (int t = 0; t < listaNodeMedieVoti.size(); t++) {
					currentNodeMediaVoto = listaNodeMedieVoti.get(t);
					if (currentNodeMediaVoto.getText().toString().contains("-")) {
						currentMediaVoto = currentMediaVoto.add(new BigDecimal(0));
					} else {
						currentMediaVoto = currentMediaVoto.add(new BigDecimal(currentNodeMediaVoto.getText().toString().replace(",", ".")));
					}
				}
				currentMediaVoto = currentMediaVoto.divide(new BigDecimal(listaNodeMedieVoti.size()), 0);

				// Recupero le medie dei fanta voti e ne faccio una media
				listaNodeMedieFantaVoti = HtmlCleanerUtil.getListOfElementsByXPathFromElement(currentNodeGiocatore, "/td[@class='fm']");
				for (int t = 0; t < listaNodeMedieFantaVoti.size(); t++) {
					currentNodeMediaFantaVoto = listaNodeMedieFantaVoti.get(t);
					if (currentNodeMediaFantaVoto.getText().toString().contains("-")) {
						currentMediaFantaVoto = currentMediaFantaVoto.add(new BigDecimal(0));
					} else {
						currentMediaFantaVoto = currentMediaFantaVoto.add(new BigDecimal(currentNodeMediaFantaVoto.getText().toString().replace(",", ".")));
					}
				}
				currentMediaFantaVoto = currentMediaFantaVoto.divide(new BigDecimal(listaNodeMedieFantaVoti.size()), 0);

				Statistiche statisticheToInsert = new Statistiche();
				statisticheToInsert.setSquadra(currentNomeSquadra.toUpperCase());
				statisticheToInsert.setNomeGiocatore(currentGiocatoreNome);
				statisticheToInsert.setRuolo(currentGiocatoreRuolo);
				statisticheToInsert.setGoalFatto(currentGoalFatto);
				statisticheToInsert.setGoalSuRigore(currentGoalSuRigore);
				statisticheToInsert.setGoalSubito(currentGoalSubito);
				statisticheToInsert.setRigoreParato(currentRigoreParato);
				statisticheToInsert.setRigoreSbagliato(currentRigoreSbagliato);
				statisticheToInsert.setAutorete(currentAutorete);
				statisticheToInsert.setAssist(currentAssist);
				statisticheToInsert.setVoto(currentMediaVoto);
				statisticheToInsert.setVotoFM(currentMediaFantaVoto);

				listaStatistiche.add(statisticheToInsert);
			}
		}
		if (ordinaPerVotoFM) {
			ComparatorStatisticheByVotoFM byVotoFM = new ComparatorStatisticheByVotoFM();
			Collections.sort(listaStatistiche, Collections.reverseOrder(byVotoFM));
		} else if (ordinaPerVoto){
			ComparatorStatisticheByVoto byVoto = new ComparatorStatisticheByVoto();
			Collections.sort(listaStatistiche, Collections.reverseOrder(byVoto));
		}
	}

	public void scriviSuFileTabella(String filePath) throws FileNotFoundException {
		PrintStream file = null;
		try {
			file = new PrintStream(filePath);
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

		tf.nextRow().nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine("Squadra");
		tf.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine("Nome");
		tf.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine("Ruolo");
		tf.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine("Voto");
		tf.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine("Media voto FG");
		tf.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine("Goal fatti");
		tf.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine("Goal su rigore");
		tf.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine("Goal subito");
		tf.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine("Rigore parato");
		tf.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine("Rigore sbagliato");
		tf.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine("Autoreti");
		tf.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine("Assist");
		tf.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine("Ammonizioni");
		tf.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine("Espulsioni");
		Statistiche currentGiocatore;
		for (int i = 0; i < listaStatistiche.size(); i++) {
			currentGiocatore = listaStatistiche.get(i);
			tf.nextRow().nextCell().addLine(currentGiocatore.getSquadra());
			tf.nextCell().addLine(currentGiocatore.getNomeGiocatore());
			tf.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine(currentGiocatore.getRuolo());
			tf.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine(currentGiocatore.getVoto().toPlainString());
			tf.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine(currentGiocatore.getVotoFM().toPlainString());
			tf.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine(String.valueOf(currentGiocatore.getGoalFatto()));
			tf.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine(String.valueOf(currentGiocatore.getGoalSuRigore()));
			tf.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine(String.valueOf(currentGiocatore.getGoalSubito()));
			tf.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine(String.valueOf(currentGiocatore.getRigoreParato()));
			tf.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine(String.valueOf(currentGiocatore.getRigoreSbagliato()));
			tf.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine(String.valueOf(currentGiocatore.getAutorete()));
			tf.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine(String.valueOf(currentGiocatore.getAssist()));
			tf.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine(String.valueOf(currentGiocatore.getAmmonizioni()));
			tf.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine(String.valueOf(currentGiocatore.getEspulsioni()));
		}
		return tf;
	}
	
	public Statistiche getStatisticheGiocatore(Giocatore giocatore){
		Statistiche statisticheToReturn=null;
		Statistiche currentStatistiche;
		for (int i=0;i<listaStatistiche.size();i++){
			currentStatistiche = listaStatistiche.get(i);
			if (currentStatistiche.isStatisticheGiocatore(giocatore.getSquadra(), giocatore.getNome(), giocatore.getRuolo())){
				statisticheToReturn = currentStatistiche;
				break;
			}
		}
		return statisticheToReturn;
	}

	public void setListaStatistiche(List<Statistiche> listaStatistiche) {
		this.listaStatistiche = listaStatistiche;
	}

	public List<Statistiche> getListaStatistiche() {
		return listaStatistiche;
	}

}
