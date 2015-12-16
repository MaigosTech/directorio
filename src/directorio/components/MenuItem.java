package directorio.components;

import org.apache.log4j.Logger;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import directorio.models.Rol;
import directorio.models.UserSession;

public class MenuItem  {
	public static Logger log = Logger.getLogger(MenuItem.class.getName());
	
    @Property @Parameter(required = true, allowNull = false, defaultPrefix = BindingConstants.LITERAL)
    private String page;
    
    @Property @Parameter(required = false, allowNull = true, defaultPrefix = BindingConstants.LITERAL)
    private boolean isPage = true;
    
    @Property @Parameter(required = false, allowNull = true, defaultPrefix = BindingConstants.LITERAL)
    private String title = null;
        
    @Inject
    private Messages messages;

    @Inject
    private ComponentResources resources;
    
	@SessionState
	private UserSession visit;
	
    public void beginRender() {
	}
        
    public String getPageTitle() {
    	if (title != null) 
    		return title;
    	
        String key = page + "-title";           
        return messages.get(key);
    }
    
    public String getClassName() {
        if (resources.getPageName().equalsIgnoreCase(page)) {
                return "selected";
        }
        return null;
    }
	
	public boolean isShow() {
		try {
    		Rol rol = visit.getUsuario().getRol();    		
    		if (rol != null && rol.startsWith(page.toLowerCase()))
    			return true;
    		else if(page.toLowerCase().startsWith("consulta"))
    			return true;
    		return false;
		} catch (Exception e) {
    		return false;
		}
	}
}