/*********************************
 * Author: Joshua Rangan
 * Date Last Edited: 26/03/2020
 * Name: FileIO
 * Purpose: To read in char from
 *          file to form a string
 ********************************/
 import java.util.*;
 import java.io.*;

 class FileIOStr
 {
     public static void main (String[] args)
     {
         //System.out.println(readFile("ReadFile.csv"));
         //readFileRN("RandomNames7000.csv");
         //writeOneRow("insertionsort.csv", insertionSort(readFileRN("RandomNames7000.csv")));
     }

     //READ
     public static String readFile(String inFile)
     {    
         FileInputStream fileStrm = null;
         InputStreamReader rdr;
         BufferedReader bufRdr;
        
         int lineNum;
	 String wordRead = ""; //String
         String line;
        
	 try
         {
             fileStrm = new FileInputStream(inFile); //Open the file
             rdr = new InputStreamReader(fileStrm);	//Creates Reader to read the stream
             bufRdr = new BufferedReader(rdr);	//Reads stream one line at a time

             lineNum = 0;
             line = bufRdr.readLine(); 	//Read the first line

             while (line != null)	//While not end-of-file process and read lines
             {   
	             wordRead += line; //returned string of csv line add to string
		
                 line = bufRdr.readLine();	//Read next line
          	     lineNum++;
             }
             fileStrm.close();		//Clean up the stream
         }
         catch (IOException e)		//Catches Exceptions
         {
             if (fileStrm != null)
             {
                 try
                 {
                     fileStrm.close();
                 }
                 catch (IOException ex2) {}
             }

             System.out.println("Error in file processing: " + e.getMessage());   
         } 

	     return wordRead;
     }

     //WRITE
     public static void writeFile (String infilename, String msgWrite)
     {
         FileOutputStream fileStrm = null;
	 PrintWriter pw;

	 try
         {
              fileStrm = new FileOutputStream(infilename); //Open file for writing
	      pw = new PrintWriter(fileStrm); //Initialise Writer

	      //WRITE MSG TO FILE
              pw.println(msgWrite);

	      pw.close(); //Clean Up Stream
	  }
	  catch(IOException e)
	  {
	       if(fileStrm != null) 
               {
	           try
		   {
	               fileStrm.close();
		   }
		   catch(IOException ex2) {}
	       }
             
               //Throw Exception
	       System.out.println("Error in writing to file: " + e.getMessage());
          }
     }
}
