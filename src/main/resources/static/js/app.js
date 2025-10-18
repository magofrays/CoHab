// Main application
const app = {
    // Инициализация приложения
    init() {
        console.log('App initialized');
        this.loadTasks();
        this.loadFamilyMembers();
    },

    // Загрузка задач
    async loadTasks() {
        try {
            uiService.showLoading();
            const tasks = await apiService.getTasks();
            uiService.renderTasks(tasks);
        } catch (error) {
            uiService.showError('Не удалось загрузить задачи: ' + error.message);
        } finally {
            uiService.hideLoading();
        }
    },

    // Загрузка членов семьи
    async loadFamilyMembers() {
        try {
            uiService.showLoading();
            const members = await apiService.getFamilyMembers();
            uiService.renderFamilyMembers(members);
        } catch (error) {
            uiService.showError('Не удалось загрузить членов семьи: ' + error.message);
        } finally {
            uiService.hideLoading();
        }
    },

    // Действия с задачами
    async toggleTask(taskId) {
        try {
            // Реализовать логику переключения статуса задачи
            console.log('Toggle task:', taskId);
            // await apiService.put(`/tasks/${taskId}/toggle`);
            this.loadTasks(); // Перезагружаем список
        } catch (error) {
            uiService.showError('Ошибка при обновлении задачи: ' + error.message);
        }
    },

    async deleteTask(taskId) {
        if (confirm('Удалить задачу?')) {
            try {
                // await apiService.delete(`/tasks/${taskId}`);
                console.log('Delete task:', taskId);
                this.loadTasks(); // Перезагружаем список
            } catch (error) {
                uiService.showError('Ошибка при удалении задачи: ' + error.message);
            }
        }
    },

    // Действия с членами семьи
    async editMember(memberId) {
        // Реализовать форму редактирования
        console.log('Edit member:', memberId);
        alert(`Редактирование члена семьи ${memberId} - нужно реализовать`);
    },

    async deleteMember(memberId) {
        if (confirm('Удалить члена семьи?')) {
            try {
                // await apiService.delete(`/family/members/${memberId}`);
                console.log('Delete member:', memberId);
                this.loadFamilyMembers(); // Перезагружаем список
            } catch (error) {
                uiService.showError('Ошибка при удалении члена семьи: ' + error.message);
            }
        }
    },

    // Формы
    showAddTaskForm() {
        alert('Форма добавления задачи - нужно реализовать');
    },

    showAddMemberForm() {
        alert('Форма добавления члена семьи - нужно реализовать');
    }
};

document.addEventListener('DOMContentLoaded', function() {
    uiService.init();
    app.init();
});