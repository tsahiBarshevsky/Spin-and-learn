package tsahi.and.kostia.spinandlearn;

import android.content.Context;

import java.util.ArrayList;

public class CitiesExercise implements Exercises
{
    private String question, answer;
    ArrayList<String> wrongAnswers;
    int flagID;


    public CitiesExercise(Context context, String question, String answer, int flagID) {
        this.question = question;
        this.answer = answer;
        this.flagID = flagID;
        wrongAnswers = new ArrayList<>();

        String[] citiesBank = {
                context.getResources().getString(R.string.jerusalem),
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
                context.getResources().getString(R.string.damascus),
                context.getResources().getString(R.string.hanoi),
                context.getResources().getString(R.string.ankara),
                context.getResources().getString(R.string.bern),
                context.getResources().getString(R.string.khartoum),
                context.getResources().getString(R.string.bucharest),
                context.getResources().getString(R.string.amsterdam),
                context.getResources().getString(R.string.rabat),
                context.getResources().getString(R.string.seoul),
                context.getResources().getString(R.string.amman),
                context.getResources().getString(R.string.rome),
                context.getResources().getString(R.string.baghdad),
                context.getResources().getString(R.string.tehran),
                context.getResources().getString(R.string.new_delhi),
                context.getResources().getString(R.string.budapest),
                context.getResources().getString(R.string.athens),
                context.getResources().getString(R.string.addis_ababa),
                context.getResources().getString(R.string.nicosia),
                context.getResources().getString(R.string.zagreb),
                context.getResources().getString(R.string.ottawa),
                context.getResources().getString(R.string.sofia),
                context.getResources().getString(R.string.brasilia),
                context.getResources().getString(R.string.vienna),
                context.getResources().getString(R.string.canberra),
                context.getResources().getString(R.string.brussels)};

        for(int i=0;i<3;i++){
            int tmp = (int)(Math.random()*40);
            while(answer.equals(citiesBank[tmp]) || wrongAnswers.contains(citiesBank[tmp])){
                tmp = (int)(Math.random()*40);
            }
            wrongAnswers.add(citiesBank[tmp]);
        }
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
        return flagID;
    }
}
