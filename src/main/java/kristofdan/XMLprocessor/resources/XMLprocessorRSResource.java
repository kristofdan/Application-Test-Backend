package kristofdan.XMLprocessor.resources;

import java.io.File;
import java.util.ArrayList;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import kristofdan.XMLprocessor.XMLprocessorRSConfiguration;
import kristofdan.XMLprocessor.beans.Handler;
import kristofdan.XMLprocessor.beans.OrderType;
import kristofdan.XMLprocessor.beans.Person;

@Path("/XMLprocessor")
public class XMLprocessorRSResource {
    
    private File file;
    SAXParserFactory factory;

    public XMLprocessorRSResource(XMLprocessorRSConfiguration config) {
         String filePath = config.getFilePath();
         file = new File(filePath);
         factory = SAXParserFactory.newInstance();
    }
    /**
     * This endpoint can take two optional query parameters: the type of ordering
     * and the required prefix.
     * 
     * It returns HTTP status code 406, if the ordering parameter is not suitable,
     * and 407, if an error occured while parsing or accessing the file
     */
    
    @Path("/data")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response process(
            @DefaultValue("byName") @QueryParam("ordering") String ordering,
            @DefaultValue("") @QueryParam("prefix") String prefix)
    {
        try {
            SAXParser saxParser = factory.newSAXParser();
            Handler handler;
            if (ordering.equals("byName")) {
                handler = new Handler(OrderType.BYNAME, prefix);
            } else if (ordering.equals("byOccurrence")) {
                handler = new Handler(OrderType.BYNUMBEROFOCCURRENCES, prefix);
            } else {
                return Response.
                        status(406).
                        build();
            }
            saxParser.parse(file, handler);   
            
            ArrayList<Person> result = handler.getResult();
            return Response.
                    ok(result).
                    build();
            
        } catch (Exception e) {
            e.printStackTrace();
            return Response.
                    status(407).
                    build();
        }
    }
}