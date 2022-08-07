/********************************
 * Author: Joshua Rangan
 * Last Edited: 05/03/2020
 * Name: CONVERT CHAR TO BINARY
 * *****************************/

 /* NOTE:
  *
  * BUGS: NONE
  *
 */

 import java.util.*;
 import java.io.*;

 public class Binary
 {
     public static void main (String[] args)
     { 
         //TEST PRINT
	 convertBin("A");
     }

     public static void convertBin(String str) 
     {
	 //CHECK TABLE
	 int[] BinTbl = {128,64,32,16,8,4,2,1};
         int[] binary = new int[8];

	 int lenStr = str.length();
         char[] chArray = str.toCharArray();

	 //LOOP EACH LETTER OF STRING
	 for(int ii=0; ii < lenStr; ii++)
	 {
             //Get Decimal Number
             int dec = chArray[ii];
     }

     }
}
