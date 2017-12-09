program expressionTest (input, output);


 var a, b : integer;
        c : real;
        x : array [0..5] of real;
 begin
   b := a * 4;
   c := (b + a)/ 2;
   b := c
 end.
 
 {Output:
    >>> ERROR on line 12 : Cannot assign real value to integer variable
 }