const firebaseConfig = {
  apiKey: "AIzaSyB7nQLJaTHwx6lzjB97-GIeIrR1i8zTPGw",
  authDomain: "comp1800-20240220.firebaseapp.com",
  projectId: "comp1800-20240220",
  storageBucket: "comp1800-20240220.appspot.com",
  messagingSenderId: "898872850037",
  appId: "1:898872850037:web:86f47b6ec4b54976a06a3c"
};

const app = firebase.initializeApp(firebaseConfig);
const db = firebase.firestore();
const storage = firebase.storage();