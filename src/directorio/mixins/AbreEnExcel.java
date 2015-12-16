package directorio.mixins;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.MixinAfter;
import org.apache.tapestry5.dom.Attribute;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.dom.Node;

import directorio.pages.OcraBasePage;

@MixinAfter
public class AbreEnExcel {
	public static Logger log = Logger.getLogger(AbreEnExcel.class.getName());
    private Element element;
    
	public void afterRender() {
    	log.debug("elemento:" + atributos(element));
		element.raw("<div align=\"left\">hola</div>");
	}
	
    void beginRender(MarkupWriter writer) {
        element = writer.getElement();
    }
    
	private String atributos(Element element) {
    	//log.debug("elemento:" + atributos(element));
		List<Node> lista = element.getChildren();
		StringBuffer buf = new StringBuffer();
		buf.append("this:" + element.getName() + "\n");
		buf.append("hijos:");
		for (Node n : lista) {
			buf.append(n.getClass().getName() + "-" + n.toString() + ",");
		}
		/*
		buf.append("\natributos:");
		Collection<Attribute> listaAttr = element.getAttributes();
		for (Attribute a : listaAttr) 
			buf.append(a.getName() + "-" + a.getValue() + ", " );
			*/
		return buf.toString();
	}

}
