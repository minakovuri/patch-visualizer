package org.volgatech.patchvisualizer.infrastructure.arguments;

public class Arguments {
    private String filePath;
    private String patchFilePath;
    private String outFilePath;
    private DisplayMode displayMode;

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setPatchFilePath(String patchFilePath) {
        this.patchFilePath = patchFilePath;
    }

    public String getPatchFilePath() {
        return patchFilePath;
    }

    public void setOutFilePath(String outFilePath) {
        this.outFilePath = outFilePath;
    }

    public String getOutFilePath() {
        return outFilePath;
    }

    public void setDisplayMode(DisplayMode displayMode) {
        this.displayMode = displayMode;
    }

    public DisplayMode getDisplayMode() {
        return this.displayMode;
    }
}
