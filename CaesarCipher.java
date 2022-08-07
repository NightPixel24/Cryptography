/**************************
 * Author: Joshua Rangan
 * Last Edited: 05/03/2020
 * Name: Caesar Cipher
 * ***********************/

 /* NOTE: PROGRAM ONLY WORKS WITH CAPITAL LETTERS
  *       K = 3
  * BUGS: 
  *
 */

 import java.util.*;
 import java.io.*;

 public class CaesarCipher
 {

    public static void main (String[] args)
    {/*
	 //PRINT ENCRYPTION
         System.out.println("PRINTING ENCRYPTION");
         System.out.println(encrypt("HEY BUDDY"));
         System.out.println();

	 //PRINT DECRYPT 
         System.out.println("PRINTING DECRYPTION");
	 System.out.println(decrypt("KHB=EXGGB"));
         System.out.println();

	 //PRINT SPACING TEST
	 System.out.println("PRINTING SPACING TEST");
	 System.out.println(decrypt(encrypt("THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG")));
         System.out.println();

     */ 
         System.out.println(encrypt("THIS IS A SECRET MSG"));
	 System.out.println(decrypt(encrypt("THIS IS A SECRET MSG")));
    }

    //ENCRYPT MESSAGE
    public static String encrypt(String msgPlain)
    {
	 int idx = 0;
	 int cipherNum = 0;
	 int lenStr = msgPlain.length();
	 char ciphertxt[] = new char[lenStr];
         
	 //PASS EACH CHAR IN ARRAY
	 char[] chArray = msgPlain.toCharArray();

	 //ENCRPYT EACH CHAR
     	 for(int ii = 0; ii < lenStr; ii++)
	 {
             //cvrt ascii - idx finder
             idx = chArray[ii] - 65;
	     cipherNum = ((idx + 3) % 26) + 65;
              
	     //Convert int to char - put into array
             ciphertxt[ii] = (char)cipherNum;
            
     	 }

     	     //Array char into String
	     String cipherStr = new String(ciphertxt);

	     return cipherStr;
     }
    
     //DECRYPT MESSAGE
     public static String decrypt(String msgCiph)
     {
	 int idx = 0;
	 int cipherNum = 0;
	 int lenStr = msgCiph.length();
	 char plaintxt[] = new char[lenStr];

	 //CREATES ARRAY FROM CIPHER 
         char[] chArray = msgCiph.toCharArray();

	 for(int ii = 0; ii < lenStr; ii++)
	 {
             //Account for Spaces
	     if(chArray[ii] == 61)
	     {
	         cipherNum = 32;
	     }
	     else
	     {
            	 //cvrt ascii - idx finder
            	 idx = chArray[ii] - 65;

	         //Accounts for negative mod opperation
	         int c1 = idx - 3;
                 
		 if(c1 < 0)
	         {
                     c1 = c1 + 26;
	         }
            
	         cipherNum = (c1 % 26) + 65; 
             }
	     
	     //Convert int to char - put into array
             plaintxt[ii] = (char)cipherNum;
    	}
         
	     //Array char into String
	     String plainStr = new String(plaintxt);

	     return plainStr;
     }

 }

