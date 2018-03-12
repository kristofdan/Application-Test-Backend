package kristofdan.XMLprocessor.beans;

import java.util.Comparator;

/**
 * @author kristof
 */

public class NameBasedComparator implements Comparator<Person>{
    
    @Override
    public int compare(Person person1, Person person2) {
        return person1.getName().compareTo(person2.getName());
    }
    
}
