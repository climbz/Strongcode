package com.climbz.strongcode;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class XMLExerciseParserHandler {
    List<Exercise> exercises;
    private Exercise exercise;
    private String text;

    public XMLExerciseParserHandler() {
        exercises = new ArrayList<Exercise>();
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public List<Exercise> parse(InputStream is) {
        XmlPullParserFactory factory = null;
        XmlPullParser parser = null;
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            parser = factory.newPullParser();

            parser.setInput(is, null);

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("exercise")) {
                            // create a new instance of exercise
                            exercise = new Exercise();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase("exercise")) {
                            // add exercise object to list
                            exercises.add(exercise);
                        } else if (tagname.equalsIgnoreCase("title")) {
                            exercise.setTitle(text);
                        } else if (tagname.equalsIgnoreCase("description")) {
                            exercise.setDescription(text);
                        } else if (tagname.equalsIgnoreCase("link")) {
                            exercise.setLink(text);
                        }
                        break;

                    default:
                        break;
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return exercises;
    }

}
