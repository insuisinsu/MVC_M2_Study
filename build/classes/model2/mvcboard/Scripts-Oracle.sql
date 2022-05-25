create table t_member(
    id varchar2(10) primary key,
    pwd varchar2(10) not null,
    name varchar2(50) not null,
    email varchar2(100) null,
    joinDate date default sysdate
);

insert into t_member
Values ('hong', '1234', '홍길동', 'hong@gmail.com', sysdate);
insert into t_member
Values ('lee', '1234', '이순신', 'lee@gmail.com', sysdate);
insert into t_member
Values ('kim', '1234', '김유신', 'kim@gmail.com', sysdate);
insert into t_member
Values ('kang', '1234', '강감찬', 'kang@gmail.com', sysdate);

select * from t_member