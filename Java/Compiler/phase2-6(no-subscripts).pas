program expressionTest (input, output);

 var a, b : integer;
        c : real;
        x : array [0..5] of real;
 begin
   a := b - 4;
   c := a mod b
 end.
 
{ Output : 
CODE
1:  call main, 0
2:  exit
3:  PROCBEGIN main
4:  alloc 14
5:  move 4, _10
6:  sub _0, _10, _9
7:  move _9, _1
8:  move _1, _11
9:  move _11, _12
10:  sub _12, _0, _11
11:  bge _11, _0, 9
12:  ltof _11, _13
13:  move _13, _2
14:  free 14
15:  PROCEND
}