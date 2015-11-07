package de.chrb.gustav.controller;

import de.chrb.gustav.model.file.GCFile;
import de.chrb.gustav.model.statistics.GCAnalyzeResult;
import javafx.scene.control.ListView;

/**
 * Used for data binding of a {@link ListView} to gc analyze results.
 *
 * @author Christian Bannes
 */
public class FileListViewGCObserver implements GCResultObserver {
	private ListView<GCFile> fileListView;

	/**
	 *  Ctor
	 *
	 * @param fileListView the underlying {@link ListView}
	 */
	public FileListViewGCObserver(ListView<GCFile> fileListView) {
		this.fileListView = fileListView;
	}

	/**
	 * Data binds the given gc analyze result to the underlying
	 * {@link ListView}
	 *
	 * @param result the gc analyze result
	 */
	@Override
	public void observe(GCAnalyzeResult result) {
		this.fileListView.getItems().add(result.getGCFile());
	}

	/**
	 * Inregisters the data binding for the given gc file from the
	 * underlying {@link ListView}
	 *
	 * @param fromFileName references the file to unregister
	 */
	@Override
	public void remove(String fromFileName) {
		this.fileListView.getItems().removeIf(f -> f.getName().equals(fromFileName));
	}

}
