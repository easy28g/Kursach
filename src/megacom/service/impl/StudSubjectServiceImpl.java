package megacom.service.impl;

import megacom.models.Groups;
import megacom.models.Students;
import megacom.models.Subgroups;
import megacom.models.Subjects;
import megacom.service.StudSubjectService;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class StudSubjectServiceImpl implements StudSubjectService {

    Scanner scanner = new Scanner(System.in);
    Connection connection;
    Statement statement;

    // Добавляем предмет
    @Override
    public void addSubject() {

        try {
            System.out.print("Введите название предмета - ");
            String subject = scanner.next();

            Subjects subjects = new Subjects(subject); // Экземпляр класса

            String query = "INSERT INTO subject(subject_name)" +
                    "VALUES ('" + subjects.getSubject() + "')";

            statement = connection.createStatement();
            statement.executeUpdate(query);


        }catch (Exception e){
            System.out.print(e.getMessage());
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
        try{
            System.out.print("Введите имя студента: ");
            String name = scanner.next();
            System.out.print("Введите фамилию студента: ");
            String fam = scanner.next();
            System.out.print("Введите группу: ");
            String group = scanner.next();

            int id = id_subgroups(group);
            int id_sg = id_group(id);

            Students students = new Students(name, fam, id_sg);

            String query = "INSERT INTO students(firstname, secondname, id_subgroups)"+
                    " VALUES('"+students.getFirstname()+"','"+students.getSecondname()+"','"+students.getId_subgroup()+"')";

            Statement statement = connection.createStatement();
            statement.executeUpdate(query);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    // Функция получения ID из таблицы subgroup
    private int id_group(int id) {
        try{
            statement = connection.createStatement();
            String query = "SELECT subgroup_name FROM subgroups  ";

            ResultSet rs = statement.executeQuery(query);

            ArrayList<String> subgroup_names = new ArrayList<>();
            int i=0;
            while(rs.next()){
                subgroup_names.add(rs.getString("subgroup_name"));
                //System.out.println(subgroup_names.get(i));
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
        try{
            System.out.print("Введите название группы - ");
            String group = scanner.next();

            Groups groups = new Groups(group);

            String query = "INSERT INTO groups(group_name) " +
                    "VALUES ('" + groups.getGroupName() + "')";

            statement = connection.createStatement();
            statement.executeUpdate(query);

        }catch (Exception e){
            System.out.println(e);
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
            System.out.print("Введите название группы - ");
            String groupname = scanner.next();
            System.out.print("Введите название подгруппы - ");
            String subgroup = scanner.next();

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
