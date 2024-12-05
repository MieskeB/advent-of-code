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

        List<Book> incorrectBooks = new ArrayList<>();
        int correctCountedResults = 0;
        for (Book book : books) {
            boolean incorrectBook = false;
            boolean incorrectBookIsAdded = false;
            for (Rule rule : rules) {
                boolean foundAfter = false;
                for (String page : book) {
                    if (page.equals(rule.after))
                        foundAfter = true;
                    if (page.equals(rule.before)) {
                        if (foundAfter) {
                            book.addCollidingRule(rule);
                            if (!incorrectBookIsAdded) {
                                incorrectBookIsAdded = true;
                                incorrectBooks.add(book);
                                incorrectBook = true;
                            }
                            break;
                        }
                        break;
                    }
                }
            }

            if (!incorrectBook)
                correctCountedResults += book.getMedian();
        }
        System.out.println("Correct median count: " + correctCountedResults);


        int correctedCountedResults = 0;
        for (Book book : incorrectBooks) {
            Collections.reverse(book.collidingRules);
            for (Rule rule : book.collidingRules) {
                int is = 0;
                int ie = 0;
                for (int i = 0; i < book.getPages().size(); i++) {
                    if (book.getPages().get(i).equals(rule.before)) {
                        is = i;
                    } else if (book.getPages().get(i).equals(rule.after)) {
                        ie = i;
                    }
                }
                String ts = book.getPages().get(is);
                String te = book.getPages().get(ie);
                book.getPages().set(is, te);
                book.getPages().set(ie, ts);
            }

            correctedCountedResults += book.getMedian();
        }

        System.out.println("Corrected median count: " + correctedCountedResults);
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
        private List<Rule> collidingRules;

        public Book(String inp) {
            this.collidingRules = new ArrayList<>();
            this.pages = new ArrayList<>();
            this.pages.addAll(Arrays.asList(inp.split(",")));
        }

        public void addCollidingRule(Rule rule) {
            this.collidingRules.add(rule);
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
