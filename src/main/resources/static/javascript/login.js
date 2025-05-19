document.addEventListener('DOMContentLoaded', function() {
    // Toggle password visibility
    const togglePassword = document.querySelector('.toggle-password');
    const passwordInput = document.getElementById('senhausuario');

    if (togglePassword && passwordInput) {
        togglePassword.addEventListener('click', function() {
            const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
            passwordInput.setAttribute('type', type);

            // Toggle icon
            this.innerHTML = type === 'password' ? '<i class="fas fa-eye"></i>' : '<i class="fas fa-eye-slash"></i>';
        });
    }

    // Form validation
    const loginForm = document.querySelector('.login-form');
    if (loginForm) {
        loginForm.addEventListener('submit', function(e) {
            const email = document.getElementById('emailusuario');
            const password = document.getElementById('senhausuario');
            let isValid = true;

            // Clear previous errors
            document.querySelectorAll('.form-group').forEach(group => {
                group.classList.remove('error');
            });

            // Validate email
            if (!email.value) {
                email.closest('.form-group').classList.add('error');
                isValid = false;
            }

            // Validate password
            if (!password.value) {
                password.closest('.form-group').classList.add('error');
                isValid = false;
            }

            if (!isValid) {
                e.preventDefault();

                // Show error message if not already shown
                if (!document.querySelector('.alert-error')) {
                    const alertDiv = document.createElement('div');
                    alertDiv.className = 'alert alert-error';
                    alertDiv.innerHTML = '<i class="fas fa-exclamation-circle"></i><span>Por favor, preencha todos os campos obrigatórios.</span>';
                    loginForm.insertBefore(alertDiv, loginForm.firstChild);
                }
            }
        });
    }
});