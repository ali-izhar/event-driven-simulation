package foxes_rabbits_v5;

import java.awt.Color;
import java.util.List;
import java.util.Random;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes and Michael Kölling and Izhar Ali
 * @version 2021.10.29
 */
public abstract class Animal
{
    private boolean alive;
    private Field field;
    private Location location;
    private int age;
    
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
        
    /**
     * Create a new animal at location in field.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(Field field, Location location) {
        alive = true;
        this.field = field;
        setLocation(location);
        this.age = 0;
    }
    
    
    
    
    
    //--------------------------------(GETTERS)----------------------------------------------//
   
    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    protected Field getField() {
        return field;
    }
    
    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation() {
        return location;
    }
    
    /**
     * Return the animal's age.
     * @return The animal's age.
     */
    protected int getAge() {
    	return age;
    }
    
    /**
     * Return the maximum age of this animal.
     * @return The maximum age of this animal.
     */
    abstract int getMaxAge();
    
    /**
     * Return the breeding age of this animal.
     * @return The breeding age of this animal.
     */
    abstract int getBreedingAge();
    
    /**
     * Get the animal's breeding probability.
     * @return The breeding probability.
     */
    abstract double getBreedingProb();
    
    /**
     * Return the animal's creation probability.
     * @return The animal's creation probability. 
     */
    abstract double getAnimalCreationProb();
    
    /**
     * Get the animal's maximum litter size.
     * @return The maximum litter size.
     */
    abstract int getMaxLitterSize();
    
    /**
     * Create an animal.
     * @param b True if to create an animal; false otherwise.
     * @param field The animal's grid field.
     * @param loc The animal's grid location.
     * @return Return the newborn animal.
     */
    abstract Animal createAnimal(boolean b, Field field, Location loc);
    
    /**
     * Return the color of the animal.
     * @return The animal's color.
     */
    abstract Color getColor();
  
    //---------------------------------------------------------------------------------------//
    
    
    
    
    
    //--------------------------------(SETTERS)----------------------------------------------//
    
    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    protected void setDead() {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }
    
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation) {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    /**
     * Set the animal's age.
     * @param age The value to set the animal's age.
     */
    protected void setAge(int age) {
    	this.age = age;
    }
    
    //---------------------------------------------------------------------------------------//
    
    
    
    

    //--------------------------------(INSTANCE METHODS)-------------------------------------//
    
    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     */
    abstract public void act(List<Animal> newAnimals);

    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    protected boolean isAlive() {
        return alive;
    }  
    
    /**
     * The animal can breed at a certain age.
     * @return The births after breeding.
     */
    protected int breed() {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= getBreedingProb()) {
            births = rand.nextInt(getMaxLitterSize()) + 1;
        }
        return births;
    }
    
    /**
     * An animal can breed if it has reached the breeding age.
     * @return True if the animal can in fact breed.
     */
    protected boolean canBreed() {
    	return age >= getBreedingAge(); 
    }
        
    /**
     * Increment the age of this animal.
     */
    protected void incrementAge() {
    	age++;
    	if (age > getMaxAge()) {
    		setDead();
    	}
    } 
    
    /**
     * Check whether or not this animal is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newborn A list to add newly born animals to.
     */
    protected void giveBirth(List<Animal> newborn)
    {
        // Newborns are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Animal young = createAnimal(false, field, loc);
            newborn.add(young);
        }
    }
    
    //---------------------------------------------------------------------------------------//
    

    
}