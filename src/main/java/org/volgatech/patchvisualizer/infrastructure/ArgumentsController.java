package org.volgatech.patchvisualizer.infrastructure;

import org.volgatech.patchvisualizer.infrastructure.arguments.Arguments;
import org.volgatech.patchvisualizer.infrastructure.arguments.DisplayMode;

import java.util.logging.Logger;

public class ArgumentsController {
    private static final int ARGUMENTS_LENGTH = 8;

    private static final String FILE_KEY = "--file";
    private static final String PATCH_FILE_KEY = "--patch";
    private static final String OUT_FILE_KEY = "--out";
    private static final String MODE_KEY = "--mode";

    private static final String UNIFIED_MODE = "unified";
    private static final String SPLIT_MODE = "split";

    private static final Logger LOG = Logger.getLogger(ArgumentsController.class.getName());

    public static Arguments parseArguments(String[] args) throws IllegalArgumentException {
        if (args.length != ARGUMENTS_LENGTH) {
            throw new IllegalArgumentException("Invalid number of arguments.");
        }

        Arguments arguments = new Arguments();

        for (int i = 0; i < args.length; i+=2) {
            String arg = args[i];
            switch (arg) {
                case FILE_KEY:
                    String filePath = args[i+1];
                    arguments.setFilePath(filePath);
                    break;
                case PATCH_FILE_KEY:
                    String patchFilePath = args[i+1];
                    arguments.setPatchFilePath(patchFilePath);
                    break;
                case OUT_FILE_KEY:
                    String outFilePath = args[i+1];
                    arguments.setOutFilePath(outFilePath);
                    break;
                case MODE_KEY:
                    String displayModeString = args[i+1];
                    DisplayMode displayMode = parseDisplayMode(displayModeString);
                    arguments.setDisplayMode(displayMode);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid command.");
            }
        }

        return arguments;
    }

    public static void showUsages() {
        LOG.info("java -jar gitVisualizer-1.0.jar <parameters>\r\n"
                + "--- Parameters: ---\r\n"
                + "    --file <full path to file>\r\n"
                + "    --patch <full path to .patch file>\r\n"
                + "    --out <full path to .html file>\r\n"
                + "    --mode <split or unified\r\n");
    }

    private static DisplayMode parseDisplayMode(String arg) throws IllegalArgumentException {
        switch (arg) {
            case UNIFIED_MODE:
                return DisplayMode.UNIFIED;
            case SPLIT_MODE:
                return DisplayMode.SPLIT;
            default:
                throw new IllegalArgumentException("Invalid display mode.");
        }
    }
}
