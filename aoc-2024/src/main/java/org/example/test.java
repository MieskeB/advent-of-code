package org.example;

public class test {

    public static void main(String[] args) {
        long maxValueUInt32 = (long) Math.pow(2, 32) - 1;
        long maxValueInt = (long) Math.pow(2, 31) - 1;

        int maxIterations = 0;
        for (int i = 0; i < (int)1e9; i++) {
            long x = i;
            long y = 1;

            int currIterations = 0;
            while (x < (int) 1e9) {
                x = x + y;
                y = 2 * y + 1;
                currIterations++;
            }
            if (maxIterations < currIterations) {maxIterations = currIterations;}

            if (x < maxValueUInt32 && y < maxValueInt) {
                continue;
            }

            String xGreater = x < maxValueUInt32 ? "smaller" : "greater";
            String yGreater = y < maxValueInt ? "smaller" : "greater";

            System.out.println("The value of x (" + x + ") is " + xGreater + " than max (" + maxValueUInt32 + ")");
            System.out.println("The value of y (" + y + ") is " + yGreater + " than max (" + maxValueInt + ")");
        }
        System.out.println(maxIterations);
    }
}
