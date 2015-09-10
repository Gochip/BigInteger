package math;

import java.math.BigInteger;
import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import sun.security.krb5.JavaxSecurityAuthKerberosAccess;

/**
 *
 * @author Parisi Germán
 */
public class BigIntegerBytesListTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    public BigIntegerBytesListTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    private AbstractBigInteger createNumber(String number) {
        return new math.BigIntegerBytesList(number);
    }

    private AbstractBigInteger createNumber(byte[] b) {
        return new math.BigIntegerBytesList(b);
    }

    private AbstractBigInteger createNumber(String number, int radix) {
        return new math.BigIntegerBytesList(number, radix);
    }

    private AbstractBigInteger createNumber(int number, byte[] magnitude) {
        return new math.BigIntegerBytesList(number, magnitude);
    }

    // TEST numberFormatException
    @Test
    public void testNumberFormatExceptionEmpty() {
        exception.expect(NumberFormatException.class);
        AbstractBigInteger big = createNumber("");
    }

    @Test
    public void testNumberFormatExceptionErrorNumber() {
        exception.expect(NumberFormatException.class);
        AbstractBigInteger big = createNumber("123456a");
    }

    @Test
    public void testNumberFormatExceptionErrorNumber2() {
        exception.expect(NumberFormatException.class);
        AbstractBigInteger big = createNumber("-d");
    }

    @Test
    public void testNumberFormatExceptionErrorNumber3() {
        exception.expect(NumberFormatException.class);
        AbstractBigInteger big = createNumber("+d");
    }

    @Test
    public void testNumberFormatExceptionWhiteSpace() {
        exception.expect(NumberFormatException.class);
        AbstractBigInteger big = createNumber(" ");
    }

    // TEST toString
    @Test
    public void testToStringNormal() {
        AbstractBigInteger big = createNumber("200");
        String expResult = "200";
        assertEquals(expResult, big.toString());
    }

    @Test
    public void testToStringLeadingZeros() {
        BigInteger bigOriginal1 = new BigInteger("0015");
        AbstractBigInteger big1 = createNumber("0015");
        assertEquals(bigOriginal1.toString(), big1.toString());

        bigOriginal1 = new BigInteger("00000");
        big1 = createNumber("00000");
        assertEquals(bigOriginal1.toString(), big1.toString());
    }

    @Test
    public void testToStringNormalPos() {
        AbstractBigInteger big = createNumber("+200");
        String expResult = "200";
        assertEquals(expResult, big.toString());
    }

    @Test
    public void testToStringNormalNeg() {
        AbstractBigInteger big = createNumber("-2350");
        String expResult = "-2350";
        assertEquals(expResult, big.toString());
    }

    @Test
    public void testToStringLarge() {
        AbstractBigInteger big = createNumber("123456789987654321123456789987654321");
        String expResult = "123456789987654321123456789987654321";
        assertEquals(expResult, big.toString());
    }

    // TEST toString(int radix)
    @Test
    public void testToStringRadix2() {
        BigInteger bigOriginal = new BigInteger("200");
        AbstractBigInteger big = createNumber("200");
        assertEquals(bigOriginal.toString(2), big.toString(2));
    }

    @Test
    public void testToStringRadix2Negative() {
        BigInteger bigOriginal = new BigInteger("-20200");
        AbstractBigInteger big = createNumber("-20200");
        assertEquals(bigOriginal.toString(2), big.toString(2));
    }

    @Test
    public void testToStringRadixN1() {
        BigInteger bigOriginal = new BigInteger("-15001659815");
        AbstractBigInteger big = createNumber("-15001659815");
        for (int i = 3; i <= 45; i++) {
            assertEquals(bigOriginal.toString(i), big.toString(i));
        }
    }

    @Test
    public void testToStringRadixN2() {
        BigInteger bigOriginal = new BigInteger("99981785");
        AbstractBigInteger big = createNumber("99981785");
        for (int i = 3; i <= 45; i++) {
            assertEquals(bigOriginal.toString(i), big.toString(i));
        }
    }

    @Test
    public void testToStringRadixExtreme() {
        BigInteger bigOriginal = new BigInteger("21651185641565612586123");
        AbstractBigInteger big = createNumber("21651185641565612586123");
        assertEquals(bigOriginal.toString(5), big.toString(5));
    }

    // TEST intValue
    @Test
    public void testIntValueNormal() {
        AbstractBigInteger big1 = createNumber("20");
        int expResult = 20;
        assertEquals(expResult, big1.intValue());
        AbstractBigInteger big2 = createNumber("-25");
        expResult = -25;
        assertEquals(expResult, big2.intValue());
    }

    @Test
    public void testIntValueLimiteCases1() {
        AbstractBigInteger big1 = createNumber("2147483647");
        int expResult = 2147483647;
        assertEquals(expResult, big1.intValue());
        AbstractBigInteger big2 = createNumber("2147483648");
        expResult = -2147483648;
        assertEquals(expResult, big2.intValue());
    }

    @Test
    public void testIntValueLimiteCases2() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("4294967296");
        AbstractBigInteger big1 = createNumber("4294967296");
        assertEquals(bigOriginal1.intValue(), big1.intValue());

        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("2147583650");
        AbstractBigInteger big2 = createNumber("2147583650");
        assertEquals(bigOriginal2.intValue(), big2.intValue());

        java.math.BigInteger bigOriginal3 = new java.math.BigInteger("-2147583650");
        AbstractBigInteger big3 = createNumber("-2147583650");
        assertEquals(bigOriginal3.intValue(), big3.intValue());
    }

    // TEST longValue
    @Test
    public void testLongValueNormal() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("4294967296");
        AbstractBigInteger big1 = createNumber("4294967296");
        assertEquals(bigOriginal1.longValue(), big1.longValue());

        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("18446744073709551616");
        AbstractBigInteger big2 = createNumber("18446744073709551616");
        assertEquals(bigOriginal2.longValue(), big2.longValue());

        java.math.BigInteger bigOriginal3 = new java.math.BigInteger("9223372036854775808");
        AbstractBigInteger big3 = createNumber("9223372036854775808");
        assertEquals(bigOriginal3.longValue(), big3.longValue());
    }

    @Test
    public void testLongValueExtreme() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("18446744073709551616184467440737095516161844674407370955161");
        AbstractBigInteger big1 = createNumber("18446744073709551616184467440737095516161844674407370955161");
        assertEquals(bigOriginal1.longValue(), big1.longValue());

        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("-18446744073709551616184467440737095516161844674407370955161");
        AbstractBigInteger big2 = createNumber("-18446744073709551616184467440737095516161844674407370955161");
        assertEquals(bigOriginal2.longValue(), big2.longValue());
    }

    // TEST floatValue
    @Test
    public void testFloatValueNormal() {
        float delta = 0f;
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-4294");
        AbstractBigInteger big1 = createNumber("-4294");
        assertEquals(bigOriginal1.floatValue(), big1.floatValue(), delta);

        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("184");
        AbstractBigInteger big2 = createNumber("184");
        assertEquals(bigOriginal2.floatValue(), big2.floatValue(), delta);

        java.math.BigInteger bigOriginal3 = new java.math.BigInteger("9223372");
        AbstractBigInteger big3 = createNumber("9223372");
        assertEquals(bigOriginal3.floatValue(), big3.floatValue(), delta);
    }

    @Test
    public void testFloatValueLimiteCases() {
        float delta = 0f;
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-2147483247");
        AbstractBigInteger big1 = createNumber("-2147483247");
        assertEquals(bigOriginal1.floatValue(), big1.floatValue(), delta);

        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("2147483248");
        AbstractBigInteger big2 = createNumber("2147483248");
        assertEquals(bigOriginal2.floatValue(), big2.floatValue(), delta);

        java.math.BigInteger bigOriginal3 = new java.math.BigInteger("4294967296");
        AbstractBigInteger big3 = createNumber("4294967296");
        assertEquals(bigOriginal3.floatValue(), big3.floatValue(), delta);
    }

    @Test
    public void testFloatValueExtreme() {
        float delta = 0f;
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-21474832472147483247214748324721474832472147483247214748324");
        AbstractBigInteger big1 = createNumber("-21474832472147483247214748324721474832472147483247214748324");
        assertEquals(bigOriginal1.floatValue(), big1.floatValue(), delta);

        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("214748324821474832482147483248214748324821474832");
        AbstractBigInteger big2 = createNumber("214748324821474832482147483248214748324821474832");
        assertEquals(bigOriginal2.floatValue(), big2.floatValue(), delta);
    }

    // TEST doubleValue
    @Test
    public void testDoubleValueNormal() {
        double delta = 0d;
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-4294");
        AbstractBigInteger big1 = createNumber("-4294");
        assertEquals(bigOriginal1.doubleValue(), big1.doubleValue(), delta);

        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("184");
        AbstractBigInteger big2 = createNumber("184");
        assertEquals(bigOriginal2.doubleValue(), big2.doubleValue(), delta);

        java.math.BigInteger bigOriginal3 = new java.math.BigInteger("9223372");
        AbstractBigInteger big3 = createNumber("9223372");
        assertEquals(bigOriginal3.doubleValue(), big3.doubleValue(), delta);
    }

    @Test
    public void testDoubleValueLimiteCases() {
        double delta = 0d;
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-9223372036854775808");
        AbstractBigInteger big1 = createNumber("-9223372036854775808");
        assertEquals(bigOriginal1.doubleValue(), big1.doubleValue(), delta);

        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("9223372036854775808");
        AbstractBigInteger big2 = createNumber("9223372036854775808");
        assertEquals(bigOriginal2.doubleValue(), big2.doubleValue(), delta);

        java.math.BigInteger bigOriginal3 = new java.math.BigInteger("18446744073709551616");
        AbstractBigInteger big3 = createNumber("18446744073709551616");
        assertEquals(bigOriginal3.doubleValue(), big3.doubleValue(), delta);
    }

    @Test
    public void testDoubleValueExtreme() {
        double delta = 0d;
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-21474832472147483247214748324721474832472147483247214748324");
        AbstractBigInteger big1 = createNumber("-21474832472147483247214748324721474832472147483247214748324");
        assertEquals(bigOriginal1.doubleValue(), big1.doubleValue(), delta);

        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("21474832472147483247214748324721474832472147483247214748324");
        AbstractBigInteger big2 = createNumber("21474832472147483247214748324721474832472147483247214748324");
        assertEquals(bigOriginal2.doubleValue(), big2.doubleValue(), delta);
    }

    // TEST abs
    @Test
    public void testAbs() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-21474832");
        AbstractBigInteger big1 = createNumber("-21474832");
        assertEquals(bigOriginal1.abs().toString(), big1.abs().toString());

        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("21474832");
        AbstractBigInteger big2 = createNumber("21474832");
        assertEquals(bigOriginal2.abs().toString(), big2.abs().toString());
    }

    @Test
    public void testAbs2() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-0");
        AbstractBigInteger big1 = createNumber("-0");
        assertEquals(bigOriginal1.abs().toString(), big1.abs().toString());

        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("0");
        AbstractBigInteger big2 = createNumber("0");
        assertEquals(bigOriginal2.abs().toString(), big2.abs().toString());
    }

    // TEST add
    @Test
    public void testAddWithEqualCiphers() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("245789");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("359123");
        AbstractBigInteger big1 = createNumber("245789");
        AbstractBigInteger big2 = createNumber("359123");
        assertEquals(bigOriginal1.add(bigOriginal2).toString(), big1.add(big2).toString());
    }

    @Test
    public void testAddWithEqualCiphersWithCarriage() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("245789");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("959123");
        AbstractBigInteger big1 = createNumber("245789");
        AbstractBigInteger big2 = createNumber("959123");
        assertEquals(bigOriginal1.add(bigOriginal2).toString(), big1.add(big2).toString());
    }

    @Test
    public void testAddWithDistinctCiphers() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("245789");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("9591");
        AbstractBigInteger big1 = createNumber("245789");
        AbstractBigInteger big2 = createNumber("9591");
        assertEquals(bigOriginal1.add(bigOriginal2).toString(), big1.add(big2).toString());

        bigOriginal1 = new java.math.BigInteger("2453");
        bigOriginal2 = new java.math.BigInteger("959120");
        big1 = createNumber("2453");
        big2 = createNumber("959120");
        assertEquals(bigOriginal1.add(bigOriginal2).toString(), big1.add(big2).toString());
    }

    @Test
    public void testAddWithEqualCiphersNegative() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-245789");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("-959123");
        AbstractBigInteger big1 = createNumber("-245789");
        AbstractBigInteger big2 = createNumber("-959123");
        assertEquals(bigOriginal1.add(bigOriginal2).toString(), big1.add(big2).toString());
    }

    @Test
    public void testAddWithDistinctCiphersNegative() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-245789");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("-9591");
        AbstractBigInteger big1 = createNumber("-245789");
        AbstractBigInteger big2 = createNumber("-9591");
        assertEquals(bigOriginal1.add(bigOriginal2).toString(), big1.add(big2).toString());

        bigOriginal1 = new java.math.BigInteger("-2453");
        bigOriginal2 = new java.math.BigInteger("-959120");
        big1 = createNumber("-2453");
        big2 = createNumber("-959120");
        assertEquals(bigOriginal1.add(bigOriginal2).toString(), big1.add(big2).toString());
    }

    @Test
    public void testAddWithDistinctCiphersNegativeAndPositive() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-245789");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("9591");
        AbstractBigInteger big1 = createNumber("-245789");
        AbstractBigInteger big2 = createNumber("9591");
        assertEquals(bigOriginal1.add(bigOriginal2).toString(), big1.add(big2).toString());

        bigOriginal1 = new java.math.BigInteger("-2453");
        bigOriginal2 = new java.math.BigInteger("959120");
        big1 = createNumber("-2453");
        big2 = createNumber("959120");
        assertEquals(bigOriginal1.add(bigOriginal2).toString(), big1.add(big2).toString());
    }

    // TEST subtract
    @Test
    public void testSubstract1() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("9000");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("8000");
        AbstractBigInteger big1 = createNumber("9000");
        AbstractBigInteger big2 = createNumber("8000");
        assertEquals(bigOriginal1.subtract(bigOriginal2).toString(), big1.subtract(big2).toString());

        bigOriginal1 = new java.math.BigInteger("37892");
        bigOriginal2 = new java.math.BigInteger("16221");
        big1 = createNumber("37892");
        big2 = createNumber("16221");
        assertEquals(bigOriginal1.subtract(bigOriginal2).toString(), big1.subtract(big2).toString());
    }

    @Test
    public void testSubstract2() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("20520");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("368950");
        AbstractBigInteger big1 = createNumber("20520");
        AbstractBigInteger big2 = createNumber("368950");
        assertEquals(bigOriginal1.subtract(bigOriginal2).toString(), big1.subtract(big2).toString());

        bigOriginal1 = new java.math.BigInteger("0");
        bigOriginal2 = new java.math.BigInteger("1456");
        big1 = createNumber("0");
        big2 = createNumber("1456");
        assertEquals(bigOriginal1.subtract(bigOriginal2).toString(), big1.subtract(big2).toString());
    }

    @Test
    public void testSubstract3() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-5515");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("-11515");
        AbstractBigInteger big1 = createNumber("-5515");
        AbstractBigInteger big2 = createNumber("-11515");
        assertEquals(bigOriginal1.subtract(bigOriginal2).toString(), big1.subtract(big2).toString());

        bigOriginal1 = new java.math.BigInteger("11515");
        bigOriginal2 = new java.math.BigInteger("11515");
        big1 = createNumber("11515");
        big2 = createNumber("11515");
        assertEquals(bigOriginal1.subtract(bigOriginal2).toString(), big1.subtract(big2).toString());
    }

    @Test
    public void testSubstract4() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-0");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("0");
        AbstractBigInteger big1 = createNumber("-0");
        AbstractBigInteger big2 = createNumber("0");
        assertEquals(bigOriginal1.subtract(bigOriginal2).toString(), big1.subtract(big2).toString());

        bigOriginal1 = new java.math.BigInteger("0");
        bigOriginal2 = new java.math.BigInteger("0");
        big1 = createNumber("0");
        big2 = createNumber("0");
        assertEquals(bigOriginal1.subtract(bigOriginal2).toString(), big1.subtract(big2).toString());
    }

    @Test
    public void testSubstract5() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("1");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("2");
        AbstractBigInteger big1 = createNumber("1");
        AbstractBigInteger big2 = createNumber("2");
        assertEquals(bigOriginal1.subtract(bigOriginal2).toString(), big1.subtract(big2).toString());

        bigOriginal1 = new java.math.BigInteger("-1");
        bigOriginal2 = new java.math.BigInteger("-2");
        big1 = createNumber("-1");
        big2 = createNumber("-2");
        assertEquals(bigOriginal1.subtract(bigOriginal2).toString(), big1.subtract(big2).toString());
    }

    @Test
    public void testSubstract6() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("00001");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("002");
        AbstractBigInteger big1 = createNumber("00001");
        AbstractBigInteger big2 = createNumber("002");
        assertEquals(bigOriginal1.subtract(bigOriginal2).toString(), big1.subtract(big2).toString());

        bigOriginal1 = new java.math.BigInteger("-0001");
        bigOriginal2 = new java.math.BigInteger("-002");
        big1 = createNumber("-0001");
        big2 = createNumber("-002");
        assertEquals(bigOriginal1.subtract(bigOriginal2).toString(), big1.subtract(big2).toString());
    }
    
    @Test
    public void testSubstract7() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-9000");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("8000");
        AbstractBigInteger big1 = createNumber("-9000");
        AbstractBigInteger big2 = createNumber("8000");
        assertEquals(bigOriginal1.subtract(bigOriginal2).toString(), big1.subtract(big2).toString());

        bigOriginal1 = new java.math.BigInteger("-37892");
        bigOriginal2 = new java.math.BigInteger("16221");
        big1 = createNumber("-37892");
        big2 = createNumber("16221");
        assertEquals(bigOriginal1.subtract(bigOriginal2).toString(), big1.subtract(big2).toString());
    }

    @Test
    public void testSubstractExtreme() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("1234567894156561181562185189");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("1234567894156561181562185189156165156165");
        AbstractBigInteger big1 = createNumber("1234567894156561181562185189");
        AbstractBigInteger big2 = createNumber("1234567894156561181562185189156165156165");
        assertEquals(bigOriginal1.subtract(bigOriginal2).toString(), big1.subtract(big2).toString());

        bigOriginal1 = new java.math.BigInteger("-1234567894156561181562185189");
        bigOriginal2 = new java.math.BigInteger("1299865685894156561181562185189156165156165");
        big1 = createNumber("-1234567894156561181562185189");
        big2 = createNumber("1299865685894156561181562185189156165156165");
        assertEquals(bigOriginal1.subtract(bigOriginal2).toString(), big1.subtract(big2).toString());
    }

    // TEST multiply
    @Test
    public void testMultiply1() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("245789");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("959123");
        AbstractBigInteger big1 = createNumber("245789");
        AbstractBigInteger big2 = createNumber("959123");
        assertEquals(bigOriginal1.multiply(bigOriginal2).toString(), big1.multiply(big2).toString());
    }

    @Test
    public void testMultiply2() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("245789");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("-959123");
        AbstractBigInteger big1 = createNumber("245789");
        AbstractBigInteger big2 = createNumber("-959123");
        assertEquals(bigOriginal1.multiply(bigOriginal2).toString(), big1.multiply(big2).toString());

        bigOriginal1 = new java.math.BigInteger("245789");
        bigOriginal2 = new java.math.BigInteger("-959123");
        big1 = createNumber("245789");
        big2 = createNumber("-959123");
        assertEquals(bigOriginal1.multiply(bigOriginal2).toString(), big1.multiply(big2).toString());
    }

    @Test
    public void testMultiply3() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-245789");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("959123");
        AbstractBigInteger big1 = createNumber("-245789");
        AbstractBigInteger big2 = createNumber("959123");
        assertEquals(bigOriginal1.multiply(bigOriginal2).toString(), big1.multiply(big2).toString());

        bigOriginal1 = new java.math.BigInteger("245789");
        bigOriginal2 = new java.math.BigInteger("-959123");
        big1 = createNumber("245789");
        big2 = createNumber("-959123");
        assertEquals(bigOriginal1.multiply(bigOriginal2).toString(), big1.multiply(big2).toString());
    }

    @Test
    public void testMultiply4() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-245789");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("-959123");
        AbstractBigInteger big1 = createNumber("-245789");
        AbstractBigInteger big2 = createNumber("-959123");
        assertEquals(bigOriginal1.multiply(bigOriginal2).toString(), big1.multiply(big2).toString());

        bigOriginal1 = new java.math.BigInteger("245789123");
        bigOriginal2 = new java.math.BigInteger("1");
        big1 = createNumber("245789123");
        big2 = createNumber("1");
        assertEquals(bigOriginal1.multiply(bigOriginal2).toString(), big1.multiply(big2).toString());
    }

    @Test
    public void testMultiply5() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-245789123");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("0");
        AbstractBigInteger big1 = createNumber("-245789123");
        AbstractBigInteger big2 = createNumber("0");
        assertEquals(bigOriginal1.multiply(bigOriginal2).toString(), big1.multiply(big2).toString());

        bigOriginal1 = new java.math.BigInteger("245789123");
        bigOriginal2 = new java.math.BigInteger("0");
        big1 = createNumber("245789123");
        big2 = createNumber("0");
        assertEquals(bigOriginal1.multiply(bigOriginal2).toString(), big1.multiply(big2).toString());

        bigOriginal1 = new java.math.BigInteger("0");
        bigOriginal2 = new java.math.BigInteger("845612");
        big1 = createNumber("0");
        big2 = createNumber("845612");
        assertEquals(bigOriginal1.multiply(bigOriginal2).toString(), big1.multiply(big2).toString());

        bigOriginal1 = new java.math.BigInteger("0");
        bigOriginal2 = new java.math.BigInteger("0");
        big1 = createNumber("0");
        big2 = createNumber("0");
        assertEquals(bigOriginal1.multiply(bigOriginal2).toString(), big1.multiply(big2).toString());
    }

    @Test
    public void testMultiplyExtreme() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("11212312321561651899889456189416523");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("1121231232156165189985616515689487498");
        AbstractBigInteger big1 = createNumber("11212312321561651899889456189416523");
        AbstractBigInteger big2 = createNumber("1121231232156165189985616515689487498");
        assertEquals(bigOriginal1.multiply(bigOriginal2).toString(), big1.multiply(big2).toString());

        bigOriginal1 = new java.math.BigInteger("11212312321561651899889456189416523");
        bigOriginal2 = new java.math.BigInteger("1121231232156165189985616515689487498");
        big1 = createNumber("11212312321561651899889456189416523");
        big2 = createNumber("1121231232156165189985616515689487498");
        assertEquals(bigOriginal1.multiply(bigOriginal2).toString(), big1.multiply(big2).toString());
    }

    // TEST divide
    @Test
    public void testDivide1() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("123456");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("123");
        AbstractBigInteger big1 = createNumber("123456");
        AbstractBigInteger big2 = createNumber("123");
        assertEquals(bigOriginal1.divide(bigOriginal2).toString(), big1.divide(big2).toString());

        bigOriginal1 = new java.math.BigInteger("15165");
        bigOriginal2 = new java.math.BigInteger("1516484665");
        big1 = createNumber("15165");
        big2 = createNumber("1516484665");
        assertEquals(bigOriginal1.divide(bigOriginal2).toString(), big1.divide(big2).toString());
    }

    @Test
    public void testDivide2() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("9999");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("9999");
        AbstractBigInteger big1 = createNumber("9999");
        AbstractBigInteger big2 = createNumber("9999");
        assertEquals(bigOriginal1.divide(bigOriginal2).toString(), big1.divide(big2).toString());

        bigOriginal1 = new java.math.BigInteger("789");
        bigOriginal2 = new java.math.BigInteger("-789");
        big1 = createNumber("789");
        big2 = createNumber("-789");
        assertEquals(bigOriginal1.divide(bigOriginal2).toString(), big1.divide(big2).toString());
    }

    @Test
    public void testDivide3() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-9988");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("85");
        AbstractBigInteger big1 = createNumber("-9988");
        AbstractBigInteger big2 = createNumber("85");
        assertEquals(bigOriginal1.divide(bigOriginal2).toString(), big1.divide(big2).toString());

        bigOriginal1 = new java.math.BigInteger("9988");
        bigOriginal2 = new java.math.BigInteger("-96");
        big1 = createNumber("9988");
        big2 = createNumber("-96");
        assertEquals(bigOriginal1.divide(bigOriginal2).toString(), big1.divide(big2).toString());
    }

    @Test
    public void testDivide4() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("0");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("45646");
        AbstractBigInteger big1 = createNumber("0");
        AbstractBigInteger big2 = createNumber("45646");
        assertEquals(bigOriginal1.divide(bigOriginal2).toString(), big1.divide(big2).toString());

        bigOriginal1 = new java.math.BigInteger("0");
        bigOriginal2 = new java.math.BigInteger("-4956");
        big1 = createNumber("0");
        big2 = createNumber("-4956");
        assertEquals(bigOriginal1.divide(bigOriginal2).toString(), big1.divide(big2).toString());
    }

    @Test
    public void testDividePerZero() {
        exception.expect(ArithmeticException.class);
        AbstractBigInteger big1 = createNumber("48");
        AbstractBigInteger big2 = createNumber("0");
        big1.divide(big2);
    }

    @Test
    public void testDivideExtreme() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("789461618491651569861639465897465138794651328794651213141653");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("46519861548651486514653");
        AbstractBigInteger big1 = createNumber("789461618491651569861639465897465138794651328794651213141653");
        AbstractBigInteger big2 = createNumber("46519861548651486514653");
        assertEquals(bigOriginal1.divide(bigOriginal2).toString(), big1.divide(big2).toString());

        bigOriginal1 = new java.math.BigInteger("561846514651486512348651324865132");
        bigOriginal2 = new java.math.BigInteger("946512348651328465123486513284465");
        big1 = createNumber("561846514651486512348651324865132");
        big2 = createNumber("946512348651328465123486513284465");
        assertEquals(bigOriginal1.divide(bigOriginal2).toString(), big1.divide(big2).toString());
    }

    // TEST divideAndRemainder
    @Test
    public void testDivideAndRemainder1() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("1855");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("100");
        AbstractBigInteger big1 = createNumber("1855");
        AbstractBigInteger big2 = createNumber("100");
        String orig = Arrays.toString(bigOriginal1.divideAndRemainder(bigOriginal2));
        String my = Arrays.toString(big1.divideAndRemainder(big2));
        assertEquals(orig, my);
    }

    @Test
    public void testDivideAndRemainder2() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("2500");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("100");
        AbstractBigInteger big1 = createNumber("2500");
        AbstractBigInteger big2 = createNumber("100");
        String orig = Arrays.toString(bigOriginal1.divideAndRemainder(bigOriginal2));
        String my = Arrays.toString(big1.divideAndRemainder(big2));
        assertEquals(orig, my);
    }

    @Test
    public void testDivideAndRemainder3() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("1250");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("1250");
        AbstractBigInteger big1 = createNumber("1250");
        AbstractBigInteger big2 = createNumber("1250");
        String orig = Arrays.toString(bigOriginal1.divideAndRemainder(bigOriginal2));
        String my = Arrays.toString(big1.divideAndRemainder(big2));
        assertEquals(orig, my);
    }

    @Test
    public void testDivideAndRemainder4() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("890");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("2541");
        AbstractBigInteger big1 = createNumber("890");
        AbstractBigInteger big2 = createNumber("2541");
        String orig = Arrays.toString(bigOriginal1.divideAndRemainder(bigOriginal2));
        String my = Arrays.toString(big1.divideAndRemainder(big2));
        assertEquals(orig, my);
    }

    @Test
    public void testDivideAndRemainder5() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-890");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("2541");
        AbstractBigInteger big1 = createNumber("-890");
        AbstractBigInteger big2 = createNumber("2541");
        String orig = Arrays.toString(bigOriginal1.divideAndRemainder(bigOriginal2));
        String my = Arrays.toString(big1.divideAndRemainder(big2));
        assertEquals(orig, my);
    }

    @Test
    public void testDivideAndRemainder6() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("890");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("-2541");
        AbstractBigInteger big1 = createNumber("890");
        AbstractBigInteger big2 = createNumber("-2541");
        String orig = Arrays.toString(bigOriginal1.divideAndRemainder(bigOriginal2));
        String my = Arrays.toString(big1.divideAndRemainder(big2));
        assertEquals(orig, my);
    }

    @Test
    public void testDivideAndRemainder7() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-550");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("-550");
        AbstractBigInteger big1 = createNumber("-550");
        AbstractBigInteger big2 = createNumber("-550");
        String orig = Arrays.toString(bigOriginal1.divideAndRemainder(bigOriginal2));
        String my = Arrays.toString(big1.divideAndRemainder(big2));
        assertEquals(orig, my);
    }

    @Test
    public void testDivide8() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("0");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("123");
        AbstractBigInteger big1 = createNumber("0");
        AbstractBigInteger big2 = createNumber("123");
        assertEquals(bigOriginal1.divide(bigOriginal2).toString(), big1.divide(big2).toString());

        bigOriginal1 = new java.math.BigInteger("100");
        bigOriginal2 = new java.math.BigInteger("1");
        big1 = createNumber("100");
        big2 = createNumber("1");
        assertEquals(bigOriginal1.divide(bigOriginal2).toString(), big1.divide(big2).toString());
    }

    @Test
    public void testDivide9() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("2540");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("2550");
        AbstractBigInteger big1 = createNumber("2540");
        AbstractBigInteger big2 = createNumber("2550");
        assertEquals(bigOriginal1.divide(bigOriginal2).toString(), big1.divide(big2).toString());

        bigOriginal1 = new java.math.BigInteger("-2530");
        bigOriginal2 = new java.math.BigInteger("-2550");
        big1 = createNumber("-2530");
        big2 = createNumber("-2550");
        assertEquals(bigOriginal1.divide(bigOriginal2).toString(), big1.divide(big2).toString());
    }

    // TEST toByteArray
    @Test
    public void testToByteArray1() {
        java.math.BigInteger bigOriginal = new java.math.BigInteger("8900");
        AbstractBigInteger big = createNumber("8900");
        String orig = Arrays.toString(bigOriginal.toByteArray());
        String my = Arrays.toString(big.toByteArray());
        assertEquals(orig, my);
    }

    @Test
    public void testToByteArray2() {
        java.math.BigInteger bigOriginal = new java.math.BigInteger("-8900");
        AbstractBigInteger big = createNumber("-8900");
        String orig = Arrays.toString(bigOriginal.toByteArray());
        String my = Arrays.toString(big.toByteArray());
        assertEquals(orig, my);
    }

    @Test
    public void testToByteArray3() {
        java.math.BigInteger bigOriginal = new java.math.BigInteger("-453612387345");
        AbstractBigInteger big = createNumber("-453612387345");
        String orig = Arrays.toString(bigOriginal.toByteArray());
        String my = Arrays.toString(big.toByteArray());
        assertEquals(orig, my);
    }

    @Test
    public void testToByteArray4() {
        java.math.BigInteger bigOriginal = new java.math.BigInteger("-1");
        AbstractBigInteger big = createNumber("-1");
        String orig = Arrays.toString(bigOriginal.toByteArray());
        String my = Arrays.toString(big.toByteArray());
        assertEquals(orig, my);
    }

    @Test
    public void testToByteArray5() {
        java.math.BigInteger bigOriginal = new java.math.BigInteger("1");
        AbstractBigInteger big = createNumber("1");
        String orig = Arrays.toString(bigOriginal.toByteArray());
        String my = Arrays.toString(big.toByteArray());
        assertEquals(orig, my);
    }

    @Test
    public void testToByteArray6() {
        java.math.BigInteger bigOriginal = new java.math.BigInteger("0");
        AbstractBigInteger big = createNumber("0");
        String orig = Arrays.toString(bigOriginal.toByteArray());
        String my = Arrays.toString(big.toByteArray());
        assertEquals(orig, my);
    }

    @Test
    public void testToByteArrayExtreme() {
        java.math.BigInteger bigOriginal = new java.math.BigInteger("45361238734435434535313153153243578654325");
        AbstractBigInteger big = createNumber("45361238734435434535313153153243578654325");
        String orig = Arrays.toString(bigOriginal.toByteArray());
        String my = Arrays.toString(big.toByteArray());
        assertEquals(orig, my);
    }

    @Test
    public void testToByteArrayExtreme2() {
        java.math.BigInteger bigOriginal = new java.math.BigInteger("-45361238734435434535313153153243578654325");
        AbstractBigInteger big = createNumber("-45361238734435434535313153153243578654325");
        String orig = Arrays.toString(bigOriginal.toByteArray());
        String my = Arrays.toString(big.toByteArray());
        assertEquals(orig, my);
    }

    // TEST shiftLeft
    @Test
    public void testShiftLeft1() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("245");
        AbstractBigInteger big1 = createNumber("245");
        assertEquals(bigOriginal1.shiftLeft(5).toString(), big1.shiftLeft(5).toString());
    }

    @Test
    public void testShiftLeft2() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("2484564865219846512894562138465125");
        AbstractBigInteger big1 = createNumber("2484564865219846512894562138465125");
        assertEquals(bigOriginal1.shiftLeft(26).toString(), big1.shiftLeft(26).toString());
    }

    @Test
    public void testShiftLeft3() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-248456486521984651289");
        AbstractBigInteger big1 = createNumber("-248456486521984651289");
        assertEquals(bigOriginal1.shiftLeft(8).toString(), big1.shiftLeft(8).toString());
    }

    @Test
    public void testShiftLeft4() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("0");
        AbstractBigInteger big1 = createNumber("0");
        assertEquals(bigOriginal1.shiftLeft(2).toString(), big1.shiftLeft(2).toString());
    }

    @Test
    public void testShiftLeft5() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-156");
        AbstractBigInteger big1 = createNumber("-156");
        assertEquals(bigOriginal1.shiftLeft(2).toString(), big1.shiftLeft(2).toString());
    }

    @Test
    public void testShiftLeft6() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("6");
        AbstractBigInteger big1 = createNumber("6");
        assertEquals(bigOriginal1.shiftRight(4).toString(), big1.shiftRight(4).toString());
    }
    
    // TEST shiftRight(int n)
    @Test
    public void testShiftRight1() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("245");
        AbstractBigInteger big1 = createNumber("245");
        assertEquals(bigOriginal1.shiftRight(5).toString(), big1.shiftRight(5).toString());
    }

    @Test
    public void testShiftRight2() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("2484564865219846512894562138465125");
        AbstractBigInteger big1 = createNumber("2484564865219846512894562138465125");
        assertEquals(bigOriginal1.shiftRight(26).toString(), big1.shiftRight(26).toString());
    }

    @Test
    public void testShiftRight3() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-248456486521984651289");
        AbstractBigInteger big1 = createNumber("-248456486521984651289");
        assertEquals(bigOriginal1.shiftRight(8).toString(), big1.shiftRight(8).toString());
    }

    @Test
    public void testShiftRight4() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("0");
        AbstractBigInteger big1 = createNumber("0");
        assertEquals(bigOriginal1.shiftRight(2).toString(), big1.shiftRight(2).toString());
    }

    @Test
    public void testShiftRight5() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-156");
        AbstractBigInteger big1 = createNumber("-156");
        assertEquals(bigOriginal1.shiftRight(2).toString(), big1.shiftRight(2).toString());
    }

    @Test
    public void testShiftRight6() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("6");
        AbstractBigInteger big1 = createNumber("6");
        assertEquals(bigOriginal1.shiftRight(4).toString(), big1.shiftRight(4).toString());
    }

    // TEST compareTo
    @Test
    public void testCompareTo1() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("245789");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("959123");
        AbstractBigInteger big1 = createNumber("245789");
        AbstractBigInteger big2 = createNumber("959123");
        assertEquals(bigOriginal1.compareTo(bigOriginal2), big1.compareTo(big2));
    }

    @Test
    public void testCompareTo2() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("12");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("156");
        AbstractBigInteger big1 = createNumber("12");
        AbstractBigInteger big2 = createNumber("156");
        assertEquals(bigOriginal1.compareTo(bigOriginal2), big1.compareTo(big2));
    }

    @Test
    public void testCompareTo3() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("78456");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("156");
        AbstractBigInteger big1 = createNumber("78456");
        AbstractBigInteger big2 = createNumber("156");
        assertEquals(bigOriginal1.compareTo(bigOriginal2), big1.compareTo(big2));
    }

    @Test
    public void testCompareTo4() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-78456");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("-156");
        AbstractBigInteger big1 = createNumber("-78456");
        AbstractBigInteger big2 = createNumber("-156");
        assertEquals(bigOriginal1.compareTo(bigOriginal2), big1.compareTo(big2));
    }

    @Test
    public void testCompareTo5() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-78456");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("-78456");
        AbstractBigInteger big1 = createNumber("-78456");
        AbstractBigInteger big2 = createNumber("-78456");
        assertEquals(bigOriginal1.compareTo(bigOriginal2), big1.compareTo(big2));
    }

    @Test
    public void testCompareTo6() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("78456");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("78456");
        AbstractBigInteger big1 = createNumber("78456");
        AbstractBigInteger big2 = createNumber("78456");
        assertEquals(bigOriginal1.compareTo(bigOriginal2), big1.compareTo(big2));
    }

    @Test
    public void testCompareTo7() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-78456");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("78456");
        AbstractBigInteger big1 = createNumber("-78456");
        AbstractBigInteger big2 = createNumber("78456");
        assertEquals(bigOriginal1.compareTo(bigOriginal2), big1.compareTo(big2));
    }

    @Test
    public void testCompareTo8() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("78456");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("-78456");
        AbstractBigInteger big1 = createNumber("78456");
        AbstractBigInteger big2 = createNumber("-78456");
        assertEquals(bigOriginal1.compareTo(bigOriginal2), big1.compareTo(big2));
    }

    @Test
    public void testCompareTo9() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-786");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("-78456");
        AbstractBigInteger big1 = createNumber("-786");
        AbstractBigInteger big2 = createNumber("-78456");
        assertEquals(bigOriginal1.compareTo(bigOriginal2), big1.compareTo(big2));
    }

    @Test
    public void testCompareTo10() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-789456");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("-789456");
        AbstractBigInteger big1 = createNumber("-789456");
        AbstractBigInteger big2 = createNumber("-789456");
        assertEquals(bigOriginal1.compareTo(bigOriginal2), big1.compareTo(big2));
    }

    // TEST max
    @Test
    public void testMax1() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-789456");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("789456");
        AbstractBigInteger big1 = createNumber("-789456");
        AbstractBigInteger big2 = createNumber("789456");
        assertEquals(bigOriginal1.max(bigOriginal2).toString(), big1.max(big2).toString());
    }
    
    @Test
    public void testMax2() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("452");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("789456");
        AbstractBigInteger big1 = createNumber("452");
        AbstractBigInteger big2 = createNumber("789456");
        assertEquals(bigOriginal1.max(bigOriginal2).toString(), big1.max(big2).toString());
    }
    
    @Test
    public void testMax3() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("457");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("451");
        AbstractBigInteger big1 = createNumber("457");
        AbstractBigInteger big2 = createNumber("451");
        assertEquals(bigOriginal1.max(bigOriginal2).toString(), big1.max(big2).toString());
    }
    
    @Test
    public void testMax4() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("100");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("100");
        AbstractBigInteger big1 = createNumber("100");
        AbstractBigInteger big2 = createNumber("100");
        assertEquals(bigOriginal1.max(bigOriginal2).toString(), big1.max(big2).toString());
    }
    
    @Test
    public void testMax5() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("0");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("100");
        AbstractBigInteger big1 = createNumber("0");
        AbstractBigInteger big2 = createNumber("100");
        assertEquals(bigOriginal1.max(bigOriginal2).toString(), big1.max(big2).toString());
    }
    
    // TEST min
    @Test
    public void testMin1() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-789456");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("789456");
        AbstractBigInteger big1 = createNumber("-789456");
        AbstractBigInteger big2 = createNumber("789456");
        assertEquals(bigOriginal1.min(bigOriginal2).toString(), big1.min(big2).toString());
    }
    
    @Test
    public void testMin2() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("452");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("789456");
        AbstractBigInteger big1 = createNumber("452");
        AbstractBigInteger big2 = createNumber("789456");
        assertEquals(bigOriginal1.min(bigOriginal2).toString(), big1.min(big2).toString());
    }
    
    @Test
    public void testMin3() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("457");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("451");
        AbstractBigInteger big1 = createNumber("457");
        AbstractBigInteger big2 = createNumber("451");
        assertEquals(bigOriginal1.min(bigOriginal2).toString(), big1.min(big2).toString());
    }
    
    @Test
    public void testMin4() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("100");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("100");
        AbstractBigInteger big1 = createNumber("100");
        AbstractBigInteger big2 = createNumber("100");
        assertEquals(bigOriginal1.min(bigOriginal2).toString(), big1.min(big2).toString());
    }
    
    @Test
    public void testMin5() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("0");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("100");
        AbstractBigInteger big1 = createNumber("0");
        AbstractBigInteger big2 = createNumber("100");
        assertEquals(bigOriginal1.min(bigOriginal2).toString(), big1.min(big2).toString());
    }

    // TEST and
    @Test
    public void testAnd() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("789456");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("849567");
        AbstractBigInteger big1 = createNumber("789456");
        AbstractBigInteger big2 = createNumber("849567");
        assertEquals(bigOriginal1.and(bigOriginal2).toString(), big1.and(big2).toString());
    }

    @Test
    public void testAnd2() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("78945645454");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("84956745");
        AbstractBigInteger big1 = createNumber("78945645454");
        AbstractBigInteger big2 = createNumber("84956745");
        assertEquals(bigOriginal1.and(bigOriginal2).toString(), big1.and(big2).toString());
    }

    @Test
    public void testAnd3() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("85242178");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("875287965485");
        AbstractBigInteger big1 = createNumber("85242178");
        AbstractBigInteger big2 = createNumber("875287965485");
        assertEquals(bigOriginal1.and(bigOriginal2).toString(), big1.and(big2).toString());
    }

    @Test
    public void testAndNegative() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-85242178");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("875287965485");
        AbstractBigInteger big1 = createNumber("-85242178");
        AbstractBigInteger big2 = createNumber("875287965485");
        assertEquals(bigOriginal1.and(bigOriginal2).toString(), big1.and(big2).toString());
    }

    @Test
    public void testAndNegative2() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-85242178");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("-875287965485");
        AbstractBigInteger big1 = createNumber("-85242178");
        AbstractBigInteger big2 = createNumber("-875287965485");
        assertEquals(bigOriginal1.and(bigOriginal2).toString(), big1.and(big2).toString());
    }

    @Test
    public void testAndNegative3() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("85242178");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("-875287965485");
        AbstractBigInteger big1 = createNumber("85242178");
        AbstractBigInteger big2 = createNumber("-875287965485");
        assertEquals(bigOriginal1.and(bigOriginal2).toString(), big1.and(big2).toString());
    }

    @Test
    public void testAndExtreme() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("79846578946513846513284651328465132865312");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("896534986539385651368541896534187453531");
        AbstractBigInteger big1 = createNumber("79846578946513846513284651328465132865312");
        AbstractBigInteger big2 = createNumber("896534986539385651368541896534187453531");
        assertEquals(bigOriginal1.and(bigOriginal2).toString(), big1.and(big2).toString());
    }

    // TEST or
    @Test
    public void testOr() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("789456");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("849567");
        AbstractBigInteger big1 = createNumber("789456");
        AbstractBigInteger big2 = createNumber("849567");
        assertEquals(bigOriginal1.or(bigOriginal2).toString(), big1.or(big2).toString());
    }

    @Test
    public void testOr2() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("78945645454");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("84956745");
        AbstractBigInteger big1 = createNumber("78945645454");
        AbstractBigInteger big2 = createNumber("84956745");
        assertEquals(bigOriginal1.or(bigOriginal2).toString(), big1.or(big2).toString());
    }

    @Test
    public void testOr3() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("85242178");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("875287965485");
        AbstractBigInteger big1 = createNumber("85242178");
        AbstractBigInteger big2 = createNumber("875287965485");
        assertEquals(bigOriginal1.or(bigOriginal2).toString(), big1.or(big2).toString());
    }

    @Test
    public void testOrNegative() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-85242178");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("875287965485");
        AbstractBigInteger big1 = createNumber("-85242178");
        AbstractBigInteger big2 = createNumber("875287965485");
        assertEquals(bigOriginal1.or(bigOriginal2).toString(), big1.or(big2).toString());
    }

    @Test
    public void testOrNegative2() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-85242178");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("-875287965485");
        AbstractBigInteger big1 = createNumber("-85242178");
        AbstractBigInteger big2 = createNumber("-875287965485");
        assertEquals(bigOriginal1.or(bigOriginal2).toString(), big1.or(big2).toString());
    }

    @Test
    public void testOrNegative3() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("85242178");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("-875287965485");
        AbstractBigInteger big1 = createNumber("85242178");
        AbstractBigInteger big2 = createNumber("-875287965485");
        assertEquals(bigOriginal1.or(bigOriginal2).toString(), big1.or(big2).toString());
    }

    @Test
    public void testOrExtreme() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("79846578946513846513284651328465132865312");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("896534986539385651368541896534187453531");
        AbstractBigInteger big1 = createNumber("79846578946513846513284651328465132865312");
        AbstractBigInteger big2 = createNumber("896534986539385651368541896534187453531");
        assertEquals(bigOriginal1.or(bigOriginal2).toString(), big1.or(big2).toString());
    }

    // TEST xor
    @Test
    public void testXor() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("789456");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("849567");
        AbstractBigInteger big1 = createNumber("789456");
        AbstractBigInteger big2 = createNumber("849567");
        assertEquals(bigOriginal1.xor(bigOriginal2).toString(), big1.xor(big2).toString());
    }

    @Test
    public void testXor2() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("78945645454");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("84956745");
        AbstractBigInteger big1 = createNumber("78945645454");
        AbstractBigInteger big2 = createNumber("84956745");
        assertEquals(bigOriginal1.xor(bigOriginal2).toString(), big1.xor(big2).toString());
    }

    @Test
    public void testXor3() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("85242178");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("875287965485");
        AbstractBigInteger big1 = createNumber("85242178");
        AbstractBigInteger big2 = createNumber("875287965485");
        assertEquals(bigOriginal1.xor(bigOriginal2).toString(), big1.xor(big2).toString());
    }

    @Test
    public void testXorNegative() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-85242178");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("875287965485");
        AbstractBigInteger big1 = createNumber("-85242178");
        AbstractBigInteger big2 = createNumber("875287965485");
        assertEquals(bigOriginal1.xor(bigOriginal2).toString(), big1.xor(big2).toString());
    }

    @Test
    public void testXorNegative2() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-85242178");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("-875287965485");
        AbstractBigInteger big1 = createNumber("-85242178");
        AbstractBigInteger big2 = createNumber("-875287965485");
        assertEquals(bigOriginal1.xor(bigOriginal2).toString(), big1.xor(big2).toString());
    }

    @Test
    public void testXorNegative3() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("85242178");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("-875287965485");
        AbstractBigInteger big1 = createNumber("85242178");
        AbstractBigInteger big2 = createNumber("-875287965485");
        assertEquals(bigOriginal1.xor(bigOriginal2).toString(), big1.xor(big2).toString());
    }

    @Test
    public void testXorExtreme() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("79846578946513846513284651328465132865312");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("896534986539385651368541896534187453531");
        AbstractBigInteger big1 = createNumber("79846578946513846513284651328465132865312");
        AbstractBigInteger big2 = createNumber("896534986539385651368541896534187453531");
        assertEquals(bigOriginal1.xor(bigOriginal2).toString(), big1.xor(big2).toString());
    }

    // TEST andNot
    @Test
    public void testAndNot() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("789456");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("849567");
        AbstractBigInteger big1 = createNumber("789456");
        AbstractBigInteger big2 = createNumber("849567");
        assertEquals(bigOriginal1.andNot(bigOriginal2).toString(), big1.andNot(big2).toString());
    }

    @Test
    public void testAndNot2() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("78945645454");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("84956745");
        AbstractBigInteger big1 = createNumber("78945645454");
        AbstractBigInteger big2 = createNumber("84956745");
        assertEquals(bigOriginal1.andNot(bigOriginal2).toString(), big1.andNot(big2).toString());
    }

    @Test
    public void testAndNot3() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("85242178");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("875287965485");
        AbstractBigInteger big1 = createNumber("85242178");
        AbstractBigInteger big2 = createNumber("875287965485");
        assertEquals(bigOriginal1.andNot(bigOriginal2).toString(), big1.andNot(big2).toString());
    }

    @Test
    public void testAndNotNegative() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-85242178");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("875287965485");
        AbstractBigInteger big1 = createNumber("-85242178");
        AbstractBigInteger big2 = createNumber("875287965485");
        assertEquals(bigOriginal1.andNot(bigOriginal2).toString(), big1.andNot(big2).toString());
    }

    @Test
    public void testAndNotNegative2() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-85242178");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("-875287965485");
        AbstractBigInteger big1 = createNumber("-85242178");
        AbstractBigInteger big2 = createNumber("-875287965485");
        assertEquals(bigOriginal1.andNot(bigOriginal2).toString(), big1.andNot(big2).toString());
    }

    @Test
    public void testAndNotNegative3() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("85242178");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("-875287965485");
        AbstractBigInteger big1 = createNumber("85242178");
        AbstractBigInteger big2 = createNumber("-875287965485");
        assertEquals(bigOriginal1.andNot(bigOriginal2).toString(), big1.andNot(big2).toString());
    }

    @Test
    public void testAndNotExtreme() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("79846578946513846513284651328465132865312");
        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("896534986539385651368541896534187453531");
        AbstractBigInteger big1 = createNumber("79846578946513846513284651328465132865312");
        AbstractBigInteger big2 = createNumber("896534986539385651368541896534187453531");
        assertEquals(bigOriginal1.andNot(bigOriginal2).toString(), big1.andNot(big2).toString());
    }

    // TEST bitCount
    @Test
    public void testBitCount1() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("100");
        AbstractBigInteger big1 = createNumber("100");
        assertEquals(bigOriginal1.bitCount(), big1.bitCount());
    }
    
    @Test
    public void testBitCount2() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-100");
        AbstractBigInteger big1 = createNumber("-100");
        assertEquals(bigOriginal1.bitCount(), big1.bitCount());
    }
    
    @Test
    public void testBitCount3() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("0");
        AbstractBigInteger big1 = createNumber("0");
        assertEquals(bigOriginal1.bitCount(), big1.bitCount());
    }
    
    @Test
    public void testBitCount4() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-9");
        AbstractBigInteger big1 = createNumber("-9");
        assertEquals(bigOriginal1.bitCount(), big1.bitCount());
    }
    
    @Test
    public void testBitCount5() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-91");
        AbstractBigInteger big1 = createNumber("-91");
        assertEquals(bigOriginal1.bitCount(), big1.bitCount());
    }
    
    @Test
    public void testBitCountExtreme() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("845612384651284651328465132846513264578465312");
        AbstractBigInteger big1 = createNumber("845612384651284651328465132846513264578465312");
        assertEquals(bigOriginal1.bitCount(), big1.bitCount());
    }

    // TEST bitLength
    @Test
    public void testBitLength1() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("100");
        AbstractBigInteger big1 = createNumber("100");
        assertEquals(bigOriginal1.bitLength(), big1.bitLength());
    }
    
    @Test
    public void testBitLength2() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-100");
        AbstractBigInteger big1 = createNumber("-100");
        assertEquals(bigOriginal1.bitLength(), big1.bitLength());
    }
    
    @Test
    public void testBitLength3() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("0");
        AbstractBigInteger big1 = createNumber("0");
        assertEquals(bigOriginal1.bitLength(), big1.bitLength());
    }
    
    @Test
    public void testBitLengthExtreme() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("48651846513248651234512");
        AbstractBigInteger big1 = createNumber("48651846513248651234512");
        assertEquals(bigOriginal1.bitLength(), big1.bitLength());
    }
    
    // TEST negate
    @Test
    public void testNegate1() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("789456");
        AbstractBigInteger big1 = createNumber("789456");
        assertEquals(bigOriginal1.negate().toString(), big1.negate().toString());
    }
    
    @Test
    public void testNegate2() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-789456");
        AbstractBigInteger big1 = createNumber("-789456");
        assertEquals(bigOriginal1.negate().toString(), big1.negate().toString());
    }
    
    @Test
    public void testNegate3() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("0");
        AbstractBigInteger big1 = createNumber("0");
        assertEquals(bigOriginal1.negate().toString(), big1.negate().toString());
    }
    
    
    // TEST not
    @Test
    public void testNot() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("789456");
        AbstractBigInteger big1 = createNumber("789456");
        assertEquals(bigOriginal1.not().toString(), big1.not().toString());
    }

    @Test
    public void testnot2() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-78945645454");
        AbstractBigInteger big1 = createNumber("-78945645454");
        assertEquals(bigOriginal1.not().toString(), big1.not().toString());
    }

    @Test
    public void testNotExtreme() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("79846578946513846513284651328465132865312");
        AbstractBigInteger big1 = createNumber("79846578946513846513284651328465132865312");
        assertEquals(bigOriginal1.not().toString(), big1.not().toString());
    }

    // TEST constructor (string)
    @Test
    public void testConstructorString() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("12316651");
        AbstractBigInteger big1 = createNumber("12316651");
        assertEquals(bigOriginal1.toString(), big1.toString());
    }

    @Test
    public void testConstructorString2() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-12316651");
        AbstractBigInteger big1 = createNumber("-12316651");
        assertEquals(bigOriginal1.toString(), big1.toString());
    }

    @Test
    public void testConstructorString3() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-0");
        AbstractBigInteger big1 = createNumber("-0");
        assertEquals(bigOriginal1.toString(), big1.toString());
    }

    @Test
    public void testConstructorString4() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("99999999444444555555633123156848651");
        AbstractBigInteger big1 = createNumber("99999999444444555555633123156848651");
        assertEquals(bigOriginal1.toString(), big1.toString());
    }

    @Test
    public void testConstructorString5() {
        exception.expect(NumberFormatException.class);
        AbstractBigInteger big1 = createNumber("9999999944444455555563312315a6848651");
    }

    @Test
    public void testConstructorString6() {
        exception.expect(NumberFormatException.class);
        AbstractBigInteger big1 = createNumber("99999999444444555555633123156848651a");
    }

    @Test
    public void testConstructorString7() {
        exception.expect(NumberFormatException.class);
        AbstractBigInteger big1 = createNumber("--99999999444444555555633123156848651");
    }

    @Test
    public void testConstructorString8() {
        exception.expect(NumberFormatException.class);
        AbstractBigInteger big1 = createNumber("4.365252");
    }

    @Test
    public void testConstructorString9() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("+456123");
        AbstractBigInteger big1 = createNumber("+456123");
        assertEquals(bigOriginal1.toString(), big1.toString());
    }

    @Test
    public void testConstructorString10() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("000000");
        AbstractBigInteger big1 = createNumber("000000");
        assertEquals(bigOriginal1.toString(), big1.toString());
    }

    @Test
    public void testConstructorString11() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-000000");
        AbstractBigInteger big1 = createNumber("-000000");
        assertEquals(bigOriginal1.toString(), big1.toString());
    }

    // TEST constructor (String, radix)
    @Test
    public void testConstructorStringRadix1() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("156156", 8);
        AbstractBigInteger big1 = createNumber("156156", 8);
        assertEquals(bigOriginal1.toString(), big1.toString());
    }

    @Test
    public void testConstructorStringRadix2() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("156156AFFFDDGGHHAAAa", 25);
        AbstractBigInteger big1 = createNumber("156156AFFFDDGGHHAAAa", 25);
        assertEquals(bigOriginal1.toString(), big1.toString());
    }

    @Test
    public void testConstructorStringRadix3() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("abcdfggkdsxzff645fshudjkr7456fsdrfsdgerfsd798465312", 36);
        AbstractBigInteger big1 = createNumber("abcdfggkdsxzff645fshudjkr7456fsdrfsdgerfsd798465312", 36);
        assertEquals(bigOriginal1.toString(), big1.toString());
    }

    @Test
    public void testConstructorStringRadix4() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-adsfdsf845689456bfsd8456cdfggkdsxzff645fshudjkr7456fsdrfsdgerfsd798465", 36);
        AbstractBigInteger big1 = createNumber("-adsfdsf845689456bfsd8456cdfggkdsxzff645fshudjkr7456fsdrfsdgerfsd798465", 36);
        assertEquals(bigOriginal1.toString(), big1.toString());
    }

    @Test
    public void testConstructorStringRadix5() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("111110100000011111011000000110010000111", 2);
        AbstractBigInteger big1 = createNumber("111110100000011111011000000110010000111", 2);
        assertEquals(bigOriginal1.toString(), big1.toString());
    }

    // TEST constructor (byte[])
    @Test
    public void testConstructorBytes() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger(new byte[]{1});
        AbstractBigInteger big1 = createNumber(new byte[]{1});
        assertEquals(bigOriginal1.toString(), big1.toString());
    }

    @Test
    public void testConstructorBytesAllPositive() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger(new byte[]{1, 120, 50, 62, 98, 1, 11});
        AbstractBigInteger big1 = createNumber(new byte[]{1, 120, 50, 62, 98, 1, 11});
        assertEquals(bigOriginal1.toString(), big1.toString());
    }

    @Test
    public void testConstructorBytesAllNegative() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger(new byte[]{-1, -120, -50, -62, -98, -1, -11});
        AbstractBigInteger big1 = createNumber(new byte[]{-1, -120, -50, -62, -98, -1, -11});
        assertEquals(bigOriginal1.toString(), big1.toString());
    }

    @Test
    public void testConstructorBytesMixed() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger(new byte[]{-1, 120, -50, 62, 98, -1, -11});
        AbstractBigInteger big1 = createNumber(new byte[]{-1, 120, -50, 62, 98, -1, -11});
        assertEquals(bigOriginal1.toString(), big1.toString());
    }

    @Test
    public void testConstructorBytesLimiteCases() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger(new byte[]{0});
        AbstractBigInteger big1 = createNumber(new byte[]{0});
        assertEquals(bigOriginal1.toString(), big1.toString());

        bigOriginal1 = new java.math.BigInteger(new byte[]{127});
        big1 = createNumber(new byte[]{127});
        assertEquals(bigOriginal1.toString(), big1.toString());

        bigOriginal1 = new java.math.BigInteger(new byte[]{-128});
        big1 = createNumber(new byte[]{-128});
        assertEquals(bigOriginal1.toString(), big1.toString());
    }

    @Test
    public void testConstructorBytesExtreme() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger(new byte[]{1, 52, -63, 58, 58, 75, 75, 85, -56, -12, -0, 1, 0, 4, 0, 5, 6});
        AbstractBigInteger big1 = createNumber(new byte[]{1, 52, -63, 58, 58, 75, 75, 85, -56, -12, -0, 1, 0, 4, 0, 5, 6});
        assertEquals(bigOriginal1.toString(), big1.toString());

        bigOriginal1 = new java.math.BigInteger(new byte[]{-1, 52, -63, 58, 58, 75, 75, 85, -56, -12, -0, 1, 0, 4, 0, 5, 6});
        big1 = createNumber(new byte[]{-1, 52, -63, 58, 58, 75, 75, 85, -56, -12, -0, 1, 0, 4, 0, 5, 6});
        assertEquals(bigOriginal1.toString(), big1.toString());
    }

    // TEST constructor(int signum, byte[] magnitude)
    @Test
    public void testConstructorBytesMagnitude1() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger(1, new byte[]{-1, 120, -50, 62, 98, -1, -11});
        AbstractBigInteger big1 = createNumber(1, new byte[]{-1, 120, -50, 62, 98, -1, -11});
        assertEquals(bigOriginal1.toString(), big1.toString());
    }

    @Test
    public void testConstructorBytesMagnitude2() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger(1, new byte[]{1, 5, 8, 9, 15, 1, -96, -98, -85, 57, 12, 120, -127, 127, -128});
        AbstractBigInteger big1 = createNumber(1, new byte[]{1, 5, 8, 9, 15, 1, -96, -98, -85, 57, 12, 120, -127, 127, -128});
        assertEquals(bigOriginal1.toString(), big1.toString());
    }

    @Test
    public void testConstructorBytesMagnitude3() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger(-1, new byte[]{1, 5, 8, 9, 15, 1, -96, -98, -85, 57, 12, 120, -127, 127, -128});
        AbstractBigInteger big1 = createNumber(-1, new byte[]{1, 5, 8, 9, 15, 1, -96, -98, -85, 57, 12, 120, -127, 127, -128});
        assertEquals(bigOriginal1.toString(), big1.toString());
    }

    @Test
    public void testConstructorBytesMagnitude4() {
        exception.expect(NumberFormatException.class);
        AbstractBigInteger big1 = createNumber(0, new byte[]{1, 5, 8});
    }

    @Test
    public void testConstructorBytesMagnitude5() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger(0, new byte[]{});
        AbstractBigInteger big1 = createNumber(0, new byte[]{});
        assertEquals(bigOriginal1.toString(), big1.toString());
    }

    @Test
    public void testConstructorBytesMagnitude6() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger(1, new byte[]{});
        AbstractBigInteger big1 = createNumber(1, new byte[]{});
        assertEquals(bigOriginal1.toString(), big1.toString());
    }

    @Test
    public void testConstructorBytesMagnitude7() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger(1, new byte[]{0, 0, 0, 0});
        AbstractBigInteger big1 = createNumber(1, new byte[]{0, 0, 0, 0});
        assertEquals(bigOriginal1.toString(), big1.toString());
    }

    @Test
    public void testConstructorBytesMagnitude8() {
        java.math.BigInteger bigOriginal1 = new java.math.BigInteger(1, new byte[]{-1, -15, -56, -85});
        AbstractBigInteger big1 = createNumber(1, new byte[]{-1, -15, -56, -85});
        assertEquals(bigOriginal1.toString(), big1.toString());
    }

    @Test
    public void testConstructorBytesMagnitude9() {
        exception.expect(NumberFormatException.class);
        AbstractBigInteger big1 = createNumber(10, new byte[]{-1, -15});
    }

    //Test signum()

    @Test
    public void testSignum() {
        java.math.BigInteger bigOriginal = new java.math.BigInteger("25669856555");
        AbstractBigInteger big = createNumber("25669856555");
        assertEquals(bigOriginal.signum(), big.signum());

        java.math.BigInteger bigOriginal1 = new java.math.BigInteger("-122545862");
        AbstractBigInteger big1 = createNumber("-122545862");
        assertEquals(bigOriginal1.signum(), big1.signum());

        java.math.BigInteger bigOriginal2 = new java.math.BigInteger("0000");
        AbstractBigInteger big2 = createNumber("0000");
        assertEquals(bigOriginal2.signum(), big2.signum());

        java.math.BigInteger bigOriginal3 = new java.math.BigInteger("-0000");
        AbstractBigInteger big3 = createNumber("-0000");
        assertEquals(bigOriginal3.signum(), big3.signum());

        java.math.BigInteger bigOriginal4 = new java.math.BigInteger("-00001");
        AbstractBigInteger big4 = createNumber("-00001");
        assertEquals(bigOriginal4.signum(), big4.signum());
    }

    //Test pow()
    @Test
    public void testPow1() {
        java.math.BigInteger bigO = new BigInteger("256");
        AbstractBigInteger big = createNumber("256");
        assertEquals(bigO.pow(7).toString(), big.pow(7).toString());

        java.math.BigInteger bigO1 = new BigInteger("256");
        AbstractBigInteger big1 = createNumber("256");
        assertEquals(bigO1.pow(0).toString(), big1.pow(0).toString());

        java.math.BigInteger bigO2 = new BigInteger("5500");
        AbstractBigInteger big2 = createNumber("5500");
        assertEquals(bigO2.pow(100).toString(), big2.pow(100).toString());
    }
    
    @Test
    public void testPowArithmeticException() {
        AbstractBigInteger big2 = createNumber("256");
        exception.expect(ArithmeticException.class);
        big2.pow(-2);
    }

    //Test remainder
    @Test
    public void testRemainder() {
        java.math.BigInteger bigO = new BigInteger("15956215");
        AbstractBigInteger big = createNumber("15956215");
        assertEquals(bigO.remainder(new BigInteger("5")).toString(), big.remainder(createNumber("5")).toString());

        java.math.BigInteger bigO1 = new BigInteger("5968548");
        AbstractBigInteger big1 = createNumber("5968548");
        assertEquals(bigO1.remainder(new BigInteger("485")).toString(), big1.remainder(createNumber("485")).toString());

        java.math.BigInteger bigO2 = new BigInteger("589");
        AbstractBigInteger big2 = createNumber("589");
        assertEquals(bigO2.remainder(new BigInteger("5698")).toString(), big2.remainder(createNumber("5698")).toString());

        java.math.BigInteger bigO3 = new BigInteger("856882");
        AbstractBigInteger big3 = createNumber("856882");
        assertEquals(bigO3.remainder(new BigInteger("-15")).toString(), big3.remainder(createNumber("-15")).toString());

    }

    public void testRemainderArithmeticException() {
        exception.expect(ArithmeticException.class);
        AbstractBigInteger big = createNumber("12832340");
        big.remainder(createNumber("0"));
    }

    //Test mod
    @Test
    public void testMod() {
        java.math.BigInteger bigO = new BigInteger("15478962");
        AbstractBigInteger big = createNumber("15478962");
        assertEquals(bigO.mod(new BigInteger("456")).toString(), big.mod(createNumber("456")).toString());

        java.math.BigInteger bigO1 = new BigInteger("5489");
        AbstractBigInteger big1 = createNumber("5489");
        assertEquals(bigO1.mod(new BigInteger("7852")).toString(), big1.mod(createNumber("7852")).toString());

        java.math.BigInteger bigO2 = new BigInteger("-785215");
        AbstractBigInteger big2 = createNumber("-785215");
        assertEquals(bigO2.mod(new BigInteger("589")).toString(), big2.mod(createNumber("589")).toString());

        java.math.BigInteger bigO3 = new BigInteger("658712345");
        AbstractBigInteger big3 = createNumber("658712345");
        assertEquals(bigO3.mod(new BigInteger("658712345")).toString(), big3.mod(createNumber("658712345")).toString());
    }

    @Test
    public void testModArithmeticException() {
        exception.expect(ArithmeticException.class);

        java.math.BigInteger bigO = new BigInteger("15478");
        AbstractBigInteger big = createNumber("15478");
        assertEquals(bigO.mod(new BigInteger("0")).toString(), big.mod(createNumber("0")).toString());

        java.math.BigInteger bigO1 = new BigInteger("15478");
        AbstractBigInteger big1 = createNumber("15478");
        assertEquals(bigO1.mod(new BigInteger("-10")).toString(), big1.mod(createNumber("-10")).toString());
    }

    //Test gcd
    public void testGcd1() {
        java.math.BigInteger bigO = new BigInteger("5869854");
        AbstractBigInteger big = createNumber("5869854");
        assertEquals(bigO.gcd(new BigInteger("4589")).toString(), big.gcd(createNumber("4589")).toString());

        java.math.BigInteger bigO1 = new BigInteger("48");
        AbstractBigInteger big1 = createNumber("48");
        assertEquals(bigO1.gcd(new BigInteger("60")).toString(), big1.gcd(createNumber("60")).toString());

        java.math.BigInteger bigO2 = new BigInteger("60");
        AbstractBigInteger big2 = createNumber("60");
        assertEquals(bigO2.gcd(new BigInteger("48")).toString(), big2.gcd(createNumber("48")).toString());

        java.math.BigInteger bigO3 = new BigInteger("589621");
        AbstractBigInteger big3 = createNumber("589621");
        assertEquals(bigO3.gcd(new BigInteger("0")).toString(), big3.gcd(createNumber("0")).toString());

        java.math.BigInteger bigO4 = new BigInteger("0");
        AbstractBigInteger big4 = createNumber("0");
        assertEquals(bigO4.gcd(new BigInteger("589962")).toString(), big4.gcd(createNumber("589962")).toString());

        java.math.BigInteger bigO5 = new BigInteger("0");
        AbstractBigInteger big5 = createNumber("0");
        assertEquals(bigO5.gcd(new BigInteger("0")).toString(), big5.gcd(createNumber("0")).toString());
    }

    public void testGcd2() {
        java.math.BigInteger bigO = new BigInteger("-5869854");
        AbstractBigInteger big = createNumber("-5869854");
        assertEquals(bigO.gcd(new BigInteger("-4589")).toString(), big.gcd(createNumber("-4589")).toString());

        java.math.BigInteger bigO1 = new BigInteger("-48");
        AbstractBigInteger big1 = createNumber("-48");
        assertEquals(bigO1.gcd(new BigInteger("-60")).toString(), big1.gcd(createNumber("-60")).toString());

        java.math.BigInteger bigO2 = new BigInteger("-60");
        AbstractBigInteger big2 = createNumber("-60");
        assertEquals(bigO2.gcd(new BigInteger("-48")).toString(), big2.gcd(createNumber("-48")).toString());

        java.math.BigInteger bigO3 = new BigInteger("-589621");
        AbstractBigInteger big3 = createNumber("-589621");
        assertEquals(bigO3.gcd(new BigInteger("0")).toString(), big3.gcd(createNumber("0")).toString());

        java.math.BigInteger bigO4 = new BigInteger("0");
        AbstractBigInteger big4 = createNumber("0");
        assertEquals(bigO4.gcd(new BigInteger("-589962")).toString(), big4.gcd(createNumber("-589962")).toString());

        java.math.BigInteger bigO5 = new BigInteger("0");
        AbstractBigInteger big5 = createNumber("0");
        assertEquals(bigO5.gcd(new BigInteger("0")).toString(), big5.gcd(createNumber("0")).toString());
    }

    public void testGcdExtreme() {
        java.math.BigInteger bigO = new BigInteger("894651846512358698584651248651244561238465132");
        AbstractBigInteger big = createNumber("894651846512358698584651248651244561238465132");
        assertEquals(bigO.gcd(new BigInteger("7984651324589")).toString(), big.gcd(createNumber("7984651324589")).toString());
    }

    //Test setBit()
    @Test
    public void testSetBit() {
        java.math.BigInteger bigO = new BigInteger("58796564");
        AbstractBigInteger big = createNumber("58796564");
        assertEquals(bigO.setBit(7).toString(), big.setBit(7).toString());

        java.math.BigInteger bigO1 = new BigInteger("3564542");
        AbstractBigInteger big1 = createNumber("3564542");
        assertEquals(bigO1.setBit(3).toString(), big1.setBit(3).toString());

        java.math.BigInteger bigO2 = new BigInteger("4567");
        AbstractBigInteger big2 = createNumber("4567");
        assertEquals(bigO2.setBit(19).toString(), big2.setBit(19).toString());

        java.math.BigInteger bigO3 = new BigInteger("2452453");
        AbstractBigInteger big3 = createNumber("2452453");
        assertEquals(bigO3.setBit(0).toString(), big3.setBit(0).toString());

        java.math.BigInteger bigO4 = new BigInteger("-58796564");
        AbstractBigInteger big4 = createNumber("-58796564");
        assertEquals(bigO4.setBit(3).toString(), big4.setBit(3).toString());

        java.math.BigInteger bigO5 = new BigInteger("-587");
        AbstractBigInteger big5 = createNumber("-587");
        assertEquals(bigO5.setBit(12).toString(), big5.setBit(12).toString());
    }

    // Test modPow()
    @Test
    public void testModPow() {
        java.math.BigInteger bigBase = new BigInteger("58796564");
        java.math.BigInteger bigExp = new BigInteger("587");
        java.math.BigInteger bigMod = new BigInteger("350");
        AbstractBigInteger bigBase2 = createNumber("58796564");
        AbstractBigInteger bigExp2 = createNumber("587");
        AbstractBigInteger bigMod2 = createNumber("350");
        assertEquals(bigBase.modPow(bigExp, bigMod).toString(), bigBase2.modPow(bigExp2, bigMod2).toString());
    }

    @Test
    public void testModPowNegativeExponent() {
        java.math.BigInteger bigBase = new BigInteger("5871");
        java.math.BigInteger bigExp = new BigInteger("-650");
        java.math.BigInteger bigMod = new BigInteger("350");
        AbstractBigInteger bigBase2 = createNumber("5871");
        AbstractBigInteger bigExp2 = createNumber("-650");
        AbstractBigInteger bigMod2 = createNumber("350");
        assertEquals(bigBase.modPow(bigExp, bigMod).toString(), bigBase2.modPow(bigExp2, bigMod2).toString());
    }

    @Test
    public void testModPowNegatives() {
        java.math.BigInteger bigBase = new BigInteger("-5871");
        java.math.BigInteger bigExp = new BigInteger("-650");
        java.math.BigInteger bigMod = new BigInteger("350");
        AbstractBigInteger bigBase2 = createNumber("-5871");
        AbstractBigInteger bigExp2 = createNumber("-650");
        AbstractBigInteger bigMod2 = createNumber("350");
        assertEquals(bigBase.modPow(bigExp, bigMod).toString(), bigBase2.modPow(bigExp2, bigMod2).toString());
    }

    // TEST modInverse
    @Test
    public void testModInverse1() {
        java.math.BigInteger bigBase = new BigInteger("58");
        java.math.BigInteger bigMod = new BigInteger("35");
        AbstractBigInteger bigBase2 = createNumber("58");
        AbstractBigInteger bigMod2 = createNumber("35");
        assertEquals(bigBase.modInverse(bigMod).toString(), bigBase2.modInverse(bigMod2).toString());
    }
    
    @Test
    public void testModInverse2() {
        java.math.BigInteger bigBase = new BigInteger("846518465132861");
        java.math.BigInteger bigMod = new BigInteger("7845");
        AbstractBigInteger bigBase2 = createNumber("846518465132861");
        AbstractBigInteger bigMod2 = createNumber("7845");
        assertEquals(bigBase.modInverse(bigMod).toString(), bigBase2.modInverse(bigMod2).toString());
    }
    
    //Test TestBit
    @Test
    public void testTestBit() {
        java.math.BigInteger bigO = new BigInteger("2548");
        AbstractBigInteger big = createNumber("2548");
        assertEquals(bigO.testBit(3), big.testBit(3));

        java.math.BigInteger bigO1 = new BigInteger("1001010001010", 2);
        AbstractBigInteger big1 = createNumber("1001010001010", 2);
        assertEquals(bigO1.testBit(15), big1.testBit(15));

        java.math.BigInteger bigO2 = new BigInteger("-856");
        AbstractBigInteger big2 = createNumber("-856");
        assertEquals(bigO2.testBit(4), big2.testBit(4));

        java.math.BigInteger bigO3 = new BigInteger("-89656225621");
        AbstractBigInteger big3 = createNumber("-89656225621");
        assertEquals(bigO3.testBit(7), big3.testBit(7));

        java.math.BigInteger bigO4 = new BigInteger("758962721365");
        AbstractBigInteger big4 = createNumber("758962721365");
        assertEquals(bigO4.testBit(12), big4.testBit(12));
    }

    //Test getLowestSetBit()
    @Test
    public void testGetLowestSetBit() {
        java.math.BigInteger bigO = new BigInteger("1254863");
        AbstractBigInteger big = createNumber("1254863");
        assertEquals(bigO.getLowestSetBit(), big.getLowestSetBit());

        java.math.BigInteger bigO1 = new BigInteger("2355478962");
        AbstractBigInteger big1 = createNumber("2355478962");
        assertEquals(bigO1.getLowestSetBit(), big1.getLowestSetBit());

        java.math.BigInteger bigO2 = new BigInteger("-8546982");
        AbstractBigInteger big2 = createNumber("-8546982");
        assertEquals(bigO2.getLowestSetBit(), big2.getLowestSetBit());

        java.math.BigInteger bigO3 = new BigInteger("-123547");
        AbstractBigInteger big3 = createNumber("-123547");
        assertEquals(bigO3.getLowestSetBit(), big3.getLowestSetBit());

        java.math.BigInteger bigO4 = new BigInteger("13647854");
        AbstractBigInteger big4 = createNumber("13647854");
        assertEquals(bigO4.getLowestSetBit(), big4.getLowestSetBit());

        java.math.BigInteger bigO5 = new BigInteger("2321324564782145785233");
        AbstractBigInteger big5 = createNumber("2321324564782145785233");
        assertEquals(bigO5.getLowestSetBit(), big5.getLowestSetBit());

    }

    //Test flipBit()
    @Test
    public void testFlipTest() {
        java.math.BigInteger bigO = new BigInteger("2321324564782145785233");
        AbstractBigInteger big = createNumber("2321324564782145785233");
        assertEquals(bigO.flipBit(5).toString(), big.flipBit(5).toString());

        java.math.BigInteger bigO1 = new BigInteger("5698532111");
        AbstractBigInteger big1 = createNumber("5698532111");
        assertEquals(bigO1.flipBit(3).toString(), big1.flipBit(3).toString());

        java.math.BigInteger bigO2 = new BigInteger("854523221458");
        AbstractBigInteger big2 = createNumber("854523221458");
        assertEquals(bigO2.flipBit(7).toString(), big2.flipBit(7).toString());

        java.math.BigInteger bigO3 = new BigInteger("2321324564782145785233");
        AbstractBigInteger big3 = createNumber("2321324564782145785233");
        assertEquals(bigO3.flipBit(19).toString(), big3.flipBit(19).toString());

        java.math.BigInteger bigO4 = new BigInteger("25441111");
        AbstractBigInteger big4 = createNumber("25441111");
        assertEquals(bigO4.flipBit(20).toString(), big4.flipBit(20).toString());

        java.math.BigInteger bigO5 = new BigInteger("-58696522");
        AbstractBigInteger big5 = createNumber("-58696522");
        assertEquals(bigO5.flipBit(10).toString(), big5.flipBit(10).toString());

        java.math.BigInteger bigO6 = new BigInteger("5845476632548");
        AbstractBigInteger big6 = createNumber("5845476632548");
        assertEquals(bigO6.flipBit(9).toString(), big6.flipBit(9).toString());
    }

}
