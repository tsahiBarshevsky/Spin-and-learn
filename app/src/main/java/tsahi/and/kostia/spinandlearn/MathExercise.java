package tsahi.and.kostia.spinandlearn;

import java.util.ArrayList;

public class MathExercise implements Exercises
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

    @Override
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
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
        return wrongAnswers;
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
