import { NotesDriver } from "./NotesDriver.js";

const notesDriver = new NotesDriver(NotesDriver.READ_MODE);
notesDriver.initializePage();
notesDriver.startIntervalReading();
