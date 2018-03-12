package kristofdan.XMLprocessor.beans;

import java.util.Comparator;

/**
 * @author kristof
 */

public class OccurrenceBasedComparator implements Comparator<Person>{
    
    @Override
    public int compare(Person person1, Person person2) {
        
        int Occurrences1 = person1.getNumberOfOccurrences();
        int Occurrences2 = person2.getNumberOfOccurrences();
        
        if (Occurrences1 == Occurrences2) {
            return 0;
        } else if (Occurrences1 > Occurrences2) {
            return 1;
        } else {
            return -1;
        }
    }
    
}

