package foxes_rabbits_v5;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing rabbits and foxes.
 * 
 * @author David J. Barnes and Michael Kölling and Izhar Ali
 * @version 2021.11.02
 */
public class Simulator
{
    // Constants representing configuration information for the simulation.
	
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;

    // List of animals in the field.
    private List<Animal> animals;
    
    // The current state of the field.
    private Field field;
    
    // The current step of the simulation.
    private int step;
    
    // A graphical view of the simulation.
    private SimulatorView view;
    
    // Animals passed in from the driver class.
    private ArrayList<Animal> driverAnimals;
    
    /**
     * Construct a simulation field with default size; pass into it the animals from the driver.
     */
    public Simulator(ArrayList<Animal> driverAnimals) {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH, driverAnimals);
    }
    
    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width, ArrayList<Animal> driverAnimals) {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        
        this.driverAnimals = driverAnimals;
        animals = new ArrayList<Animal>();
        field = new Field(depth, width);
        view = new SimulatorView(depth, width);
        reset();
    }
    
    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 steps).
     */
    public void runLongSimulation() {
        simulate(4000);
    }
    
    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps) {
        for(int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
        }
    }
    
    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * fox and rabbit.
     */
    public void simulateOneStep() {
        step++;

        //provide space for newborn animal.
        List<Animal> newAnimals = new ArrayList<Animal>();
        //Let all animals act.
        for(Iterator<Animal> it = animals.iterator(); it.hasNext();) {
            Animal animal = it.next();
            animal.act(newAnimals);
            if(!animal.isAlive()) {
                it.remove();
            }
        }
        animals.addAll(newAnimals);
        view.showStatus(step, field);
    }
    
    /**
     * Randomly populate the field with animals.
     */
    private void populate() {
    	Random rand = Randomizer.getRandom();
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
            	for(int i = 0; i < driverAnimals.size(); i++) {
            		if (rand.nextDouble() <= driverAnimals.get(i).getAnimalCreationProb()) {
            			Location location = new Location(row, col);
            			animals.add(driverAnimals.get(i).createAnimal(true, field, location));
            		}
            	}
            }            	
        }
        animals.forEach(animal -> view.setColor(animal.getClass(), animal.getColor()));
    }
        
    /**
     * Reset the simulation to a starting position.
     */
    public void reset() {
        step = 0;
        animals.clear();
        populate();
        
        // Show the starting state in the view.
        view.showStatus(step, field);
    }
}