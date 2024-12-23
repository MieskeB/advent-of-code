package org.example;

import java.util.*;

public class Day20 {
    public static void main(String[] args) {
        List<String> fileContent = Utils.getContentFromFileByLine(20, "input.txt");

        char[][] grid = new char[fileContent.get(0).length()][fileContent.size()];
        Position start = new Position(0,0);
        Position goal = new Position(0,0);
        for (int y = 0; y < fileContent.size(); y++) {
            for (int x = 0; x < fileContent.get(0).length(); x++) {
                grid[x][y] = fileContent.get(y).charAt(x);
                if (fileContent.get(y).charAt(x) == 'S') {
                    start = new Position(x, y);
                } else if (fileContent.get(y).charAt(x) == 'E') {
                    goal = new Position(x, y);
                }
            }
        }

        displayGrid(grid);

        List<Position> shortestPath = aStar(grid, start, goal);
        assert shortestPath != null;
        int normalPath = shortestPath.size();

        List<CheatCode> cheatCodes = new ArrayList<>();
        for (int y = 1; y < fileContent.size() - 1; y++) {
            for (int x = 1; x < fileContent.get(0).length() - 1; x++) {
                if (grid[x][y] != '#') continue;

                grid[x][y] = '\u0000';
                List<Position> cheatPath = aStar(grid, start, goal);
                if (cheatPath.size() < normalPath) {
                    cheatCodes.add(new CheatCode(new Position(x, y), normalPath - cheatPath.size()));
                }
                grid[x][y] = '#';
            }
        }

        Map<Integer, Integer> saveTimeCount = new HashMap<>();
        for (CheatCode cheatCode : cheatCodes) {
            saveTimeCount.put(cheatCode.saveTime, saveTimeCount.getOrDefault(cheatCode.saveTime, 0) + 1);
        }

        List<Integer> sortedKeys = new ArrayList<>(saveTimeCount.keySet());
        Collections.sort(sortedKeys);

        int amountOfCheatsToSave100Seconds = 0;
        for (int saveTime : sortedKeys) {
            int count = saveTimeCount.get(saveTime);
            if (count > 1) {
                System.out.println("There are " + count + " cheats that save " + saveTime + " picoseconds.");
            } else {
                System.out.println("There is one cheat that saves " + saveTime + " picoseconds.");
            }

            if (saveTime >= 100) {
                amountOfCheatsToSave100Seconds += count;
            }
        }

        System.out.println(amountOfCheatsToSave100Seconds);
    }

    private static void displayGrid(char[][] grid) {
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid.length; x++) {
                if (grid[x][y] == '\u0000') {
                    System.out.print(".");
                } else {
                    System.out.print(grid[x][y]);
                }
            }
            System.out.println();
        }
    }


    private static List<Position> aStar(char[][] grid, Position start, Position goal) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparing(n -> n.fCost));
        openSet.add(new Node(start, 0, manhattenDistance(start, goal)));

        Map<Position, Position> cameFrom = new HashMap<>();
        Map<Position, Integer> gCostMap = new HashMap<>();
        gCostMap.put(start, 0);

        while (!openSet.isEmpty()) {
            Node currentNode = openSet.poll();
            Position currentPos = currentNode.position;

            if (currentPos.equals(goal)) {
                return reconstructPath(cameFrom, currentPos);
            }

            for (Position neighbour : getNeighbours(currentPos, grid)) {
                if (grid[neighbour.x][neighbour.y] == '#') continue;

                int expectedGCost = gCostMap.get(currentPos) + 1;

                if (!gCostMap.containsKey(neighbour) || expectedGCost < gCostMap.get(neighbour)) {
                    cameFrom.put(neighbour, currentPos);
                    gCostMap.put(neighbour, expectedGCost);
                    int fCost = expectedGCost + manhattenDistance(neighbour, goal);
                    openSet.add(new Node(neighbour, expectedGCost, fCost));
                }
            }
        }

        return null;
    }

    private static int manhattenDistance(Position a, Position b) {
        return Math.abs((a.x - b.x) + (a.y - b.y));
    }

    private static List<Position> reconstructPath(Map<Position, Position> cameFrom, Position current) {
        List<Position> path = new ArrayList<>();
        while (cameFrom.containsKey(current)) {
            path.add(current);
            current = cameFrom.get(current);
        }
        Collections.reverse(path);
        return path;
    }

    private static List<Position> getNeighbours(Position pos, char[][] grid) {
        List<Position> neighbors = new ArrayList<>();
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        for (int i = 0; i < 4; i++) {
            int newX = pos.x + dx[i];
            int newY = pos.y + dy[i];

            if (newX >= 0 && newX < grid[0].length && newY >= 0 && newY < grid.length) {
                neighbors.add(new Position(newX, newY));
            }
        }
        return neighbors;
    }

    private static class Position {
        int x;
        int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return x + " " + y;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Position position = (Position) obj;
            return x == position.x && y == position.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    private static class Node {
        Position position;
        int gCost;
        int fCost;

        public Node(Position position, int gCost, int fCost) {
            this.position = position;
            // Cost from start
            this.gCost = gCost;
            // Expected cost
            this.fCost = fCost;
        }
    }

    private static class CheatCode {
        Position position;
        int saveTime;

        public CheatCode(Position position, int saveTime) {
            this.position = position;
            this.saveTime = saveTime;
        }

        @Override
        public String toString() {
            return position.x + ", " + position.y + ": " + saveTime;
        }
    }
}
