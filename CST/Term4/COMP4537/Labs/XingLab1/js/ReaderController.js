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

export class ReaderController {
  constructor() {
    this.noteListContainer = document.querySelector(".noteListContainer");
    this.timestampValue = document.getElementById("timestampValue");
    this.backBtn = document.getElementById("backBtn");
  }

  init() {
    // Init Title
    this.ui = new UserInterface();
    this.ui.setAppTitle();

    // Init page content
    document.getElementById("title").textContent = STRINGS.READER;
    this.backBtn.textContent = STRINGS.BACK_BTN;
    document.getElementById("timestampLabel").textContent = STRINGS.UPDATED_AT;
    this.timestampValue.textContent = STRINGS.SPACE;
    this.noteListContainer.textContent = STRINGS.SPACE;

    // Init manager
    this.manager = new NotesManager("notes", this.timestampValue);
    this.loadNotes();
    this.autoLoad();

    // Event listeners
    this.backBtn.addEventListener("click", () => {
      this.ui.showIndex();
    });
  }

  /**
   * Load notes
   */
  loadNotes() {
    this.noteListContainer.innerHTML = STRINGS.SPACE;
    const notesData = this.manager.load();
    notesData.forEach((content) => {
      const element = this.createNoteElement(content);
      this.noteListContainer.appendChild(element);
    });
    this.timestampValue.textContent = new Date().toLocaleTimeString();
  }

  /**
   * Create:
   * <div class="notesContainer">
   *   <div class="noteText readOnly">content</div>
   * </div>
   */
  createNoteElement(content) {
    const container = document.createElement("div");
    container.className = "notesContainer";

    const noteText = document.createElement("div");
    noteText.className = "noteText readOnly";
    noteText.textContent = content;

    container.appendChild(noteText);
    return container;
  }

  /**
   * Autoload notes every 2s
   */
  autoLoad() {
    setInterval(() => {
      this.loadNotes();
    }, 2000);
  }
}

const reader = new ReaderController();
reader.init();
