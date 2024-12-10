package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;

public class Day5 {
    public static void main(String[] args) {
        List<String> fileContent = Utils.getContentFromFileByLine(5, "input.txt");

        List<Rule> rules = new ArrayList<>();
        List<Book> books = new ArrayList<>();

        boolean isAtRules = true;
        for (String l : fileContent) {
            if (l.isEmpty()) {
                isAtRules = false;
                continue;
            }

            if (isAtRules) {
                rules.add(new Rule(l.split("\\|")[0], l.split("\\|")[1]));
            } else {
                books.add(new Book(l));
            }
        }


        int correctCountedResults = 0;
        for (Book book : books) {
            if (isValid(book, rules))
                correctCountedResults += book.getMedian();
        }
        System.out.println("Correct median count: " + correctCountedResults);


        List<String> orderedList = getOrder(rules);
        int correctedCountedResults = 0;
        for (Book book : books) {
            if (isValid(book, rules)) continue;

            Map<String, Integer> orderMap = new HashMap<>();
            for (int i = 0; i < orderedList.size(); i++) {
                orderMap.put(orderedList.get(i), i);
            }

            book.pages.sort((a, b) -> {
                int indexA = orderMap.getOrDefault(a, Integer.MAX_VALUE);
                int indexB = orderMap.getOrDefault(b, Integer.MAX_VALUE);
                return Integer.compare(indexA, indexB);
            });

            correctedCountedResults += book.getMedian();
        }

        System.out.println("Corrected median count: " + correctedCountedResults);
    }


    private static boolean isValid(Book book, List<Rule> ruleSet) {
        for (Rule r : ruleSet) {
            boolean afterFound = false;
            for (String page : book.getPages()) {
                if (page.equals(r.before)) {
                    if (afterFound) {
                        return false;
                    }
                } else if (page.equals(r.after)) {
                    afterFound = true;
                }
            }
        }
        return true;
    }

    private static List<String> getOrder(List<Rule> ruleSet) {
        List<String> orderedList = new ArrayList<>();

        for (Rule rule : ruleSet) {
            if (!orderedList.contains(rule.before)) {
                orderedList.add(rule.before);
            }

            int beforeIndex = orderedList.indexOf(rule.before);
            if (!orderedList.contains(rule.after)) {
                if (beforeIndex == orderedList.size() - 1) {
                    orderedList.add(rule.after);
                } else {
                    orderedList.add(orderedList.indexOf(rule.before) + 1, rule.after);
                }
            } else {
                int afterIndex = orderedList.indexOf(rule.after);
                if (afterIndex < beforeIndex) {
                    orderedList.remove(afterIndex);
                    beforeIndex--;
                    if (beforeIndex == orderedList.size() - 1) {
                        orderedList.add(rule.after);
                    } else {
                        orderedList.add(beforeIndex + 1, rule.after);
                    }
                }
            }
        }

        return orderedList;
    }


    @Data
    @AllArgsConstructor
    private static class Rule {
        private String before;
        private String after;

        @Override
        public String toString() {
            return before + "|" + after;
        }
    }


    @Data
    private static class Book implements Iterable<String> {
        private List<String> pages;

        public Book(String inp) {
            this.pages = new ArrayList<>();
            this.pages.addAll(Arrays.asList(inp.split(",")));
        }

        public int getMedian() {
            int medianIndex = (int) Math.round((double) this.pages.size() / 2) - 1;
            return Integer.parseInt(this.pages.get(medianIndex));
        }

        @Override
        public Iterator<String> iterator() {
            return this.pages.iterator();
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (String p : pages) {
                sb.append(p);
                sb.append(",");
            }
            return sb.toString();
        }
    }
}
