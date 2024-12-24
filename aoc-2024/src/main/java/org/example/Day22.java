package org.example;

import java.util.ArrayList;
import java.util.List;

public class Day22 {
    public static void main(String[] args) {
        List<String> fileContent = Utils.getContentFromFileByLine(22, "input.txt");

        List<Integer> initialNumbers = new ArrayList<>();
        for (String line : fileContent) {
            initialNumbers.add(Integer.parseInt(line));
        }

        long res = 0;
        List<List<Change>> changes = new ArrayList<>();
        for (long secretNumber : initialNumbers) {

            int prevN = (int) (secretNumber % 10);

            System.out.print(secretNumber + ": ");
            changes.add(new ArrayList<>());
            changes.get(changes.size() - 1).add(new Change(prevN, 0));

            for (int i = 0; i < 2000; i++) {
                long value = secretNumber * 64L;
                secretNumber = mix(value, secretNumber);
                secretNumber = prune(secretNumber);

                value = (int) Math.floor((double) secretNumber / 32d);
                secretNumber = mix(value, secretNumber);
                secretNumber = prune(secretNumber);

                value = secretNumber * 2048L;
                secretNumber = mix(value, secretNumber);
                secretNumber = prune(secretNumber);

                int newN = (int) (secretNumber % 10);
                changes.get(changes.size() - 1).add(new Change(newN, newN - prevN));
                prevN = newN;
            }

            System.out.println(secretNumber);
            res += secretNumber;
        }

        System.out.println("Result: " + res);

        int highestValue = 0;
        for (int i1 = -9; i1 <= 9; i1++) {
            for (int i2 = -9; i2 <= 9; i2++) {
                for (int i3 = -9; i3 <= 9; i3++) {
                    for (int i4 = -9; i4 <= 9; i4++) {
                        int totalValue = 0;
                        changeListLoop:
                        for (List<Change> changeList : changes) {
                            for (int c = 0; c < changeList.size() - 3; c++) {
                                if (changeList.get(c).getChange() == i1 &&
                                        changeList.get(c + 1).getChange() == i2 &&
                                changeList.get(c + 2).getChange() == i3 &&
                                changeList.get(c + 3).getChange() == i4) {
                                    totalValue += changeList.get(c + 3).getValue();
                                    continue changeListLoop;
                                }
                            }
                        }
                        if (totalValue > highestValue) {
                            System.out.println("Found new combination with highest value: " + i1 + "," + i2 + "," + i3 + "," + i4);
                            highestValue = totalValue;
                        }
                    }
                }
            }
        }

        System.out.println("Highest possible value: " + highestValue);
    }

    private static long mix(long value, long secretNumber) {
        return value ^ secretNumber;
    }

    private static long prune(long secretNumber) {
        return secretNumber % 16777216L;
    }

    private static class Change {
        private int value;
        private int change;

        public Change(int value, int change) {
            this.value = value;
            this.change = change;
        }

        public int getValue() {
            return value;
        }

        public int getChange() {
            return change;
        }
    }
}
