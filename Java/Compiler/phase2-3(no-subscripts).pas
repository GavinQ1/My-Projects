program expressionTest (input, output);

 var a, b : integer;
        c : real;
        x : array [0..5] of real;
 begin
   b := a - 4;
   c := ((b + a)/ 2) div (c-b)
 end.
 
{ Output:
>>> ERROR on line 10 : Operands of the DIV operator must both be of type integer

}