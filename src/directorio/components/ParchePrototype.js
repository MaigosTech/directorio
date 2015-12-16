Element.Methods = {
		  writeAttribute: function(element, name, value) {
		    element = $(element);
		    var attributes = { }, t = Element._attributeTranslations.write;

		    if (typeof name == 'object') attributes = name;
		    else attributes[name] = Object.isUndefined(value) ? true : value;

		    for (var attr in attributes) {
		      name = t.names[attr] || attr;
		      value = attributes[attr];
		      if (t.values[attr]) name = t.values[attr](element, value);
		      if (value === false || value === null)
		        element.removeAttribute(name);
		      else if (value === true)
		        element.setAttribute(name, name);
		      else element.setAttribute(name, value);
		    }
		    var ieClass = (name == 'className');
		    if(ieClass && value && element.className == '') {
		        element.removeAttribute('className');
		        if(value === true) {
		            element.setAttribute('class', 'class'); }
		        else {
		            element.setAttribute('class', value); }
		    } else if(ieClass && (value === false || value === null) && element.className != '') {
		    	alert('intentando remover');
		        element.removeAttribute('class'); 
	        } 
		    return element;
		  }
};