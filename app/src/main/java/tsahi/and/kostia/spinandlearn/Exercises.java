package tsahi.and.kostia.spinandlearn;

import java.util.ArrayList;

public class Exercises
{
    ArrayList<MathExercise> mathExercises;
    ArrayList<CitiesExercise> citiesExercises;
    ArrayList<WordExercise> wordExercises;

    public Exercises() {
        mathExercises = new ArrayList<>();
        citiesExercises = new ArrayList<>();
        wordExercises = new ArrayList<>();
    }

    public Exercises(ArrayList<MathExercise> mathExercises, ArrayList<CitiesExercise> citiesExercises, ArrayList<WordExercise> wordExercises) {
        this.mathExercises = mathExercises;
        this.citiesExercises = citiesExercises;
        this.wordExercises = wordExercises;
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

    @Override
    public String toString() {
        return "Exercises{" +
                "mathExercises=" + mathExercises +
                ", citiesExercises=" + citiesExercises +
                ", wordExercises=" + wordExercises +
                '}';
    }
}