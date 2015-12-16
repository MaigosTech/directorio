function abrePop () {
	var obj = document.getElementById('popDatos');
	var mCalendario = document.getElementById('datos');
	obj.style.top = 0 + 'px';

	var toTop = (screen.height/3) - (mVentanita.offsetHeight / 3);

	mVentanita.style.marginTop = toTop + 'px';

}

function cierraPop(){
	var obj = document.getElementById('popDatos');

	obj.style.top = -9999 + 'px';
}