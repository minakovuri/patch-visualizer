package org.volgatech.patchvisualizer.app;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.volgatech.patchvisualizer.app.model.PatchInfo;
import org.volgatech.patchvisualizer.app.service.DifferenceBlockValidator;
import org.volgatech.patchvisualizer.app.service.MainFileReader;
import org.volgatech.patchvisualizer.app.service.PatchReader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

public class DifferenceBlockValidatorTest {
    @Test
    public void differenceBlockValidatorDoesNotFailOnValidFile() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL patch = classLoader.getResource("differenceblockvalidatortestfiles/input1.patch");
        URL input = classLoader.getResource("differenceblockvalidatortestfiles/input1.txt");
        if (patch == null || input == null) {
            fail("Test file not found");
        }

        File patchFile = new File(patch.getFile());
        File inputFile = new File(input.getFile());

        PatchInfo patchInfo = PatchReader.parsePatchInfo(new String(Files.readAllBytes(Paths.get(patchFile.getPath()))));

        String contents = new String(Files.readAllBytes(Paths.get(inputFile.getPath())));
        List<String> mfrLines = MainFileReader.parseLines(contents);

        Assertions.assertDoesNotThrow(() -> DifferenceBlockValidator.check(patchInfo.getDifferenceBlocks(), mfrLines));
    }
}
