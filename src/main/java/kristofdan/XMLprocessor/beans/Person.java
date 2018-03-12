package kristofdan.XMLprocessor.beans;

import lombok.*;

/**
 * Stores, how many times a given person's name was found (in the right context).
 * equals() and hashcode() have been overridden to use only the name property.
 * 
 * @author kristof
 */

@Getter
@EqualsAndHashCode(of = "name")
public class Person {
    
    private String name;
    private int numberOfOccurrences;
    
    Person(String name) {
        this.name = name;
        numberOfOccurrences = 1;
    }
    
    public void incrementOccurrences() {
        numberOfOccurrences++;
    }
    
    @Override
    public String toString() {
        return name + ":  " + String.valueOf(numberOfOccurrences) + "\n";
    }
}
