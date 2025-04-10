
const urlParams = new URLSearchParams(window.location.search);
const chatId = urlParams.get("chatId");
const type = urlParams.get("type");

const chatBox = document.getElementById("chat-box");
const input = document.getElementById("messageInput");
//webSocket是采用webSocket通讯的 -- 应用层协议
let socket;

function connectWebSocket() {
    const wsType = type === "roomChat" ? "group" : "private";
    //1.新建webSocket，需要提供通讯的地址,并且指明所采用的协议
    socket = new WebSocket(`ws://localhost:8080/ws/chat/${wsType}/${chatId}`);
    //2.这是接收消息的方法
    socket.onmessage = (event) => {
        const msg = JSON.parse(event.data);
        appendMessage(`${msg.senderName}: ${msg.content}`);
    };
    //3.这时连接关闭的方法
    socket.onclose = () => {
        console.log("WebSocket 关闭，尝试重连...");
        // setTimeout(connectWebSocket, 1000); // 自动重连
    };
}

//将消息加载到本地的聊天框内
function appendMessage(text) {
    const p = document.createElement("p");
    p.textContent = text;
    chatBox.appendChild(p);
    chatBox.scrollTop = chatBox.scrollHeight;
}

//4.这时采用socket发送消息的方法 -- 触发
function sendMessage() {
    const content = input.value.trim();
    if (content && socket && socket.readyState === WebSocket.OPEN) {
        const message = {
            content: content,
            roomId: type === "roomChat" ? parseInt(chatId) : null,
            receiverId: type === "privateChat" ? parseInt(chatId) : null
        };
        socket.send(JSON.stringify(message));
        input.value = "";
    }
}

/*
 * 加载聊天记录
 */
function loadChatHistory() {
    let apiUrl = type === "roomChat"
        ? `../chat/message/group?roomId=${chatId}`
        : `../chat/message/private?userId=${chatId}`;

    fetch(apiUrl)
        .then(res => res.json())
        .then(messages => {
            chatBox.innerHTML = "";
            messages.forEach(msg => {
                appendMessage(`${msg.senderName}: ${msg.content}`);
            });
        })
        .catch(err => console.error("加载聊天记录失败:", err));
}

//找到名字为user的cookie
window.onload = function () {
    fetch("../autoLogin")
        .then(response => response.json())
        .then(data => {
            if (data.status !== "success") {
                // 登录状态失效就跳回登录页
                window.location.href = "../login/login.html";
            } else {
                // 可以做欢迎展示
                console.log("欢迎回来，用户ID:", data.user);
                // 可选展示用户名
                // document.getElementById("welcome").innerText = "欢迎，" + data.user;
                loadChatHistory();
                connectWebSocket();
            }
        });
};
