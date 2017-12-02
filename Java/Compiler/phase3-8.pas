Program reltest (input,output);
Var
    h,i,j,k,l:integer;
    m,o : array[1..5] of integer;
    n : array[0..4] of real;
    s,t,u,v,x : real;    
    
begin

h := -1;
i := 0;
j := 1;
k := 2;
l := 3;


h := h * (-1) + 3;

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
	 j := j +1
		
	end;
	

while (((i <= h) or (m[5] > 8)) and (o[1] < 35)) do
	begin
		i := i + 1
	end
	
end.

{Output:

Program reltest (input,output);
Var
    h,i,j,k,l:integer;
    m,o : array[1..5] of integer;
    n : array[0..4] of real;
    s,t,u,v,x : real;    
    
begin

h := -1;
i := 0;
j := 1;
k := 2;
l := 3;


h := h * (-1) + 3;

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
	 j := j +1
		
	end;
	

while (((i <= h) or (m[5] > 8)) and (o[1] < 35)) do
	begin
		i := i + 1
	end
	
end.

{Output:

CODE
1:  call main, 0
2:  exit
3:  PROCBEGIN main
4:  alloc 100
5:  move 1, _26
6:  uminus _26, _25
7:  move _25, _4
8:  move 0, _27
9:  move _27, _3
10:  move 1, _28
11:  move _28, _2
12:  move 2, _29
13:  move _29, _1
14:  move 3, _30
15:  move _30, _0
16:  move 1, _32
17:  uminus _32, _31
18:  mul _4, _31, _33
19:  move 3, _35
20:  add _33, _35, _34
21:  move _34, _4
22:  move 1.1, _37
23:  fuminus _37, _36
24:  move _36, _24
25:  move 1.1, _38
26:  move _38, _23
27:  move 2.1, _39
28:  move _39, _22
29:  move 3.1, _40
30:  move _40, _21
31:  move 4.1, _41
32:  move _41, _20
33:  move 1, _43
34:  move 1, _44
35:  sub _43, _44, _42
36:  move 1, _45
37:  stor _45, _42, _5
38:  move 2, _47
39:  move 1, _48
40:  sub _47, _48, _46
41:  move 2, _49
42:  stor _49, _46, _5
43:  move 3, _51
44:  move 1, _52
45:  sub _51, _52, _50
46:  move 3, _53
47:  stor _53, _50, _5
48:  move 4, _55
49:  move 1, _56
50:  sub _55, _56, _54
51:  move 4, _57
52:  stor _57, _54, _5
53:  move 5, _59
54:  move 1, _60
55:  sub _59, _60, _58
56:  move 5, _61
57:  stor _61, _58, _5
58:  move 1, _63
59:  add _3, _63, _62
60:  move 1, _65
61:  sub _62, _65, _64
62:  stor _4, _64, _10
63:  move 1, _67
64:  sub _1, _67, _66
65:  stor _3, _66, _10
66:  move 1, _69
67:  sub _0, _69, _68
68:  stor _2, _68, _10
69:  move 1, _71
70:  sub _4, _71, _70
71:  stor _1, _70, _10
72:  add _2, _4, _72
73:  move 1, _74
74:  sub _72, _74, _73
75:  stor _0, _73, _10
76:  move 0, _76
77:  sub _4, _76, _75
78:  stor _24, _75, _15
79:  move 0, _78
80:  sub _3, _78, _77
81:  stor _23, _77, _15
82:  move 0, _80
83:  sub _2, _80, _79
84:  stor _22, _79, _15
85:  move 0, _82
86:  sub _1, _82, _81
87:  stor _21, _81, _15
88:  move 0, _84
89:  sub _0, _84, _83
90:  stor _20, _83, _15
91:  move 6, _85
92:  blt _2, _85, 94
93:  goto 98
94:  move 1, _87
95:  add _2, _87, _86
96:  move _86, _2
97:  goto 91
98:  ble _3, _4, 107
99:  goto 100
100:  move 5, _89
101:  move 1, _90
102:  sub _89, _90, _88
103:  load _10, _88, _91
104:  move 8, _92
105:  bgt _91, _92, 107
106:  goto 118
107:  move 1, _94
108:  move 1, _95
109:  sub _94, _95, _93
110:  load _5, _93, _96
111:  move 35, _97
112:  blt _96, _97, 114
113:  goto 118
114:  move 1, _99
115:  add _3, _99, _98
116:  move _98, _3
117:  goto 98
118:  free 100
119:  PROCEND
}




