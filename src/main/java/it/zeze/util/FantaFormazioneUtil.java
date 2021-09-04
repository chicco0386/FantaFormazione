package it.zeze.util;

import it.zeze.bo.Giocatore;
import it.zeze.bo.ListaGiocatori;
import it.zeze.html.cleaner.HtmlCleanerUtil;
import it.zeze.selenium.SeleniumUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

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

    public static boolean salvaVotiFantaGenius(String pathFileDest, String user, String password) throws FileNotFoundException, IOException {
        boolean saveVoti = true;
        try {
            if (user != null && password != null) {
                SeleniumUtil.setDriverPage("https://www.fantacalcio.it/");
                SeleniumUtil.setInputFieldByTagAttribute("input", "class", "login-user", user);
                SeleniumUtil.setInputFieldByTagAttribute("input", "class", "login-password", password);
                SeleniumUtil.clickLinkTagAttribute("input", "class", "login-submit");
                SeleniumUtil.setDriverPage("https://www.fantacalcio.it/voti-fantagazzetta-serie-A");
                SeleniumUtil.waitForXPathExpression("//div[@id='allvotes']");
            } else {
                throw new Exception("Rilancio per fare parsing con nuovo HTML");
            }
        } catch (Exception e) {
            SeleniumUtil.setDriverPage("http://www.fantagazzetta.com/voti-fantacalcio-serie-a");
            try {
                SeleniumUtil.waitForXPathExpression("//*[@id='hvoti']");
            } catch (Exception ex) {
                System.out.println("Skip save statistiche");
                saveVoti = false;
            }
        }
        if (saveVoti) {
            SeleniumUtil.saveCurrentPage(pathFileDest);
        }
        return saveVoti;
    }

    public static void salvaSquadre(String pathFileDest, boolean useSelenium) throws FileNotFoundException, IOException {
        String urlPath = "https://www.fantacalcio.it/serie-a/classifica";
        if (useSelenium) {
            SeleniumUtil.setDriverPage(urlPath);
            SeleniumUtil.saveCurrentPage(pathFileDest);
        } else {
            FileUtils.copyURLToFile(new URL(urlPath), new File(pathFileDest));
        }
    }

    public static void salvaGiocatoriPortieri(String pathFileDest) throws FileNotFoundException, IOException {
        SeleniumUtil.setDriverPage("https://www.fantacalcio.it/quotazioni-serie-a/portieri/costodesc");
        SeleniumUtil.saveCurrentPage(pathFileDest);
    }

    public static void salvaGiocatoriDifensori(String pathFileDest) throws FileNotFoundException, IOException {
        SeleniumUtil.setDriverPage("https://www.fantacalcio.it/quotazioni-serie-a/difensori/costodesc");
        SeleniumUtil.saveCurrentPage(pathFileDest);
    }

    public static void salvaGiocatoriCentrocampisti(String pathFileDest) throws FileNotFoundException, IOException {
        SeleniumUtil.setDriverPage("https://www.fantacalcio.it/quotazioni-serie-a/centrocampisti/costodesc");
        SeleniumUtil.saveCurrentPage(pathFileDest);
    }

    public static void salvaGiocatoriAttaccanti(String pathFileDest) throws FileNotFoundException, IOException {
        SeleniumUtil.setDriverPage("https://www.fantacalcio.it/quotazioni-serie-a/attaccanti/costodesc");
        SeleniumUtil.saveCurrentPage(pathFileDest);
    }

    public static void salvaTuttiGiocatoriNew(String pathFileDest, String pathFileDestRuolo, String urlTemplate, boolean useSelenium) throws Exception {
        String urlPath = "https://www.fantacalcio.it/quotazioni-fantacalcio/alternativi";
        if (useSelenium) {
            SeleniumUtil.setDriverPage(urlPath);
            SeleniumUtil.saveCurrentPage(pathFileDest);
        } else {
            FileUtils.copyURLToFile(new URL(urlPath), new File(pathFileDest));
        }
        List<TagNode> listRuoli = HtmlCleanerUtil.getListOfElementsByXPathFromFile(pathFileDest, "//ul[@id='qttabs']//li");
        String ruolo;
        String stampa;
        String url;
        String response;
        for (TagNode currentRuolo : listRuoli) {
            ruolo = currentRuolo.getAttributeByName("data-role");
            stampa = currentRuolo.getAttributeByName("data-stamp");
            // System.out.println("[" + ruolo + "]-[" + stampa + "]");
            url = StringUtils.replace(urlTemplate, "{ruolo}", ruolo);
            url = StringUtils.replace(url, "{stampa}", stampa);
            response = ClientRestUtils.getCall(url, null, null, MediaType.APPLICATION_JSON_TYPE, String.class);
            File destFile = new File(StringUtils.replace(pathFileDestRuolo, "{ruolo}", ruolo));
            FileUtils.writeStringToFile(destFile, response);
        }
    }

    public static void salvaCalendario(String pathFileDest, boolean useSelenium) throws FileNotFoundException, IOException, XPatherException {
        String urlPath = "https://www.fantacalcio.it/serie-a/calendario";
        if (useSelenium) {
            SeleniumUtil.setDriverPage(urlPath);
            SeleniumUtil.saveCurrentPage(pathFileDest);
        } else {
            FileUtils.copyURLToFile(new URL(urlPath), new File(pathFileDest));
        }
        File currentFile = new File(pathFileDest);
        List<TagNode> listGiornate = HtmlCleanerUtil.getListOfElementsByXPathFromFile(pathFileDest, "//select[@id='selectGiornata']/option");
        String currentGiornata;
        for (TagNode currentNode : listGiornate) {
            currentGiornata = currentNode.getAttributeByName("value");
            String destinationFile = StringUtils.replace(currentFile.getAbsolutePath(), "{giornata}", currentGiornata);
            String currentUrlPath = urlPath + "/" + currentGiornata;
            if (useSelenium) {
                SeleniumUtil.setDriverPage(currentUrlPath);
                SeleniumUtil.waitForXPathExpression("//div[@id='artContainer']");
                SeleniumUtil.saveCurrentPage(destinationFile);
            } else {
                FileUtils.copyURLToFile(new URL(currentUrlPath), new File(destinationFile));
            }
        }

        File tmpFile = new File(pathFileDest);
        if (tmpFile.exists()) {
            FileUtils.forceDelete(tmpFile);
        }
    }

    public static void salvaStatistichePerTutteLeGiornate(String pathFileDest, String user, String pass) throws FileNotFoundException, IOException, XPatherException {
        boolean saveVoti = salvaVotiFantaGenius(pathFileDest, user, pass);
        if (saveVoti) {
            // Rinomino il file accodando il numero della giornata
            File currentFile = new File(pathFileDest);
            String currentNumGiornata = null;
            String stagione = null;
            boolean isNuovoHTML = false;
            try {
                List<TagNode> listNodeGiorantaSelected = HtmlCleanerUtil.getListOfElementsByXPathFromFile(pathFileDest, "//div[@class='wrapper']/div[@class='main']/div[@class='graphic-big']/div[@class='day']/ul[@class='daynav']/li[@class='selected']");
                currentNumGiornata = listNodeGiorantaSelected.get(0).getText().toString();
            } catch (Exception e) {
                // Nuovo formato HTML FantaGazzetta
                currentNumGiornata = HtmlCleanerUtil.getAttributeValueFromFile(pathFileDest, "class", "xlsgior", null);
                stagione = HtmlCleanerUtil.getAttributeValueFromFile(pathFileDest, "id", "hStagione", "value");
                isNuovoHTML = true;
            }

            int currentIntNunmGiornata = Integer.valueOf(currentNumGiornata);
            do {
                File destinationFile = new File(StringUtils.replace(currentFile.getAbsolutePath(), "{giornata}", String.valueOf(currentIntNunmGiornata)));
                if (!destinationFile.exists()) {
                    System.out.println("Salvo stat giornata [" + currentIntNunmGiornata + "]");
                    try {
                        if (!isNuovoHTML) {
                            SeleniumUtil.setDriverPage("https://www.fantacalcio.it/voti-fantagazzetta-serie-A-" + currentIntNunmGiornata + "-giornata");
                            // SeleniumUtil.clickLink(String.valueOf(currentIntNunmGiornata));
                            // System.out.println("Wait XPath");
                            SeleniumUtil.waitForXPathExpression("//div[@id='allvotes']");
                            // isNuovoHTML = true;
                        } else {
                            throw new Exception("Rilancio per fare parsing con nuovo HTML");
                        }
                    } catch (Exception e) {
                        // Nuovo formato HTML FantaGazzetta
                        String url = "https://www.fantacalcio.it/voti-fantacalcio-serie-a/{stagione}/{giornata}";
                        url = StringUtils.replace(url, "{stagione}", stagione);
                        url = StringUtils.replace(url, "{giornata}", String.valueOf(currentIntNunmGiornata));
                        SeleniumUtil.setDriverPage(url);
                        // System.out.println("Wait XPath");
                        SeleniumUtil.waitForXPathExpression("//div[@id='hvoti']");
                        // isNuovoHTML = true;
                    }
                    // System.out.println("Prima di save page");
                    SeleniumUtil.saveCurrentPage(pathFileDest);
                    // System.out.println("Dopo save page");
                    destinationFile = new File(StringUtils.replace(currentFile.getAbsolutePath(), "{giornata}", String.valueOf(currentIntNunmGiornata)));

                    currentFile.renameTo(destinationFile);
                } else {
                    // System.out.println("Giornata [" + currentIntNunmGiornata
                    // + "]
                    // gia' salvata");
                }
                currentIntNunmGiornata = currentIntNunmGiornata - 1;
            } while (currentIntNunmGiornata > 0);

            if (currentFile.exists()) {
                FileUtils.forceDelete(currentFile);
            }
        }

    }

    public static void salvaProbabiliFormazioniFG(String pathFileHTMLProbFormazioniFG, boolean useSelenium) throws FileNotFoundException, IOException, XPatherException {
        String urlPath = "https://www.fantacalcio.it/probabili-formazioni-serie-A";
        if (useSelenium) {
            SeleniumUtil.saveHTMLPage(urlPath, pathFileHTMLProbFormazioniFG);
        } else {
            FileUtils.copyURLToFile(new URL(urlPath), new File(pathFileHTMLProbFormazioniFG));
        }
        // Rinomino il file accodando il numero della giornata
        File currentFile = new File(pathFileHTMLProbFormazioniFG);
        String currentGiornata = null;
        try {
            currentGiornata = HtmlCleanerUtil.getListOfElementsByAttributeFromFile(pathFileHTMLProbFormazioniFG, "id", "ContentPlaceHolderElle_Labelgiornata").get(0).getText().toString();
            currentGiornata = StringUtils.remove(currentGiornata.trim().toLowerCase(), " giornata");
        } catch (Exception e) {
            // Nuovo formato HTML FantaGazzetta
            currentGiornata = HtmlCleanerUtil.getAttributeValueFromFile(pathFileHTMLProbFormazioniFG, "id", "id_giornata", "value");
        }
        File destinationFile = new File(StringUtils.replace(currentFile.getAbsolutePath(), "{giornata}", currentGiornata));
        if (destinationFile.exists()) {
            FileUtils.forceDelete(destinationFile);
        }
        currentFile.renameTo(destinationFile);
    }

    public static void salvaProbabiliFormazioniGazzetta(String pathFileHTMLProbFormazioniGS, boolean useSelenium) throws FileNotFoundException, IOException, XPatherException {
        String urlPath = "https://www.gazzetta.it/Calcio/prob_form/";
        if (useSelenium) {
            SeleniumUtil.saveHTMLPage(urlPath, pathFileHTMLProbFormazioniGS);
        } else {
            FileUtils.copyURLToFile(new URL(urlPath), new File(pathFileHTMLProbFormazioniGS));
        }
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

    public static void salvaStatistichePerTutteLeGiornateNew(String pathFileHTMLStatistiche) throws Exception {
        String urlPath = "https://www.fantacalcio.it/voti-fantacalcio-serie-a";
        String pathTemp = pathFileHTMLStatistiche + "_tmp";
        File currentFile = new File(pathTemp);
        FileUtils.copyURLToFile(new URL(urlPath), currentFile);
        String stagione = HtmlCleanerUtil.getAttributeValueFromFile(pathTemp, "id", "hStagione", "value");
        String tvStamp = HtmlCleanerUtil.getAttributeValueFromFile(pathTemp, "id", "tvstamp", "value");
        String ultimaGiornataCalcolata = HtmlCleanerUtil.getAttributeValueFromFile(pathTemp, "id", "ultimaC", "value");
        // Ciclo per tutte le squadre e creo un file unico
        List<TagNode> listDataTeam = HtmlCleanerUtil.getListOfElementsByAttributeFromFile(pathTemp, "data-team", null);
        String urlRestTemplate = "https://www.fantacalcio.it/Servizi/Voti.ashx?s={stagione}&g={currentGiornata}&tv={tvStamp}&t={currentDataTeam}";
        urlRestTemplate = StringUtils.replace(urlRestTemplate, "{stagione}", stagione);
        urlRestTemplate = StringUtils.replace(urlRestTemplate, "{tvStamp}", tvStamp);
        int currentGiornata = Integer.valueOf(ultimaGiornataCalcolata);
        String responseDecoded;
        while (currentGiornata > 0) {
            File destinationFile = new File(StringUtils.replace(pathFileHTMLStatistiche, "{giornata}", String.valueOf(currentGiornata)));
            if (!destinationFile.exists()) {
                System.out.println("Salvo stat giornata [" + currentGiornata + "]");
                String currentUrlRestTemplate = StringUtils.replace(urlRestTemplate, "{currentGiornata}", String.valueOf(currentGiornata));
                String currentDataTeam;
                for (TagNode currentDataTeamNode : listDataTeam) {
                    currentDataTeam = currentDataTeamNode.getAttributeByName("data-team");
                    System.out.println("    Salvo squadra [" + currentDataTeam + "]");
                    String currentUrlRest = StringUtils.replace(currentUrlRestTemplate, "{currentDataTeam}", String.valueOf(currentDataTeam));
                    try (InputStream response = ClientRestUtils.getCall(currentUrlRest, null, null, MediaType.APPLICATION_OCTET_STREAM_TYPE, InputStream.class)) {
                        FileUtils.writeStringToFile(destinationFile, IOUtils.toString(response), true);
                    }
                }
            }
            currentGiornata = currentGiornata - 1;
        }
        if (currentFile.exists()) {
            FileUtils.forceDelete(currentFile);
        }
    }

    public static void salvaStatistichePerTutteLeGiornateNew202122(String pathFileHTMLStatistiche) throws Exception {
        String urlPath = "https://www.fantacalcio.it/voti-fantacalcio-serie-a";
        String pathTemp = pathFileHTMLStatistiche + "_tmp";
        File currentFile = new File(pathTemp);
        FileUtils.copyURLToFile(new URL(urlPath), currentFile);
        String stagione = HtmlCleanerUtil.getAttributeValueFromFile(pathTemp, "id", "hStagione", "value");
        String ultimaGiornataCalcolata = HtmlCleanerUtil.getAttributeValueFromFile(pathTemp, "id", "ultimaC", "value");
        String urlRestTemplate = "https://www.fantacalcio.it/voti-fantacalcio-serie-a/{stagione}/{currentGiornata}";
        urlRestTemplate = StringUtils.replace(urlRestTemplate, "{stagione}", stagione);
        int currentGiornata = Integer.valueOf(ultimaGiornataCalcolata);
        while (currentGiornata > 0) {
            File destinationFile = new File(StringUtils.replace(pathFileHTMLStatistiche, "{giornata}", String.valueOf(currentGiornata)));
            if (!destinationFile.exists()) {
                System.out.println("Salvo stat giornata [" + currentGiornata + "]");
                String currentUrlRestTemplate = StringUtils.replace(urlRestTemplate, "{currentGiornata}", String.valueOf(currentGiornata));
                FileUtils.copyURLToFile(new URL(currentUrlRestTemplate), destinationFile);
            }
            currentGiornata = currentGiornata - 1;
        }
        if (currentFile.exists()) {
            FileUtils.forceDelete(currentFile);
        }
    }
}
