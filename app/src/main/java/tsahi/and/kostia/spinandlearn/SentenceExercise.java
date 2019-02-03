package tsahi.and.kostia.spinandlearn;

import java.util.ArrayList;
import java.util.Arrays;

public class SentenceExercise  implements Exercises {

    private String question, answer;
    ArrayList<String> wrongAnswers;

    public SentenceExercise(String question){
        this.question = question;
        ArrayList<String> word = new ArrayList<>(Arrays.asList(question.split(" ")));
        int numOfWord = word.size();
        int rndWord = (int)(Math.random()*numOfWord);
        answer = word.get(rndWord);
        answer = answer.substring(0, 1).toUpperCase() + answer.substring(1);

        int answerSize = answer.length();
        char lastChar = answer.charAt(answerSize-1);
        if(lastChar == '.' || lastChar == ',' || lastChar == '!' || lastChar == '?'){
            answerSize--;
            answer = answer.substring(0, answerSize);
        }

        StringBuilder blankWord = new StringBuilder(word.get(rndWord));
        for(int i=0;i<answerSize;i++){
            blankWord.setCharAt(i, '_');
        }

        word.set(rndWord, blankWord.toString());

        this.question = word.get(0);
        for(int i = 1; i<numOfWord;i++){
            this.question += " ";
            this.question += word.get(i);
        }
        wrongAnswers = new ArrayList<>();
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
        return 0;
    }

    public void setWrongAnswers(ArrayList<String> wrongAnswersBank) {
        int bankSize = wrongAnswersBank.size();
        for(int i=0;i<3;i++){
            int tmp = (int)(Math.random()*bankSize);
            while(answer.equals(wrongAnswersBank.get(tmp)) || wrongAnswers.contains(answer.equals(wrongAnswersBank.get(tmp)))){
                tmp = (int)(Math.random()*bankSize);
            }
            wrongAnswers.add(wrongAnswersBank.get(tmp));
        }
    }
}
