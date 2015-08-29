/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.math.BigInteger;
import math.AbstractBigInteger;
import math.BigIntegerBytesList;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author Fede
 */
public class Prueba {
    public static void main(String args[]){
//        BigIntegerBytesList big = new BigIntegerBytesList("25686545");
//        System.out.println(big.pow(225).toString());
        java.math.BigInteger bigO = new BigInteger("5000");
       BigIntegerBytesList big = new BigIntegerBytesList("5000");
        System.out.println(bigO.pow(100)+" "+big.pow(100));
    }
}
