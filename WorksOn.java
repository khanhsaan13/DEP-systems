/*------------------------------------------------------
My name: Quoc Van Khanh An Tran (Anthony)
My student number: 6778136
My course code: CSIT213
My email address: qvkat302@uowmail.edu.au
Assignment number: 3
-------------------------------------------------------*/

import java.util.*;

public class WorksOn implements DataIO {
    private int eNumber;
    private int pNumber;
    private int hours;

    public WorksOn(){
        eNumber = 0;
        pNumber = 0;
        hours = 0;
    }

    public WorksOn(int eNumber, int pNumber, int hours){
        this.eNumber = eNumber;
        this.pNumber = pNumber;
        this.hours = hours;
    }

    public int getENumber(){
        return eNumber;
    }

    public void setENumber(int num){
        this.eNumber = num;
    }

    public int getPNumber(){
        return pNumber;
    }

    public void setPNumber(int num){
        this.pNumber = num;
    }

    public int getHours(){
        return hours;
    }

    public void setHours(int num){
        this.hours = num;
    }

    public void dataInput(Scanner sc){
        try{
            eNumber = sc.nextInt();
            pNumber = sc.nextInt();
            hours = sc.nextInt();
        }
        catch(InputMismatchException err){
            System.out.println("Cannot read file.");
        }
    }

    public void dataOutput(Formatter fm){
        fm.format("%s\n", toString());
    }

    public String toString(){
        String s = String.format("%d, %d, %d", eNumber, pNumber, hours);
        return s;
    }
}
