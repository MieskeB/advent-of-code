package org.example;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Day11 {
    public static void main(String[] args) {
        List<String> fileContent = Utils.getContentFromFileByLine(11, "input.txt");

        List<BigInteger> stones = new ArrayList<>();
        for (String stone : fileContent.get(0).split(" ")) {
            stones.add(new BigInteger(stone));
        }

        for (int blinks = 0; blinks < 75; blinks++) {
            for (int i = 0; i < stones.size(); i++) {
                BigInteger currentStoneValue = stones.get(i);
                if (currentStoneValue.equals(BigInteger.ZERO)) {
                    stones.set(i, BigInteger.ONE);
                } else if ((currentStoneValue).toString().length() % 2 == 0) {
                    String currentStoneValueAsString = currentStoneValue.toString();
                    int mid = currentStoneValueAsString.length() / 2;
                    stones.set(i, new BigInteger(currentStoneValueAsString.substring(0, mid)));
                    i++;
                    stones.add(i, new BigInteger(currentStoneValueAsString.substring(mid)));
                } else {
                    stones.set(i, currentStoneValue.multiply(BigInteger.valueOf(2024)));
                }
            }
            System.out.println(blinks + ": " + stones.size());
        }

        System.out.println("Amount of stones after 25 blinks: " + stones.size());


        for (int blinks = 0; blinks < 75; blinks++) {

        }
    }
}
