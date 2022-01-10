package foxes_rabbits_v5;

import java.util.List;
import java.awt.Color;
import java.util.Iterator;
import java.util.Random;

/**
 * A simple model of a fox.
 * Foxes age, move, eat rabbits, and die.
 * 
 * @author David J. Barnes and Michael Kölling and Izhar Ali
 * @version 2021.11.01
 */

public class Wolf extends Animal
{
	// Characteristics shared by all foxes (class variables).
	
	// The age at which a wolf can start to breed.
    private static final int BREEDING_AGE = 20;
    
    // The age to which a wolf can live.
    private static final int MAX_AGE = 200;
    
    // The likelihood of a wolf breeding.
    private static final double BREEDING_PROBABILITY = 0.05;
    
    // The likelihood of the creation of a wolf.
    private static final double WOLF_CREATION_PROBABILITY = 0.04;
    
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 1;
    
    // The food value of a single rabbit. In effect, this is the number of steps a fox can go before it has to eat again.
    private static final int RABBIT_FOOD_VALUE = 8;
    private static final int FOX_FOOD_VALUE = 3;
    
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    // The wolf's food level, which is increased by eating rabbits & foxes.
    private int foodLevel;
    
    // The fox's generic color.
    private static final Color wolfColor = Color.black;

    
    
    /**
     * Create a fox. A fox can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the fox will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Wolf(boolean randomAge, Field field, Location location) {
        super(field, location);
        if(randomAge) {
            setAge(rand.nextInt(MAX_AGE));
            foodLevel = rand.nextInt(RABBIT_FOOD_VALUE + FOX_FOOD_VALUE);
        }
        else {
            setAge(0);
            foodLevel = (RABBIT_FOOD_VALUE + FOX_FOOD_VALUE);
        }
    }
    
    /**
     * This is what the fox does most of the time: it hunts for
     * rabbits. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param field The field currently occupied.
     * @param newFoxes A list to return newly born foxes.
     */
    @Override
    public void act(List<Animal> newFoxes) {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newFoxes);            
            // Move towards a source of food if found.
            Location newLocation = findFood();
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                setDead();
            }
        }
    }
    
    /**
     * Make this fox more hungry. This could result in the fox's death.
     */
    private void incrementHunger() {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }
    
    /**
     * Look for rabbits adjacent to the current location.
     * Only the first live rabbit is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood() {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if(rabbit.isAlive()) { 
                    rabbit.setDead();
                    foodLevel = RABBIT_FOOD_VALUE;
                    // Remove the dead rabbit from the field.
                    return where;
                }  
            }
            if(animal instanceof Fox) {
                Fox fox = (Fox) animal;
                if(fox.isAlive()) { 
                    fox.setDead();
                    foodLevel = FOX_FOOD_VALUE;
                    // Remove the dead rabbit from the field.
                    return where;
                }  
            }
        }
        return null;
    }
    
    
    //--------------------------------(CONCRETE METHODS)-------------------------------------//
    
    /**
     * Return the maximum age of wolf.
     * @return The maximum age of wolf.
     */
    @Override
    protected int getMaxAge() {
    	return MAX_AGE;
    }
    
    /**
     * Return the breeding age of wolf.
     * @return The breeding age of wolf.
     */
    @Override
    protected int getBreedingAge() {
    	return BREEDING_AGE;
    }
    
    /**
     * Return the breeding probability of wolf.
     * @return The breeding probability of wolf.
     */
    @Override
    protected double getBreedingProb() {
    	return BREEDING_PROBABILITY;
    }
    
    /**
     * Return the creation probability of wolf.
     * @return The creation probability of wolf.
     */
    @Override
    protected double getAnimalCreationProb() {
    	return WOLF_CREATION_PROBABILITY;
    }
    
    /**
     * Return the maximum litter size of wolf.
     * @return The maximum litter size of wolf.
     */
    @Override
    protected int getMaxLitterSize() {
    	return MAX_LITTER_SIZE;
    }
    
    /**
     * Create a wolf.
     * @param b True if to create a wolf; false otherwise.
     * @param field The wolf's grid field.
     * @param loc The wolf's grid location.
     * @return Return the newborn wolf.
     */
    @Override
    protected Animal createAnimal(boolean b, Field field, Location location) {
    	return new Wolf(b, field, location);
    }
    
    /**
     * Return the color of the wolf.
     * @return The color of the wolf.
     */
    @Override
    protected Color getColor() {
    	return wolfColor;
    }
   
}