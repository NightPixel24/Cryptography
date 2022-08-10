/************************
 *Author: Joshua Rangan
 *Date: 02/04/2020
 *Name: Lehmann
 * *********************/
/*
 * NOTES: TO TEST WHETHER INPUT NUM IS PRIME OR NOT
 *        - IF 1 or -1, 50% chance its prime, else not prime 
 * BUGS: none
 *
 */

 import java.util.*;
 import java.io.*;
 public class Lehmann
 {
     public static void main(String[] args)
     {
	 //TEST PRINT
         System.out.println(leCalc(13)); 
     }

     //TEST WHETHER INT P IS PRIME
     public static int leCalc(int p)
     {
	 //BLANK OR INVALID INPUT
	 int num = 0;
	 num = p;

         int result = 0;

	 //GENERATE RANDOM NUM < P
	 Random rand = new Random();
         int randNum = rand.nextInt(num - 1) + 1;
         
         //FORMULA CALC
	 int exp = ((num - 1) / 2);
	 int pow = (int) Math.pow(randNum, exp);
         result = pow % num;
	 
	 return result;
     }



 }
