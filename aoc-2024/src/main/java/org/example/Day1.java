package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day1 {
    public static void main(String[] args) {
        List<String> fileContent = Utils.getContentFromFileByLine(1, "input.txt");

        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        for (String l : fileContent) {
            String[] split = l.split(" {3}");
            left.add(Integer.parseInt(split[0]));
            right.add(Integer.parseInt(split[1]));
        }

        Collections.sort(left);
        Collections.sort(right);

        int distRes = 0;
        for (int i = 0; i < left.size(); i++) {
            distRes += Math.abs(left.get(i) - right.get(i));
        }
        System.out.println("Distance: " + distRes);

        int simRes = 0;
        for (int leftItem : left) {
            int c = 0;
            for (int rightItem : right) {
                if (leftItem == rightItem) {
                    c++;
                }
            }
            simRes += c * leftItem;
        }
        System.out.println("Similarity: " + simRes);
    }
}
