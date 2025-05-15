function manageChatRoom() {
    const name = document.getElementById("room-name").value.trim();
    const imageInput = document.getElementById("room-image-upload");
    const imageFile = imageInput.files[0];

    if (!name) {
        alert("聊天室名称不能为空！");
        return;
    }

    if (imageFile) {
        const formData = new FormData();
        formData.append("file", imageFile);

        fetch("../file/upload", {
            method: "POST",
            body: formData
        })
            .then(res => res.json())
            .then(data => {
                if (data.status === "success") {
                    createChatRoom(name, data.fileUrl);
                } else {
                    alert("头像上传失败：" + data.message);
                }
            })
            .catch(() => alert("头像上传请求失败"));
    } else {
        createChatRoom(name, null);
    }
}

function createChatRoom(name, image) {
    fetch("../chat/addRoom", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            name: name,
            chatImage: image
        })
    })
        .then(res => res.json())
        .then(data => {
            const msg = document.getElementById("message");
            if (data.status === "success") {
                msg.innerText = "聊天室创建成功！";
                msg.style.color = "green";
            } else {
                msg.innerText = "创建失败：" + data.message;
                msg.style.color = "red";
            }
        })
        .catch(() => {
            document.getElementById("message").innerText = "请求失败，请重试。";
        });
}

function renameRoom() {
    const roomId = document.getElementById("rename-room-id").value.trim();
    const newName = document.getElementById("rename-room-name").value.trim();
    const imageInput = document.getElementById("rename-room-image-upload");
    const imageFile = imageInput.files[0];

    if (!roomId) {
        alert("请输入聊天室 ID");
        return;
    }

    // 有头像文件时先上传
    if (imageFile) {
        const formData = new FormData();
        formData.append("file", imageFile);

        fetch("../file/upload", {
            method: "POST",
            body: formData
        })
            .then(res => res.json())
            .then(data => {
                if (data.status === "success") {
                    updateChatRoom(roomId, newName, data.fileUrl);
                } else {
                    alert("头像上传失败：" + data.message);
                }
            })
            .catch(() => alert("头像上传请求失败"));
    } else {
        updateChatRoom(roomId, newName, null);
    }
}

function updateChatRoom(roomId, newName, image) {
    const params = new URLSearchParams();
    params.append("roomId", roomId);
    if (newName) params.append("newName", newName);
    if (image) params.append("chatImage", image);

    fetch("../chat/renameRoom", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: params.toString()
    })
        .then(res => res.json())
        .then(data => {
            alert(data.status === "success" ? "修改成功" : "修改失败：" + data.message);
        })
        .catch(() => alert("修改请求失败"));
}
function deleteRoom() {
    const roomId = document.getElementById("delete-room-id").value.trim();

    if (!roomId) {
        alert("请输入聊天室 ID");
        return;
    }

    const params = new URLSearchParams();
    params.append("roomId", roomId);

    fetch("../chat/deleteRoom", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: params.toString()
    })
        .then(res => res.json())
        .then(data => {
            const msg = document.getElementById("message");
            if (data.status === "success") {
                msg.innerText = "聊天室删除成功！";
                msg.style.color = "green";
            } else {
                msg.innerText = "删除失败：" + data.message;
                msg.style.color = "red";
            }
        })
        .catch(() => {
            const msg = document.getElementById("message");
            msg.innerText = "请求失败，请重试。";
            msg.style.color = "red";
        });
}
