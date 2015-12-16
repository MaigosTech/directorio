package directorio.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

public class MenuItemSimple  {
    @Property @Parameter(required = true, allowNull = false, defaultPrefix = BindingConstants.LITERAL)
    private String page;
    
    @Property @Parameter(required = true, allowNull = false, defaultPrefix = BindingConstants.LITERAL)
    private String context;
    
    @Property @Parameter(required = false, allowNull = true, defaultPrefix = BindingConstants.LITERAL)
    private boolean isPage = true;
    
    @Property @Parameter(required = false, allowNull = true, defaultPrefix = BindingConstants.LITERAL)
    private String title = null;
        
    @Inject
    private Messages messages;

    @Inject
    private ComponentResources resources;
        
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
}