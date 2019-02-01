package tsahi.and.kostia.spinandlearn;

import android.content.Context;

import java.util.ArrayList;

public class SentenceExercise  implements Exercises {

    private String question, answer;
    ArrayList<String> wrongAnswers;


    public SentenceExercise(Context context, String question, String answer) {
        this.question = question;
        this.answer = answer;
        wrongAnswers = new ArrayList<>();


        String[] wordBank = {context.getResources().getString(R.string.tooteth),
                context.getResources().getString(R.string.smoke),
                context.getResources().getString(R.string.book),
                context.getResources().getString(R.string.thousand),
                context.getResources().getString(R.string.hand),
                context.getResources().getString(R.string.practice),
                context.getResources().getString(R.string.loves),
                context.getResources().getString(R.string.sight),
                context.getResources().getString(R.string.grasp)};

        for(int i=0;i<3;i++){
            int tmp = (int)(Math.random()*9);
            while(answer.equals(wordBank[tmp]) || wrongAnswers.contains(wordBank[tmp])){
                tmp = (int)(Math.random()*9);
            }
            wrongAnswers.add(wordBank[tmp]);
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
        return "CitiesExercise{" +
                "question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
