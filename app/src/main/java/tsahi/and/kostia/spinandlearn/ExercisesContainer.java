package tsahi.and.kostia.spinandlearn;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;

public class ExercisesContainer
{
    ArrayList<MathExercise> mathExercises;
    ArrayList<CitiesExercise> citiesExercises;
    ArrayList<WordExercise> wordExercises;
    ArrayList<SentenceExercise> sentenceExercises;

    public ExercisesContainer(Context context) {
        mathExercises = new ArrayList<>();
        citiesExercises = new ArrayList<>();
        wordExercises = new ArrayList<>();
        sentenceExercises = new ArrayList<>();

        mathExercises.add(new MathExercise(context.getString(R.string.math1), "49"));
        mathExercises.add(new MathExercise(context.getString(R.string.math2), "15"));
        mathExercises.add(new MathExercise(context.getString(R.string.math3), "60"));
        mathExercises.add(new MathExercise(context.getString(R.string.math4), "8"));
        mathExercises.add(new MathExercise(context.getString(R.string.math5), "69"));
        mathExercises.add(new MathExercise(context.getString(R.string.math6), "4"));
        mathExercises.add(new MathExercise(context.getString(R.string.math7), "121"));
        mathExercises.add(new MathExercise(context.getString(R.string.math8), "96"));
        mathExercises.add(new MathExercise(context.getString(R.string.math9), "32"));
        mathExercises.add(new MathExercise(context.getString(R.string.math10), "125"));

        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.israel) + "?", context.getString(R.string.jerusalem), R.drawable.israel_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.spain) + "?", context.getString(R.string.madrid), R.drawable.spain_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.england) + "?", context.getString(R.string.london), R.drawable.uk_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.usa) + "?", context.getString(R.string.washington), R.drawable.usa_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.france) + "?", context.getString(R.string.paris), R.drawable.france_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.russsia) + "?", context.getString(R.string.moscow), R.drawable.russia_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.ukraine) + "?", context.getString(R.string.kiev), R.drawable.ukraine_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.japan) + "?", context.getString(R.string.tokyo), R.drawable.japan_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.south_africa) + "?", context.getString(R.string.capetown), R.drawable.south_africa_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.germany) + "?", context.getString(R.string.berlin), R.drawable.germany_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.argentina) + "?", context.getString(R.string.buenos_aires), R.drawable.argentina_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.peru) + "?", context.getString(R.string.lima), R.drawable.peru_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.egypt) + "?", context.getString(R.string.cairo), R.drawable.egypt_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.saudi_arabia) + "?", context.getString(R.string.riahd), R.drawable.saudi_arabia_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.lebanon) + "?", context.getString(R.string.beiruth), R.drawable.lebanon_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.siriya) + "?", context.getString(R.string.damascus), R.drawable.syria_flag));

        wordExercises.add(new WordExercise(context,context.getString(R.string.encylopedia_definition), context.getString(R.string.encyclopedia)));
        wordExercises.add(new WordExercise(context,context.getString(R.string.bacteria_definition), context.getString(R.string.bacteria)));
        wordExercises.add(new WordExercise(context,context.getString(R.string.allergy_definition), context.getString(R.string.allergy)));
        wordExercises.add(new WordExercise(context,context.getString(R.string.ballerina_definition), context.getString(R.string.ballerina)));
        wordExercises.add(new WordExercise(context,context.getString(R.string.boomernag_definition), context.getString(R.string.boomernag)));
        wordExercises.add(new WordExercise(context,context.getString(R.string.camouflage_definition), context.getString(R.string.camouflage)));
        wordExercises.add(new WordExercise(context,context.getString(R.string.magazine_definition), context.getString(R.string.magazine)));
        wordExercises.add(new WordExercise(context,context.getString(R.string.archive_definition), context.getString(R.string.archive)));
        wordExercises.add(new WordExercise(context,context.getString(R.string.feather_definition), context.getString(R.string.feather)));
        wordExercises.add(new WordExercise(context,context.getString(R.string.success_definition), context.getString(R.string.success)));

        sentenceExercises.add(new SentenceExercise(context,context.getString(R.string.tooteth_missing), context.getString(R.string.tooteth)));
        sentenceExercises.add(new SentenceExercise(context,context.getString(R.string.smoke_missing), context.getString(R.string.smoke)));
        sentenceExercises.add(new SentenceExercise(context,context.getString(R.string.book_missing), context.getString(R.string.book)));
        sentenceExercises.add(new SentenceExercise(context,context.getString(R.string.thousand_missing), context.getString(R.string.thousand)));
        sentenceExercises.add(new SentenceExercise(context,context.getString(R.string.hand_missing), context.getString(R.string.hand)));
        sentenceExercises.add(new SentenceExercise(context,context.getString(R.string.practice_missing), context.getString(R.string.practice)));
        sentenceExercises.add(new SentenceExercise(context,context.getString(R.string.loves_missing), context.getString(R.string.loves)));
        sentenceExercises.add(new SentenceExercise(context,context.getString(R.string.sight_missing), context.getString(R.string.sight)));
        sentenceExercises.add(new SentenceExercise(context,context.getString(R.string.grasp_missing), context.getString(R.string.grasp)));

        Collections.shuffle(citiesExercises);
        Collections.shuffle(mathExercises);
        Collections.shuffle(sentenceExercises);
        Collections.shuffle(wordExercises);
    }

    public ArrayList<MathExercise> getMathExercises() {
        return mathExercises;
    }

    public void setMathExercises(ArrayList<MathExercise> mathExercises) {
        this.mathExercises = mathExercises;
    }

    public ArrayList<CitiesExercise> getCitiesExercises() {
        return citiesExercises;
    }

    public void setCitiesExercises(ArrayList<CitiesExercise> citiesExercises) {
        this.citiesExercises = citiesExercises;
    }

    public ArrayList<WordExercise> getWordExercises() {
        return wordExercises;
    }

    public void setWordExercises(ArrayList<WordExercise> wordExercises) {
        this.wordExercises = wordExercises;
    }

    public ArrayList<SentenceExercise> getSentenceExercises() {
        return sentenceExercises;
    }

    public void setSentenceExercises(ArrayList<SentenceExercise> sentenceExercises) {
        this.sentenceExercises = sentenceExercises;
    }

    @Override
    public String toString() {
        return "ExercisesContainer{" +
                "mathExercises=" + mathExercises +
                ", citiesExercises=" + citiesExercises +
                ", wordExercises=" + wordExercises +
                ", sentenceExercises=" + sentenceExercises +
                '}';
    }
}
