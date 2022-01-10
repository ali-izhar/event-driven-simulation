package foxes_rabbits_v5;

import java.awt.Color;
import java.util.List;
import java.util.Random;

/**
 * A simple model of a rabbit.
 * Rabbits age, move, breed, and die.
 * 
 * @author David J. Barnes and Michael Kölling and Izhar Ali
 * @version 2021.11.01
 */
public class Rabbit extends Animal
{
	// Characteristics shared by all rabbits (class variables).
	
	// The age at which a rabbit can start to breed.
    private static final int BREEDING_AGE = 5;
    
    // The age to which a rabbit can live.
    private static final int MAX_AGE = 40;
    
    // The likelihood of a rabbit breeding.
    private static final double BREEDING_PROBABILITY = 0.12;
    
    // The likelihood of the creation of a rabbit.
    private static final double RABBIT_CREATION_PROBABILITY = 0.08; 
    
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;
    
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    // The rabbit's generic color.
    private static final Color rabbitColor = Color.orange;
    
    
    /**
     * Create a new rabbit. A rabbit may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the rabbit will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Rabbit(boolean randomAge, Field field, Location location) {
        super(field, location);
        setAge(0);
        if(randomAge) {
            setAge(rand.nextInt(MAX_AGE));
        }
    }
    
    /**
     * This is what the rabbit does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newRabbits A list to return newly born rabbits.
     */
    @Override
    public void act(List<Animal> newRabbits) {
        incrementAge();
        if(isAlive()) {
            giveBirth(newRabbits);            
            // Try to move into a free location.
            Location newLocation = getField().freeAdjacentLocation(getLocation());
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                setDead();
            }
        }
    }
    
    
    //--------------------------------(CONCRETE METHODS)-------------------------------------//
    
    /**
     * Return the maximum age of rabbit.
     * @return The maximum age of rabbit.
     */
    @Override
    protected int getMaxAge() {
    	return MAX_AGE;
    }
    
    /**
     * Return the breeding age of rabbit.
     * @return The breeding age of rabbit.
     */
    @Override
    protected int getBreedingAge() {
    	return BREEDING_AGE;
    }
    
    /**
     * Return the breeding probability of rabbit.
     * @return The breeding probability of rabbit.
     */
    @Override
    protected double getBreedingProb() {
    	return BREEDING_PROBABILITY;
    }
    
    /**
     * Return the creation probability of rabbit.
     * @return The creation probability of rabbit.
     */
    @Override
    protected double getAnimalCreationProb() {
    	return RABBIT_CREATION_PROBABILITY;
    }
    
    /**
     * Return the maximum litter size of rabbit.
     * @return The maximum litter size of rabbit.
     */
    @Override
    protected int getMaxLitterSize() {
    	return MAX_LITTER_SIZE;
    }
    
    /**
     * Create a rabbit.
     * @param b True if to create a rabbit; false otherwise.
     * @param field The rabbit's grid field.
     * @param loc The rabbit's grid location.
     * @return Return the newborn rabbit.
     */
    @Override
    protected Animal createAnimal(boolean b, Field field, Location location) {
    	return new Rabbit(b, field, location);
    }
    
    /**
     * Return the color of the rabbit.
     * @return The color of the rabbit.
     */
    @Override
    protected Color getColor() {
    	return rabbitColor;
    } 

}