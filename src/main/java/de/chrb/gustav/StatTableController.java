package de.chrb.gustav;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class StatTableController {

    @FXML
    private TableColumn<?, ?> tcAvg;

    @FXML
    private TableColumn<?, ?> tcMin;

    @FXML
    private TableColumn<?, ?> tcNumPerc;

    @FXML
    private TableColumn<?, ?> tcMax;

    @FXML
    private TableColumn<?, ?> tcNum;

    @FXML
    private TableColumn<?, ?> tbSigma;

    @FXML
    private TableView<?> statTable;

    @FXML
    private TableColumn<?, ?> gcType;

    @FXML
    private TableColumn<?, ?> tcOverhead;

    @FXML
    private TableColumn<?, ?> tcTocalGcPerc;

    @FXML
    private TableColumn<?, ?> tcTocalGc;


}
