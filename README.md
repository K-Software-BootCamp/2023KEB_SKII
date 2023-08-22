# 💻 Smart bearing condition monitoring service
- phm-2012 dataset을 통해 학습한 인공지능 모델로 베어링 열화 모니터링 서비스를 구현 및 스마트 팩토리 시스템 구현 프로젝트

<div >
<img width="1000" alt="스크린샷 2023-08-23 오전 3 53 04" src="https://github.com/SWTeam2/learning_infer/assets/139730231/2a3e5652-f73e-4970-b68d-8a74131a5ce1">
<div/>

## Service

- 센서 데이터의 실시간 수집과 모델의 예측 결과를 효과적으로 연동하는 시스템을 구현
 
- CNN-LSTM 기법모델을 활용하여 Bearing의 상태 변화와 남은 수명 간의 패턴을 식별하고  예측하는 모델서비스을 구축 
 
- 연구에 그치는 것이 아닌 현업에서 부품의 수명 주기 관리 및 안전성 향상을 위한 중요한 도구로 활용 가능한 서비스 개발 틀 배포


## Directories
1. [Data_preprocessing](Data_preprocessing)
    - 데이터셋의 증강 및 주파수 변환 기법 사용
2. [DB_server](DB_server)
    - MSA(Microservices Architecture)로 운영
3. [modeling_inferServer](modeling_inferServer)
    - cnn_lstm 모델 사용
4. [web_service](web_service)
    - CI/CD 아키텍쳐


## Usage(Guide)
```
code or url
```
## ERD
![usecase diagram](https://github.com/SWTeam2/learning_infer/assets/139730231/43820183-0401-41d8-ab88-b8ac5abe88f0)

## Project Doc
- notion
- git repo


## Acknowledgement
```
“본 연구는 과학기술정보통신부 및 정보통신기획평가원의 SW전문인재양성사업의 연구결과로 수행되었음“(2022-0-01127)
```
