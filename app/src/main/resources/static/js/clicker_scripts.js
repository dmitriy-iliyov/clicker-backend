let socket;

async function buttonClicked() {
    if (!socket || socket.readyState === WebSocket.CLOSED) {
        socket = new WebSocket("wss://localhost:8446/api/clicker");

        socket.onopen = () => {
            console.log("WebSocket connection opened");
            socket.send("click")
        };

        socket.onmessage = (event) => {
            try {
                const data = JSON.parse(event.data);
                const clicks = data.clicksCount;
                const probability = data.probability;
                updateUI(clicks, probability);
            } catch (e) {
                console.error("Parse error:", e, event.data);
            }
        };

        socket.onerror = (error) => {
            console.error("WebSocket error:", error);
        };

        socket.onclose = () => {
            console.log("WebSocket connection closed");
        };
    } else {
        socket.send("click")
    }
}

function updateUI(counter, probability) {
    if (probability === 100) {
        clickCountContent.textContent = `${counter}/1000`;
        probabilityContent.textContent = '100%';
    } else {
        clickCountContent.textContent = `${counter}/1000`;
        probabilityContent.textContent = `${probability}%`;
    }
}

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
