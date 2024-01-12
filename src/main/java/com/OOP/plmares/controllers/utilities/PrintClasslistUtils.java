package com.OOP.plmares.controllers.utilities;

import com.OOP.plmares.controllers.tableUtils.TableModel;
import javafx.collections.ObservableList;
import javafx.print.PageLayout;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.util.Pair;

public class PrintClasslistUtils {
    public Node createPrintNode(TableView<TableModel.ClasslistEnrollees> tableView, String strSchoolYear, String strSemester, Pair<String, String> keyValuePair) {
        StringBuilder printContent = new StringBuilder();
        String universityName = "Pamantasan ng Lungsod ng Maynila";

        // Header
        printContent.append(String.format("\t\t\t\t\t\t       %s\n\t\t\t\t\t\t\t\t       S.Y. %s\n\t\t\t\t\t\t\t\t\t    Semester %s  \n\n\n",
                universityName, strSchoolYear, strSemester));

        // Subject
        if(!keyValuePair.getKey().isEmpty()){
            printContent.append(String.format("%s: %s", keyValuePair.getKey(), keyValuePair.getValue()) + "\n\n");
        }

        // Column names
        printContent.append(String.format("\t\t\t\t%-37s%-40s%-40s", "Status", "Student No.", "Full Name")).append("\n\n");

        // Content
        ObservableList<TableModel.ClasslistEnrollees> items = tableView.getItems();
        int lineNumber = 1; // Counter for line numbers
        for (TableModel.ClasslistEnrollees item : items) {
            // Add line number
            printContent.append(lineNumber).append(".\t\t\t\t");

            // Append the field values separated by \t\t
            printContent.append(item.getStrStatus()).append("\t\t\t\t");
            printContent.append(item.getStrStudentNo()).append("\t\t\t\t");
            printContent.append(item.getStrFullName()).append("\n");

            lineNumber++;
        }

        // Create a node with the printable content, e.g., a TextArea
        // Customize this based on your requirements
        TextArea printTextArea = new TextArea(printContent.toString());
        printTextArea.setEditable(false);
        printTextArea.setWrapText(true);

        return printTextArea;
    }

    public boolean printNode(PrinterJob job, Node node) {
        PageLayout pageLayout = job.getJobSettings().getPageLayout();

        if (node instanceof TextArea) {
            return printTextArea(job, (TextArea) node, pageLayout);
        } else {
            // Handle other types of nodes (if needed)...
            return false;
        }
    }

    private boolean printTextArea(PrinterJob job, TextArea textArea, PageLayout pageLayout) {
        int totalLines = countLines(textArea.getText());
        int linesPerPage = 37;

        for (int start = 0; start < totalLines; start += linesPerPage) {
            int end = Math.min(start + linesPerPage, totalLines);
            String pageText = extractLines(textArea.getText(), start, end);

            // Create a new TextArea for printing
            TextArea printArea = new TextArea(pageText);
            printArea.setEditable(false);
            printArea.setWrapText(true);

            // Apply CSS to make background and border transparent
            printArea.setStyle(
                    "-fx-background: white; " +
                            "-fx-background-color: white; " +
                            "-fx-border-width: 0; " +
                            "-fx-text-fill: black; " +
                            "-fx-font-family: 'Times New Roman';" +
                            "-fx-effect: null; " +
                            "-fx-padding: 0; " +
                            "-fx-control-inner-background: transparent; " +
                            "-fx-min-width: 900px;"
            );


            boolean success = job.printPage(pageLayout, printArea);

            if (!success) {
                return false;
            }
        }

        return true;
    }

    private int countLines(String text) {
        return text.split("\n").length;
    }

    private String extractLines(String text, int start, int end) {
        String[] lines = text.split("\n");
        StringBuilder pageText = new StringBuilder();

        for (int i = start; i < end && i < lines.length; i++) {
            pageText.append(lines[i]).append('\n');
        }

        return pageText.toString();
    }


}

