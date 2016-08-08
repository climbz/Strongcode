package com.climbz.strongcode;




public class Exercise {

    private String title;
    private String description;
    private String link;

    public Exercise(String title, String description, String link) {
        this.title = title;
        this.description = description;
        this.link = link;
    }

    public Exercise(){
        this.title = "blank";
        this.description = "";
        this.link = "";
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }
}
