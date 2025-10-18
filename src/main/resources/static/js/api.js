// Конфигурация API endpoints
const API_CONFIG = {
    baseUrl: 'http://localhost:8080/api',
    endpoints: {
        tasks: '/tasks',
        familyMembers: '/family/members',
        addTask: '/tasks',
        addMember: '/family/members'
    }
};

// API service
const apiService = {
    async get(url) {
        const response = await fetch(`${API_CONFIG.baseUrl}${url}`);
        if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
        return await response.json();
    },

    async post(url, data) {
        const response = await fetch(`${API_CONFIG.baseUrl}${url}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data)
        });
        if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
        return await response.json();
    },

    async put(url, data) {
        const response = await fetch(`${API_CONFIG.baseUrl}${url}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data)
        });
        if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
        return await response.json();
    },

    async delete(url) {
        const response = await fetch(`${API_CONFIG.baseUrl}${url}`, {
            method: 'DELETE'
        });
        if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
        return await response.json();
    },

    // Специфичные методы API
    async getTasks() {
        return await this.get(API_CONFIG.endpoints.tasks);
    },

    async getFamilyMembers() {
        return await this.get(API_CONFIG.endpoints.familyMembers);
    },

    async createTask(taskData) {
        return await this.post(API_CONFIG.endpoints.addTask, taskData);
    },

    async createMember(memberData) {
        return await this.post(API_CONFIG.endpoints.addMember, memberData);
    }
};