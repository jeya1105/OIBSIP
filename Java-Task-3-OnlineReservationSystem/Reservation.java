
public class Reservation {

    private String pnr;
    private String passengerName;
    private String trainNumber;
    private String trainName;
    private String classType;
    private String journeyDate;
    private String source;
    private String destination;
    private boolean cancelled;

    public Reservation(String pnr, String passengerName,
                       String trainNumber, String trainName,
                       String classType, String journeyDate,
                       String source, String destination) {

        this.pnr = pnr;
        this.passengerName = passengerName;
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.classType = classType;
        this.journeyDate = journeyDate;
        this.source = source;
        this.destination = destination;
        this.cancelled = false;
    }

    public String getPnr() {
        return pnr;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public String getTrainName() {
        return trainName;
    }

    public String getClassType() {
        return classType;
    }

    public String getJourneyDate() {
        return journeyDate;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void cancelReservation() {
        cancelled = true;
    }

    public void displayDetails() {
        System.out.println("\n----- Reservation Details -----");
        System.out.println("PNR: " + pnr);
        System.out.println("Passenger: " + passengerName);
        System.out.println("Train Number: " + trainNumber);
        System.out.println("Train Name: " + trainName);
        System.out.println("Class: " + classType);
        System.out.println("Journey Date: " + journeyDate);
        System.out.println("Source: " + source);
        System.out.println("Destination: " + destination);
        System.out.println("Status: "
                + (cancelled ? "Cancelled" : "Confirmed"));
    }
}
