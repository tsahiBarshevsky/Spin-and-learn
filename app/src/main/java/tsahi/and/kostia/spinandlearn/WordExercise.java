package tsahi.and.kostia.spinandlearn;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class WordExercise extends AppCompatActivity {
    private String definition, answer;
    ArrayList<Character> letterBank;
    private int numOf_missing_letters, answer_size;
    private StringBuilder question;

    public WordExercise(String definition, String answer) {
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
            letterBank.add((char)(Math.random()*26 + getResources().getString(R.string.rnd_letter_index).charAt(0)));
        }
    }



    @Override
    public String toString() {
        return "WordExercise{" +
                "definition='" + definition + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
