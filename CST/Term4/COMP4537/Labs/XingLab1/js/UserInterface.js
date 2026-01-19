/**
 * ChatGPT used to:
 * - Generate part of the stylesheet (.noteText{}, .readOnly{})
 * - Provide guidance on the load implementation in ReaderController.js and WriterController.js
 * - Verify assignment requirements after completion
 * - Recall CSS properties and their functions
 * - Troubleshoot deployment issues
 */
import {STRINGS} from "../lang/messages/en/strings.js";

export class UserInterface {
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