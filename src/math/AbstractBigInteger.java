package math;


/**
 *
 * @author Parisi Germán
 * @param <T>
 */
public abstract class AbstractBigInteger<T extends AbstractBigInteger> extends Number  implements Comparable<T> {

    public abstract T abs();
    public abstract T add(T val);
    public abstract T and(T val);
    public abstract T andNot(T val);
    public abstract int bitCount();
    public abstract int bitLength();
    public abstract T clearBit(int n);
    public abstract T divide(T val);
    public abstract T[] divideAndRemainder(T val);
    public abstract T flipBit(int n);
    public abstract T gcd(T val);
    public abstract int getLowestSetBit();
    public abstract boolean isProbablePrime(int certainty);
    public abstract T max(T val);
    public abstract T min(T val);
    public abstract T mod(T val);
    public abstract T modInverse(T val);
    public abstract T modPow(T exponent, T m);
    public abstract T multiply(T val);
    public abstract T negate();
    public abstract T nextProbablePrime();
    public abstract T not();
    public abstract T or(T val);
    public abstract T pow(int exponent);
    public abstract T remainder(T val);
    public abstract T setBit(int n);
    public abstract T shiftLeft(int n);
    public abstract T shiftRight(int n);
    public abstract int signum();
    public abstract T subtract(T val);
    public abstract byte[] toByteArray();
    public abstract String toString(int radix);
    public abstract T xor(T val);

}
