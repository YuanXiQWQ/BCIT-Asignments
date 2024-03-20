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
    ajaxGET("/footer?format=html", function(data) {
        document.getElementById("ajax_footer").innerHTML = data;
    });
});

document.addEventListener('DOMContentLoaded', function (e) {
    ajaxGET("/ajax_ranking?format=json", function (data) {

        let parsedData = JSON.parse(data);

        let str = "<table>";
        str += "<tr>"
                + "<th>Rank</th>"
                + "<th>Character</th>"
                + "<th>Character Name</th>"
                + "<th>World</th>"
                + "<th>Job</th>"
                + "<th>Level</th>"
                + "</tr>"
        for(let i = 0; i < parsedData.length; i++) {
            let item = parsedData[i];
            str += "<tr><td>" + item["rank"] + "</td>"
                + "<td><img alt='img' src='" + item["img"] + "'/></td>"
                + "<td>" + item["name"]+ "</td>"
                + "<td><img alt='img' src='" + item["world"] + "'/></td>"
                + "<td><img alt='img' src='" + item["job"] + "'/></td>"
                + "<td>" + item["level"] + "</td></tr>";
        }
        str += "</table>";

        document.getElementById("ajax_ranking").innerHTML = str;
    });
});