/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.math.BigInteger;
import math.BigIntegerBytesList;


/**
 *
 * @author Fede
 */
public class Prueba {
    public static void main(String args[]){
        BigIntegerBytesList big = new BigIntegerBytesList("-587");
        BigInteger b  = new BigInteger("-587");
        System.out.println(b.setBit(12)+" "+big.setBit(12));
        
    }
}
