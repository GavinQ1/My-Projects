Program andortest (input,output);
Var
   h, i , j : integer;
   
 begin
 
h:= 1;
 i:= 2;
 j:= 3;
  if ((h <> j) and (h = j)) then
      j := 5
      else h := 5;
  
  
  if ((h <> j) or (h + i = j)) then
     i := 20
  
 end.

{Output:

CODE
1:  call main, 0
2:  exit
3:  PROCBEGIN main
4:  alloc 10
5:  move 1, _3
6:  move _3, _2
7:  move 2, _4
8:  move _4, _1
9:  move 3, _5
10:  move _5, _0
11:  bne _2, _0, 13
12:  goto 18
13:  beq _2, _0, 15
14:  goto 18
15:  move 5, _6
16:  move _6, _0
17:  goto 20
18:  move 5, _7
19:  move _7, _2
20:  bne _2, _0, 25
21:  goto 22
22:  add _2, _1, _8
23:  beq _8, _0, 25
24:  goto 27
25:  move 20, _9
26:  move _9, _1
27:  free 10
28:  PROCEND

}