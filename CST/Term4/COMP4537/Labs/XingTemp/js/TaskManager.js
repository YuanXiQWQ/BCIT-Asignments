import { STRINGS } from "../lang/messages/en/user.js";

export class TaskManager {
    constructor() {
        this.tasks = [];
    }

    addTask(text) {
        if (!text) {
            throw new Error(STRINGS.ERROR_EMPTY);
        }
        this.tasks.push(text);
    }

    initialize() {
        console.log("TaskManager ready");
    }
}