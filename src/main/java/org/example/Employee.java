package org.example;

public class Employee {
    private String firstName;
    private String lastName;
    private String departmentName;

    private String skillName;
    private int age;
    public Employee(String firstName, String lastName, String departmentName, int age, String skillName){
        this.departmentName = departmentName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.skillName = skillName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public int getAge() {
        return age;
    }

    void print(){
        System.out.println(firstName + "\t" + lastName + "\t" + departmentName + "\t" + age
         + "\t" + skillName);
    }
}
