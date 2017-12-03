program fibTest(input, output);   {Sample tvi code here}
var
        i, done: integer;
function fib(n:integer) : result integer;
var
        j, k : integer;
begin
        if n = 1 then
                fib := 1
        else if n = 2 then
                fib := 1
        else
                fib := fib(n-1) + fib(n-2)
end
begin
        done := 0;
        while done = 0 do begin
                read(i);
                if i = 0 then
                        done := 1
                else begin
                        write(i);
                        i := fib(i);
                        write(i)
                end    { else part }
        end    { while }
end.

