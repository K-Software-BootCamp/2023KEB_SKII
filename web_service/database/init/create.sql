CREATE TABLE employee
(
    employee_id varchar(100) not null comment '사원코드',
    name        varchar(100) not null comment '이름',
    birthday    date         not null comment '생년월일',
    gender      varchar(1)   not null comment '성별',
    phone       varchar(100) not null comment '연락처',
    email       varchar(100) not null comment '사내이메일',
    department  varchar(100) not null comment '부서',
    position    varchar(100) not null comment '직급',
    in_charge    varchar(100) null comment '담당',
    primary key (employee_id)
) comment '사원';

CREATE TABLE member
(
    employee_id varchar(100) not null comment '사원코드',
    created_at  datetime(6) not null comment '생성일',
    member_id   varchar(100) not null comment '아이디',
    password    varchar(100) not null comment '비밀번호',
    primary key (employee_id)
) comment '회원';

-- Employee
INSERT INTO employee (employee_id, name, birthday, gender, phone, email, department, position, in_charge)
VALUES ('1001', '홍길동', '2001-01-01', 'M', '010-0000-0000', 'hgd@gmail.com', '데이터 1팀', '관리자', 'Bearing_1');
INSERT INTO employee (employee_id, name, birthday, gender, phone, email, department, position, in_charge)
VALUES ('1002', '홍길순', '2001-01-02', 'M', '010-0000-0001', 'hgs@gmail.com', '데이터 1팀', '사원', null);
INSERT INTO employee (employee_id, name, birthday, gender, phone, email, department, position, in_charge)
VALUES ('1003', '김미미', '2001-01-03', 'F', '010-0000-0002', 'kmm@gmail.com', '데이터 2팀', '사원', null);

-- Member
INSERT INTO member (employee_id, member_id, password, created_at)
VALUES ('1001', 'hgd', '1234', now());
INSERT INTO member (employee_id, member_id, password, created_at)
VALUES ('1002', 'hgs', '12345', now());
