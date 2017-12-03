program whiletest(input, output); 
{big old test file! Tests pretty much everything, recursion, while, if, else, procedure, function (with and without parameters, arrays, expressions, etc...}
var
    h,i,j,k,l:integer;
    m,o : array[1..5] of integer;
    n : array[0..4] of real;
    s,t,u,v,x : real;    

function testi(a,b:integer): result integer;
begin

	if a < b then 
	testi := b
	else testi := testi(a,b+1)
		
end

function sum(a,b: real): result real;
begin
	sum := a+b
end

function externalsum: result integer;
begin
	externalsum := i +j
end

function testc(a,b: array[1..5] of integer): result integer;
begin
	
	
	write(a[1] + b[1]);
	write(a[2] + b[2]);
	write(a[3] + b[3]);
	testc := 500
end


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

h := -1;
i := 0;
j := 1;
k := 2;
l := 3;

write("h = -1: "h);

h := h * (-1) + 3;
write(h);

s := -1.1;
t := 1.1;
u := 2.1;
v := 3.1;
x := 4.1;

o[1] := 1;
o[2] := 2;
o[3] := 3;
o[4] := 4;
o[5] := 5;

m[i+1] := h;
m[k] := i;
m[l] := j;
m[h] := k;
m[j+h] := l;

n[h] := s;
n[i] := t;
n[j] := u;
n[k] := v;
n[l] := x;


while j < 6 do 
	begin
	 write(j);
	 write( m[j]);
	 j := j +1
		
	end;
	

while i <= h do
	begin
		write(i);
		write(n[i]);
		i := i + 1
	end;
	
write(testi(4,1));

x := 5 * (1 + 2 + 3.1 - 4.3 + 0.2) / 4;
l := 5*7;
v := k * v;
write(x);
write(l);
write(v);

i := m[3] + 4;
write(i);


m[1] := 1;
m[2] := 2;
m[3] := 3;
m[4] := 4;
m[5] := 5;

i := testc(m,o);
write(i);

	
	i := 0;
	J := 0;
	if i = 0 then 
		write(0)
	else if i = 1 then
		write(1)
	else if i = 2 then
		write(2)
	else write (99);

	i := 1;
	if i = 0 then 
		write(0)
	else if i = 1 then
		write(1)
	else if i = 2 then
		write(2)
	else write (99);

	i := 2;
        if i = 0 then
                   write(0)
        else if i = 1 then
                   write(1)
        else if i = 2 then
                   write(2)
        else write (99);
	
	i := 3;
	if i = 0 then
		   write(0)
	else if i = 1 then
		   write(1)
	else if i = 2 then
		   write(2)
        else write (99);
        
        if i < 0 then 
        	write(0)
         else write(1);
        
        
        if i <= j then 
	        write(0)
	else write(1);
        
        if i <> j then 
        	write(0)
	else write(1);
        
        if i >=j then 
        	write(0)
        else write(1);
        
        if i > j then 
        	write(0)
        else write(1);
        
write(i);
write(j);
write(sum(externalsum+1.1, 3.1));

testb(1,2,x,x,x,x)
end.