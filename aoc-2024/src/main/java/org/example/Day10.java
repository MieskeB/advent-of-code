package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;

public class Day10 {
    public static void main(String[] args) {
        List<String> fileContent = Utils.getContentFromFileByLine(10, "input.txt");

        int gridWidth = fileContent.get(0).length();
        int gridHeight = fileContent.size();

        List<Location> grid = new ArrayList<>();
        for (int y = 0; y < gridHeight; y++) {
            for (int x = 0; x < gridWidth; x++) {
                grid.add(new Location(x, y, fileContent.get(y).toCharArray()[x] - '0'));
            }
        }

        int trailheads = 0;
        int trailheadRatings = 0;
        for (Location zeroLocation : grid) {
            if (zeroLocation.getValue() != 0) continue;

            Map<Integer, List<Location>> paths = new HashMap<>();
            List<Location> zeroList = new ArrayList<>();
            zeroList.add(zeroLocation);
            paths.put(0, zeroList);
            for (int i = 1; i <= 9; i++) {
                List<Location> foundLocationList = new ArrayList<>();
                for (Location location : paths.get(i - 1)) {
                    int finalI = i;
                    grid.stream().filter(loc -> loc.getX() == (location.getX() - 1) && loc.getY() == location.getY() && loc.getValue() == finalI).findFirst().ifPresent(foundLocationList::add);
                    grid.stream().filter(loc -> loc.getX() == (location.getX() + 1) && loc.getY() == location.getY() && loc.getValue() == finalI).findFirst().ifPresent(foundLocationList::add);
                    grid.stream().filter(loc -> loc.getX() == location.getX() && loc.getY() == (location.getY() - 1) && loc.getValue() == finalI).findFirst().ifPresent(foundLocationList::add);
                    grid.stream().filter(loc -> loc.getX() == location.getX() && loc.getY() == (location.getY() + 1) && loc.getValue() == finalI).findFirst().ifPresent(foundLocationList::add);
                }
                paths.put(i, foundLocationList);
            }

            List<Location> reachableDistinctNines = new ArrayList<>();
            for (Location location : paths.get(9)) {
                if (!reachableDistinctNines.contains(location))
                    reachableDistinctNines.add(location);
            }
            trailheads += reachableDistinctNines.size();
            trailheadRatings += paths.get(9).size();
        }
        System.out.println("Trailheads: " + trailheads);
        System.out.println("Trailhead ratings: " + trailheadRatings);
    }

    @AllArgsConstructor
    @Data
    public static class Location {
        private int x;
        private int y;
        private int value;

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Location location = (Location) obj;
            return x == location.x && y == location.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
