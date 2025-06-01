let isPhoneRegister = true;

function sendVerification(){
    let code = Math.floor(100000 + Math.random() * 900000); // 生成6位验证码
    document.getElementById("verification").value = code.toString();
}

function toggleRegister() {
    isPhoneRegister = !isPhoneRegister;
    document.getElementById('phone-register').style.display = isPhoneRegister ? 'block' : 'none';
    document.getElementById('email-register').style.display = isPhoneRegister ? 'none' : 'block';
    document.querySelector('.switch-link').innerText = isPhoneRegister ? 'Register with Email' : 'Register with Phone';
}

function validatePhone() {
    let phone = document.getElementById('phone').value;
    let phoneRegex = /^1[3-9]\d{9}$/;
    document.getElementById('logicCheck').innerText = phoneRegex.test(phone) ? "" : "Please enter a valid phone number.";
}

function validateEmail() {
    let email = document.getElementById('email').value;
    let emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    document.getElementById('logicCheckEmail').innerText = emailRegex.test(email) ? "" : "Please enter a valid email.";
}

function validatePassword() {
    let password = document.getElementById('password').value;
    let passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}$/;
    document.getElementById('logicCheckPassword').innerText = passwordRegex.test(password) ? "" : "Password must be at least 6 characters and contain letters and numbers.";
}

/**
 * 处理用户注册
 */
function register() {
    // 获取手机号、邮箱和密码的值
    let phone = document.getElementById('phone').value;
    let email = document.getElementById('email').value;
    let password = document.getElementById('password').value;

    // 检查手机号和邮箱的格式是否正确
    if (isPhoneRegister && document.getElementById('logicCheck').innerText !== "") {
        alert("请输入正确的手机号！");
        return;
    }
    if (!isPhoneRegister && document.getElementById('logicCheckEmail').innerText !== "") {
        alert("请输入正确的邮箱！");
        return;
    }
    // 检查密码格式是否正确
    if (document.getElementById('logicCheckPassword').innerText !== "") {
        alert("请输入符合规则的密码！");
        return;
    }

    // 根据注册类型决定使用手机号还是邮箱
    let account = isPhoneRegister ? phone : email;

    // 构造请求数据
    let data = `account=${account}&password=${password}`;

    // 发送POST请求到/register
    let xhr = new XMLHttpRequest();
    xhr.open('POST', "/register", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.send(data);

    // 处理请求响应
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            let response = JSON.parse(xhr.responseText);
            // 如果注册成功，跳转到登录页面
            if (xhr.status === 200 && response.success) {
                window.location.href = "../login/login.html";
            } else {
                // 如果注册失败，显示错误消息
                document.getElementById('repeatCheck').innerText = response.message || "该手机号/邮箱已被注册！";
            }
        }
    };
}
function getCookie(name) {
    const match = document.cookie.match(new RegExp('(^| )' + name + '=([^;]+)'));
    return match ? decodeURIComponent(match[2]) : null;
}