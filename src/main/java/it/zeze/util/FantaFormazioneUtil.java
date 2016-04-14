package it.zeze.util;

import it.zeze.bo.Giocatore;
import it.zeze.bo.ListaGiocatori;
import it.zeze.html.cleaner.HtmlCleanerUtil;
import it.zeze.selenium.SeleniumUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

public class FantaFormazioneUtil {

	public static ListaGiocatori calcolaProbabilitaImpiego(List<ListaGiocatori> listeGiocatori) {
		ListaGiocatori listaFinaleToReturn = new ListaGiocatori();
		listaFinaleToReturn.setProvenienza("Finale");
		calcolaProbabilitaImpiegoTra2Liste(listaFinaleToReturn, listeGiocatori.get(0), listeGiocatori.get(1));
		controlloDiRitorno(listaFinaleToReturn, listeGiocatori.get(1), listeGiocatori.get(0));
		return listaFinaleToReturn;
	}

	private static void calcolaProbabilitaImpiegoTra2Liste(ListaGiocatori listaGiocatoriFinale, ListaGiocatori listaGiocatori1, ListaGiocatori listaGiocatori2) {
		Giocatore currentGiocatore = null;
		Giocatore currentGiocatoreToInsert = null;
		int indexOf = -1;
		Giocatore currentGiocatoreContains = null;
		int currentProb;
		for (int i = 0; i < listaGiocatori1.size(); i++) {
			currentGiocatore = listaGiocatori1.get(i);
			currentGiocatoreToInsert = new Giocatore(currentGiocatore);
			indexOf = listaGiocatori2.indexOf(currentGiocatore);
			if (indexOf != -1) {
				currentGiocatoreContains = listaGiocatori2.get(indexOf);
				if (currentGiocatoreContains.isIn() && currentGiocatore.isIn()) {
					currentProb = currentGiocatore.getProbabilitaImpiegoTitolare();
					currentGiocatoreToInsert.setProbabilitaImpiegoTitolare(currentProb + 1);
				} else if (currentGiocatoreContains.isInSub() && currentGiocatore.isInSub()) {
					currentProb = currentGiocatore.getProbabilitaImpiegoPanchina();
					currentGiocatoreToInsert.setProbabilitaImpiegoPanchina(currentProb + 1);
				}
			}
			listaGiocatoriFinale.add(currentGiocatoreToInsert);
		}
	}

	private static void controlloDiRitorno(ListaGiocatori listaGiocatoriFinale, ListaGiocatori listaGiocatori1, ListaGiocatori listaGiocatori2) {
		Giocatore currentGiocatore = null;
		for (int i = 0; i < listaGiocatori1.size(); i++) {
			currentGiocatore = listaGiocatori1.get(i);
			if (!listaGiocatori2.contains(currentGiocatore)) {
				listaGiocatoriFinale.add(currentGiocatore);
			}
		}
	}

	public static ListaGiocatori generaFormazione(ListaGiocatori listaGiocatoriConImpiego, ListaGiocatori listaFormazione) {
		ListaGiocatori listaMiglioreFormazione = new ListaGiocatori();
		Giocatore currentGiocatore = null;
		for (int i = 0; i < listaFormazione.size(); i++) {
			currentGiocatore = listaFormazione.get(i);
			int indexOf = listaGiocatoriConImpiego.indexOf(currentGiocatore);
			if (indexOf != -1) {
				currentGiocatore = listaGiocatoriConImpiego.get(indexOf);
				listaMiglioreFormazione.add(currentGiocatore);
			} else {
				currentGiocatore.setNote("NON presente in titolari e riserve");
				listaMiglioreFormazione.add(currentGiocatore);

			}
		}
		return listaMiglioreFormazione;
	}

	public static void salvaMiaFormazioneFantaGeniusSisal(String pathFileDest, String user, String password) throws FileNotFoundException, IOException {
		SeleniumUtil.setDriverPage("http://sisal.fantagenius.com");
		SeleniumUtil.setInputFieldByTagAttribute("input", "name", "usr", user);
		SeleniumUtil.setInputFieldByTagAttribute("input", "name", "passw", password);
		SeleniumUtil.clickLinkTagAttribute("input", "name", "Submit");
		SeleniumUtil.setDriverPage("http://sisal.fantagenius.com/squadra.asp");
		SeleniumUtil.saveCurrentPage(pathFileDest);
	}

	public static void salvaMiaFormazioneFantaGenius(String pathFileDest, String user, String password) throws FileNotFoundException, IOException {
		SeleniumUtil.setDriverPage("http://fantagenius.fantagazzetta.com/");
		SeleniumUtil.setInputFieldByTagAttribute("input", "name", "usr", user);
		SeleniumUtil.setInputFieldByTagAttribute("input", "name", "passw", password);
		SeleniumUtil.clickLinkTagAttribute("input", "name", "Submit");
		SeleniumUtil.setDriverPage("http://fantagenius.fantagazzetta.com/squadra.asp");
		SeleniumUtil.saveCurrentPage(pathFileDest);
	}

	public static void salvaVotiFantaGenius(String pathFileDest, String user, String password) throws FileNotFoundException, IOException {
		SeleniumUtil.setDriverPage("http://www.fantagazzetta.com/");
		try {
			SeleniumUtil.setInputFieldByTagAttribute("input", "class", "login-user", user);
			SeleniumUtil.setInputFieldByTagAttribute("input", "class", "login-password", password);
			SeleniumUtil.clickLinkTagAttribute("input", "class", "login-submit");
			SeleniumUtil.setDriverPage("http://www.fantagazzetta.com/voti-fantagazzetta-serie-A");
			SeleniumUtil.waitForXPathExpression("//div[@id='allvotes']");
		} catch (Exception e){
			SeleniumUtil.setDriverPage("http://www.fantagazzetta.com/voti-serie-a");
			SeleniumUtil.waitForXPathExpression("//*[@id='hvoti']");
		}
		SeleniumUtil.saveCurrentPage(pathFileDest);
	}

	public static void salvaSquadre(String pathFileDest) throws FileNotFoundException, IOException {
		SeleniumUtil.setDriverPage("http://www.fantagazzetta.com/classifica-serie-A");
		SeleniumUtil.saveCurrentPage(pathFileDest);
	}

	public static void salvaGiocatoriPortieri(String pathFileDest) throws FileNotFoundException, IOException {
		SeleniumUtil.setDriverPage("http://www.fantagazzetta.com/quotazioni-serie-a/portieri/costodesc");
		SeleniumUtil.saveCurrentPage(pathFileDest);
	}

	public static void salvaGiocatoriDifensori(String pathFileDest) throws FileNotFoundException, IOException {
		SeleniumUtil.setDriverPage("http://www.fantagazzetta.com/quotazioni-serie-a/difensori/costodesc");
		SeleniumUtil.saveCurrentPage(pathFileDest);
	}

	public static void salvaGiocatoriCentrocampisti(String pathFileDest) throws FileNotFoundException, IOException {
		SeleniumUtil.setDriverPage("http://www.fantagazzetta.com/quotazioni-serie-a/centrocampisti/costodesc");
		SeleniumUtil.saveCurrentPage(pathFileDest);
	}

	public static void salvaGiocatoriAttaccanti(String pathFileDest) throws FileNotFoundException, IOException {
		SeleniumUtil.setDriverPage("http://www.fantagazzetta.com/quotazioni-serie-a/attaccanti/costodesc");
		SeleniumUtil.saveCurrentPage(pathFileDest);
	}

	public static void salvaCalendario(String pathFileDest) throws FileNotFoundException, IOException {
		SeleniumUtil.setDriverPage("http://www.fantagazzetta.com/calendario-serie-A");
		SeleniumUtil.saveCurrentPage(pathFileDest);
	}

	public static void salvaStatistichePerTutteLeGiornate(String pathFileDest, String user, String pass) throws FileNotFoundException, IOException, XPatherException {
		salvaVotiFantaGenius(pathFileDest, user, pass);
		// Rinomino il file accodando il numero della giornata
		File currentFile = new File(pathFileDest);
		String currentNumGiornata = null;
		String stagione = null;
		try {
			List<TagNode> listNodeGiorantaSelected = HtmlCleanerUtil.getListOfElementsByXPathFromFile(pathFileDest, "//div[@class='wrapper']/div[@class='main']/div[@class='graphic-big']/div[@class='day']/ul[@class='daynav']/li[@class='selected']");
			currentNumGiornata = listNodeGiorantaSelected.get(0).getText().toString();
		} catch (Exception e){
			// Nuovo formato HTML FantaGazzetta
			currentNumGiornata = HtmlCleanerUtil.getAttributeValueFromFile(pathFileDest, "class", "xlsgior", null);
			stagione = HtmlCleanerUtil.getAttributeValueFromFile(pathFileDest, "id", "hStagione", "value");
		}
		File destinationFile = new File(StringUtils.replace(currentFile.getAbsolutePath(), "{giornata}", String.valueOf(currentNumGiornata)));
		if (destinationFile.exists()) {
			FileUtils.forceDelete(destinationFile);
		}
		currentFile.renameTo(new File(StringUtils.replace(currentFile.getAbsolutePath(), "{giornata}", currentNumGiornata)));
		int currentIntNunmGiornata = Integer.valueOf(currentNumGiornata) - 1;
		boolean isNuovoHTML = false;
		while (currentIntNunmGiornata > 0) {
			System.out.println("Salvo stat giornata [" + currentIntNunmGiornata + "]");
			try {
				if (!isNuovoHTML){
					SeleniumUtil.setDriverPage("http://www.fantagazzetta.com/voti-fantagazzetta-serie-A-"+currentIntNunmGiornata+"-giornata");
		//			SeleniumUtil.clickLink(String.valueOf(currentIntNunmGiornata));
					System.out.println("Wait XPath");
					SeleniumUtil.waitForXPathExpression("//div[@id='allvotes']");
					isNuovoHTML = true;
				} else {
					throw new Exception("Rilancio per fare parsing con nuovo HTML");
				}
			} catch (Exception e){
				// Nuovo formato HTML FantaGazzetta
				String url = "http://www.fantagazzetta.com/voti-serie-a/{stagione}/{giornata}";
				url = StringUtils.replace(url, "{stagione}", stagione);
				url = StringUtils.replace(url, "{giornata}", String.valueOf(currentIntNunmGiornata));
				SeleniumUtil.setDriverPage(url);
				System.out.println("Wait XPath");
				SeleniumUtil.waitForXPathExpression("//div[@id='hvoti']");
				isNuovoHTML = true;
			}
			System.out.println("Prima di save page");
			SeleniumUtil.saveCurrentPage(pathFileDest);
			System.out.println("Dopo save page");
			destinationFile = new File(StringUtils.replace(currentFile.getAbsolutePath(), "{giornata}", String.valueOf(currentIntNunmGiornata)));
			if (destinationFile.exists()) {
				FileUtils.forceDelete(destinationFile);
			}
			currentFile.renameTo(destinationFile);
			currentIntNunmGiornata = currentIntNunmGiornata - 1;
		}
	}

	public static void salvaProbabiliFormazioniFG(String pathFileHTMLProbFormazioniFG) throws FileNotFoundException, IOException, XPatherException {
		SeleniumUtil.saveHTMLPage("http://www.fantagazzetta.com/probabili-formazioni-serie-A", pathFileHTMLProbFormazioniFG);
		// Rinomino il file accodando il numero della giornata
		File currentFile = new File(pathFileHTMLProbFormazioniFG);
		String currentGiornata = null;
		try {
			currentGiornata = HtmlCleanerUtil.getListOfElementsByAttributeFromFile(pathFileHTMLProbFormazioniFG, "id", "ContentPlaceHolderElle_Labelgiornata").get(0).getText().toString();
			currentGiornata = StringUtils.remove(currentGiornata.trim().toLowerCase(), " giornata");
		} catch (Exception e){
			// Nuovo formato HTML FantaGazzetta
			currentGiornata = HtmlCleanerUtil.getAttributeValueFromFile(pathFileHTMLProbFormazioniFG, "id", "id_giornata", "value");
		}
		File destinationFile = new File(StringUtils.replace(currentFile.getAbsolutePath(), "{giornata}", currentGiornata));
		if (destinationFile.exists()) {
			FileUtils.forceDelete(destinationFile);
		}
		currentFile.renameTo(destinationFile);
	}

	public static void salvaProbabiliFormazioniGazzetta(String pathFileHTMLProbFormazioniGS) throws FileNotFoundException, IOException, XPatherException {
		SeleniumUtil.saveHTMLPage("http://www.gazzetta.it/Calcio/prob_form/", pathFileHTMLProbFormazioniGS);
		// Rinomino il file accodando il numero della giornata
		File currentFile = new File(pathFileHTMLProbFormazioniGS);
		String currentGiornata = "";
		try {
			currentGiornata = HtmlCleanerUtil.getListOfElementsByAttributeFromFile(pathFileHTMLProbFormazioniGS, "class", "num-day").get(0).getText().toString();
			currentGiornata = StringUtils.substringBefore(currentGiornata.trim().toLowerCase(), "^");
		} catch (Exception e) {
			// Nuovo HTML
			System.out.println("nuovoHTML");
			List<TagNode> listNode = HtmlCleanerUtil.getListOfElementsByXPathFromFile(pathFileHTMLProbFormazioniGS, "//*[@id='calcio']/section[1]/section[3]/div[1]/div/div[1]/h3");
			
			String currentGiornataNew = listNode.get(0).getText().toString();
			// 1a giornata
			currentGiornata = StringUtils.substringBefore(currentGiornataNew.trim().toLowerCase(), "a giornata".toLowerCase());
			
		}
		File destinationFile = new File(StringUtils.replace(currentFile.getAbsolutePath(), "{giornata}", currentGiornata.trim()));
		if (destinationFile.exists()) {
			FileUtils.forceDelete(destinationFile);
		}
		currentFile.renameTo(destinationFile);
	}
}
