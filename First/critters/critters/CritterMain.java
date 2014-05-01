package critters;


// CSE 142 Homework 8 (Critters)
// Authors: Stuart Reges and Marty Stepp
//
// CritterMain provides the main method for a simple simulation program.  Alter
// the number of each critter added to the simulation if you want to experiment
// with different scenarios.  You can also alter the width and height passed to
// the CritterFrame constructor.

public class CritterMain {
    public static void main(String[] args) {
        final int numberOfCritters = 20;
        CritterFrame frame = new CritterFrame(60, 40);

        // uncomment each of these lines as you complete these classes
       // frame.add(numberOfCritters, Bear.class);
        frame.add(numberOfCritters, Lion.class);
        frame.add(numberOfCritters, Giant.class);
        frame.add(numberOfCritters, Crab.class);
        frame.add(numberOfCritters, TRex.class);
        frame.add(numberOfCritters, FlyTrap.class);
        frame.add(numberOfCritters, Food.class);
        
//        frame.add(numberOfCritters, Husky_Hogeland.class);
       // frame.add(numberOfCritters, Husky_Pollock_Rosebush3.class);
        /*
         * N.B. public static final boolean ENABLE_DASHBOARD = true;
         * This should be set to false in order to disable dashboard.
         * Variable in statics package.
         */
        //frame.add(numberOfCritters, DaboHusky.class);

        frame.start();
    }
}
