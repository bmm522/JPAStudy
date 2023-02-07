drop table if exists Board CASCADE;
drop table if exists Heart CASCADE;
drop table if exists Member CASCADE;
drop table if exists Reply CASCADE;
create table Board (boardId bigint generated by default as identity, content varchar(500) not null, createDate timestamp, deleteDate timestamp, hit integer default 0 not null, status integer, title varchar(50) not null, updateDate timestamp, memberId bigint, primary key (boardId));
create table Heart (heartId bigint generated by default as identity, board_id bigint, member_id bigint, primary key (heartId));
create table Member (memberId bigint generated by default as identity, accountId varchar(50) not null, accountType varchar(50) not null, nickname varchar(50) not null, quit varchar(255), primary key (memberId));
create table Reply (replyId bigint generated by default as identity, content varchar(255) not null, createDate timestamp, board_id bigint, member_id bigint, primary key (replyId));
alter table Board add constraint FKad83dxf0turt4s2e5ijeb4yup foreign key (memberId) references Member;
alter table Heart add constraint FKb36433c37sa503wpvv40bcdtf foreign key (board_id) references Board;
alter table Heart add constraint FKqywlnrmknna1wyayy6xj43621 foreign key (member_id) references Member;
alter table Reply add constraint FKf29xb044km9swon79clvq3noe foreign key (board_id) references Board;
alter table Reply add constraint FKrssj15ssc80te3x9317wmpbcu foreign key (member_id) references Member;