package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day6 {
    public static void main(String[] args) {
        List<String> fileContent = Utils.getContentFromFileByLine(6, "input.txt");

        char[][] grid = new char[fileContent.get(0).length()][fileContent.size()];
        Location guardLoc = null;
        for (int i = 0; i < fileContent.size(); i++) {
            for (int j = 0; j < fileContent.get(i).length(); j++) {
                grid[j][i] = fileContent.get(i).toCharArray()[j];
                if (grid[j][i] == '^') {
                    guardLoc = new Location(Direction.UP, j, i);
                }
            }
        }

        int c = 0;
        int lastDisplayedPercent = -1;
        List<Location> loopLocations = new ArrayList<>();
        for (int x = 0; x < grid[0].length; x++) {
            for (int y = 0; y < grid.length; y++) {
                c++;
                int totalCells = grid[0].length * grid.length;
                int perc = (int) ((c * 100.0) / totalCells);

                if (perc % 5 == 0 && perc != lastDisplayedPercent) {
                    System.out.println("Now at " + perc + "%");
                    lastDisplayedPercent = perc;
                }

                if (grid[x][y] == '#' || grid[x][y] == '^') continue;

                grid[x][y] = 'O';
                boolean res = canBeLoop(grid, new Location(guardLoc));
                grid[x][y] = '.';

                if (!res) continue;

                Location printerLocation = new Location(null, x, y);
                if (!loopLocations.contains(printerLocation)) {
                    loopLocations.add(printerLocation);
                }
            }
        }
        System.out.println("Possible printer locations: " + loopLocations.size());

        List<Location> visitedLocations = new ArrayList<>();
        try {
            while (true) {
                Location location = new Location(null, guardLoc.x, guardLoc.y);
                if (!visitedLocations.contains(location)) {
                    visitedLocations.add(location);
                }

                int nextX = guardLoc.nextX();
                int nextY = guardLoc.nextY();

                if (grid[nextX][nextY] == '#') {
                    guardLoc.turnRight();
                } else {
                    guardLoc.doNextLoc();
                }
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Possible locations: " + visitedLocations.size());
        }
    }

    public static boolean canBeLoop(char[][] grid, Location guardLoc) {
        List<Location> trace = new ArrayList<>();
        guardLoc.compareWithDirection = true;

        try {
            while (true) {
                if (trace.contains(guardLoc)) {
                    return true;
                } else {
                    trace.add(new Location(guardLoc));
                }

                int nextX = guardLoc.nextX();
                int nextY = guardLoc.nextY();

                if (grid[nextX][nextY] == '#') {
                    guardLoc.turnRight();
                } else if (grid[nextX][nextY] == 'O') {
                    guardLoc.turnRight();
                } else {
                    guardLoc.doNextLoc();
                }
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    @Data
    @AllArgsConstructor
    public static class Location {
        private Direction direction;
        private int x;
        private int y;
        private boolean compareWithDirection = false;

        public Location(Location other) {
            this.direction = other.getDirection();
            this.x = other.getX();
            this.y = other.getY();
            this.compareWithDirection = other.compareWithDirection;
        }

        public Location(Direction direction, int x, int y) {
            this.direction = direction;
            this.x = x;
            this.y = y;
        }

        public int nextX() {
            return this.direction.getX(this.x);
        }

        public int nextY() {
            return this.direction.getY(this.y);
        }

        public void doNextLoc() {
            this.x = this.direction.getX(this.x);
            this.y = this.direction.getY(this.y);
        }

        public void turnRight() {
            this.direction = this.direction.getRightTurn();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Location location = (Location) obj;
            if (!compareWithDirection) {
                return x == location.x && y == location.y;
            } else {
                return x == location.x && y == location.y && direction == location.direction;
            }
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    public enum Direction {
        LEFT,
        RIGHT,
        UP,
        DOWN;

        int getX(int currX) {
            return switch (this) {
                case LEFT -> currX - 1;
                case RIGHT -> currX + 1;
                case UP, DOWN -> currX;
            };
        }

        int getY(int currY) {
            return switch (this) {
                case UP -> currY - 1;
                case DOWN -> currY + 1;
                case RIGHT, LEFT -> currY;
            };
        }

        Direction getRightTurn() {
            return switch (this) {
                case LEFT -> UP;
                case RIGHT -> DOWN;
                case UP -> RIGHT;
                case DOWN -> LEFT;
            };
        }
    }
}
