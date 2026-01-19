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
import {NotesManager} from "./NotesManager.js";

export class WriterController {
  constructor() {
    this.noteListContainer = document.querySelector(".noteListContainer");
    this.timestampValue = document.getElementById("timestampValue");
    this.addBtn = document.getElementById("addBtn");
    this.backBtn = document.getElementById("backBtn");
  }

  init() {
    // Init Title
    this.ui = new UserInterface();
    this.ui.setAppTitle();

    // Init page content
    document.getElementById("title").textContent = STRINGS.WRITER;
    this.addBtn.textContent = STRINGS.ADD_BTN;
    this.backBtn.textContent = STRINGS.BACK_BTN;
    document.getElementById("timestampLabel").textContent = STRINGS.STORED_AT;
    this.timestampValue.textContent = STRINGS.SPACE;

    // Init manager
    this.manager = new NotesManager("notes", this.timestampValue);
    const notesData = this.manager.load();
    notesData.forEach((content) => {
      this.addNote(content);
    });
    this.manager.autoSave();

    // Event listeners
    this.backBtn.addEventListener("click", () => {
      this.ui.showIndex();
    });
    this.addBtn.addEventListener("click", () => {
      this.addNote("");
    });
  }

  /**
   * Create element and register to manager
   */
  addNote(content) {
    const noteData = {
      content: content,
      element: null,
      textarea: null
    };
    const element = this.createNoteElement(noteData);
    noteData.element = element;
    this.manager.add(noteData);
    this.noteListContainer.appendChild(element);
  }


  /**
   * Create:
   * <div class="notesContainer">
   *   <textarea class="noteText">content</textarea>
   *   <button class="button removeBtn">Remove</button>
   * </div>
   */
  createNoteElement(noteData) {
    const container = document.createElement("div");
    container.className = "notesContainer";

    const textarea = document.createElement("textarea");
    textarea.className = "noteText";
    textarea.value = noteData.content;
    noteData.textarea = textarea;

    const removeBtn = document.createElement("button");
    removeBtn.className = "button removeBtn";
    removeBtn.textContent = STRINGS.REMOVE_BTN;
    removeBtn.addEventListener("click", () => {
      const currentIndex = this.manager.notes.indexOf(noteData);
      this.manager.remove(currentIndex);
    });

    container.appendChild(textarea);
    container.appendChild(removeBtn);
    return container;
  }
}

const writer = new WriterController();
writer.init();
