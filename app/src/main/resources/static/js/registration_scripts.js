document.getElementById('registrationForm').addEventListener('submit', submitForm);

async function submitForm(event) {
    event.preventDefault();

    const form = document.getElementById("registrationForm");
    const formData = new FormData(form);
    const jsonData = Object.fromEntries(formData);

    try {
        const response = await fetch("/users", {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(jsonData)
        });

        if (!response.ok) {
            const errorData = await response.json();
            displayErrors(errorData);
        } else {
            window.location.href = "/users/login";
        }
    } catch (error) {
        console.error("Error submitting form:", error);
    }
}

function displayErrors(errors) {
    const errorList = document.getElementById("errorList");
    errorList.innerHTML = "";

    for (const [field, message] of Object.entries(errors)) {
        const errorItem = document.createElement("li");

        if (typeof message === 'object' && message !== null) {
            errorItem.textContent = `${field}:`;
            errorList.appendChild(errorItem);

            for (const [subField, subMessage] of Object.entries(message)) {
                const subErrorItem = document.createElement("li");
                subErrorItem.textContent = `${subField}: ${subMessage}`;
                errorList.appendChild(subErrorItem);
            }
        } else {
            errorItem.textContent = `${field}: ${message}`;
            errorList.appendChild(errorItem);
        }
    }
}