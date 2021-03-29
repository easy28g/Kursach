package megacom;

import megacom.service.StudSubjectService;
import megacom.service.impl.StudSubjectServiceImpl;

public class Main {

    public static void main(String[] args) {
        StudSubjectService studSubjectService = new StudSubjectServiceImpl();
        studSubjectService.ConnectionSQLite(); // connection to DB
        System.out.println("-----------------Добавление нового предмета-----------------");
        //studSubjectService.addSubject(); // add subject to DB
        studSubjectService.selectSubject(); // get subject table from DB*/
        System.out.println("-----------------Создание новой группы-----------------");
        //studSubjectService.addGroup(); // add group to DB
        studSubjectService.selectGroup(); // get groups table from DB
        System.out.println("-----------------Создание подрупп для группы-----------------");
        //studSubjectService.addSubgroups();
        studSubjectService.selectSubgroups();
        System.out.println("-----------------Добавление студента к группе-----------------");
        //studSubjectService.addStudent();
        //studSubjectService.selectStudents();
        studSubjectService.close(); // close connetion DB
    }
}
