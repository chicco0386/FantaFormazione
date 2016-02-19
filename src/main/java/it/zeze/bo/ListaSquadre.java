package it.zeze.bo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import it.zeze.fantaformazioneweb.entity.Squadre;
import it.zeze.html.cleaner.HtmlCleanerUtil;

public class ListaSquadre {

	private List<Squadre> listaSquadre = new ArrayList<Squadre>();

	public void unmarshallFromHtmlFile(String filePath) throws IOException, XPatherException {
		List<TagNode> listNodeSquadre = HtmlCleanerUtil.getListOfElementsByXPathFromFile(filePath, "//div[@class='content']/table/tbody/tr/td[@class='a-left']/a");
		TagNode currentNodeSquadra;
		for (int i = 0; i < listNodeSquadre.size(); i++) {
			currentNodeSquadra = listNodeSquadre.get(i);
			if (!currentNodeSquadra.getText().toString().trim().isEmpty()) {
				listaSquadre.add(new Squadre(currentNodeSquadra.getText().toString().trim().toUpperCase(), null, null, null));
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
