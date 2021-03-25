package megacom.service.impl;

import megacom.service.StudSubjectService;

import java.sql.*;
import java.util.Scanner;

public class StudSubjectServiceImpl implements StudSubjectService {

    Scanner scanner = new Scanner(System.in);
    Connection connection=null;

    @Override
    public void addSubject() {

        try {
            System.out.print("Введите название предмета - ");
            String subject = scanner.next();

            String query = "INSERT INTO subject(subject_name)" +
                    "VALUES ('" + subject + "')";

            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        }catch (Exception e){
            System.out.print(e.getMessage());
        }

    }

    @Override
    public void selectSubject() {
        try {
            Statement statement  = connection.createStatement ();
            String query = "SELECT id, subject_name FROM subject";
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("subject_name");
                System.out.println(id+ "\t|" + name);
            }
            rs.close();
            statement.close();
            connection.close();
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
        }catch(Exception e)
        {
            System.out.print(e.getMessage());
        }
    }

    private Connection getConnection() {

        try{
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection(
                    "jdbc:sqlite:D:\\Azamat\\Javaitschool\\Kursach\\sqlite3\\stud_reg.db");
            System.out.println("Connection");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
