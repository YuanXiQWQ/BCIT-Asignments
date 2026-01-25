import { IDS } from "./domIds.js";
import { NotesDriver } from "./NotesDriver.js";

const RANDOM_ID_MULTIPLE = 1000000;

export class Note {
	constructor(content, id = null) {
		this.id = id || Note.getRandomId();
		this.content = content;
	}

	update(content) {
		this.content = content;
	}

	render(mode = NotesDriver.READ_MODE) {
		const notesDisplay = document.getElementById(IDS.NOTES_DISPLAY);
		const node =
			mode === NotesDriver.WRITE_MODE
				? this.getWritableNoteNode()
				: this.getReadOnlyNoteNode();

		notesDisplay.appendChild(node);
	}

	getWritableNoteNode() {
		const element = document.getElementById(IDS.WRITER_NOTE_TEMPLATE);
		const removeButton = element.content.querySelector("button");
		removeButton.id = IDS.REMOVE_NOTE_BUTTON_ID(this.id);
		return this.getNoteNodeFromTemplate(element);
	}

	getReadOnlyNoteNode() {
		const element = document.getElementById(IDS.READER_NOTE_TEMPLATE);
		return this.getNoteNodeFromTemplate(element);
	}

	getNoteNodeFromTemplate(template) {
		const noteElement = template.content.cloneNode(true);
		const textArea = noteElement.querySelector("textarea");
		const label = noteElement.querySelector("label");
		const div = noteElement.querySelector("div");

		div.id = IDS.NOTE_TILE_ID(this.id);
		textArea.id = IDS.NOTE_TEXTAREA_ID(this.id);
		label.htmlFor = IDS.NOTE_TEXTAREA_ID(this.id);
		textArea.value = this.content;

		return noteElement;
	}

	static getRandomId() {
		return `${Date.now()}${Math.floor(Math.random() * RANDOM_ID_MULTIPLE)}`;
	}
}
