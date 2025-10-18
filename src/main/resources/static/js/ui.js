// UI service
const uiService = {
    // Элементы DOM
    elements: {
        loadingIndicator: document.getElementById('loadingIndicator'),
        tasksContainer: document.getElementById('tasksContainer'),
        membersContainer: document.getElementById('membersContainer'),
        tasksCount: document.getElementById('tasksCount'),
        membersCount: document.getElementById('membersCount')
    },

    // Инициализация UI
    init() {
        console.log('UI service initialized');
    },

    // Управление loading state
    showLoading() {
        if (this.elements.loadingIndicator) {
            this.elements.loadingIndicator.classList.remove('hidden');
        }
    },

    hideLoading() {
        if (this.elements.loadingIndicator) {
            this.elements.loadingIndicator.classList.add('hidden');
        }
    },

    // Утилиты
    showError(message) {
        alert(`Ошибка: ${message}`);
    },

    formatDate(dateString) {
        if (!dateString) return 'Не указана';
        const date = new Date(dateString);
        return date.toLocaleDateString('ru-RU');
    },

    // Рендер задач
    renderTasks(tasks) {
        if (!this.elements.tasksContainer) return;

        this.elements.tasksCount.textContent = `${tasks.length} задач`;

        if (tasks.length === 0) {
            this.elements.tasksContainer.innerHTML = `
                <div class="text-center py-8">
                    <p class="text-gray-500">Задачи пока не добавлены</p>
                </div>
            `;
            return;
        }

        this.elements.tasksContainer.innerHTML = tasks.map(task => `
            <div class="p-4 bg-white rounded-lg border border-gray-200 hover:bg-gray-50 transition-colors">
                <div class="flex justify-between items-start">
                    <div>
                        <h3 class="font-medium text-gray-800">${task.title || 'Без названия'}</h3>
                        <p class="text-sm text-gray-600 mt-1">${task.description || 'Нет описания'}</p>
                    </div>
                    <span class="${task.completed ? 'bg-green-100 text-green-800' : 'bg-yellow-100 text-yellow-800'} 
                            text-xs font-medium px-2 py-1 rounded-full">
                        ${task.completed ? 'Выполнено' : 'В процессе'}
                    </span>
                </div>
                <div class="flex justify-between items-center mt-3 text-sm text-gray-500">
                    <span>${task.assignedTo || 'Не назначено'}</span>
                    <span>${task.dueDate ? this.formatDate(task.dueDate) : 'Без срока'}</span>
                </div>
                <div class="flex gap-2 mt-3">
                    <button onclick="app.toggleTask('${task.id}')" class="text-xs bg-blue-500 text-white px-2 py-1 rounded">
                        ${task.completed ? 'Возобновить' : 'Завершить'}
                    </button>
                    <button onclick="app.deleteTask('${task.id}')" class="text-xs bg-red-500 text-white px-2 py-1 rounded">
                        Удалить
                    </button>
                </div>
            </div>
        `).join('');
    },

    // Рендер членов семьи
    renderFamilyMembers(members) {
        if (!this.elements.membersContainer) return;

        this.elements.membersCount.textContent = `${members.length} человек`;

        if (members.length === 0) {
            this.elements.membersContainer.innerHTML = `
                <div class="text-center py-8">
                    <p class="text-gray-500">Члены семьи не найдены</p>
                </div>
            `;
            return;
        }

        this.elements.membersContainer.innerHTML = members.map(member => `
            <div class="p-4 bg-white rounded-lg border border-gray-200 hover:bg-gray-50 transition-colors">
                <div class="flex items-center space-x-4">
                    <div class="w-12 h-12 bg-blue-500 rounded-full flex items-center justify-center text-white font-semibold">
                        ${(member.personalInfoDto?.firstname?.charAt(0) || '') + (member.personalInfoDto?.lastname?.charAt(0) || '')}
                    </div>
                    <div class="flex-1">
                        <h3 class="font-medium text-gray-800">
                            ${member.personalInfoDto?.firstname || 'Не указано'} ${member.personalInfoDto?.lastname || 'Не указано'}
                        </h3>
                        <p class="text-sm text-gray-600">${member.username || 'Без логина'}</p>
                        <p class="text-xs text-gray-500 mt-1">
                            Дата рождения: ${member.personalInfoDto?.birthDate ? this.formatDate(member.personalInfoDto.birthDate) : 'Не указана'}
                        </p>
                    </div>
                </div>
                ${member.familyDto ? `
                    <div class="mt-3 pt-3 border-t border-gray-100">
                        <span class="text-xs text-gray-500">Семья:</span>
                        <span class="text-sm text-gray-700 ml-2">${member.familyDto.familyName}</span>
                    </div>
                ` : ''}
                <div class="flex gap-2 mt-3">
                    <button onclick="app.editMember('${member.uuid}')" class="text-xs bg-green-500 text-white px-2 py-1 rounded">
                        Редактировать
                    </button>
                    <button onclick="app.deleteMember('${member.uuid}')" class="text-xs bg-red-500 text-white px-2 py-1 rounded">
                        Удалить
                    </button>
                </div>
            </div>
        `).join('');
    }
};