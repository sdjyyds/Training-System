let isPhoneRegister = true;

function sendVerification(){
    let code = Math.floor(100000 + Math.random() * 900000); // 生成6位验证码
    document.getElementById("verification").innerText = code.toString();
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

function register() {
    let phone = document.getElementById('phone').value;
    let email = document.getElementById('email').value;
    let password = document.getElementById('password').value;

    if (isPhoneRegister && document.getElementById('logicCheck').innerText !== "") {
        alert("请输入正确的手机号！");
        return;
    }
    if (!isPhoneRegister && document.getElementById('logicCheckEmail').innerText !== "") {
        alert("请输入正确的邮箱！");
        return;
    }
    if (document.getElementById('logicCheckPassword').innerText !== "") {
        alert("请输入符合规则的密码！");
        return;
    }

    let data = isPhoneRegister ? `phone=${phone}&password=${password}` : `email=${email}&password=${password}`;
    let url = isPhoneRegister ? "/registerByPhone" : "/registerByEmail";

    let xhr = new XMLHttpRequest();
    xhr.open('POST', url, true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.send(data);

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            let response = JSON.parse(xhr.responseText);
            if (xhr.status === 200 && response.success) {
                window.location.href = "../login/login.html";
            } else {
                document.getElementById('repeatCheck').innerText = response.message || "该手机号/邮箱已被注册！";
            }
        }
    };
}