INSERT INTO Member(nickname, accountType, accountId,quit) VALUES ('김지인', 'Realtor', 'Realtor 1', 'Y');
INSERT INTO Member(nickname, accountType, accountId,quit) VALUES ('강건강', 'Lessor', 'Lessor 2', 'Y');
INSERT INTO Member(nickname, accountType, accountId,quit) VALUES ('남나눔', 'Lessee', 'Lessee 3', 'Y');
INSERT INTO BOARD(title, content, createDate,status ,memberId) VALUES ('테스트 글 첫번째 내용', '테스트 글 첫번째 내용',now(),'Y','1');
INSERT INTO BOARD(title, content, createDate,status ,memberId) VALUES ('테스트 글 두번째 내용', '테스트 글 두번째 내용', now(),'Y','2');
INSERT INTO BOARD(title, content, createDate,status ,memberId) VALUES ('테스트 글 세번째 내용', '테스트 글 세번째 내용', now(),'Y','3');
