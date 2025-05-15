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
const userId = getCookie("login");
if (!userId) {
    alert("未登录，请先登录！");
    location.href = "../login/login.html";
} else {
    fetch(`/api/user-courses/my?login=${userId}`)
        .then(res => res.json())
        .then(courses => {
            const container = document.getElementById('my-course-list');
            courses.forEach(course => {
                const rawUrl = course.videoUrl || "";
                const realUrl = rawUrl.startsWith("[video]")
                    ? rawUrl.replace("[video]", "")
                    : rawUrl;
                const div = document.createElement('div');
                div.className = 'course-card';
                div.innerHTML = `
                    <h3>${course.name}</h3>
                    <p>${course.shortDescription}</p>
                    <button onclick="previewVideo('${realUrl}', this)">播放课程</button>
                `;
                container.appendChild(div);
            });
        })
        .catch(err => {
            console.error('加载课程失败:', err);
            alert('无法加载课程，请稍后重试');
        });
}
