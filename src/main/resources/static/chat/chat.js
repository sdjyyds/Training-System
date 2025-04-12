
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
    const protocol = window.location.protocol === "https:" ? "wss" : "ws";
    const host = window.location.host; // 自动获取当前域名和端口
    const path = `/ws/chat/${wsType}/${chatId}`;
    socket = new WebSocket(`${protocol}://${host}${path}`);
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
 * 发送文件
 */
function uploadFile() {
    const fileInput = document.getElementById("fileInput");
    const file = fileInput.files[0];
    if (!file) return;

    const formData = new FormData();
    formData.append("file", file);
    formData.append("senderId", getUserId()); // 获取当前登录用户ID
    formData.append("type", type);
    if (type === "roomChat") {
        formData.append("roomId", chatId);
    } else {
        formData.append("receiverId", chatId);
    }

    fetch("../file/upload", {
        method: "POST",
        body: formData
    })
        .then(res => res.json())
        .then(data => {
            if (data.status === "success") {
                // 文件上传成功，动态显示文件
                const fileUrl = data.fileUrl; // 假设后端返回的文件访问路径为 fileUrl
                appendFileMessage(fileUrl, file.name); // 调用函数将文件插入到聊天框中
            } else {
                console.error("文件上传失败:", data.error);
            }
        });
}
function appendFileMessage(fileUrl, fileName) {
    // 根据文件类型动态生成文件展示元素
    let fileElement;
    const fileExtension = fileName.split('.').pop().toLowerCase();

    if (["jpg", "jpeg", "png", "gif"].includes(fileExtension)) {
        // 图片
        fileElement = `<img src="${fileUrl}" alt="${fileName}" class="file-message">`;
    } else if (["mp4", "mkv", "webm"].includes(fileExtension)) {
        // 视频
        fileElement = `<video controls src="${fileUrl}" class="file-message"></video>`;
    } else if (["pdf", "docx", "txt"].includes(fileExtension)) {
        // 文档
        fileElement = `<a href="${fileUrl}" target="_blank" class="file-message">${fileName}</a>`;
    } else {
        // 其他文件类型
        fileElement = `<a href="${fileUrl}" target="_blank" class="file-message">${fileName}</a>`;
    }

    // 将文件消息添加到聊天框中
    const chatBox = document.getElementById("chat-box");
    const p = document.createElement("p");
    p.innerHTML = fileElement; // 这里插入生成的文件元素
    chatBox.appendChild(p);
    chatBox.scrollTop = chatBox.scrollHeight;
}
function getUserId() {
    const cookies = document.cookie.split(";");
    for (let cookie of cookies) {
        const [key, value] = cookie.trim().split("=");
        if (key === "login") {
            return value;
        }
    }
    return null;
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
                if (msg.content.startsWith("[file]")) {
                    // 提取文件名并展示文件
                    const filename = msg.content.substring(6);
                    const fileUrl = `/uploads/${filename}`;
                    appendFileMessage(fileUrl, filename); // 使用appendFileMessage来处理文件消息
                } else {
                    appendMessage(`${msg.senderName}: ${msg.content}`);
                }
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
