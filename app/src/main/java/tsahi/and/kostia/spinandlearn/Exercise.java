package tsahi.and.kostia.spinandlearn;

public class Exercise
{
    private String question, answer, type, wrongAnswers[];

    public Exercise(String question, String answer, String type, String[] wrongAnswers) {
        this.question = question;
        this.answer = answer;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String[] getWrongAnswers() {
        return wrongAnswers;
    }

    public void setWrongAnswers(String[] wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
