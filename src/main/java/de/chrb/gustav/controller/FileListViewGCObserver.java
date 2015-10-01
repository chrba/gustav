package de.chrb.gustav.controller;

import de.chrb.gustav.model.file.GCFile;
import de.chrb.gustav.model.statistics.GCAnalyzeResult;
import javafx.scene.control.ListView;

public class FileListViewGCObserver implements GCResultObserver {

	private ListView<GCFile> fileListView;

	public FileListViewGCObserver(ListView<GCFile> fileListView) {
		this.fileListView = fileListView;
	}

	@Override
	public void observe(GCAnalyzeResult result) {
		this.fileListView.getItems().add(result.getGCFile());
	}

	@Override
	public void remove(String fromFileName) {
		this.fileListView.getItems().removeIf(f -> f.getName().equals(fromFileName));
	}

}
