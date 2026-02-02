const MathModule = require("./modules/math");

class App {
  run() {
    const mathModule = new MathModule();

    const firstNumber = 123;
    const secondNumber = 321;

    const sumResult = mathModule.addTwoNumbers(firstNumber, secondNumber);
    const subtractResult = mathModule.subtractTwoNumbers(firstNumber, secondNumber);

    console.log(`Hello Jerry. The sum is ${sumResult}, the subtract is ${subtractResult}`);
  }
}

new App().run();
