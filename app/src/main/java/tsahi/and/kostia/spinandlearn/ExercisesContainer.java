package tsahi.and.kostia.spinandlearn;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;

public class ExercisesContainer
{
    ArrayList<CitiesExercise> citiesExercises;
    ArrayList<WordExercise> wordExercises;
    ArrayList<SentenceExercise> sentenceExercises;

    public ExercisesContainer(Context context) {
        citiesExercises = new ArrayList<>();
        wordExercises = new ArrayList<>();
        sentenceExercises = new ArrayList<>();

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
        wordExercises.add(new WordExercise(context,context.getString(R.string.panic_definition), context.getString(R.string.panic)));
        wordExercises.add(new WordExercise(context,context.getString(R.string.spoiler_definition), context.getString(R.string.spoiler)));
        wordExercises.add(new WordExercise(context,context.getString(R.string.popular_definition), context.getString(R.string.popular)));
        wordExercises.add(new WordExercise(context,context.getString(R.string.modernization_definition), context.getString(R.string.modernization)));
        wordExercises.add(new WordExercise(context,context.getString(R.string.confetti_definition), context.getString(R.string.confetti)));
        wordExercises.add(new WordExercise(context,context.getString(R.string.newsletter_definition), context.getString(R.string.newsletter)));
        wordExercises.add(new WordExercise(context,context.getString(R.string.handyman_definition), context.getString(R.string.handyman)));
        wordExercises.add(new WordExercise(context,context.getString(R.string.muffins_definition), context.getString(R.string.muffins)));
        wordExercises.add(new WordExercise(context,context.getString(R.string.strudel_definition), context.getString(R.string.strudel)));
        wordExercises.add(new WordExercise(context,context.getString(R.string.meditation_definition), context.getString(R.string.meditation)));
        wordExercises.add(new WordExercise(context,context.getString(R.string.collection_definition), context.getString(R.string.collection)));
        wordExercises.add(new WordExercise(context,context.getString(R.string.tweezers_definition), context.getString(R.string.tweezers)));
        wordExercises.add(new WordExercise(context,context.getString(R.string.scissors_definition), context.getString(R.string.scissors)));
        wordExercises.add(new WordExercise(context,context.getString(R.string.sticklight_definition), context.getString(R.string.sticklight)));
        wordExercises.add(new WordExercise(context,context.getString(R.string.kaleidoscope_definition), context.getString(R.string.kaleidoscope)));
        wordExercises.add(new WordExercise(context,context.getString(R.string.intelligence_definition), context.getString(R.string.intelligence)));
        wordExercises.add(new WordExercise(context,context.getString(R.string.acoustics_definition), context.getString(R.string.acoustics)));
        wordExercises.add(new WordExercise(context,context.getString(R.string.privilege_definition), context.getString(R.string.privilege)));
        wordExercises.add(new WordExercise(context,context.getString(R.string.controversy_definition), context.getString(R.string.controversy)));
        wordExercises.add(new WordExercise(context,context.getString(R.string.optimism_definition), context.getString(R.string.optimism)));
        wordExercises.add(new WordExercise(context,context.getString(R.string.opposition_definition), context.getString(R.string.opposition)));
        wordExercises.add(new WordExercise(context,context.getString(R.string.mechanics_definition), context.getString(R.string.mechanics)));
        wordExercises.add(new WordExercise(context,context.getString(R.string.bureaucracy_definition), context.getString(R.string.bureaucracy)));
        wordExercises.add(new WordExercise(context,context.getString(R.string.gladiator_definition), context.getString(R.string.gladiator)));
        wordExercises.add(new WordExercise(context,context.getString(R.string.genetics_definition), context.getString(R.string.genetics)));
        wordExercises.add(new WordExercise(context,context.getString(R.string.generator_definition), context.getString(R.string.generator)));
        wordExercises.add(new WordExercise(context,context.getString(R.string.dolphin_definition), context.getString(R.string.dolphin)));
        wordExercises.add(new WordExercise(context,context.getString(R.string.hipopotam_definition), context.getString(R.string.hipopotam)));
        wordExercises.add(new WordExercise(context,context.getString(R.string.democracy_definition), context.getString(R.string.democracy)));
        wordExercises.add(new WordExercise(context,context.getString(R.string.technology_definition), context.getString(R.string.technology)));

        sentenceExercises.add(new SentenceExercise(context.getString(R.string.sentence1)));
        sentenceExercises.add(new SentenceExercise(context.getString(R.string.sentence2)));
        sentenceExercises.add(new SentenceExercise(context.getString(R.string.sentence3)));
        sentenceExercises.add(new SentenceExercise(context.getString(R.string.sentence4)));
        sentenceExercises.add(new SentenceExercise(context.getString(R.string.sentence5)));
        sentenceExercises.add(new SentenceExercise(context.getString(R.string.sentence6)));
        sentenceExercises.add(new SentenceExercise(context.getString(R.string.sentence7)));
        sentenceExercises.add(new SentenceExercise(context.getString(R.string.sentence8)));
        sentenceExercises.add(new SentenceExercise(context.getString(R.string.sentence9)));
        sentenceExercises.add(new SentenceExercise(context.getString(R.string.sentence10)));
        sentenceExercises.add(new SentenceExercise(context.getString(R.string.sentence11)));
        sentenceExercises.add(new SentenceExercise(context.getString(R.string.sentence12)));
        sentenceExercises.add(new SentenceExercise(context.getString(R.string.sentence13)));
        sentenceExercises.add(new SentenceExercise(context.getString(R.string.sentence14)));
        sentenceExercises.add(new SentenceExercise(context.getString(R.string.sentence15)));
        sentenceExercises.add(new SentenceExercise(context.getString(R.string.sentence16)));
        sentenceExercises.add(new SentenceExercise(context.getString(R.string.sentence17)));
        sentenceExercises.add(new SentenceExercise(context.getString(R.string.sentence18)));
        sentenceExercises.add(new SentenceExercise(context.getString(R.string.sentence19)));
        sentenceExercises.add(new SentenceExercise(context.getString(R.string.sentence20)));
        sentenceExercises.add(new SentenceExercise(context.getString(R.string.sentence21)));
        sentenceExercises.add(new SentenceExercise(context.getString(R.string.sentence22)));
        sentenceExercises.add(new SentenceExercise(context.getString(R.string.sentence23)));
        sentenceExercises.add(new SentenceExercise(context.getString(R.string.sentence24)));
        sentenceExercises.add(new SentenceExercise(context.getString(R.string.sentence25)));
        sentenceExercises.add(new SentenceExercise(context.getString(R.string.sentence26)));
        sentenceExercises.add(new SentenceExercise(context.getString(R.string.sentence27)));
        sentenceExercises.add(new SentenceExercise(context.getString(R.string.sentence28)));
        sentenceExercises.add(new SentenceExercise(context.getString(R.string.sentence29)));
        sentenceExercises.add(new SentenceExercise(context.getString(R.string.sentence30)));
        sentenceExercises.add(new SentenceExercise(context.getString(R.string.sentence31)));
        sentenceExercises.add(new SentenceExercise(context.getString(R.string.sentence32)));
        sentenceExercises.add(new SentenceExercise(context.getString(R.string.sentence33)));
        sentenceExercises.add(new SentenceExercise(context.getString(R.string.sentence34)));
        sentenceExercises.add(new SentenceExercise(context.getString(R.string.sentence35)));
        sentenceExercises.add(new SentenceExercise(context.getString(R.string.sentence36)));
        sentenceExercises.add(new SentenceExercise(context.getString(R.string.sentence37)));
        sentenceExercises.add(new SentenceExercise(context.getString(R.string.sentence38)));
        sentenceExercises.add(new SentenceExercise(context.getString(R.string.sentence39)));
        sentenceExercises.add(new SentenceExercise(context.getString(R.string.sentence40)));


        ArrayList<String> wrongAnswerBank = new ArrayList<>();
        for(int i=0;i<40;i++){
            wrongAnswerBank.add(sentenceExercises.get(i).getAnswer());
        }
        for(int i=0;i<40;i++){
            sentenceExercises.get(i).setWrongAnswers(wrongAnswerBank);
        }

        Collections.shuffle(citiesExercises);
        Collections.shuffle(sentenceExercises);
        Collections.shuffle(wordExercises);
    }

    public ArrayList<CitiesExercise> getCitiesExercises() {
        return citiesExercises;
    }

    public ArrayList<WordExercise> getWordExercises() {
        return wordExercises;
    }

    public ArrayList<SentenceExercise> getSentenceExercises() {
        return sentenceExercises;
    }
}
