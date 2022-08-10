/******************************
 *Author: Joshua Rangan
 *Date: 02/04/2020
 *Name: Revised Affine Cipher
 *****************************/
/*
 * BUGS: None
 * 
 * NOTES: Based Off Assertion: gcd(a, b) = gcd(b, a % b) 
 * 
 */
import java.util.Scanner;
public class EuclideanAlgo
{
    public static void main (String[] args)
    {
        int a, b, gcdResult;

        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Eucldean Algorithm Calculator\n");

        //User Input
        do
        {
            System.out.println("Please Enter a Value for a");
            a = sc.nextInt();
            System.out.println("Please Enter a Value for b");
            b = sc.nextInt();

            if(a < b)
            {
                System.out.println("Value a is less than b\n");
            }
        }while(a < b);

        gcdResult = gcd(a, b);

        System.out.println("\nGreatest Common Divisor: " + gcdResult);
        sc.close();
    }

    public static int gcd (int a, int b)
    {
        int result = 0;

        //CALC GCD
        if (b != 0)
        {
            //TAKE REMAINDER OF MOD
            int rem = a % b;

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
}