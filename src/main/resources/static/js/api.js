// Конфигурация API endpoints
const API_CONFIG = {
    baseUrl: 'http://localhost:8080/api',
    endpoints: {
        tasks: '/tasks',
        familyMembers: '/family/members',
        member: {
            create: '/member/create'
        }
    }
};

// Базовые HTTP методы
const httpService = {
    async request(url, options = {}) {
        const response = await fetch(`${API_CONFIG.baseUrl}${url}`, {
            headers: {
                'Content-Type': 'application/json',
                ...options.headers
            },
            ...options
        });

        if (!response.ok) {
            // Пробуем распарсить ошибку как JSON (для валидации)
            try {
                const errorData = await response.json();
                throw errorData;
            } catch {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
        }

        return await response.json();
    },

    async get(url) {
        return this.request(url, { method: 'GET' });
    },

    async post(url, data) {
        return this.request(url, {
            method: 'POST',
            body: JSON.stringify(data)
        });
    },

    async put(url, data) {
        return this.request(url, {
            method: 'PUT',
            body: JSON.stringify(data)
        });
    },

    async delete(url) {
        return this.request(url, { method: 'DELETE' });
    }
};

// Специфичные методы API
const apiService = {
    // Tasks
    async getTasks() {
        return await httpService.get(API_CONFIG.endpoints.tasks);
    },

    async createTask(taskData) {
        return await httpService.post(API_CONFIG.endpoints.tasks, taskData);
    },

    // Family Members
    async getFamilyMembers() {
        return await httpService.get(API_CONFIG.endpoints.familyMembers);
    },

    async createFamilyMember(memberData) {
        return await httpService.post(API_CONFIG.endpoints.familyMembers, memberData);
    },

    // Member Registration (новый эндпоинт)
    async createMember(memberData) {
        return await httpService.post(API_CONFIG.endpoints.member.create, memberData);
    },

    // Общие методы для переиспользования
    async updateTask(taskId, taskData) {
        return await httpService.put(`${API_CONFIG.endpoints.tasks}/${taskId}`, taskData);
    },

    async deleteTask(taskId) {
        return await httpService.delete(`${API_CONFIG.endpoints.tasks}/${taskId}`);
    },

    async deleteFamilyMember(memberId) {
        return await httpService.delete(`${API_CONFIG.endpoints.familyMembers}/${memberId}`);
    }
};