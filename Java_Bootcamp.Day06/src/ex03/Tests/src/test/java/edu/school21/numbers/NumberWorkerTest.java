package edu.school21.numbers;

import edu.school21.exceptions.IllegalNumberException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class NumberWorkerTest {
     private final NumberWorker numberWorker = new NumberWorker();

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 5, 7, 11, 13, 17})
    void isPrimeForPrimers(int prime) {
        assertTrue(numberWorker.isPrime(prime));
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 6, 8, 9, 10, 12, 14, 15, 16})
    void isPrimeForNotPrimeNumbers(int composite) {
        assertFalse(numberWorker.isPrime(composite));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 1})
    void isPrimeForIncorrectNumbers(int incorrect) {
        assertThrows(IllegalNumberException.class, () -> numberWorker.isPrime(incorrect));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    void digitsSumTest(int number, int expectedSum) {
        assertEquals(expectedSum, numberWorker.digitsSum(number));
    }
}
