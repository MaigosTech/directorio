package directorio.mixins;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.RenderSupport;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.IncludeJavaScriptLibrary;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;

/* This annotation tells Tapestry to declare the js file in the page so that the browser will pull it in. */
@IncludeJavaScriptLibrary("zone_updater_1_0.js")
public class ZoneUpdater {
	public static final String PLACEHOLDER = "XXX";

	@Inject
	private ComponentResources resources;

	@Environmental
	private RenderSupport renderSupport;

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String clientEvent;

	@Parameter(defaultPrefix = BindingConstants.LITERAL, required = true)
	private String event;

	@InjectContainer
	private ClientElement element;

	@Parameter
	private Object[] context;

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	// To enable popups to fire events on this document, enter "document" here.
	private String listeningElement;

	@Parameter(defaultPrefix = BindingConstants.LITERAL, required = true)
	private String zone;

	protected Link createLink(Object[] context) {
		if (context == null) {
			context = new Object[] {PLACEHOLDER};
		}
		else{
			context = add(context, PLACEHOLDER); // To be replaced by javascript
		}
		return resources.createEventLink(event, context);
	}
	
	void afterRender() {
		String link = createLink(context).toAbsoluteURI();
		String elementId = element.getClientId();
		if (clientEvent == null) {
			clientEvent = event;
		}
		if (listeningElement == null) {
			listeningElement = "$('" + elementId + "')";
		}
		// Tell Tapestry to add some javascript that instantiates a ZoneUpdater for the element we're mixing into.
		// Tapestry will put it at the end of the page in a section that runs once the DOM has been loaded.
		// The ZoneUpdater class it refers to is NOT THIS class - it is actually the one defined in zone_updater_1_0.js.
		renderSupport.addScript("new ZoneUpdater('%s', %s, '%s', '%s', '%s', '%s')", elementId, listeningElement,
				clientEvent, link, zone, PLACEHOLDER);
	}
	
	private Object[] add(Object[] in, Object item) {
		Object[] out = new Object[in.length + 1];
		System.arraycopy(in, 0, out, 0, in.length);
		out[in.length] = item;
		return out;
	}
}
