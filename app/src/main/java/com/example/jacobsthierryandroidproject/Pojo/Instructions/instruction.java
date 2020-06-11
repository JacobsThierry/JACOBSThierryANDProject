package com.example.jacobsthierryandroidproject.Pojo.Instructions;

import java.io.Serializable;
import java.util.List;

public class instruction implements Serializable {

    private List<step> steps;

    public List<com.example.jacobsthierryandroidproject.Pojo.Instructions.step> getSteps() {
        return steps;
    }

    public void setStep(List<com.example.jacobsthierryandroidproject.Pojo.Instructions.step> step) {
        this.steps = step;
    }
}
