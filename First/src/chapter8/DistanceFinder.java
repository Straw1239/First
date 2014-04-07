package chapter8;
// This class provides a sample usage of the GeoLocator.find method and the
// GeoLocation class.  It prompts the user for two locations and reports the
// distance between them.

import java.util.*;

public class DistanceFinder {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        System.out.println("This program finds the distance between two");
        System.out.println("places using Google Maps data.");
        System.out.println();
        System.out.print("first location? ");
        GeoLocation one = GeoLocator.find(console.nextLine());
        if (one == null) {
            System.out.println("no matches for that search string.");
        } else {
            System.out.println("found at " + one);
            System.out.print("second location? ");
            GeoLocation two = GeoLocator.find(console.nextLine());
            if (two == null) {
                System.out.println("no matches for that search string.");
            } else {
                System.out.println("found at " + two);
                System.out.printf("%.3f miles apart\n", one.distanceFrom(two));
            }
        }
    }
}