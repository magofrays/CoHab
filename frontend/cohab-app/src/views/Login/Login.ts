import { ref } from 'vue'
import { useRouter } from 'vue-router'

export default {
  name: 'LoginView',
  setup() {
    const router = useRouter()
    const username = ref<string>('')
    const password = ref<string>('')
    const error = ref<string>('')
    const loading = ref<boolean>(false)

    interface LoginResponse {
      token: string
    }

    const handleLogin = async (): Promise<void> => {
      loading.value = true
      error.value = ''
      
      try {
        const response = await fetch('/api/login', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            username: username.value,
            password: password.value
          })
        })
        
        if (response.ok) {
          const data: LoginResponse = await response.json()
          localStorage.setItem('token', data.token)
          router.push('/')
        } else {
          error.value = 'Неверное имя пользователя или пароль'
        }
      } catch (err) {
        error.value = 'Ошибка сети'
      } finally {
        loading.value = false
      }
    }

    // 🔥 ВАЖНО: верните всё что используется в template
    return {
      username,
      password,
      error,
      loading,
      handleLogin
    }
  }
}