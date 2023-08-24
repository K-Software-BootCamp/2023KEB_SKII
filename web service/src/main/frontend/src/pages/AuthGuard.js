import React, {useEffect, useState} from 'react';
import {Navigate, Outlet} from 'react-router-dom';
import AuthService from './AuthService.js';

const AuthGuard = ({children}) => {
    const [isTokenValid, setTokenValid] = useState(null);

    const authService = new AuthService();

    useEffect(() => {
        const checkTokenValidity = async () => {
            try {
                const accessAllowed = await authService.checkAccess();

                if (!accessAllowed) {
                    alert('접근 방식이 올바르지 않습니다. 다시 로그인 해주세요.');
                    authService.logout();
                    document.location.href = "/";
                }

                setTokenValid(accessAllowed);
            } catch (error) {
                console.error('토큰 유효성 검사 에러 - ', error);
                setTokenValid(false);
            }
        };

        checkTokenValidity();
    }, []);

    if (isTokenValid === null) {
        return <p>Loading...</p>;
    }

    if (!isTokenValid) {
        return <Navigate to={"/"} replace/>;
    }

    return <Outlet/>;
}

export default AuthGuard;
