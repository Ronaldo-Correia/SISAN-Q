document.addEventListener('DOMContentLoaded', function() {
  const togglePassword = document.querySelector('.toggle-password');
  const passwordInput = document.getElementById('senhausuario');

  if (togglePassword && passwordInput) {
    togglePassword.addEventListener('mouseover', function() {
      passwordInput.setAttribute('type', 'text');
      this.innerHTML = '<i class="fas fa-eye-slash"></i>';
    });

    togglePassword.addEventListener('mouseout', function() {
      passwordInput.setAttribute('type', 'password');
      this.innerHTML = '<i class="fas fa-eye"></i>';
    });
  }

  const loginForm = document.querySelector('.login-form');
  if (loginForm) {
    loginForm.addEventListener('submit', function(e) {
      const email = document.getElementById('emailusuario');
      const password = document.getElementById('senhausuario');
      let isValid = true;

      document.querySelectorAll('.form-group').forEach(group => {
        group.classList.remove('error');
      });

      if (!email.value) {
        email.closest('.form-group').classList.add('error');
        isValid = false;
      }

      if (!password.value) {
        password.closest('.form-group').classList.add('error');
        isValid = false;
      }

      if (!isValid) {
        e.preventDefault();
        if (!document.querySelector('.alert-error')) {
          const alertDiv = document.createElement('div');
          alertDiv.className = 'alert-error';
          alertDiv.innerHTML = '<i class="fas fa-exclamation-circle"></i><span> Preencha todos os campos!</span>';
          loginForm.insertBefore(alertDiv, loginForm.firstChild);
        }
      }
    });
  }
});
