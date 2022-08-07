/****************************
 * AUTHOR: Joshua Rangan
 * DATE: 10/03/2020
 * NAME: DATA ENCRYPTION DES 
 * *************************/

 /* NOTES:
  *       - KEY GEN LINE 29
  *       - CALC FUNCTION F LINE 176
  *       - ALGO LINE 380
  *
  * BUGS: NONE
  *
 */

 import java.util.*;
 import java.io.*;
 public class DES
 {
     public static void main (String[] args)
     {
	    //TEST PRINT
	    int SixFourBit[] = {0,0,0,0,0,1,0,1,0,1,0,0,0,1,0,1,0,1,0,1,0,1,0,1,0,0,0,1,1,1,0,0,0,1,1,1,0,0,0,1,0,1,0,1,0,1,0,1,0,1,1,0,0,0,1,1,1,1,0,1,0,1,0,0};
	 
	    int key[] = {0,1,0,0,1,0,1,1,0,1,0,1,1,0,1,0,1,0,1,1,0,1,0,1,0,1,0,1,0,1,1,1,0,0,1,0,1,1,0,1,0,1,0,0,1,1,0,0,1,0,1,0,1,0,1,0,1,0,1,1,0,1,1,0}; 
        
        int[] cipher = new int[64];
        cipher = encrypt(SixFourBit, key); 
        decrypt(cipher, key);
        	    
     }
     //ENCRYPTION
     public static void encrypt(int[] msg, int[] key)
     {
         //GENERATE SUB-KEYS
         int[][] subKeys = keyGen(key);
 
         //RUN ALGORITHM
         int[] cipherText = new int[64];
         
         cipherText = algo(msg, subKeys);
        
         //WRITE TO FILE         
     }
     //DECRYPTION
     public static void decrypt(int[] msg, int[] key)
     {
         //GENERATE SUB-KEYS
         int[][] subKeys = keyGen(key);
                              
         //REVERSE SUB-KEYS int[][]
         int[][] resultArr = new int[16][48]; 

         int count = 0;
         for(int ii = 15; ii >= 0; ii--)
         {
             for(int jj = 0; jj < 48; jj++)
             {
                 //Keep Bit Strings in Order 
                 //Change Order of SubKeys
                 resultArr[count][jj] = subKeys[ii][jj];  
             }
             count++;
         } 


         //RUN ALGORITHM
         int[] plainText = new int[64];
         
         plainText = algo(msg, resultArr);

         //WRITE TO FILE      
     } 
     //SHOULD RETURN KEYGEN [][]
     public static int[][] keyGen(int[] key)
     {
	    int ii;
        int jj;

	    //Left Shift Rounds
	    int lsft[] = {1,1,2,2,2,2,2,2,1,2,2,2,2,2,2,1};

	    //PC-1
	    int pcOne[] = {57,49,41,33,25,17,9,
		               1,58,50,42,34,26,18,
			           10,2,59,51,43,35,27,
			           19,11,3,60,52,44,36,
			           63,55,47,39,31,23,15,
			           7,62,54,46,38,30,22,
			           14,6,61,53,45,37,29,
			           21,13,5,28,20,12,4};

	    //PC-2
	    int pcTwo[] = {14,17,11,24,1,5,
		               3,28,15,6,21,10,
			           23,19,12,4,26,8,
			           16,7,27,20,13,2,
			           41,52,31,37,47,55,
			           30,40,51,45,33,48,
			           44,49,39,56,34,53,
			           46,42,50,36,29,32};

	    int[] pcOneResult = new int[56]; //64 -> 56 
        int[] pcTwoResult = new int[48]; //56 -> 48 

	    //Store keys Gen in Array
	    int[][] keyArray = new int[16][48];

        //===========
	    //PC - 1
	    //===========
	    /*Loop through Array pcOne
	    becomes index of key[] 
	    add to first element result[]*/
	    int pcIndex = 0;
	    for(ii = 0; ii < pcOne.length; ii++)
	    {
            pcIndex = pcOne[ii] - 1;
            pcOneResult[ii] = key[pcIndex];
	    }
         
	    //=============
	    //SPLIT C AND D
	    //=============
	    int[] C = new int[28];
	    int[] D = new int[28];
         
	    //FILL ARRAY C
	    for(ii = 0; ii < 28; ii++)
	    {
            C[ii] = pcOneResult[ii];
	    }

        //FILL ARRAY D
	    jj = 0;
	    for(ii = 28; ii < 56; ii++)
	    {   
            D[jj] = pcOneResult[ii];
	        jj++;
	    }
									 
	    //START LOOP FOR EACH ROUND ========================================
	    for(int kk = 0; kk < 16; kk++)
	    {
	        //=============
	        // LEFT SHIFT
	        //=============

	        if(lsft[kk] == 1) //Perform Shift Once
	        {
                C = lShift(C);
                D = lShift(D);
	        }
	        else //Perform Shift Twice
	        {
                C = lShift(C);
	            C = lShift(C);
	            D = lShift(D);
	            D = lShift(D);
	        }

	        //Combine Array 
            int[] CDarr = new int[56];
         
            System.arraycopy(C, 0, CDarr, 0, 28);
            System.arraycopy(D, 0, CDarr, 28, 28);

	        //RUN PC-2 on CDarr
	        /*Loop through Array pcOne
	        becomes index of key[] 
	        add to first element result[]*/
	        
            pcIndex = 0;
	        for(ii = 0; ii < 48; ii++)
	        {
                pcIndex = pcTwo[ii] - 1;
                pcTwoResult[ii] = CDarr[pcIndex];
	        }

	        //extract key (keys defined above) //2D Array
	        //Use For Loop Index - for Row
	        for(jj = 0; jj < 48; jj++)	 
	        {
		        //COPY PC-2 to KeyArray
                keyArray[kk][jj] = pcTwoResult[jj];
	        }

         }   
	     
         return keyArray;
     } 

     public static int[] lShift(int[] A)
     {
        int temp;
	    int len = A.length;

        //Wrap Around	
	    temp = A[len - 1];
	    A[len - 1] = A[0];

	    //Shift
	    for(int ii = 1; ii < len; ii++)
        {
            A[ii - 1] = A[ii];
	    }

        //Insert Temp after Wrap Around
	    A[len - 2] = temp;

	    return A;
     }

     public static int[] calcF(int[] r, int[]k)
     {
	    int ii;
	    int tableE[] = {32,1,2,3,4,5,
		                4,5,6,7,8,9,
		                8,9,10,11,12,13,
		                12,13,14,15,16,17,
		                16,17,18,19,20,21,
		                20,21,22,23,24,25,
		                24,25,26,27,28,29,
		                28,29,30,31,32,1};

	    int permutation[] = {16,7,20,21,
		                     29,12,28,17,
			                 1,15,23,26,
			                 5,18,31,10,
			                 2,8,24,14,
			                 32,27,3,9,
			                 19,13,30,6,
			                 22,11,4,25};

	    int[] eResult = new int[48];
        int[] xorResult = new int[48];

	    //S-BOXES
	    int sbox1[][] = {
		          {14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7},
		          {0,15,7,4,14,2,13,10,3,6,12,11,9,5,3,8},
			      {4,1,14,8,13,6,2,11,15,12,9,7,3,10,5,0},
			      {15,12,8,2,4,9,1,7,5,11,3,14,10,0,6,13},
	                    };
        int sbox2[][] = {
		          {15,1,8,14,6,11,3,4,9,7,2,13,12,0,5,10},
		          {3,13,4,7,15,2,8,14,12,0,1,10,6,9,11,5},
			      {0,14,7,11,10,4,13,1,5,8,12,6,9,3,2,15},
			      {13,8,10,1,3,15,4,2,11,6,7,12,0,5,14,9},
	                    };
        int sbox3[][] = {
		          {10,0,9,14,6,3,15,5,1,13,12,7,11,4,2,8},
		          {13,7,0,9,3,4,6,10,2,8,5,14,12,11,15,1},
			      {13,6,4,9,8,15,3,0,11,1,2,12,5,10,14,7},
			      {1,10,13,0,6,9,8,7,4,15,14,3,11,5,2,12},
	                    };
        int sbox4[][] = {
		          {7,13,14,3,0,6,9,10,1,2,8,5,11,12,4,15},
		          {13,8,11,5,6,15,0,3,4,7,2,12,1,10,14,9},
			      {10,6,9,0,12,11,7,13,15,1,3,14,5,2,8,4},
			      {3,15,0,6,10,1,13,8,9,4,5,11,12,7,2,14},
	                    };
        int sbox5[][] = {
		          {2,12,4,1,7,10,11,6,8,5,3,15,13,0,14,9,},
		          {14,11,2,12,4,7,13,1,5,0,15,10,3,9,8,6},
			      {4,2,1,11,10,13,7,8,15,9,12,5,6,3,0,14},
			      {11,8,12,7,1,14,2,13,6,15,0,9,10,4,5,3},
	                     };
        int sbox6[][] = {
		          {12,1,10,15,9,2,6,8,0,13,3,4,14,7,5,11},
		          {10,15,4,2,7,12,9,5,6,1,13,14,0,11,3,8},
			      {9,14,15,5,2,8,12,3,7,0,4,10,1,13,11,6},
			      {4,3,2,12,9,5,15,10,11,14,1,7,10,0,8,13},
	                    };
        int sbox7[][] = {
		          {4,11,2,14,15,0,8,13,3,12,9,7,5,10,6,1},
		          {13,0,11,7,4,9,1,10,14,3,5,12,2,15,8,6},
			      {1,4,11,13,12,3,7,14,10,15,6,8,0,5,9,2},
			      {6,11,13,8,1,4,10,7,9,5,0,15,14,2,3,12},
	                    };
        int sbox8[][] = {
		          {13,2,8,4,6,15,11,1,10,9,3,14,5,0,12,7},
		          {1,15,13,8,10,3,7,4,12,5,6,11,10,14,9,2},
			      {7,11,4,1,9,12,14,2,0,6,10,10,15,3,5,8},
			      {2,1,14,7,4,10,8,13,15,12,9,9,3,5,6,11},
	                    };

	    //CREATE 3D ARRAY OF 2D S-BOXES
	    int sbox[][][] = {sbox1,sbox2,sbox3,sbox4,sbox5,sbox6,sbox7,sbox8};

	    //BIT SELECT TABLE E
        int eIndex = 0;
	    for(ii = 0; ii < tableE.length; ii++)
	    {
            eIndex = tableE[ii] - 1;
            eResult[ii] = r[eIndex];
	    }


	    //XOR -> eResult[] + k[]
	    int result = 0;
	    for(int jj = 0; jj < xorResult.length; jj++)
	    {
            //Use XOR operator
	        result = eResult[jj]^k[jj];
	        xorResult[jj] = xorResult[jj] + result;
	    } 


        //SPLIT XOR ARRAY INTO 8 Arrays
	    //6 Bits Each
        int xor1[] = Arrays.copyOfRange(xorResult, 0, 6);    
        int xor2[] = Arrays.copyOfRange(xorResult, 6, 12);
        int xor3[] = Arrays.copyOfRange(xorResult, 12, 18);
        int xor4[] = Arrays.copyOfRange(xorResult, 18, 24);
        int xor5[] = Arrays.copyOfRange(xorResult, 24, 30);
        int xor6[] = Arrays.copyOfRange(xorResult, 30, 36);
        int xor7[] = Arrays.copyOfRange(xorResult, 36, 42);
        int xor8[] = Arrays.copyOfRange(xorResult, 42, 48);
	 
	    //2D array for storage and use
	    int[][] xor = {xor1,xor2,xor3,xor4,xor5,xor6,xor7,xor8};

        //RESULT OF ALL THE S-BOXES CALCS 
        int sBoxResult[] = new int[32];
        int count = 0;

	    for(int kk = 0; kk < 8; kk++)
        {//RUN 8 ROUNDS OF S BOX

            //====================
	        //FINDING ROWS & COLS
	        //====================

            int[] row = new int[2];      
            int[] col = new int[4];

	        //BINARY TABLE USED FOR CONVERSION
            int binary[] = {8,4,2,1};

	        //BINARY ROW FOR S-BOX
	        row[0] = xor[kk][0];
	        row[1] = xor[kk][5];
            
	        //BINARY COL FOR S-BOX
	        col[0] = xor[kk][1]; 
	        col[1] = xor[kk][2]; 
	        col[2] = xor[kk][3]; 
	        col[3] = xor[kk][4]; 

            int ll;
	     
            //CONVERT ROW - BINARY TO DECIMAL 
            int rowDecimal = 0;
            for(ll = 0; ll < 2; ll++)
            {
                rowDecimal = rowDecimal + (row[ll] * binary[ll + 2]); 
            }
                
            //CONVERT COL - BINARY TO DECIMAL 
            int colDecimal = 0;
            for(ll = 0; ll < 4; ll++)
            {
                colDecimal = colDecimal + (col[ll] * binary[ll]); 
            }

            //=================
            //BEGIN S-BOX CALCS
            //=================
                 
            //GET S-BOX VALUE
            int sboxVal = 0;
            sboxVal = sbox[kk][rowDecimal][colDecimal];

            int[] binResult = new int[4];

            //CONVERT S-BOX VALUE TO BINARY VALUE
            for(int bb = 0; bb < 4; bb++)
            {	     
	            if(sboxVal < binary[bb])
	            {
	                binResult[bb] = 0;
	            }
		        else
	            {
                    binResult[bb] = 1;
		            sboxVal = sboxVal - binary[bb];
	            }		 
	        }  
             
	        //BINARY S-BOX VALUES FILL 32 BIT RESULT ARRAY
	        for(int bb = 0; bb < 4; bb++)
	        {
                sBoxResult[count] = binResult[bb];
		        count++;
	        }

	    }//END FOR

	    //PERMUTATION P
        //Run on sBoxResult
         
        int pIndex = 0;
	    int[] pResult = new int[32];

	    for(ii = 0; ii < permutation.length; ii++)
	    {
             pIndex = permutation[ii] - 1;
             pResult[ii] = sBoxResult[pIndex];
	    }  
         
	    //RESULT OF FUNCTION F
	    return pResult; 
     }
     
     //RUN MAIN ALGORITHM
     public static int[] algo(int[] msg, int[][] subKeys)
     {
         int[] IP = {58,50,42,34,26,18,10,2,
		             60,52,44,36,28,20,12,4,
		             62,54,46,38,30,22,14,6,
		             64,56,48,40,32,24,16,8,
		             57,49,41,33,25,17,9,1,
		             59,51,43,35,27,19,11,3,
		             61,53,45,37,29,21,13,5,
		             63,55,47,39,31,23,15,7};
	 
	     int[] IPinv = {40,8,48,16,56,24,64,32,
		                39,7,47,15,55,23,63,31,
		        	    38,6,46,14,54,22,62,30,
		        	    37,5,45,13,53,21,61,29,
		        	    36,4,44,12,52,20,60,28,
		        	    35,3,43,11,51,19,59,27,
		        	    34,2,42,10,50,18,58,26,
		        	    33,1,41,9,49,17,57,25};

	     //INITIAL PERMUTATION INTO ipResult
	     int ipIndex = 0;
	     int[] ipResult = new int[64];

	     for(int ii = 0; ii < IP.length; ii++)
	     {
             ipIndex = IP[ii] - 1;
             ipResult[ii] = msg[ipIndex];
	     }
         
	
         //SPLIT ARRAYS INT 32BITS Li Ri
         int Li[] = Arrays.copyOfRange(ipResult, 0, 32);    
         int Ri[] = Arrays.copyOfRange(ipResult, 32, 64);    
	 
	     //FOR LOOP BEGIN - RUN 16 ROUNDS
         for(int bb = 0; bb < 16; bb++)
         { 
             //RUN SWITCH FUNCTION
             switchFunc(Li, Ri);
             
             //FUNC F
             int[] f = new int[32];
             f = calcF(Ri, subKeys[bb]);
            
             //Li stays same
             //Ri XOR func F 
                  
             //XOR -> Ri[] + f[]
	         int result = 0;
             int[] xorResult = new int[32];
             
	         for(int jj = 0; jj < xorResult.length; jj++)
	         {
                 //Use XOR Operator
	             result = Ri[jj]^f[jj];
	             xorResult[jj] = xorResult[jj] + result;
	         } 
             
             //SET NEW Ri, Li Already SET in switchFunc
             System.arraycopy(xorResult, 0, Ri, 0, 32);
         }
         
         //RUN LAST ROUND SWITCH FUNC
         switchFunc(Li, Ri);

         //COMBINE R16 & L16
         int[] RLcomb = new int[64];
         System.arraycopy(Li, 0, RLcomb, 0, 32);
         System.arraycopy(Ri, 0, RLcomb, 32, 32);

         //FINAL PERMUTATION
         int IPinvIndex = 0;
	     int[] cipherText = new int[64];

	     for(int ii = 0; ii < IPinv.length; ii++)
	     {
             IPinvIndex = IPinv[ii] - 1;
             cipherText[ii] = RLcomb[ipIndex];
	     }
       
         return cipherText;
     } 
     public static void switchFunc(int[] Li, int[] Ri)
     {
        //SWITCH Li to Ri, Ri to Li

        //STORE IN TEMP ARRAY	 
        int[] temp = new int[32];

        //Li -> temp
        System.arraycopy(Li, 0, temp, 0, 32);
        //Ri -> Li
        System.arraycopy(Ri, 0, Li, 0, 32);
        //Li -> Ri
        System.arraycopy(temp, 0, Ri, 0, 32);
     }
 }

