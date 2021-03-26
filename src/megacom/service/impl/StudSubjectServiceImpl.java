package megacom.service.impl;

import megacom.models.Groups;
import megacom.models.Subgroups;
import megacom.models.Subjects;
import megacom.service.StudSubjectService;

import java.sql.*;
import java.util.Scanner;

public class StudSubjectServiceImpl implements StudSubjectService {

    Scanner scanner = new Scanner(System.in);
    Connection connection;
    Statement statement;

    @Override
    public void addSubject() {

        try {
            System.out.print("Введите название предмета - ");
            String subject = scanner.next();

            Subjects subjects = new Subjects(subject);

            String query = "INSERT INTO subject(subject_name)" +
                    "VALUES ('" + subjects.getSubject() + "')";

            statement = connection.createStatement();
            statement.executeUpdate(query);


        }catch (Exception e){
            System.out.print(e.getMessage());
        }

    }

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

    @Override
    public void addStudent() {
        try{



        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

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

    @Override
    public void selectGroup() {
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

    @Override
    public void addSubgroups() {
        try{
            System.out.print("Введите название группы - ");
            String groupname = scanner.next();
            System.out.print("Введите название подгруппы - ");
            String subgroup = scanner.next();

            int id = id_subgroups(groupname);

            Subgroups subgroups = new Subgroups(subgroup, id);

            String query = "INSERT INTO subgroups(subgroup_name, id_group) "+
                    "VALUES ('"+subgroups.getSubgroupName()+"', '" + id_subgroups(groupname) + "')";

            statement = connection.createStatement();
            statement.executeUpdate(query);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void selectSubgroups() {
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
        return id;
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
