program expressionTest (input, output);
 var a, b : integer;
        c : real;
        x : array [0..5] of real;
 begin
   b := a * 4;
   c := (b + a)/ 2
    
 end.
 
{Output :
 
CODE
1:  call main, 0
2:  exit
3:  PROCBEGIN main
4:  alloc 18
5:  move 4, _11
6:  mul _1, _11, _9
7:  move _9, _0
8:  add _0, _1, _12
9:  ltof _12, _14
10:  move 2, _17
11:  ltof _17, _15
12:  fdiv _14, _15, _16
13:  move _16, _2
14:  free 18
15:  PROCEND

}