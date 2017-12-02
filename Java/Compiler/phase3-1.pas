Program relationalTest (input,output);
Var
   i,j : integer;

begin
   if (i <= j)
   then if (j - i = 0)
      then begin
         j := i - 5
         end
end.

{Output:

CODE
1:  call main, 0
2:  exit
3:  PROCBEGIN main
4:  alloc 6
5:  ble _1, _0, 7
6:  goto 14
7:  sub _0, _1, _2
8:  move 0, _3
9:  beq _2, _3, 11
10:  goto 14
11:  move 5, _5
12:  sub _1, _5, _4
13:  move _4, _0
14:  free 6
15:  PROCEND

}