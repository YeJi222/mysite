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

