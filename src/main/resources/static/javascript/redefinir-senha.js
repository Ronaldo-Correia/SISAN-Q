document.addEventListener('DOMContentLoaded', function() {
    // Toggle password visibility
    const togglePasswordButtons = document.querySelectorAll('.toggle-password');
    
    togglePasswordButtons.forEach(button => {
        button.addEventListener('click', function() {
            const input = this.closest('.password-input').querySelector('input');
            const type = input.getAttribute('type') === 'password' ? 'text' : 'password';
            input.setAttribute('type', type);
            
            // Toggle icon
            this.innerHTML = type === 'password' ? '<i class="fas fa-eye"></i>' : '<i class="fas fa-eye-slash"></i>';
        });
    });
    
    // Password strength indicator
    const passwordInput = document.getElementById('novaSenha');
    const strengthBar = document.querySelector('.strength-bar');
    const strengthText = document.querySelector('.strength-text');
    const confirmPasswordInput = document.getElementById('confirmarSenha');
    
    if (passwordInput) {
        passwordInput.addEventListener('input', function() {
            const password = this.value;
            let strength = 0;
            
            // Length check
            if (password.length > 7) strength += 1;
            if (password.length > 11) strength += 1;
            
            // Character variety checks
            if (/[A-Z]/.test(password)) strength += 1; // Uppercase
            if (/[0-9]/.test(password)) strength += 1; // Numbers
            if (/[^A-Za-z0-9]/.test(password)) strength += 1; // Special chars
            
            // Update strength bar
            const width = Math.min(strength * 20, 100);
            strengthBar.style.width = `${width}%`;
            
            // Update color and text
            if (password.length === 0) {
                strengthBar.style.backgroundColor = 'transparent';
                strengthText.textContent = 'Força da senha';
                strengthText.style.color = 'var(--gray-color)';
            } else if (strength < 2) {
                strengthBar.style.backgroundColor = 'var(--error-color)';
                strengthText.textContent = 'Fraca';
                strengthText.style.color = 'var(--error-color)';
            } else if (strength < 4) {
                strengthBar.style.backgroundColor = '#ff9800';
                strengthText.textContent = 'Média';
                strengthText.style.color = '#ff9800';
            } else {
                strengthBar.style.backgroundColor = 'var(--success-color)';
                strengthText.textContent = 'Forte';
                strengthText.style.color = 'var(--success-color)';
            }
        });
    }
    
    // Form validation
    const form = document.querySelector('.recuperar-senha-form');
    if (form) {
        form.addEventListener('submit', function(e) {
            const password = document.getElementById('novaSenha').value;
            const confirmPassword = document.getElementById('confirmarSenha').value;
            let isValid = true;
            
            // Clear previous errors
            document.querySelectorAll('.form-group').forEach(group => {
                group.classList.remove('error');
            });
            
            // Validate password
            if (!password) {
                document.querySelector('#novaSenha').closest('.form-group').classList.add('error');
                isValid = false;
            } else if (password.length < 8) {
                document.querySelector('#novaSenha').closest('.form-group').classList.add('error');
                isValid = false;
            }
            
            // Validate password confirmation
            if (!confirmPassword) {
                document.querySelector('#confirmarSenha').closest('.form-group').classList.add('error');
                isValid = false;
            } else if (password !== confirmPassword) {
                document.querySelector('#confirmarSenha').closest('.form-group').classList.add('error');
                isValid = false;
                
                // Show error message
                if (!document.querySelector('.alert-error')) {
                    const alertDiv = document.createElement('div');
                    alertDiv.className = 'alert alert-error';
                    alertDiv.innerHTML = '<i class="fas fa-exclamation-circle"></i><span>As senhas não coincidem</span>';
                    form.insertBefore(alertDiv, form.firstChild);
                }
            }
            
            if (!isValid) {
                e.preventDefault();
                
                // Scroll to first error
                const firstError = document.querySelector('.error');
                if (firstError) {
                    firstError.scrollIntoView({
                        behavior: 'smooth',
                        block: 'center'
                    });
                }
            }
        });
    }
});