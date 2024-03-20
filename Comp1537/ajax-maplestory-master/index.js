const express = require("express");
const app = express();
app.use(express.json());
const fs = require("fs");

app.use("/js", express.static("./public/js"));
app.use("/css", express.static("./public/css"));
app.use("/img", express.static("./public/img"));

app.get("/", function (req, res) {
    let doc = fs.readFileSync("./app/html/main.html", "utf8");
    res.send(doc);
});

app.get("/rankings", function (req, res) {
    let doc = fs.readFileSync("./app/html/rankings.html", "utf8");
    res.send(doc);
});

app.get("/navbar", function(req, res) {
    let formatOfResponse = req.query["format"];
    if (formatOfResponse == "html") {
        res.setHeader("Content-Type", "text/html");
        res.send(fs.readFileSync("./app/data/navbar.html", "utf8"));
    }
});

app.get("/article_data", function(req, res) {
    let formatOfResponse = req.query["format"];
    if (formatOfResponse == "html") {
        res.setHeader("Content-Type", "text/html");
        res.send(fs.readFileSync("./app/data/article_data.html", "utf8"));
    } else if (formatOfResponse == "json") {
        let doc = fs.readFileSync("./app/data/article_data.json", "utf8");
        res.setHeader("Content-Type", "application/json");
        res.send(doc);
    } 
});

app.get("/footer", function(req, res) {
    let formatOfResponse = req.query["format"];
    if (formatOfResponse == "html") {
        res.setHeader("Content-Type", "text/html");
        res.send(fs.readFileSync("./app/data/footer.html", "utf8"));
    }
});

app.get("/ajax_ranking", function (req, res) {
    let doc = fs.readFileSync("./app/data/ranking_data.json", "utf8");
    let formatOfResponse = req.query["format"];
    if (formatOfResponse == "json") {
        let doc = fs.readFileSync("./app/data/ranking_data.json", "utf8");
        res.setHeader("Content-Type", "application/json");
        res.send(doc);
    } 
});

app.use(function (req, res, next) {
    res.status(404).send("<html><head><title>Page not found!</title></head><body><p>Nothing here.</p></body></html>");
});

// RUN SERVER
let port = 8000;
app.listen(port, function () {
    console.log("Listening ON port: " + port + ".");
});
