import re, os, cPickle as pickle
from bs4 import BeautifulSoup
from Student import Student, Dormitory
from progress_animation import Progress_bar

_SIZE = 2086

_CONTENT = ['\n', '\t']
def clean_content(s):
    for c in _CONTENT:
        s = s.replace(c, '')
    return s

_TAG = ['<b>', '</b>', '<td>',
        '<td colspan="3">', '</td>',
        '</tr>', '<ahref>', '</ahref>',
        '</tdrowspan>', '<br/>', '<hr>'
        '</tdcolspan>', '<td colspan="6">', ]
def remove_tag(s):
    s = str(s)
    for t in _TAG:
        s = s.replace(t, '')
    return s

class My_Parser(object):
    def __init__(self, fileList):
        self.students = dict()
        self.file_list = fileList

        bar = Progress_bar(len(fileList), 'Processing files...')
        for f in fileList:
            bar.animation()
            sz = os.path.getsize(f)
            if sz <= _SIZE or not f.endswith('txt'):
                continue
            self.file_handler(f)
        self.class_years = dict()
        self.dorms = dict()
        self.home_codes = dict()
        self.home_cities = dict()
        self.class_year_handler()
        self.dorm_handler()
        self.home_cities_handler()
        self.home_codes_handler()

    def export_pic_by_class_year(self):
        init = os.getcwd()
        for y in self.class_years:
            if not os.path.exists(y):
                os.mkdir(y)
            os.chdir(y)
            for s in self.class_years[y]:
                s.export_pic()
            os.chdir(init)

    def show_dormlist(self):
        return sorted(self.dorms.keys())

    def show_namelist(self):
        return sorted(self.students.keys())

    def show_class_year_list(self):
        return sorted(self.class_years.keys())

    def show_class_year_namelist(self, y):
        assert y in self.class_years
        return sorted(self.class_years[y])
        
    def class_year_handler(self):
        d = self.students
        for s in d:
            y = d[s].class_year
            self.class_years[y] = self.class_years.get(y, [])
            self.class_years[y].append(d[s].name)

    def home_cities_handler(self):
        d = self.students
        for s in d:
            hc = d[s].home_city
            self.home_cities[hc] = self.home_cities.get(hc, [])
            self.home_cities[hc].append(d[s].name)

    def dorm_handler(self):
        d = self.students
        for s in d:
            dorm, room = d[s].dorm_info()
            self.dorms[dorm] = self.dorms.get(dorm, Dormitory(dorm))
            self.dorms[dorm].add_student(d[s].name, room)

    def home_codes_handler(self):
        d = self.students
        for s in d:
            hc = d[s].home_state
            self.home_codes[hc] = self.home_codes.get(hc, [])
            self.home_codes[hc].append(d[s].name)
            
    
    def file_handler(self, filename):
        p_pic = re.compile("<td rowspan='6'>.*?/>")
        with open(filename, 'r') as f:
            f_str = clean_content(f.read())
            pics = re.findall(p_pic, f_str)
            s = re.sub(p_pic, '', f_str)

        soup = BeautifulSoup(s, "lxml")
        table = soup.find('div', attrs={'class': 'container-formtable'})
        rows = table.findAll('tr')
        processed_rows = list()
        for tr in rows:
            cols = tr.findAll('td')
            l = []
            for i in cols:
                i = remove_tag(i)
                l.append(i.strip())
            if not l:
                continue
            processed_rows.append(l)

        count = 0
        for r in processed_rows:
            if count == 0:
                temp = Student()
                temp.add_pic(pics.pop(0))
                count += 1
                continue
            if count == 7:
                self.students[temp.name] = self.students.get(temp.name, temp)
                count = 0
                continue
            temp.handler(r, count)
            count += 1

def test():
    fileList = os.listdir('.')
    t = My_Parser(fileList)
    saves = [t]
    pickle.dump(saves, open('2016-fall-vassar.p', 'wb'), pickle.HIGHEST_PROTOCOL)
    print 'done...'
    input()

#test
if __name__ == '__main__':
    test()
    
    
    

        
