package org.volgatech.patchvisualizer.infrastructure.htmlgenerator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.volgatech.patchvisualizer.app.model.OutCommitLine;
import org.volgatech.patchvisualizer.app.model.PatchInfo;
import org.volgatech.patchvisualizer.app.service.CommitLineGenerator;
import org.volgatech.patchvisualizer.app.service.MainFileReader;
import org.volgatech.patchvisualizer.app.service.PatchReader;
import org.volgatech.patchvisualizer.infrastructure.arguments.DisplayMode;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

public class HtmlGeneratorTest {
    @Test
    public void checkGeneratorCreatesValidHTMLFileInSplitMode() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL patch = classLoader.getResource("htmlgeneratortestfiles/testSourceFile.txt");
        URL input = classLoader.getResource("htmlgeneratortestfiles/testSourceFilePatch.patch");
        URL result = classLoader.getResource("htmlgeneratortestfiles/splitExpectedOutput.html");

        if (patch == null || input == null || result == null) {
            fail("Test file not found");
        }

        File patchFile = new File(patch.getFile());
        File inputFile = new File(input.getFile());
        File resultFile = new File(result.getFile());

        PatchInfo patchInfo = PatchReader.parsePatchInfo(new String(Files.readAllBytes(Paths.get(patchFile.getPath()))));

        String contents = new String(Files.readAllBytes(Paths.get(inputFile.getPath())));
        List<String> mfrLines = MainFileReader.parseLines(contents);

        CommitLineGenerator clg = new CommitLineGenerator();
        List<OutCommitLine> commitList = clg.getOutCommitLines(mfrLines, patchInfo.getDifferenceBlocks());

        String shouldReturnHtml = new String(Files.readAllBytes(Paths.get(resultFile.getPath())));
        String returnedHtml = HtmlGenerator.generateHtml(DisplayMode.SPLIT, commitList, patchInfo.getCommitInfo());

        Assertions.assertEquals(shouldReturnHtml, returnedHtml);
    }

    @Test
    public void checkGeneratorCreatesValidHTMLFileInUnifiedMode() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL patch = classLoader.getResource("htmlgeneratortestfiles/testSourceFile.txt");
        URL input = classLoader.getResource("htmlgeneratortestfiles/testSourceFilePatch.patch");
        URL result = classLoader.getResource("htmlgeneratortestfiles/unifiedExpectedOutput.html");

        if (patch == null || input == null || result == null) {
            fail("Test file not found");
        }

        File patchFile = new File(patch.getFile());
        File inputFile = new File(input.getFile());
        File resultFile = new File(result.getFile());

        PatchInfo patchInfo = PatchReader.parsePatchInfo(new String(Files.readAllBytes(Paths.get(patchFile.getPath()))));

        String contents = new String(Files.readAllBytes(Paths.get(inputFile.getPath())));
        List<String> mfrLines = MainFileReader.parseLines(contents);

        CommitLineGenerator clg = new CommitLineGenerator();
        List<OutCommitLine> commitList = clg.getOutCommitLines(mfrLines, patchInfo.getDifferenceBlocks());

        String shouldReturnHtml = new String(Files.readAllBytes(Paths.get(resultFile.getPath())));
        String returnedHtml = HtmlGenerator.generateHtml(DisplayMode.UNIFIED, commitList, patchInfo.getCommitInfo());

        Assertions.assertEquals(shouldReturnHtml, returnedHtml);
    }

}
