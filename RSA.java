/******************************
 *Author: Joshua Rangan
 *Date: 20/04/2020
 *Name: RSA
 *****************************/
/*
 * BUGS: None
 * 
 * NOTES:
 * 
 */
import java.util.*;
import java.math.BigInteger;
public class RSA 
{
    public static void main(String[] args) 
    {
        //RSA RUN
        System.out.println("Test Prints");
        //GENERATE P & Q
        int p = genPQ();
        System.out.println("p: " + p);
        int q = genPQ();
        System.out.println("q: " + q);
        
        //SOLVE n
        long n = ((long)p * (long)q);
        System.out.println("n: " + n);
        //SOLVE phi of n
        long phiN = ((long)(p-1) * (long)(q-1));
        System.out.println("phi: " + phiN);
        //COMPUTE VALID PUBLIC KEY e
        long e = publicKey(phiN);
        System.out.println("e: " + e);
        //SOLVE FOR e^-1
        long eInv = extEuc(e, phiN);
        System.out.println("e^-1: " + eInv);
        //SOLVE PRIVATE KEY d
        long d = eInv % phiN;
        System.out.println("d: " + d);
        //ENCRYPTION
        encryption(e, n, "test.txt");

        //DECRYPTION
        decryption(d, n, "Encryption.txt");
    }

    //ENCRYPTION
    public static void encryption(long e, long n, String filename)
    {
        //BIG INTEGERS
        BigInteger bigE = BigInteger.valueOf(e);
        BigInteger bigN = BigInteger.valueOf(n);
        BigInteger bigC;
        BigInteger result;

        //READ IN PLAINTEXT MSG
        String msg = FileIOStr.readFile(filename);

        //CREATE CHAR ARRAY
        char[] chArray = msg.toCharArray();
        int strlen = chArray.length;
        long[] cipherText = new long[strlen];

        for(int ii = 0; ii < strlen; ii++)
        {
            //FOR EACH LETTER ENCRYPT
            int letter = chArray[ii];
            bigC = BigInteger.valueOf(letter);

            //ENCRYPTION FORMULA
            result = bigC.modPow(bigE, bigN);

            //STORE AS LONG TO PRINT TO FILE
            long cipherNum = result.longValue();
            cipherText[ii] = cipherNum;
        }
        //WRITE TO FILE
        FileIOStr.writeCipherText("Encryption.txt", cipherText);
    }
    
    //DECRYPTION
    public static void decryption(long d, long n, String filename)
    {
        //BIG INTEGER
        BigInteger bigLetter;
        BigInteger result;
        BigInteger bigN = BigInteger.valueOf(n);
        BigInteger bigD = BigInteger.valueOf(d);

        String cipherText = FileIOStr.readFile(filename);
        String[] cipherNums = cipherText.split(","); //Split into numbers
        int strlen = cipherNums.length;
        String plainText = "";

        for(int ii = 0; ii < strlen; ii++)
        {
            //CONVERT STRING TO LONG
            long longLetter = Long.parseLong(cipherNums[ii]);

            //CONVERT TO BIG INTEGER
            bigLetter = BigInteger.valueOf(longLetter);

            //DECRYPTION FORMULA
            result = bigLetter.modPow(bigD, bigN);

            //CONVERT TO LONG AND THEN CHAR
            long longPlain = result.longValue();
            char plainLetter = (char)longPlain;
            
            //ADD TO PLAINTEXT STRING
            plainText = plainText + plainLetter;
        }
        //WRITE STRING TO FILE
        FileIOStr.writePlainText("Decryption.txt", cipherText);
    }

    public static long publicKey(Long phiN)
    {//Finds public valid public key e
        long e = 0;
        boolean eValid = false;
        long min = 1L;
        long max = phiN - 1;
        
        //LOOP UNTIL VALID e FOUND
        //gcd(e, phiN) = 1 
        do
        {
            long randE = min + (long)(Math.random() * (max - min));
            if(gcd(randE, phiN) == 1)
            {
                //FOUND VALID e
                e = randE;
                eValid = true;
            }
        }while(eValid == false);

        return e;
    }

    public static int leCalc(int prime)
    {//Lehmann Algo - Test Valid Prime
        int result = 0;
        
        //GENERATE RANDOM NUM < Prime
	    Random rand = new Random();
        int randNum = rand.nextInt(prime - 1) + 1;
         
         //FORMULA CALC
	    int exp = ((prime - 1) / 2);
	    int pow = (int) Math.pow(randNum, exp);
        result = pow % prime;
	 
	    return result;
    }

    public static int genPQ()
    {//Finds prime number within a range
        int prime = 0;
        boolean found = false;
        Random rand = new Random();
        
        //LOOP UNTIL VALID PRIME FOUND        
        do
        {
            int randPrime = rand.nextInt((100000 - 10000) + 1) + 10000;
            if((leCalc(randPrime) == 1) || (leCalc(randPrime) == -1))
            {
                //FOUND PRIME
                prime = randPrime;
                found = true;
            }
        }while(found == false);
        
        return prime;
    }

    public static long gcd (long a, long b)
    {//Calculate GCD(a, b)

        long result = 0L;
        if (b != 0)
        {
            //TAKE REMAINDER OF MOD
            long rem = a % b;
            //RECURSE TILL b = 0
            result = gcd(b, rem);
        }
        else
        {
            //b = 0, GCD is value a
            result = a;
        }
        return result;
    }
    public static long extEuc(long a, long b)
    {//return s, a^-1
        long s = 0, t = 1, pvS = 1, pvT = 0;
        long result = 0, div = 0, rem = 0;
        long tempS = 0, tempT = 0;

        while(b != 0)
        {
            div = a / b;
            rem = a % b;

            //Reverse Eqn Solve s and t
            tempS = s;
            s = (pvS - (div * s));
            pvS = tempS;
        
            tempT = t;
            t = (pvT - (div * t));
            pvT = tempT;

            //GCD
            a = b;
            b = rem;

            //Inverse of a
            result = pvS;
        }
        return result;
    }
}
