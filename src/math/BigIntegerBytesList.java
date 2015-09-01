package math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

/**
 *
 * @author Parisi Germán
 * @version 1.0
 */
public class BigIntegerBytesList extends AbstractBigInteger<BigIntegerBytesList> {

    public static final BigIntegerBytesList ZERO = new BigIntegerBytesList("0");
    public static final BigIntegerBytesList ONE = new BigIntegerBytesList("1");
    public static final BigIntegerBytesList TWO = new BigIntegerBytesList("2");
    public static final BigIntegerBytesList TEN = new BigIntegerBytesList("10");

    /**
     * The digits in big-endian.
     */
    protected ArrayList<Digit> digits;

    protected boolean negative;

    public BigIntegerBytesList(String number) {
        if (number.length() == 0) {
            throw new NumberFormatException();
        }
        this.digits = new ArrayList<>();
        for (int i = 0; i < number.length(); i++) {
            char c = number.charAt(i);
            if (Character.isDigit(c)) {
                Digit digit = new Digit(Byte.parseByte(String.valueOf(c)));
                this.digits.add(digit);
            } else if (i == 0 && (c == '-' || c == '+')) {
                if (c == '-') {
                    negative = true;
                }
            } else {
                throw new NumberFormatException("For input string: " + number);
            }
        }
        int i = 0;
        byte b = 0;
        while (i < digits.size() && b == 0) {
            b = digits.get(i).getDigit();
            if (b == 0) {
                digits.remove(i);
            } else {
                i++;
            }
        }
        // Special cases: -0 or 0
        if (digits.isEmpty()) {
            digits.add(new Digit((byte) 0));
            this.negative = false;
        }
    }

    private String getBinary(byte[] val) {
        StringBuilder resString = new StringBuilder();
        for (int i = 0; i < val.length; i++) {
            String v = Integer.toBinaryString(val[i]);
            // Substring for negatives.
            v = v.substring(Math.max(v.length() - 8, 0));
            // v.length() <= 8
            while (v.length() < 8) {
                v = "0" + v;
            }
            resString.append(v);
        }
        String complement = complement(resString.toString());
        return complement;
    }

    private String getBinary2(byte[] val) {
        StringBuilder resString = new StringBuilder();
        for (int i = 0; i < val.length; i++) {
            String v = Integer.toBinaryString(val[i]);
            // Substring for negatives.
            v = v.substring(Math.max(v.length() - 8, 0));
            // v.length() <= 8
            while (v.length() < 8) {
                v = "0" + v;
            }
            resString.append(v);
        }
        return resString.toString();
    }

    public BigIntegerBytesList(byte[] val) {
        // Convert to String.
        // Subtract bit to bit. C2 = 2^n - N => N = 2^n - C2.
        byte res[] = new byte[val.length];
        if (val[0] < 0) {
            // Negative.
            String complement = getBinary(val);
            for (int i = 0, j = 0; i < complement.length(); i += 8, j++) {
                String part = complement.substring(i, i + 8);
                // Byte.parseByte no se puede utilizar porque solo puede hasta 7 bits.
                short aux = Short.parseShort(part, 2);
                res[j] = (byte) aux;
            }
            negative = true;
        } else {
            // Positive
            for (int i = 0; i < res.length; i++) {
                res[i] = val[i];
            }
        }
        byte mascara;
        BigIntegerBytesList multiplicador = ONE;
        BigIntegerBytesList result = new BigIntegerBytesList("0");
        for (int i = res.length - 1; i >= 0; i--) {
            mascara = 1;
            while (mascara != 0) {
                BigIntegerBytesList big = ((res[i] & mascara) == (byte) mascara) ? ONE : ZERO;
                result = result.add(multiplicador.multiply(big));
                multiplicador = multiplicador.multiply(TWO);
                mascara <<= 1;
            }
        }
        digits = result.digits;
    }

    public BigIntegerBytesList(int signum, byte[] magnitude) {
        if (signum == 0 && magnitude.length > 0) {
            throw new NumberFormatException("signum-magnitude mismatch");
        }
        String binary = getBinary2(magnitude);
        if (signum == -1) {
            negative = true;
        }
        BigIntegerBytesList bigRadix = new BigIntegerBytesList("2");
        BigIntegerBytesList mult = ONE;
        BigIntegerBytesList big = ZERO;
        for (int i = binary.length() - 1; i >= 0; i--) {
            String dig = String.valueOf(binary.charAt(i));
            BigIntegerBytesList bigDig = new BigIntegerBytesList(dig);
            if (bigDig.compareTo(bigRadix) < 0) {
                big = big.add(mult.multiply(bigDig));
                mult = mult.add(mult);
            } else {
                throw new NumberFormatException("Error en el formato del número: " + binary);
            }
        }
        this.digits = big.digits;
    }

    public BigIntegerBytesList(int bitLength, int certainty, Random rnd) {

    }

    public BigIntegerBytesList(int numBits, Random rnd) {
        this(1, getRandomBytes(numBits, rnd));
    }

    private static byte[] getRandomBytes(int numBits, Random rnd) {
        if (numBits < 0) {
            throw new IllegalArgumentException("numBits must be non-negative");
        }
        if (numBits == 0) {
            return new byte[]{0};
        }
        byte[] bytes = new byte[(int) (Math.ceil((double) numBits / 8.0))];
        rnd.nextBytes(bytes);
        int tmp = numBits % 8;
        int mask;
        if (tmp == 0) {
            mask = (int) 0xff;
        } else {
            mask = (int) (Math.pow(2, tmp) - 1);
        }
        bytes[0] &= mask;
        return bytes;
    }

    public BigIntegerBytesList(String val, int radix) {
        if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
            throw new NumberFormatException("Radix out of range");
        }
        if (val.charAt(0) == '-') {
            this.negative = true;
        }
        int until = 0;
        if (val.charAt(0) == '+' || val.charAt(0) == '-') {
            // Ignore the first digit, i.e. the sign.
            until = 1;
        }
        BigIntegerBytesList bigRadix = new BigIntegerBytesList(String.valueOf(radix));
        BigIntegerBytesList mult = ONE;
        BigIntegerBytesList big = ZERO;
        for (int i = val.length() - 1; i >= until; i--) {
            String dig = String.valueOf(val.charAt(i));
            BigIntegerBytesList bigDig = new BigIntegerBytesList(dig);
            if (bigDig.compareTo(bigRadix) < 0) {
                big = big.add(mult.multiply(bigDig));
                mult = mult.add(mult);
            } else {
                throw new NumberFormatException("Error en el formato del número: " + val);
            }
        }
        this.digits = big.digits;
    }

    @Override
    public BigIntegerBytesList abs() {
        BigIntegerBytesList bigAbs = new BigIntegerBytesList("0");
        bigAbs.digits = digits;
        return (negative) ? negate() : bigAbs;
    }

    @Override
    public BigIntegerBytesList add(BigIntegerBytesList val) {
        BigIntegerBytesList result = null;
        StringBuilder resultString = new StringBuilder();

        // Check the opposite sign
        if ((this.negative && !val.negative) || (!this.negative && val.negative)) {
            return subtract2(val);
        }

        BigIntegerBytesList sumando1 = this;
        BigIntegerBytesList sumando2 = val;
        int nm = sumando1.digits.size();
        int ns = sumando2.digits.size();
        int max = nm;
        int min = ns;
        if (max < min) {
            BigIntegerBytesList tmp = sumando1;
            sumando1 = sumando2;
            sumando2 = tmp;
            max = ns;
            min = nm;
        }
        int diff = max - min;

        // Start the algorithm
        // The sumando1 have the greater count of digits.
        // The sumando2 have the lower count of digits.
        int carriage = 0;
        for (int i = max - 1, j = min - 1; i >= diff; i--, j--) {
            Digit digit1 = sumando1.digits.get(i);
            Digit digit2 = sumando2.digits.get(j);
            int x = digit1.getDigit();
            int y = digit2.getDigit();
            int z = x + y + carriage;
            int r = z % 10;
            carriage = z / 10;
            resultString.insert(0, String.valueOf(r));
        }

        // Reading the rest of sumando1.
        for (int i = diff - 1; i >= 0; i--) {
            Digit digit = sumando1.digits.get(i);
            int z = digit.getDigit() + carriage;
            int r = z % 10;
            carriage = z / 10;
            resultString.insert(0, String.valueOf(r));
        }
        if (carriage != 0) {
            resultString.insert(0, String.valueOf(carriage));
        }

        result = new BigIntegerBytesList(resultString.toString());
        result.negative = negative;
        return result;
    }

    @Override
    public BigIntegerBytesList and(BigIntegerBytesList val) {
        byte[] bigMenor = toByteArray();
        byte[] bigMayor = val.toByteArray();
        int min = bigMenor.length;
        int max = bigMayor.length;

        if (min > max) {
            min = bigMayor.length;
            max = bigMenor.length;
            byte tmp[] = bigMenor;
            bigMenor = bigMayor;
            bigMayor = tmp;
        }
        byte[] tmp = new byte[max];
        for (int i = 1; i <= min; i++) {
            tmp[tmp.length - i] = bigMenor[bigMenor.length - i];
        }
        if (bigMenor[0] < 0) {
            for (int i = min + 1; i <= tmp.length; i++) {
                tmp[tmp.length - i] = (byte) 255; // All ones.
            }
        }
        bigMenor = tmp;

        byte[] result = new byte[max];
        for (int i = 0; i < max; i++) {
            result[i] = (byte) (bigMenor[i] & bigMayor[i]);
        }

        BigIntegerBytesList res = new BigIntegerBytesList(result);
        return res;
    }

    @Override
    public BigIntegerBytesList andNot(BigIntegerBytesList val) {
        return this.and(val.not());
    }

    @Override
    public int bitCount() {
        return this.toByteArray().length * 8;
    }

    @Override
    public int bitLength() {
        return toString(2).length();
    }

    /**
     * Return a BigInteger whose value is equivalent to this BigInteger with the
     * designated bit cleared. (Computes (this & ~(1<<n)).)
     *
     * @param n index of bit to clear
     * @return this & ~(1<<n)
     */
    @Override
    public BigIntegerBytesList clearBit(int n) {
        if (n < 0) {
            throw new ArithmeticException("Negative bit address");
        }
        BigIntegerBytesList mask2 = ONE.shiftLeft(n).not();
        return and(mask2);
    }

    @Override
    public int compareTo(BigIntegerBytesList other) {
        int n1 = this.digits.size();
        int n2 = other.digits.size();
        int resp = Integer.MAX_VALUE;

        if (!this.negative && other.negative) {
            resp = 1;
        } else if (this.negative && !other.negative) {
            resp = -1;
        } else {
            // Both have the same sign
            if (n1 > n2) {
                resp = 1;
            } else if (n1 < n2) {
                resp = -1;
            } else {
                for (int i = 0; i < this.digits.size(); i++) {
                    byte d1 = this.digits.get(i).getDigit();
                    byte d2 = other.digits.get(i).getDigit();
                    if (d1 > d2) {
                        resp = 1;
                        break;
                    } else if (d1 < d2) {
                        resp = -1;
                        break;
                    }
                }
                if (resp == Integer.MAX_VALUE) {
                    resp = 0;
                }
            }
            if (this.negative && other.negative) {
                // Both numbers are negative
                resp *= -1;
            }
        }

        return resp;
    }

    @Override
    public BigIntegerBytesList divide(BigIntegerBytesList val) {
        return divideAndRemainder(val)[0];
    }

    @Override
    public BigIntegerBytesList[] divideAndRemainder(BigIntegerBytesList val) {
        if (val.equals(ZERO)) {
            throw new ArithmeticException("BigInteger divide by zero");
        }
        BigIntegerBytesList dividendo = this.abs();
        BigIntegerBytesList divisor = (BigIntegerBytesList) val.abs();

        StringBuilder quotientString = new StringBuilder();
        int n = dividendo.digits.size();
        int i = 0;
        int d1 = dividendo.digits.get(i).getDigit();
        BigIntegerBytesList tmp = new BigIntegerBytesList(String.valueOf(d1));
        BigIntegerBytesList remainder = ZERO;
        while (tmp.compareTo(divisor) < 0 && i < n - 1) {
            i++;
            d1 = dividendo.digits.get(i).getDigit();
            tmp.digits.add(new Digit((byte) d1));
        }
        if (i == n) {
            quotientString.append("0");
        } else {
            int r = inside(tmp, divisor);
            i++;
            quotientString.append(String.valueOf(r));
            BigIntegerBytesList mult = divisor.multiply(new BigIntegerBytesList(String.valueOf(r)));
            remainder = tmp.subtract(mult);
            for (; i < n; i++) {
                String remainderString = remainder.toString();
                d1 = dividendo.digits.get(i).getDigit();
                remainderString += d1;
                remainder = new BigIntegerBytesList(remainderString);
                if (remainder.compareTo(divisor) < 0) {
                    r = 0;
                } else {
                    r = inside(remainder, divisor);
                }
                quotientString.append(String.valueOf(r));
                mult = divisor.multiply(new BigIntegerBytesList(String.valueOf(r)));
                remainder = remainder.subtract(mult);
            }
        }
        BigIntegerBytesList quotient = new BigIntegerBytesList(quotientString.toString());
        quotient.negative = this.negative != ((BigIntegerBytesList) val).negative;
        remainder.negative = this.negative;
        BigIntegerBytesList[] result = new BigIntegerBytesList[2];
        result[0] = quotient;
        result[1] = remainder;
        return result;
    }

    @Override
    public double doubleValue() {
        double res = 0;
        double pot = 1;
        for (int i = digits.size() - 1; i >= 0; i--) {
            Digit digit = digits.get(i);
            res += ((double) digit.getDigit()) * pot;
            pot *= 10;
        }
        if (negative) {
            res *= -1;
        }
        return res;
    }

    @Override
    public BigIntegerBytesList flipBit(int n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float floatValue() {
        float res = 0;
        float pot = 1;
        for (int i = digits.size() - 1; i >= 0; i--) {
            Digit digit = digits.get(i);
            res += ((float) digit.getDigit()) * pot;
            pot *= 10;
        }
        if (negative) {
            res *= -1;
        }
        return res;
    }

    @Override
    public BigIntegerBytesList gcd(BigIntegerBytesList val) {

        if (val.equals(ZERO)) {
            return this;
        }
        if (this.equals(ZERO)) {
            return val;
        }

        BigIntegerBytesList a = this;
        BigIntegerBytesList b = val;
        if (a.compareTo(b) > 0) {
            BigIntegerBytesList aux = b;
            b = a;
            a = aux;
        }
        while (!b.equals(ZERO)) {
            BigIntegerBytesList r = a.mod(b);
            a = b;
            b = r;
        }

        return a;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.digits);
        hash = 53 * hash + (this.negative ? 1 : 0);
        return hash;
    }

    @Override
    public int intValue() {
        int res = 0;
        int pot = 1;
        for (int i = digits.size() - 1; i >= 0; i--) {
            Digit digit = digits.get(i);
            res += digit.getDigit() * pot;
            pot *= 10;
        }
        if (negative) {
            res *= -1;
        }
        return res;
    }

    @Override
    public long longValue() {
        long res = 0;
        long pot = 1;
        for (int i = digits.size() - 1; i >= 0; i--) {
            Digit digit = digits.get(i);
            res += ((long) digit.getDigit()) * pot;
            pot *= 10;
        }
        if (negative) {
            res *= -1;
        }
        return res;
    }

    @Override
    public BigIntegerBytesList negate() {
        BigIntegerBytesList bigNeg = new BigIntegerBytesList("0");
        bigNeg.digits = digits;
        bigNeg.negative = !negative;
        return bigNeg;
    }

    @Override
    public BigIntegerBytesList subtract(BigIntegerBytesList val) {
        BigIntegerBytesList minuendo = this;
        BigIntegerBytesList sustraendo = val;
        sustraendo = sustraendo.negate();
        if (minuendo.negative == sustraendo.negative) {
            return minuendo.add(sustraendo);
        } else {
            return minuendo.subtract2(sustraendo);
        }
    }

    @Override
    protected Object clone() {
        BigIntegerBytesList big = this.abs();
        big.negative = this.negative;
        return big;
    }

    @Override
    public byte[] toByteArray() {
        byte[] result = null;
        BigIntegerBytesList quotient = (BigIntegerBytesList) this.clone();
        if (quotient.compareTo(ZERO) == 0) {
            // Si es cero, retornamos el byte cero.
            result = new byte[1];
            result[0] = 0;
        } else {
            // Si no es cero, ejecutamos la división.
            String resultString = getBinaryRepresentation(quotient);
            result = new byte[resultString.length() / 8];
            for (int i = resultString.length(), k = result.length - 1; i > 0; i -= 8, k--) {
                int hasta = i;

                int desde = i - 8;
                String sub = null;
                if (desde >= 0) {
                    sub = resultString.substring(desde, hasta);
                } else {
                    sub = resultString.substring(0, hasta);
                }

                byte b = 0;
                b = (byte) Short.parseShort(sub, 2);
                result[k] = b;
            }
        }
        return result;
    }

    private static String getBinaryRepresentation(BigIntegerBytesList quotient) {
        StringBuilder resultString = new StringBuilder();
        while (quotient.abs().compareTo(ONE) != 0) {
            BigIntegerBytesList[] tmp = quotient.divideAndRemainder(TWO);
            quotient = tmp[0];
            BigIntegerBytesList remainder = tmp[1];
            // Agrego los restos adelante y formo así un string en binario.
            resultString.insert(0, remainder.abs().toString());
        }
        resultString.insert(0, "1");
        if (quotient.negative) {
            // Si el número es negativo, calculo el complemento a dos.
            resultString = new StringBuilder(complement(resultString.toString()));
            resultString.insert(0, '1');
            while (resultString.length() % 8 != 0) {
                resultString.insert(0, '1');
            }
        } else {
            resultString.insert(0, '0');
            while (resultString.length() % 8 != 0) {
                resultString.insert(0, '0');
            }
        }
        return resultString.toString();
    }

    private static String complement(String binaryNumber) {
        StringBuilder result = new StringBuilder(binaryNumber);

        int i = result.length() - 1;
        while (result.charAt(i) == '0') {
            i--;
        }
        i--;
        for (; i >= 0; i--) {
            char c = result.charAt(i);
            if (c == '1') {
                result.replace(i, i + 1, "0");
            } else {
                result.replace(i, i + 1, "1");
            }
        }
        return result.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return this.toString().equals(obj.toString());
    }

    /**
     * Cuantas veces está big2 en big1. Sabiendo que no puede estar más de 9
     * veces.
     *
     * @param big1
     * @param big2
     * @return
     */
    private int inside(BigIntegerBytesList big1, BigIntegerBytesList big2) {
        int r = 1;
        BigIntegerBytesList tmp = new BigIntegerBytesList("0");
        tmp.digits = (ArrayList<Digit>) big2.digits.clone();
        int comp = 0;
        while ((comp = big1.compareTo(tmp)) > 0) {
            tmp = tmp.add(big2);
            r++;
        }
        if (comp != 0) {
            r--;
        }
        return r;
    }

    private BigIntegerBytesList subtract2(BigIntegerBytesList val) {
        BigIntegerBytesList minuendo = this;
        BigIntegerBytesList sustraendo = val;
        StringBuilder stringResult = new StringBuilder();

        if (minuendo.negative && sustraendo.negative) {
            throw new RuntimeException();
        }
        int comp = minuendo.abs().compareTo(sustraendo.abs());
        if (comp == 0) {
            stringResult.append("0");
        } else {
            if (comp < 0) {
                // Swap.
                BigIntegerBytesList tmp = minuendo;
                minuendo = sustraendo;
                sustraendo = tmp;
            }
            // Start
            for (int i = minuendo.digits.size() - 1, j = sustraendo.digits.size() - 1; j >= 0; i--, j--) {
                byte dm = minuendo.digits.get(i).getDigit();
                byte ds = sustraendo.digits.get(j).getDigit();
                int r = 0;
                if (dm >= ds) {
                    r = dm - ds;
                } else {
                    int p = i - 1;
                    int k = i - 1;
                    byte dmOther = minuendo.digits.get(k).getDigit();
                    while (dmOther == 0) {
                        k--;
                        dmOther = minuendo.digits.get(k).getDigit();
                    }
                    if (dmOther > 0) {
                        Digit newDigit = new Digit((byte) (dmOther - 1));
                        minuendo.digits.set(k, newDigit);
                        for (int t = k + 1; t <= p; t++) {
                            newDigit = new Digit((byte) 9);
                            minuendo.digits.set(t, newDigit);
                        }
                    }
                    r = 10 + dm - ds;
                }
                stringResult.insert(0, String.valueOf(r));
            }
            int diff = minuendo.digits.size() - sustraendo.digits.size();
            for (int i = diff - 1; i >= 0; i--) {
                byte dm = minuendo.digits.get(i).getDigit();
                stringResult.insert(0, String.valueOf(dm));
            }
        }
        BigIntegerBytesList result = new BigIntegerBytesList(stringResult.toString());
        result.negative = minuendo.negative;
        return result;
    }

    private String getNumbersOrLetters(int n) {
        if (n < 10) {
            return String.valueOf(n);
        } else {
            char letters[] = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
            return String.valueOf(letters[n - 10]);
        }
    }

    @Override
    public int getLowestSetBit() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isProbablePrime(int certainty) {
        BigIntegerBytesList thisMinusOne = this.subtract(ONE);
        int[] primo = {2,3,5,7,11,13,17,19};
        int s = 0;
        boolean esPrimo = true;
        BigIntegerBytesList a, r, y;
        int j;
        while(thisMinusOne.remainder(TWO).compareTo(ZERO) == 0){
            thisMinusOne = thisMinusOne.divide(TWO);
            s = s + 1;
        }
        r = thisMinusOne;
        thisMinusOne = this.subtract(ONE);
        for (int i = 0; i <= 7; i++) {
            a = new BigIntegerBytesList(String.valueOf(primo[i]));
            y = a.modPow(r, this);
            if(y.compareTo(ONE) != 0 && y.compareTo(thisMinusOne) != 0){
                j = 1;
                while(j <= s - 1 && y.compareTo(thisMinusOne) != 0){
                    y = y.modPow(TWO, this);
                    if(y.compareTo(ONE) == 0){
                        esPrimo = false;
                    }
                    j++;
                }
                if(y.compareTo(thisMinusOne) != 0){
                    esPrimo = false;
                }
            }
        }
        return esPrimo;
    }

    @Override
    public BigIntegerBytesList max(BigIntegerBytesList val) {
        return (this.compareTo(val) > 0) ? this : val;
    }

    @Override
    public BigIntegerBytesList min(BigIntegerBytesList val) {
        return (this.compareTo(val) < 0) ? this : val;
    }

    @Override
    public BigIntegerBytesList mod(BigIntegerBytesList val) {
        if (val.signum() <= 0) {
            throw new ArithmeticException("Val is negative or equals to zero");
        }
        BigIntegerBytesList res = divideAndRemainder(val)[1];
        //http://stackoverflow.com/questions/4403542/how-does-java-do-modulus-calculations-with-negative-numbers
        if (res.negative) {
            res = res.add(val);
        }
        return res;
    }

    @Override
    public BigIntegerBytesList modInverse(BigIntegerBytesList m) {
        if(m.compareTo(ZERO) <= 0){
            throw new ArithmeticException("BigInteger: modulus not positive");
        }
        if(gcd(m).compareTo(ONE) != 0){
            throw new ArithmeticException("BigInteger not invertible");
        }
        BigIntegerBytesList i = ONE;
        for(; i.compareTo(m) < 0; i = i.add(ONE)){
            if(this.multiply(i).mod(m).compareTo(ONE) == 0){
                return i;
            }
        }
        // never it should run this
        return null;
    }

    @Override
    public BigIntegerBytesList modPow(BigIntegerBytesList exponent, BigIntegerBytesList m) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigIntegerBytesList multiply(BigIntegerBytesList val) {
        StandardMultiplication sm = new StandardMultiplication();
        return sm.multiply(this, val);
    }

    @Override
    public BigIntegerBytesList nextProbablePrime() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigIntegerBytesList not() {
        byte big[] = this.toByteArray();
        byte result[] = new byte[big.length];
        for (int i = 0; i < big.length; i++) {
            result[i] = (byte) ~big[i];
        }
        return new BigIntegerBytesList(result);
    }

    @Override
    public BigIntegerBytesList or(BigIntegerBytesList val) {
        byte[] bigMenor = toByteArray();
        byte[] bigMayor = val.toByteArray();
        int min = bigMenor.length;
        int max = bigMayor.length;

        if (min > max) {
            min = bigMayor.length;
            max = bigMenor.length;
            byte tmp[] = bigMenor;
            bigMenor = bigMayor;
            bigMayor = tmp;
        }
        byte[] tmp = new byte[max];
        for (int i = 1; i <= min; i++) {
            tmp[tmp.length - i] = bigMenor[bigMenor.length - i];
        }
        if (bigMenor[0] < 0) {
            for (int i = min + 1; i <= tmp.length; i++) {
                tmp[tmp.length - i] = (byte) 255; // All ones.
            }
        }
        bigMenor = tmp;

        byte[] result = new byte[max];
        for (int i = 0; i < max; i++) {
            result[i] = (byte) (bigMenor[i] | bigMayor[i]);
        }

        BigIntegerBytesList res = new BigIntegerBytesList(result);
        return res;
    }

    @Override
    public BigIntegerBytesList pow(int exponent) {
        if (exponent < 0) {
            throw new ArithmeticException("Exponent is negative");
        }
        if (exponent == 0) {
            return ONE;
        }
        BigIntegerBytesList res = (BigIntegerBytesList) this.clone();
        for (int i = 0; i < exponent - 1; i++) {
            res = res.multiply(this);
        }
        return res;
    }

    @Override
    public BigIntegerBytesList remainder(BigIntegerBytesList val) {
        if (ZERO.equals(val)) {
            throw new ArithmeticException("Val is zero");
        }
        BigIntegerBytesList res = divideAndRemainder(val)[1];
        return res;
    }

    
    @Override
    public BigIntegerBytesList setBit(int n) {
        if(n < 0){
            throw new ArithmeticException("n is invalid");
        }
        String binary;
        if(this.negative){
            binary = complement(this.toString(2).substring(1));
        }else{
            binary = this.toString(2);
        }
        StringBuilder newBinary = new StringBuilder();
        for(int i = 0, j = (binary.length() - 1); i <= n || j >= 0; i++,j--){
           if(i==n){
               newBinary.append('1');
           }else if(j >= 0){
               newBinary.append(binary.charAt(j));
           }else{
               if(this.negative){
                   newBinary.append('1');
               }else{
                   newBinary.append('0');
               }
           }
        }
        newBinary = newBinary.reverse();
        BigIntegerBytesList result;
        if(this.negative){
            result = new BigIntegerBytesList(complement(newBinary.toString()),2);
            result.negative = true;
        }else{
            result = new BigIntegerBytesList(newBinary.toString(),2);
        }
        return result;
    }

    @Override
    public BigIntegerBytesList shiftLeft(int n) {
        if (n < 0) {
            return shiftRight(n * -1);
        }
        String binary = toString(2);
        StringBuilder displacement = new StringBuilder();
        for (int i = 0; i < n; i++) {
            displacement.append("0");
        }
        StringBuilder resultString = new StringBuilder(displacement);
        resultString.insert(0, binary);
        BigIntegerBytesList result = new BigIntegerBytesList(resultString.toString(), 2);
        return result;
    }

    @Override
    public BigIntegerBytesList shiftRight(int n) {
        if (n < 0) {
            return shiftLeft(n * -1);
        }
        String binary = toString(2);
        String padding = "0";
        if (negative) {
            binary = complement(binary);
            padding = "1";
        }
        StringBuilder displacement = new StringBuilder();
        for (int i = 0; i < n; i++) {
            displacement.append(padding);
        }
        StringBuilder resultString = new StringBuilder(displacement);
        resultString.append(binary.substring(0, binary.length() - n));
        String rs = null;
        if (negative) {
            rs = complement(resultString.toString());
        } else {
            rs = resultString.toString();
        }
        BigIntegerBytesList result = new BigIntegerBytesList(rs, 2);
        result.negative = negative;
        return result;
    }

    @Override
    public int signum() {
        int sig = 1;
        if (this.negative) {
            sig = -1;
        } /*else if (this.digits.get(0).getDigit() == 0) {
         sig = 0;
         }*/ else if (this.equals(ZERO)) {
            sig = 0;
        }
        return sig;
    }

    @Override
    public String toString(int radix) {
        if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
            return toString();
        }
        BigIntegerBytesList radixBigInteger = new BigIntegerBytesList(String.valueOf(radix));
        BigIntegerBytesList quotient = (BigIntegerBytesList) this.clone();
        StringBuilder resultString = new StringBuilder();
        while (quotient.abs().compareTo(ZERO) != 0) {
            BigIntegerBytesList[] tmp = quotient.divideAndRemainder(radixBigInteger);
            quotient = tmp[0];
            String remainder = getNumbersOrLetters(tmp[1].abs().intValue());
            resultString.insert(0, remainder);
        }
        if (quotient.negative) {
            resultString.insert(0, "-");
        }
        return resultString.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        boolean FirstDigitIsNotZero = digits.get(0).getDigit() != 0;
        sb.append((negative && FirstDigitIsNotZero) ? "-" : "");
        for (Digit digit : digits) {
            sb.append(digit.getDigit());
        }
        return sb.toString();
    }

    @Override
    public BigIntegerBytesList xor(BigIntegerBytesList val) {
        byte[] bigMenor = toByteArray();
        byte[] bigMayor = val.toByteArray();
        int min = bigMenor.length;
        int max = bigMayor.length;

        if (min > max) {
            min = bigMayor.length;
            max = bigMenor.length;
            byte tmp[] = bigMenor;
            bigMenor = bigMayor;
            bigMayor = tmp;
        }
        byte[] tmp = new byte[max];
        for (int i = 1; i <= min; i++) {
            tmp[tmp.length - i] = bigMenor[bigMenor.length - i];
        }
        if (bigMenor[0] < 0) {
            for (int i = min + 1; i <= tmp.length; i++) {
                tmp[tmp.length - i] = (byte) 255; // All ones.
            }
        }
        bigMenor = tmp;

        byte[] result = new byte[max];
        for (int i = 0; i < max; i++) {
            result[i] = (byte) (bigMenor[i] ^ bigMayor[i]);
        }

        BigIntegerBytesList res = new BigIntegerBytesList(result);
        return res;
    }

}

/**
 * This class represent a digit: 0 to 9.
 */
class Digit {

    private byte digit;

    public Digit(byte digit) {
        this.digit = digit;
    }

    public byte getDigit() {
        return digit;
    }

    public void setDigit(byte digit) {
        this.digit = digit;
    }
}
