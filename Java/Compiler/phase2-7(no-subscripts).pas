program expressionTest (input, output);

 var a, b : integer;
        c : real;
        x : array [0..5] of real;
 begin
   a := b - 4;
   c := ((b + a)/ 2) mod b
 end.
 
 { Output : 
     >>> ERROR on line 10 : Operands of the MOD operator must both be of type integer
 }