import {STRINGS} from "../lang/messages/en/strings.js";
import {UserInterface} from "./UserInterface.js";

// import {NotesManager} from "./NotesManager.js";

export class WriterController {
  constructor() {
    this.ui = new UserInterface(document.body);
    this.ui.setAppTitle();
    this.notesContainer = document.getElementById("notesContainer");
    this.timestampValue = document.getElementById("timestampValue");
    this.title = document.getElementById("title")
    this.addBtn = document.getElementById("addBtn");
    this.backBtn = document.getElementById("backBtn");
  }

  init() {
    this.title.textContent = STRINGS.WRITER;
    this.addBtn.textContent = STRINGS.ADD_BTN;
    this.backBtn.textContent = STRINGS.BACK_BTN;
    document.getElementById("timestampLabel").textContent = STRINGS.STORED_AT;
    this.timestampValue.textContent = STRINGS.SPACE;
    this.backBtn.addEventListener("click", () => {
      this.ui.showIndex();
    });


    // this.manager = new NotesManager("notes", notesContainer, timestamp);
    // this.manager.load();
    // this.manager.startAutoSave();

    // addBtn.addEventListener("click", () => {
    //   this.manager.addNote("");
    // });
  }

  updateTimestamp = () => {
    this.timestamp.textContent = new Date().toLocaleString();
  }
}

const controller = new WriterController();
controller.init();
