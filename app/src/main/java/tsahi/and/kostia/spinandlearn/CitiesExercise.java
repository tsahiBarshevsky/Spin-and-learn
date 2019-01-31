package tsahi.and.kostia.spinandlearn;

import android.content.Context;

import java.util.ArrayList;

public class CitiesExercise
{
    private String question, answer;
    ArrayList<String> wrongAnswers;


    public CitiesExercise(Context context, String question, String answer) {
        this.question = question;
        this.answer = answer;
        wrongAnswers = new ArrayList<>();

        String[] citiesBank = {context.getResources().getString(R.string.jerusalem),
                context.getResources().getString(R.string.madrid),
                context.getResources().getString(R.string.london),
                context.getResources().getString(R.string.washington),
                context.getResources().getString(R.string.paris),
                context.getResources().getString(R.string.moscow),
                context.getResources().getString(R.string.kiev),
                context.getResources().getString(R.string.tokyo),
                context.getResources().getString(R.string.capetown),
                context.getResources().getString(R.string.berlin),
                context.getResources().getString(R.string.buenos_aires),
                context.getResources().getString(R.string.lima),
                context.getResources().getString(R.string.cairo),
                context.getResources().getString(R.string.riahd),
                context.getResources().getString(R.string.beiruth),
                context.getResources().getString(R.string.damascus)};

        for(int i=0;i<3;i++){
            int tmp = (int)(Math.random()*16);
            while(answer.equals(citiesBank[tmp]) || wrongAnswers.contains(citiesBank[tmp])){
                tmp = (int)(Math.random()*16);
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
