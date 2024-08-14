package org.example;

import java.sql.*;

/**
 * Hello world!
 *
 */
public class SqlReader
{
    public static void main( String[] args )
    {
        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println("Class found");
        } catch (ClassNotFoundException ex){
            System.out.println("Not found:" + ex.getMessage());
            return;
        }
        Connection connection = null;
        Statement statement = null;

        try {
            connection = DriverManager
                    .getConnection("jdbc:sqlite:C:\\Users\\Dzmitry_Shykunou\\Downloads\\SQLiteDatabaseBrowserPortable\\Employees.db");
            statement = connection.createStatement();
            String query = "SELECT * from Employee;";

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()){
                System.out.print(resultSet.getString(2));
                System.out.print("\t");
                System.out.println(resultSet.getString("LastName"));
            }
            String query2 = "SELECT DISTINCT FirstName, LastName, DateOfBirth as dob, DepartmentName, SkillName from Employee e\n" +
                    "INNER JOIN Department d on e.DepartmentId = d.DepartmentId\n" +
                    "INNER JOIN PersobnalData pd on pd.EmployeeId = e.EmployeeId\n" +
                    "INNER JOIN EmployeeSkill es on e.EmployeeId = es.EmployeeId\n" +
                    "INNER JOIN Skills s on s.SkillId = es.SkillId";
            ResultSet result2 = statement.executeQuery(query2);

            while (resultSet.next()){

                System.out.print(resultSet.getString("FirstName"));
                System.out.print("\t");
                System.out.println(resultSet.getString("LastName"));
                System.out.print("\t");
                System.out.println(resultSet.getString("dob"));
                System.out.print("\t");
                System.out.println(resultSet.getString("DepartmentName"));
                System.out.print("\t");
                System.out.println(resultSet.getString("SkillName"));
            }


        } catch (SQLException ex){
            ex.printStackTrace();
        } finally {

            try {
                if(statement!=null){
                    statement.close();
                }
                if(connection!=null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Cant close connection");
            }

        }
    }
}
