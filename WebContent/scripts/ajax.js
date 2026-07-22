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
                    if(errorDiv) errorDiv.style.display = "none";
                    func(request);
                } else {
                    var msg = "Problemi nell'esecuzione della richiesta: " + request.statusText;
                    if(request.status == 0) {
                        msg = "Nessuna risposta ricevuta in tempo";
                    }
                    if(errorDiv) {
                        errorDiv.innerHTML = msg;
                        errorDiv.style.display = "block";
                    }
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
            request.setRequestHeader("X-Requested-With", "XMLHttpRequest");
            request.send(null);
        } else {
            request.open("POST", url, true);
            request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            request.setRequestHeader("X-Requested-With", "XMLHttpRequest");
            if(param){
                request.send(param);
            } else {
                request.send();
            }
        }
    }
}

function filtraCatalogo() {
    var inputCerca = document.getElementById('cerca').value;
    var params = "cerca=" + inputCerca;
    
    var checkboxes = document.querySelectorAll('input[name="categorie"]:checked');
    for (var i = 0; i < checkboxes.length; i++) {
        params += "&categorie=" + checkboxes[i].value;
    }
    
    loadDoc("catalogo", 1, params, handleCatalogo);
}

function handleCatalogo(request) {
    var prodotti = JSON.parse(request.responseText);
    
    var sezioneProdotti = document.getElementById('prodotti');
    sezioneProdotti.innerHTML = "";
    
    if (prodotti.length === 0) {
        sezioneProdotti.innerHTML = "<p>Nessun prodotto trovato.</p>";
        return;
    }

    for (var i = 0; i < prodotti.length; i++) {
        var p = prodotti[i];
        
        sezioneProdotti.innerHTML += 
            '<div class="glass">' +
                '<h3>' + p.nome_prodotto + '</h3><br>' +
                '<label class="prezzo">' + p.prezzo + ' €</label><br>' +
                '<a href="SchedaProdottoServlet?id_prodotto=' + p.id_prodotto + '">Dettagli</a>' +
            '</div>';
    }
}