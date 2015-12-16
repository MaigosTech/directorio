package directorio.components;
import org.apache.log4j.Logger;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.RenderSupport;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.BeforeRenderBody;
import org.apache.tapestry5.annotations.IncludeJavaScriptLibrary;
import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;

// Menujs.com
//@IncludeStylesheet({"Menu.css"})
//@IncludeJavaScriptLibrary({"Menu.js"})

public class MenuHolder {
	public static Logger log = Logger.getLogger(MenuHolder.class.getName());
    private static String MENU_CONTAINER = "menu-container-";
    @Inject
    private ComponentResources resources;
    @Inject
    private RenderSupport renderSupport;
        
    @Parameter(name = "id", defaultPrefix = BindingConstants.LITERAL, required = true)
    private String id;
    
    public String getMenuID() {
        return MENU_CONTAINER + id;
    }
	
    public void beginRender() {
    	log.debug("beginRender de MenuHolder");
	}
    
        /*@AfterRender
        public void AfterRender(MarkupWriter writer) {
            String divName = getMenuID();
            renderSupport.addScript(String.format("Menu.init('%s', " +
            		"{'orientation': Menu.HORIZONTAL, 'hidePause': 0.1});", 
    		divName));
        } */
        
        @BeforeRenderBody
        public Object RenderMenuItems(MarkupWriter writer) {
            // here search and append to the existing root menu the menu items defined in the child pages   
            ComponentResources parentResources = resources.getContainerResources();
            Block toRender = null;
            while (parentResources != null) {
                Block temp = parentResources.findBlock(id);
                if (temp != null) {
                    toRender = temp;
                }
                parentResources = parentResources.getContainerResources();
            }
            return toRender;
        }
}