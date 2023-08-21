import React, {useEffect, useState} from "react";
import '../App.css';
import usePosition from "./usePosition.js";
import {Navigate} from "react-router-dom";

const EmployeeCreate = () => {
    const [employeeId, setEmployeeId] = useState('');
    const [name, setName] = useState('');
    const [birthday, setBirthday] = useState('');
    const [gender, setGender] = useState('');
    const [phone, setPhone] = useState('');
    const [email, setEmail] = useState('');
    const [department, setDepartment] = useState('');
    const [position, setPosition] = useState('');
    const [inCharge, setInCharge] = useState('');
    const [isDisabled_pre, setIsDisabled_pre] = useState(true);
    const [isDisabled_post, setIsDisabled_post] = useState(false);
    const [isLoading, setIsLoading] = useState(true);

    const userPosition = usePosition();

    useEffect(() => {
        if (userPosition !== null) {
            setIsLoading(false);
        }
    }, [userPosition]);

    const handleGenderChange = (event) => {
        setGender(event.target.value);
    };

    const checkEmployee = async () => {
        try {
            const checkEmployee = await fetch('/api/employees/register/check-employee', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'X-AUTH-TOKEN': localStorage.getItem("token")
                },
                body: JSON.stringify({employeeId}),
            });

            if (checkEmployee.ok) {
                const checkEmployeeData = await checkEmployee.json();

                if (checkEmployeeData) {
                    alert('이미 사용 중인 사원 번호입니다.');
                } else {
                    alert('사용 가능한 사원 번호입니다.');
                    setIsDisabled_pre(false);
                    setIsDisabled_post(true);
                }
            } else {
                console.log('사원 정보 확인 실패');
            }
        } catch (error) {
            console.error('사원 정보 확인 에러 - ', error);
        }
    };

    const newEmployee = async (e) => {
        e.preventDefault();

        if (!name) {
            alert('이름을 입력해주세요.');
            return;
        } else if (!birthday) {
            alert('생일을 입력해주세요.');
            return;
        } else if (!gender) {
            alert('성별을 입력해주세요.');
            return;
        } else if (!phone) {
            alert('연락처를 입력해주세요.');
            return;
        } else if (!email) {
            alert('이메일을 입력해주세요.');
            return;
        } else if (!department) {
            alert('부서를 입력해주세요.');
            return;
        } else if (!position) {
            alert('직급을 입력해주세요.');
            return;
        }

        try {
            const newEmployee = await fetch('/api/employees/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'X-AUTH-TOKEN': localStorage.getItem("token")
                },
                body: JSON.stringify({
                    employeeId,
                    name,
                    birthday,
                    gender,
                    phone,
                    email,
                    department,
                    position,
                    inCharge
                }),
            });

            if (newEmployee.status === 201) {
                alert('사원이 생성되었습니다.');
                window.location.href = '/employee';
            } else {
                console.log('사원 생성 실패');
            }
        } catch (error) {
            console.error('사원 생성 에러 - ', error);
        }
    };

    if (isLoading) {
        return <div>Loading...</div>
    }

    if (userPosition !== "관리자") {
        alert('관리자만 접근할 수 있는 페이지입니다.');
        return <Navigate to={"/dashboard"} replace/>;
    }

    return (
        <div style={{backgroundColor: '#F2F4F8', width: '100%', height: '100%', margin: 0}}>
            <div className="center-outer-div">
                <div className="center-inner-div" style={{textAlign: 'center'}}>
                    <div className="center-box-title"
                         style={{width: '300px', textAlign: 'center', marginBottom: '70px'}}>
                        New Employee
                    </div>

                    <form onSubmit={newEmployee}>
                        <div>
                            <input
                                id="employee_id"
                                className="center-box-input"
                                placeholder="Employee ID"
                                style={{display: 'inline-block', width: '154px', marginRight: '9px'}}
                                disabled={isDisabled_post}
                                value={employeeId}
                                onChange={(e) => setEmployeeId(e.target.value)}
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
                            id="name"
                            className="center-box-input"
                            placeholder="Name"
                            type="text"
                            disabled={isDisabled_pre}
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                        />
                        <div>
                            <input
                                id="birthday"
                                className="center-box-input"
                                placeholder="Birthday"
                                style={{display: 'inline-block', width: '154px', marginRight: '9px'}}
                                type="date"
                                disabled={isDisabled_pre}
                                value={birthday}
                                onChange={(e) => setBirthday(e.target.value)}
                            />
                            <select
                                id="gender"
                                value={gender} onChange={handleGenderChange}
                                className="center-box-input"
                                style={{display: 'inline-block', width: '110px', height: '47.5px'}}
                                disabled={isDisabled_pre}
                            >
                                <option value="" disabled hidden>Gender</option>
                                <option value="F">F</option>
                                <option value="M">M</option>
                            </select>
                        </div>
                        <input
                            id="phone"
                            className="center-box-input"
                            placeholder="010-0000-0000"
                            type="text"
                            disabled={isDisabled_pre}
                            value={phone}
                            onChange={(e) => setPhone(e.target.value)}
                        />
                        <input
                            id="email"
                            className="center-box-input"
                            placeholder="email@gmail.com"
                            type="email"
                            disabled={isDisabled_pre}
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                        />
                        <input
                            id="department"
                            className="center-box-input"
                            placeholder="Department"
                            type="text"
                            disabled={isDisabled_pre}
                            value={department}
                            onChange={(e) => setDepartment(e.target.value)}
                        />
                        <input
                            id="position"
                            className="center-box-input"
                            placeholder="Position"
                            type="text"
                            disabled={isDisabled_pre}
                            value={position}
                            onChange={(e) => setPosition(e.target.value)}
                        />
                        <input
                            id="inCharge"
                            className="center-box-input"
                            placeholder="In Charge"
                            type="text"
                            disabled={isDisabled_pre}
                            value={inCharge}
                            onChange={(e) => setInCharge(e.target.value)}
                        />
                        <button className="center-box-btn bg-blue" type="submit">
                            CREATE
                        </button>
                        <a className="a-blue" href="/employee">
                            Return to Employee page
                        </a>
                    </form>
                </div>
            </div>
        </div>
    );
};

export default EmployeeCreate;