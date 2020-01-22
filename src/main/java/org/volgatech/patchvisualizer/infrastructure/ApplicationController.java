package org.volgatech.patchvisualizer.infrastructure;

import org.volgatech.patchvisualizer.app.service.DifferenceBlockValidator;
import org.volgatech.patchvisualizer.app.service.MainFileReader;
import org.volgatech.patchvisualizer.app.model.PatchInfo;
import org.volgatech.patchvisualizer.app.service.PatchReader;
import org.volgatech.patchvisualizer.infrastructure.arguments.Arguments;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
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
            String patchFileContent = Files.readString(patchFilePath);
            patchInfo = PatchReader.parsePatchInfo(patchFileContent);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage());
            return;
        }

        List<String> mainFileLines;
        try {
            Path mainFilePath = Paths.get(arguments.getFilePath());
            String mainFileContent = Files.readString(mainFilePath);
            mainFileLines = MainFileReader.parseLines(mainFileContent);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getMessage());
            return;
        }

        try {
            DifferenceBlockValidator.check(patchInfo.getDifferenceBlocks(), mainFileLines);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getMessage());
            return;
        }
    }
}
