/*------------------------------------------------------
My name: Quoc Van Khanh An Tran (Anthony)
My student number: 6778136
My course code: CSIT213
My email address: qvkat302@uowmail.edu.au
Assignment number: 3
-------------------------------------------------------*/

import java.util.*;

public class Department implements DataIO{
    private int number;
    private String name;
    private int manager;
    private double budget;
    private String startDate;

    public Department(){
        number = 0;
        name = "";
        manager = 0;
        budget = 0;
        startDate = "";
    }

    public Department(int number, String name, int manager, double budget, String startDate){
        this.number = number;
        this.name = name;
        this.manager = manager;
        this.budget = budget;
        this.startDate = startDate;
    }

    public int getNumber(){
        return number;
    }

    public void setNumber(int newNum){
        number = newNum;
    }

    public String getName(){
        return name;
    }

    public void setName(String newName){
        name = newName;
    }

    public int getManager(){
        return manager;
    }

    public void setManager(int newMan){
        manager = newMan;
    }

    public double getBudget(){
        return budget;
    }

    public void setBudget(double newBud){
        budget = newBud;
    }

    public String getStartDate(){
        return startDate;
    }

    public void setStartDate(String newDate){
        startDate = newDate;
    }

    public void dataInput(Scanner sc){
        try{
            number = sc.nextInt();
            name = sc.next();
            manager = sc.nextInt();
            budget = sc.nextDouble();
            startDate = sc.next();
        }
        catch(InputMismatchException err){
            System.out.println("Cannot read file.");
        }
    }

    public void dataOutput(Formatter fm){
        fm.format("%s\n", toString());
    }

    public String toString(){
        String s = String.format("%d, %s, %d, %.2f, %s", number, name, manager, budget, startDate);
        return s;
    }
}

