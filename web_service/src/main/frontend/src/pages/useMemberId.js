import { useState, useEffect } from "react";
import jwt_decode from 'jwt-decode';

function useMemberId() {
    const [memberId, setMemberId] = useState(null);

    useEffect(() => {
        const token = localStorage.getItem('token');

        if(token) {
            const decodedToken = jwt_decode(token);
            setMemberId(decodedToken.memberId);
        }
    }, []);

    return memberId;
}

export default useMemberId;