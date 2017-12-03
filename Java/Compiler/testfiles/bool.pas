program boolean(input, output);
var
	i, j: integer;

begin
	i := 10;
	j := 20;

	if (i = 10) and (j = 20) then
		write(i,j)
        else
		write(j);

	if (i = 10) and (j = 10) then
		write(j,i)
	else
		write(i);

	if (i = 10) or (j = 10) then
		write(i,i)
	else
		write(j,j);

	if (i = 20) or (j = 10) then
		write(i,j,i)
	else
		write(j,i,j);

	if not(i = 20) then
		write(i,j,i,j)
	else
		write(j,i,j,i)

end.
