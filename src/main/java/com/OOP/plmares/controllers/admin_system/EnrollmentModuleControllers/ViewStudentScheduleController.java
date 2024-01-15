package com.OOP.plmares.controllers.admin_system.EnrollmentModuleControllers;

import com.OOP.plmares.controllers.DataInitializable;
import com.OOP.plmares.controllers.tableUtils.TableModel;
import com.OOP.plmares.controllers.tableUtils.TableUtils;
import com.OOP.plmares.controllers.tableUtils.admin_system.DBMethodsEnrollmentMod;
import com.OOP.plmares.controllers.tableUtils.admin_system.DBMethodsSySem;
import com.OOP.plmares.controllers.utilities.CommonUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.*;

public class ViewStudentScheduleController implements DataInitializable {
    @FXML private AnchorPane anchorPaneContentContainer;
    @FXML private Label lblFullName, lblCourse, lblStudentNo, lblStatus, lblYearLevel, lblSyTitle, lblSemesterTitle;
    @FXML
    private TableView<TableModel.ScheduleCalendar> tblVwScheduleCalendar;
    @FXML
    private TableColumn<TableModel.ScheduleCalendar, String> colMonday, colTuesday, colWednesday, colThursday, colFriday, colSaturday, colSunday;
    private final CommonUtils c = new CommonUtils();
    private final TableUtils t = new TableUtils();
    private String strSy = "", strSemester = "",
            strStudentNo = "", strFullName = "", strPrevModule = "";
    private boolean isInitialized = false;

    @Override
    public void initializeData(Map<String, Object> data) {
        strStudentNo = (String) data.get("studentNo");
        strFullName = (String) data.get("fullName");
        strPrevModule = (String) data.get("prevModule");

        if (!isInitialized) {
            isInitialized = true;
            initialize();
        }
    }

    @FXML
    private void initialize() {
        if (isInitialized) {
            // Set up active sy and sem
            strSy = DBMethodsSySem.getActiveSy();
            strSemester = DBMethodsSySem.getActiveSem();
            lblSyTitle.setText(strSy);
            lblSemesterTitle.setText(strSemester);

            // setup student general info labels
            TableModel.StudentGeneralInfo stdInfo = DBMethodsEnrollmentMod.getStudentGeneralInfo(strSy, strStudentNo);
            lblFullName.setText(stdInfo.getFullName().toUpperCase());
            lblCourse.setText(stdInfo.getCourseDescription());
            lblStudentNo.setText(stdInfo.getStudentNo());
            lblStatus.setText(stdInfo.getStatus());
            lblYearLevel.setText(stdInfo.getYearLevel());

            setupScheduleTextWrapping(colMonday);
            colMonday.setCellValueFactory(cellData -> cellData.getValue().strMondayProperty());
            setupScheduleTextWrapping(colTuesday);
            colTuesday.setCellValueFactory(cellData -> cellData.getValue().strTuesdayProperty());
            setupScheduleTextWrapping(colWednesday);
            colWednesday.setCellValueFactory(cellData -> cellData.getValue().strWednesdayProperty());
            setupScheduleTextWrapping(colThursday);
            colThursday.setCellValueFactory(cellData -> cellData.getValue().strThursdayProperty());
            setupScheduleTextWrapping(colFriday);
            colFriday.setCellValueFactory(cellData -> cellData.getValue().strFridayProperty());
            setupScheduleTextWrapping(colSaturday);
            colSaturday.setCellValueFactory(cellData -> cellData.getValue().strSaturdayProperty());
            setupScheduleTextWrapping(colSunday);
            colSunday.setCellValueFactory(cellData -> cellData.getValue().strSundayProperty());


            // Populate the TableView
           tblVwScheduleCalendar.getColumns().forEach(column -> column.setReorderable(false));
            tblVwScheduleCalendar.setFocusModel(null);
            String strRawSchedule = DBMethodsEnrollmentMod.getRawStudentSchedule(strSy, strSemester, strStudentNo);

            // Create a list of ScheduleCalendar objects based on the raw schedule string
            ObservableList<TableModel.ScheduleCalendar> scheduleCalendars = createScheduleCalendars(strRawSchedule);

            // Set the items in the TableView
            tblVwScheduleCalendar.setItems(scheduleCalendars);
        }
    }

    private ObservableList<TableModel.ScheduleCalendar> createScheduleCalendars(String strRawSchedule) {
        // Split the raw schedule string by commas to get individual schedules
        List<String> schedules = Arrays.asList(strRawSchedule.split(","));

        // Create 7 lists (1 list per day)
        List<List<String>> dayLists = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            dayLists.add(new ArrayList<>());
        }

        // Populate the day lists based on the schedules
        for (String schedule : schedules) {
            // Split the schedule string by "-" to get the day of the week and the rest of the schedule
            String[] parts = schedule.split("-");
            if (parts.length >= 2) {
                String day = parts[0];
                String restOfSchedule = String.join("-", Arrays.copyOfRange(parts, 1, parts.length));

                // Add the restOfSchedule to the appropriate list in dayLists
                switch (day) {
                    case "M":
                        dayLists.get(0).add(restOfSchedule);
                        break;
                    case "T":
                        dayLists.get(1).add(restOfSchedule);
                        break;
                    case "W":
                        dayLists.get(2).add(restOfSchedule);
                        break;
                    case "Th":
                        dayLists.get(3).add(restOfSchedule);
                        break;
                    case "F":
                        dayLists.get(4).add(restOfSchedule);
                        break;
                    case "S":
                        dayLists.get(5).add(restOfSchedule);
                        break;
                    case "Su":
                        dayLists.get(6).add(restOfSchedule);
                        break;
                    default:
                        System.out.println("Invalid day format!");
                        break;
                }
            }
        }

        // Create ScheduleCalendar objects based on the day lists
        ObservableList<TableModel.ScheduleCalendar> scheduleCalendars = FXCollections.observableArrayList();
        int maxListSize = dayLists.stream().mapToInt(List::size).max().orElse(0);

        for (int i = 0; i < maxListSize; i++) {
            TableModel.ScheduleCalendar scheduleCalendar = new TableModel.ScheduleCalendar(
                    getValueOrNull(dayLists.get(0), i),
                    getValueOrNull(dayLists.get(1), i),
                    getValueOrNull(dayLists.get(2), i),
                    getValueOrNull(dayLists.get(3), i),
                    getValueOrNull(dayLists.get(4), i),
                    getValueOrNull(dayLists.get(5), i),
                    getValueOrNull(dayLists.get(6), i)
            );

            scheduleCalendars.add(scheduleCalendar);
        }

        return scheduleCalendars;
    }

    // Helper method to get the value at index or null if index is out of bounds
    private String getValueOrNull(List<String> list, int index) {
        return (index < list.size()) ? list.get(index) : null;
    }


    public void onClickBtnGoBack() {
        if (strPrevModule.equals("APPROVALS")) {
            Map<String, Object> data = new HashMap<>();
            data.put("studentNo", strStudentNo);
            data.put("fullName", strFullName);
            c.loadScreen("/FXML/admin_system/EnrollmentModule/SubjectsApproval.fxml", anchorPaneContentContainer, data);
        } else if (strPrevModule.equals("ENROLLEES")) {
            Map<String, Object> data = new HashMap<>();
            data.put("studentNo", strStudentNo);
            data.put("fullName", strFullName);
            c.loadScreen("/FXML/admin_system/EnrollmentModule/EnrolleesMasterlist.fxml", anchorPaneContentContainer, data);
        }
    }

    // ----- 7.)  format text into new strings when inputted on schedule tables
    public <T> void setupScheduleTextWrapping(TableColumn<T, String> column) {
        column.setCellFactory(col -> {
            TableCell<T, String> cell = new TableCell<>() {
                private final Text text = new Text();

                {
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    setGraphic(text);
                    setPrefHeight(Control.USE_COMPUTED_SIZE);
                    text.wrappingWidthProperty().bind(column.widthProperty());
                    text.setTextAlignment(TextAlignment.CENTER);
                    text.setLineSpacing(3);
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        text.setText(null);
                        setGraphic(null);
                    } else {
                        // Splice the last letter
                        String displayedText = item.substring(0, item.length() - 1);

                        text.setText(displayedText);

                        // Color code the text based on the condition
                        if (item.endsWith("P")) {
                            text.setFill(Color.web("#dc8e35"));
                        } else {
                            text.setFill(Color.BLACK);
                        }

                        setGraphic(text);
                    }
                }
            };

            return cell;
        });
    }
}
