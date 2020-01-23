package org.volgatech.patchvisualizer.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.volgatech.patchvisualizer.app.model.PatchInfo;
import org.volgatech.patchvisualizer.app.service.PatchReader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class PatchReaderTest {
    @Test
    public void patchReaderReturnValidPatchInfo() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL r = classLoader.getResource("patchreadertestfiles/input1.patch");
        if (r == null) {
            fail("Test file not found");
        }

        File file = new File(r.getFile());
        String contents = new String(Files.readAllBytes(Paths.get(file.getPath())));
        PatchInfo data = PatchReader.parsePatchInfo(contents);

        assertEquals("Subject: [PATCH] Update HelloWorld.java", data.getCommitInfo().getSubject());
        assertEquals("Date: Thu, 30 Nov 2017 23:20:04 +0300", data.getCommitInfo().getDate());
    }
}
