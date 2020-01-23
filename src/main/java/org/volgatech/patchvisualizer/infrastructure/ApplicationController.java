package org.volgatech.patchvisualizer.infrastructure;

import org.volgatech.patchvisualizer.app.model.CommitInfo;
import org.volgatech.patchvisualizer.app.model.GeneralCommitLine;
import org.volgatech.patchvisualizer.app.service.CommitLineGenerator;
import org.volgatech.patchvisualizer.app.service.DifferenceBlockValidator;
import org.volgatech.patchvisualizer.app.service.MainFileReader;
import org.volgatech.patchvisualizer.app.model.PatchInfo;
import org.volgatech.patchvisualizer.app.service.PatchReader;

import org.volgatech.patchvisualizer.infrastructure.htmlgenerator.HtmlGenerator;
import org.volgatech.patchvisualizer.infrastructure.arguments.Arguments;
import org.volgatech.patchvisualizer.infrastructure.arguments.DisplayMode;

import java.io.File;
import java.io.FileWriter;
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
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage());
            return;
        }

        try {
            DifferenceBlockValidator.check(patchInfo.getDifferenceBlocks(), mainFileLines);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage());
            return;
        }

        CommitLineGenerator commitLineGenerator = new CommitLineGenerator();
        List<GeneralCommitLine> generalCommitLines;
        try {
            generalCommitLines = commitLineGenerator.getGeneralCommitLines(mainFileLines, patchInfo.getDifferenceBlocks());
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage());
            return;
        }

        File outFile;
        FileWriter fileWriter = null;

        try {
            DisplayMode displayMode = arguments.getDisplayMode();
            CommitInfo commitInfo= patchInfo.getCommitInfo();
            String htmlText = HtmlGenerator.generateHtml(displayMode, generalCommitLines, commitInfo);

            outFile = new File(arguments.getOutFilePath());
            fileWriter = new FileWriter(outFile);

            fileWriter.write(htmlText);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage());
        } finally {
            try {
                assert fileWriter != null;
                fileWriter.close();
            } catch (IOException e) {
                LOG.log(Level.SEVERE, e.getMessage());
            }
        }
    }
}
