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
        mathExercises.add(new MathExercise(context.getString(R.string.math11), "24"));
        mathExercises.add(new MathExercise(context.getString(R.string.math12), "12"));
        mathExercises.add(new MathExercise(context.getString(R.string.math13), "9"));
        mathExercises.add(new MathExercise(context.getString(R.string.math14), "8"));
        mathExercises.add(new MathExercise(context.getString(R.string.math15), "8"));
        mathExercises.add(new MathExercise(context.getString(R.string.math16), "1024"));
        mathExercises.add(new MathExercise(context.getString(R.string.math17), "256"));
        mathExercises.add(new MathExercise(context.getString(R.string.math18), "256"));
        mathExercises.add(new MathExercise(context.getString(R.string.math19), "64"));
        mathExercises.add(new MathExercise(context.getString(R.string.math20), "144"));
        mathExercises.add(new MathExercise(context.getString(R.string.math21), "49"));
        mathExercises.add(new MathExercise(context.getString(R.string.math22), "51"));
        mathExercises.add(new MathExercise(context.getString(R.string.math23), "21"));
        mathExercises.add(new MathExercise(context.getString(R.string.math24), "64"));
        mathExercises.add(new MathExercise(context.getString(R.string.math25), "133"));
        mathExercises.add(new MathExercise(context.getString(R.string.math26), "376"));
        mathExercises.add(new MathExercise(context.getString(R.string.math27), "1"));
        mathExercises.add(new MathExercise(context.getString(R.string.math28), "634"));
        mathExercises.add(new MathExercise(context.getString(R.string.math29), "48"));
        mathExercises.add(new MathExercise(context.getString(R.string.math30), "30"));
        mathExercises.add(new MathExercise(context.getString(R.string.math31), "55"));
        mathExercises.add(new MathExercise(context.getString(R.string.math32), "99"));
        mathExercises.add(new MathExercise(context.getString(R.string.math33), "25"));
        mathExercises.add(new MathExercise(context.getString(R.string.math34), "39"));
        mathExercises.add(new MathExercise(context.getString(R.string.math35), "100"));
        mathExercises.add(new MathExercise(context.getString(R.string.math36), "36"));
        mathExercises.add(new MathExercise(context.getString(R.string.math37), "140"));
        mathExercises.add(new MathExercise(context.getString(R.string.math38), "56"));
        mathExercises.add(new MathExercise(context.getString(R.string.math39), "23"));
        mathExercises.add(new MathExercise(context.getString(R.string.math40), "6"));

        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.australia) + "?", context.getString(R.string.canberra), R.drawable.australia_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.argentina) + "?", context.getString(R.string.buenos_aires), R.drawable.argentina_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.austria) + "?", context.getString(R.string.vienna), R.drawable.austria_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.belgium) + "?", context.getString(R.string.brussels), R.drawable.belgium_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.brazil) + "?", context.getString(R.string.brasilia), R.drawable.brazil_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.bulgaria) + "?", context.getString(R.string.sofia), R.drawable.bulgaria_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.canada) + "?", context.getString(R.string.ottawa), R.drawable.canada_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.croatia) + "?", context.getString(R.string.zagreb), R.drawable.croatia_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.cyprus) + "?", context.getString(R.string.nicosia), R.drawable.cyprus_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.egypt) + "?", context.getString(R.string.cairo), R.drawable.egypt_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.ethiopia) + "?", context.getString(R.string.addis_ababa), R.drawable.ethiopia_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.france) + "?", context.getString(R.string.paris), R.drawable.france_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.germany) + "?", context.getString(R.string.berlin), R.drawable.germany_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.greece) + "?", context.getString(R.string.athens), R.drawable.greece_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.hungary) + "?", context.getString(R.string.budapest), R.drawable.hungary_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.india) + "?", context.getString(R.string.new_delhi), R.drawable.india_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.iran) + "?", context.getString(R.string.tehran), R.drawable.iran_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.iraq) + "?", context.getString(R.string.baghdad), R.drawable.iraq_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.israel) + "?", context.getString(R.string.jerusalem), R.drawable.israel_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.italy) + "?", context.getString(R.string.rome), R.drawable.italy_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.japan) + "?", context.getString(R.string.tokyo), R.drawable.japan_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.jordan) + "?", context.getString(R.string.amman), R.drawable.jordan_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.korea) + "?", context.getString(R.string.seoul), R.drawable.korea_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.lebanon) + "?", context.getString(R.string.beiruth), R.drawable.lebanon_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.morocco) + "?", context.getString(R.string.rabat), R.drawable.morocco_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.netherlands) + "?", context.getString(R.string.amsterdam), R.drawable.netherlands_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.peru) + "?", context.getString(R.string.lima), R.drawable.peru_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.romania) + "?", context.getString(R.string.bucharest), R.drawable.romania_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.russsia) + "?", context.getString(R.string.moscow), R.drawable.russia_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.spain) + "?", context.getString(R.string.madrid), R.drawable.spain_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.sudan) + "?", context.getString(R.string.khartoum), R.drawable.sudan_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.switzerland) + "?", context.getString(R.string.bern), R.drawable.switzerland_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.siriya) + "?", context.getString(R.string.damascus), R.drawable.syria_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.south_africa) + "?", context.getString(R.string.capetown), R.drawable.south_africa_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.saudi_arabia) + "?", context.getString(R.string.riahd), R.drawable.saudi_arabia_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.turkey) + "?", context.getString(R.string.ankara), R.drawable.turkey_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.england) + "?", context.getString(R.string.london), R.drawable.uk_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.ukraine) + "?", context.getString(R.string.kiev), R.drawable.ukraine_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.usa) + "?", context.getString(R.string.washington), R.drawable.usa_flag));
        citiesExercises.add(new CitiesExercise(context,context.getString(R.string.capital_of) + " " + context.getString(R.string.vietnam) + "?", context.getString(R.string.hanoi), R.drawable.vietnam_flag));



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
