package org.volgatech.patchvisualizer.app.model;

public class CommitInfo {
    private Author author; // TODO: непонятно, зачем использовать это, если в name сетается вся строка, а не имя
    private String date;
    private String subject;
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String newName) {
        this.fileName = fileName;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
