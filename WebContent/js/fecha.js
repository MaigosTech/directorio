/*
 * Autor: Francisco Peña Coronado (pacop28@gmail.com)
 *
 * Archivo js para almacenar las validaciones de las fechas 
 *
 */
 	/* 
	 * Función para verificar que ya exista fecha inicial antes de introducir la final
	 * Recibe parametros de Fecha Inicial y Fecha Final a comparar entre si
	 *
	 */	
	function verFechaFin(fechaIni, fechaFin, iniNombre, finNombre){
		fechaInicio = document.forms[0][fechaIni].value;
		if(fechaInicio == ''){
			alert('- Se debe de introducir primero la ' + iniNombre + '.');
			document.forms[0][fechaFin].value = '';
			document.forms[0][fechaIni].focus();
		}else if(fechaInicio == 'X'){
			unaFechaVsActual(fechaFin, finNombre);
		}else{
			validaFecha(fechaIni, fechaFin, iniNombre, finNombre);
		}
	}
	/* 
	 * Función para verificar que ya exista fecha inicial antes de introducir la final
	 * Recibe parametros de Fecha Inicial y Fecha Final a comparar entre si
	 *
	 */	
	function verFechaFinEsp(fechaIni, fechaFin, iniNombre, finNombre){
		fechaInicio = document.forms[0][fechaIni].value;
		if(fechaInicio == ''){
			alert('- Se debe de introducir primero la ' + iniNombre + '.');
			document.forms[0][fechaFin].value = '';
			document.forms[0][fechaIni].focus();
		}else if(fechaInicio == 'X'){
			unaFechaVsActual(fechaFin, finNombre);
		}else{
			validaFechaEsp(fechaIni, fechaFin, iniNombre, finNombre);
		}
	}
	/*
	 * Función para verificación de fechas
	 */
	function validaFecha(fechaIni, fechaFin, iniNombre, finNombre){
		fechaInicio = document.forms[0][fechaIni].value;
		fechaFinal = document.forms[0][fechaFin].value;
		var alerta = false;
		var alerta2 = false;
		
		if(fechaInicio != ''){
			document.forms[0][fechaIni].value = fechaInicio.toUpperCase();
			document.forms[0][fechaIni].value = simplifica(document.forms[0][fechaIni].value);
			alerta = noMayorFechaAct(document.forms[0][fechaIni].value);
			if(alerta){
				alert('- "' + iniNombre + '" debe ser anterior a la Fecha actual.');
				document.forms[0][fechaIni].value = '';
				document.forms[0][fechaIni].focus();
			}
		}
		if(fechaFinal != '' && !alerta){
			document.forms[0][fechaFin].value = fechaFinal.toUpperCase();
			document.forms[0][fechaFin].value = simplifica(document.forms[0][fechaFin].value);
			alerta = noMayorFechaAct(document.forms[0][fechaFin].value);
			if(alerta){
				alert('- "' + finNombre + '" debe ser anterior a la Fecha actual.');
				document.forms[0][fechaFin].value = '';
				document.forms[0][fechaFin].focus();
			}else{
				alerta2 = diferenciaFechas(document.forms[0][fechaIni].value, document.forms[0][fechaFin].value);
				if(alerta2){
					alert('- "' + finNombre + '" debe ser posterior a "' + iniNombre + '"');
					document.forms[0][fechaFin].value = '';
					document.forms[0][fechaFin].focus();
				}
			}
		}
	}
	/*
	 * Función para verificación de fechas
	 */
	function validaFechaEsp(fechaIni, fechaFin, iniNombre, finNombre){
		fechaInicio = document.forms[0][fechaIni].value;
		fechaFinal = document.forms[0][fechaFin].value;
		var alerta = false;
		
		if(fechaInicio != ''){
			document.forms[0][fechaIni].value = fechaInicio.toUpperCase();
			document.forms[0][fechaIni].value = simplifica(document.forms[0][fechaIni].value);
		}
		if(fechaFinal != '' && !alerta){
			document.forms[0][fechaFin].value = fechaFinal.toUpperCase();
			document.forms[0][fechaFin].value = simplifica(document.forms[0][fechaFin].value);
			alerta = diferenciaFechas(document.forms[0][fechaIni].value, document.forms[0][fechaFin].value);
			if(alerta){
				alert('- "' + finNombre + '" debe ser posterior a "' + iniNombre + '"');
				document.forms[0][fechaFin].value = '';
				document.forms[0][fechaFin].focus();
			}
		}
	}
	/*
	 * Función para verificación de una fecha contra la fecha actual
	 */
	function unaFechaVsActual(fechaEntrada, fechaNombre){
		fecha = document.forms[0][fechaEntrada].value;
		var alerta = false;
		
		if(fecha != ''){
			document.forms[0][fechaEntrada].value = fecha.toUpperCase();
			alerta = noMayorFechaAct(document.forms[0][fechaEntrada].value);
			if(alerta){
				alert('- "' + fechaNombre + '" debe ser anterior a la Fecha actual.');
				document.forms[0][fechaEntrada].value = '';
				document.forms[0][fechaEntrada].focus();
			}
		}
	}
	/*
	 * Función para verificación de una fecha contra la fecha actual pero que sea mayor o igual a la actual
	 */
	function unaFechaDespuesActual(fechaEntrada, fechaNombre){
		fecha = document.forms[0][fechaEntrada].value;
		var alerta = false;
		
		if(fecha != ''){
			document.forms[0][fechaEntrada].value = fecha.toUpperCase();
			alerta = noMenorFechaAct(document.forms[0][fechaEntrada].value);
			if(alerta){
				alert('- "' + fechaNombre + '" debe ser igual o posterior a la Fecha actual.');
				document.forms[0][fechaEntrada].value = '';
				document.forms[0][fechaEntrada].focus();
			}
		}
	}
	/* 
	 * Función para verificar que la fecha inicial no sea mayor a la fecha final
	 *
	 */	
	function diferenciaFechas(inicio, fFinal){
		inicio = inicio.toUpperCase();
		fFinal = fFinal.toUpperCase();
		
		diaI = inicio.substring(0,2);
		e1I = inicio.substring(2,3);
		mesI = inicio.substring(3,6);
		e2I = inicio.substring(6,7);
		anioI = inicio.substring(7);
		
		diaF = fFinal.substring(0,2);
		e1F = fFinal.substring(2,3);
		mesF = fFinal.substring(3,6);
		e2F = fFinal.substring(6,7);
		anioF = fFinal.substring(7);
		
		diaI = diaI * 1;
		anioI = anioI * 1;
		diaF = diaF * 1;
		anioF = anioF * 1;
	
		if(parseInt(anioF) < parseInt(anioI))
			return true;
		if((parseInt(anioF) == parseInt(anioI)) && 
			(parseInt(checaMes(mesF)) < parseInt(checaMes(mesI))))
			return true;
		if((parseInt(anioF) == parseInt(anioI)) &&
			(parseInt(checaMes(mesF)) == parseInt(checaMes(mesI))) && 
			(parseInt(diaF) < parseInt(diaI)))
			return true;
	}
	/* 
	 * Función para verificar que la fecha no sea mayor a la fecha actual
	 *
	 */	
	function noMayorFechaAct(fechaEntrada){
		var val = fechaEntrada;
		var texto = '';
		
		fechaAct = document.forms[0].hFecha.value;
		diaAc = fechaAct.substring(1,3);
		mesAc = fechaAct.substring(4,6);
		anioAc = fechaAct.substring(7,11);
		
		dia = val.substring(0,2);
		e1 = val.substring(2,3);
		mes = val.substring(3,6);
		e2 = val.substring(6,7);
		anio = val.substring(7);
		
		diaAc = diaAc * 1;
		mesAc = mesAc * 1;
		anioAc = anioAc * 1;
		dia = dia * 1;
		anio = anio * 1;
		
		if((isNaN(dia)) || (isNaN(anio)) || (e1 != ' ') || (e2 != ' ') ||
			((mes != 'ENE') && (mes != 'JAN') && (mes != 'FEB') && (mes != 'MAR') &&
			 (mes != 'ABR') && (mes != 'APR') && (mes != 'MAY') && (mes != 'JUN') &&
			 (mes != 'JUL') && (mes != 'AGO') && (mes != 'SEP') && (mes != 'OCT') &&
			 (mes != 'DEC') && (mes != 'DIC') && (mes != 'NOV') && (mes != 'AUG')))
		{
			alert('- Fecha invalida ' + val + '.');
			return false;
		}
		if(parseInt(anioAc) < parseInt(anio))
			return true;
		if((parseInt(anioAc) == parseInt(anio)) &&
			(parseInt(mesAc) < parseInt(checaMes(mes))))
			return true;
		if((parseInt(anioAc) == parseInt(anio)) && 
			(parseInt(mesAc) == parseInt(checaMes(mes))) && 
			(parseInt(diaAc) < parseInt(dia)))
			return true;
	}
	/* 
	 * Función para verificar que la fecha no sea menor a la fecha actual
	 *
	 */	
	function noMenorFechaAct(fechaEntrada){
		var val = fechaEntrada;
		var texto = '';
		
		fechaAct = document.forms[0].hFecha.value;
		diaAc = fechaAct.substring(1,3);
		mesAc = fechaAct.substring(4,6);
		anioAc = fechaAct.substring(7,11);
		
		dia = val.substring(0,2);
		e1 = val.substring(2,3);
		mes = val.substring(3,6);
		e2 = val.substring(6,7);
		anio = val.substring(7);
		
		diaAc = diaAc * 1;
		mesAc = mesAc * 1;
		anioAc = anioAc * 1;
		dia = dia * 1;
		anio = anio * 1;
		
		if((isNaN(dia)) || (isNaN(anio)) || (e1 != ' ') || (e2 != ' ') ||
			((mes != 'ENE') && (mes != 'JAN') && (mes != 'FEB') && (mes != 'MAR') &&
			 (mes != 'ABR') && (mes != 'APR') && (mes != 'MAY') && (mes != 'JUN') &&
			 (mes != 'JUL') && (mes != 'AGO') && (mes != 'SEP') && (mes != 'OCT') &&
			 (mes != 'DEC') && (mes != 'DIC') && (mes != 'NOV') && (mes != 'AUG')))
		{
			alert('- Fecha invalida ' + val + '.');
			return false;
		}
		if(parseInt(anioAc) > parseInt(anio))
			return true;
		if((parseInt(anioAc) == parseInt(anio)) &&
			(parseInt(mesAc) > parseInt(checaMes(mes))))
			return true;
		if((parseInt(anioAc) == parseInt(anio)) && 
			(parseInt(mesAc) == parseInt(checaMes(mes))) && 
			(parseInt(diaAc) > parseInt(dia)))
			return true;
	}
	/* 
	 *	Funcion para cambiar el mes de numerico a String, detectando de que mes se trata
	 */
	function checaMes(mes){
		valor = 0;
		if(mes == 'ENE' || mes == 'JAN')
			valor = 1;
		if(mes == 'FEB')
			valor = 2;
		if(mes == 'MAR')
			valor = 3;
		if(mes == 'ABR' || mes == 'APR')
			valor = 4;
		if(mes == 'MAY')
			valor = 5;
		if(mes == 'JUN')
			valor = 6;
		if(mes == 'JUL')
			valor = 7;
		if(mes == 'AUG' || mes == 'AGO')
			valor = 8;
		if(mes == 'SEP')
			valor = 9;
		if(mes == 'OCT')
			valor = 10;
		if(mes == 'NOV')
			valor = 11;
		if(mes == 'DEC' || mes == 'DIC')
			valor = 12;
		return valor;
	}
	function simplifica(fecha){
		esp = fecha.substring(0,1);
		if(esp == 'S')
			fecha = fecha.substring(1,11);
		return fecha;	
	}
	function horaFecha(fecha){
		horaA = fecha.substring(fecha.indexOf('-')+1, fecha.indexOf(':'));
		return horaA;
	}
	function minutosFecha(fecha){
		minutosA = fecha.substring(fecha.indexOf(':')+1);
		return minutosA;
	}
	function hoy(fechaEntrada){
		fechaAct = document.forms[0].hFecha.value;
		fechaAct = simplifica(fechaAct);
		fechaApl = document.forms[0][fechaEntrada].value;
		fechaApl = fechaApl.substring(0,2) + "/" + checaMes(fechaApl.substring(3,6)) + "/" + fechaApl.substring(7,11);
		if(fechaApl.length == 9)
			fechaApl = fechaApl.substring(0,3) + "0" + fechaApl.substring(3);
		if(fechaAct == fechaApl)
			return true;
		else
			return false;
	}
	/*
	 *	Función que realiza diferentes validaciones para campos de tipo caracter
	 *
	 */
	function validaComunes(valor, nombre){
		var caracteres = /[$\\@\\%\^\&\*\(\)\[\]\+\!\·\"\,\.\'\¿\¡\´\?\_\-\{\}\`\~\=\|\<\>]/;
		if(valor.value != ''){
			AllTrim(valor);
			valor.value = valor.value.toUpperCase();
			if(valor.value.search(caracteres) > -1){
				alert('- "'+ nombre +'" contiene caracteres invalidos: ' + valor.value + '.');
				valor.focus();
			}
		}
	}
	
	function validaComunesCinco(valor, nombre){
		var caracteres = /[$\\@\\%\^\&\*\(\)\[\]\+\!\·\"\'\¿\¡\´\?\_\-\{\}\`\~\=\|\<\>]/;
		if(valor.value.length != 5){
			alert('- "'+ nombre +'" NO es de 5 d\355gitos');	
			valor.focus();
		}
		if(valor.value != ''){
			AllTrim(valor);
			valor.value = valor.value.toUpperCase();
			if(valor.value.search(caracteres) > -1){
				alert('- "'+ nombre +'" contiene caracteres invalidos: ' + valor.value + '.');
				valor.focus();
			}
		}
	}
	
	function validaActa(valor, nombre){
		var caracteres = /[$\\@\\%\^\&\*\(\)\[\]\+\!\·\"\'\¿\¡\´\?\_\{\}\`\~\=\|\<\>]/;
		if(valor.value != ''){
			AllTrim(valor);
			valor.value = valor.value.toUpperCase();
			if(valor.value.search(caracteres) > -1){
				alert('- "'+ nombre +'" contiene caracteres invalidos: ' + valor.value + '.');
				valor.focus();
			}
		}
	}