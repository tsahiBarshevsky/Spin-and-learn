package tsahi.and.kostia.spinandlearn;

import java.util.ArrayList;

public interface Exercises {
    String getQuestion();
    String getAnswer();
    ArrayList<String> getWrongAnswers();
    ArrayList<Character> getLetterBank();
    String getDefinition();
    int getFlagID();
}
