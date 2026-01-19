import { STRINGS } from "../lang/messages/en/user.js";

export class UserInterface {
    constructor(rootElement) {
        this.root = rootElement;
    }

    showMessage(msg) {
        this.root.textContent = msg;
    }

    createStartButton() {
        const btn = document.createElement("button");
        btn.textContent = STRINGS.BTN_START;
        return btn;
    }
}