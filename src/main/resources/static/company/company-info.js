window.onload = function () {
    fetch("../autoLogin")
        .then(response => response.json())
        .then(data => {
            if (data.status !== "success") {
                // 登录状态失效就跳回登录页
                window.location.href = "login.html";
            } else {
                // 可以做欢迎展示
                console.log("欢迎回来，用户ID:", data.user);
                // 可选展示用户名
                // document.getElementById("welcome").innerText = "欢迎，" + data.user;
            }
        });
}
function loadProducts() {
    fetch('https://example.com/api/products')  // 远端服务器 API
        .then(response => response.json())
        .then(data => {
            const container = document.getElementById('product-list');
            container.innerHTML = '';
            data.forEach(product => {
                const productCard = document.createElement('div');
                productCard.classList.add('product-card');
                productCard.innerHTML = `
                            <img src="${product.image}" alt="${product.name}">
                            <div class="product-title">${product.name}</div>
                            <div class="product-price">$${product.price}</div>
                        `;
                container.appendChild(productCard);
            });
        })
        .catch(error => console.error('Error fetching products:', error));
}

window.onload = loadProducts;