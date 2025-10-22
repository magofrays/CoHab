const API_BASE_URL = 'http://cohab-back:8080/api'

async function apiRequest(url: string, options: RequestInit = {}) {
  const token = localStorage.getItem('token')
  
  const headers = {
    'Content-Type': 'application/json',
    ...options.headers,
  } as HeadersInit

  // ✅ Добавляем Authorization header если есть токен
  if (token) {
    (headers as any)['Authorization'] = `Bearer ${token}`
  }

  const response = await fetch(`${API_BASE_URL}${url}`, {
    ...options,
    headers,
  })

  if (response.status === 401) {
    localStorage.removeItem('token')
    window.location.href = '/login'
    throw new Error('Unauthorized')
  }

  if (!response.ok) {
    throw new Error(`HTTP error! status: ${response.status}`)
  }

  return response.json()
}

export const apiService = {
  get: (url: string) => apiRequest(url),
  post: (url: string, data: any) => 
    apiRequest(url, { method: 'POST', body: JSON.stringify(data) }),
  put: (url: string, data: any) => 
    apiRequest(url, { method: 'PUT', body: JSON.stringify(data) }),
  delete: (url: string) => 
    apiRequest(url, { method: 'DELETE' })
}