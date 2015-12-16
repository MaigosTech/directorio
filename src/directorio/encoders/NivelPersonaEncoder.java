package directorio.encoders;

import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.services.ValueEncoderFactory;

import directorio.models.NivelPersona;

public class NivelPersonaEncoder implements ValueEncoder<NivelPersona>, ValueEncoderFactory<NivelPersona> { 
	     
    public String toClient(NivelPersona value) {
        // return the given object's ID
        return String.valueOf(value.getId()); 
    }
 
    public NivelPersona toValue(String id) { 
        // find the color object of the given ID in the database
        return NivelPersona.loadFromId(Integer.parseInt(id)); 
    }
 
    // let this ValueEncoder also serve as a ValueEncoderFactory
    public ValueEncoder<NivelPersona> create(Class<NivelPersona> type) {
        return this; 
    }
}
