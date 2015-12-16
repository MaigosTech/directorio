package directorio.components;

import org.apache.tapestry5.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.BindingConstants;

import directorio.models.UserSession;
import directorio.models.Usuario;

/**
 * Layout component for pages of application prueba.
 */

public class Layout
{
	@SessionState(create = false)
	private UserSession session;
	
    /** The page title, for the <title> element and the <h1> element. */
    @Property
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String title;

    @Property
    private String pageName;

    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String sidebarTitle;

    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private Block sidebar;

    @Inject
    private ComponentResources resources;

    public String getClassForPageName()
    {
      return resources.getPageName().equalsIgnoreCase(pageName)
             ? "current_page_item"
             : null;
    }

    public String[] getPageNames()
    {
      //return new String[] { "Index", "About", "Contact" };
      return new String[] { "Index"};
    }
    
    public boolean getImprimiendo() {
    	return false;
    }
    
    public Usuario getUsuario() {
    	if (session == null)
    		return null;
    	return session.getUsuario();
    }
}
