import subprocess, sys, os, getopt

default_path = 'testfiles'
base_command = ['java', '-jar', 'Compiler.jar']

def main():
    opts, args = getopt.getopt(sys.argv[1:], 'havd:f:')

    ALL = 0
    SHOW = 0
    path = default_path
    f = None
    for op, val in opts:
        if op == '-a':
            ALL = 1
        elif op == '-d':
            path = val
        elif op == '-f':
            f = val
        elif op == '-v':
            SHOW = 1
        else:
            print '''-h: show help\n
-a: flag, if compile all files in directory, default is false\n
-f [filename]: compile a single file\n
-d [dir]: compile all files in given directory\n
-v: flag, if show output in console, default is false
'''
            return

    if ALL:
        assert path, 'Should give a directory!'
        for f in os.listdir(path):
            f_basename, f_ext = os.path.splitext(f)
            if f_ext == '.pas':
                res = subprocess.check_output(base_command + [os.path.join(path, f)])
                if SHOW:
                    print res
    else:
        assert f, 'Should give a file!'
        res = subprocess.check_output(base_command  + [f])
        if SHOW:
            print res
         

if __name__ == '__main__':
    try:
        main()
    except Exception as e:
        print e
