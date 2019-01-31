package tsahi.and.kostia.spinandlearn;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

public class MathExercise
{
    private String question, answer;
    ArrayList<String> wrongAnswers;

    public MathExercise(String question, String answer) {
        this.question = question;
        this.answer = answer;
        Integer range = Integer.parseInt(answer);
        wrongAnswers = new ArrayList<>();
        for (int i =1;i<3;i++){
            Integer tmp = (int)(Math.random()*(range+20) + (range-20));
            while (wrongAnswers.contains(tmp.toString())){
                tmp = (int)(Math.random()*(range+20) + (range-20));
            }
            wrongAnswers.add(tmp.toString());
        }
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public ArrayList<String> getWrongAnswers() {
        return wrongAnswers;
    }

    public void setWrongAnswers(ArrayList<String> wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    @Override
    public String toString() {
        return "MathExercise{" +
                "question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
