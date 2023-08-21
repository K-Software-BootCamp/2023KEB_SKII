import React from 'react';
import '../App.css';

const EmployeeRow = ({employee}) => {
    return (
        <div className="main-row drag-prevent">
            <div style={{width: '3%'}}></div>
            <div style={{width: '15%'}}>{employee.employeeId}</div>
            <div style={{width: '12%'}}>{employee.name}</div>
            <div style={{width: '15%'}}>{employee.department}</div>
            <div style={{width: '10%'}}>{employee.position}</div>
            <div style={{width: '10%'}}>{employee.inCharge || '-'}</div>
            <div style={{width: '20%'}}>{employee.email}</div>
            <div style={{width: '15%'}}>{employee.phone}</div>
        </div>
    );
};

export default EmployeeRow;