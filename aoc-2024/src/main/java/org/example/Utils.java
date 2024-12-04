package org.example;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static List<String> getContentFromFileByLine(int challengeNumber, String fileName) {
        ClassLoader classLoader = Utils.class.getClassLoader();
        try {
            Path path = Paths.get(classLoader.getResource("Day" + challengeNumber + "/" + fileName).toURI());
            return Files.readAllLines(path);
        } catch (NullPointerException | URISyntaxException | IOException e) {
            return new ArrayList<>();
        }
    }
}
