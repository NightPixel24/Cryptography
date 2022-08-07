/*************************************
 * Author: Joshua Rangan
 * Last Edited: 07/04/2020
 * Name: Key Validity Checker
 * Purpose: Test whether keys (a,b)
 *       are valid for Affine Cipher
 * **********************************/

 /* NOTE: USE METHOD userIn to run
  *
  * BUGS: None 
  *
 */

 import java.util.*;
 import java.io.*;
 public class ValidKey
 {   
     public static boolean valid(int a, int b) 
     {
         boolean validKey = true;
	 int compare[] = {3,9,27};

	 if((a < 0) || (a > 26))
	 {
	     System.out.println("INVALID KEY A:"+ a + "\n");
	     validKey = false;
	 }
         else if((b < 0) || (b > 26)) 
         {
	     System.out.println("INVALID KEY B:"+ b + "\n");
	     validKey = false;
	 }
	 else
         {
             //FACTORISE A AND COMPARE CO-PRIME TO 27
	     for(int ii = 1; ii <= a; ii++)
             {   
		 //NUMBER IS A FACTOR
                 if(a % ii == 0)
	         {
                      //COMPARE TO 26 FACTORS
		      for(int jj = 0; jj < compare.length; jj ++)
		      {
                          if(ii == compare[jj]) //COMPARE TO FACTORS IN ARRAY
		          {
			      //CO-PRIME
			      validKey = false;
			  }
		      }     
                 }
	     }
	     if(validKey == false)
	     {
                 System.out.println("INVALID KEY A IS NOT CO PRIME TO 26\n");
	     }
	 }
	 return validKey; 
     }

 }

