function get_csrf() {
    return fetch('/api/csrf')
        .then(response => {
            if (response.ok) {
                return response.json();
            }
            return Promise.reject("Failed to fetch CSRF token: " + response.statusText);
        })
        .then(response_data => {
            return response_data.token;
        })
        .catch(error => {
            console.error("CSRF Error:", error);
            throw error;
        });
}