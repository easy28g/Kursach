package megacom.service;

public interface StudSubjectService {

    void addSubject();
    void selectSubject();

    void ConnectionSQLite();
    void close();

    void addStudent();
    void selectStudents();

    void addGroup();
    void selectGroup();

    void addSubgroups();
    void selectSubgroups();

    void fill_group_student_subject();

    void addAttendance();

    void totalAcademicAttendance();

}
