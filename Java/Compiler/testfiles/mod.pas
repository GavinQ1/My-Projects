program modTest (input, output);   {Test for the better mod function}
 var 
 	a, b, c, d, e, f : integer;
 	p : real;
  
  
 begin
   
   a := 5 mod 7;
   b := 13 mod 7;
   c := - 14; 
   d := -13 mod 7;
   e := 13 mod (-7);
   f := -13 mod (-7);
   write(a,b,c,d,e,f);
   
   p := -1.3;
   write(p)
  
 end.