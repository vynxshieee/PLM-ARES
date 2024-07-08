# PLM-ARES
#### Pamantasan ng Lungsod ng Maynila - Academic Records and Enrollment System
PLM-ARES is a standalone Java application built using JavaFX, featuring a local MySQL database connection. It provides a streamlined solution for student enrollment processes, specifically designed for educational institutions' use. The system can be adapted to enrollment systems with regular and irregular students.


### [A] Features & Modules
##### View full documentation here: [PLM_ARES-Documentation](https://drive.google.com/file/d/1SWUJvZwKT2z2lGe16KNvcIyArUCstr_e/view?usp=sharing)

1. **Login Page**

2. **Admin-side Modules**
    - 2.1 Admin Information Module
    - 2.2 Application Module
        * 2.2.1 Subject Application Submodule
    - 2.3 Enrollment Module
        * 2.3.1 Approvals Console Submodule
           + 2.3.1.1 Subjects Approvals Submodule 
        * 2.3.2 Subjects-Specific Enrollees Submodule
        * 2.3.3 Enrollees Masterlist Submodule
           + 2.3.3.1 View Schedule Submodule
    - 2.4 Enrollment History
    - 2.5 Student Masterlist
    - 2.6 Student Records Module
    - 2.7 Employee Masterlist Module
    - 2.8 Classlist Generator Module
        * 2.8.1 Subject-Section Classlist Submodule
        * 2.8.2 General Classlist Submodule
   - 2.9 Grade Entry Module
   - 2.10 Subject & Scheduling Module 
        * 2.10.1 Subject Module
        * 2.10.2 Schedule Module 
   - 2.11 Course Module
   - 2.12 College Module
   - 2.13 Semester Module
   - 2.14 School Year Module

3. **Student-side Modules**

   - 3.1 Student Information Module
   - 3.2 Dashboard Module
   - 3.3 Enlistment Module
   - 3.4 Tuition Module
   - 3.5 Registration Module
   - 3.6 Schedule Module
   - 3.7 Grades Module
   - 3.8 Subjects Catalog
   - 3.9 Curriculums Catalog


### [B] Installation and Usage

**1. Install requirements- Make sure to have the following installed:**
   - [MySQL Server 8.2](https://dev.mysql.com/downloads/mysql/) or newer installed locally.
   - [Java SDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) or newer.

**2. Set Up Local Database**
   - Import the provided SQL dump into your MySQL Server from the latest release [here](https://github.com/vynxshieee/PLM-ARES/releases/tag/v.1.0.0).
   - Ensure the database credentials are set to "root" as the username and "password" as the password.
   - Run the SQL dump.

**3. Download the application**
   - Download PLM-ARES jar file from the latest release [here](https://github.com/vynxshieee/PLM-ARES/releases/tag/v.1.0.0).

**4. Run the application**
   - Execute the JAR file.
   - Upon running the app, you will be directed to the login page. Enter any credentials from the `login_credential` table to login.
      * For simplicity's sake, you may login with the following:
      
         + **Admin**:
            - Username: E097
            - Password: password

         + **Student (*Regular*)**:
            - Username: 2019-20001
            - Password: P@$$w0Rd123

         + **Student (*Irregular*)**
            - Username: 2019-20001
            - Password: password

### [C] Screenshots
Here are some screenshots from PLM-ARES. To see more, download the app or view the [full documentation](https://drive.google.com/file/d/1SWUJvZwKT2z2lGe16KNvcIyArUCstr_e/view?usp=sharing).

<center>
<img style="width:500px" alt="ss1" src="https://imgur.com/unqOvxc"><br>
<img style="width:500px" alt="ss2" src="https://imgur.com/5xfDPAX"><br>
<img style="width:500px" alt="ss3" src="https://imgur.com/2QVezC8"><br>
<img style="width:500px" alt="ss4" src="https://imgur.com/Yb6x7OW"><br>
<img style="width:500px" alt="ss5" src="https://imgur.com/Atsvt6g"><br>
<img style="width:500px" alt="ss6" src="https://imgur.com/mpZI8ws"><br>
<img style="width:500px" alt="ss7" src="https://imgur.com/asDps5m"><br>
</center>
