function eliminaProdotto() {
	let input = document.getElementById("action");
	input.value = "elimina";
	let form = input.closest("form");
	form.submit();
}

function aggiornaProdotto() {
	let input = document.getElementById("action");
	input.value = "aggiorna";
	let form = input.closest("form");
	form.submit();
}