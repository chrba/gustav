package de.chrb.gustav.model.statistics;

import de.chrb.gustav.controller.StatTableController;
import de.chrb.gustav.model.file.GCFile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;

/**
 * Class that holds all statistics data of a gc file.
 * The data is connected to corresponding charts, data binding
 * will change the charts as soon as the data is changed.
 *
 * @author Christian Bannes
 */
public class DataContainer {
	private final ObservableList<Statistics> statistics = FXCollections.observableArrayList();
	private final ObservableList<Tab> tabs = FXCollections.observableArrayList();
	private final ObservableList<GCFile> files = FXCollections.observableArrayList();
	private final ObservableList<Series<String, Integer>> nums = FXCollections.observableArrayList();

	private final StatisticsParser statisticsParser = new StatisticsParser();


	public void add(final GCFile gcFile) {
		this.files.add(gcFile);
		this.statistics.addAll(statisticsParser.createNewFrom(gcFile));
	}

	public void remove(final GCFile gcFile) {
		this.files.remove(gcFile);
		statistics.removeIf(s -> s.fileName.get().equals(gcFile.getName()));
		tabs.removeIf(t -> t.getText().equals(gcFile.getName()));
		nums.removeIf(s -> s.getName().equals(gcFile.getName()));
	}

	public void bind(final StatTableController controller) {
		controller.setItems(this.statistics);
	}

	public void bind(final ListView<GCFile> listView) {
		listView.setItems(this.files);
	}


}
