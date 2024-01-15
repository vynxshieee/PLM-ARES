package com.OOP.plmares.controllers.tableUtils;
import javafx.beans.property.*;
public class TableModel {
    public static class ParentStudentInfo {  // this is  a parent table that has the information of (a) student(s) to be displayed in multiple tables
        private final StringProperty strStudentNo, strFullName, strCourse, strYear, strStatus, strActive;
        public ParentStudentInfo(String strStudentNo, String strFullName, String strCourse, String strYear, String strStatus, String strActive) {
            this.strStudentNo = new SimpleStringProperty(strStudentNo);
            this.strFullName = new SimpleStringProperty(strFullName);
            this.strCourse = new SimpleStringProperty(strCourse);
            this.strYear = new SimpleStringProperty(strYear);
            this.strStatus = new SimpleStringProperty(strStatus);
            this.strActive = new SimpleStringProperty(strActive);
        }
        public String getStrStudentNo() { return strStudentNo.get(); }
        public String getStrFullName() { return strFullName.get(); }
        public String getStrCourse() { return strCourse.get(); }
        public String getStrStatus() { return strStatus.get(); }
        public String getStrActive() { return strActive.get(); }
        public StringProperty strStudentNoProperty() { return strStudentNo; }
        public StringProperty strFullNameProperty() { return strFullName; }
        public StringProperty strCourseProperty() { return strCourse; }
        public StringProperty strYearProperty() { return strYear; }
        public StringProperty strStatusProperty() { return strStatus; }
        public StringProperty strActiveProperty() { return strActive; }
    }

    public static class IrregPotentialApplicant extends ParentStudentInfo {
        private final IntegerProperty intUnitsEnrolled;
        public IrregPotentialApplicant(String strStudentNo, String strFullName, String strCourse, String strYear, String strStatus, int intUnitsEnrolled) {
            super(strStudentNo, strFullName, strCourse, strYear, strStatus, null); // "active" are not applicable

            this.intUnitsEnrolled = new SimpleIntegerProperty(intUnitsEnrolled);
        }

        public int getIntUnitsEnrolled() { return intUnitsEnrolled.get(); }

        public IntegerProperty intUnitsEnrolledProperty() { return intUnitsEnrolled; }
    }

    public static class StudentEnrollees extends ParentStudentInfo {
        public StudentEnrollees(String strStudentNo, String strFullName, String strCourse, String strYear, String strStatus) {
            super(strStudentNo, strFullName, strCourse, strYear, strStatus, null); // "active" are not applicable
        }
    }
    public static class ClasslistEnrollees extends ParentStudentInfo { // for classlist generators
        public ClasslistEnrollees() {
            super(null, null, null, null, null, null);
        }
        public ClasslistEnrollees(String strStudentNo, String strFullName, String strStatus) {
            super(strStudentNo, strFullName, null, null, strStatus, null); // "active" are not applicable
        }
    }

    public static class GradeEntryStudent extends ParentStudentInfo { // for grade entry module
        private final DoubleProperty dblGrade;
        private final StringProperty strRemark;

        public GradeEntryStudent(String strStudentNo, String strFullName, double dblGrade, String strRemark) {
            super(strStudentNo, strFullName, null, null, null, null);
            this.dblGrade = new SimpleDoubleProperty(dblGrade);
            this.strRemark = new SimpleStringProperty(strRemark);
        }
        public DoubleProperty dblGradeProperty() { return dblGrade; }
        public double getDblGrade() { return dblGrade.get(); }
        public StringProperty strRemarkProperty() { return strRemark; }
        public void setStrRemark(String remark) { strRemark.set(remark); }

    }

    public static class IrregApprovals extends ParentStudentInfo {
        private final IntegerProperty intSubjectCount;
        public IrregApprovals(String strStudentNo, String strFullName, String strCourse, String strYear, int intSubjectCount) {
            super(strStudentNo, strFullName, strCourse, strYear, null, null); // "status" and "active" are not applicable
            this.intSubjectCount = new SimpleIntegerProperty(intSubjectCount);
        }
        public IntegerProperty intSubjectCountProperty() {
            return intSubjectCount;
        }
    }

    public static class StudentMasterlist extends ParentStudentInfo {
        private final StringProperty strGender, strBirthday, strMobileNum, strAddress, strEmail;
        public StudentMasterlist(String strStudentNo, String strFullName, String strGender, String strCourse, String strStatus, String strBirthday,
                                 String strMobileNum, String strAddress, String strEmail, String strActive) {
            super(strStudentNo, strFullName, strCourse, null, strStatus, strActive); // "yr lvl" not applicable
            this.strGender = new SimpleStringProperty(strGender);
            this.strBirthday = new SimpleStringProperty(strBirthday);
            this.strMobileNum = new SimpleStringProperty(strMobileNum);
            this.strAddress = new SimpleStringProperty(strAddress);
            this.strEmail = new SimpleStringProperty(strEmail);
        }
        public String getStrGender() { return strGender.get(); }
        public String getStrBirthday() { return strBirthday.get(); }
        public String getStrMobileNum() { return strMobileNum.get(); }
        public String getStrAddress() { return strAddress.get(); }
        public String getStrEmail() { return strEmail.get(); }
        public StringProperty strGenderProperty() { return strGender; }
        public StringProperty strBirthdayProperty() { return strBirthday; }
        public StringProperty strMobileNumProperty() { return strMobileNum; }
        public StringProperty strAddressProperty() { return strAddress; }
        public StringProperty strEmailProperty() { return strEmail; }
    }

    public static class EmployeeMasterlist extends StudentMasterlist {
        private final StringProperty strEmployeeID;
        public EmployeeMasterlist(String strEmployeeID, String strFullName, String strEmail, String strGender, String strMobileNum, String strAddress,
                                  String strBirthday, String strActive) {
            super(null, strFullName, strGender, null, null, strBirthday, strMobileNum, strAddress, strEmail, strActive);
            this.strEmployeeID = new SimpleStringProperty(strEmployeeID);
        }
        public String getStrEmployeeID() { return strEmployeeID.get(); }
        public StringProperty strEmployeeIDProperty() { return strEmployeeID; }
    }
    public static class FacultyOptions extends EmployeeMasterlist {
        public FacultyOptions(String strEmployeeID, String strFullName) {
            super(strEmployeeID, strFullName, null, null, null, null,
                    null, null);
        }
    }

    public static class StudentEnrollmentHistory extends ParentStudentInfo {
        private final StringProperty strSy, strSemester;
        public StudentEnrollmentHistory(String strStudentNo, String strFullName, String strCourse,
                                        String strYear, String strStatus, String strSy, String strSemester, String strActive) {
            super(strStudentNo, strFullName, strCourse, strYear, strStatus, strActive);

            this.strSy = new SimpleStringProperty(strSy);
            this.strSemester = new SimpleStringProperty(strSemester);
        }
        public StringProperty strSyProperty() { return strSy; }
        public String getStrSy() { return strSy.get(); }
        public StringProperty strSemesterProperty() { return strSemester; }
        public String getStrSemester() { return strSemester.get(); }
    }

    public static class GeneralSubjectInfo {
        private final StringProperty subjectCode, description, section, dayTimeModality, faculty, college, strRemark;
        private final IntegerProperty units, classCount, slots, queue;

        public GeneralSubjectInfo(String subjectCode, String description, String section,
                                  String dayTimeModality, int units, String faculty, String college, int classCount,
                                  int slots, int queue, String strRemark) {
            this.subjectCode = new SimpleStringProperty(subjectCode);
            this.description = new SimpleStringProperty(description);
            this.section = new SimpleStringProperty(section);
            this.dayTimeModality = new SimpleStringProperty(dayTimeModality);
            this.units = new SimpleIntegerProperty(units);
            this.faculty = new SimpleStringProperty(faculty);
            this.college = new SimpleStringProperty(college);
            this.classCount = new SimpleIntegerProperty(classCount);
            this.slots = new SimpleIntegerProperty(slots);
            this.queue = new SimpleIntegerProperty(queue);
            this.strRemark = new SimpleStringProperty(strRemark);
        }
        public String getSubjectCode() { return subjectCode.get(); }
        public String getDescription() { return description.get(); }
        public String getSection() { return section.get(); }
        public int getUnits() { return units.get(); }
        public String getFaculty() { return faculty.get(); }
        public String getCollege() { return college.get(); }
        public String getRemark() { return strRemark.get(); }
        public StringProperty subjectCodeProperty() { return subjectCode; }
        public StringProperty descriptionProperty() { return description; }
        public StringProperty sectionProperty() { return section; }
        public StringProperty dayTimeModalityProperty() { return dayTimeModality; }
        public IntegerProperty unitsProperty() { return units; }
        public StringProperty facultyProperty() { return faculty; }
        public StringProperty collegeProperty() { return college; }
        public IntegerProperty classCountProperty() { return classCount; }
        public IntegerProperty slotsProperty() { return slots; }
        public IntegerProperty queueProperty() { return queue; }
        public StringProperty remarkProperty() { return strRemark; }

    }

    public static class SubjectModuleInfo extends GeneralSubjectInfo {    // subject details for subject module
        private final StringProperty strCurriculum, status;

        public SubjectModuleInfo(String subjectCode, String description, int units, String strCurriculum, String strCollege, String status) {
            super(subjectCode, description, null, null, units, null, strCollege, 0,0, 0, null);

            this.strCurriculum = new SimpleStringProperty(strCurriculum);
            this.status = new SimpleStringProperty(status);
        }
        public String getStrCurriculum() { return strCurriculum.get(); }
        public String getStatus() { return status.get(); }
        public StringProperty strCurriculumProperty() { return strCurriculum; }
        public StringProperty statusProperty() { return status; }
    }
    public static class SubjectOptions extends GeneralSubjectInfo{
        public SubjectOptions(String subjectCode, String description){
            super(subjectCode, description, null, null, 0, null, null,0,
                    0, 0, null);
        }
    }
    public static class ScheduleModule extends GeneralSubjectInfo {
        private final StringProperty strSemester, strDay, strTime, strRoom, strType, strSequenceNumber;

        public ScheduleModule(String strSemester, String strCollegeCode, String strSection, String strSubjectCode, String strDescription, String strDay,
                              String strTime, String strRoom, String strType, String strSequenceNumber, String strFaculty) {
            super(strSubjectCode, strDescription, strSection, null, 0, strFaculty, strCollegeCode, 0, 0, 0, null);

            this.strSemester = new SimpleStringProperty(strSemester);
            this.strDay = new SimpleStringProperty(strDay);
            this.strTime = new SimpleStringProperty(strTime);
            this.strRoom = new SimpleStringProperty(strRoom);
            this.strType = new SimpleStringProperty(strType);
            this.strSequenceNumber = new SimpleStringProperty(strSequenceNumber);
        }
        public String getStrSemester() { return strSemester.get(); }
        public StringProperty strSemesterProperty() { return strSemester; }
        public String getStrDay() { return strDay.get(); }
        public StringProperty strDayProperty() { return strDay; }
        public String getStrTime() { return strTime.get(); }
        public StringProperty strTimeProperty() { return strTime; }
        public String getStrRoom() { return strRoom.get(); }
        public StringProperty strRoomProperty() { return strRoom; }
        public String getStrType() { return strType.get(); }
        public StringProperty strTypeProperty() { return strType; }
        public String getStrSequenceNumber() { return strSequenceNumber.get(); }
        public StringProperty strSequenceNumberProperty() { return strSequenceNumber; }
    }

    public static class SubjectSectionClasslist extends GeneralSubjectInfo {
        private final String strSy, strSemester;
        public SubjectSectionClasslist(String strSy, String strSemester, String subjectCode, String strFaculty, String strSection, String strDescription ) {
            super(subjectCode, strDescription, strSection, null, 0, strFaculty, null, 0,0, 0, null); // faculty and remark is null
            this.strSy = new String(strSy);
            this.strSemester = new String(strSemester);
        }
        public String getStrSy() { return strSy; }
        public String getStrSemester() { return strSemester; }
    }

    public static class ApprovalSubjectInfo extends GeneralSubjectInfo {
        public ApprovalSubjectInfo(String subjectCode, String description, String section,
                                   String dayTimeModality, int units, String college,
                                   int slots, int queue, String strStatus) {
            super(subjectCode, description, section, dayTimeModality, units, null, college, 0, slots, queue, strStatus); // faculty and remark is null
        }
    }

    public static class StudentRecordSubject extends GeneralSubjectInfo {
        private final StringProperty strGrade;
        public StudentRecordSubject(String strSubjectCode, String strDescription, int intUnits, String strGrade, String strRemark) {
            super(strSubjectCode, strDescription, null, null, intUnits, null, null, 0,0, 0, strRemark);
            this.strGrade = new SimpleStringProperty(strGrade);
        }
        public StringProperty gradeProperty() { return strGrade; }
        public String getGrade() { return strGrade.get(); }

    }

    public static class StudentGeneralInfo {  // an object for single string returns
        private String studentNo, fullName, courseDescription, status, yearLevel;

        public StudentGeneralInfo(String studentNo, String fullName, String courseDescription, String status, String yearLevel) {
            this.studentNo = studentNo;
            this.fullName = fullName;
            this.courseDescription = courseDescription;
            this.status = status;
            this.yearLevel = yearLevel;
        }
        public String getStudentNo() { return studentNo; }
        public String getFullName() { return fullName; }
        public String getCourseDescription() { return courseDescription; }
        public String getStatus() { return status; }
        public String getYearLevel() { return yearLevel; }
    }

    public static class SchoolYearModuleInfo extends StudentEnrollmentHistory {
        public SchoolYearModuleInfo(String strSy, String strStatus) {
            super(null, null,null, null, strStatus, strSy, null, null);
        }
    }

    public static class SemesterModuleInfo extends StudentEnrollmentHistory {
        public SemesterModuleInfo(String strSemester, String strStatus) {
            super(null, null,null, null, strStatus, null, strSemester, null);
        }
    }
    public static class CourseModule {
        private final StringProperty strCourseCode, strDescription, strCollegeCode, strDateOpened, strDateClosed, strStatus;
        public CourseModule(String strCourseCode, String strDescription, String strCollegeCode,
                            String strDateOpened, String strDateClosed, String strStatus) {
            this.strCourseCode = new SimpleStringProperty(strCourseCode);
            this.strDescription = new SimpleStringProperty(strDescription);
            this.strCollegeCode = new SimpleStringProperty(strCollegeCode);
            this.strDateOpened = new SimpleStringProperty(strDateOpened);
            this.strDateClosed = new SimpleStringProperty(strDateClosed);
            this.strStatus = new SimpleStringProperty(strStatus);
        }
        public String getStrCourseCode() { return strCourseCode.get(); }
        public StringProperty strCourseCodeProperty() { return strCourseCode; }
        public String getStrDescription() { return strDescription.get(); }
        public StringProperty strDescriptionProperty() { return strDescription; }
        public String getStrCollegeCode() { return strCollegeCode.get(); }
        public StringProperty strCollegeCodeProperty() { return strCollegeCode; }
        public String getStrDateOpened() { return strDateOpened.get(); }
        public StringProperty strDateOpenedProperty() { return strDateOpened; }
        public String getStrDateClosed() { return strDateClosed.get(); }
        public StringProperty strDateClosedProperty() { return strDateClosed; }
        public String getStrStatus() { return strStatus.get(); }
        public StringProperty strStatusProperty() { return strStatus; }
    }

    public static class CollegeModule extends CourseModule {
        public CollegeModule(String strCollegeCode, String strDescription,
                             String strDateOpened, String strDateClosed, String strStatus) {
            super(null, strDescription, strCollegeCode, strDateOpened, strDateClosed, strStatus);
        }
    }

    public static class ScheduleCalendar {
        private final StringProperty strMonday, strTuesday, strWednesday, strThursday, strFriday, strSaturday, strSunday;

        public ScheduleCalendar(String strMonday, String strTuesday, String strWednesday, String strThursday, String strFriday, String strSaturday, String strSunday) {
            this.strMonday = new SimpleStringProperty(strMonday);
            this.strTuesday = new SimpleStringProperty(strTuesday);
            this.strWednesday = new SimpleStringProperty(strWednesday);
            this.strThursday = new SimpleStringProperty(strThursday);
            this.strFriday = new SimpleStringProperty(strFriday);
            this.strSaturday = new SimpleStringProperty(strSaturday);
            this.strSunday = new SimpleStringProperty(strSunday);
        }
        public StringProperty strMondayProperty() { return strMonday; }
        public StringProperty strTuesdayProperty() { return strTuesday; }
        public StringProperty strWednesdayProperty() { return strWednesday; }
        public StringProperty strThursdayProperty() { return strThursday; }
        public StringProperty strFridayProperty() { return strFriday; }
        public StringProperty strSaturdayProperty() { return strSaturday; }
        public StringProperty strSundayProperty() { return strSunday;
        }
    }
}
