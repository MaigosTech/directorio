function validaLogin () {
	var user = document.getElementById('user');
	var pass = document.getElementById('pass');

	var error = document.getElementById('error_login');

	if(user.value == ""){
		error.innerHTML = "El usuario introducido es incorrecto.";
	}else if(pass.value == ""){
		error.innerHTML = "La contraseña introducida es incorrecta.";
	}else{
		//submit
		error.innerHTML = "";
	}
}