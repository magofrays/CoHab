
export interface ProblemDetail {
  type?: string;
  title?: string;
  status: number;
  detail?: string;
  instance?: string;
  // Дополнительные поля, которые могут быть в вашем Spring приложении
  timestamp?: string;
  message?: string;
  path?: string;
  error?: string;
}

// 🔥 Базовые интерфейсы для API
export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  refreshToken?: string;
  expiresAt: string;
}

export interface ApiError {
  message: string;
  status: number;
  problem?: ProblemDetail;
}