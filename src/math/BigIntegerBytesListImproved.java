package math;

import java.util.ArrayList;

/**
 *
 * @author Parisi Germán
 * @version 1.0
 */
public class BigIntegerBytesListImproved extends Number {

    protected ArrayList<Digit> digits;

    protected boolean negative;

    public BigIntegerBytesListImproved(String number) {
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
    }

    public BigIntegerBytesListImproved add(BigIntegerBytesListImproved val) {
        BigIntegerBytesListImproved result = null;
        StringBuilder resultString = new StringBuilder();

        result = new BigIntegerBytesListImproved(resultString.toString());
        return result;
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

}
