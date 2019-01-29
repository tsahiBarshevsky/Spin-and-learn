package tsahi.and.kostia.spinandlearn;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class WordExercise {
    private Context context;

    private String definition, answer;
    ArrayList<Character> letterBank;
    private int numOf_missing_letters, answer_size;
    private StringBuilder question;

    public WordExercise(Context context, String definition, String answer) {
        this.context=context;
        this.definition = definition;
        this.answer = answer;

        letterBank = new ArrayList<>();
        answer_size = answer.length();
        numOf_missing_letters = answer_size/2;
        question = new StringBuilder(answer);
        for(int i=0;i<numOf_missing_letters;i++){
            int tmp = (int)(Math.random()*(answer_size-1));
            while (question.charAt(tmp) == '_'){
                tmp = (int)(Math.random()*(answer_size-1));
            }
            question.setCharAt(tmp, '_');
            letterBank.add(answer.charAt(tmp));
        }

        for(int i=0;i<20-numOf_missing_letters;i++){
            letterBank.add((char)(Math.random()*26 + context.getResources().getString(R.string.rnd_letter_index).charAt(0)));
        }
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public ArrayList<Character> getLetterBank() {
        return letterBank;
    }

    public void setLetterBank(ArrayList<Character> letterBank) {
        this.letterBank = letterBank;
    }

    public int getNumOf_missing_letters() {
        return numOf_missing_letters;
    }

    public void setNumOf_missing_letters(int numOf_missing_letters) {
        this.numOf_missing_letters = numOf_missing_letters;
    }

    public int getAnswer_size() {
        return answer_size;
    }

    public void setAnswer_size(int answer_size) {
        this.answer_size = answer_size;
    }

    public StringBuilder getQuestion() {
        return question;
    }

    public void setQuestion(StringBuilder question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "WordExercise{" +
                "definition='" + definition + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
