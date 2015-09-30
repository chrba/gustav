package de.chrb.gustav.controller;

import de.chrb.gustav.model.statistics.GCAnalyzeResult;
import javafx.scene.control.TabPane;

public class TabPaneAdapter implements Controller {

	private TabPane accordionTabPane;

	public TabPaneAdapter(TabPane accordionTabPane) {
		this.accordionTabPane = accordionTabPane;
	}

	@Override
	public void add(GCAnalyzeResult result) {
    	final CustomTab tab = new CustomTab();
    	tab.setText(result.getGCFile().getName());
    	accordionTabPane.getTabs().add(tab);
    	tab.addData(result);
	}

	@Override
	public void remove(String fromFileName) {
		// TODO Auto-generated method stub

	}


}
