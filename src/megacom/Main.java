package megacom;

import megacom.service.StudSubjectService;
import megacom.service.impl.StudSubjectServiceImpl;

public class Main {

    public static void main(String[] args) {
        StudSubjectService studSubjectService = new StudSubjectServiceImpl();
        studSubjectService.ConnectionSQLite(); // connection to DB
        //studSubjectService.addSubject(); // add subject to DB
        studSubjectService.selectSubject(); // get subject table from DB*/
        //studSubjectService.addGroup(); // add group to DB
        studSubjectService.selectGroup(); // get groups table from DB
        //studSubjectService.addSubgroups();
        studSubjectService.selectSubgroups();
        studSubjectService.addStudent();
        studSubjectService.selectStudents();
        studSubjectService.close(); // close connetion DB
    }
}
