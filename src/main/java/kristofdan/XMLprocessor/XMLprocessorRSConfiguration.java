package kristofdan.XMLprocessor;

import lombok.*;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;


@Getter
@Setter
public class XMLprocessorRSConfiguration extends Configuration {
    
    @NotEmpty
    protected String filePath;
    
}
