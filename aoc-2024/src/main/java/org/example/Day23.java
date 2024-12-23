package org.example;

import java.util.*;

public class Day23 {
    public static void main(String[] args) {
        List<String> fileContent = Utils.getContentFromFileByLine(23, "input.txt");

        Map<String, List<String>> hasConnectionTo = new HashMap<>();
        for (String line : fileContent) {
            String c1 = line.split("-")[0].strip();
            String c2 = line.split("-")[1].strip();

            if (!hasConnectionTo.containsKey(c1)) {
                hasConnectionTo.put(c1, new ArrayList<>());
            }
            if (!hasConnectionTo.containsKey(c2)) {
                hasConnectionTo.put(c2, new ArrayList<>());
            }

            hasConnectionTo.get(c1).add(c2);
            hasConnectionTo.get(c2).add(c1);
        }


        List<LanConnection> lanConnectionsWith3Computers = new ArrayList<>();
        for (Map.Entry<String, List<String>> connectionsTo : hasConnectionTo.entrySet()) {
            for (int i = 0; i < connectionsTo.getValue().size() - 1; i++) {
                for (int j = i + 1; j < connectionsTo.getValue().size(); j++) {
                    if (hasConnectionTo.get(connectionsTo.getValue().get(i)).contains(connectionsTo.getValue().get(j))) {
                        LanConnection lanConnection = new LanConnection(connectionsTo.getKey(), connectionsTo.getValue().get(i), connectionsTo.getValue().get(j));
                        if (!lanConnectionsWith3Computers.contains(lanConnection)) {
                            lanConnectionsWith3Computers.add(lanConnection);
                        }
                    }
                }
            }
        }

        System.out.println("Amount of lan parties (==3): " + lanConnectionsWith3Computers.size());

        List<LanConnection> lanConnectionsWithT = new ArrayList<>();
        for (LanConnection lanConnection : lanConnectionsWith3Computers) {
            if (lanConnection.hasComputerStartingWithT()) {
                lanConnectionsWithT.add(lanConnection);
            }
        }
        System.out.println("Amount of lan parties (==3) with a computer starting with T: " + lanConnectionsWithT.size());

        LanConnection largestConnection = null;
        for (LanConnection lanConnection : lanConnectionsWith3Computers) {
            LanConnection currentConnection = new LanConnection();
            currentConnection.get().addAll(lanConnection.get());

            boolean expanded;
            do {
                expanded = false;
                List<String> toAdd = new ArrayList<>();
                for (String itemToAdd : hasConnectionTo.get(currentConnection.get(0))) {
                    // Check if `itemToAdd` is connected to all current computers
                    boolean valid = true;
                    for (String c : currentConnection.get()) {
                        if (!hasConnectionTo.get(c).contains(itemToAdd)) {
                            valid = false;
                            break;
                        }
                    }
                    if (valid && !currentConnection.get().contains(itemToAdd)) {
                        toAdd.add(itemToAdd);
                    }
                }

                if (!toAdd.isEmpty()) {
                    currentConnection.get().addAll(toAdd);
                    expanded = true;
                }
            } while (expanded);

            if (largestConnection == null || currentConnection.size() > largestConnection.size()) {
                largestConnection = currentConnection;
            }
        }

        System.out.println(largestConnection);
    }

    private record Connection(String computer1, String computer2) {
    }

    private static class LanConnection {
        private final List<String> connections;

        public LanConnection() {
            this.connections = new ArrayList<>();
        }

        public LanConnection(String c1, String c2, String c3) {
            this.connections = new ArrayList<>();
            this.connections.add(c1);
            this.connections.add(c2);
            this.connections.add(c3);
        }

        public List<String> get() {
            return this.connections;
        }

        public String get(int index) {
            return this.connections.get(index);
        }

        public boolean add(String computer) {
            if (this.connections.contains(computer))
                return false;
            this.connections.add(computer);
            return true;
        }

        public boolean containsOne(Connection connection) {
            return this.connections.contains(connection.computer1) || this.connections.contains(connection.computer2);
        }

        public boolean containsAll(Connection connection) {
            return this.connections.contains(connection.computer1) && this.connections.contains(connection.computer2);
        }

        public int size() {
            return this.connections.size();
        }

        public boolean hasComputerStartingWithT() {
            for (String c : this.connections) {
                if (c.startsWith("t")) {
                    return true;
                }
            }
            return false;
        }

        public boolean hasMinimalAmountOfConnections(int amount) {
            return this.connections.size() >= amount;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            LanConnection lanConnection = (LanConnection) obj;
            for (String computer : lanConnection.connections) {
                if (!this.connections.contains(computer)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.connections.get(0), this.connections.get(1), this.connections.get(2));
        }

        @Override
        public String toString() {
            List<String> sortedConnections = new ArrayList<>(this.connections);
            Collections.sort(sortedConnections);
            return String.join(",", sortedConnections);
        }
    }
}
