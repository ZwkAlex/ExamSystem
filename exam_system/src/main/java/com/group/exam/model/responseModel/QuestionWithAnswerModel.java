package com.group.exam.model.responseModel;

import com.group.exam.model.daoModel.QuestionDao;
import com.group.exam.model.entity.Question;

import java.util.List;

public class QuestionWithAnswerModel extends Question {
    private List<String> answers;

    public QuestionWithAnswerModel(QuestionDao questionDao){
        super(questionDao);
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }
}
