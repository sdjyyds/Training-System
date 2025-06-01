window.onload = function () {
    fetch("../autoLogin")
        .then(response => response.json())
        .then(data => {
            if (data.status === "success") {
                // 可以做欢迎展示
                console.log("欢迎回来，用户ID:", data.user);
                // 可选展示用户名
                // document.getElementById("welcome").innerText = "欢迎，" + data.user;
            } else {
                document.cookie = "token=; Max-Age=0; path=/"; // 删除无效 token
                // 登录状态失效就跳回登录页
                window.location.href = "../login/login.html";
            }
        });
}