
program funcTest(input, output);        
var
        i, j, k:integer;

function Sum(a,b:integer): result integer;
begin
        Sum := a + b
end

begin
        i := 10;
        j := 20;
        k := Sum(i,j) * 2;
        write(k)
end.