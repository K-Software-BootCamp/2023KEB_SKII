import React, {useState, useEffect} from "react";
import '../App.css';
import usePosition from "./usePosition.js";
import {Navigate} from "react-router-dom";

const EmployeeUpdate = () => {
    const [name, setName] = useState('');
    const [birthday, setBirthday] = useState('');
    const [gender, setGender] = useState('');
    const [phone, setPhone] = useState('');
    const [email, setEmail] = useState('');
    const [department, setDepartment] = useState('');
    const [position, setPosition] = useState('');
    const [inCharge, setInCharge] = useState('');
    const [isDisabled_pre, setIsDisabled_pre] = useState(true);

    const [employeeData, setEmployeeData] = useState([]);
    const [isLoading, setIsLoading] = useState(true);

    const userPosition = usePosition();

    useEffect(() => {
        if (userPosition !== null) {
            setIsLoading(false);
        }
    }, [userPosition]);

    const getEmployees = async () => {
        try {
            const employees = await fetch('/api/employees', {
                method: 'GET',
                headers: {
                    'X-AUTH-TOKEN': localStorage.getItem("token")
                },
            });

            if (employees.ok) {
                const employeesData = await employees.json();
                setEmployeeData(employeesData);
            } else {
                console.log('사원 목록 불러오기 실패');
            }
        } catch (error) {
            console.error('사원 목록 불러오기 에러 - ', error);
        }
    };

    useEffect(() => {
        getEmployees();
    }, []);

    const [selectedEmployee, setSelectedEmployee] = useState('');

    useEffect(() => {
        const selectedEmp = employeeData.find(emp => emp.employeeId === selectedEmployee);
        if (selectedEmp) {
            setName(selectedEmp.name);
            setBirthday(selectedEmp.birthday);
            setGender(selectedEmp.gender);
            setPhone(selectedEmp.phone);
            setEmail(selectedEmp.email);
            setDepartment(selectedEmp.department);
            setPosition(selectedEmp.position);
            setInCharge(selectedEmp.inCharge);
            setIsDisabled_pre(false);
        }
    }, [selectedEmployee, employeeData]);

    const handleEmployeeChange = (event) => {
        setSelectedEmployee(event.target.value);
    };

    const updateEmployee = async (e) => {
        e.preventDefault();

        if (!selectedEmployee) {
            alert('사원을 선택해주세요.');
            return;
        } else if (!phone) {
            alert('연락처를 입력해주세요.');
            return;
        } else if (!department) {
            alert('부서를 입력해주세요.');
            return;
        } else if (!position) {
            alert('직급을 입력해주세요.');
            return;
        }

        try {
            const updateEmployee = await fetch(`/api/employees/${selectedEmployee}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'X-AUTH-TOKEN': localStorage.getItem("token")
                },
                body: JSON.stringify({phone, department, position, inCharge}),
            });

            if (updateEmployee.ok) {
                alert('사원 정보가 수정되었습니다.');
                window.location.href = '/employee';
            } else {
                console.log('사원 수정 실패');
            }
        } catch (error) {
            console.error('사원 수정 에러 - ', error);
        }
    };

    if (isLoading) {
        return <div>Loading...</div>
    }

    if (userPosition !== "관리자") {
        alert('관리자만 접근할 수 있는 페이지입니다.');
        return <Navigate to={"/dashboard"} replace />;
    }

    return (
        <div style={{backgroundColor: '#F2F4F8', width: '100%', height: '100%', margin: 0}}>
            <div className="center-outer-div">
                <div className="center-inner-div" style={{textAlign: 'center'}}>
                    <div className="center-box-title"
                         style={{width: '300px', textAlign: 'center', marginBottom: '70px'}}>
                        Edit Employee
                    </div>

                    <form onSubmit={updateEmployee}>
                        <select
                            id="employee"
                            value={selectedEmployee}
                            onChange={handleEmployeeChange}
                            style={{width: '300px'}}
                            className="center-box-input"
                        >
                            <option value="" disabled hidden>Choose Employee</option>
                            {employeeData.map(emp => (
                                <option key={emp.employeeId} value={emp.employeeId}>
                                    [{emp.employeeId} : {emp.department}] {emp.name}
                                </option>
                            ))}
                        </select>
                        <input
                            id="name"
                            className="center-box-input"
                            placeholder="Name"
                            type="text"
                            disabled
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
                                disabled
                                value={birthday}
                                onChange={(e) => setBirthday(e.target.value)}
                            />
                            <select
                                id="gender"
                                value={gender}
                                className="center-box-input"
                                style={{display: 'inline-block', width: '110px', height: '47.5px'}}
                                disabled
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
                            disabled
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
                            value={inCharge || ''}
                            onChange={(e) => setInCharge(e.target.value)}
                        />
                        <button className="center-box-btn bg-blue" type="submit">
                            UPDATE
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

export default EmployeeUpdate;