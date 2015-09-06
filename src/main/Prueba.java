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
        BigIntegerBytesList big = new BigIntegerBytesList("12533");
        BigInteger b  = new BigInteger("12533");
        System.out.println(b.flipBit(2)+" "+ big.flipBit(2));
    }
}