function aggiornaCatalogo(search) {
	let url = "/catalogo";
	let parametri = "search=" + search;
	
	loadDoc(url, 1, parametri, function(request) {
		
		let risposta = request.responseText;
		let prodotti = JSON.parse(risposta);
		let catalogo = document.getElementById("catalogo");
		
		catalogo.innerHTML = "";
		
		for(let i=0; i<prodotti.length; i++){
			let prodotto = prodotti[i];
			
			catalogo.innerHTML += "";
		}
	});
}