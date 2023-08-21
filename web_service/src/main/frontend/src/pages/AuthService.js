class AuthService {
    async checkAccess() {
        try {
            const response = await fetch('/api/members/check-access', {
                method: 'GET',
                headers: {
                    'X-AUTH-TOKEN': localStorage.getItem('token')
                }
            });

            if (response.ok) {
                return true;
            } else {
                console.log("토큰 유효성 검사 실패");
                return false;
            }
        } catch (error) {
            console.error('토큰 유효성 검사 에러 - ', error);
            return false;
        }
    }

    logout() {
        localStorage.removeItem('token');
    }
}

export default AuthService;