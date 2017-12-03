Program theUltimateTest (input,output);
{
    This file may contain some INTENTIONAL BUGS to test your compiler.
    As they are encountered, you should fix them and re-run until the 
    program runs.
}
 
Var
   h,z,i,x,y : integer;
   w : array [1..5] of integer;
   rl : real;

function gcd (a, b : integer) : result integer;
   var x : integer;
begin
   if (b = 0) then gcd := a
   else begin
     x := a;
     while (x >= b) do 
      begin
        x := x - b
      end;
      gcd := gcd(b,x)
     end
end

procedure this (why : integer; note : real);
begin
   { The comparison checks how you handle arithmetic 
     and comparisons with mixed type numbers  }
   if ((why = note - 1608) or (not (note = why)))
   then if (x - y = 0)
      then begin
         w[i] := why DIV note
         end
end

procedure that;
var h : integer;
    z : real;
begin
   h := 1;
   z := 7.43;
   x := y;
   this (h,z)
end

begin
   i := 1;
   x := 5;
   While (I <= 5) and (x <= 75) do
   begin
      w [i] := x;
      w[x] := w[i] * 20;
      i := i + 1 
      end;
   read (x,y);
   if x>y then write (gcd(x, y)) else write (gcd (h,z));
   w[x] := 6758;
   rl := 23e10;
   write(w[x]);
   this (gcd(x,y),rl);
   this (w[x],rl);
   that;
   i := 1;
   while (i <= 5) do
     begin
       write(w[i]);
       i := i + 1
     end;
   write(h,i,x,y,z)
end.

