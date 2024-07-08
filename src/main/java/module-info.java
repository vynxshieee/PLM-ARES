module com.example.plmares {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires org.apache.pdfbox;
    requires javafx.web;
    requires javax.mail.api;

    exports com.OOP.plmares.controllers;
    opens com.OOP.plmares.controllers to javafx.fxml;

    exports com.OOP.plmares.views;
    opens com.OOP.plmares.views to javafx.fxml;

    exports com.OOP.plmares.controllers.student_system;
    opens com.OOP.plmares.controllers.student_system to javafx.fxml;

    exports com.OOP.plmares.controllers.admin_system;
    opens com.OOP.plmares.controllers.admin_system to javafx.fxml;

    exports com.OOP.plmares.controllers.utilities;
    opens com.OOP.plmares.controllers.utilities to javafx.fxml;

    exports com.OOP.plmares.controllers.admin_system.StudentMasterlistControllers;
    opens com.OOP.plmares.controllers.admin_system.StudentMasterlistControllers to javafx.fxml;

    exports com.OOP.plmares.controllers.admin_system.ApplicationModuleControllers;
    opens com.OOP.plmares.controllers.admin_system.ApplicationModuleControllers to javafx.fxml;

    exports com.OOP.plmares.controllers.admin_system.EnrollmentModuleControllers;
    opens com.OOP.plmares.controllers.admin_system.EnrollmentModuleControllers to javafx.fxml;

    exports com.OOP.plmares.controllers.admin_system.EmployeeMasterlistControllers;
    opens com.OOP.plmares.controllers.admin_system.EmployeeMasterlistControllers to javafx.fxml;

    exports com.OOP.plmares.controllers.admin_system.ClasslistGeneratorControllers;
    opens com.OOP.plmares.controllers.admin_system.ClasslistGeneratorControllers to javafx.fxml;

    exports com.OOP.plmares.controllers.admin_system.SubjectModuleControllers;
    opens com.OOP.plmares.controllers.admin_system.SubjectModuleControllers to javafx.fxml;

    exports com.OOP.plmares.controllers.admin_system.CourseModule;
    opens com.OOP.plmares.controllers.admin_system.CourseModule to javafx.fxml;

    exports com.OOP.plmares.controllers.admin_system.CollegeModule;
    opens com.OOP.plmares.controllers.admin_system.CollegeModule to javafx.fxml;
    exports com.OOP.plmares.views.DataGenerators;
    opens com.OOP.plmares.views.DataGenerators to javafx.fxml;

}