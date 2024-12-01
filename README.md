
# Gestion de Scolarité (J2EE Project)

This project is a web-based school management system developed using J2EE technologies. It allows students, teachers, and administrators to manage academic information efficiently. The application includes functionalities for managing students, courses, enrollments, grades, and authentication.

## Features

### Students
- Registration and updating of student information (name, date of birth, contact details).
- Viewing student lists and details.
- Searching and filtering students.

### Teachers
- Registration and updating of teacher information.
- Assigning teachers to courses.
- Viewing teacher details.

### Courses
- Creating, modifying, and deleting courses.
- Viewing course lists.
- Assigning courses to teachers and students.

### Enrollment
- Enrolling students in courses.
- Viewing courses for each student.

### Grades
- Teachers can input grades for students.
- Viewing grades and calculating averages.
- Students can view their results.

### Authentication and Authorization
- User roles: Student, Teacher, and Administrator.
- Secure access to functionalities based on user roles.

## Architecture

The project follows the **MVC (Model-View-Controller)** architecture:

- **Model**: Java classes representing entities (Student, Teacher, Course, etc.).
- **View**: JSP pages for user interfaces.
- **Controller**: Servlets to handle HTTP requests and business logic.

### Technologies Used

- **Frontend**: HTML, CSS, JavaScript
- **Backend**: Java (J2EE)
- **Frameworks**: Hibernate, Spring Boot (for advanced version)
- **Database**: MySQL
- **Server**: Apache Tomcat

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/abdemeh/gestionScolarite_JEE.git
   ```
2. Set up the database:
    - Import the schema and initial data from the `sql/schema.sql` file.

3. Configure the project:
    - Update `hibernate.cfg.xml` with your database credentials (root and password).
    - Deploy the project on Apache Tomcat (v10.1.33).

4. Run the application:
    - Access the web application at `http://localhost:8080/projetJEE`.

## Contributors

- **@abdemeh**: Abdellatif
- **@alifpdf**: Alif
- **@Harruki2k**: Gloddy
- **@mohamedLamine949**: Mohamed Lamine
- **@spytek95**: Baptiste

## Development Roadmap

### Phase 1: J2EE Implementation
- Develop CRUD operations for students, teachers, and courses.
- Implement authentication and authorization.

### Phase 2: JSP Interfaces
- Build user-friendly interfaces for each role.

### Phase 3: Add Advanced Features
- Implement email notifications.
- Add reporting features for student performance.

### Phase 4: Spring Boot Migration
- Migrate the backend to use Spring Boot for improved scalability and maintainability.

## Reporting Issues
For issues or suggestions, please open a GitHub issue in this repository.