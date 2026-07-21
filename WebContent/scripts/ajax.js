function createXMLHttpRequest() {
	var request;
	try {
		// Firefox 1+, Chrome 1+, Opera 8+, Safari 1.2+, Edge 12+, Internet Explorer 7+
		request = new XMLHttpRequest();
	} catch (e) {
		// past versions of Internet Explorer 
		try {
			request = new ActiveXObject("Msxml2.XMLHTTP");  
		} catch (e) {
			try {
				request = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e) {
				alert("Il browser non supporta AJAX");
				return null;
			}
		}
	}
	return request;
}

function loadDoc(url, methodIsGET, param, func) {
	var request = createXMLHttpRequest();
	var errorDiv = document.getElementById("error");
	
	if(request) {
		request.onreadystatechange = function() {
			if(this.readyState == 4) {
				if(this.status == 200) {
					errorDiv.hide();
					func(this);
				} else {
					msg = "Problemi nell'esecuzione della richiesta: " + this.statuText;
					if(this.status == 0) {
						msg = "Nessuna risposta ricevuta in tempo";
					} else if(this.status == 418) {
						msg = "Sono una teiera";
					}
					errorDiv.innerHTML = msg;
					errorDiv.show();
				}
			}
		};
		
		setTimeout(function() {
			if(request.readyState < 4){
				request.abort();
			}
		}, 15000);
		
		if(methodIsGET == 1){
			if(param){
				request.open("GET", url + "?" + param, true);
			} else {
				request.open("GET", url, true);
			}
			request.setRequestHeader("Connection", "close");
			request.send(null);
		} else {
			request.open("POST", url, true);
			request.setRequestHeader("Connection", "close");
			request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			
			if(param){
				request.send(param);
			}
		}
	}
}

function aggiornaCatalogo(search, categorie) {
	let url = "/catalogo";
	let parametri;
	
	if(search != null) {
		parametri = "search=" + search;
	}else if(categorie != null){
		parametri = "categorie=" + categorie[0];
		for(let i = 1; i<categorie.length; i++){
			parametri += "&categorie=" + categorie[i];
		}
	}
	
	loadDoc(url, 1, parametri, function(request) {
		
		let risposta = request.responseText;
		let prodotti = JSON.parse(risposta);
		let catalogo = document.getElementById("catalogo");
		
		catalogo.innerHTML = "";
		
		for(let i=0; i<prodotti.length; i++){
			let prodotto = prodotti[i];
			
			catalogo.innerHTML += ("<p>" + prodotto + "</p><br>");
		}
	});
}

window.onload = function() {
  aggiornaCatalogo(null, null);
};