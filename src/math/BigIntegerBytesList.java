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
    private static final char LETTERS_MINUS[] = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private static final char LETTERS_MAYUS[] = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    /**
     * The digits in big-endian. Each digit represent 0 to 9.
     */
    protected ArrayList<Digit> digits;

    /**
     * Flag indicate if this number is negative.
     */
    protected boolean negative;

    public BigIntegerBytesList(String number) {
        if (number.length() == 0) {
            throw new NumberFormatException("Zero length BigIntegerBytesList");
        }

        // Determine the sign.
        char first = number.charAt(0);
        int since = 0;
        if (first == '-' || first == '+') {
            negative = first == '-';
            since = 1;
        }

        // Delete the zeros of the left.
        int i = since;
        char c = number.charAt(i);
        while (i < number.length() - 1 && c == '0') {
            i++;
            c = number.charAt(i);
        }
        since = i;

        // Create the number.
        this.digits = new ArrayList<>();
        for (i = since; i < number.length(); i++) {
            c = number.charAt(i);
            if (Character.isDigit(c)) {
                Digit digit = new Digit(Byte.parseByte(String.valueOf(c)));
                this.digits.add(digit);
            } else {
                throw new NumberFormatException("For input string: " + number);
            }
        }

        // Special case: -0, because is not negative.
        if (digits.size() == 1 && digits.get(0).getDigit() == 0) {
            negative = false;
        }
    }

    /**
     * val is in complement to 2.
     *
     * @param val is in complement to 2
     * @return the binary number
     */
    private static String getBinaryComplementTo2(byte[] val) {
        String binary = toStringBytes(val);
        if (val[0] < 0) {
            binary = complement(binary);
        }
        return binary;
    }

    /**
     * to string of val. result.len is multiple of 8.
     *
     * @param val
     * @return
     */
    private static String toStringBytes(byte[] val) {
        StringBuilder resString = new StringBuilder();
        for (int i = 0; i < val.length; i++) {
            String binary = Integer.toBinaryString(val[i]);
            // Substring for negatives because the integers are 32 bits .
            binary = binary.substring(Math.max(binary.length() - 8, 0));
            // binary.length() <= 8
            while (binary.length() < 8) {
                binary = "0" + binary;
            }
            resString.append(binary);
        }
        return resString.toString();
    }

    public BigIntegerBytesList(byte[] val) {
        this(getBinaryComplementTo2(val), 2);
        if (val[0] < 0) {
            negative = true;
        }
    }

    private static String magnitude(int signum, byte[] magnitude) {
        if (signum < -1 || signum > 1) {
            throw new NumberFormatException("Invalid signum value");
        }
        if (signum == 0 && magnitude.length > 0) {
            throw new NumberFormatException("signum-magnitude mismatch");
        }
        if (magnitude.length == 0 || signum == 0) {
            return "0";
        }
        return toStringBytes(magnitude);
    }

    public BigIntegerBytesList(int signum, byte[] magnitude) {
        this(magnitude(signum, magnitude), 2);
        if (signum == -1) {
            negative = true;
        }
    }

    public BigIntegerBytesList(int bitLength, int certainty, Random rnd) {
        this(getRandomPrime(bitLength, certainty, rnd).toString());
    }

    private static BigIntegerBytesList getRandomPrime(int bitLength, int certainty, Random rnd) {
        BigIntegerBytesList from = TWO.pow(bitLength - 1).add(ONE);
        BigIntegerBytesList until = TWO.pow(bitLength);
        int factor = 100;
        int r = (int) (rnd.nextDouble() * factor);
        BigIntegerBytesList diff = until.subtract(from);
        BigIntegerBytesList big = valueOf(r).multiply(diff).divide(valueOf(factor)).add(from);
        BigIntegerBytesList i;
        if (big.isProbablePrime(certainty)) {
            return big;
        } else {
            if (big.mod(TWO).compareTo(ZERO) == 0) {
                i = big.add(ONE);
            } else {
                i = big.add(TWO);
            }
            while (!i.isProbablePrime(certainty)) {
                i = i.add(TWO);
                if (i.compareTo(until) >= 0) {
                    i = from;
                }
            }
        }
        return i;
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

        int validBits = numBits % 8;
        int mask;
        if (validBits == 0) {
            mask = (int) 0xff;
        } else {
            mask = (int) (Math.pow(2, validBits) - 1);
        }
        bytes[0] &= mask;
        return bytes;
    }

    public BigIntegerBytesList(String val, int radix) {
        if (val.length() == 0) {
            throw new NumberFormatException("Zero length BigIntegerBytesList");
        }
        if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
            throw new NumberFormatException("Radix out of range");
        }

        // Determine the sign.
        char first = val.charAt(0);
        int until = 0;
        if (first == '-' || first == '+') {
            negative = first == '-';
            until = 1;
        }

        // 439 = 4 * radix ^ 2 + 3 * radix ^ 1 + 9 * radix ^ 0
        BigIntegerBytesList mult = ONE; // pow(radix, 0) = 1
        BigIntegerBytesList result = ZERO;
        for (int i = val.length() - 1; i >= until; i--) {
            char c = val.charAt(i);
            int value = Character.digit(c, radix);
            if (value > -1) {
                result = result.add(mult.multiply(valueOf(value)));
                mult = mult.multiply(valueOf(radix));
            } else {
                throw new NumberFormatException("For input string: " + val);
            }
        }
        this.digits = result.digits;
    }

    @Override
    public BigIntegerBytesList abs() {
        BigIntegerBytesList bigAbs = new BigIntegerBytesList("0");
        bigAbs.digits = new ArrayList<>();
        for (int i = 0; i < digits.size(); i++) {
            bigAbs.digits.add((Digit) digits.get(i).clone());
        }
        bigAbs.negative = false;
        return bigAbs;
    }

    /**
     * Returns a BigInteger whose value is (this + val).
     *
     * @param val value to be added to this BigInteger.
     * @return this + val
     */
    @Override
    public BigIntegerBytesList add(BigIntegerBytesList val) {
        BigIntegerBytesList result = null;
        StringBuilder resultString = new StringBuilder();

        // Check the opposite sign
        if ((this.negative && !val.negative) || (!this.negative && val.negative)) {
            return subtract2(val);
        }

        BigIntegerBytesList sumando1 = (BigIntegerBytesList) this.clone();
        BigIntegerBytesList sumando2 = (BigIntegerBytesList) val.clone();
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

    /**
     * Returns a BigInteger whose value is (this & val). (This method returns a
     * negative BigInteger if and only if this and val are both negative.)
     *
     * @param val value to be AND'ed with this BigInteger.
     * @return this & val
     */
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
        // Copiar para tener el mismo tamaño.
        bigMenor = extendTheSize(bigMenor, max);

        // Operation and.
        byte[] result = new byte[max];
        for (int i = 0; i < max; i++) {
            result[i] = (byte) (bigMenor[i] & bigMayor[i]);
        }

        BigIntegerBytesList res = new BigIntegerBytesList(result);
        return res;
    }

    /**
     * Extiende el arreglo de bytes val a la cantidad de bytes max de acuerdo al
     * signo de val. Si val es negativo entonces se rellena con 1s, si es
     * positivo se rellena con 0s.
     *
     * @param val
     * @param max
     * @return
     */
    private byte[] extendTheSize(byte[] val, int max) {
        int min = val.length;
        byte[] tmp = new byte[max];
        for (int i = 1; i <= min; i++) {
            tmp[tmp.length - i] = val[val.length - i];
        }
        if (val[0] < 0) {
            for (int i = min + 1; i <= tmp.length; i++) {
                tmp[tmp.length - i] = (byte) 255; // All ones.
            }
        }
        return tmp;
    }

    /**
     * Returns a BigInteger whose value is (this & ~val). This method, which is
     * equivalent to and(val.not()), is provided as a convenience for masking
     * operations. (This method returns a negative BigInteger if and only if
     * this is negative and val is positive.)
     *
     * @param val value to be complemented and AND'ed with this BigInteger.
     * @return this & ~val
     */
    @Override
    public BigIntegerBytesList andNot(BigIntegerBytesList val) {
        return this.and(val.not());
    }

    /**
     * Returns the number of bits in the two's complement representation of this
     * BigInteger that differ from its sign bit. This method is useful when
     * implementing bit-vector style sets atop BigIntegers.
     *
     * @return number of bits in the two's complement representation of this
     * BigInteger that differ from its sign bit.
     */
    @Override
    public int bitCount() {
        String binary = toString(2);
        int count = 0;
        char symbol = '0';
        int since = 0;
        if (this.negative) {
            symbol = '1';
            since = 1; // Excluyo el símbolo -.
        }
        for (int i = since; i < binary.length(); i++) {
            if (binary.charAt(i) != symbol) {
                count++;
            }
        }
        return count;
    }

    /**
     * Returns the number of bits in the minimal two's-complement representation
     * of this BigInteger, excluding a sign bit. For positive BigIntegers, this
     * is equivalent to the number of bits in the ordinary binary
     * representation. (Computes (ceil(log2(this < 0 ? -this : this+1))).)
     * @return number of
     *
     * bits in the minimal two's-complement representation of this BigInteger,
     * excluding a sign bit.
     */
    @Override
    public int bitLength() {
        if (this.negative) {
            return toString(2).length() - 1;
        } else {
            return toString(2).length();
        }
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
        BigIntegerBytesList mask = ONE.shiftLeft(n).not();
        return and(mask);
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

    /**
     * Returns a BigInteger whose value is (this / val).
     *
     * @param val value by which this BigInteger is to be divided.
     * @return this / val
     * @throws ArithmeticException if val is zero.
     */
    @Override
    public BigIntegerBytesList divide(BigIntegerBytesList val) {
        return divideAndRemainder(val)[0];
    }

    /**
     * Returns an array of two BigIntegers containing (this / val) followed by
     * (this % val).
     *
     * @param val value by which this BigInteger is to be divided, and the
     * remainder computed.
     * @return an array of two BigIntegers: the quotient (this / val) is the
     * initial element, and the remainder (this % val) is the final element.
     * @throws ArithmeticException if val is zero.
     */
    @Override
    public BigIntegerBytesList[] divideAndRemainder(BigIntegerBytesList val) {
        if (val.equals(ZERO)) {
            throw new ArithmeticException("BigInteger divide by zero");
        }
        BigIntegerBytesList dividendo = this.abs();
        BigIntegerBytesList divisor = val.abs();

        StringBuilder quotientString = new StringBuilder();
        int n = dividendo.digits.size();
        int i = 0;
        int d1 = dividendo.digits.get(i).getDigit();
        BigIntegerBytesList tmp = new BigIntegerBytesList(String.valueOf(d1));
        BigIntegerBytesList remainder = ZERO;
        // Selecciono los primeros digitos mayores que el divisor.
        // Si no es mayor, selecciono todos.
        while (tmp.compareTo(divisor) < 0 && i < n - 1) {
            i++;
            d1 = dividendo.digits.get(i).getDigit();
            tmp.digits.add(new Digit((byte) d1));
        }

        int times = inside(tmp, divisor);
        i++;
        quotientString.append(String.valueOf(times));

        // Multiplico divisor por el cociente.
        BigIntegerBytesList mult = divisor.multiply(new BigIntegerBytesList(String.valueOf(times)));

        // Hago la resta.
        remainder = tmp.subtract(mult);

        // A partir de este momento aplico el algoritmo de la división.
        for (; i < n; i++) {
            String remainderString = remainder.toString();
            d1 = dividendo.digits.get(i).getDigit();
            remainderString += d1;
            remainder = new BigIntegerBytesList(remainderString);
            if (remainder.compareTo(divisor) < 0) {
                times = 0;
            } else {
                times = inside(remainder, divisor);
            }
            quotientString.append(String.valueOf(times));
            mult = divisor.multiply(new BigIntegerBytesList(String.valueOf(times)));
            remainder = remainder.subtract(mult);
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

    /**
     * Returns a BigInteger whose value is equivalent to this BigInteger with
     * the designated bit flipped. (Computes (this ^ (1<<n)).)
     *
     * @param n index of bit to flip.
     * @return this ^ (1<<n)
     * @throws ArithmeticException n is negative.
     */
    @Override
    public BigIntegerBytesList flipBit(int n) {
        if (n < 0) {
            throw new ArithmeticException("n is negative");
        }
        BigIntegerBytesList result = this.xor(ONE.shiftLeft(n));
        return result;
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

    /**
     * Returns a BigInteger whose value is the greatest common divisor of
     * abs(this) and abs(val). Returns 0 if this==0 && val==0.
     *
     * @param val value with which the GCD is to be computed.
     * @return GCD(abs(this), abs(val))
     */
    @Override
    public BigIntegerBytesList gcd(BigIntegerBytesList val) {
        BigIntegerBytesList a = (BigIntegerBytesList) this.clone();
        BigIntegerBytesList b = (BigIntegerBytesList) val.clone();
        if (b.equals(ZERO)) {
            return a;
        }
        if (a.equals(ZERO)) {
            return b;
        }

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
        bigNeg.digits = new ArrayList<>();
        for (int i = 0; i < digits.size(); i++) {
            bigNeg.digits.add((Digit) digits.get(i).clone());
        }
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
            } else if (c == '0') {
                result.replace(i, i + 1, "1");
            } else {
                // Ignore
                throw new NumberFormatException("Error");
            }
        }
        return result.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return this.toString().equals(obj.toString());
    }

    /**
     * Retorna cuantas veces está big2 en big1. Sabiendo que no puede estar más
     * de 9 veces.
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
        // Si me paso en el while anterior resto uno.
        // Me doy cuenta que me paso cuando comp vale = -1 porque si vale 0
        // es porque big1 es múltiplo de big2.
        if (comp != 0) {
            r--;
        }
        return r;
    }

    private BigIntegerBytesList subtract2(BigIntegerBytesList val) {
        BigIntegerBytesList minuendo = (BigIntegerBytesList) this.clone();
        BigIntegerBytesList sustraendo = (BigIntegerBytesList) val.clone();
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

    /**
     * Return the value of the numeral.
     *
     * @param c the numeral
     * @return the value
     * @deprecated
     */
    private int getValueDigit(char c) {
        if (Character.isDigit(c)) {
            return Integer.parseInt(String.valueOf(c));
        } else {
            if (c >= 65 && c <= 90) {
                // MAYUS
                return c - 55;
            } else if (c >= 97 && c <= 122) {
                // MINUS
                return c - 87;
            } else {
                throw new NumberFormatException("Error in the number format: " + c);
            }
        }
    }

    private static String getNumbersOrLetters(int n) {
        if (n < 10) {
            return String.valueOf(n);
        } else {
            return String.valueOf(LETTERS_MINUS[n - 10]);
        }
    }

    @Override
    public int getLowestSetBit() {
        if (this.equals(ZERO)) {
            return -1;
        }
        String binary = this.toString(2);
        for (int i = 0, j = binary.length() - 1; j >= 0; i++, j--) {
            if (binary.charAt(j) == '1') {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean isProbablePrime(int certainty) {
        int[] primes = {2, 3, 5, 7, 11, 13, 17, 19};
        for (int i = 0; i < primes.length; i++) {
            if (valueOf(primes[i]).equals(this)) {
                return true;
            }
        }
        BigIntegerBytesList thisMinusOne = this.subtract(ONE);
        int s = 0;
        boolean esPrimo = true;
        BigIntegerBytesList a, r, y;
        int j;
        while (thisMinusOne.remainder(TWO).compareTo(ZERO) == 0) {
            thisMinusOne = thisMinusOne.divide(TWO);
            s = s + 1;
        }
        r = thisMinusOne;
        thisMinusOne = this.subtract(ONE);
        for (int i = 0; i <= certainty; i++) {
            a = new BigIntegerBytesList(String.valueOf(primes[i]));
            y = a.modPow(r, this);
            if (y.compareTo(ONE) != 0 && y.compareTo(thisMinusOne) != 0) {
                j = 1;
                while (j <= s - 1 && y.compareTo(thisMinusOne) != 0) {
                    y = y.modPow(TWO, this);
                    if (y.compareTo(ONE) == 0) {
                        esPrimo = false;
                    }
                    j++;
                }
                if (y.compareTo(thisMinusOne) != 0) {
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
        BigIntegerBytesList res = remainder(val);
        //http://stackoverflow.com/questions/4403542/how-does-java-do-modulus-calculations-with-negative-numbers
        if (res.negative) {
            res = res.add(val);
        }
        return res;
    }

    @Override
    public BigIntegerBytesList modInverse(BigIntegerBytesList m) {
        if (m.compareTo(ZERO) <= 0) {
            throw new ArithmeticException("BigInteger: modulus not positive");
        }
        if (gcd(m).compareTo(ONE) != 0) {
            throw new ArithmeticException("BigInteger not invertible");
        }
        BigIntegerBytesList i = ONE;
        for (; i.compareTo(m) < 0; i = i.add(ONE)) {
            if (this.multiply(i).mod(m).compareTo(ONE) == 0) {
                return i;
            }
        }
        // never it should run this
        return null;
    }

    @Override
    public BigIntegerBytesList modPow(BigIntegerBytesList exponent, BigIntegerBytesList m) {
        if (m.compareTo(ZERO) <= 0) {
            throw new ArithmeticException("BigInteger: modulus not positive");
        }
        boolean exponentNeg = exponent.negative;
        if (exponentNeg && mod(m).compareTo(ZERO) == 0) {
            throw new ArithmeticException("BigInteger not invertible");
        } else if (mod(m).compareTo(ZERO) == 0) {
            return (BigIntegerBytesList) ZERO.clone();
        }
        exponent = exponent.abs();
        BigIntegerBytesList i = ONE;
        BigIntegerBytesList a = this.mod(m);
        BigIntegerBytesList r = (BigIntegerBytesList) a.clone();
        for (; i.compareTo(exponent) < 0; i = i.add(ONE)) {
            r = r.multiply(a).mod(m);
        }
        if (exponentNeg) {
            r = r.modInverse(m);
        }
        return r;
    }

    /**
     * Returns a BigInteger whose value is (this * val).
     *
     * @param val value to be multiplied by this BigInteger.
     * @return this * val
     */
    @Override
    public BigIntegerBytesList multiply(BigIntegerBytesList val) {
        StandardMultiplication sm = new StandardMultiplication();
        return sm.multiply(this, val);
    }

    private BigIntegerBytesList nextProbablePrime(int certainty) {
        if (compareTo(ZERO) < 0) {
            throw new ArithmeticException("start < 0: -1");
        }
        if (compareTo(ONE) <= 0) {
            return (BigIntegerBytesList) TWO.clone();
        }
        BigIntegerBytesList i;
        if (this.mod(TWO).compareTo(ZERO) == 0) {
            // even
            i = this.add(ONE);
        } else {
            // odd
            i = this.add(TWO);
        }
        while (!i.isProbablePrime(certainty)) {
            i = i.add(TWO);
        }
        return i;
    }

    @Override
    public BigIntegerBytesList nextProbablePrime() {
        return nextProbablePrime(100);
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

        bigMenor = extendTheSize(bigMenor, max);

        byte[] result = new byte[max];
        for (int i = 0; i < max; i++) {
            result[i] = (byte) (bigMenor[i] | bigMayor[i]);
        }

        BigIntegerBytesList res = new BigIntegerBytesList(result);
        return res;
    }

    /**
     * Returns a BigInteger whose value is (this^exponent). Note that exponent
     * is an integer rather than a BigInteger.
     *
     * @param exponent exponent to which this BigInteger is to be raised.
     * @return this^exponent
     * @throws ArithmeticException - exponent is negative. (This would cause the
     * operation to yield a non-integer value.)
     */
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
        if (n < 0) {
            throw new ArithmeticException("n is invalid");
        }
        String binary;
        if (this.negative) {
            binary = complement(this.toString(2).substring(1));
        } else {
            binary = this.toString(2);
        }
        StringBuilder newBinary = new StringBuilder();
        for (int i = 0, j = (binary.length() - 1); i <= n || j >= 0; i++, j--) {
            if (i == n) {
                newBinary.append('1');
            } else if (j >= 0) {
                newBinary.append(binary.charAt(j));
            } else {
                if (this.negative) {
                    newBinary.append('1');
                } else {
                    newBinary.append('0');
                }
            }
        }
        newBinary = newBinary.reverse();
        BigIntegerBytesList result;
        if (this.negative) {
            result = new BigIntegerBytesList(complement(newBinary.toString()), 2);
            result.negative = true;
        } else {
            result = new BigIntegerBytesList(newBinary.toString(), 2);
        }
        return result;
    }

    @Override
    public BigIntegerBytesList shiftLeft(int n) {
        if (n < 0) {
            return shiftRight(n * -1);
        }
        String binary = toString(2);
        StringBuilder resultString = new StringBuilder(binary);
        for (int i = 0; i < n; i++) {
            resultString.append("0");
        }
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
            binary = complement(binary.substring(1));
            padding = "1";
        }
        StringBuilder displacement = new StringBuilder();
        for (int i = 0; i < n; i++) {
            displacement.append(padding);
        }
        StringBuilder resultString = new StringBuilder(displacement);
        resultString.append(binary.substring(0, Math.max(0, binary.length() - n)));
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
        } else if (this.digits.get(0).getDigit() == 0) {
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
            String numeral = getNumbersOrLetters(tmp[1].abs().intValue());
            resultString.insert(0, numeral);
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

        bigMenor = extendTheSize(bigMenor, max);

        byte[] result = new byte[max];
        for (int i = 0; i < max; i++) {
            result[i] = (byte) (bigMenor[i] ^ bigMayor[i]);
        }

        BigIntegerBytesList res = new BigIntegerBytesList(result);
        return res;
    }

    @Override
    public boolean testBit(int n) {
        if (n < 0) {
            throw new ArithmeticException("n is negative");
        }
        boolean result = !this.and(ONE.shiftLeft(n)).equals(ZERO);
        return result;
    }

    public static BigIntegerBytesList probablePrime(int bitLength, Random rnd) {
        return getRandomPrime(bitLength, 100, rnd);
    }

    public static BigIntegerBytesList valueOf(long val) {
        return new BigIntegerBytesList(String.valueOf(val));
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

    @Override
    protected Object clone() {
        return new Digit(digit);
    }
}

//Son las 3:50 am
