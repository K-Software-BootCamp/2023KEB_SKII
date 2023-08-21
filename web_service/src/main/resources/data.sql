-- Employee
INSERT INTO Employee (employee_id, name, birthday, gender, phone, email, department, position, in_charge)
VALUES ('1001', '박성부', '2001-01-01', 'M', '010-0000-0000', 'psb@gmail.com', '데이터 1팀', '관리자', 'Bearing 1_1');
INSERT INTO Employee (employee_id, name, birthday, gender, phone, email, department, position, in_charge)
VALUES ('1002', '김다은', '2001-01-02', 'F', '010-0000-0001', 'kde@gmail.com', '데이터 1팀', '사원', 'Bearing 1_2');
INSERT INTO Employee (employee_id, name, birthday, gender, phone, email, department, position, in_charge)
VALUES ('1003', '김정호', '2001-01-03', 'M', '010-0000-0002', 'kjh@gmail.com', '데이터 2팀', '사원', 'Bearing 1_3');

-- Member : password는 1234로 통일
INSERT INTO Member (employee_id, member_id, password, created_at)
VALUES ('1001', 'psb', '$2a$12$rld8jbKJQc6cQXnaF9445u9/SB6r6mhXkyj4YMCR8IDAsMte0O9kK', now());
INSERT INTO Member (employee_id, member_id, password, created_at)
VALUES ('1002', 'kde', '$2a$12$rld8jbKJQc6cQXnaF9445u9/SB6r6mhXkyj4YMCR8IDAsMte0O9kK', now());
