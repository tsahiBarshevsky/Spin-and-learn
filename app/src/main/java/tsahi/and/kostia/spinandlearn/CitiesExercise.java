package tsahi.and.kostia.spinandlearn;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class CitiesExercise extends AppCompatActivity
{
    private String question, answer;
    ArrayList<String> wrongAnswers;

    public CitiesExercise(String question, String answer) {
        this.question = question;
        this.answer = answer;
        wrongAnswers = new ArrayList<>();

        String citiesBank[] = {getResources().getString(R.string.jerusalem),
                getResources().getString(R.string.madrid),
                getResources().getString(R.string.london),
                getResources().getString(R.string.washington),
                getResources().getString(R.string.paris),
                getResources().getString(R.string.moscow),
                getResources().getString(R.string.kiev),
                getResources().getString(R.string.tokyo),
                getResources().getString(R.string.capetown),
                getResources().getString(R.string.berlin),
                getResources().getString(R.string.buenos_aires),
                getResources().getString(R.string.lima),
                getResources().getString(R.string.cairo),
                getResources().getString(R.string.riahd),
                getResources().getString(R.string.beiruth),
                getResources().getString(R.string.damascus)};

        for(int i=0;i<3;i++){
            int tmp = (int)(Math.random()*15);
            while(answer.equals(citiesBank[tmp]) || wrongAnswers.contains(citiesBank[tmp])){
                tmp = (int)(Math.random()*15);
            }
            wrongAnswers.add(citiesBank[tmp]);
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
