/****************************
 * Author: Joshua Rangan
 * LAST EDITED: 05/03/2020
 * NAME: LETTER FREQUENCY
 * *************************/

 /* NOTE: ONLY WORKS FOR LETTERS IN ARRAY ALPH,
  *       TWO ARRAYS ONE FOR LETTERS ANOTHER FOR FREQUENCY 
  *       PRINTS BOTH ARRAYS
  * 
  *       ACCOUNTS FOR CHAR CASE SENSITIVITY
  *
  *
  *       HOW TO RUN:
  *       - TYPE FILENAME IN MAIN
  *
  *
  * BUGS: NONE
  *
 */

 import java.util.*;
 import java.io.*;

 public class Letters
 {
     public static void main (String[] args)
     {
	     //run on cipher text file
         frequency("testfile-Affine.txt");
     }

     public static void frequency(String filename) 
     {
         char[] alph = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
	 int fqncy[] = new int[26];

         System.out.println("LETTER FREQUENCY\n");

	 String str = FileIOStr.readFile(filename); //Retrieve String from File

	 int lenStr = str.length(); //Need Length
         char[] chArray = str.toCharArray();

	 //CONVERT ALL LETTERS TO UPPER CASE
	 for(int ltr = 0; ltr < chArray.length; ltr++)
         {
             chArray[ltr] = Character.toUpperCase(chArray[ltr]);
         }

	     //LOOP EACH LETTER OF STRING
	     for(int ii=0; ii < lenStr; ii++)
	     {
             	//FIND FREQUENCY INDEX FOR A LETTER
             	for(int jj=0; jj < 26; jj++)
	        {
		    //LETTERS ARE EQUAL
                    if(chArray[ii] == alph[jj])
		    {
	                //ADD COUNT OF ONE
                        fqncy[jj] = fqncy[jj] + 1;
		    }	 
	         } 
	     }

	     //TEST PRINT BOTH ARRAYS
	     for(int ii=0; ii < 26; ii++)
	     {
	         System.out.print(alph[ii] + " ");
	         System.out.println(fqncy[ii]);
	     }
     }
 }
