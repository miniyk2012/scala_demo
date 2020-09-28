package programming_scala.scalaForTheJavaEyes;

public class Car {
    private final int year;
    private int miles;

    public Car(int yearOfMake) {
        year = yearOfMake;
    }

    public int getYear() {
        return year;
    }

    public int getMiles() {
        return miles;
    }

    public void driver(int distance) {
        this.miles += Math.abs(distance);
    }
}
