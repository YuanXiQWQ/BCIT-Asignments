import { Note } from "./Note.js";
import { Time } from "./Time.js";

const NOTES_LOCAL_STORAGE_KEY = "notes";
const TIME_LOCAL_STORAGE_KEY = "time";

export class LocalStorageManager {
	writeNotes(notes) {
		const serializedNotes = JSON.stringify(notes || []);
		localStorage.setItem(NOTES_LOCAL_STORAGE_KEY, serializedNotes);
	}

	readNotes() {
		const serializedNotes = localStorage.getItem(NOTES_LOCAL_STORAGE_KEY);
		const deserializedNotes = JSON.parse(serializedNotes) || [];
		const notes = this.getConstructedNotes(deserializedNotes);

		return notes;
	}

	writeTime(time) {
		const serializedTime = JSON.stringify(time || {});
		localStorage.setItem(TIME_LOCAL_STORAGE_KEY, serializedTime);
	}

	readTime() {
		const serializedTime = localStorage.getItem(TIME_LOCAL_STORAGE_KEY);
		const deserializedTime = JSON.parse(serializedTime) || {};
		const time = this.getConstructedTime(deserializedTime);
		return time;
	}

	getConstructedNotes(deserializedNotes) {
		// make sure notes are the Note object
		const notes = [];

		for (const note of deserializedNotes) {
			const noteId = Number.parseInt(note.id);
			notes.push(new Note(note.content, noteId));
		}

		return notes || [];
	}

	getConstructedTime(deserializedTime) {
		// make sure time is the Time object
		return new Time(deserializedTime.updatedAt, deserializedTime.storedAt);
	}
}
