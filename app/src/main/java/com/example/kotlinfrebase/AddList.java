package com.example.kotlinfrebase;

public class AddList {
    String name,condition;

    public AddList() {
    }

    public AddList(String name, String condition) {
        this.name = name;
        this.condition = condition;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
