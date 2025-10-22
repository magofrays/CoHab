import { ref, onMounted } from 'vue'

interface Task {
  id: string
  title: string
  description: string
  completed: boolean
  assignedTo: string
  dueDate: string
}

interface PersonalInfoDto {
  firstname: string
  lastname: string
  birthDate: string
}

interface FamilyDto {
  familyName: string
}

interface FamilyMember {
  uuid: string
  username: string
  personalInfoDto: PersonalInfoDto
  familyDto: FamilyDto
}

export default {
  name: 'HomeView',
  setup() {
    const loading = ref(false)
    const tasks = ref<Task[]>([])
    const familyMembers = ref<FamilyMember[]>([])

    const formatDate = (dateString: string): string => {
      if (!dateString) return 'Не указана'
      const date = new Date(dateString)
      return date.toLocaleDateString('ru-RU')
    }

    const showError = (message: string) => {
      alert(`Ошибка: ${message}`)
    }

    const loadTasks = async (): Promise<void> => {
      try {
        loading.value = true
        // Замените на ваш API вызов
        // const response = await fetch('/api/tasks')
        // tasks.value = await response.json()
        
        // Временные mock данные
        tasks.value = [
          {
            id: '1',
            title: 'Купить продукты',
            description: 'Молоко, хлеб, яйца',
            completed: false,
            assignedTo: 'Мама',
            dueDate: '2024-01-15'
          }
        ]
      } catch (error) {
        showError('Не удалось загрузить задачи')
      } finally {
        loading.value = false
      }
    }

    const loadFamilyMembers = async (): Promise<void> => {
      try {
        loading.value = true
        // Замените на ваш API вызов
        // const response = await fetch('/api/family/members')
        // familyMembers.value = await response.json()
        
        // Временные mock данные
        familyMembers.value = [
          {
            uuid: '1',
            username: 'papa',
            personalInfoDto: {
              firstname: 'Иван',
              lastname: 'Иванов',
              birthDate: '1980-01-01'
            },
            familyDto: {
              familyName: 'Ивановы'
            }
          }
        ]
      } catch (error) {
        showError('Не удалось загрузить членов семьи')
      } finally {
        loading.value = false
      }
    }

    const toggleTask = async (taskId: string): Promise<void> => {
      try {
        // await fetch(`/api/tasks/${taskId}/toggle`, { method: 'PUT' })
        await loadTasks()
      } catch (error) {
        showError('Ошибка при обновлении задачи')
      }
    }

    const deleteTask = async (taskId: string): Promise<void> => {
      if (confirm('Удалить задачу?')) {
        try {
          // await fetch(`/api/tasks/${taskId}`, { method: 'DELETE' })
          await loadTasks()
        } catch (error) {
          showError('Ошибка при удалении задачи')
        }
      }
    }

    const editMember = (memberId: string): void => {
      alert(`Редактирование члена семьи ${memberId} - нужно реализовать`)
    }

    const deleteMember = async (memberId: string): Promise<void> => {
      if (confirm('Удалить члена семьи?')) {
        try {
          // await fetch(`/api/family/members/${memberId}`, { method: 'DELETE' })
          await loadFamilyMembers()
        } catch (error) {
          showError('Ошибка при удалении члена семьи')
        }
      }
    }

    const showAddTaskForm = (): void => {
      alert('Форма добавления задачи - нужно реализовать')
    }

    const showAddMemberForm = (): void => {
      alert('Форма добавления члена семьи - нужно реализовать')
    }

    onMounted(() => {
      loadTasks()
      loadFamilyMembers()
    })

    return {
      loading,
      tasks,
      familyMembers,
      formatDate,
      loadTasks,
      loadFamilyMembers,
      toggleTask,
      deleteTask,
      editMember,
      deleteMember,
      showAddTaskForm,
      showAddMemberForm
    }
  }
}