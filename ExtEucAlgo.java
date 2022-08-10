/*************************************
 *Author: Joshua Rangan
 *Date: 02/04/2020
 *Name: Extended Euclidean Algorithm
 ************************************/
/*
 * NOTES:
 *
 * BUGS: none
 *
 */

 import java.util.*;
 import java.io.*;
 public class ExtEucAlgo
 { 

     public static void main (String[] args)
     {
 	 //TEST PRINT
	 System.out.println(gcd(10, 15));
     }
     public static int gcd (int a, int b)
     {
 	  int result = 0;
	
	  //CALC GCD
	  if (a != 0)
	  {
	      //TAKE REMAINDER
	      int rem = b % a;
	    
	      //RECURSE TILL a = 0
	      result = gcd(rem, a);
	  }
	  else
	  {
	      //a = 0, GCD is b
	      result = b;
	  }

	  return result;
     }   
 } 


