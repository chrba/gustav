package de.chrb.gustav.controller;

import de.chrb.gustav.model.statistics.GCAnalyzeResult;
import javafx.scene.control.TabPane;

/**
 * Used for data binding of a {@link TabPane} to gc analyze results.
 *
 * @author Christian Bannes
 */
public class TabPaneGCObserver implements GCResultObserver {

	private TabPane accordionTabPane;

	public TabPaneGCObserver(TabPane accordionTabPane) {
		this.accordionTabPane = accordionTabPane;
	}

	/**
     * Data binds the given gc analyze result to the underlying
     * {@link TabPane}
     *
     * @param result the gc analyze result
     */
	@Override
	public void observe(GCAnalyzeResult result) {
    	final CustomTab tab = new CustomTab();
    	tab.setText(result.getGCFile().getName());
    	tab.setId(result.getGCFile().getName());
    	accordionTabPane.getTabs().add(tab);
    	tab.addData(result);
	}

	/**
     * Inregisters the data binding for the given gc file from the
     * underlying {@link TabPane}
     *
     * @param fromFileName references the file to unregister
     */
	@Override
	public void remove(String fromFileName) {
		accordionTabPane.getTabs().removeIf(t -> t.getId().equals(fromFileName));

	}


}
