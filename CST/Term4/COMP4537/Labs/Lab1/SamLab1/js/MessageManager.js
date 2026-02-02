import { USER_STRINGS } from "../lang/messages/en/user.js";
import { IDS } from "./domIds.js";
import { NotesDriver } from "./NotesDriver.js";
import { NOTE_LABEL } from "./selectors.js";

const WRITER_IDS = IDS.WRITER_PAGE_IDS;
const READER_IDS = IDS.READER_PAGE_IDS;
const LANDING_IDS = IDS.LANDING_PAGE_IDS;

export class MessageManager {
	initializePage(mode) {
		switch (mode) {
			case NotesDriver.READ_MODE:
				MessageManager.injectReaderStrings();
				break;
			case NotesDriver.WRITE_MODE:
				MessageManager.injectWriterStrings();
				break;
			default:
				MessageManager.injectLandingStrings();
				break;
		}
	}

	static injectLandingStrings() {
		document.getElementById(LANDING_IDS.LANDING_TITLE).textContent =
			USER_STRINGS.LANDING_TITLE;
		document.getElementById(LANDING_IDS.WRITER_LINK).textContent =
			USER_STRINGS.WRITER;
		document.getElementById(LANDING_IDS.READER_LINK).textContent =
			USER_STRINGS.READER;
	}

	static injectWriterStrings() {
		document.getElementById(WRITER_IDS.TITLE).textContent = USER_STRINGS.WRITER;
		document.getElementById(WRITER_IDS.SAVED_AT_LABEL).textContent =
			USER_STRINGS.SAVED_AT;
		document.getElementById(IDS.LATEST_REFRESH_TIME_SPAN).textContent =
			USER_STRINGS.NA;
		document.getElementById(WRITER_IDS.ADD_NOTE).textContent = USER_STRINGS.ADD;
		document.getElementById(IDS.BACK_BUTTON).textContent = USER_STRINGS.BACK;

		// Set label in template
		const template = document.getElementById(IDS.WRITER_NOTE_TEMPLATE);
		if (template) {
			const label = template.content.querySelector(NOTE_LABEL);
			if (label) label.textContent = USER_STRINGS.NOTE;
			const textarea = template.content.querySelector("textarea");
			if (textarea) textarea.placeholder = USER_STRINGS.NOTE;
            const removeButton = template.content.querySelector("button");
            if (removeButton) removeButton.textContent = USER_STRINGS.REMOVE;
		}
	}

	static injectReaderStrings() {
		document.getElementById(READER_IDS.TITLE).textContent = USER_STRINGS.READER;
		document.getElementById(READER_IDS.UPDATED_AT_LABEL).textContent =
			USER_STRINGS.UPDATED_AT;
		document.getElementById(IDS.LATEST_REFRESH_TIME_SPAN).textContent =
			USER_STRINGS.NA;
		document.getElementById(IDS.BACK_BUTTON).textContent = USER_STRINGS.BACK;

		// Set label in template
		const template = document.getElementById(IDS.READER_NOTE_TEMPLATE);
		if (template) {
			const label = template.content.querySelector(NOTE_LABEL);
			if (label) label.textContent = USER_STRINGS.NOTE;
			const textarea = template.content.querySelector("textarea");
			if (textarea) textarea.placeholder = USER_STRINGS.NOTE;
		}
	}
}
