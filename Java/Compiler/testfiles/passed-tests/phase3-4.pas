Program relationalTest (input,output);
Var
   i,x : real;
   why, note : real;

begin
   if ((why = note - 1608) or not (note = why))
   then if (x - i = 0)
      then begin
         x := why / note
         end
end.

{Output :

CODE
1:  call main, 0
2:  exit
3:  PROCBEGIN main
4:  alloc 11
5:  move 1608, _6
6:  ltof _6, _4
7:  fsub _2, _4, _5
8:  beq _3, _5, 12
9:  goto 10
10:  beq _2, _3, 19
11:  goto 12
12:  fsub _0, _1, _7
13:  move 0, _9
14:  ltof _9, _8
15:  beq _7, _8, 17
16:  goto 19
17:  fdiv _2, _3, _10
18:  move _10, _0
19:  free 11
20:  PROCEND

}