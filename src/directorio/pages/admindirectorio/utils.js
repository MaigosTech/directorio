function makeSublist(parent,child,isSubselectOptional,childVal)
{
	jQuery("body").append("<select style='display:none' id='"+parent+child+"'></select>");
	console.log("Parent +  child: " + parent + child);
	jQuery('#'+parent+child).html(jQuery("#"+child+" option"));
	
	var parentValue = jQuery('#'+parent).val();
	jQuery('#'+child).html(jQuery("#"+parent+child+" .sub_"+parentValue).clone());
	
	/* childVal = (typeof childVal == "undefined")? "" : childVal ;
	jQuery("#"+child).val(childVal); */
	
	jQuery('#'+parent).change( 
		function() {
			console.log("Cambiando combo: " + jQuery('#'+parent).val());
			var parentValue = jQuery('#'+parent).val();
			jQuery('#'+child).html(jQuery("#"+parent+child+" .sub_"+parentValue).clone());
			if(isSubselectOptional) jQuery('#'+child).prepend("<option value='-1'> -- Todas las opciones -- </option>");
			jQuery('#'+child).trigger("change");
                        jQuery('#'+child).focus();
		}
	);
}

function initTodasOpciones(combo) {
	jQuery('#' + combo).prepend("<option value='-1'> -- Todas las opciones -- </option>");
}

function toUpperCase(elem) {
	if (elem.value != undefined)
		elem.value = elem.value.toUpperCase();
}