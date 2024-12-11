package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;

public class Day8 {
    public static void main(String[] args) {
        List<String> fileContent = Utils.getContentFromFileByLine(8, "test.txt");

        List<Antenna> antennas = new ArrayList<>();
        int c = 0;
        for (String line : fileContent) {
            char[] lineBytes = line.toCharArray();
            for (int i = 0; i < fileContent.get(0).length(); i++) {
                if (lineBytes[i] == '.') continue;

                Antenna antenna = new Antenna();
                antenna.setFrequency(lineBytes[i]);
                antenna.setLocation(new Location(i, c));
                antennas.add(antenna);
            }
            c++;
        }

        antennas.sort(Comparator.comparing(Antenna::getFrequency));

        // TODO
    }

    @Data
    public static class Antenna {
        private char frequency;
        private Location location;
    }

    @Data
    @AllArgsConstructor
    public static class Location {
        private int x;
        private int y;


        public boolean isLeftOf(Location other) {
            return x < other.getX();
        }

        public boolean isAboveOf(Location other) {
            return y < other.getY();
        }


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
