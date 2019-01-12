package tsahi.and.kostia.spinandlearn;

import java.util.Arrays;

public class CitiesExercise
{
    private String question, answer, wrongAnswers[];

    public CitiesExercise(String question, String answer, String[] wrongAnswers) {
        this.question = question;
        this.answer = answer;
        this.wrongAnswers = wrongAnswers;
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

    public String[] getWrongAnswers() {
        return wrongAnswers;
    }

    public void setWrongAnswers(String[] wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    @Override
    public String toString() {
        return "CitiesExercise{" +
                "question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", wrongAnswers=" + Arrays.toString(wrongAnswers) +
                '}';
    }
}
