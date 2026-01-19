import {STRINGS} from "../lang/messages/en/strings.js";

export class UserInterface {
  constructor(rootElement) {
    this.root = rootElement;
  }

  showMessage(msg) {
    this.root.textContent = msg;
  }

  /**
   * Set app title
   */
  setAppTitle() {
    document.title = STRINGS.APP_TITLE;
  }

  /**
   * Show index page
   */
  showIndex() {
    window.location.href = "index.html";
  }
}