/**
 * ChatGPT used to:
 * - Generate part of the stylesheet (.noteText{}, .readOnly{})
 * - Provide guidance on the load implementation in ReaderController.js and WriterController.js
 * - Verify assignment requirements after completion
 * - Recall CSS properties and their functions
 * - Troubleshoot deployment issues
 */
export class NotesManager {
  constructor(storageKey, timestampValue) {
    this.storageKey = storageKey;
    this.timestampValue = timestampValue;
    this.notes = [];
  }

  /**
   * Load notes from localStorage
   */
  load() {
    const data = localStorage.getItem(this.storageKey);
    return JSON.parse(data) || [];
  }

  /**
   * Add note
   */
  add(noteData) {
    this.notes.push(noteData);
  }

  /**
   * Remove note
   */
  remove(index) {
    this.notes[index].element.remove();
    this.notes.splice(index, 1);
  }

  /**
   * Save notes to localStorage
   */
  save() {
    const notesData = this.notes.map((note) => note.textarea.value);
    localStorage.setItem(this.storageKey, JSON.stringify(notesData));
    this.timestampValue.textContent = new Date().toLocaleTimeString();
  }

  /**
   * Autosave notes every 2s
   */
  autoSave() {
    setInterval(() => {
      this.save();
    }, 2000);
  }
}
