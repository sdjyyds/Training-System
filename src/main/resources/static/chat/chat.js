// 获取 URL 参数中的 chatId 和 type，用于区分聊天类型和聊天对象/群聊
const urlParams = new URLSearchParams(window.location.search);
const chatId = urlParams.get("chatId");      // 聊天对象或聊天室 ID
const type = urlParams.get("type");          // 聊天类型（roomChat 或 privateChat）

// 获取聊天框和输入框 DOM 元素
const chatBox = document.getElementById("chat-box");
const input = document.getElementById("messageInput");
let userId = null;
let socket; // WebSocket 实例，用于消息通信

/**
 * 页面加载后：自动登录验证 -> 加载聊天记录 -> 建立 WebSocket 连接
 */
window.onload = function () {
    fetch("../autoLogin")
        .then(res => res.json())
        .then(data => {
            if (data.status === "success") {
                console.log("欢迎回来，用户ID:", data.user);
                userId = data.user;
                loadChatHistory();     // 加载消息记录
                connectWebSocket();    // 启动 WebSocket 通讯
            } else {
                document.cookie = "token=; Max-Age=0; path=/"; // 删除无效 token
                window.location.href = "../login/login.html";

            }
        });
};
/**
 * 建立 WebSocket 连接，并绑定消息接收与关闭事件
 */
function connectWebSocket() {
    const wsType = type === "roomChat" ? "group" : "private"; // 根据聊天类型确定路径
    const protocol = window.location.protocol === "https:" ? "wss" : "ws"; // 选择 ws/wss 协议
    const host = window.location.host;
    const path = `/ws/chat/${wsType}/${chatId}/${userId}`; // 构建 WebSocket 路径
    socket = new WebSocket(`${protocol}://${host}${path}`); // 创建 WebSocket 连接

    // 监听服务器发送的消息
    socket.onmessage = (event) => {
        const msg = JSON.parse(event.data); // 解析 JSON 消息
        appendMessage(`${msg.senderName}: ${msg.content}`); // 展示到聊天框
    };

    // 监听连接关闭事件，可实现自动重连
    socket.onclose = () => {
        console.log("WebSocket 关闭，尝试重连...");
        // setTimeout(connectWebSocket, 1000); // 如需要可启用自动重连
    };
}

/**
 * 将普通文本消息添加到聊天框
 */
function appendMessage(text) {
    const p = document.createElement("p");
    p.textContent = text;
    chatBox.appendChild(p);
    chatBox.scrollTop = chatBox.scrollHeight; // 滚动到底部
}

/**
 * 通过 WebSocket 发送一条消息
 */
function sendMessage() {
    const content = input.value.trim(); // 获取输入框内容
    if (content && socket && socket.readyState === WebSocket.OPEN) {
        const message = {
            content: content,
            roomId: type === "roomChat" ? parseInt(chatId) : null,
            receiverId: type === "privateChat" ? parseInt(chatId) : null
        };
        socket.send(JSON.stringify(message)); // 发送 JSON 格式消息
        input.value = ""; // 清空输入框
    }
}

/**
 * 上传文件到服务器（通过 FormData + fetch）
 */
function uploadFile() {
    const fileInput = document.getElementById("fileInput");
    const file = fileInput.files[0];
    if (!file) return;

    const formData = new FormData();
    formData.append("file", file); // 上传文件本体
    formData.append("senderId", userId); // 当前用户 ID
    formData.append("type", type);
    if (type === "roomChat") {
        formData.append("roomId", chatId);
    } else {
        formData.append("receiverId", chatId);
    }

    fetch("../file/upload/file", {
        method: "POST",
        body: formData
    })
        .then(res => res.json())
        .then(data => {
            if (data.status === "success") {
                // 上传成功后，直接预览文件（动态插入 DOM）
                const fileUrl = data.fileUrl;
                appendFileMessage(fileUrl, file.name);
            } else {
                console.error("文件上传失败:", data.error);
            }
        });
}

/**
 * 根据文件类型，动态生成图片、视频、文档或链接元素，插入聊天框
 */
function appendFileMessage(fileUrl, fileName) {
    let fileElement;
    const fileExtension = fileName.split('.').pop().toLowerCase(); // 获取扩展名

    if (["jpg", "jpeg", "png", "gif"].includes(fileExtension)) {
        fileElement = `<img src="${fileUrl}" alt="${fileName}" class="file-message">`;
    } else if (["mp4", "mkv", "webm"].includes(fileExtension)) {
        fileElement = `<video controls src="${fileUrl}" class="file-message"></video>`;
    } else if (["pdf", "docx", "txt"].includes(fileExtension)) {
        fileElement = `<a href="${fileUrl}" target="_blank" class="file-message">${fileName}</a>`;
    } else {
        fileElement = `<a href="${fileUrl}" target="_blank" class="file-message">${fileName}</a>`;
    }

    const p = document.createElement("p");
    p.innerHTML = fileElement;
    chatBox.appendChild(p);
    chatBox.scrollTop = chatBox.scrollHeight;
}
/**
 * 加载历史聊天记录，支持普通消息与文件消息（[file] 开头）
 */
function loadChatHistory() {
    let apiUrl = type === "roomChat"
        ? `../chat/message/group?roomId=${chatId}&userId=${userId}`
        : `../chat/message/private?userId=${chatId}&myUserId=${userId}`;

    fetch(apiUrl)
        .then(res => res.json())
        .then(messages => {
            chatBox.innerHTML = "";
            messages.forEach(msg => {
                if (msg.content.startsWith("[file]")) {
                    const fileUrl = msg.content.substring(6); // 提取 URL
                    const filename = fileUrl.split("/").pop(); // 提取文件名
                    appendFileMessage(fileUrl, filename);
                } else {
                    appendMessage(`${msg.senderName}: ${msg.content}`);
                }
            });
        })
        .catch(err => console.error("加载聊天记录失败:", err));
}