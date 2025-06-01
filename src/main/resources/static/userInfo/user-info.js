let userId = null;
window.onload = function () {
    fetch("../autoLogin")
        .then(res => res.json())
        .then(data => {
            if (data.status === "success") {
                loadUserInfo(data.user);
            } else {
                document.cookie = "token=; Max-Age=0; path=/"; // 删除无效 token
                window.location.href = "../login/login.html";

            }
        });
};
function loadUserInfo(id) {
    userId = id;
    fetch(`../userInfo/getInfo?userId=${userId}`)
        .then(res => res.json())
        .then(data => {
            if (data.username !== null) {
                document.getElementById("username").value = data.username;
            }
            if (data.email !== null) {
                document.getElementById("email-hidden").innerText = maskEmail(data.email);
            }
            if (data.phone !== null) {
                document.getElementById("phone-hidden").innerText = maskPhone(data.phone);
            }
            if (data.address !== null) {
                document.getElementById("address").value = data.address;
            }
            if (data.hometown !== null) {
                document.getElementById("hometown").value = data.hometown;
            }
            if (data.role !== null) {
                document.getElementById("role").innerText = data.role;
            }
            if (data.createdAt !== null) {
                document.getElementById("created_at").value = new Date(data.createdAt).toLocaleString();
            }
            if (data.userImage !== null) {
                document.getElementById("user-image").src = data.userImage;
            }
        })
        .catch(err => {
            console.error("加载用户信息失败:", err);
        });
}

function maskEmail(email) {
    const [local, domain] = email.split("@");
    return local.slice(0, 3) + "***@" + domain;
}

function maskPhone(phone) {
    return phone.slice(0, 3) + "****" + phone.slice(7);
}

function revealEmail() {
    openPasswordModal("请输入密码以验证身份：", function(password) {
        console.log("准备发请求", { userId, password, type: "email" });
        if (!password) return;
        fetch("../userInfo/getEmailOrPhone", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ userId, password, type: "email" })
        })
            .then(res => res.json())
            .then(data => {
                if (data.status === "success") {
                    document.getElementById("email-hidden").innerText = data.value;
                } else {
                    alert("验证失败或权限不足！");
                }
            });
    });
}

function revealPhone() {
    openPasswordModal("请输入密码以验证身份：", function(password) {
        if (!password) return;
        fetch("../userInfo/getEmailOrPhone", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ userId, password, type: "phone" })
        })
            .then(res => res.json())
            .then(data => {
                if (data.status === "success") {
                    document.getElementById("phone-hidden").innerText = data.value;
                } else {
                    alert("验证失败或权限不足！");
                }
            });
    });
}

function updateUserInfo() {
    // if (!validatePhone() || !validateEmail()) {
    //     alert("请检查输入格式！");
    //     return;
    // }

    const payload = {
        id: userId,
        username: document.getElementById("username").value,
        address: document.getElementById("address").value,
        hometown: document.getElementById("hometown").value
    };

    fetch("../userInfo/updateInfo", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(payload)
    })
        .then(res => res.json())
        .then(data => {
            if (data.status === "success") {
                alert("修改成功！");
            } else {
                alert("修改失败：" + data.status);
            }
        });
}

function recoverPassword() {
    const input = prompt("请输入您注册时的邮箱或手机号：");
    if (!input) return;

    const phoneRegex = /^1[3-9]\d{9}$/;
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

    let keyType = phoneRegex.test(input) ? "phone" :
        emailRegex.test(input) ? "email" : "";

    if (!keyType) {
        alert("输入格式无效，请输入正确的手机号或邮箱！");
        return;
    }

    fetch("../userInfo/verifyEmailOrPhone", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ userId, emailOrPhone: input, type: keyType })
    })
        .then(res => res.json())
        .then(data => {
            if (data.status === "success") {
                promptResetPassword(userId);
            } else {
                alert("未找到匹配信息，请确认邮箱或手机号是否正确。");
            }
        });
}

function promptResetPassword(userId) {
    openPasswordModal("验证成功，请输入新的密码：", function(newPassword) {
        if (!newPassword || newPassword.length < 6) {
            alert("密码不能为空且长度不得少于6位！");
            return;
        }

        fetch("../userInfo/resetPassword", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ userId, newPassword })
        })
            .then(res => res.json())
            .then(data => {
                if (data.status === "success") {
                    alert("密码修改成功！请使用新密码登录.");
                    window.location.href = "/logout";
                } else {
                    alert("密码重设失败：" + data.status);
                }
            });
    });
}

let passwordCallback = null;

function openPasswordModal(promptText, callback) {
    document.getElementById("modal-text").innerText = promptText;
    document.getElementById("modal-password").value = "";
    document.getElementById("password-modal").style.display = "flex";
    passwordCallback = callback;
    document.getElementById("modal-password").focus();
}

function closePasswordModal() {
    document.getElementById("password-modal").style.display = "none";
    passwordCallback = null;
}

function submitPassword() {
    const pwd = document.getElementById("modal-password").value;
    console.log("输入的密码：", pwd); // ✅ 添加日志
    if (passwordCallback) {
        passwordCallback(pwd);
    }
    closePasswordModal();
}
