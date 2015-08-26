package math;

/**
 *
 * @author Parisi Germán
 */
public class BigIntegerPositionalArray extends AbstractBigInteger<BigIntegerPositionalArray> {

    private byte number[];

    /**
     * La base del nuevo sistema posicional.
     */
    private final static int base = 100;

    /**
     * Número de dígitos decimales por cada dígito en el nuevo sistema
     * posicional.
     */
    private final static int decimalDigits = 2;

    public BigIntegerPositionalArray(String val) {
        this(val, 10);
    }

    public BigIntegerPositionalArray(String val, int radix) {
        int n = val.length();
        int tam = (int) (n / decimalDigits) + 1;
        number = new byte[tam];
        for (int i = n, j = tam - 1; i >= 0; i -= decimalDigits, j--) {
            int max = Math.max(i - decimalDigits, 0);
            String parts = val.substring(max, i);
            number[j] = Byte.parseByte(parts);
        }
        for (int i = 0; i < number.length; i++) {
            System.out.println(number[i]);
        }
    }


    @Override
    public int intValue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long longValue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float floatValue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double doubleValue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int compareTo(BigIntegerPositionalArray o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public byte[] toByteArray() {
        
        return number;
    }

    public static void main(String args[]) {
        AbstractBigInteger bigInteger = new BigIntegerPositionalArray("135");
        byte[] b = bigInteger.toByteArray();
        for (int i = 0; i < b.length; i++) {
            byte c = b[i];
            System.out.println(c);
        }
    }

    @Override
    public BigIntegerPositionalArray abs() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigIntegerPositionalArray add(BigIntegerPositionalArray val) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigIntegerPositionalArray and(BigIntegerPositionalArray val) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigIntegerPositionalArray andNot(BigIntegerPositionalArray val) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int bitCount() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int bitLength() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigIntegerPositionalArray clearBit(int n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigIntegerPositionalArray divide(BigIntegerPositionalArray val) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigIntegerPositionalArray[] divideAndRemainder(BigIntegerPositionalArray val) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigIntegerPositionalArray flipBit(int n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigIntegerPositionalArray gcd(BigIntegerPositionalArray val) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigIntegerPositionalArray subtract(BigIntegerPositionalArray val) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigIntegerPositionalArray multiply(BigIntegerPositionalArray val) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString(int radix) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getLowestSetBit() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isProbablePrime(int certainty) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigIntegerPositionalArray max(BigIntegerPositionalArray val) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigIntegerPositionalArray min(BigIntegerPositionalArray val) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigIntegerPositionalArray mod(BigIntegerPositionalArray val) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigIntegerPositionalArray modInverse(BigIntegerPositionalArray val) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigIntegerPositionalArray modPow(BigIntegerPositionalArray exponent, BigIntegerPositionalArray m) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigIntegerPositionalArray negate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigIntegerPositionalArray nextProbablePrime() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigIntegerPositionalArray not() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigIntegerPositionalArray or(BigIntegerPositionalArray val) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigIntegerPositionalArray pow(int exponent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigIntegerPositionalArray remainder(BigIntegerPositionalArray val) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigIntegerPositionalArray setBit(int n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigIntegerPositionalArray shiftLeft(int n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigIntegerPositionalArray shiftRight(int n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int signum() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigIntegerPositionalArray xor(BigIntegerPositionalArray val) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
