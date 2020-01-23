package org.volgatech.patchvisualizer.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.volgatech.patchvisualizer.app.service.MainFileReader;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainFileReaderTest {
    List<File> m_testFiles;

    @BeforeEach
    public void init() {
        m_testFiles = new ArrayList<>();

        ClassLoader classLoader = getClass().getClassLoader();
        URL r = classLoader.getResource("mainfilereadertestfiles/input1.txt");
        if (r != null) {
            File file = new File(r.getFile());
            m_testFiles.add(file);
        }
    }

    @Test
    public void readFileWithMainFileReaderAndCompareStrings() throws IOException {
        for (File f : m_testFiles) {
            Scanner sc = new Scanner(f);
            List<String> lines = new ArrayList<>();
            while (sc.hasNextLine()) {
                lines.add(sc.nextLine());
            }

            String contents = new String(Files.readAllBytes(Paths.get(f.getPath())));

            List<String> mfrLines = MainFileReader.parseLines(contents);

            Assertions.assertEquals(lines, mfrLines);
        }
    }
}
