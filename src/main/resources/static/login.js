document
  .getElementById("login-form")
  .addEventListener("submit", async function (event) {
    event.preventDefault(); // Prevent the default form submission

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    const errorMessage = document.getElementById("error-message");

    const loginData = {
      userName: username,
      password: password,
    };

    try {
      const response = await fetch("/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(loginData),
      });

      if (!response.ok) {
        const errorData = await response.json();
        errorMessage.textContent =
          errorData.message || "Login failed. Please try again.";
        return;
      }

      const data = await response.text();
      localStorage.setItem("token", data);
      console.log(localStorage.getItem("token"));
      alert("Login successful!");
      // Redirect to another page or perform further actions
      window.location.href = "/admin-page"; // Example redirect
    } catch (error) {
      errorMessage.textContent = "An error occurred. Please try again.";
      console.error("Error:", error);
    }
  });
