import subprocess, sys, os, getopt

default_path = '.'
base_command = ['java', '-jar', 'tvi-2.2.0.jar']

def write(path, content):
    f_basename, f_ext = os.path.splitext(path)
    with open('output-%s.txt'%f_basename, 'w') as f:
        f.write(content)

def main():
    opts, args = getopt.getopt(sys.argv[1:], 'haod:f:')

    ALL = 0
    OUT = 0
    path = default_path
    f = None
    o = None
    for op, val in opts:
        if op == '-a':
            ALL = 1
        elif op == '-d':
            path = val
        elif op == '-f':
            f = val
        elif op == '-o':
            OUT = 1
        else:
            print '''-h: show help\n
-a: flag, if run all compiled files in directory, default is false\n
-f [filename]: run a single file\n
-d [dir]: run all files in given directory, defualt is current path\n
-o: flag, if write output in a file, default is false
'''
            return

    if ALL:
        assert path, 'Should give a directory!'
        for f in os.listdir(path):
            f_basename, f_ext = os.path.splitext(f)
            if f_ext == '.tvi':
                res = subprocess.check_output(base_command + [os.path.join(path, f)])
                print "\n**************************"
                print "Output for %s:\n"%f
                print res
                if OUT:
                    write(f, res)
                print "**************************\n"
    else:
        assert f, 'Should give a file!'
        res = subprocess.check_output(base_command  + [f])
        print "\n**************************"
        print "Output for %s:\n"%f
        print res
        if OUT:
            write(f, res)
        print "**************************\n"
         

if __name__ == '__main__':
    try:
        print "\n This helper script does NOT work for programs that need input!\n" 
        main()
    except Exception as e:
        print e


