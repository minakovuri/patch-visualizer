package org.volgatech.patchvisualizer.infrastructure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ApplicationControllerTest {
    @Test
    public void checkIfNotThrowsIfFileNotFound() {
        String[] args = new String[8];
        args[0] = "--file";
        args[1] = "path1";
        args[2] = "--patch";
        args[3] = "path3";
        args[4] = "--out";
        args[5] = "path5";
        args[6] = "--mode";
        args[7] = "split";

        Assertions.assertDoesNotThrow(() -> ApplicationController.handle(args));
    }
}
