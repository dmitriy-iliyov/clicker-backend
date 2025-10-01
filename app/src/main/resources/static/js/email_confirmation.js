document.getElementById('confirmButton').addEventListener('click', async () => {
    const params = new URLSearchParams(window.location.search);
    const token = params.get('token');

    if (!token) {
        const messageElement = document.getElementById('message');
        messageElement.textContent = 'Token is missing from the URL.';
        messageElement.style.color = 'red';
        return;
    }

    const url = `/ui/confirmation/email?token=${token}`;
    const messageElement = document.getElementById('message');

    try {
        const response = await fetch(url, { method: 'POST'});

        if (response.redirected) {
            window.location.href = response.url;
        } else {
            messageElement.textContent = 'Failed to confirm email. Please try again later.';
            messageElement.style.color = 'red';
        }
    } catch (error) {
        console.error("Error submitting form:", error);
        messageElement.textContent = 'An error occurred. Please try again later.';
        messageElement.style.color = 'red';
    }
});
