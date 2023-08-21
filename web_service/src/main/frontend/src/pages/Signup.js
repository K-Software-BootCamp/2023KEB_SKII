import React, {useState} from "react";
import '../App.css';

const Signup = () => {
    const [employeeId, setEmployeeId] = useState('');
    const [email, setEmail] = useState('');
    const [memberId, setMemberId] = useState('');
    const [password, setPassword] = useState('');
    const [isDisabled_pre, setIsDisabled_pre] = useState(true);
    const [isDisabled_post, setIsDisabled_post] = useState(false);

    const checkEmployee = async () => {
        try {
            const checkEmployee = await fetch('/api/members/signup/check-employee', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({employeeId, email}),
            });

            if (checkEmployee.ok) {
                const checkEmployeeData = await checkEmployee.json();

                if (checkEmployeeData) {
                    try {
                        const isExisted = await fetch('/api/members/signup/is-existed', {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json',
                            },
                            body: JSON.stringify({employeeId}),
                        });

                        if (isExisted.ok) {
                            const isExistedData = await isExisted.json();
                            if (isExistedData) {
                                alert('이미 회원 가입한 사원입니다.');
                            } else {
                                alert('사원 정보가 확인되었습니다.');
                                setIsDisabled_pre(false);
                                setIsDisabled_post(true);
                            }
                        } else {
                            console.log('회원가입 실패');
                        }
                    } catch (error) {
                        console.error('회원가입 에러 - ', error);
                    }
                } else {
                    alert('사원 정보가 일치하지 않습니다.');
                }
            } else {
                console.log('사원 정보 확인 실패');
            }
        } catch (error) {
            console.error('사원 정보 확인 에러 - ', error);
        }
    };

    const signUp = async (e) => {
        e.preventDefault();
        try {
            const isDuplicated = await fetch('/api/members/signup/is-duplicated', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({memberId}),
            });

            if (isDuplicated.ok) {
                const isDuplicatedData = await isDuplicated.json();

                if (isDuplicatedData) {
                    alert('이미 사용 중인 아이디입니다.');
                } else {
                    try {
                        const signup = await fetch('/api/members/signup', {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json',
                            },
                            body: JSON.stringify({employeeId, email, memberId, password}),
                        });

                        if (signup.status == 201) {
                            alert('회원 가입이 완료되었습니다.');
                            window.location.href = "/";
                        } else {
                            console.log('회원 가입 실패');
                        }
                    } catch (error) {
                        console.error('회원 가입 에러 - ', error);
                    }
                }
            } else {
                console.log('아이디 중복 검사 실패');
            }
        } catch (error) {
            console.error('아이디 중복 검사 에러 - ', error);
        }
    };

    return (
        <div style={{backgroundColor: '#F2F4F8', width: '100%', height: '100%', margin: 0}}>
            <div className="center-outer-div">
                <div className="center-inner-div" style={{textAlign: 'center'}}>
                    <div className="center-box-title"
                         style={{width: '300px', textAlign: 'center', marginBottom: '70px'}}>
                        Sign Up
                    </div>

                    <form onSubmit={signUp}>
                        <input
                            id="employee_id"
                            className="center-box-input"
                            placeholder="Employee ID"
                            disabled={isDisabled_post}
                            value={employeeId}
                            onChange={(e) => setEmployeeId(e.target.value)}
                        />
                        <div>
                            <input
                                id="business_email"
                                className="center-box-input"
                                placeholder="Business Email"
                                style={{display: 'inline-block', width: '154px', marginRight: '9px'}}
                                disabled={isDisabled_post}
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                            />
                            <button
                                className="center-box-btn bg-charcoal"
                                style={{display: 'inline-block', width: '110px'}}
                                type="button"
                                onClick={checkEmployee}
                            >
                                CONFIRM
                            </button>
                        </div>
                        <input
                            id="id"
                            className="center-box-input"
                            placeholder="ID"
                            disabled={isDisabled_pre}
                            value={memberId}
                            onChange={(e) => setMemberId(e.target.value)}
                        />
                        <input
                            id="pw"
                            className="center-box-input"
                            placeholder="Password"
                            type="password"
                            disabled={isDisabled_pre}
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                        />
                        <button className="center-box-btn bg-blue" type="submit">
                            SIGN UP
                        </button>
                        <a className="a-blue" href="/">
                            Return to Login page
                        </a>
                    </form>
                </div>
            </div>
        </div>
    );
};

export default Signup;