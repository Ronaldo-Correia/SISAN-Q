document.addEventListener('DOMContentLoaded', function() {
    // Seções do formulário
    const sections = document.querySelectorAll('.section');
    const progressBar = document.getElementById('progressBar');
    const progressText = document.getElementById('progressText');

    // Botões de navegação
    const btnNext1 = document.getElementById('btnNext1');
    const btnPrev2 = document.getElementById('btnPrev2');
    const btnNext2 = document.getElementById('btnNext2');
    const btnPrev3 = document.getElementById('btnPrev3');

    // Mostrar apenas a primeira seção inicialmente
    sections.forEach((section, index) => {
        if (index !== 0) {
            section.classList.remove('active');
        }
    });

    // Atualizar barra de progresso
    function updateProgress(currentSection) {
        const progress = ((currentSection + 1) / sections.length) * 100;
        progressBar.style.width = `${progress}%`;
        progressText.textContent = `${currentSection + 1} de ${sections.length} seções`;
    }

    // Navegação entre seções
    function navigateToSection(targetIndex) {
        sections.forEach((section, index) => {
            if (index === targetIndex) {
                section.classList.add('active');
            } else {
                section.classList.remove('active');
            }
        });

        updateProgress(targetIndex);
        window.scrollTo(0, 0);
    }

    // Event listeners para botões de navegação
    if (btnNext1) {
        btnNext1.addEventListener('click', function() {
            navigateToSection(1);
        });
    }

    if (btnPrev2) {
        btnPrev2.addEventListener('click', function() {
            navigateToSection(0);
        });
    }

    if (btnNext2) {
        btnNext2.addEventListener('click', function() {
            navigateToSection(2);
        });
    }

    if (btnPrev3) {
        btnPrev3.addEventListener('click', function() {
            navigateToSection(1);
        });
    }

    // Validação antes de avançar
    function validateSection(sectionIndex) {
        const currentSection = sections[sectionIndex];
        const requiredInputs = currentSection.querySelectorAll('input[required]');
        let isValid = true;

        requiredInputs.forEach(input => {
            if (!input.value && !input.checked) {
                isValid = false;
                input.closest('.form-group').classList.add('error');
            } else {
                input.closest('.form-group').classList.remove('error');
            }
        });

        return isValid;
    }

    // Atualizar progresso inicial
    updateProgress(0);

    // Habilitar campos "Outro" quando selecionados
    document.querySelectorAll('.other-option input[type="radio"]').forEach(radio => {
        radio.addEventListener('change', function() {
            const textInput = this.closest('.other-option').querySelector('input[type="text"]');
            if (this.checked) {
                textInput.disabled = false;
                textInput.required = true;
            } else {
                textInput.disabled = true;
                textInput.required = false;
                textInput.value = '';
            }
        });
    });

    // Desabilitar campos "Outro" inicialmente
    document.querySelectorAll('.other-option input[type="text"]').forEach(input => {
        input.disabled = true;
        input.required = false;
    });
});