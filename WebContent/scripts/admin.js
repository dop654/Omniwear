function eliminaProdotto(form){
	let i = form.closest("#action");
	i.value = "elimina";
	document.getElementById("interface").submit();
}

function caricaProdotto(idProdotto, nome, prezzo){
	form = document.getElementById("interface");
	document.getElementById("action").value = "aggiorna";
	document.getElementById("id_prodotto").value = idProdotto;
	document.getElementById("nomeProdotto").value = nome;
	document.getElementById("prezzo").value = prezzo;
	document.getElementById("submit").value = "Modifica";
}