package com.climbz.strongcode;


import java.util.ArrayList;

public class Exercise {

    private String title;
    private String description;
    private String link;
    private ArrayList<String> progression;

    public Exercise(String title, String description, String link, ArrayList<String> progression) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.progression = progression;
    }

    public Exercise(){
        this.title = "blank";
        this.description = "";
        this.link = "";
        this.progression = new ArrayList<String>();
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

    public void addProgression(String prog){
        progression.add(prog);
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<String> getProgression() {
        return progression;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }
}
