package org.example;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Взяв за основу пример, написать программу,
 * добавить поле Salay d таблицу PersonalData
 * выбрать всех работников конкретного департамента,
 * а также рассчитать минимальную, максимальную и среднюю зарплату

 * Q3TZWnFR5SGVDtv
 * dima-admin
 * jdbc:sqlserver://dmitry-test.database.windows.net:1433;database=test_database;user=dima-admin@dmitry-test;password={your_password_here};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;
 */
public class SqlObjectReader {
    public static void main(String[] args) {

        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println("Class found");
        } catch (ClassNotFoundException ex) {
            System.out.println("Not found:" + ex.getMessage());
            return;
        }
        Set<Employee> employees = new HashSet<>();
        try (Statement statement = DriverManager
                .getConnection("jdbc:sqlite:C:\\Users\\Dzmitry_Shykunou\\Downloads\\SQLiteDatabaseBrowserPortable\\Employees.db")
                .createStatement()) {
            String query = "SELECT DISTINCT FirstName, LastName,DateOfBirth,DepartmentName,SkillName,\n" +
                    "round((julianday(Date('now'))-julianday(DateOfBirth))/365) as age\n" +
                    "FROM Employee e\n" +
                    "INNER JOIN PersonalData pd on pd.EmployeeId = e.EmployeeId\n" +
                    "INNER JOIN Department d on d.DepartmentID = e.DepartmentID\n" +
                    "INNER JOIN EmployeeSkill es on es.EmployeeId = e.EmployeeId\n" +
                    "INNER JOIN Skills s on s.SkillId = es.SkillId;";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String firstName = null;
                if (resultSet.getObject("FirstName") instanceof String) {
                    firstName = resultSet.getString("FirstName");
                }
                String lastName = null;
                if (resultSet.getObject("LastName") instanceof String) {
                    lastName = resultSet.getString("LastName");
                }
                String departmentName = null;
                if (resultSet.getObject("DepartmentName") instanceof String) {
                    departmentName = resultSet.getString("DepartmentName");
                }
                String skillName = null;
                if (resultSet.getObject("SkillName") instanceof String) {
                    skillName = resultSet.getString("SkillName");
                }
                int age = 0;
                if (resultSet.getObject("age") instanceof Double) {
                    age = (int) resultSet.getDouble("age");
                }
                Employee employee = new Employee(firstName, lastName, departmentName, age, skillName);
                employees.add(employee);
            }


        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        Set<Employee> result = employees.stream().filter(employee -> employee.getAge() > 57).collect(Collectors.toSet());
        result.stream().forEach(Employee::print);
    }
}
