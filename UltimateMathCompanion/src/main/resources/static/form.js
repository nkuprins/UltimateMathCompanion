
const createErrorAlert = function () {
    alert("Invalid format of data\n" +
    "Possible mistakes:\n" +
        "1. The text is empty\n" +
        "2. The text contains invalid symbols like: 'A','$','!'\n" +
        "3. The text contains has DECIMAL numbers but it should be INTEGERS.\n" +
        "4. The text does not have enough spaces or they are redundant");
}
