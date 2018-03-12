package kristofdan.XMLprocessor;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import kristofdan.XMLprocessor.resources.XMLprocessorRSResource;

public class XMLprocessorRSApplication extends Application<XMLprocessorRSConfiguration> {

    public static void main(final String[] args) throws Exception {
        new XMLprocessorRSApplication().run(args);
    }

    XMLprocessorRSApplication() {
        
    }
    
    @Override
    public String getName() {
        return "XMLprocessorRS";
    }

    @Override
    public void initialize(final Bootstrap<XMLprocessorRSConfiguration> bootstrap) {
        //Initialization not needed
    }

    @Override
    public void run(final XMLprocessorRSConfiguration configuration,
                    final Environment environment) {
        environment.jersey().register(CORSResponseSupplementer.class);
        environment.jersey().register(new XMLprocessorRSResource(configuration));
    }
}
