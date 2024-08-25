// products.js

document.addEventListener("DOMContentLoaded", function () {
  fetch("/product", {
    method: "GET",
    headers: {
      ContentType: "application/json",
    },
  })
    .then((response) => response.json())
    .then((products) => {
      const productGrid = document.getElementById("productGrid");
      productGrid.innerHTML = ""; // Clear existing content

      products.forEach((product) => {
        const card = document.createElement("div");
        card.className = "card";

        const imageUrl = `/product/${product.id}/images/0`;

        card.innerHTML = `
                    <div class="card-body">
                        <img src="${imageUrl}" alt="add your picture to display here!">
                        <h3>${product.name}</h3>
                        <p>${product.description}</p>
                        <p>Price: $${product.price}</p>
                    </div>
                `;

        productGrid.appendChild(card);
      });
    })
    .catch((error) => console.error("Error fetching products:", error));
});
