program math(input, output);
var
	i: integer;
begin
	i := (1 + 3) * 4;
	if i = 16 then
		write(16)
	else write(i);


	i := 1 + 3 * 4;
	if i = 13 then
		write(13)
	else write(i);

	i := 16 div 2 + 3;
	if i = 11 then
		write(11)
	else 
		write(i);
	

	i := 32769 * 2 - 2;
	if i =  65536 then
		write(65536)
	else 
		write(i)		
end.