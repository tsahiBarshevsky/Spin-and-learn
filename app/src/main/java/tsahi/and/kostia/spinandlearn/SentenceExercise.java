package tsahi.and.kostia.spinandlearn;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class SentenceExercise extends AppCompatActivity {
    private String question, answer;
    ArrayList<String> wrongAnswers;

    public SentenceExercise(String question, String answer) {
        this.question = question;
        this.answer = answer;

        String wordBank[] = {getResources().getString(R.string.tooteth),
                getResources().getString(R.string.smoke),
                getResources().getString(R.string.book),
                getResources().getString(R.string.thousand),
                getResources().getString(R.string.hand),
                getResources().getString(R.string.practice),
                getResources().getString(R.string.loves),
                getResources().getString(R.string.sight),
                getResources().getString(R.string.grasp)};

        for(int i=0;i<3;i++){
            int tmp = (int)(Math.random()*8);
            while(answer.equals(wordBank[tmp]) || wrongAnswers.contains(wordBank[tmp])){
                tmp = (int)(Math.random()*8);
            }
            wrongAnswers.add(wordBank[tmp]);
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
        return "CitiesExercise{" +
                "question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
