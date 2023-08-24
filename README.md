# 💻 Smart bearing condition monitoring service
- phm-2012dataset을 통해 학습한 인공지능 모델로 베어링 열화 모니터링 서비스를 구현 및 스마트 팩토리 시스템 구현 프로젝트

<div >
<img width="1000" alt="스크린샷 2023-08-23 오전 3 53 04" src="https://github.com/SWTeam2/learning_infer/assets/139730231/2a3e5652-f73e-4970-b68d-8a74131a5ce1">
<div/>
<br>

## 💡 Project introduction

- 2023.07.26 - 2023.08.23
- 스마트 팩토리 시스템 환경에서의 이상 탐지와 RUL 예측 서비스는 산업 분야에 혁명적인 변화를 가져오고 있습니다. 기계의 고장은 생산 라인 중단과 비용 증가로 이어질 수 있기 때문에, 기계의 상태를 모니터링하고
  조기에 이상을 감지하며 필요한 유지보수를 수행하는 것이 매우 중요합니다. 이를 통해 생산성을 향상시키고 비용을 절감할 수 있습니다.
  <br><br>
 
## 🧞‍♂️ Service
+ 대시보드 서비스
![대시보드 서비스 시연영상](
https://github.com/K-Software-BootCamp/2023KEB_SKII/assets/76683835/a2d6f7cc-a236-45e5-bfcd-93b501b6403a
)


+ 회원 관리 화면
![회원관리 시연영상](
https://github.com/K-Software-BootCamp/2023KEB_SKII/assets/76683835/c5b92833-71ae-42db-9722-c50fcc8dcb32
)

- 센서 데이터의 실시간 수집과 모델의 예측 결과를 효과적으로 연동하는 시스템을 구현

- CNN-LSTM 기법모델을 활용하여 Bearing의 상태 변화와 남은 수명 간의 패턴을 식별하고  예측하는 모델서비스을 구축 
 
- 연구에 그치는 것이 아닌 현업에서 부품의 수명 주기 관리 및 안전성 향상을 위한 중요한 도구로 활용 가능한 서비스 개발 틀 배포
<br><br>

## 📋 MSA structure

![MSA](https://github.com/K-Software-BootCamp/2023KEB_SKII/assets/127288729/07759fc0-bd4b-4831-9520-e05a55279c4f)
<br><br>

## 📁 Directories
1. [Data_preprocessing](Data_preprocessing)
    - 데이터셋의 증강 및 전처리 과정
2. [DB_server](https://github.com/K-Software-BootCamp/2023KEB_SKII/tree/main/DB_server)
    - DB 서버 구현 및 API 통신 
3. [modeling_inferServer](https://github.com/K-Software-BootCamp/2023KEB_SKII/tree/main/modeling_inferServer)
    - Neural network modeling 및 최적화, prediction 기능을 하는 서버 구축
5. [web_service](https://github.com/K-Software-BootCamp/2023KEB_SKII/tree/main/web_service)
   - Web 서비스에 필요한 서버 및 프론트 구현
<br><br>

## 👥 Member
🤹🏻‍♂️ 모델링 : 김찬영, 최병훈

⚙️ 데이터 엔지니어링 : 박성부, 방가윤, 김정호, 윤정우

🖥 백엔드 & 프론트엔드 : 조희연, 김다은, 이낙규
<br><br>

## 📋 Service Sequence
### - Bearing data work flow
![sequence_bearing](https://github.com/chy0503/SmartBearing/blob/main/document/sequence/sequence_bearing.png)
<br><br>

### - Log in & out work flow
![sequence_member](https://github.com/chy0503/SmartBearing/blob/main/document/sequence/sequence_member.png)
<br><br>

### - Employee
![sequence employee](https://github.com/chy0503/SmartBearing/blob/main/document/sequence/sequence_employee.png)
<br><br>

### - Notification
<img src="https://github.com/chy0503/SmartBearing/blob/main/document/sequence/sequence_notification.png" alt="sequence notification" height="450">
<br><br>

## 📈 Service Structure
![usecase diagram](https://github.com/SWTeam2/learning_infer/assets/139730231/43820183-0401-41d8-ab88-b8ac5abe88f0)
<br><br>

## ⚙️ CI/CD Architecture
![ci/cd diagram](https://github.com/SWTeam2/SmartBearing/blob/main/document/CICD.png)
<br><br>


## 📄 Project Docs
- [notion link](https://www.notion.so/choheeyeon/SK2-Smart-Bearing-af1f4c8346c049489b396224684dd3f5)
- [git repository](https://github.com/SWTeam2)
<br><br>

## 🛠️ Tech Stack

### Frontend
<img src="https://img.shields.io/badge/Figma-F24E1E.svg?style=for-the-badge&logo=Figma&logoColor=white"/> <img src="https://img.shields.io/badge/NPM-%23CB3837.svg?style=for-the-badge&logo=npm&logoColor=white"/> <img src="https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=React&logoColor=black"/> <img src="https://img.shields.io/badge/React_Router-CA4245?style=for-the-badge&logo=react-router&logoColor=white"/> <img src="https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white"/> <img src="https://img.shields.io/badge/Chart.js-FF6384.svg?style=for-the-badge&logo=chartdotjs&logoColor=white"/>

### Backend
<img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white"/> <img src="https://img.shields.io/badge/Spring DATA JPA-6DB33F?style=for-the-badge&logo=Spring&logoColor=white"/> <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=Spring Security&logoColor=white"/> <img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=Gradle&logoColor=white"/><br>
<img src="https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens"/> <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white"/> <img src="https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=Redis&logoColor=white"/> <img src="https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white"/> <img src="https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white"/>

### Data engineering & Modelling
<img src="https://img.shields.io/badge/python-3776AB?style=for-the-badge&logo=python&logoColor=white">
<img src="https://img.shields.io/badge/pytorch-EE4C2C?style=for-the-badge&logo=pytorch&logoColor=white">
<img src="https://img.shields.io/badge/numpy-013243?style=for-the-badge&logo=numpy&logoColor=white">
<img src="https://img.shields.io/badge/postgresql-4169E1?style=for-the-badge&logo=postgresql&logoColor=white">
<br>
<img src="https://img.shields.io/badge/anaconda-44A833?style=for-the-badge&logo=anaconda&logoColor=white">
<img src="https://img.shields.io/badge/fastapi-009688?style=for-the-badge&logo=fastapi&logoColor=white">
<img src="https://img.shields.io/badge/pandas-150458?style=for-the-badge&logo=pandas&logoColor=white">
<img src="https://img.shields.io/badge/scikitlearn-F7931E?style=for-the-badge&logo=scikitlearn&logoColor=white">
<img src="https://img.shields.io/badge/maplibre-396CB2?style=for-the-badge&logo=maplibre&logoColor=white">

### Server Infra
<img src="https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white"> <img src="https://img.shields.io/badge/Amazon EC2-FF9900?style=for-the-badge&logo=Amazon EC2&logoColor=white"> <img src="https://img.shields.io/badge/Ubuntu-E95420?style=for-the-badge&logo=ubuntu&logoColor=white"> <img src="https://img.shields.io/badge/GitHub Actions-2088FF?style=for-the-badge&logo=GitHub Actions&logoColor=white">

### Team Tools
<img src="https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white"/> <img src="https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white"/> <img src="https://img.shields.io/badge/gitkraken-%179287.svg?style=for-the-badge&logo=gitkraken&logoColor=white"/> <img src="https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=slack&logoColor=white"/> <img src="https://img.shields.io/badge/Notion-%23000000.svg?style=for-the-badge&logo=notion&logoColor=white"/> <br><br>
<br>

## Acknowledgement
```
“본 연구는 과학기술정보통신부 및 정보통신기획평가원의 SW전문인재양성사업의 연구결과로 수행되었음“(2022-0-01127)
```
