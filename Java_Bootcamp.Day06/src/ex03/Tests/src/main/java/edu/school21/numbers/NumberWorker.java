package edu.school21.numbers;

import edu.school21.exceptions.IllegalNumberException;

public class NumberWorker {
    public boolean isPrime(int number) {
        if (number <= 1) {
            throw new IllegalNumberException("Number must be a natural (positive integer) greater than 1.");
        }

        if (number == 2) {
            return true;
        }

        if (number % 2 == 0) {
            return false;
        }

        for (int i = 3; i <= Math.sqrt(number); i += 2) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    public int digitsSum(int number) {
        int sum = 0;
        int absNumber = Math.abs(number);

        while (absNumber > 0) {
            sum += absNumber % 10;
            absNumber /= 10;
        }

        return sum;
    }
}