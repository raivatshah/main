package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.EventDescriptor;

/**
 * Panel showing the list of places or activities,
 * and their frequencies in descending order.
 */
public class FrequencyListPanel extends UiPart<Region> {
    private static final String FXML = "FrequencyListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(FrequencyListPanel.class);

    @FXML
    private TableView<EventDescriptor> frequencyTable;

    @FXML
    private TableColumn<EventDescriptor, String> placeColumn;

    @FXML
    private TableColumn<EventDescriptor, String> frequencyColumn;

    public FrequencyListPanel(ObservableList<EventDescriptor> frequencyList) {
        super(FXML);
        placeColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        frequencyColumn.setCellValueFactory(cellData -> cellData.getValue().frequencyProperty());
        frequencyTable.setItems(frequencyList);
    }
}
