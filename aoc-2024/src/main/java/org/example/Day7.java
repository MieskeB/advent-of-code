package org.example;

import lombok.Data;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Day7 {
    public static void main(String[] args) {
        List<String> fileContent = Utils.getContentFromFileByLine(7, "input.txt");

        BigInteger res = BigInteger.ZERO;
        for (String line : fileContent) {
            Equation equation = new Equation();
            equation.setResult(new BigInteger(line.split(":")[0]));
            equation.setNumbers(new ArrayList<>());
            for (String number : line.split(": ")[1].strip().split(" ")) {
                equation.getNumbers().add(new BigInteger(number));
            }

            if (equation.isValid()) {
                res = res.add(equation.result);
            }
        }
        System.out.println(res);
    }

    @Data
    public static class Equation {
        private BigInteger result;
        private List<BigInteger> numbers;

        public boolean isValid() {
            return hasPathToResult(1, numbers.get(0));
        }

        private boolean hasPathToResult(int index, BigInteger currResult) {
            if (index == numbers.size()) {
                return currResult.equals(result);
            }

            return hasPathToResult(index + 1, currResult.add(numbers.get(index))) || hasPathToResult(index + 1, currResult.multiply(numbers.get(index))) || hasPathToResult(index + 1, new BigInteger(currResult.toString() + numbers.get(index)));
        }
    }
}
