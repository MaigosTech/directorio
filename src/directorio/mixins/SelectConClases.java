package directorio.mixins;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.MixinAfter;
import org.apache.tapestry5.dom.Attribute;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.dom.Node;

import directorio.models.Entidad;
import directorio.models.Global;
import directorio.pages.OcraBasePage;

@MixinAfter
public class SelectConClases {
	public static Logger log = Logger.getLogger(SelectConClases.class.getName());
    private Element element;
    private static int invocacion = 0;

    void beginRender(MarkupWriter writer) {
        element = writer.getElement();
        
    }

    void afterRender() {
    	//log.debug("elemento:" + atributos(element));    	
		List<Node> lista = element.getChildren();
		for (Node n: lista) {
			// Asumo que son de tipo Element
			Element el = (Element)n;
			log.debug("el.getChildren(): " + el.getChildren());
			try {
    			String texto = el.getChildren().get(0).toString(); // Selecciono el texto del elemento option
    			
    			// Pongo el atributo 'class' para que referencie al padre.
    			el.attribute("class", "sub_" + texto.substring(0, texto.indexOf('-')));
    			
    			// Quito el texto y le pongo uno nuevo, sin el -
    			el.removeChildren(); 
    			el.text(texto.substring(texto.indexOf('-')+1));
			} catch (Exception e) {
				// nada
			}
		}
    }

	private String atributos(Element element) {
		List<Node> lista = element.getChildren();
		StringBuffer buf = new StringBuffer();
		buf.append("this:" + element.getName() + "\n");
		buf.append("hijos:");
		for (Node n : lista) {
			buf.append(n.getClass().getName() + "-" + n.toString() + ",");
		}
		buf.append("\natributos:");
		Collection<Attribute> listaAttr = element.getAttributes();
		for (Attribute a : listaAttr) 
			buf.append(a.getName() + "-" + a.getValue() + ", " );
		return buf.toString();
	}
}
