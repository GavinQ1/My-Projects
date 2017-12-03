Program recursionTest (input,output);
Var
   x,y : integer;

function gcd (a, b: integer) : result real;
   var x : integer;
begin
   if b= 0 then gcd := a
   else begin
     x := a;
     while (x >= b) do
      begin
        x := x - b
      end;
      gcd := gcd(x,b)
     end
end
begin
   read (x,y);
   if x>y then write (gcd(x, y))
end.