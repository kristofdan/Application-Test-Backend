package kristofdan.XMLprocessor.beans;

import lombok.*;
import java.util.*;
import java.util.regex.Pattern;
import org.xml.sax.Attributes;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author kristof
 */

public class Handler extends DefaultHandler{
    
    private Boolean insideAppropriateDatafield;
    private Boolean insideAppropriateSubfield;
    @Getter
    private Boolean finished;
    
    private ArrayList<Person> peopleFound;
    private final OrderType orderType;
    private String namePrefix;

    public Handler() {
        this(OrderType.BYNAME, "");
    }    
    
    public Handler(OrderType orderType) {
        this(orderType, "");
    }

    public Handler(String namePrefix) {
        this(OrderType.BYNAME, namePrefix);
    }
    
    public Handler(OrderType orderType, String namePrefix) {
        this.orderType = orderType;
        this.namePrefix = namePrefix;
        peopleFound = new ArrayList<>();
        
        insideAppropriateDatafield = false;
        insideAppropriateSubfield = false;
        finished = false;
    }
    
    @Override
    public void error(SAXParseException spe) {
        System.out.println("A non-fatal SAX error occured");
    }
    
    @Override
    public void startElement(
        String uri, String localName, String qName, Attributes attributes) {
        
        switch (qName) {
            case "datafield":
                if (attributes.getValue("tag").equals("100")) {
                    insideAppropriateDatafield = true;
                }
                break;
            case "subfield":
                if (attributes.getValue("code").equals("a")) {
                    insideAppropriateSubfield= true;
                }
                break;
        }
   }

    @Override
    public void endElement(String uri, String localName, String qName) {
           
        if (qName.equals("datafield")) {
            insideAppropriateDatafield = false;
        } else if (qName.equals("subfield")) {
            insideAppropriateSubfield = false;
        }        
    }

    @Override
    public void characters(char ch[], int start, int length) {
        String text = new String(ch, start, length);
        
        if (insideAppropriateDatafield && insideAppropriateSubfield) {
            Boolean matchesPrefix = matchesPrefix(text);
            if (matchesPrefix) {
                addOrIncrementPerson(text);
            }
        }
    }
    
    private Boolean matchesPrefix(String name) {
        String regexp = "^" + namePrefix + ".*";
        return Pattern.matches(regexp, name);
    }
    
    private void addOrIncrementPerson(String text) {
        Person personFound = new Person(text);
        int index = peopleFound.indexOf(personFound);
        if (index == -1) {
            peopleFound.add(personFound);
        } else {
            peopleFound.get(index).incrementOccurrences();
        }        
    }
    
    @Override
    public void endDocument() {
        Comparator comparator;
        if (orderType == OrderType.BYNAME){
            comparator = new NameBasedComparator();
        } else {
            comparator = new OccurrenceBasedComparator();
        }
        
        Collections.sort(peopleFound, comparator);
        finished = true;
    }
    
    /**
     * If the end of the file is reached, returns the result, otherwise null.
     */
    public ArrayList<Person> getResult() {
        if (finished) {
            return peopleFound;
        } else {
            return null;
        }
    }
}
