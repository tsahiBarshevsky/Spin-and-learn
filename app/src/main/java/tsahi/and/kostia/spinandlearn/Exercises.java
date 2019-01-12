package tsahi.and.kostia.spinandlearn;

import java.util.ArrayList;

public class Exercises
{
    ArrayList<MathExercise> mathExercises;
    ArrayList<CitiesExercise> citiesExercises;

    public Exercises() {
        mathExercises = new ArrayList<>();
        citiesExercises = new ArrayList<>();
    }

    public Exercises(ArrayList<MathExercise> mathExercises, ArrayList<CitiesExercise> citiesExercises) {
        this.mathExercises = mathExercises;
        this.citiesExercises = citiesExercises;
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

    @Override
    public String toString() {
        return "Exercises{" +
                "mathExercises=" + mathExercises +
                ", citiesExercises=" + citiesExercises +
                '}';
    }
}
