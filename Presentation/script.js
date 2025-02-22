document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('registerForm').addEventListener('submit', function(event) {
        event.preventDefault();

        const formData = {
            name: document.getElementById('name').value,
            email: document.getElementById('email').value,
            phone: document.getElementById('phone').value,
            password: document.getElementById('password').value,
            role: document.getElementById('role').value
        };

        console.log(formData); // Log the form data to verify it's correct

        fetch('http://localhost:8080/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        })
        .then(response => response.text()) // Expect plain text response
        .then(data => {
            alert(data); // Show the plain text message from the response
            if (data === "Registered Successfully") {
                // Redirect to login page after successful registration
                window.location.href = "http://127.0.0.1:5500/Presentation/login.html";
            }
        })
        .catch(error => console.error('Error:', error));
    });
});
