package it.zeze.bo;

import it.zeze.html.cleaner.HtmlCleanerUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import com.inamik.utils.SimpleTableFormatter;
import com.inamik.utils.TableFormatter;

public class ListaPartite {

	private String giornata;
	private List<Partita> listaPartite = new ArrayList<Partita>();

	public void unmarshallFromHtmlFile(String filePath) throws IOException, XPatherException {
		List<TagNode> listNodeGiornata = HtmlCleanerUtil.getListOfElementsByXPathFromFile(filePath, "//h2/span[@id='ContentPlaceHolderElle_Labelgiornata']");
		this.giornata = listNodeGiornata.get(0).getText().toString();
		List<TagNode> listMatchsNode = HtmlCleanerUtil.getListOfElementsByAttributeFromFile(filePath, "class", "score-probabili");
		TagNode currentMatchNode = null;
		String squadraIn = "";
		String squadraOut = "";
		for (int i = 0; i < listMatchsNode.size(); i++) {
			currentMatchNode = listMatchsNode.get(i);
			squadraIn = currentMatchNode.findElementByAttValue("class", "team-in-p", false, true).getText().toString();
			squadraOut = currentMatchNode.findElementByAttValue("class", "team-out-p", false, true).getText().toString();
			Partita partitaToInsert = new Partita();
			partitaToInsert.setSquadraCasa(squadraIn);
			partitaToInsert.setSquadraFuoriCasa(squadraOut);
			listaPartite.add(partitaToInsert);
		}
	}

	public TableFormatter tableFormatter() {
		TableFormatter tf = new SimpleTableFormatter(true);
		tf.nextRow().nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine("Squadra casa");
		tf.nextCell(TableFormatter.ALIGN_CENTER, TableFormatter.VALIGN_CENTER).addLine("Squadra fuori casa");
		Partita currentPartita;
		for (int i = 0; i < listaPartite.size(); i++) {
			currentPartita = listaPartite.get(i);
			tf.nextRow().nextCell().addLine(currentPartita.getSquadraCasa());
			tf.nextCell().addLine(currentPartita.getSquadraFuoriCasa());
		}
		return tf;
	}

	public void scriviSuFileTabella(String filePath) throws FileNotFoundException {
		PrintStream file = null;
		try {
			file = new PrintStream(filePath);
			file.println("Giornata: " + this.giornata);
			String[] tabella = this.tableFormatter().getFormattedTable();
			for (int i = 0, size = tabella.length; i < size; i++) {
				file.println(tabella[i]);
			}
		} finally {
			IOUtils.closeQuietly(file);
		}
	}

	public boolean isGiocatoreInCasa(String squadra) {
		boolean inCasa = false;
		Partita currentPartita;
		for (int i = 0; i < listaPartite.size(); i++) {
			currentPartita = listaPartite.get(i);
			if (currentPartita.isInCasa(squadra)) {
				inCasa = true;
			} else if (currentPartita.isFuoriCasa(squadra)) {
				inCasa = false;
			}
		}
		return inCasa;
	}

	public String getSquadraAvversaria(String squadra) {
		String squadraAvvToReturn = "";
		Partita currentPartita;
		for (int i = 0; i < listaPartite.size(); i++) {
			currentPartita = listaPartite.get(i);
			squadraAvvToReturn = currentPartita.getSquadraAvversaria(squadra);
			if (!squadraAvvToReturn.isEmpty()) {
				break;
			}
		}
		return squadraAvvToReturn;
	}

	public String getGiornata() {
		return giornata;
	}

	public void setGiornata(String giornata) {
		this.giornata = giornata;
	}

	public List<Partita> getListaPartite() {
		return listaPartite;
	}

	public void setListaPartite(List<Partita> listaPartite) {
		this.listaPartite = listaPartite;
	}

}
