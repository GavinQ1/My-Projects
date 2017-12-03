

program uminusTest (input, output);
 var a, b : integer;
        c : real;
  function two (i, j : integer): result integer;
    var n : integer;
    begin
      n := i + j;
      two := -n
    end
  begin
    a := 1;
    b := 2;
    c := a+b+two(a,b);
    write(c)
  end.