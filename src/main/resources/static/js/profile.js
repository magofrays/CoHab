// Profile module
const profile = {
    currentMember: null,

    // Инициализация профиля
    init() {
        this.loadMemberProfile();
    },

    // Загрузка профиля участника
    async loadMemberProfile() {
        try {
            uiService.showLoading();

            // Получаем username из URL
            const username = this.getUsernameFromUrl();
            if (!username) {
                throw new Error('Username not found in URL');
            }

            const member = await apiService.getMemberByUsername(username);
            this.currentMember = member;
            this.renderProfile(member);

        } catch (error) {
            console.error('Error loading profile:', error);
            this.showError('Не удалось загрузить профиль: ' + error.message);
        } finally {
            uiService.hideLoading();
        }
    },

    // Получение username из URL
    getUsernameFromUrl() {
        const pathSegments = window.location.pathname.split('/');
        return pathSegments[pathSegments.length - 1];
    },

    // Отрисовка профиля
    renderProfile(member) {
        const container = document.getElementById('profileContainer');

        if (!member) {
            container.innerHTML = `
                <div class="text-center py-8">
                    <p class="text-red-500">Профиль не найден</p>
                </div>
            `;
            return;
        }

        container.innerHTML = `
            <!-- UUID -->
            <div class="p-4 bg-gray-50 rounded-lg">
                <label class="block text-sm font-medium text-gray-600 mb-2">ID участника</label>
                <p class="text-sm text-gray-700 font-mono">${member.uuid || 'Не указан'}</p>
            </div>

            <!-- Username -->
            <div class="p-4 bg-gray-50 rounded-lg">
                <label class="block text-sm font-medium text-gray-600 mb-2">Имя пользователя</label>
                <p class="text-lg font-semibold text-gray-800">${member.username || 'Не указан'}</p>
            </div>

            <!-- Personal Info Section -->
            <div class="p-4 bg-gray-50 rounded-lg border border-gray-200">
                <h2 class="text-lg font-medium text-gray-700 mb-4">Личная информация</h2>
                <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                    <div>
                        <label class="block text-sm font-medium text-gray-600 mb-2">Имя</label>
                        <p class="text-gray-800">${member.personalInfoDto?.firstname || 'Не указано'}</p>
                    </div>
                    <div>
                        <label class="block text-sm font-medium text-gray-600 mb-2">Фамилия</label>
                        <p class="text-gray-800">${member.personalInfoDto?.lastname || 'Не указано'}</p>
                    </div>
                    <div class="md:col-span-2">
                        <label class="block text-sm font-medium text-gray-600 mb-2">Дата рождения</label>
                        <p class="text-gray-800">
                            ${member.personalInfoDto?.birthDate ?
            profile.formatDate(member.personalInfoDto.birthDate) : 'Не указана'}
                        </p>
                    </div>
                </div>
            </div>

            <!-- Family Section -->
            ${member.familyDto ? `
                <div class="p-4 bg-gray-50 rounded-lg border border-gray-200">
                    <h2 class="text-lg font-medium text-gray-700 mb-4">Семья</h2>
                    <div>
                        <label class="block text-sm font-medium text-gray-600 mb-2">Название семьи</label>
                        <p class="text-gray-800">${member.familyDto.familyName}</p>
                    </div>
                </div>
            ` : ''}

            <!-- Access Section -->
            ${member.accesses && member.accesses.length > 0 ? `
                <div class="p-4 bg-gray-50 rounded-lg border border-gray-200">
                    <h2 class="text-lg font-medium text-gray-700 mb-4">Права доступа</h2>
                    <div class="space-y-2">
                        ${member.accesses.map(access => `
                            <div class="flex justify-between items-center p-2 bg-white rounded border">
                                <span class="text-sm text-gray-700">${access.accessType || 'Не указан'}</span>
                                <span class="text-xs text-gray-500">${access.resource || 'Общий'}</span>
                            </div>
                        `).join('')}
                    </div>
                </div>
            ` : ''}
        `;
    },

    // Форматирование даты
    formatDate(dateString) {
        if (!dateString) return 'Не указана';
        try {
            const date = new Date(dateString);
            return date.toLocaleDateString('ru-RU', {
                day: '2-digit',
                month: '2-digit',
                year: 'numeric'
            });
        } catch (e) {
            return dateString;
        }
    },

    // Показ ошибки
    showError(message) {
        const errorAlert = document.getElementById('errorAlert');
        if (errorAlert) {
            errorAlert.textContent = message;
            errorAlert.classList.remove('hidden');
            setTimeout(() => errorAlert.classList.add('hidden'), 5000);
        }
    },

    // Действия
    editProfile() {
        if (this.currentMember) {
            alert(`Редактирование профиля: ${this.currentMember.username}`);
            // window.location.href = `/member/${this.currentMember.username}/edit`;
        }
    },

    backToList() {
        window.location.href = '/';
    }
};