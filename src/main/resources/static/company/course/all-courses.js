function getCookie(name) {
    const match = document.cookie.match(new RegExp('(^| )' + name + '=([^;]+)'));
    return match ? decodeURIComponent(match[2]) : null;
}

function previewVideo(url, button) {
    const video = document.createElement("video");
    video.src = url;
    video.controls = true;
    video.width = 400;

    const parent = button.parentElement;

    // 防止重复插入或切换显示
    const existing = parent.querySelector("video");
    if (existing) {
        existing.remove();
    } else {
        parent.appendChild(video);
    }
}



function buyCourse(courseId) {
    const userId = getCookie("login");
    if (!userId) {
        alert("未登录，请先登录！");
        location.href = "../login/login.html";
        return;
    }
    fetch(`/api/user-courses/buy?courseId=${courseId}&login=${userId}`, { method: 'POST' })
        .then(res => res.text())
        .then(msg => alert(msg))
        .catch(err => alert('购买失败'));
}

fetch('/api/courses')
    .then(res => res.json())
    .then(courses => {
        const container = document.getElementById('course-list');
        courses.forEach(course => {
            const rawUrl = course.videoUrl || "";
            const prefix = "[video]";
            const rawTrimmed = rawUrl.trim();
            const realUrl = rawTrimmed.toLowerCase().startsWith(prefix)
                ? rawTrimmed.substring(prefix.length)
                : rawTrimmed;
            console.log(`realUrl: ${realUrl}`);
            console.log(`${realUrl}`);
            const div = document.createElement('div');
            div.className = 'course-card';
            div.innerHTML = `
                <img src="${course.imageUrl}" alt="课程图片">
                <h3>${course.name}</h3>
                <p>${course.shortDescription}</p>
                <p>价格：￥${course.price}</p>
                <button onclick="buyCourse(${course.id})">购买</button>
                <button onclick="previewVideo('${realUrl}', this)">预览</button>
            `;
            container.appendChild(div);
        });
    })
    .catch(err => {
        console.error('课程加载失败:', err);
        alert('无法加载课程，请稍后重试');
    });
