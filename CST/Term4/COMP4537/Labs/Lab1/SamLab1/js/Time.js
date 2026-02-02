import { IDS } from "./domIds.js";
import { NotesDriver } from "./NotesDriver.js";

export class Time {
	constructor(updatedAt = null, storedAt = null) {
		this.updatedAt = updatedAt || "N/A";
		this.storedAt = storedAt || "N/A";
	}

	setUpdatedAt() {
		this.updatedAt = new Date().toLocaleTimeString();
	}

	setStoredAt() {
		this.storedAt = new Date().toLocaleTimeString();
	}

	render(mode = NotesDriver.READ_MODE) {
		const timeSpan = document.getElementById(IDS.LATEST_REFRESH_TIME_SPAN);

		timeSpan.innerText =
			mode === NotesDriver.READ_MODE ? this.updatedAt : this.storedAt;
	}
}
