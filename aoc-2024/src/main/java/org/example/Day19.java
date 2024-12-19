package org.example;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day19 {
    public static void main(String[] args) {
        List<String> fileContent = Utils.getContentFromFileByLine(19, "input.txt");

        String[] towelPatternsList = fileContent.get(0).split(", ");
        Map<Character, List<String>> towelPatterns = new HashMap<>();
        towelPatterns.put('w', new ArrayList<>());
        towelPatterns.put('u', new ArrayList<>());
        towelPatterns.put('b', new ArrayList<>());
        towelPatterns.put('r', new ArrayList<>());
        towelPatterns.put('g', new ArrayList<>());
        for (String towelPattern : towelPatternsList) {
            char key = towelPattern.charAt(0);
            towelPatterns.get(key).add(towelPattern);
        }

        List<String> patternsToTest = new ArrayList<>();
        for (int i = 2; i < fileContent.size(); i++) {
            patternsToTest.add(fileContent.get(i));
        }

        int validPaths = 0;
        BigInteger totalValidPaths = BigInteger.ZERO;
        for (String fullPattern : patternsToTest) {
            System.out.print(fullPattern);
            long pathCount = patternToTry(fullPattern, towelPatterns, "", new HashMap<>());
            if (pathCount > 0) {
                validPaths += 1;
                System.out.println(" - valid (" + pathCount + ")");
            } else {
                System.out.println(" - not valid");
            }
            totalValidPaths = totalValidPaths.add(BigInteger.valueOf(pathCount));
        }
        System.out.println("Amount of valid orderings: " + validPaths);
        System.out.println("Amount of valid combinations: " + totalValidPaths);
    }


    private static long patternToTry(String fullPattern, Map<Character, List<String>> towelPatterns, String currPattern, Map<String, Long> memo) {
        if (memo.containsKey(currPattern)) {
            return memo.get(currPattern);
        }

        if (fullPattern.equals(currPattern)) {
            memo.put(currPattern, 1L);
            return 1L;
        }

        if (!fullPattern.startsWith(currPattern)) {
            memo.put(currPattern, 0L);
            return 0L;
        }

        long totalPaths = 0;

        for (String pattern : towelPatterns.get(fullPattern.charAt(currPattern.length()))) {
            totalPaths += patternToTry(fullPattern, towelPatterns, currPattern + pattern, memo);
        }

        memo.put(currPattern, totalPaths);
        return totalPaths;
    }
}
