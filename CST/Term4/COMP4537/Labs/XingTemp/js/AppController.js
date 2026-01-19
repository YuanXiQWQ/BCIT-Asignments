import { STRINGS } from "../lang/messages/en/user.js";

export class AppController {
    constructor(ui, manager) {
        this.ui = ui;
        this.manager = manager;
    }

    start() {
        this.ui.showMessage(STRINGS.APP_TITLE);
        this.manager.initialize();
    }
}