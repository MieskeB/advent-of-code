package org.example;

import java.util.ArrayList;
import java.util.List;

public class Day25 {
    public static void main(String[] args) {
        List<String> fileContent = Utils.getContentFromFileByLine(25, "test.txt");

        List<Key> keys = new ArrayList<>();
        List<Lock> locks = new ArrayList<>();
        for (int i = 0; i < fileContent.size(); i++) {
            char[][] grid = new char[7][5];
            while (!fileContent.get(i).isEmpty()) {
                char[] chars = fileContent.get(i).toCharArray();
                for (int j = 0; j < chars.length; j++) {
                    grid[i][j] = chars[j];
                }
                i++;
            }

            KeyLock keyLock = new KeyLock();
            for (int a = 0; a < grid.length; a++) {
                int counter = 0;
                for (int b = 0; b < grid[i].length; b++) {
                }
            }
        }
    }

    private static class KeyLock {
        private final List<Integer> values;

        public KeyLock(int... values) {
            this.values = new ArrayList<>();
            for (int value : values) {
                this.values.add(value);
            }
        }

        public List<Integer> getValues() {
            return values;
        }

        public void addValue(int value) {
            this.values.add(value);
        }
    }

    private static class Key extends KeyLock {
        public Key(int... values) {
            super(values);
        }
    }

    private static class Lock extends KeyLock {
        public Lock(int... values) {
            super(values);
        }
    }
}
