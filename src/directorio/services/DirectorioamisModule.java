package directorio.services;

import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.ComponentClassTransformWorker;
import org.apache.tapestry5.services.ValueEncoderFactory;

import directorio.encoders.NivelPersonaEncoder;
import directorio.models.NivelPersona;

public class DirectorioamisModule {
	
	/**
     * Adds a number of standard component class transform workers:
     * <ul>
     * <li>InjectSelectionModel - generates the SelectionModel and ValueEncoder for a any marked list of objects.</li>
     * </ul>
     */
    public static void contributeComponentClassTransformWorker(OrderedConfiguration<ComponentClassTransformWorker> configuration, 
    															PropertyAccess propertyAccess)
    {
        configuration.add("InjectSelectionModel", new InjectSelectionModelWorker(propertyAccess), "after:Inject*");
    }
    public static void contributeValueEncoderSource(MappedConfiguration<Class,
            ValueEncoderFactory> configuration)
	{
	configuration.addInstance(NivelPersona.class, NivelPersonaEncoder.class);
	//configuration.addInstance(SomeOtherType.class, SomeOtherTypeEncoder.class);
	}
}
