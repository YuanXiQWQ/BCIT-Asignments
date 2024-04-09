document.addEventListener('DOMContentLoaded', function () {
    fetch('/get-posts')
        .then(response => response.json())
        .then(posts => {
            const postsContainer = document.querySelector('.post-container');
            postsContainer.innerHTML = '';
            posts.forEach(post => {
                const postLink = document.createElement('a');
                postLink.href = `/Post?postId=${post.id}`;
                postLink.classList.add('post-link', 'black', 'undecorated');

                const postElement = document.createElement('div');
                postElement.classList.add('posts');

                // 使用从后端返回的用户名
                const postUser = document.createElement('div');
                postUser.classList.add('post-details', 'postUser');
                postUser.textContent = post.user_name;

                // 帖子标题
                const postTitle = document.createElement('div');
                postTitle.classList.add('post-details', 'postTitle');
                postTitle.textContent = post.post_title;

                // 帖子文本
                const postText = document.createElement('div');
                postText.classList.add('post-details', 'postText');
                postText.textContent = post.post_text;

                // 帖子信息（日期、时间、浏览量）
                const postInfo = document.createElement('div');
                postInfo.classList.add('post-details', 'postInfo');

                const postDateAndTime = document.createElement('div');
                postDateAndTime.classList.add('postDateAndTime');

                const postDate = document.createElement('div');
                postDate.classList.add('postDate');
                postDate.textContent = new Date(post.post_date).toLocaleDateString();

                const postTime = document.createElement('div');
                postTime.classList.add('postTime');
                postTime.textContent = post.post_time;

                const postViews = document.createElement('div');
                postViews.classList.add('postViews');
                postViews.innerHTML = `<span>views:</span> ${post.post_views}`;

                // 构建帖子信息结构
                postDateAndTime.appendChild(postDate);
                postDateAndTime.appendChild(postTime);
                postInfo.appendChild(postDateAndTime);
                postInfo.appendChild(postViews);

                // 将所有部分添加到帖子容器中
                postElement.appendChild(postUser);
                postElement.appendChild(postTitle);
                postElement.appendChild(postText);
                postElement.appendChild(postInfo);

                // 将帖子容器添加到页面中
                postLink.appendChild(postElement);
                postsContainer.appendChild(postLink);
            });
        })
        .catch(error => console.error('Failed to load posts:', error));
});
