//Imports
const express = require("express");
const path = require("node:path");
const app = express();
app.use(express.json());
const fs = require("node:fs");

//Server

//Static files
app.use("/scripts", express.static(path.resolve(__dirname, "./public/scripts")));
app.use("/styles", express.static(path.resolve(__dirname, "./public/styles")));
app.use("/images", express.static(path.resolve(__dirname, "./public/images")));

//Functions
/*Pages*/
app.get("/index", (req, res) => {
    let doc = fs.readFileSync(path.resolve(__dirname, "./app/html/index.html"), "utf8");
    res.send(doc);
});
//
// app.get("/Redstone", (req, res) => {
//     let doc = fs.readFileSync(path.resolve(__dirname, "./app/html/Redstone.html"), "utf8");
//     res.send(doc);
// });
//
app.get("/Construction", (req, res) => {
    let doc = fs.readFileSync(path.resolve(__dirname, "./app/html/Construction.html"), "utf8");
    res.send(doc);
});
//
// app.get("/about", (req, res) => {
//     let doc = fs.readFileSync(path.resolve(__dirname, "./app/html/about.html"), "utf8");
//     res.send(doc);
// });

/*Snippets of HTML*/
app.get("/get-header", (req, res) => {
    res.sendFile(path.resolve(__dirname, "./app/data/header.html"));
});

app.get("/get-navbar", (req, res) => {
    res.sendFile(path.resolve(__dirname, "./app/data/navbar.html"));
});

app.get("/get-footer", (req, res) => {
    res.sendFile(path.resolve(__dirname, "./app/data/footer.html"));
});


/*Construction.html
* */

/*Snippets of HTML*/
app.get("/get-constructionList", (req, res) => {
    res.sendFile(path.join(__dirname, "./app/data/constructionList.html"));
});

app.get("/get-constructionList-data", (req, res) => {
    res.sendFile(path.join(__dirname, "./app/data/constructionList_data.json"));
});

/*Interactions*/
app.get("/languages", (req, res) => {
    const languageDir = path.join(__dirname, "./app/data/language");
    fs.readdir(languageDir, (err, files) => {
        if (err) {
            console.error("Could not list the directory.", err);
            res.status(500).send("Server error when listing languages");
            return;
        }
        const languages = files.map(file => {
            const code = file.split(".")[0];
            const name = code; // 简单示例，直接使用code作为显示名称
            return {code, name};
        });
        res.json(languages);
    });
});

app.get("/languages/:lang", (req, res) => {
    const lang = req.params.lang;
    const languageFilePath = path.resolve(__dirname, `./app/data/language/${lang}.json`);
    if (fs.existsSync(languageFilePath)) {
        res.sendFile(languageFilePath);
    } else {
        res.status(404).send({error: "Language file not found."});
    }
});

app.post("/add-construction", (req, res) => {
    const newBuilding = req.body;
    const filePath = path.join(__dirname, "./app/data/constructionList_data.json");

    fs.readFile(filePath, (err, data) => {
        if (err) {
            console.error("Error reading construction list data:", err);
            return res.status(500).send("Error reading construction data");
        }

        const constructionData = JSON.parse(data.toString());
        constructionData.buildings.push(newBuilding);

        fs.writeFile(filePath, JSON.stringify(constructionData, null, 2), (err) => {
            if (err) {
                console.error("Error writing construction list data:", err);
                return res.status(500).send("Error updating construction data");
            }

            res.status(200).send("Construction added successfully");
        });
    });
});


//Run Server
app.listen(8000);
