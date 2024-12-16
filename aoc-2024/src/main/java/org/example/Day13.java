package org.example;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class Day13 {
    public static void main(String[] args) {
        List<String> fileContent = Utils.getContentFromFileByLine(13, "test.txt");

        List<Machine> machines = new ArrayList<>();
        {
            Machine machine = new Machine();
            for (String line : fileContent) {
                if (line.startsWith("Button")) {
                    Button button = new Button();
                    button.setXMovement(Integer.parseInt(line.split("X+")[1].split(",")[0]));
                    button.setYMovement(Integer.parseInt(line.split("Y+")[1]));
                    machine.getButtons().add(button);
                } else if (line.startsWith("Prize")) {
                    machine.setXPrize(Integer.parseInt(line.split("X=")[1].split(",")[0]) + 10000000000000L);
                    machine.setYPrize(Integer.parseInt(line.split("Y=")[1]) + 10000000000000L);
                } else {
                    machines.add(machine);
                    machine = new Machine();
                }
            }
            machines.add(machine);
        }


        int res = 0;
        for (Machine machine : machines) {
            int lowestPresses = Integer.MAX_VALUE;
            for (int aPressed = 0; aPressed < (machine.xPrize / machine.getLargestXMovement() + 1); aPressed++) {
                for (int bPressed = 0; bPressed < (machine.yPrize / machine.getLargestYMovement() + 1); bPressed++) {
                    if (((machine.getButtons().get(0).getXMovement() * aPressed) + (machine.getButtons().get(1).getXMovement() * bPressed)) == machine.xPrize &&
                            ((machine.getButtons().get(0).getYMovement() * aPressed) + (machine.getButtons().get(1).getYMovement() * bPressed)) == machine.yPrize) {
                        if (lowestPresses > aPressed * 3 + bPressed)
                            lowestPresses = aPressed * 3 + bPressed;
                    }
                }
            }
            if (lowestPresses != Integer.MAX_VALUE)
                res += lowestPresses;
        }

        System.out.println(res);
    }

    @Data
    private static class Machine {
        private List<Button> buttons;

        private long xPrize;
        private long yPrize;

        public long getLargestXMovement() {
            long largest = 0;
            for (Button button : this.buttons) {
                if (button.getXMovement() > largest) {
                    largest = button.getXMovement();
                }
            }
            return largest;
        }

        public long getLargestYMovement() {
            long largest = 0;
            for (Button button : this.buttons) {
                if (button.getYMovement() > largest) {
                    largest = button.getYMovement();
                }
            }
            return largest;
        }

        public Machine() {
            this.buttons = new ArrayList<>();
        }
    }

    @Data
    private static class Button {
        private long xMovement;
        private long yMovement;
    }
}
