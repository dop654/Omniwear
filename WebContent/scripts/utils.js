let homeIndex = 0;
showHomeSlides();

function showHomeSlides() {
  let i;
  let slides = document.getElementsByClassName("mySlides");
  
  if (slides.length === 0) {
          return; 
      }
	  
  let dots = document.getElementsByClassName("dot");
  for (i = 0; i < slides.length; i++) {
    slides[i].style.display = "none";  
  }
  homeIndex++;
  if (homeIndex > slides.length) {homeIndex = 1}    
  for (i = 0; i < dots.length; i++) {
    dots[i].className = dots[i].className.replace(" active", "");
  }
  slides[homeIndex-1].style.display = "block";  
  dots[homeIndex-1].className += " active";
  setTimeout(showHomeSlides, 2000);
}

let productIndex = 1;
showProductSlides(productIndex);

function plusSlides(n) {
    showProductSlides(productIndex += n);
}

function showProductSlides(n) {
    let i;
    let slides = document.getElementsByClassName("productSlides");

    if (slides.length == 0) {
			return;
		}
		
    if (n > slides.length){
        	productIndex = 1;
		}
		
    if (n < 1) {
        	productIndex = slides.length;
		}

    for (i = 0; i < slides.length; i++) {
        	slides[i].style.display = "none";
		}

    slides[productIndex - 1].style.display = "block";
}

function currentSlide(n) {
    showProductSlides(productIndex = n);
}