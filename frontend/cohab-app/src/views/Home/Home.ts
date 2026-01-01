import { ref, onMounted } from 'vue'
import { hasFamily, getFamilyMembers, createFamily } from '@/services/api'
import type { ReadMemberDto, ReadFamilyDto } from '@/types/api'
import type { ApiError } from '@/types/api'

interface Task {
  id: string
  title: string
  description: string
  completed: boolean
  assignedTo: string
  dueDate: string
}

export default {
  name: 'HomeView',
  setup() {
    const loading = ref(false)
    const tasks = ref<Task[]>([])
    const familyMembers = ref<ReadMemberDto[]>([])
    const userHasFamily = ref<boolean | null>(null)
    const showCreateFamilyForm = ref(false)
    const newFamilyName = ref('')
    const creatingFamily = ref(false)

    const formatDate = (dateString: string): string => {
      if (!dateString) return 'Не указана'
      const date = new Date(dateString)
      return date.toLocaleDateString('ru-RU')
    }

    const formatFamilyNames = (families?: ReadFamilyDto[]): string => {
      if (!families || families.length === 0) return ''
      return families.map((f: ReadFamilyDto) => f.familyName).join(', ')
    }

    const showError = (message: string) => {
      alert(`Ошибка: ${message}`)
    }

    const checkHasFamily = async (): Promise<void> => {
      try {
        loading.value = true
        userHasFamily.value = await hasFamily()
        if (userHasFamily.value) {
          await loadFamilyMembers()
        } else {
          showCreateFamilyForm.value = true
        }
      } catch (error) {
        const apiError = error as ApiError
        showError(apiError.message || 'Не удалось проверить наличие семьи')
      } finally {
        loading.value = false
      }
    }

    const loadTasks = async (): Promise<void> => {
      try {
        loading.value = true
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
        familyMembers.value = await getFamilyMembers()
      } catch (error) {
        const apiError = error as ApiError
        showError(apiError.message || 'Не удалось загрузить членов семьи')
      } finally {
        loading.value = false
      }
    }

    const handleCreateFamily = async (): Promise<void> => {
      if (!newFamilyName.value.trim()) {
        showError('Введите название семьи')
        return
      }

      try {
        creatingFamily.value = true
        const createdFamily: ReadFamilyDto = await createFamily(newFamilyName.value.trim())
        userHasFamily.value = true
        showCreateFamilyForm.value = false
        newFamilyName.value = ''
        await loadFamilyMembers()
      } catch (error) {
        const apiError = error as ApiError
        showError(apiError.message || 'Не удалось создать семью')
      } finally {
        creatingFamily.value = false
      }
    }

    const toggleTask = async (taskId: string): Promise<void> => {
      try {
        await loadTasks()
      } catch (error) {
        showError('Ошибка при обновлении задачи')
      }
    }

    const deleteTask = async (taskId: string): Promise<void> => {
      if (confirm('Удалить задачу?')) {
        try {
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
          await loadFamilyMembers()
        } catch (error) {
          const apiError = error as ApiError
          showError(apiError.message || 'Ошибка при удалении члена семьи')
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
      checkHasFamily()
      loadTasks()
    })

    return {
      loading,
      tasks,
      familyMembers,
      userHasFamily,
      showCreateFamilyForm,
      newFamilyName,
      creatingFamily,
      formatDate,
      formatFamilyNames,
      loadTasks,
      loadFamilyMembers,
      toggleTask,
      deleteTask,
      editMember,
      deleteMember,
      showAddTaskForm,
      showAddMemberForm,
      handleCreateFamily
    }
  }
}