import re, time, threading, os, sys, getpass
from string import lowercase
from itertools import permutations
try:
    from bs4 import BeautifulSoup
    from seleniumrequests import PhantomJS
    from selenium.webdriver.common.keys import Keys
except ImportError as e:
    print e

_CONTENT = ['\n', '\t', ' ']
def clean_content(s):
    for c in _CONTENT:
        s = s.replace(c, '')
    return s

def permutation(s, n):
    for i in permutations(s, n):
        res = ''
        for l in range(n):
            res += i[l]
        yield res

threadLimiter = threading.BoundedSemaphore(150)
class MyThread(threading.Thread):
    def __init__(self, func, args, name=''):
        threading.Thread.__init__(self)
        self.name = name
        self.func = func
        self.args = args

    def run(self):
        threadLimiter.acquire()
        try:
            self.res = apply(self.func, self.args)
        finally:
            threadLimiter.release()

class Vassar_Downloader(object):
    err_log_name = 'ERROR_LOG.txt'
    download_log = "temp.txt"
    raw_file_folder = 'Raw files -- %s'%str(time.localtime()[0:3])

    def __init__(self, urn=None, pwd=None, init_dict=[res for res in permutation(lowercase, 2)]):
        if not os.path.exists(self.raw_file_folder):
            os.mkdir(self.raw_file_folder)
        os.chdir(self.raw_file_folder)
        if not urn:
            urn = raw_input('Enter the username: ').strip()
            print ''
        else:
            print 'Username: ', urn, '\n'
        if not pwd:
            pwd = getpass.getpass('Enter the password: ').strip()
            os.system('cls')
        with open(self.err_log_name, 'w') as f:
            f.write('Starting crawling from %s...\n'%time.ctime())
            
        self.url = 'https://aisapps.vassar.edu/directory/internal_auth_studir_srch.php'
        
        print 'Initializing...\n'
        self.browser = PhantomJS()
        
        self.i = 0

        self.urn = urn
        self.pwd = pwd
        self.DICT = init_dict

        self.searchReq = 'https://aisapps.vassar.edu/directory/internal_auth_studirctry_rslts.php'

        self.init()

    def init(self):
        self.if_continue()
        self.cluster()

    def if_continue(self):
        self.finished_list = []
        if os.path.exists(self.download_log):
            with open(self.download_log, 'r') as f:
                data = f.read()
                self.finished_list = data.split(',')
                self.finished_list.pop(-2)
                self.waiting_list = filter(lambda x : x not in self.finished_list, self.DICT)
                self.status = "Continue from the last dumping... %d / %d\n"%(len(self.DICT)-len(self.waiting_list), len(self.DICT))
                
                print self.status
        else:
            self.waiting_list = self.DICT

    def login(self):
        print 'Connecting to "%s"...'%self.url
        self.browser.get(self.url)
        self.status = '\nTrying to login as %s...'%self.urn
        print self.status
        
        username = self.browser.find_element_by_name("username")
        password = self.browser.find_element_by_name("password")
        username.send_keys(self.urn)
        password.send_keys(self.pwd)
        login_attempt = self.browser.find_element_by_xpath("//*[@type='submit']")
        login_attempt.submit()
        return self.browser.page_source
        
    def request(self, data, method='POST', error_time=0):
        filename = 'source - %s.txt'%(str(data.values()))
        #print 'downloading %s...'%filename

        try:
            resp = self.browser.request(method, self.searchReq, data=data)
            with open(filename, 'w') as f:
                #f.write(clean_content(resp.content))
                f.write(resp.content)
            temp = data['lastname']
            self.finished_list.append(temp)
            self.waiting_list.remove(temp)
            self.status = '%d / %d done...'%(self.total-len(self.waiting_list), self.total)
            
            print self.status
            with open(self.download_log, 'a') as f:
                f.write('%s,'%temp)

        except Exception as e:
            error_time += 1
            if error_time <= 3:
                self.logger('Error! %s failed %d times.'%(str(data), error_time))
                self.request(data, error_time=error_time)
            else:
                self.logger('<<Error! %s not downloaded!>>'%(str(data)))
            
    def cluster(self):
        s = self.login()
        if 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd' not in s:
            self.status = '\nAuthentification failed.\n'
            
            print self.status
            self.logger('Authentification failed.')
            return None
        else:
            self.status = '\nAuthentification succeeded.\n\nDownloading data...\n\n'
            print self.status

        self.total = len(self.DICT)
        threads = [MyThread(self.request, ({'lastname': ln}, 'POST'), name=ln) for ln in self.waiting_list]
        for t in threads:
            t.start()

        for t in threads:
            t.join()

        self.status = '\nClearing cache...\n'
        print self.status
        os.remove(self.download_log)
        print 'Done...\n'

    def logger(self, msg):
        with open(self.err_log_name, 'a') as f:
            f.write('%s --- %s\n'%(msg, time.ctime()))


def main():
    Vassar_Downloader()
    input()

if __name__ == '__main__':
    main()
    
