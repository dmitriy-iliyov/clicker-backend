function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
}


async function logoutFunc() {
    const csrfToken = await get_csrf()
    const options = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-XSRF-TOKEN': csrfToken
        }
    };
    await fetch("/api/logout", options)
        .then(response => {
        if (response.ok) {
            if(response.redirected){
                window.location.href = response.url
            }
            // window.location.href = '/home';
        } else {
            console.error('Logout failed:', response.statusText);
        }
    })
        .catch(error => {
            console.error('Error during logout:', error);
        });
}

async function delAccountFunc() {
    const csrfToken = await get_csrf()
    const options = {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            'X-XSRF-TOKEN': csrfToken
        }
    };
    await fetch("/api/users/me", options)
        .then(response => {
            if (response.ok) {
                if(response.redirected) {
                    logoutFunc()
                    window.location.href = response.url
                }
                // window.location.href = '/home';
            } else {
                console.error('del failed:', response.statusText);
            }
        })
        .catch(error => {
            console.error('Error during logout:', error);
        });
}

// async function updProfileFunc() {
//
//     const form = document.getElementById('profileForm');
//
//     const formData = new FormData(form);
//     const data = Object.fromEntries(formData.entries());
//
//     const csrf = get_csrf()
//
//     try {
//         const response = await fetch('/users/user/', {
//             method: 'PUT',
//             headers: {
//                 'Content-Type': 'application/json',
//                 'X-XSRF-TOKEN': csrf
//             },
//             body: JSON.stringify(data),
//         });
//
//         if (response.ok) {
//             const result = await response.json();
//             console.log('Profile updated successfully:', result);
//             alert('Profile updated successfully!');
//         } else {
//             console.error('Error updating profile:', response);
//             alert('Failed to update profile. Please try again.');
//         }
//     } catch (error) {
//         console.error('Network error:', error);
//         alert('An error occurred while updating your profile.');
//     }
// }

// document.getElementById('connect-wallet').addEventListener('click', updProfileFunc);
//
// document.getElementById('change-account').addEventListener('click', updProfileFunc);

document.getElementById('logout').addEventListener('click', logoutFunc);

document.getElementById('delete-account').addEventListener('click', delAccountFunc);

document.addEventListener('DOMContentLoaded', () => {
    const confirmationCookie = getCookie('__Secure-auth_details');
    if (confirmationCookie==='Unconfirmed') {
        const notification = document.getElementById('confirmationNotification');
        notification.style.display = 'block';
    }
});