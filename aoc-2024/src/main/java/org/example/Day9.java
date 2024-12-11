package org.example;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Day9 {
    public static void main(String[] args) {
        List<String> fileContent = Utils.getContentFromFileByLine(9, "input.txt");

        int[] fullDiskMap = fileContent.get(0).chars().map(c -> c - '0').toArray();

        List<Character> memory = new ArrayList<>();
        for (int i = 0; i < fullDiskMap.length; i++) {
            boolean isDots = i % 2 != 0;
            for (int j = 0; j < fullDiskMap[i]; j++) {
                if (isDots) memory.add('.');
                else memory.add((char)(i / 2 + '0'));
            }
        }

        int c = 0;
        outer:
        for (int i = memory.size() - 1; i >= 0; i--) {
            if (memory.get(i) == '.') {
                memory.remove(i);
                continue;
            }

            for (; c < memory.size(); c++) {
                if (memory.get(c) == '.') {
                    memory.set(c, memory.get(i));
                    memory.remove(i);
                    continue outer;
                }
            }
            break;
        }

        BigInteger res = BigInteger.ZERO;
        for (int i = 0; i < memory.size(); i++) {
            res = res.add(BigInteger.valueOf(i).multiply(BigInteger.valueOf(memory.get(i) - '0')));
        }
        System.out.println(res);
    }
}
