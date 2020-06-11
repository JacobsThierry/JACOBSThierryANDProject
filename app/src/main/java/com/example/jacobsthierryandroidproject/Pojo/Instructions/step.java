package com.example.jacobsthierryandroidproject.Pojo.Instructions;

import java.io.Serializable;

public class step implements Serializable {
    private int number;
    private String step;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    @Override
    public String toString() {
        return "step{" +
                "number=" + number +
                ", step='" + step + '\'' +
                '}';
    }
}
