package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day16 {
    public static void main(String[] args) {
        List<String> fileContent = Utils.getContentFromFileByLine(16, "test.txt");

        Grid grid = new Grid(fileContent.get(0).length(), fileContent.size());
        for (int y = 0; y < fileContent.size(); y++) {
            char[] lineChars = fileContent.get(y).toCharArray();
            for (int x = 0; x < lineChars.length; x++) {
                switch (lineChars[x]) {
                    case '#' -> grid.getWalls().add(new Grid.Wall(x, y));
                    case 'S' -> {
                        grid.setCharacter(new Grid.Character(x, y, Grid.Character.Direction.NORTH));
                        grid.setStart(new Grid.Position(x, y));
                    }
                    case 'E' -> grid.setGoal(new Grid.Position(x, y));
                }
            }
        }

        int res = 0;
    }


    @Data
    private static class Grid {
        private int horizontalSize;
        private int verticalSize;
        private List<Wall> walls;
        private Character character;
        private Position start;
        private Position goal;


        public Grid(int horizontalSize, int verticalSize) {
            this.horizontalSize = horizontalSize;
            this.verticalSize = verticalSize;
            this.walls = new ArrayList<>();
            this.character = new Character(0, 0, Character.Direction.NORTH);
            this.start = new Position(0, 0);
            this.goal = new Position(horizontalSize - 1, verticalSize - 1);
        }


        public boolean characterCanMoveTo(Character.Direction direction) {
            return switch (this.character.direction) {
                case EAST ->
                        !(this.walls.contains(new Wall(character.x + 1, character.y)) || character.x + 1 >= horizontalSize);
                case SOUTH ->
                        !(this.walls.contains(new Wall(character.x, character.y + 1)) || character.y >= verticalSize);
                case NORTH -> !(this.walls.contains(new Wall(character.x, character.y - 1)) || character.y < 0);
                case WEST -> !(this.walls.contains(new Wall(character.x - 1, character.y)) || character.x < 0);
            };
        }

        public List<Character.Direction> getAllMoveDirections() {
            List<Character.Direction> directions = new ArrayList<>();
            if (characterCanMoveTo(Character.Direction.SOUTH) && !character.direction.isOpposite(Character.Direction.SOUTH)) directions.add(Character.Direction.SOUTH);
            if (characterCanMoveTo(Character.Direction.NORTH) && !character.direction.isOpposite(Character.Direction.NORTH)) directions.add(Character.Direction.NORTH);
            if (characterCanMoveTo(Character.Direction.EAST) && !character.direction.isOpposite(Character.Direction.EAST)) directions.add(Character.Direction.EAST);
            if (characterCanMoveTo(Character.Direction.WEST) && !character.direction.isOpposite(Character.Direction.WEST)) directions.add(Character.Direction.WEST);
            return directions;
        }

        public int doCharacterMove(Character.Direction direction) {
            int res = 0;
            if (character.direction != direction) {
                res += 1000;
            }
            res++;
            return res;
        }


        @Data
        @AllArgsConstructor
        public static class Wall {
            private int x;
            private int y;


            @Override
            public boolean equals(Object obj) {
                if (this == obj) return true;
                if (obj == null || getClass() != obj.getClass()) return false;
                Wall location = (Wall) obj;
                return x == location.x && y == location.y;
            }

            @Override
            public int hashCode() {
                return Objects.hash(x, y);
            }
        }

        @AllArgsConstructor
        @Data
        public static class Character {
            private int x;
            private int y;
            private Direction direction;


            public enum Direction {
                NORTH,
                EAST,
                SOUTH,
                WEST;

                public boolean isOpposite(Direction direction) {
                    return switch (this) {
                        case SOUTH -> direction == NORTH;
                        case WEST -> direction == EAST;
                        case EAST -> direction == WEST;
                        case NORTH -> direction == SOUTH;
                    };
                }
            }
        }

        @Data
        @AllArgsConstructor
        public static class Position {
            private int x;
            private int y;
        }
    }
}
