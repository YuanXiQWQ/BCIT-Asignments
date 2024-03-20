function ajaxGET(url, callback) {

    const xhr = new XMLHttpRequest();

    xhr.onload = function() {
        value = this.responseText;
        if (this.readyState == XMLHttpRequest.DONE && this.status == 200) {

            value = this.responseText;
            callback(this.responseText);

        } else {
            console.log(this.status);
        }
    }
    xhr.open("GET", url);
    xhr.send();

}

document.addEventListener('DOMContentLoaded', function (e) {
    ajaxGET("/navbar?format=html", function(data) {
        document.getElementById("ajax_navbar").innerHTML = data;
    });
});

document.addEventListener('DOMContentLoaded', function (e) {
    ajaxGET("/article_data?format=html", function(data) {
        document.getElementById("article_data").innerHTML = data;
    });
});

// FEATURED-TOP
document.addEventListener('DOMContentLoaded', function (e) {
    ajaxGET("/article_data?format=json", function(data) {
        let articleData = JSON.parse(data);
        let htmlContent = '';
        let startIndex = 0;
        let numIterations = 2;

        for (let i = startIndex; i < Math.min(startIndex + numIterations, articleData.length); i++) {
            htmlContent += `
                <div class='article-container1'>
                    <!-- Attribute #1: Image -->
                    <div class='card-image1'>
                        <img src='${articleData[i].img}' alt='img'/>
                    </div>
                    <div class='card-body'>
                        <!-- Attribute #2: Article Title -->
                        <h1 class='card-title'>${articleData[i].title}</h1>
                        <!-- Attribute #3: Text -->
                        <p class='card-text'>${articleData[i].text}</p>
                        <!-- Attribute #4: Date -->
                        <p class='card-date'>${articleData[i].date}</p>
                        <!-- Attribute #5: Article Number -->
                        <p class='card-date'>Article #${articleData[i].articleIndex}</p>
                    </div>
                </div>
            `;
        }
        document.getElementById("featured-top").innerHTML = htmlContent;
    });
});

// FEATURED-BOTTOM
document.addEventListener('DOMContentLoaded', function (e) {
    ajaxGET("/article_data?format=json", function(data) {
        let articleData = JSON.parse(data);
        let htmlContent = '';
        let startIndex = 2;
        let numIterations = 2;

        for (let i = startIndex; i < Math.min(startIndex + numIterations, articleData.length); i++) {
            htmlContent += `
                <div class='article-container2'>
                    <!-- Attribute #1: Image -->
                    <div class='card-image2'>
                        <img src='${articleData[i].img}' alt='img'/>
                    </div>
                    <div class='card-body'>
                        <!-- Attribute #2: Article Title -->
                        <h1 class='card-title'>${articleData[i].title}</h1>
                        <!-- Attribute #3: Text -->
                        <p class='card-text'>${articleData[i].text}</p>
                        <!-- Attribute #4: Date -->
                        <p class='card-date'>${articleData[i].date}</p>
                        <!-- Attribute #5: Article Number -->
                        <p class='card-date'>Article #${articleData[i].articleIndex}</p>
                    </div>
                </div>
            `;
        }
        document.getElementById("featured-bottom").innerHTML = htmlContent;
    });
});

// COMMUNITY-NEWS
document.addEventListener('DOMContentLoaded', function (e) {
    ajaxGET("/article_data?format=json", function(data) {
        let articleData = JSON.parse(data);
        let htmlContent = '';
        let startIndex = 4;
        let numIterations = 6;

        for (let i = startIndex; i < Math.min(startIndex + numIterations, articleData.length); i++) {
            htmlContent += `
                <div class='article-container3'>
                    <!-- Attribute #1: Image -->
                    <div class='card-image2'>
                        <img src='${articleData[i].img}' alt='img'/>
                    </div>
                    <div class='card-body'>
                        <!-- Attribute #2: Article Title -->
                        <h1 class='card-title'>${articleData[i].title}</h1>
                        <!-- Attribute #3: Text -->
                        <p class='card-text'>${articleData[i].text}</p>
                        <!-- Attribute #4: Date -->
                        <p class='card-date'>${articleData[i].date}</p>
                        <!-- Attribute #5: Article Number -->
                        <p class='card-date'>Article #${articleData[i].articleIndex}</p>
                    </div>
                </div>
            `;
        }
        document.getElementById("community").innerHTML = htmlContent;
    });
});

document.addEventListener('DOMContentLoaded', function (e) {
    ajaxGET("/footer?format=html", function(data) {
        document.getElementById("ajax_footer").innerHTML = data;
    });
});