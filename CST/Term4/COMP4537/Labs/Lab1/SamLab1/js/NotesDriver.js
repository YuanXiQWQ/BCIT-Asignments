import { IDS } from "./domIds.js";
import { LocalStorageManager } from "./LocalStorageManager.js";
import { Note } from "./Note.js";
import { Time } from "./Time.js";
import {
	ALL_REMOVE_BUTTONS_SELECTOR,
	ALL_TEXTAREAS_SELECTOR,
} from "./selectors.js";
import { MessageManager } from "./MessageManager.js";

const NO_ITEM_FOUND_INDEX = -1;
const REMOVE_SINGLE_NOTE = 1;
const ID_INDEX = 1;
const REFRESH_INTERVAL_MS = 2000;
const NOTE_ID_DELIMITER = "-";

export class NotesDriver {
	static READ_MODE = "read";
	static WRITE_MODE = "write";

	constructor(mode = NotesDriver.READ_MODE) {
		this.mode = mode;
		this.notes = [];
		this.time = new Time();
		this.localStorageManager = new LocalStorageManager();
		this.messageManager = new MessageManager();
	}

	initializePage() {
		this.messageManager.initializePage(this.mode);
		this.renderContent();
		this.setupButtonListeners();
		this.setupInputListeners();
	}

	reRenderPage() {
		this.renderContent();
		if (this.mode === NotesDriver.WRITE_MODE) {
			this.setupRemoveButtonListeners();
			this.setupInputListeners();
		}
	}

	startIntervalReading() {
		setInterval(() => {
			this.time.setUpdatedAt();
			this.localStorageManager.writeTime(this.time);
			this.reRenderPage();
		}, REFRESH_INTERVAL_MS);
	}

	setupButtonListeners() {
		if (this.mode === NotesDriver.WRITE_MODE) {
			this.setupAddNoteButtonListeners();
			this.setupRemoveButtonListeners();
		}
	}

	setupInputListeners() {
		if (this.mode === NotesDriver.WRITE_MODE) {
			this.setupTextboxListeners();
		}
	}

	setupAddNoteButtonListeners() {
		document
			.getElementById(IDS.ADD_NOTE_BUTTON)
			.addEventListener("click", () => this.createNote());
	}

	setupRemoveButtonListeners() {
		const removeButtons = document.querySelectorAll(
			ALL_REMOVE_BUTTONS_SELECTOR,
		);

		for (const button of removeButtons) {
			if (button) {
				const noteId = this.getNoteId(button.id);
				button.addEventListener("click", () => this.removeNote(noteId));
			}
		}
	}

	setupTextboxListeners() {
		const textAreas = document.querySelectorAll(ALL_TEXTAREAS_SELECTOR);

		for (const textArea of textAreas) {
			if (textArea) {
				const noteId = this.getNoteId(textArea.id);

				textArea.addEventListener("blur", () => {
					const value = textArea.value;
					this.updateNote(noteId, value);
				});
			}
		}
	}

	syncNotesArray() {
		const notes = this.localStorageManager.readNotes();
		this.notes.length = 0;
		this.notes = notes;
	}

	syncTime() {
		this.time = this.localStorageManager.readTime();
	}

	renderContent() {
		this.renderNotes();
		this.renderTime();
	}

	renderNotes() {
		// ensure array is up to date
		this.syncNotesArray();

		// clear current notes
		document.getElementById(IDS.NOTES_DISPLAY).innerHTML = "";

		for (const note of this.notes) {
			if (note) {
				note.render(this.mode);
			}
		}
	}

	renderTime() {
		this.syncTime();

		document.getElementById(IDS.LATEST_REFRESH_TIME_SPAN).innerHTML = "";
		this.time.render(this.mode);
	}

	updateNote(id, content) {
		const note = this.getNoteById(id);

		if (note) {
			note.update(content);
			this.localStorageManager.writeNotes(this.notes);

			this.time.setStoredAt();
			this.localStorageManager.writeTime(this.time);

			this.reRenderPage();
		}
	}

	createNote(content = "") {
		const note = new Note(content);
		this.notes.push(note);
		this.localStorageManager.writeNotes(this.notes);

		this.time.setStoredAt();
		this.localStorageManager.writeTime(this.time);

		this.reRenderPage();
	}

	removeNote(id) {
		const noteIndex = this.notes.findIndex((note) => note.id === id);

		if (noteIndex > NO_ITEM_FOUND_INDEX) {
			this.notes.splice(noteIndex, REMOVE_SINGLE_NOTE);
			this.localStorageManager.writeNotes(this.notes);

			this.time.setStoredAt();
			this.localStorageManager.writeTime(this.time);

			this.reRenderPage();
		}
	}

	getNoteById(id) {
		return this.notes.find((note) => note.id === id);
	}

	getNoteId(id) {
		const noteId = id.split(NOTE_ID_DELIMITER)[ID_INDEX];
		return Number.parseInt(noteId);
	}
}
