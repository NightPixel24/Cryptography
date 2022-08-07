/******************************
 *Author: Joshua Rangan
 *Date: 02/04/2020
 *Name: Revised Affine Cipher
 * ***************************/
/*
 * NOTES: Affine uses mod 27 instead of 26  
 *        - Use ASCII Table for Alphabet
 *        - Calc Valid Keys (user input)
 *        - FileIO class to read and write message
 *
 *        - Sorry for indenting if it looks weird I did this on my laptop because
 *          Covid 19 and could not come to the labs to check if it was well formatted 
 *          for those specific PC's
 *        
 *        - Letters A - Z, 65-90 plus [91
 *        - Letters a - z, 97-122 plus {123
 *
 * BUGS:  NONE
 *
 */

 import java.util.*;
 import java.io.*;
 public class Affine
 {
     public static void main(String[] args)
     {
         //KEYS INPUT
	     int numA, numB; 
	     String cipher;
	     boolean checkValid = true;
	     do
	     {
             Scanner sc = new Scanner(System.in);

	         System.out.println("Please Enter Key A");
             numA = sc.nextInt();

	         System.out.println("Please Enter Key B");
             numB = sc.nextInt();
		 
	         //TEST PRINT
             checkValid = ValidKey.valid(numA, numB);

	     }while(checkValid == false);
	 
	     //ENCRYPTION - FILEIO SAVE
	     cipher = encrypt("testfile-Affine.txt", numA, numB);
         
         //DECRYPTION - FILEIO SAVE
	     decrypt(cipher, numA, numB);
     }

     //ENCRYPTION
     public static String encrypt(String filename, int a, int b)
     {
	     int letter = 0;
	     int result = 0;
	     char ciphLetter = 'a';
	     String cipherText = "";

	     //READ IN PLAINTEXT MSG
         String msg = FileIOStr.readFile(filename);
         
         //CREATE CHAR ARRAY
         char[] chArray = msg.toCharArray();
         int strlen = chArray.length;


	     //LOOP THROUGH EACH LETTER
         for(int ii=0; ii < strlen; ii++)
	     {
             //CNVRT LETTER TO INT
	         letter = chArray[ii];

	         //ACCOUNT FOR UPPERCASE/LOWERCASE
	         if((letter >= 65) && (letter <= 90))
             {
		         //UPPERCASE LETTER
	             letter = letter - 65;
                 //ENCRYPT FORMULA
	             //c = f(m) = (a m + b) mod 27
	             result = ((a * letter) + b) % 27;
                 
	             //CNVRT BACK TO ASCII CHAR
	             ciphLetter = (char) (result + 65); 

	             //CIPHER TEXT STRING CREATION
		         cipherText = cipherText + ciphLetter; 
	         }
	         else if((letter >= 97) && (letter <= 122))
             {
                 //LOWERCASE LETTER
		         letter = letter - 97;
		 
		         //ENCRYPT FORMULA
	             //c = f(m) = (a m + b) mod 27
	             result = ((a * letter) + b) % 27;
             
	             //CNVRT BACK TO ASCII CHAR
	             ciphLetter = (char) (result + 97); 

	             //CIPHER TEXT STRING CREATION
		         cipherText = cipherText + ciphLetter; 
	         }
	         else
	         {
	             //SKIP LETTER
		 
		         //SPACES ONLY
		         /*if(letter == 32)
	               {
	                   ciphLetter = (char) letter;
                       cipherText = cipherText + ciphLetter;
		           }*/

		         //OR

		         //ADD LETTER TO STRING
	          	
		         ciphLetter = (char) letter;
		         cipherText = cipherText + ciphLetter; 
		 
             }

         }

	     //WRITE CIPHER TEXT TO FILE
	     FileIOStr.writeFile("Encrypt.txt", cipherText);

	     return cipherText;
     }

     //DECRYPTION
     public static String decrypt(String msg, int a, int b)
     {
	    int result = 0;
	    int aInv = 0;
	    char letter = 'e';
	    int ascii = 0;
	    int placeholder = 0;
	    int placeholder2 = 0;
	    int decryptLetter = 0;
	    String plainText = "";
        
        int len = msg.length();
        char[] chArray = msg.toCharArray();

	    //FIND a^-1
	    //aa^-1 MOD 27 = 1

	    //LOOOP THROUGH 1-26 KEY POSSIBILITIES
	    for(int ii = 1; ii < 27; ii++)
	    {  
	        //USE FORMULA
	        result = (a*ii) % 27;
            
	        //A INVERSE FOUND - SAVED
	        if(result == 1)
            {
                aInv = ii;
            }
	    }
	    
        //DECRYPT FORMULA
	    //m =f^-1(c) = a^-1 (c - b) mod 27

	    //FOR EACH LETTER IN CIPHER TEXT
        for(int jj = 0; jj < len; jj++)
	    {
            //CONVERT LETTER TO INT
            ascii = chArray[jj];
            if((ascii >= 65) && (ascii <= 91)) //CAPITAL
            {
                //ACCOUNT FOR ASCII VALUE
                ascii = ascii - 65;
	         
		        //FORMULA DECRYPT
		        placeholder = ascii - b;
                placeholder2 = (aInv * placeholder);

                //IF NEGATIVE MODULAR
		        while(placeholder2 < 27)
	            {
                    placeholder2 = placeholder2 + 27;
		        }
		        decryptLetter = placeholder2 % 27;

	            //CNVRT TO ASCII (MAPPING)
		        letter = (char) (decryptLetter + 65);

		        //ADD LETTER TO PLAINTEXT STRING
		        plainText = plainText + letter;
	        }
	        else if((ascii >= 97) && (ascii <= 123)) //LOWERCASE
	        {

                //ACCOUNT FOR ASCII VALUE
                ascii = ascii - 97;
                 
		        //FORMULA DECRYPT
		        placeholder = ascii - b;
                placeholder2 = (aInv * placeholder);

		        //IF NEGATIVE MODULAR
		        while(placeholder2 < 27)
	            {
                    placeholder2 = placeholder2 + 27;
		        }
		        decryptLetter = placeholder2 % 27;
	         
		        //CNVRT TO ASCII (MAPPING)
		        letter = (char) (decryptLetter + 97);

		        //ADD LETTER TO PLAINTEXT STRING
		        plainText = plainText + letter;
	        }
	        else
            {
                //SKIP LETTER
		 
		        /*SPACES ONLY
		        if(ascii == 32)
	            {
	                letter = (char) ascii;
                    plainText = plainText + letter;
		        }*/

		        //OR

		        //ADD LETTER TO STRING
	         	
		        letter = (char) ascii;
		        plainText = plainText + letter; 		 
	        }             
	    }

	        FileIOStr.writeFile("Decrypt.txt", plainText);

	        return plainText;
     }
}
