package org.volgatech.patchvisualizer.app;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.volgatech.patchvisualizer.app.model.PatchInfo;
import org.volgatech.patchvisualizer.app.service.CommitLineGenerator;
import org.volgatech.patchvisualizer.app.service.MainFileReader;
import org.volgatech.patchvisualizer.app.service.PatchReader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

public class CommitLineGeneratorTest {
    @Test
    public void checkCommitLineGeneratorDoesNotThrowExceptionOnValidInput() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL input = classLoader.getResource("commitlinegeneratortestfiles/testSourceFile.txt");
        URL patch = classLoader.getResource("commitlinegeneratortestfiles/testSourceFilePatch.patch");
        if (patch == null || input == null) {
            fail("Test file not found");
        }

        File patchFile = new File(patch.getFile());
        File inputFile = new File(input.getFile());

        PatchInfo patchInfo = PatchReader.parsePatchInfo(new String(Files.readAllBytes(Paths.get(patchFile.getPath()))));

        String contents = new String(Files.readAllBytes(Paths.get(inputFile.getPath())));
        List<String> mfrLines = MainFileReader.parseLines(contents);

        CommitLineGenerator clg = new CommitLineGenerator();
        Assertions.assertDoesNotThrow(() -> clg.getOutCommitLines(mfrLines, patchInfo.getDifferenceBlocks()));
    }
}
