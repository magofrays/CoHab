class RegistrationForm {
    constructor() {
        this.form = document.getElementById('registrationForm');
        this.errorContainer = document.getElementById('errorContainer');
        this.errorList = document.getElementById('errorList');
        this.init();
    }

    init() {
        this.form.addEventListener('submit', (e) => this.handleSubmit(e));
    }

    async handleSubmit(e) {
        e.preventDefault();

        const formData = this.getFormData();

        try {
            await apiService.createMember(formData);
            this.showSuccess();
        } catch (error) {
            this.showErrors(error.errors);
        }
    }

    getFormData() {
        return {
            memberDto: {
                username: document.getElementById('username').value,
                password: document.getElementById('password').value
            },
            personalInfoDto: {
                firstname: document.getElementById('firstname').value,
                lastname: document.getElementById('lastname').value,
                birthDate: document.getElementById('birthDate').value
            }
        };
    }

    showErrors(errors) {
        this.errorList.innerHTML = '';
        errors.forEach(error => {
            const li = document.createElement('li');
            li.textContent = `${this.getFieldLabel(error.field)}: ${error.message}`;
            this.errorList.appendChild(li);
        });

        this.errorContainer.classList.remove('hidden');
    }

    getFieldLabel(field) {
        const labels = {
            'memberDto.username': 'Имя пользователя',
            'memberDto.password': 'Пароль',
            'personalInfoDto.firstname': 'Имя',
            'personalInfoDto.lastname': 'Фамилия',
            'personalInfoDto.birthDate': 'Дата рождения'
        };
        return labels[field] || field;
    }

    showSuccess() {
        alert('Регистрация успешна!');
        this.form.reset();
        this.errorContainer.classList.add('hidden');
    }
}

// Инициализация при загрузке страницы
document.addEventListener('DOMContentLoaded', () => {
    new RegistrationForm();
});