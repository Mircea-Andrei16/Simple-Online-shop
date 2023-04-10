// Cart 
let cartIcon = document.querySelector('#cart-icon');
let cart = document.querySelector('.cart');
let closeCart = document.querySelector('#close-cart');
let downloadButton = document.querySelector('.add-doc');
var searchTermInput = document.getElementById("query");
// Open Close Cart
cartIcon.onclick = () => {
    if (cart.classList.contains("active")) {
        cart.classList.remove("active");
    } else {
        cart.classList.add("active");
    }
}
closeCart.onclick = () => {
    cart.classList.remove("active");
}

searchTermInput.addEventListener("input", function() {
  // Call the autocomplete function whenever the input value changes
  autocomplete(searchTermInput.value);
});

function autocomplete(inputValue) {
  // Make an AJAX call to retrieve a list of autocomplete suggestions
  //delete anterior div class
  $.ajax({
    type: "GET",
    url: "/autocomplete",
    data: { term: inputValue },
    success: function(response) {
      // Create a dropdown menu to display the autocomplete suggestions
      var dropdownMenu = document.querySelector('.autocomplete-items');
      if(dropdownMenu === null){
      	dropdownMenu = document.createElement("div");
      	dropdownMenu.classList.add("autocomplete-items");
      	searchTermInput.parentNode.appendChild(dropdownMenu);
	  }
	  while ( dropdownMenu.firstChild ){
	   dropdownMenu.removeChild( dropdownMenu.firstChild );
	   }  
      // Add a new option to the dropdown menu for each suggestion
      for (var i = 0; i < response.length; i++) {
        var option = document.createElement("div");
        option.classList.add("suggestion");
        option.innerHTML = response[i];
        option.addEventListener("click", function() {
          // When an option is clicked, set the input value to the suggestion and hide the dropdown menu
          searchTermInput.value = this.innerHTML;
          dropdownMenu.remove();
        });
        dropdownMenu.appendChild(option);
      }
    }
  });
}


downloadButton.onclick = () => {
    const link = document.createElement('a');
    link.setAttribute('href', '/download');
    document.body.appendChild(link);

    const event = new MouseEvent('click', {
        view: window,
        bubbles: true,
        cancelable: true
      });

      link.dispatchEvent(event);
      document.body.removeChild(link);
}

// Cart Working JS
if (document.readyState = 'loading') {
    document.addEventListener("DOMContentLoaded", ready);
} else {
    ready();
    updatetotal();
}

// Making Function
function ready() {
    // remove Items from cart
    var removeCartButtons = document.getElementsByClassName('cart-remove');
    for (var i = 0; i < removeCartButtons.length; i++) {
        var button = removeCartButtons[i];
        button.addEventListener('click', removeCartItem);
    }
    //Quantity Changes
    var quantityInputs = document.getElementsByClassName('cart-quantity');
    for (var i = 0; i < removeCartButtons.length; i++) {
        var input = quantityInputs[i];
        input.addEventListener("change", quantityChanged);
    }
    // Add to Cart
    var addCart = document.getElementsByClassName('add-cart');
    for (var i = 0; i < addCart.length; i++) {
        var button = addCart[i]
        button.addEventListener('click', addToCartIfClicked);
    }

    //buy button work
    document.getElementsByClassName('btn-buy')[0].addEventListener('click', buyButtonClicked);
}

// buy button clicked
function buyButtonClicked() {
    alert("Your order is placed")
    var cartContent = document.getElementsByClassName('cart-content')[0];
    while (cartContent.hasChildNodes()) {
        cartContent.removeChild(cartContent.firstChild);
    }
    updateTotal();
}

// remove Item from cart
function removeCartItem(event) {
    var buttonClicked = event.target;
    buttonClicked.parentElement.remove();
    updateTotal();
}

//Quantity changes
function quantityChanged(event) {
    var input = event.target
    if (isNaN(input.value) || input.value <= 0) {
        input.value = 1;
    }
    updateTotal();
}

//add to cart products
function addToCartIfClicked(event) {
    var button = event.target
    var shopProducts = button.parentElement
    var title = shopProducts.getElementsByClassName('product-title')[0].innerText
    var price = shopProducts.getElementsByClassName('price')[0].innerText
    var producImg = shopProducts.getElementsByClassName('product-img')[0].src
    addProductToCart(title, price, producImg);
    updateTotal();
}

function addProductToCart(title, price, productImg) {
    var cartShopBox = document.createElement('div');
    cartShopBox.classList.add('cart-box');
    var cartItems = document.getElementsByClassName('cart-content')[0];
    var cartItemsNames = cartItems.getElementsByClassName('cart-product-title');
    for (var i = 0; i < cartItemsNames.length; i++) {
        if (cartItemsNames[i].innerText == title) {
            alert('You have alerady add this item to chart');
            return;
        }
    }

    var cartBoxContent = `<img src="${productImg}" alt="" class="cart-img">
<div class="detail-box">
  <div class="cart-product-title">${title}</div>
  <div class="cart-price">${price}</div>
  <input type="number" value="1" class="cart-quantity">
</div>
<!-- Remove -->
<i class='bx bxs-trash-alt cart-remove'></i>`;

    cartShopBox.innerHTML = cartBoxContent;
    cartItems.append(cartShopBox);
    cartShopBox
        .getElementsByClassName('cart-remove')[0]
        .addEventListener('click', removeCartItem);
    cartShopBox
        .getElementsByClassName('cart-quantity')[0]
        .addEventListener('change', quantityChanged);
}


//Update Total
function updateTotal() {
    var cartContent = document.getElementsByClassName('cart-content')[0];
    var cartBoxes = cartContent.getElementsByClassName('cart-box');
    var total = 0;
    for (var i = 0; i < cartBoxes.length; i++) {
        var cartBox = cartBoxes[i];
        var priceElement = cartBox.getElementsByClassName('cart-price')[0];
        var quantityElement = cartBox.getElementsByClassName('cart-quantity')[0];
        var price = parseFloat(priceElement.innerText.replace("$", ""));
        var quatity = quantityElement.value;
        total = total + (price * quatity);

        // if price contains some cents value
        total = Math.round(total * 100) / 100;
    }
    document.getElementsByClassName('total-price')[0].innerText = '$' + total;
}