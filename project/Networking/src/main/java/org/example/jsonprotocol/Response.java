package org.example.jsonprotocol;


import org.example.Domain.Bug;
import org.example.Domain.Programmer;
import org.example.Domain.Tester;

import java.io.Serializable;
import java.util.List;


public class Response implements Serializable {
    private ResponseType type;
    private String errorMessage;
    private Tester tester;
    private Programmer programmer;
    private List<Bug> bugs;

    public Response() {
    }

    public ResponseType getType() {
        return type;
    }

    public void setType(ResponseType type) {
        this.type = type;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Tester getTester() {
        return tester;
    }

    public Programmer getProgrammer() {
        return programmer;
    }

    public void setTester(Tester tester) {
        this.tester = tester;
    }

    public void setProgrammer(Programmer programmer) {
        this.programmer = programmer;
    }

    public List<Bug> getBugs() {
        return bugs;
    }

    public void setBugs(List<Bug> bugs) {
        this.bugs = bugs;
    }

    @Override
    public String toString() {
        return "Response{" +
                "type=" + type +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
