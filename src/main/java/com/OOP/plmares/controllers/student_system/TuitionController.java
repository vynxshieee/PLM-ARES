package com.OOP.plmares.controllers.student_system;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.print.PageOrientation;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class TuitionController {
    @FXML private AnchorPane anchorPaneTuitionTable;
    @FXML private TableView<FeeItem> tblVwTuition, tblVwMiscellaneous, tblVwLab, tblVwOther;
    @FXML private TableColumn<FeeItem, String> colDescription, colDescription1, colDescription2, colDescription3;
    @FXML private TableColumn<FeeItem, Integer> colAmount, colAmount1, colAmount2, colAmount3;

    @FXML
    private void initialize() {
        // tuition table
        colDescription.setCellValueFactory(cellData -> cellData.getValue().strDescriptionProperty());
        colAmount.setCellValueFactory(cellData -> cellData.getValue().intAmountProperty().asObject());
        tblVwTuition.getColumns().forEach(column -> column.setReorderable(false));
        tblVwTuition.getItems().addAll(
                new FeeItem("Tuition Fee (23.00 units)", 27600)
        );
        // Miscellaneous table
        colDescription1.setCellValueFactory(cellData -> cellData.getValue().strDescriptionProperty());
        colAmount1.setCellValueFactory(cellData -> cellData.getValue().intAmountProperty().asObject());
        tblVwMiscellaneous.setFocusModel(null);
        tblVwMiscellaneous.getItems().addAll(
                new FeeItem("Cultural Activity", 74),
                new FeeItem("Library Fee", 732),
                new FeeItem("Medical/Dental Fee", 293),
                new FeeItem("Guidance Fee", 146),
                new FeeItem("Athletic Fee with PE", 293),
                new FeeItem("Student Welfare", 74),
                new FeeItem("Registration Fee", 74)
        );

        // Lab table
        colDescription2.setCellValueFactory(cellData -> cellData.getValue().strDescriptionProperty());
        colAmount2.setCellValueFactory(cellData -> cellData.getValue().intAmountProperty().asObject());
        tblVwLab.setFocusModel(null);
        tblVwLab.getColumns().forEach(column -> column.setReorderable(false));
        tblVwLab.getItems().addAll(
                new FeeItem("Category 3 Laboratory", 1800)
        );
        // Other fee table
        colDescription3.setCellValueFactory(cellData -> cellData.getValue().strDescriptionProperty());
        colAmount3.setCellValueFactory(cellData -> cellData.getValue().intAmountProperty().asObject());

        tblVwOther.setFocusModel(null);
        tblVwOther.getColumns().forEach(column -> column.setReorderable(false));
        tblVwOther.getItems().addAll(
                new FeeItem("Development Fund", 146),
                new FeeItem("Ang Pamantasan Fee", 50),
                new FeeItem("Supreme Student Council", 50)
        );
    }

    public static class FeeItem {
        private final SimpleStringProperty strDescription;
        private final SimpleIntegerProperty intAmount;

        public FeeItem(String description, int amount) {
            this.strDescription = new SimpleStringProperty(description);
            this.intAmount = new SimpleIntegerProperty(amount);
        }

        public String getStrDescription() {
            return strDescription.get();
        }

        public int getIntAmount() {
            return intAmount.get();
        }

        public StringProperty strDescriptionProperty() {
            return strDescription;
        }

        public IntegerProperty intAmountProperty() {
            return intAmount;
        }
    }

    @FXML
    private void handlePrint() {
        // Get the node to be printed
        Node nodeToPrint = anchorPaneTuitionTable;
        // Create a PrinterJob
        PrinterJob job = PrinterJob.createPrinterJob();

        if (job != null && job.showPrintDialog(anchorPaneTuitionTable.getScene().getWindow())) {
            job.getJobSettings().setPageLayout(
                    job.getPrinter().createPageLayout(javafx.print.Paper.A4, PageOrientation.PORTRAIT, javafx.print.Printer.MarginType.HARDWARE_MINIMUM)
            );

            job.printPage(nodeToPrint);

            job.endJob();
        }
    }
}
