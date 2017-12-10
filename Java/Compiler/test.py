import subprocess, getopt, sys


def main():
    res = subprocess.check_output(['java', '-jar', 'tvi-2.2.0.jar', sys.argv[1]])
    print res     

if __name__ == '__main__':
    main()
