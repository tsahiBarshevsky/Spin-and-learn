package tsahi.and.kostia.spinandlearn;

import java.util.ArrayList;

public class MathExercise implements Exercises
{
    private String question, answer;


    public MathExercise(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public ArrayList<String> getWrongAnswers() {
        return null;
    }

    @Override
    public ArrayList<Character> getLetterBank() {
        return null;
    }

    @Override
    public String getDefinition() {
        return null;
    }

    @Override
    public int getFlagID() {
        return 0;
    }
}
