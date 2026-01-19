/**
 * ChatGPT used to:
 * - Generate part of the stylesheet (.noteText{}, .readOnly{})
 * - Provide guidance on the load implementation in ReaderController.js and WriterController.js
 * - Verify assignment requirements after completion
 * - Recall CSS properties and their functions
 * - Troubleshoot deployment issues
 */
import {STRINGS} from "../lang/messages/en/strings.js";
import {UserInterface} from "./UserInterface.js";

export class IndexController {
  init() {
    // Init Title
    this.ui = new UserInterface();
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
