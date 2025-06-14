let userId = null

window.onload = function () {
    fetch("../autoLogin")
        .then(response => response.json())
        .then(data => {
            if (data.status === "success") {
                // 可以做欢迎展示
                console.log("欢迎回来，用户ID:", data.user);
                userId = data.user;
                loadChatList();
                // 可选展示用户名
                // document.getElementById("welcome").innerText = "欢迎，" + data.user;
            } else {
                document.cookie = "token=; Max-Age=0; path=/"; // 删除无效 token
                // 登录状态失效就跳回登录页
                window.location.href = "../login/login.html";
            }
        });
}

function loadChatList() {
    fetch(`../showChatList?userId=${userId}`)
        .then(response => response.json())
        .then(data => {
            // 渲染 chat 列表
            const chatList = document.getElementById("chat-list");
            data.forEach(chat => {
                const chatItem = document.createElement("div");
                chatItem.classList.add("chat-item");
                chatItem.innerHTML = `
                    <img src="${chat.chatImage}" alt="${chat.chatName}">
                    <div>${chat.chatName}</div>
                `;
                chatItem.addEventListener("click", () => {
                    location.href = `../chat/chat.html?type=${chat.type}&chatId=${chat.id}`; // 跳转到聊天页面
                });
                chatList.appendChild(chatItem);
            });
        });
}
function searchChat(type) {
    const chatId = type === 'user'
        ? document.getElementById("user-id-input").value
        : document.getElementById("room-id-input").value;

    if (!chatId || isNaN(chatId)) {
        alert("请输入有效的 ID");
        return;
    }

    fetch(`../chat/search?type=${type}&chatId=${chatId}`)
        .then(res => res.json())
        .then(data => {
            const resultDiv = document.getElementById("search-result");
            resultDiv.innerHTML = "";

            if (data.id == -1) {
                console.error("请求失败，请稍后再试");
                return;
            }
            const chatItem = document.createElement("div");
            chatItem.classList.add("chat-item");

            let chatName = data.chatName, chatImage = data.chatImage, chatType = data.type, chatId = data.id;
            chatItem.innerHTML = `
                    <img src="${chatImage}" alt="${chatName}">
                    <div>${chatName}</div>
                `;
            chatItem.addEventListener("click", () => {
                location.href = `../chat/chat.html?type=${chatType}&chatId=${chatId}`;
            });

            resultDiv.appendChild(chatItem);
        })
        .catch(() => {
            document.getElementById("search-result").innerHTML =
                "<p style='color:red;'>请求失败，请稍后再试</p>";
        });
}
