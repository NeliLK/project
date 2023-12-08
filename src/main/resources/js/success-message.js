/* success-message.js */

document.addEventListener('DOMContentLoaded', function () {
    setTimeout(function () {
        var successMessage = document.getElementById('successMessage');
        if (successMessage) {
            successMessage.style.display = 'none';
        }
    }, 5000);
});

