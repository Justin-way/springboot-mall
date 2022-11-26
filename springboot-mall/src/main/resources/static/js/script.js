// let splide = new Splide( '.splide' );
// splide.mount();

let productWrapper = document.getElementById('product-wrapper');

fetch("https://demo7776462.mockable.io/popular_products")
  .then(function(response) {
    console.log(response);
    return response.json();
  })
  .then(function(myJson) {
    let products = myJson.products;

    for (let i = 0; i < products.length; i++) {

      let newProductEl = document.createElement('div');

      let templateCard = document.querySelector("#card-template");
      let template_clone = templateCard.content.cloneNode(true);

      let hotTagEl = template_clone.getElementById('hot-tag');
      let titleEl = template_clone.getElementById('title');
      let subtitleEl = template_clone.getElementById('subtitle');
      let priceEl = template_clone.getElementById('price');
      let thumbnailEl = template_clone.getElementById('thumbnail');

      titleEl.textContent = products[i].name;
      subtitleEl.textContent = products[i].author;
      priceEl.textContent = products[i].price;
      thumbnailEl.src = products[i].thumbnail;

      if (products[i].isHot) {
         hotTagEl.classList.add('active');
      }

      newProductEl.appendChild(template_clone);

      // let templateCard = document.getElementById('card-template').text.trim();

      // titleEl.textContent = products[i].name;
      // let subtitleEl = newProductEl.getElementsByTagName('span')[1];
      // subtitleEl.textContent = products[i].author;
      // let priceEl = newProductEl.getElementsByTagName('span')[2];
      // priceEl.textContent = products[i].price;
      // let thumbnailImg = newProductEl.getElementsByTagName('img')[0];
      // thumbnailImg.src = products[i].thumbnail;



//       var template= document.querySelector("#add-feeds");
// my_template_clone = template.content.cloneNode(true); // clone the template
// var my_ul = my_template_clone.getElementById('tag_list'); //now you can find the *ul*

// var newLI = document.createElement("LI"); //create random stuff
// newLI.innerHTML="hello";
// my_ul.appendChild(newLI);  //append it to the ul

// var div = document.getElementById('show_list');
// div.appendChild(my_ul); //now add it into the DOM



      // newProductEl.innerHTML = templateCard;
      // let titleEl = newProductEl.getElementsByTagName('span')[0];
      // titleEl.textContent = products[i].name;
      // let subtitleEl = newProductEl.getElementsByTagName('span')[1];
      // subtitleEl.textContent = products[i].author;
      // let priceEl = newProductEl.getElementsByTagName('span')[2];
      // priceEl.textContent = products[i].price;
      // let thumbnailImg = newProductEl.getElementsByTagName('img')[0];
      // thumbnailImg.src = products[i].thumbnail;


      productWrapper.appendChild(newProductEl);

    }
  }).catch((err) => {
    console.log(err);
  });