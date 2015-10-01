package de.chrb.gustav.controller;

import de.chrb.gustav.model.statistics.GCAnalyzeResult;
import javafx.scene.control.TabPane;

public class TabPaneGCObserver implements GCResultObserver {

	private TabPane accordionTabPane;

	public TabPaneGCObserver(TabPane accordionTabPane) {
		this.accordionTabPane = accordionTabPane;
	}

	@Override
	public void observe(GCAnalyzeResult result) {
    	final CustomTab tab = new CustomTab();
    	tab.setText(result.getGCFile().getName());
    	tab.setId(result.getGCFile().getName());
    	accordionTabPane.getTabs().add(tab);
    	tab.addData(result);
	}

	@Override
	public void remove(String fromFileName) {
		accordionTabPane.getTabs().removeIf(t -> t.getId().equals(fromFileName));

	}


}
