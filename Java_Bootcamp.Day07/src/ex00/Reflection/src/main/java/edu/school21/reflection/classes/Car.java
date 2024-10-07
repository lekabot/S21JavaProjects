package edu.school21.reflection.classes;

import java.util.StringJoiner;

public class Car {
    private String brand;
    private String model;
    private int power;
    private double fuelQnt;

    public Car() {
        this.brand = "Default brand";
        this.model = "Default model";
        this.power = 0;
        this.fuelQnt = 0;
    }

    public Car(String brand, String model, int power, double fuelQnt) {
        this.brand = brand;
        this.model = model;
        this.power = power;
        this.fuelQnt = fuelQnt;
    }

    public double fillTank(double gasFilled) {
        this.fuelQnt += gasFilled;
        return this.fuelQnt;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Car.class.getSimpleName() + "[", "]")
                .add("Brand='" + brand + "'")
                .add("Model='" + model + "'")
                .add("Power=" + power)
                .add("Fuel quantity=" + fuelQnt)
                .toString();
    }
}
