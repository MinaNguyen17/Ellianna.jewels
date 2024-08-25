// admin.js

document.addEventListener("DOMContentLoaded", function () {
  const token = localStorage.getItem("token");
  console.log(token);

  if (!token) {
    // Redirect to the login page if no token is found
    alert("You are not authorized to view this page. Please log in first.");
    window.location.href = "/signin-page";
    return;
  }

  // If the token is valid, you can proceed to fetch data for the admin page
  // Example: Fetching some protected data
  fetch("/admin-page", {
    method: "GET",
    headers: {
      Authorization: `Bearer ${token}`,
    },
  }).then((response) => {
    if (response.ok) {
      return response.json();
    } else {
      throw new Error("Failed to fetch admin data");
    }
  });
});

$("#createProductForm").on("submit", (e) => {
  e.preventDefault();
  var name = $("#name").val();
  var description = $("#description").val();
  var price = $("#price").val();
  var available = $("#available").is(":checked"); // For checkbox
  var isCustomized = $("#isCustomized").is(":checked");

  var productData = {
    name: name,
    description: description,
    price: price,
    available: available,
    isCustomized: isCustomized,
  };
  console.log("Product Data:", productData);

  // Send the data to the server using fetch
  fetch("/product", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(productData),
  })
    .then((response) => {
      if (response.ok) {
        return response.json(); // Parse the JSON response if the request was successful
      } else {
        throw new Error("Error creating product: " + response.statusText);
      }
    })
    .then((data) => {
      console.log("Product created successfully:", data);
      // Optionally, do something with the successful response (e.g., update the UI)
      alert("Success! Product ID: " + data.id);
      window.location.href = "/admin-page";
    })
    .catch((error) => {
      console.error("Error creating product:", error);
      // Handle errors, such as displaying an error message to the user
    });
});

$("#updateImageForm").on("submit", (e) => {
  e.preventDefault();

  // Get the product ID value
  var id = $("#productId").val();

  // Get the selected image file(s)
  var imageFiles = $("#productImage")[0].files;

  // Create a FormData object
  var formData = new FormData();
  formData.append("id", id); // Append the product ID

  // Append each selected image to the FormData object
  for (let i = 0; i < imageFiles.length; i++) {
    formData.append("images", imageFiles[i]);
  }

  // Send the form data using fetch
  fetch(`/product/${id}/images`, {
    method: "POST",
    body: formData,
  })
    .then((response) => {
      if (response.ok) {
        return response;
      } else {
        throw new Error("Failed to update images");
      }
    })
    .then((data) => {
      console.log("Image updated successfully");
      alert("Image(s) updated successfully for Product ID: " + id);
      window.location.href = "/admin-page"; // Redirect to the admin page
    })
    .catch((error) => {
      console.error("Error updating image:", error);
      alert("Failed to update image(s). Please try again.");
    });
});
