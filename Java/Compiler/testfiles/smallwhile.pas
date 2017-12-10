program whiletest(input, output); 
{big old test file! Tests pretty much everything, recursion, while, if, else, procedure, function (with and without parameters, arrays, expressions, etc...}
var
    k:integer;
    v,x : real;    

procedure testb(a,b:integer; c,d,e,f : real );
begin 
	write(a);
	write(b);
	write(c);
	write(d);
	write(e);
	write(f)
end
	
begin


k := 2;

v := 3.1;
x := 4.1;


x := 5 * (1 + 2 + 3.1 - 4.3 + 0.2) / 4;

v := k * v;
write(x);
write(v);
testb(1,2,x,x,x,x)
end.