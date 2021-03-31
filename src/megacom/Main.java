package megacom;

import megacom.service.StudSubjectService;
import megacom.service.impl.StudSubjectServiceImpl;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        StudSubjectService studSubjectService = new StudSubjectServiceImpl();
        studSubjectService.ConnectionSQLite(); // connection to DB

        System.out.println("1. Добавить предмет \n" +
                "2. Создать новую группу \n" +
                "3. Создать подгруппу для группы \n" +
                "4. Добавть студента к группе \n" +
                "5. Регистрация студента на предмет \n" +
                "6. Проставить академ.успеваемость \n" +
                "7. Выход из меню (Любая кнопка)");


        do{
            System.out.print("Выберите кнопку: ");
            int n = scanner.nextInt();

            if (n == 1) {
                System.out.println("-----------------Добавление нового предмета-----------------");
                studSubjectService.addSubject(); // add subject to DB
                studSubjectService.selectSubject(); // get subject table from DB
            } else if(n == 2){
                System.out.println("-----------------Создание новой группы-----------------");
                studSubjectService.addGroup(); // add group to DB
                studSubjectService.selectGroup(); // get groups table from DB
            } else if(n == 3){
                System.out.println("-----------------Создание подрупп для группы-----------------");
                studSubjectService.addSubgroups();
                studSubjectService.selectSubgroups();
            } else if(n == 4){
                System.out.println("-----------------Добавление студента к группе-----------------");
                studSubjectService.addStudent();
                studSubjectService.selectStudents();
            } else if(n == 5){
                System.out.println("-----------------Регистрация студента на предмет-----------------");
                studSubjectService.fill_group_student_subject();
            } else if(n == 6){
                System.out.println("-----------------Проставить академ.успевамость-----------------");
                studSubjectService.addAttendance();
            } else {
                break;
            }
        }while(true);


        studSubjectService.close(); // close connetion DB
    }
}
