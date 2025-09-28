async function buttonClicked() {
    const url = '/clicker';

    const csrfToken = await get_csrf()

    const options = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-XSRF-TOKEN': csrfToken
        },
        body: JSON.stringify({ 'message': 'Button clicked' })
    };

    try {
        fetch(url, options)
            .then(response => {
                if (response.ok) {
                    return response.json();
                } else {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
            })
            .then(response_data => {
                if (response_data && typeof response_data.probability === 'number') {
                    updateUI(response_data.probability);
                } else {
                    console.warn("Unexpected response format:", response_data);
                }
            })
            .catch(error => {
                console.error('Error:', error);
            });
    } catch (error) {
        console.error("Error submitting form:", error);
    }
}

function updateUI(probability) {
    if (probability === 100) {
        counter = 0;
        clickCountContent.textContent = `${counter}/1000`;
        probabilityContent.textContent = '100%';
    } else {
        counter++;
        clickCountContent.textContent = `${counter}/1000`;
        probabilityContent.textContent = `${probability}%`;
    }
}

// Проверка мобильного устройства и добавление блоков для ПК
function isMobile() {
    return /Android|iPhone|iPad|iPod|Opera Mini|IEMobile|WPDesktop/i.test(navigator.userAgent) || window.innerWidth <= 768;
}

if (!isMobile()) {
    console.log("PC");
    const contentDiv = document.getElementById('dynamic-content');

    const newBlock = document.createElement('div');
    newBlock.className = 'dynamic-advertising-block-1';
    newBlock.textContent = 'Этот блок добавлен, так как вы на ПК.';

    contentDiv.appendChild(newBlock);

    const anotherBlock = document.createElement('div');
    anotherBlock.className = 'dynamic-advertising-block-2';
    anotherBlock.textContent = 'Ещё один блок для ПК пользователей.';

    contentDiv.appendChild(anotherBlock);
}

var clickCountContent = document.getElementById('clickCount');
var probabilityContent = document.getElementById('probability')
let counter = 0;
document.getElementById('clickerButton').addEventListener('click', buttonClicked);
