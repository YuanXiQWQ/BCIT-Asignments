//Imports
const express = require("express");
const app = express();
const mysql = require('mysql2/promise');
const path = require("node:path");
const fs = require("node:fs");
app.use(express.json());

//Static files
app.use("/images", express.static(path.resolve(__dirname, "./public/images")));
app.use("/scripts", express.static(path.resolve(__dirname, "./public/scripts")));
app.use("/styles", express.static(path.resolve(__dirname, "./public/styles")));

//Functions
/*Pages*/
app.get("/", (req, res) => {
    res.sendFile(path.resolve(__dirname, "./app/html/index.html"));
});

//
// app.get("/Redstone", (req, res) => {
//     res.sendFile(path.resolve(__dirname, "./app/html/redstone.html"));
// });
//
app.get("/Construction", (req, res) => {
    res.sendFile(path.resolve(__dirname, "./app/html/construction.html"));
});
//
// app.get("/about", (req, res) => {
//     res.sendFile(path.resolve(__dirname, "./app/html/about.html"));
// });

app.get("/login", (req, res) => {
    let filePath = user ? "./app/html/profile.html" : "./app/html/login.html";
    res.sendFile(path.resolve(__dirname, filePath));
});

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


/*construction.html
* */

/*Snippets of HTML*/
app.get("/get-constructionList", (req, res) => {
    res.sendFile(path.resolve(__dirname, "./app/data/constructionList.html"));
});

app.get("/get-constructionList-data", (req, res) => {
    res.sendFile(path.resolve(__dirname, "./app/data/constructionList_data.json"));
});

/*Interactions*/
app.get("/languages", (req, res) => {
    const languageDir = path.resolve(__dirname, "./app/data/language");
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
    const filePath = path.resolve(__dirname, "./app/data/constructionList_data.json");

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


/*Database*/
const connection = mysql.createConnection({
    host: "localhost",
    user: "root",
    password: "Xjr@66773738",
    database: "assignment6"
});

let user = false;

app.post('/login', async (req, res) => {
    const {username, password} = req.body;

    try {
        const [rows] = await connection.execute('SELECT * FROM a01354731_user WHERE user_name = ? AND password = ?', [username, password]);
        if (rows.length > 0) {
            req.session.loggedin = user = true;
            req.session.username = username;
            res.redirect('/profile');
        } else {
            res.send('Username or Password is incorrect.');
        }
    } catch (error) {
        console.error('Database error', error);
        res.send('Error while connecting to database');
    } finally {
        await connection.end();
    }
});


//Run Server
app.listen(8000);
console.log("Server running on port 8000");
