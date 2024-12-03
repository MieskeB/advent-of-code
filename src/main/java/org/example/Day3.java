package org.example;

import java.util.List;

public class Day3 {
    public static void main(String[] args) {
        List<String> fileContent = Utils.getContentFromFileByLine(3, "input.txt");

        int res = 0;
        boolean doing = true;
        for (String l : fileContent) {
            char[] cs = l.toCharArray();
            String buffer = "";
            for (int i = 0; i < l.length(); i++) {
                buffer += cs[i];
                if (buffer.matches("^mul\\(\\d+,\\d+\\)$")) {
                    if (doing) {
                        String numbers = buffer.split("\\(")[1].split("\\)")[0];
                        int left = Integer.parseInt(numbers.split(",")[0]);
                        int right = Integer.parseInt(numbers.split(",")[1]);
                        res += left * right;
                        buffer = "";
                    } else {
                        buffer = "";
                    }
                } else if (buffer.matches("^do\\(\\)$")) {
                    doing = true;
                    buffer = "";
                } else if (buffer.matches("^don't\\(\\)$")) {
                    doing = false;
                    buffer = "";
                } else if (!(buffer.matches("^m(u(l(\\((\\d*(,(\\d*(\\))?)?)?)?)?)?)?$") ||
                        buffer.matches("^d(o(\\((\\))?)?)?$") ||
                        buffer.matches("^d(o(n('(t(\\((\\))?)?)?)?)?)?$"))) {
                    buffer = "";
                }
            }
        }
        System.out.println(res);
    }
}
