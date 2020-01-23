package org.volgatech.patchvisualizer.infrastructure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.volgatech.patchvisualizer.infrastructure.arguments.Arguments;
import org.volgatech.patchvisualizer.infrastructure.arguments.DisplayMode;


public class ArgumentsControllerTest {
    @Test
    public void checkArgumentsControllerFailsIfArgCountIsNotValid() {
        String[] args = new String[5];
        Assertions.assertThrows(Exception.class, () -> ArgumentsController.parseArguments(args));
    }

    @Test
    public void checkArgumentsControllerDoesNoThrowWithValidArguments() {
        String[] args = new String[8];
        args[0] = "--file";
        args[1] = "path1";
        args[2] = "--patch";
        args[3] = "path3";
        args[4] = "--out";
        args[5] = "path5";
        args[6] = "--mode";
        args[7] = "split";

        Assertions.assertDoesNotThrow(() -> ArgumentsController.parseArguments(args));
    }

    @Test
    public void checkArgumentsControllerThrowsWithInvalidMode() {
        String[] args = new String[8];
        args[0] = "--file";
        args[1] = "path1";
        args[2] = "--patch";
        args[3] = "path3";
        args[4] = "--out";
        args[5] = "path5";
        args[6] = "--mode";
        args[7] = "mode";

        Assertions.assertThrows(Exception.class, () -> ArgumentsController.parseArguments(args));
    }

    @Test
    public void checkArgumentsControllerThrowsWithInvalidPatch() {
        String[] args = new String[8];
        args[0] = "--file";
        args[1] = "path1";
        args[2] = "--patc";
        args[3] = "path3";
        args[4] = "--out";
        args[5] = "path5";
        args[6] = "--mode";
        args[7] = "split";

        Assertions.assertThrows(Exception.class, () -> ArgumentsController.parseArguments(args));
    }

    @Test
    public void checkArgumentsControllerThrowsWithInvalidFile() {
        String[] args = new String[8];
        args[0] = "--filee";
        args[1] = "path1";
        args[2] = "--patch";
        args[3] = "path3";
        args[4] = "--out";
        args[5] = "path5";
        args[6] = "--mode";
        args[7] = "split";

        Assertions.assertThrows(Exception.class, () -> ArgumentsController.parseArguments(args));
    }

    @Test
    public void checkArgumentsControllerReturnValidArguments() {
        String[] args = new String[8];
        args[0] = "--file";
        args[1] = "path1";
        args[2] = "--patch";
        args[3] = "path3";
        args[4] = "--out";
        args[5] = "path5";
        args[6] = "--mode";
        args[7] = "split";

        Arguments arguments = ArgumentsController.parseArguments(args);
        Assertions.assertEquals("path1", arguments.getFilePath());
        Assertions.assertEquals("path3", arguments.getPatchFilePath());
        Assertions.assertEquals("path5", arguments.getOutFilePath());
        Assertions.assertEquals(DisplayMode.SPLIT, arguments.getDisplayMode());
    }
}
