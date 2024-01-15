package com.OOP.plmares.controllers.tableUtils;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.function.Consumer;

public class TableUtils {
    // ----- 1. set items to a table based on a list's data
    public static <T> void populateTable(TableView<T> tableView, ObservableList<T> data) {
        tableView.setItems(data);
    }

    // ----- 2. mouse listener for a row click
    public static <T> void setTableClickListener(TableView<T> tableView, Consumer<T> rowSelectionHandler) {
        tableView.setRowFactory(tv -> {
            TableRow<T> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && !row.isEmpty()) {
                    // Retrieve the selected item from the row
                    T selectedItem = row.getItem();

                    // Call the provided handler to handle the selection
                    rowSelectionHandler.accept(selectedItem);
                }
            });
            return row;
        });
    }

    // ----- 3. remove mouse listener for a row click
    public static <T> void removeTableClickListener(TableView<T> tableView) {
        tableView.setRowFactory(tv -> {
            TableRow<T> row = new TableRow<>();
            //row.setOnMouseClicked(defaultClickHandler);
            return row;
        });
    }

    // ----- 4. text wrap and adjust height for a table row
    public <T> void setupTextWrapping(TableColumn<T, String> column) {
        column.setCellFactory(col -> {
            TableCell<T, String> cell = new TableCell<>() {
                private final Text text = new Text();

                {
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    setGraphic(text);
                    setPrefHeight(Control.USE_COMPUTED_SIZE);
                    text.wrappingWidthProperty().bind(column.widthProperty());
                    text.setTextAlignment(TextAlignment.CENTER); // Set text alignment to center
                    text.setLineSpacing(3); // Adjust this value for the desired line spacing
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        text.setText(null);
                        setGraphic(null);
                    } else {
                        text.setText(item);
                        setGraphic(text);
                    }
                }

            };

            return cell;
        });
    }

    // ----- 5.)  text wrap and adjust height for a table row and create new line in comma occurrence (for day-time-modality)
    public <T> void setupTextWrappingWithLineBreaks(TableColumn<T, String> column) {
        column.setCellFactory(col -> {
            TableCell<T, String> cell = new TableCell<>() {
                private final Text text = new Text();
                private final VBox graphicContainer = new VBox();

                {
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    graphicContainer.getChildren().add(text);
                    setGraphic(graphicContainer);
                    setPrefHeight(Control.USE_COMPUTED_SIZE);
                    text.wrappingWidthProperty().bind(column.widthProperty());
                    text.setTextAlignment(TextAlignment.CENTER); // Set text alignment to center
                    text.setLineSpacing(5); // Adjust this value for the desired line spacing
                    graphicContainer.setPadding(new Insets(5, 0, 5, 0)); // Adjust padding as needed
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        text.setText(null);
                    } else {
                        // Replace commas with line breaks
                        String formattedText = item.replace(",", "\n");
                        text.setText(formattedText);
                    }
                }

            };
            return cell;
        });
    }
}
