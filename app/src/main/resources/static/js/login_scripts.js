document.getElementById('loginForm').addEventListener('submit', submitForm);


async function submitForm(event) {
    event.preventDefault();

    const email = document.querySelector('input[name="email"]').value;
    const password = document.querySelector('input[name="password"]').value;


    fetch('/api/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: new URLSearchParams({
            email: email,
            password: password
        })
    })
        .then(response => {
            if (response.redirected) {
                window.location.href = response.url;
            } else {
                response.text().then(text => {
                    document.getElementById("error-message").textContent = text || "Login failed. mes from login_scripts.js";
                });
            }
        })
        .catch(error => {
            console.error('Error:', error);
            document.getElementById("error-message").textContent = "An unexpected error occurred.";
        });
}