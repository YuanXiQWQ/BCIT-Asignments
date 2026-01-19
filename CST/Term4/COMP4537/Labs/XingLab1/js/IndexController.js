import {STRINGS} from "../lang/messages/en/strings.js";
import {UserInterface} from "./UserInterface.js";

export class IndexController {
  constructor() {
    this.ui = new UserInterface(document.body);
  }

  init() {
    this.ui.setAppTitle();

    // Init page content
    document.getElementById("title").textContent = STRINGS.INDEX_TITLE;
    document.getElementById("studentName").textContent = STRINGS.INDEX_STUDENT_NAME;
    document.getElementById("writerLink").textContent = STRINGS.WRITER;
    document.getElementById("readerLink").textContent = STRINGS.READER;
  }
}

const index = new IndexController();
index.init();
