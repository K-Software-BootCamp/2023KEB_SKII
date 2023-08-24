import {useState, useEffect} from "react";
import useMemberId from "./useMemberId.js";

function useLoginInfo() {
    const memberId = useMemberId();
    const [loginInfo, setLoginInfo] = useState(null);

    useEffect(() => {
        if (localStorage.getItem('loginInfo') == null) {
            if (memberId) {
                fetch(`/api/employees/${memberId}`, {
                    method: 'GET',
                    headers: {
                        'X-AUTH-TOKEN': localStorage.getItem("token")
                    }
                })
                    .then(response => response.json())
                    .then(data => {
                        setLoginInfo(data);
                        localStorage.setItem('loginInfo', JSON.stringify(data));
                    })
                    .catch(error => {
                        console.error('사원 정보 불러오기 에러 - ', error)
                    })
            }
        } else {
            setLoginInfo(JSON.parse(localStorage.getItem('loginInfo')));
        }
    }, [memberId]);

    return loginInfo;
}

export default useLoginInfo;