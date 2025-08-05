// Function to handle the search button action
function performSearch() {
    const searchQuery = document.getElementById("search-bar").value;
    const tableNumber = document.getElementById("table-number").value;

    if (!searchQuery && !tableNumber) {
        alert("Please enter a search query or table number.");
        return;
    }

    // Simulate a search operation (you can connect this to a backend later)
    alert(`Searching for: ${searchQuery}, Table Number: ${tableNumber}`);
}
