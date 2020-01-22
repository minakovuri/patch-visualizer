package org.volgatech.patchvisualizer.app.service;

import java.util.Arrays;
import java.util.List;

public class MainFileReader {
    public static List<String> parseLines(String mainFileText) {
        return Arrays.asList(mainFileText.split("\\r?\\n"));
    }
}
