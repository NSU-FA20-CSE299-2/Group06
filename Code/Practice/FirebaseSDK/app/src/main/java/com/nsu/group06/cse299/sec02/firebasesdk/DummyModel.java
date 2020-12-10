package com.nsu.group06.cse299.sec02.firebasesdk;

/*
model class just for testing written database classes
 */
public class DummyModel {

    String textData;

    public DummyModel() {
    }

    public DummyModel(String textData) {
        this.textData = textData;
    }

    public String getTextData() {
        return textData;
    }

    public void setTextData(String textData) {
        this.textData = textData;
    }

    @Override
    public String toString() {
        return "DummyModel{" +
                "textData='" + textData + '\'' +
                '}';
    }
}
