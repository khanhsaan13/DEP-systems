/*------------------------------------------------------
My name: Quoc Van Khanh An Tran (Anthony)
My student number: 6778136
My course code: CSIT213
My email address: qvkat302@uowmail.edu.au
Assignment number: 3
-------------------------------------------------------*/

import java.util.*;

public class Employee implements DataIO{
    private String type;
    private int number;
    private String name;
    private String dob;
    private String address;
    private String gender;
    private double salary;
    private int supervisor;
    private int dNumber;

    public Employee(){
        number = 0;
        name = "";
        dob = "";
        address = "";
        gender = "";
        salary = 0;
        supervisor = 0;
        dNumber = 0;
    }

    public Employee(String type, int number, String name, String dob, String address, String gender, double salary, int supervisor, int dNumber){
        this.type = type;
        this.number = number;
        this.name = name;
        this.dNumber = dNumber;
        this.dob = dob;
        this.address = address;
        this.gender = gender;
        this.salary = salary;
        this.supervisor = supervisor;
    }

    public String getTypes(){
        return type;
    }

    public void setType(String newTy){
        this.type = newTy;
    }

    public int getNumber(){
        return number;
    }

    public void setNumber(int newNum){
        this.number = newNum;
    }

    public String getName(){
        return name;
    }

    public void setName(String newName){
        this.name = newName;
    }

    public String getDob(){
        return dob;
    }

    public void setDob(String newDob){
        this.dob = newDob;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress(String newAddress){
        this.address = newAddress;
    }

    public String getGender(){
        return gender;
    }

    public void setGender(String newGen){
        this.gender = newGen;
    }

    public double getSalary(){
        return salary;
    }

    public void setSalary(double newSa){
        this.salary = newSa;
    }

    public int getSupervisor(){
        return supervisor;
    }

    public void setSupervisor(int newsup){
        this.supervisor = newsup;
    }

    public int getDNumber(){
        return dNumber;
    }

    public void setDNumber(int newD){
        this.dNumber = newD;
    }

    // interface methods
    public void dataInput(Scanner sc){
        try{
            type = sc.next();
            number = sc.nextInt();
            name = sc.next();
            dob = sc.next();
            address = sc.next();
            gender = sc.next();
            salary = sc.nextDouble();
            supervisor = sc.nextInt();
            dNumber = sc.nextInt();
        }   
        catch(InputMismatchException error){
            System.out.println(error);
        }
    }

    public void dataOutput(Formatter fm){
        fm.format("%s\n", toString());
    }

    public String toString(){
        String s = String.format("%s, %d, %s, %s, %s, %s, %.1f, %d, %d", type, number, name, dob, address, gender, salary, supervisor, dNumber);

        return s;
    }
}
class Admin extends Employee{
    private String skills;

    public Admin(){
        skills = "";
    }

    public Admin(String type, int number, String name, String dob, String address, String gender, double salary, int supervisor, int dNumber, String skills){
        super(type, number, name, dob, address, gender, salary, supervisor, dNumber);
        this.skills = skills;
    }

    public String getSkills(){
        return skills;
    }

    public int getNumber(){
        return super.getNumber();
    }

    @Override
    public void dataInput(Scanner sc){
        try{
            super.dataInput(sc);
            skills = sc.next();
        }
        catch(InputMismatchException err){
            System.out.println("Cannot read file.");
        }
    }

    @Override
    public void dataOutput(Formatter fm){
        fm.format("%s\n", toString());
    }

    @Override
    public String toString(){
        String s = String.format("%s, %s", super.toString(), skills);
        return s;
    }
}

class Developer extends Employee{
    private String languages;

    public Developer(){
        languages = "";
    }

    public Developer(String type, int number, String name, String dob, String address, String gender, double salary, int supervisor, int dNumber, String languages){
        super(type, number, name, dob, address, gender, salary, supervisor, dNumber);
        this.languages = languages;
    }

    public String getLanguages(){
        return languages;
    }

    public int getNumber(){
        return super.getNumber();
    }

    @Override
    public void dataInput(Scanner sc){
        try{
            super.dataInput(sc);
            languages = sc.next();
        }
        catch(InputMismatchException err){
            System.out.println("Cannot read file.");
        }
    }

    @Override 
    public void dataOutput(Formatter fm){
        fm.format("%s\n", toString());
    }

    @Override
    public String toString(){
        String s = String.format("%s, %s", super.toString(), languages);

        return s;
    }
}

