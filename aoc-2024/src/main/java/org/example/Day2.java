package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day2 {
    public static void main(String[] args) {
        List<String> linesToTest = Utils.getContentFromFileByLine(2, "input.txt");

        int safeLines = 0;
        for (String l : linesToTest) {
            List<Integer> items = Arrays.stream(l.split(" ")).map(Integer::valueOf).toList();
            if (isSafe(items))
                safeLines++;
        }

        System.out.println("Safe lines: " + safeLines);

        int dSafeLines = 0;
        for (String l : linesToTest) {
            List<Integer> items = Arrays.stream(l.split(" ")).map(Integer::valueOf).toList();

            if (isSafeWithDampener(items))
                dSafeLines++;
        }

        System.out.println("Safe lines (Problem Dampener): " + dSafeLines);
    }

    private static boolean isSafeWithDampener(List<Integer> numbers) {
        for (int i = 0; i < numbers.size(); i++) {
            List<Integer> newNumbers = new ArrayList<>();
            for (int j = 0; j < numbers.size(); j++) {
                if (i != j) newNumbers.add(numbers.get(j));
            }
            if (isSafe(newNumbers)) return true;
        }
        return false;
    }

    private static boolean isSafe(List<Integer> numbers) {
        boolean isInc = numbers.get(0) < numbers.get(1);
        for (int i = 1; i < numbers.size(); i++) {
            int diff = Math.abs(numbers.get(i-1) - numbers.get(i));
            if (diff > 3 || diff < 1) {
                return false;
            }
            if (isInc) {
                if (!(numbers.get(i - 1) < numbers.get(i))) {
                    return false;
                }
            } else {
                if (!(numbers.get(i - 1) > numbers.get(i))) {
                    return false;
                }
            }
        }
        return true;
    }
}
