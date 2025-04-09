function validateAccount() {
    const accountInput = document.getElementById('account');
    const accountError = document.getElementById('account-error');
    const account = accountInput.value.trim();

    const phoneRegex = /^1[3-9]\d{9}$/;  // 中国大陆 11 位手机号
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;  // 邮箱格式

    if (phoneRegex.test(account) || emailRegex.test(account)) {
        accountError.style.display = 'none';
        return true;
    } else {
        accountError.style.display = 'block';
        return false;
    }
}

function validatePassword() {
    const passwordInput = document.getElementById('password');
    const passwordError = document.getElementById('password-error');
    const password = passwordInput.value;

    const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}$/;  // 至少6个字符，包含字母和数字

    if (passwordRegex.test(password)) {
        passwordError.style.display = 'none';
        return true;
    } else {
        passwordError.style.display = 'block';
        return false;
    }
}

// 页面加载时检查 sessionId，如果有效则自动跳转
window.onload = function () {
    fetch("../autoLogin")
        .then(response => response.json())
        .then(data => {
            if (data.status === "success") {
                window.location.href = data.redirect;
            }
        });
};