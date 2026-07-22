const msg_solo_lettere = "Sono consentite solo lettere e spazi";
const msg_vuoto = "Questo campo non può essere vuoto";
const msg_email = "Inserire una email valida";
const msg_pw = "<br>La password deve contenere almeno 8 caratteri, di cui almeno un numero, una lettera ed un carattere speciale (_.,?!)";
const msg_addr = "Sono consentiti solo lettere, numeri, spazi e ,.";
const msg_carta = "Inserire il PAN da 16 cifre<br>";
const msg_cvc = "Inserire il CVC composto da 3 cifre<br>";

function validateField(elemento_form, error_span, error_msg) {
	if(elemento_form.checkValidity()){
		elemento_form.style.color = "black";
		error_span.innerHTML = "";
		return true;
	}
	elemento_form.style.color = "red";
	if(elemento_form.validity.valueMissing){
		error_span.innerHTML = msg_vuoto;
	} else {
		error_span.innerHTML = error_msg;
	}
	return false;
}