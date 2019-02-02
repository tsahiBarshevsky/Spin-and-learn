package tsahi.and.kostia.spinandlearn;

import android.content.Context;

import java.util.ArrayList;

public class WordExercise  implements Exercises {

    private String definition, answer;
    ArrayList<Character> letterBank;
    private int numOf_missing_letters, answer_size;
    private String question;
    private StringBuilder questionSB;

    public WordExercise(Context context, String definition, String answer) {
        this.definition = definition;
        this.answer = answer;

        letterBank = new ArrayList<>();
        answer_size = answer.length();
        numOf_missing_letters = answer_size/2;
        questionSB = new StringBuilder(answer);
        for(int i=0;i<numOf_missing_letters;i++){
            int tmp = (int)(Math.random()*(answer_size-1));
            while (questionSB.charAt(tmp) == '_'){
                tmp = (int)(Math.random()*(answer_size-1));
            }
            questionSB.setCharAt(tmp, '_');
            letterBank.add(answer.charAt(tmp));
        }

        question = questionSB.toString();

        for(int i=0;i<14-numOf_missing_letters;i++){
            letterBank.add((char)(Math.random()*26 + context.getResources().getString(R.string.rnd_letter_index).charAt(0)));
        }
    }

    @Override
    public String getDefinition() {
        return definition;
    }

    @Override
    public int getFlagID() {
        return 0;
    }

    @Override
    public String getAnswer() {
        return answer;
    }

    @Override
    public ArrayList<String> getWrongAnswers() {
        return null;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public ArrayList<Character> getLetterBank() {
        return letterBank;
    }

    @Override
    public String getQuestion() {
        return question;
    }
}
