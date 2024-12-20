package org.example;

import java.util.*;

public class Day18 {
    public static void main(String[] args) {
        List<String> fileContent = Utils.getContentFromFileByLine(18, "input.txt");

        int gridSize = 71;
        int amountOfBytes = 1024;

        char[][] grid = new char[gridSize][gridSize];
        grid[0][0] = 'S';
        for (int i = 0; i < amountOfBytes; i++) {
            grid[Integer.parseInt(fileContent.get(i).split(",")[0])][Integer.parseInt(fileContent.get(i).split(",")[1])] = '#';
        }

        displayGrid(grid);

        List<Position> shortestPath = aStar(grid, new Position(0, 0), new Position(gridSize - 1, gridSize - 1));
        if (shortestPath == null) {
            System.out.println("Apples");
        } else {
            for (Position position : shortestPath) {
                grid[position.x][position.y] = 'O';
            }
            System.out.println(shortestPath.size());
        }

        displayGrid(grid);

        for (int i = amountOfBytes; i < fileContent.size(); i++) {
            grid[Integer.parseInt(fileContent.get(i).split(",")[0])][Integer.parseInt(fileContent.get(i).split(",")[1])] = '#';
            if (aStar(grid, new Position(0,0), new Position(gridSize - 1, gridSize - 1)) == null) {
                System.out.println(fileContent.get(i));
                break;
            }
        }
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
}
