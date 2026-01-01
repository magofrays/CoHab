import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { apiService } from '@/services/api';
import type { ApiError, LoginResponse } from '@/types/api';

export default {
    name: 'Registration',
    setup() {
        const router = useRouter();
        const username = ref<string>('');
        const password = ref<string>('');
        const firstname = ref<string>('');
        const lastname = ref<string>('');
        const birthDate = ref<string>('');
        const error = ref<string>('');
        const loading = ref<boolean>(false);

        const handleLogin = async (): Promise<void> => {
            loading.value = true;
            error.value = '';

            try {
                const response = await apiService.post('registration', {
                    username: username.value,
                    password: password.value,
                    firstname: firstname.value,
                    lastname: lastname.value,
                    birthDate: birthDate.value
                });

                const data: LoginResponse = await response.json();
                localStorage.setItem('token', data.token);
                localStorage.setItem('tokenExpiresAt', data.expiresAt);
                router.push('/');

            } catch (err: unknown) {
                const apiError = err as ApiError;
                error.value = apiError.message ;

                if (apiError.status === 403) {
                    console.warn('Account issue:', apiError.problem?.detail);
                }
            } finally {
                loading.value = false;
            }
        };

        return {
            username,
            password,
            firstname,
            lastname,
            birthDate,
            error,
            loading,
            handleLogin,
        };
    },
};