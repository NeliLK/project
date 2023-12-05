/* success-message.js */

document.addEventListener('DOMContentLoaded', function () {
    // Set a timeout to hide the success message after 5 seconds (adjust as needed)
    setTimeout(function () {
        var successMessage = document.getElementById('successMessage');
        if (successMessage) {
            successMessage.style.display = 'none';
        }
    }, 5000);
});

