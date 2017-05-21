package edu.ucr.cutiehack.parkbnb;

/**
 * Created by karen on 5/20/17.
 */

public class ParkingSpot {

    public long id;
    public String images;
    public double lat;
    public double lng;
    public double price;
    public double rate;
    public boolean taken;
    public String user;

    public ParkingSpot() {
        // Default constructor for DataSnapshot.getValue(ParkingSpot.class)
    }

    public ParkingSpot(long id, String images, double lat, double lng, double price, double rate, boolean taken, String user) {
        this.id = id;
        this.images = images;
        this.lat = lat;
        this.lng = lng;
        this.price = price;
        this.rate = rate;
        this.taken = taken;
        this.user = user;
    }
}