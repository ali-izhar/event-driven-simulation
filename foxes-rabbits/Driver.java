package foxes_rabbits_v5;

import java.util.ArrayList;
import java.util.Scanner;

public class Driver {

	public static void main(String[] args) {
		
		// Create a list of animals that we can pass into the simulation to start with.
		ArrayList<Animal> newAnimals = new ArrayList<Animal>();
		Simulator mySim = new Simulator(createAnimals(newAnimals));

		char choice;
		Scanner sc = new Scanner(System.in);
		do {
			choice = getOption(sc);
			takeAction(choice, mySim);
		} while (choice != 'X');
	}
	
	
	private static char getOption(Scanner sc) {
		System.out.println("Enter a menu option");
		System.out.println(" R. Reset the simulation");
		System.out.println(" 1. Simulate one step");
		System.out.println(" 2. Simulate two steps");
		System.out.println(" 3. Simulate three steps");
		System.out.println(" 4. Simulate forty steps");
		System.out.println(" 5. Simulate fifty steps");
		System.out.println(" 0. Simulate 100 steps");
		System.out.println(" L. Run long simulation (4000 steps)");
		System.out.println(" X. Exit simulator");
		return sc.next().toUpperCase().charAt(0);
	}

	private static void takeAction(char choice, Simulator mySim) {
		switch (choice) {
		case ('R'): 
			mySim.reset();
			break;
		case('3'):
			mySim.simulateOneStep();
		case('2'):
			mySim.simulateOneStep();
		case('1'):
			mySim.simulateOneStep();
			break;
		case('5'):
			mySim.simulate(10);
		case('4'):
			mySim.simulate(40);
			break;
		case('0'):
			mySim.simulate(100);
			break;
		case('L'):
			mySim.runLongSimulation();
			break;
		case('X'):
			System.out.println("Goodbye.");
			break;
		}
	}
	
	/**
	 * Create a list of newborn animals that we can pass into the simulator.
	 * @param animalsThatExist The ArrayList of animals.
	 * @return The ArrayList of animals.
	 */
	private static ArrayList<Animal> createAnimals(ArrayList<Animal> newAnimals) {
		Field field = new Field(2, 2);
		Location location = new Location(1, 1);
		
		// Create an instance of every animal.
		Fox fox = new Fox(false, field, location);
		Rabbit rabbit = new Rabbit(false, field, location);
		Wolf wolf = new Wolf(false, field, location);
		
		// Add those animals to the ArrayList.
		newAnimals.add(fox);
		newAnimals.add(rabbit);
		newAnimals.add(wolf);
		
		return newAnimals;
	}
}