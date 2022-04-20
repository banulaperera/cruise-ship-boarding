public class Passenger {

    private String firstName;
    private String surName;
    private double expenses;
    private int cabinNumber;

    public Passenger(String firstName, String surName, double expenses) {
        this.firstName = firstName;
        this.surName = surName;
        this.expenses = expenses;
    }

    public int getCabinNumber() {
        return cabinNumber;
    }

    public void setCabinNumber(int cabinNumber) {
        this.cabinNumber = cabinNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public double getExpenses() {
        return expenses;
    }

    public void setExpenses(double expenses) {
        this.expenses = expenses;
    }
}
