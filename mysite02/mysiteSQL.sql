use webdb;

select count(*) from board;
select max(g_no) + 1 from board;

select board.no, title, contents, hit, date_format(reg_date, '%Y-%m-%d %H:%i:%s'),
			g_no, o_no, depth, name from board join user where user.no = board.user_no
order by g_no desc, o_no asc;

select min(o_no) from board where g_no=1 and o_no > 1;

select count(*) from user join board
where user.no = board.user_no and
      board.no = 115 and
      user.password=password('test');

-- 검색 쿼리(정렬 조건 포함)
select t1.*, t2.name
from board t1 join user t2
where t1.user_no = t2.no
order by g_no desc, o_no asc;

-- get Total posts
select count(*) TOTCNT from(
    select t1.*, t2.name
    from board t1 join user t2
    where t1.user_no = t2.no
    order by g_no desc, o_no asc
) s1;

SELECT *,
       ROW_NUMBER() over () AS RNUM
from board;

SELECT * FROM (
    SELECT S1.*,
           ROW_NUMBER() over () AS RNUM,
           COUNT(*) OVER() TOTCNT
    FROM (
        select t1.*, t2.name
        from board t1 join user t2
        where t1.user_no = t2.no
        order by g_no desc, o_no asc
    ) S1
) S2 where RNUM >= 11 and RNUM <=  15;


