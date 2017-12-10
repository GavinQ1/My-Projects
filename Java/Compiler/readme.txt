# java command 

java -jar Compiler.jar [path/filename]



# compile helper

python compile.py [-a] [-d:dir] [-f:filename] [-h] [-n]

-h: show help
-a: flag, if compile all files in directory, default is false
-f [filename]: compile a single file
-d [dir]: compile all files in given directory
-n: flag, if show output in console, default is true


# run compiled files

python run.py [-a] [-d:dir] [-f:filename] [-h] [-o]

-h: show help
-a: flag, if run all compiled files in directory, default is false
-f [filename]: run a single file
-d [dir]: run all files in given directory, defualt is current path
-o: flag, if write output in a file, default is false