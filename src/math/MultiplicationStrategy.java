package math;

/**
 *
 * @author Parisi Germán
 * @version 1.0
 */
public abstract class MultiplicationStrategy {

    abstract BigIntegerBytesList multiply(BigIntegerBytesList big1, BigIntegerBytesList big2);
}

class StandardMultiplication extends MultiplicationStrategy {

    @Override
    BigIntegerBytesList multiply(BigIntegerBytesList big1, BigIntegerBytesList big2) {
        int n1 = big1.digits.size();
        int n2 = big2.digits.size();

        BigIntegerBytesList[] bigIntegers = new BigIntegerBytesList[n2];
        for (int i = n2 - 1; i >= 0; i--) {
            int carriage = 0;
            StringBuilder partialResult = new StringBuilder();
            int digit2 = big2.digits.get(i).getDigit();
            for (int j = n1 - 1; j >= 0; j--) {
                int digit1 = big1.digits.get(j).getDigit();
                int z = digit1 * digit2 + carriage;
                int r = z % 10;
                carriage = z / 10;
                partialResult.insert(0, String.valueOf(r));
            }
            if (carriage != 0) {
                partialResult.insert(0, String.valueOf(carriage));
            }
            BigIntegerBytesList bigInteger = new BigIntegerBytesList(partialResult.toString());
            bigIntegers[n2 - i - 1] = bigInteger;
        }

        BigIntegerBytesList result = bigIntegers[0];
        for (int i = 1; i < bigIntegers.length; i++) {
            BigIntegerBytesList sum = bigIntegers[i];
            Digit digit = new Digit((byte) 0);
            int k = 0;
            while (k < i) {
                sum.digits.add(digit);
                k++;
            }
            result = result.add(sum);
        }

        result.negative = big1.negative != big2.negative;
        
        return result;
    }
}
