package it.zeze.bo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.xpath.XPathExpressionException;

import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import it.zeze.fantaformazioneweb.entity.Squadre;
import it.zeze.html.cleaner.HtmlCleanerUtil;

public class ListaSquadre {

	private List<Squadre> listaSquadre = new ArrayList<Squadre>();

	public void unmarshallFromHtmlFile(String filePath) throws IOException, XPatherException, XPathExpressionException, ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException {
		List<TagNode> listNodeSquadre = HtmlCleanerUtil.getListOfElementsByXPathFromFile(filePath, "//div[@class='content']/table/tbody/tr/td[@class='a-left']/a");
		if (listNodeSquadre == null || listNodeSquadre.isEmpty()) {
			// Leggo squadre nuovo HTML
			listNodeSquadre = HtmlCleanerUtil.getListOfElementsByXPathSpecialFromFile(filePath, "//table[@id='DataTables_Table_0']/tbody/tr/td/a/span[contains(@class,'nteam')][1]");
		}
		TagNode currentNodeSquadra;
		String nomeSquadra;
		for (int i = 0; i < listNodeSquadre.size(); i++) {
			currentNodeSquadra = listNodeSquadre.get(i);
			if (!currentNodeSquadra.getText().toString().trim().isEmpty()) {
				nomeSquadra = currentNodeSquadra.getText().toString().trim().toUpperCase();
				listaSquadre.add(new Squadre(nomeSquadra, null, null, null));
			}
		}
	}

	public List<Squadre> getListaSquadre() {
		return listaSquadre;
	}

	public void setListaSquadre(List<Squadre> listaSquadre) {
		this.listaSquadre = listaSquadre;
	}

}
