import type { ProblemDetail, ApiError } from '@/types/api';

const API_BASE_URL = import.meta.env.VITE_API_URL;
async function parseProblemDetail(response: Response): Promise<ProblemDetail> {
  try {
    const problem: ProblemDetail = await response.json();
    
    return {
      ...problem, // ← данные от сервера имеют приоритет
      title: problem.title || 'Ошибка',
      detail: problem.detail || 'Произошла неизвестная ошибка',
    };
  } catch {
    return {
      status: response.status,
      title: 'Ошибка',
      detail: `HTTP Error: ${response.status} ${response.statusText}`
    };
  }
}


export async function handleApiError(response: Response): Promise<ApiError> {
  const problem = await parseProblemDetail(response);
  
  return {
    message: problem.detail || `Ошибка ${response.status}`,
    status: response.status,
    problem
  };
}

async function apiRequest(url: string, options: RequestInit = {}) {
  const token = localStorage.getItem('token');

  const headers = {
    'Content-Type': 'application/json',
    ...options.headers,
  } as HeadersInit;

  if (token) {
    (headers as any)['Authorization'] = `Bearer ${token}`;
  }

  const response = await fetch(`${API_BASE_URL}${url}`, {
    ...options,
    headers,
  });

  return response;
}

// 🔥 API сервис с улучшенной обработкой ошибок
export const apiService = {
  async get(url: string) {
    const response = await apiRequest(url);
    if (!response.ok) {
      throw await handleApiError(response);
    }
    return response;
  },

  async post(url: string, data: any) {
    const response = await apiRequest(url, {
      method: 'POST',
      body: JSON.stringify(data),
    });
    if (!response.ok) {
      throw await handleApiError(response);
    }
    return response;
  },

  async put(url: string, data: any) {
    const response = await apiRequest(url, {
      method: 'PUT',
      body: JSON.stringify(data),
    });
    if (!response.ok) {
      throw await handleApiError(response);
    }
    return response;
  },

  async delete(url: string) {
    const response = await apiRequest(url, { method: 'DELETE' });
    if (!response.ok) {
      throw await handleApiError(response);
    }
    return response;
  },
};