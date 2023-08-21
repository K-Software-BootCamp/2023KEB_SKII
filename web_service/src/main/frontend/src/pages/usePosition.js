import {useState, useEffect} from "react";
import jwt_decode from 'jwt-decode';

function usePosition() {
    const [position, setPosition] = useState(null);

    useEffect(() => {
        const token = localStorage.getItem('token');

        if (token) {
            const decodedToken = jwt_decode(token);
            setPosition(decodedToken.position);
        }
    }, []);

    return position;
}

export default usePosition;