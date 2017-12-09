Program relationalTest (input,output);
Var
   i,j : integer;
   c,d:real;
   z:array[1..100] of integer;

begin
   if (z[j] >= j)
     then if (j - i <> 0)
          then begin
             j := i - 5
          end
          else i := z[j]         
end.

{Output:

CODE
1:  call main, 0
2:  exit
3:  PROCBEGIN main
4:  alloc 114
5:  move 1, _105
6:  sub _0, _105, _104
7:  load _4, _104, _106
8:  bge _106, _0, 10
9:  goto 22
10:  sub _0, _1, _107
11:  move 0, _108
12:  bne _107, _108, 14
13:  goto 18
14:  move 5, _110
15:  sub _1, _110, _109
16:  move _109, _0
17:  goto 22
18:  move 1, _112
19:  sub _0, _112, _111
20:  load _4, _111, _113
21:  move _113, _1
22:  free 114
23:  PROCEND

}