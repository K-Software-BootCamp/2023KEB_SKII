import React, {useState} from 'react';
import '../App.css';
import logo from '../images/logo_O.png';
import {useNavigate} from 'react-router-dom';

const Login = () => {
    const [id, setId] = useState('');
    const [password, setPassword] = useState('');
    const history = useNavigate();

    const handleKeyDown = (event) => {
        const key = event.code;
        switch(key) {
            case 'Enter':
                login();
            break;
            default:
        }
    }

    const login = async () => {
        try {
            const response = await fetch('/api/members/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({memberId: id, password: password})
            });

            if (response.ok) {
                const token = await response.text();
                localStorage.setItem('token', token);
                history('/dashboard');
            } else {
                console.log('로그인 실패');
                alert("로그인에 실패하였습니다.");
                setId('');
                setPassword('');
            }
        } catch (error) {
            console.error('로그인 에러 - ', error);
        }
    };

    return (
        <div style={{backgroundColor: '#F2F4F8', width: '100%', height: '100%', margin: 0}}>
            <div className="center-outer-div">
                <div className="center-inner-div">
                    <div style={{width: '220px', padding: '40px', height: '100px', marginBottom: '25px'}}>
                        <img src={logo} width="100%" alt="로고"/>
                    </div>
                    <div>
                        <input
                            className="center-box-input"
                            id="id"
                            placeholder="Id"
                            value={id}
                            onChange={(e) => setId(e.target.value)}
                            onKeyDown={handleKeyDown}
                        />
                        <input
                            className="center-box-input"
                            id="password"
                            placeholder="Password"
                            type="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            onKeyDown={handleKeyDown}
                        />
                        <button className="center-box-btn bg-blue" type="button" onClick={login}>
                            LOGIN
                        </button>
                        <button className="center-box-btn bg-charcoal" type="button"
                                onClick={() => (window.location.href = '/signup')}>
                            SIGN UP
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Login;