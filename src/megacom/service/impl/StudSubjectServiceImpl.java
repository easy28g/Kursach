package megacom.service.impl;

import megacom.models.Groups;
import megacom.models.Students;
import megacom.models.Subgroups;
import megacom.models.Subjects;
import megacom.service.StudSubjectService;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class StudSubjectServiceImpl implements StudSubjectService {

    Scanner scanner = new Scanner(System.in);
    Connection connection;
    Statement statement;

    // Добавляем предмет
    @Override
    public void addSubject() {

        String subject = null;
        try {
            System.out.println("Существующие предметы в базе");
            selectSubject();

            System.out.print("Введите название предмета - ");
            subject = scanner.next();

            Subjects subjects = new Subjects(subject); // Экземпляр класса

            String query = "INSERT INTO subject(subject_name)" +
                    "VALUES ('" + subjects.getSubject() + "')";

            statement = connection.createStatement();
            statement.executeUpdate(query);


        }catch (Exception e){
            System.out.print("Предмет "+subject+" уже существует в базе");
        }

    }

    // Вывод таблицы Предмета
    @Override
    public void selectSubject() {
        try {
            statement  = connection.createStatement ();
            String query = "SELECT id, subject_name FROM subject";
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("subject_name");
                System.out.println(id+ "\t|" + name);
            }
            rs.close();
            statement.close();
            //connection.close();
        }catch(Exception e)
        {
            System.out.print(e.getMessage());
        }
    }

    @Override
    public void ConnectionSQLite() {
        Connection connection = getConnection();
        System.out.println("connection");

    }

    @Override
    public void close() {
        try{
            connection.close();
            System.out.println("Close connection");
        }catch(Exception e)
        {
            System.out.print(e.getMessage());
        }
    }

    // Добавлние студента
    @Override
    public void addStudent() {
        int n = 1;
        do {
            if(n==1) {
                try {
                    statement = connection.createStatement();

                    System.out.print("Введите имя студента: ");
                    String name = scanner.next();
                    System.out.print("Введите фамилию студента: ");
                    String fam = scanner.next();

                    // Вывод названий групп для удобства выбора пользователя
                    String query1 = "SELECT id, group_name FROM groups";
                    ResultSet rs1 = statement.executeQuery(query1);
                    while (rs1.next()) {
                        int id1 = rs1.getInt("id");
                        String name1 = rs1.getString("group_name");
                        System.out.println(id1 + "\t|" + name1);
                    }
                    //rs1.close();

                    System.out.print("Выберите группу: ");
                    String group = scanner.next();

                    int id = id_subgroups(group);
                    int id_sg = id_group(id, group);

                    Students students = new Students(name, fam, id_sg);

                    String query = "INSERT INTO students(firstname, secondname, id_subgroups)" +
                            " VALUES('" + students.getFirstname() + "','" + students.getSecondname() + "','" + students.getId_subgroup() + "')";

                    statement.executeUpdate(query);
                    statement.close();

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                System.out.println("1. Добавть еще одного студента \n" +
                                   "2. Выход из Добавления студента (Любая кнопка)");
                System.out.print("Выберите команду: ");
                n = scanner.nextInt();

            } else {
                break;
            }

        }while (true);
    }

    // Функция получения ID из таблицы subgroup
    private int id_group(int id, String groupName) {
        try{
            statement = connection.createStatement();
            String query = "select subgroups.id, subgroups.subgroup_name from subgroups " +
                    "inner join groups on groups.id = subgroups.id_group where groups.group_name = '"+groupName+"' ";

            ResultSet rs = statement.executeQuery(query);

            ArrayList<String> subgroup_names = new ArrayList<>();
            int i=0;
            while(rs.next()){
                int id_sg = rs.getInt("id");
                subgroup_names.add(rs.getString("subgroup_name"));
                System.out.println(id_sg + " | "+ subgroup_names.get(i));
                i++;
            }

            Statement statement1 = connection.createStatement();
            System.out.print("Введите название подгруппы - ");
            String subg = scanner.next();
            for(int j=0; j<subgroup_names.size(); j++){
                if (subgroup_names.get(j).equals(subg)) {
                    String query2 = "SELECT id FROM subgroups WHERE subgroup_name = '"+subg+"' AND" +
                            " id_group = '"+id+"'";
                    ResultSet rsSG = statement1.executeQuery(query2);
                    while (rsSG.next()){
                        int idsg = rsSG.getInt("id");
                        return idsg;
                    }
                }
            }

            rs.close();
            statement.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        throw new RuntimeException("Такой подгруппы нет");
    }

    @Override
    public void selectStudents() {
       selectStudentsFunction();
    }
    private void selectStudentsFunction() {
        try {
            statement  = connection.createStatement ();
            String query = "SELECT id, firstname, secondname, id_subgroups FROM students";
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("firstname");
                String fam = rs.getString("secondname");
                int id_sg = rs.getInt("id_subgroups");
                System.out.println(id+ "\t|"+ name+ "\t|"+fam+ "\t|"+id_sg);
            }
            rs.close();
            statement.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    // Добавляем Группу
    @Override
    public void addGroup() {
        String group = null;
        try{
            System.out.println("Доступные группы в базе");
            selectGroup();
            System.out.print("Введите название группы - ");
            group = scanner.next();

            Groups groups = new Groups(group);

            String query = "INSERT INTO groups(group_name) " +
                    "VALUES ('" + groups.getGroupName() + "')";

            statement = connection.createStatement();
            statement.executeUpdate(query);

        }catch (Exception e){
            System.out.println(group + " уже существует");
        }
    }

    // Вывод таблицы Групп
    @Override
    public void selectGroup() {
        //System.out.println("Таблица Групп:");
        try{
            statement  = connection.createStatement ();
            String query = "SELECT id, group_name FROM groups";
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("group_name");
                System.out.println(id+ "\t|"+ name);
            }
            rs.close();
            statement.close();
            //connection.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    // Добавляем Подгруппу
    @Override
    public void addSubgroups() {
        try{
            System.out.println("Доступные группы в базе");
            selectGroup();

            System.out.print("Введите название группы - ");
            String groupname = scanner.next();

            // Вывод названий подгрупп для удобства выбора
            String query3 = "SELECT subgroups.id, subgroup_name FROM subgroups " +
                    "   INNER JOIN groups ON groups.id = subgroups.id_group WHERE groups.group_name = '"+groupname+"'";
            ResultSet rs3 = statement.executeQuery(query3);
            ArrayList<String> subgroups_name = new ArrayList<>();
            int i=0;
            while (rs3.next()) {
                int id3 = rs3.getInt("id");
                String name3 = rs3.getString("subgroup_name");
                subgroups_name.add(name3);
                //System.out.println(subgroups_name.get(i));
                System.out.println(id3 + "\t|" + name3);
                i++;
            }

            System.out.print("Введите название подгруппы - ");
            String subgroup = scanner.next();

            for(String item: subgroups_name){
                if(item.equals(subgroup)){
                    throw new RuntimeException("Подгруппа "+subgroup+" уже существует в группе "+ groupname);
                }
            }

            int id = id_subgroups(groupname);

            Subgroups subgroups = new Subgroups(subgroup, id_subgroups(groupname));

            String query = "INSERT INTO subgroups(subgroup_name, id_group) "+
                    "VALUES ('"+subgroups.getSubgroupName()+"', '" + id_subgroups(groupname) + "')";

            statement = connection.createStatement();
            statement.executeUpdate(query);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    // Вывд Подгруппы
    @Override
    public void selectSubgroups() {
        //System.out.println("Таблица подгрупп: ");
        try{
            statement  = connection.createStatement ();
            String query = "SELECT id, subgroup_name, id_group FROM subgroups";
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("subgroup_name");
                System.out.println(id+ "\t|"+ name);
            }
            rs.close();
            statement.close();
            //connection.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void selectStudentsFunctionFromGroup(String group) {
        try {
            statement  = connection.createStatement ();
            String query = "SELECT students.id, firstname, secondname FROM students " +
                    " INNER JOIN subgroups ON subgroups.id = students.id_subgroups " +
                    " INNER JOIN groups ON groups.id = subgroups.id_group WHERE groups.group_name = '"+group+"'";
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()) {
                int id_ssffg = rs.getInt("id");
                String name = rs.getString("firstname");
                String fam = rs.getString("secondname");
                System.out.println(id_ssffg+ "\t|"+ name+ "\t|"+fam);
            }
            rs.close();
            statement.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    // Заполнение тадлицы group_student_subject
    @Override
    public void fill_group_student_subject() {

        int n = 1;
        do {
            if(n==1) {
                selectSubject();
                System.out.print("Введите предмет - ");
                String subj = scanner.next();

                selectGroup();
                System.out.print("Введите группу - ");
                String group = scanner.next();

                selectStudentsFunctionFromGroup(group);
                
                System.out.print("Введите имя студента - ");
                String fam = scanner.next();
                System.out.print("Введите фамилию студента - ");
                String imya = scanner.next();
                
                selectNameStudentsFunctionFromSubject();


                int idstud = getIdStudent(imya, fam);
                try {
                    if(catchEqualStudentId(idstud)) {

                        statement = connection.createStatement();
                        int id_subj = getIdSubject(subj);
                        int id_group = getIdGroup(group);
                        int id_fio = getIdStudent(imya, fam);

                        String query = "INSERT INTO group_student_subject(id_student, id_subject, id_group) " +
                                " VALUES('" + id_fio + "', '" + id_subj + "', '" + id_group + "')";

                        statement = connection.createStatement();
                        statement.executeUpdate(query);
                    }
                } catch (Exception r) {
                        System.out.println(fam + " " + imya + " уже зарегистрирован на данный предмет!!!");
                }

                System.out.println("1. Зарегистрировать еще одного студента на предмет \n" +
                                   "2. Выход из Регистрации студента (Любая кнопка)");
                System.out.print("Выберите команду: ");
                n = scanner.nextInt();

            } else {
                break;
            }

        }while (true);

    }

    private boolean catchEqualStudentId(int idget) {
        ArrayList<Integer> idStudents = new ArrayList<>();
        int id=0;
        try{
            statement = connection.createStatement();
            String query = "SELECT id_student FROM group_student_subject";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                id = rs.getInt("id_student");
                idStudents.add(id);
            }
            for(Integer item: idStudents){
                if(item == idget){
                    return true;
                }
            }
            statement.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }


    private void selectNameStudentsFunctionFromSubject() {
    }

    @Override
    public void addAttendance() {
        System.out.println("Доступные предметы");
        selectSubject();
        System.out.print("Введите предмет - ");
        String subj = scanner.next();

        System.out.println("Доступные группы");
        selectGroup();

        System.out.print("Введите группу - ");
        String group = scanner.next();
        selectStudentsFunctionFromGroup(group);

        System.out.print("Введите имя студента - ");
        String fam = scanner.next();
        System.out.print("Введите фамилию студента - ");
        String imya = scanner.next();

        try {

            statement = connection.createStatement();
            int id_subj = getIdSubject(subj);
            int id_group = getIdGroup(group);
            int id_fio = getIdStudent(imya, fam);

            int id_gss = getIdGSS(id_subj, id_group, id_fio);
            String yn;
            do {
                System.out.println("Отметить Успеваемость/Посещаемость?  -  (y/n) ");
                yn = scanner.next();
                if(yn.equals("n")){
                    break;
                }
                addMarkAndCheckedStud(id_gss);
            }while(true);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException("Запос addAttendace не выполнен");
        }

    }

    @Override
    public void totalAcademicAttendance() {

        System.out.println("Доступные предметы: ");
        selectSubject();

        System.out.print("Введите название предмета: ");
        String subjName = scanner.next();

        System.out.println("Доступные группы: ");
        selectGroup();

        System.out.print("Введите группу: ");
        String groupName = scanner.next();
        //selectRegStudentInGroup(groupName);
        selectStudentsFunctionFromGroup(groupName);

        System.out.print("Введите имя студента: ");
        String name = scanner.next();

        System.out.print("Введите фамилию студента: ");
        String fam = scanner.next();

        // SELECT avg (mark) FROM attendance WHERE id_group_student_subject = 3



    }

    private void addMarkAndCheckedStud(int id_gss) {
        System.out.print("Введите тип занаятия - ");
        String lessonType = scanner.next();

        int check;
        //do {
            System.out.print("Посещаемость 1-был(а) | 0-не был(а) ==> ");
            check = scanner.nextInt();
        //}while (check>=0 || check<=1);

        System.out.print("Поставить оценку? 1-да | 0-нет ==> ");
        int yn = scanner.nextInt();
        int mark=0;
        if(yn==1){
            //do{
                System.out.print("Оценка (1-5) ==> ");
                mark = scanner.nextInt();
            //}while(mark>=1 || mark<=5);
        }

        System.out.println("Дата занятия");
        int month;
        //do{
            System.out.print("Месяц: ");
            month = scanner.nextInt();
        //}while(month>=1 || month<=12);

        // Надо доработать количество дней
        int day;
        //do{
            System.out.print("День: ");
            day = scanner.nextInt();
        //}while(day>=1 || day<=31);

        // Сделать с календарем
        //Calendar calendar = Calendar.getInstance();
        int year;
        //do{
            System.out.print("Год: ");
            year = scanner.nextInt();
        //}while (year>2000);

        String dd = String.valueOf(day+"."+month+"."+year);

        try{

            String query = "INSERT INTO attendance(check_stud, mark, lesson_date, id_group_student_subject, lesson_type) "+
                    "VALUES ('"+check+"', '"+mark+"', '"+dd+"', '"+id_gss+"', '"+lessonType+"')";

            statement = connection.createStatement();
            statement.executeUpdate(query);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    // Функция получения ID из таблицы GSS
    // для метода addAttendance()
    private int getIdGSS(int id_subj, int id_group, int id_fio) {
        try{
            statement  = connection.createStatement ();

            String query = "SELECT id FROM group_student_subject " +
                    " WHERE id_student = '"+id_fio+"' AND " +
                    " id_subject = '"+id_subj+"' AND " +
                    " id_group = '"+id_group+"' ";
            ResultSet rs = statement.executeQuery(query);

            int id = rs.getInt("id");
            return id;
            //connection.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        throw new RuntimeException("Запрос addAttendance в функции getIdGSS");
    }

    // Функция получение ID из таблицы СТУДЕНТЫ
    // для таблцы group_student_subject
    // для метода fill_groups_student_subject
    private int getIdStudent(String imya, String fam) {
        try {
            statement = connection.createStatement();
            String query = "SELECT id FROM students WHERE firstname = '"+fam+"' " +
                    " AND secondname = '"+imya+"'";
            ResultSet rs = statement.executeQuery(query);
            int id = rs.getInt("id");
            return id;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new RuntimeException("Такого студента нет");
    }

    // Функция получение ID из таблицы ГРУППЫ
    // для таблцы group_student_subject
    // для метода fill_groups_student_subject
    private int getIdGroup(String group) {
        try {
            statement = connection.createStatement();
            String query = "SELECT id FROM groups WHERE group_name = '"+group+"'";
            ResultSet rs = statement.executeQuery(query);
            int id = rs.getInt("id");
            return id;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new RuntimeException("Такой группы нет");
    }

    // Функция получение ID из таблицы ПРЕДМЕТА
    // для таблцы group_student_subject
    // для метода fill_groups_student_subject
    private int getIdSubject(String subj) {
        try {
            statement = connection.createStatement();
            String query = "SELECT id FROM subject WHERE subject_name = '"+subj+"'";
            ResultSet rs = statement.executeQuery(query);
            int id = rs.getInt("id");
            return id;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new RuntimeException("Такого предмета нет");
    }

    // Функция получения ID из таблицы Групп
    private int id_subgroups(String groupname) {
        int id = 0;
        try{

            statement  = connection.createStatement ();
            String query = "SELECT id FROM groups "+
                    " WHERE groups.group_name ='"+groupname+"'";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                id = rs.getInt("id");
                //System.out.println(id);
                return id;
            }

            rs.close();
            statement.close();

            //connection.close();
        }catch (Exception e){
            System.out.println(e.getMessage());

        }
        throw new RuntimeException("Нет группы с таким названием!");
    }

    private Connection getConnection() {

        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(
                    "jdbc:sqlite:D:\\Azamat\\Javaitschool\\Kursach\\sqlite3\\stud_reg.db");
            System.out.println("Connection");
            return connection;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
