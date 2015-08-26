package main;

import java.math.BigInteger;
import java.util.Arrays;
import math.AbstractBigInteger;
import math.BigIntegerBytesList;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author Parisi Germán
 * @version 1.0
 */
public class Main {

    public static void main(String args[]) {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("165156322");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("-875287965485");
//        System.out.println(Arrays.toString(bigOriginal1.toByteArray()));
        
        System.out.println(bigOriginal1.clearBit(2453645));
        AbstractBigInteger big1 = new math.BigIntegerBytesList("165156322");
        
        
        AbstractBigInteger big2 = new math.BigIntegerBytesList("-875287965485");
//        System.out.println(Arrays.toString(big1.toByteArray()));
        System.out.println(big1.clearBit(2453645));
//        AbstractBigInteger big2 = new math.BigIntegerBytesList(new byte[]{-4, 10, 125, -45});

//        BigInteger big = new BigInteger("-1115648995623");
//        System.out.println(Arrays.toString(big.toByteArray()));

//        System.out.println("CORRECTO: " + bigOriginal2.toString());
//        System.out.println(big1.toString());
//        System.out.println(Arrays.toString(bigOriginal1.toByteArray()));
//        System.out.println(Arrays.toString(big1.toByteArray()));
//        
//        System.out.println(Integer.toBinaryString(-4));
    }
}
