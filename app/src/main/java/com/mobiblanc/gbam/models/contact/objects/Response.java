package com.mobiblanc.gbam.models.contact.objects;

import java.util.List;

public class Response {
    private List<Subject> subjects;
    private List<CommandNumber> commandNumbers;

    public List<Subject> getSubjects() {
        return subjects;
    }

    public List<CommandNumber> getCommandNumbers() {
        return commandNumbers;
    }
}
