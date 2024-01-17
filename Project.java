/*------------------------------------------------------
My name: Quoc Van Khanh An Tran (Anthony)
My student number: 6778136
My course code: CSIT213
My email address: qvkat302@uowmail.edu.au
Assignment number: 3
-------------------------------------------------------*/

import java.util.*;

public class Project implements DataIO{
    private int number;
    private String title;
    private String sponsor;
    private int dNumber;
    private double budget;

    public Project(){
        number = 0;
        title = "";
        sponsor = "";
        dNumber = 0;
        budget = 0;
    }

    public Project(int number, String title, String sponsor, int dNumber, double budget){
        this.number = number;
        this.title = title;
        this.sponsor = sponsor;
        this.dNumber = dNumber;
        this.budget = budget;
    }

    public int getNumber(){
        return number;
    }

    public void setNumber(int newNum){
        number = newNum;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String newTi){
        title = newTi;
    }

    public String getSponsor(){
        return sponsor;
    }

    public void setSponsor(String newSpon){
        sponsor = newSpon;
    }

    public int getDNumber(){
        return dNumber;
    }

    public void setDNumber(int newNum){
        dNumber = newNum;
    }

    public double getBudget(){
        return budget;
    }

    public void setBudget(double newBud){
        budget = newBud;
    }

    public void dataInput(Scanner sc){
        try{
            number = sc.nextInt();
            title = sc.next();
            sponsor = sc.next();
            dNumber = sc.nextInt();
            budget = sc.nextDouble();
        }
        catch(InputMismatchException err){
            System.out.println("Cannot read file.");
        }
    }

    public void dataOutput(Formatter fm){
        fm.format("%s\n", toString());
    }

    public String toString(){
        String s = String.format("%d, %s, %s, %d, %.0f", number, title, sponsor, dNumber, budget);
        return s;
    }
}
