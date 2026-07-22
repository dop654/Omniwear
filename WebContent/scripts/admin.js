function eliminaProdotto(btn){
	document.getElementById("action").value = "elimina";
	document.getElementById("interface").submit();
}

function caricaProdotto(idProdotto, nome, prezzo, quantita){
	form = document.getElementById("interface");
	document.getElementById("action").value = "aggiorna";
	document.getElementById("id_prodotto").value = idProdotto;
	document.getElementById("id_prodotto_img").value = idProdotto;
	document.getElementById("nomeProdotto").value = nome;
	document.getElementById("prezzo").value = prezzo;
	document.getElementById("quantita").value = quantita;
	document.getElementById("aggiungi").value = "Modifica";
}