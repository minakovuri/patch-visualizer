package org.volgatech.patchvisualizer.infrastructure;

import org.volgatech.patchvisualizer.app.patch.PatchInfo;
import org.volgatech.patchvisualizer.app.patch.PatchReader;
import org.volgatech.patchvisualizer.infrastructure.arguments.Arguments;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ApplicationController {
    private static final Logger LOG = Logger.getLogger(ApplicationController.class.getName());

    public static void handle(String[] args) {
        Arguments arguments;
        try {
            arguments = ArgumentsController.parseArguments(args);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage());
            ArgumentsController.showUsages();
            return;
        }

        PatchInfo patchInfo;
        try {
            Path patchFilePath = Paths.get(arguments.getPatchFilePath());
            String patchFileContent = "";
            patchFileContent = Files.readString(patchFilePath);
            patchInfo = PatchReader.parsePatchInfo(patchFileContent);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage());
            return;
        }
    }
}
