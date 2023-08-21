import React, {useEffect, useState} from 'react';
import '../App.css';
import logo_X from "../images/logo_X.png";
import label from "../images/label.png";
import folders from "../images/folders.png";
import user from "../images/user.png";
import chart from "../images/chart.png";
import people from "../images/people.png";
import {useNavigate} from 'react-router-dom';
import {logout} from "./useLogout.js";
import usePosition from "./usePosition.js";
import useLoginInfo from "./useLoginInfo.js";

const Notification = () => {
    const handleNavigate = useNavigate();
    const userPosition = usePosition();
    const employeeInfo = useLoginInfo();

    const [notificationData, setNotificationData] = useState([]);

    const handleLogout = () => {
        logout(handleNavigate);
    };

    const getNotis = async () => {
        try {
            const notifications = await fetch('/api/notification', {
                method: 'GET',
                headers: {
                    'X-AUTH-TOKEN': localStorage.getItem("token")
                }
            });

            if (notifications.ok) {
                const notificationData = await notifications.json();
                setNotificationData(notificationData);
            } else {
                console.log('알림 목록 불러오기 실패');
            }
        } catch (error) {
            console.error('알림 목록 불러오기 에러 - ', error);
        }
    };

    useEffect(() => {
        getNotis();
    }, []);

    return (
        <div style={{display: 'flex', backgroundColor: '#F2F4F8', height: '100vh'}}>
            {/* 사이드바 */}
            <div style={{background: 'white', width: '18vw', height: '100%'}}>
                <div style={{padding: '15%'}}>
                    <img src={logo_X} width="100%" alt="로고"/>
                </div>

                <div className="sidebar-row drag-prevent">
                    <div className="sidebar-icon">
                        <img src={label} width="100%" alt="아이콘"/>
                    </div>
                    <div className="sidebar-text">{employeeInfo?.employeeId}</div>
                </div>
                <div className="sidebar-row drag-prevent">
                    <div className="sidebar-icon">
                        <img src={folders} width="100%" alt="아이콘"/>
                    </div>
                    <div className="sidebar-text">{employeeInfo?.department}</div>
                </div>
                <div className="sidebar-row drag-prevent">
                    <div className="sidebar-icon">
                        <img src={user} width="100%" alt="아이콘"/>
                    </div>
                    <div className="sidebar-text">{employeeInfo?.name}</div>
                </div>

                <div style={{height: '1px', margin: '10% 10%', background: 'black'}}></div>

                <div
                    className="sidebar-row drag-prevent cursor-pointer hover-bg-grey"
                    onClick={() => {
                        window.location.href = '/dashboard';
                    }}
                >
                    <div className="sidebar-icon">
                        <img src={chart} width="100%" alt="아이콘"/>
                    </div>
                    <div className="sidebar-text">Dashboard</div>
                </div>
                {userPosition !== "관리자" ? "" : (
                    <div
                        className="sidebar-row drag-prevent cursor-pointer hover-bg-grey"
                        onClick={() => {
                            window.location.href = '/employee';
                        }}
                    >
                        <div className="sidebar-icon">
                            <img src={people} width="100%" alt="아이콘"/>
                        </div>
                        <div className="sidebar-text">Employee</div>
                    </div>
                )}

                <div
                    style={{position: 'fixed', bottom: '0', left: '0', width: '18vw'}}
                    className="cursor-pointer drag-prevent"
                >
                    <div style={{padding: '20px'}}>
                        <button className="logout-btn bg-charcoal" type="button" style={{width: '100%'}} onClick={handleLogout}>
                            <div style={{paddingLeft: '5%', fontSize: '1.1rem'}}>LOGOUT</div>
                        </button>
                    </div>
                </div>
            </div>

            {/* main */}
            <div style={{width: '82vw', height: '100vh', overflow: 'auto'}}>
                <div className="row" style={{margin: '3% 4%'}}>
                    <div className="layout-title">Notification</div>
                </div>

                {notificationData.map((noti) => (
                    <div className="main-noti drag-prevent">
                        <div style={{fontWeight:'bold', color: '#8A96A8'}} >{noti.createdAt.replace('T', ' ').split('.')[0]}</div>
                        <div className="main-noti-content">
                            <div style={{width: '13%', fontWeight:'bold'}}>{noti.publisher}</div>
                            <div style={{width: '87%', wordWrap: 'break-word'}}>{noti.message}</div>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default Notification;
