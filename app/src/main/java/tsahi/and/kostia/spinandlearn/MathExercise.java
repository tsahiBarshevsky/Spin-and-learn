package tsahi.and.kostia.spinandlearn;

import java.util.ArrayList;

import static java.lang.Math.floor;
import static java.lang.Math.pow;

public class MathExercise implements Exercises
{
    private String question, answer;

    public MathExercise(){
        Double a, b, symbol, result = -1.0;
        boolean flag = true;
        String strSymb = "";
        symbol = floor(Math.random() * 4);
        while(flag) {
            a = floor(Math.random() * 100 + 2);
            b = floor(Math.random() * 100 + 2);

            if(symbol == 0.0) {
                result = a + b;
                strSymb = "+";
            }
            else if(symbol == 1.0) {
                result = a - b;
                strSymb = "-";
            }
            else if(symbol == 2.0) {
                result = a * b;
                strSymb = "x";
            }
            else if(symbol == 3.0) {
                result = a / b;
                strSymb = "/";
            }

            if(result == floor(result) && result > 1 && result <= 1000){
                question = new Integer(a.intValue()).toString() + strSymb + new Integer(b.intValue()).toString();
                answer = new Integer(result.intValue()).toString();
                flag = false;
            }
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

    @Override
    public ArrayList<String> getWrongAnswers() {
        return null;
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
}
