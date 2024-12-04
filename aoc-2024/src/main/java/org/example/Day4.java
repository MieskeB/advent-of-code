package org.example;

import java.util.List;

public class Day4 {
    public static void main(String[] args) {
        List<String> fileContent = Utils.getContentFromFileByLine(4, "input.txt");

        char[][] input = new char[fileContent.get(0).length()][fileContent.size()];
        for (int i = 0; i < fileContent.size(); i++) {
            for (int j = 0; j < fileContent.get(i).length(); j++) {
                input[j][i] = fileContent.get(i).toCharArray()[j];
            }
        }

        int res = 0;
        for (int x = 0; x < input.length; x++) {
            for (int y = 0; y < input[0].length; y++) {
                if (input[x][y] == 'X') {
                    res += amountFromPoint(input, x, y);
                }
            }
        }
        System.out.println("Amount of XMAS: " + res);

        res = 0;
        for (int x = 0; x < input.length; x++) {
            for (int y = 0; y < input[0].length; y++) {
                if (input[x][y] == 'A')
                    if (crossCheck(input, x, y))
                        res++;
            }
        }
        System.out.println("Amount of crosses: " + res);
    }

    private static int amountFromPoint(char[][] input, int x, int y) {
        int res = 0;
        for (Direction d : Direction.values()) {
            if (isInDirection(input, d, x, y))
                res++;
        }
        return res;
    }

    private static boolean crossCheck(char[][] input, int x, int y) {
        try {
            StringBuilder lsb = new StringBuilder();
            lsb.append(input[Direction.LeftUp.getNextX(x)][Direction.LeftUp.getNextY(y)]);
            lsb.append('A');
            lsb.append(input[Direction.RightDown.getNextX(x)][Direction.RightDown.getNextY(y)]);
            String l = lsb.toString();
            StringBuilder rsb = new StringBuilder();
            rsb.append(input[Direction.LeftDown.getNextX(x)][Direction.LeftDown.getNextY(y)]);
            rsb.append('A');
            rsb.append(input[Direction.RightUp.getNextX(x)][Direction.RightUp.getNextY(y)]);
            String r = rsb.toString();

            return (l.equals("MAS") || l.equals("SAM")) && (r.equals("MAS") || r.equals("SAM"));
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    private static boolean isInDirection(char[][] input, Direction direction, int x, int y) {
        if (input[x][y] != 'X') return false;

        String w = "X";
        for (int i = 0; i < 3; i++) {
            x = direction.getNextX(x);
            y = direction.getNextY(y);

            try {
                w += input[x][y];
            } catch (ArrayIndexOutOfBoundsException e) {
                return false;
            }
        }
        return w.equals("XMAS");
    }

    private enum Direction {
        Up,
        RightUp,
        Right,
        RightDown,
        Down,
        LeftDown,
        Left,
        LeftUp;

        public int getNextX(int x) {
            switch (this) {
                case Up:
                case Down:
                    break;
                case Left:
                case LeftDown:
                case LeftUp:
                    return x - 1;
                case Right:
                case RightDown:
                case RightUp:
                    return x + 1;
            }
            return x;
        }

        public int getNextY(int y) {
            switch (this) {
                case Left:
                case Right:
                    break;
                case LeftDown:
                case Down:
                case RightDown:
                    return y + 1;
                case Up:
                case LeftUp:
                case RightUp:
                    return y - 1;
            }
            return y;
        }
    }
}
