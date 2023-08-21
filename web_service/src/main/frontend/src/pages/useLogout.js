export const logout = async (handleNavigate) => {
    try {
        const response = await fetch('/api/members/logout', {
            method: 'POST',
            headers: {
                'X-AUTH-TOKEN': localStorage.getItem('token')
            }
        });

        if (response.ok) {
            localStorage.removeItem('token');
            localStorage.removeItem('loginInfo');
            handleNavigate('/');
        } else {
            console.log('로그아웃 실패');
        }
    } catch (error) {
        console.error('로그아웃 에러 - ', error);
    }
};