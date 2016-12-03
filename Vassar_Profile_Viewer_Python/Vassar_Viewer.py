import os, StringIO, re, tkMessageBox
from Tkinter import *
from ttk import *
from PIL import Image, ImageTk
from Vassar_Processor import My_Parser

try:
    import cPickle as pickle
except ImportError:
    import pickle

__author__ = 'Gavin'

class Simple_Q(object):
    def __init__(self):
        self.q = []

    def __str__(self):
        return str(self.q)

    def size(self):
        return len(self.q)

    def isEmpty(self):
        return len(self.q) == 0

    def enqueue(self, stuff):
        self.q = [stuff] + self.q

    def dequeue(self):
        if not self.isEmpty():
            return self.q.pop()
        return ""

class Vassar_Viewer(Frame):
    _CONFIG = 'configure.txt'
    search_menu = ['lastname', 'firstname', 'class-year', 'dorm', 'room', 'home-city', 'home-code',
                   'dorm-room', 'home code', 'home city', 'class year', 'dorm-list', 'clear']
      
    def __init__(self, parent):
        Frame.__init__(self, parent)   
         
        self.parent = parent

        self.loader = None

        self.alone = False
        self.setup()
        
        
    def setup(self):
        _LOADY = 430
        _SEARY = 465
      
        self.parent.title("Vassar Profile Viewer")          
        self.pack(fill=BOTH, expand=True)
        
        try:
            bg = Image.open('bg.jpg')
            bg = bg.resize((600, 800), Image.ANTIALIAS)
            bg = ImageTk.PhotoImage(bg)
            bglabel = Label(self, image=bg)
            bglabel.grid(row=0, sticky='NW')
            bglabel.image = bg
        except:
            pass

        ####
        results_lb = LabelFrame(self, text='Search Results')
        results_lb.grid(row=0, padx=1)
        
        #result label
        self.res_lb = Label(results_lb, text='Result List')
        self.res_lb.grid(row=1, sticky='NW', padx=1, pady=1, ipadx=1, ipady=1)

        #subresult label
        self.sub_res_lb = Label(results_lb, text='SubResult List')
        self.sub_res_lb.grid(row=1, column=1, sticky='WE', padx=1, pady=1, ipadx=1, ipady=1)
        
        #result listbox
        self.res_list_box = Listbox(results_lb, height=21)
        self.res_list_box.bind("<<ListboxSelect>>", self._onSelect_res)                
        #self.res_list_box.pack(pady=15, fill=BOTH)
        #self.res_list_box.place(x=30, y=60)
        self.res_list_box.grid(row=2, column=0, columnspan=1, rowspan=1, sticky='W', padx=10, pady=5)

        #subResult listbox
        self.sub_res_box = Listbox(results_lb, height=21)
        self.sub_res_box.bind("<<ListboxSelect>>", self._onSelect_sub_res)
        self.sub_res_box.grid(row=2, column=1, columnspan=1, rowspan=1, sticky='WE', padx=10, pady=5)

        #load info
        self.load_label = LabelFrame(self, text='Dataset Loaded: None')
        self.load_label.grid(row=3, sticky='W', padx=2, pady=3)

        #load button
        self.load_bu = Button(self.load_label, text='Load File', command=self._load_file)
        self.load_bu.grid(row=4, sticky='W')

        #load entry box
        self.load_en = Entry(self.load_label, width=33)
        self.load_en.grid(row=4, column=1, sticky='W')

        if os.path.exists(self._CONFIG):
            with open(self._CONFIG, 'r') as f:
                filename = f.read()
                if os.path.exists(filename):
                    self._load_file(filename)

        ####
                    
        #search button
        search_bu = Button(self.load_label, text='Search', command=self._search)
        search_bu.grid(row=5, column=0, sticky='W')
        
        #search entry box
        self.search_en = Entry(self.load_label, width=33)
        self.search_en.grid(row=5, column=1, sticky='W')

        #search menu
        search_mb = Menubutton(self.load_label, text='option')
        search_mb.menu = Menu(search_mb, tearoff=0)
        search_mb['menu'] = search_mb.menu
        op = self.search_menu
        
        search_mb.menu.add_command(label=op[0], command=lambda : self._search_option(op[0]))
        search_mb.menu.add_command(label=op[1], command=lambda : self._search_option(op[1]))
        search_mb.menu.add_command(label=op[2], command=lambda : self._search_option(op[2]))
        search_mb.menu.add_command(label=op[3], command=lambda : self._search_option(op[3]))
        search_mb.menu.add_command(label=op[4], command=lambda : self._search_option(op[4]))
        search_mb.menu.add_command(label=op[5], command=lambda : self._search_option(op[5]))
        search_mb.menu.add_command(label=op[6], command=lambda : self._search_option(op[6]))

        search_mb.menu.add_separator()
        search_mb.menu.add_command(label=op[-6], command=lambda : self._search_option(op[-6], alone=True))
        
        search_mb.menu.add_command(label=op[-5], command=lambda : self._default_search(op[-5]))
        search_mb.menu.add_command(label=op[-4], command=lambda : self._default_search(op[-4]))
        search_mb.menu.add_command(label=op[-3], command=lambda : self._default_search(op[-3]))
        search_mb.menu.add_command(label=op[-2], command=lambda : self._default_search(op[-2]))

        search_mb.menu.add_separator()
        search_mb.menu.add_command(label=op[-1], command=self._clear_search_en)
        
        search_mb.grid(row=6, column=0, sticky='W')

        ####
        author = Label(self.load_label, text='Presented by --- An Anonymous Eggplant')
        author.grid(row=8)
        ####

        #info box
        info_frame = LabelFrame(self, text='Profile')
        info_frame.pack(fill="both", expand="yes")
        info_frame.grid(row=0, column=3, sticky='E', padx=5)

       ####
        _IMGSIZE = 220, 250
        #image box
        img_frame = LabelFrame(info_frame, width=_IMGSIZE[0], height=_IMGSIZE[1])
        img_frame.pack(fill="both", expand="yes")
        img_frame.grid(row=1, column=3)

        #image
        self.imageName = Label(img_frame, text='Welcome')
        self.imageName.pack()

        try:
            idphoto = Image.open('Welcome.jpeg')
            idphoto = idphoto.resize(_IMGSIZE, Image.ANTIALIAS)

            idphoto = ImageTk.PhotoImage(idphoto)        
            self.imglb = Label(img_frame, image=idphoto)
            self.imglb.image = idphoto
            self.imglb.pack()
        except:
            self.imglb = Label(img_frame)
            self.imglb.pack() 
        
        self.info_title = Label(info_frame, text='Profile')
        self.info_title.grid(row=2, column=3)

        self.info_text = Text(info_frame, width=35, height=21)
        self.info_text.grid(row=3, column=3)

        ####

    def _change_img(self, obj):
        try:
            self.imageName.config(text=obj.name)
            _IMGSIZE = 220, 250
            tempBuff = StringIO.StringIO()
            tempBuff.write(obj.img)
            tempBuff.seek(0)
            idphoto = Image.open(tempBuff)
            idphoto = idphoto.resize(_IMGSIZE, Image.ANTIALIAS)
            idphoto = ImageTk.PhotoImage(idphoto)
            self.imglb.config(image=idphoto)
            self.imglb.image = idphoto
        except:
            self._clear_image()
            self.imageName.config(text='NONE')
            self.error_message_box('Image not found!')
    
    def _change_profile(self, obj):
        self.info_text.delete('1.0', END)
        self.info_text.insert(INSERT, str(obj))
        self.info_title.config(text='Profile of %s'%obj.name)

    def _clear_profile(self):
        self.info_text.delete('1.0', END)
        self.info_title.config(text='Profile')

    def _clear_image(self):
        self.imageName.config(text='NONE')
        _IMGSIZE = 220, 250
        try:
            idphoto = Image.open('Welcome.jpeg')
            idphoto = idphoto.resize(_IMGSIZE, Image.ANTIALIAS)

            idphoto = ImageTk.PhotoImage(idphoto)
            self.imglb.config(image=idphoto)
            self.imglb.image = idphoto
        except Exception as e:
            self.imglb.config(image=None)
            self.imglb.image = None

    def _clear_all(self):
        self._clean_listbox()
        self._clear_profile()
        self._clear_image()
        
    def _onSelect_res(self, val):
        try:
            assert self.results, 'Operation Not Supported'
        except AssertionError as e:
            print e
            self.error_message_box(e)
            return None
            
        sender = val.widget
        idx = sender.curselection()
        value = sender.get(idx)

        obj = self.results[value]
        if 'Student.Student' in str(obj.__class__):
            self._change_img(obj)
            self._change_profile(obj)
        elif 'Student.Room' in str(obj.__class__):
            res = obj.show_namelist()
            self.sub_res_lb.config(text='Room %s: %d found'%(obj.name, len(res)))
            self.sub_res_box.delete(0, self.sub_res_box.size())
            self.sub_results = {}
            for s in res:
                self.sub_res_box.insert(END, s)
                self.sub_results[s] = self.sub_results.get(s, self.loader.students[s])
        else:
            res = sorted(obj)
            self.sub_res_lb.config(text='SubResult List: %d found'%len(res))
            self.sub_res_box.delete(0, self.sub_res_box.size())
            self.sub_results = {}
            for s in res:
                self.sub_res_box.insert(END, s)
                self.sub_results[s] = self.sub_results.get(s, self.loader.students[s])

    def _onSelect_sub_res(self, val):
        sender = val.widget
        idx = sender.curselection()
        value = sender.get(idx)

        obj = self.sub_results[value]
        if 'Student.Student' in str(obj.__class__):
            self._change_img(obj)
            self._change_profile(obj)

    def _load_file(self, config=False):            
        if not config:
            filename = self.load_en.get().strip()
            with open(self._CONFIG, 'w') as f:
                f.write(filename)
        else:
            filename = config
            self.load_en.delete(0, len(self.load_en.get()))
            self.load_en.insert(0, filename)
        try:
            assert os.path.exists(filename), '"%s" does not exist.'%filename
            self.loader = Loader(filename)
            self.load_label.config(text="Loaded Dataset: %s"%os.path.basename(filename))
        except AssertionError as e:
            self.error_message_box(e)

    def _search_option(self, op, alone=False):
        self.alone = alone
        if self.alone:
            self._clear_search_en()

        i = len(self.search_en.get())
        insert = '%s: '%op
        
        self.search_en.insert(i, insert)

    def _clean_listbox(self):
        self.res_list_box.delete(0, self.res_list_box.size())
        self.sub_res_box.delete(0, self.sub_res_box.size())
        self.res_lb.config(text='Result List')
        self.sub_res_lb.config(text='SubResult List')

    def _clear_search_en(self):
        self.search_en.delete(0, len(self.search_en.get()))
        self._clear_all()

    def _search(self):
        self._clear_all()
        
        raw_commands = self.search_en.get().strip()
        try:
            assert self.loader is not None, 'Please load a dataset'
            #assert len(raw_commands) > 0, 'Please enter command to search'
        except AssertionError as e:
            self.error_message_box(e)
            
        command = ""
        i = 0

        keys = Simple_Q()
        vals = Simple_Q()
        ops = Simple_Q()
        search_range = []
        self.results = {}
        rlen = len(raw_commands)
        
        while i < rlen:
            l = raw_commands[i]
            if l == ':':
                keys.enqueue(command)
                command = ""
            elif l == ' ':
                pass
            elif l == '&':
                vals.enqueue(command)
                ops.enqueue(l)
                
                command = ""
                if i+1 < rlen and raw_commands[i+1] == '&':
                    i += 1
            elif l == '|':
                vals.enqueue(command)
                ops.enqueue(l)
                
                command = ""
                if i+1 < rlen and raw_commands[i+1] == '|':
                    i += 1
            else:
                command += l
            i += 1

        if command != "":
            vals.enqueue(command)
        
        try:
            if not self.alone:
                assert not keys.isEmpty() and not vals.isEmpty(), 'Search command not completed.'
                search_range = self.loader.search(keys.dequeue(), vals.dequeue(), search_range)
                while not vals.isEmpty():
                    if not ops.isEmpty():
                        op = ops.dequeue()
                        if op == '&':
                            search_range = self.loader.search(keys.dequeue(), vals.dequeue(), search_range)
                        elif op == '|':
                            search_range.extend(self.loader.search(keys.dequeue(), vals.dequeue(), []))
            else:
                dorm = self.loader.search('d', vals.dequeue())
                self.results = dorm.rooms
                dorm_name = dorm.name
                if len(dorm_name.split(' ')) >= 2:
                    dorm_name = ' '.join(dorm_name.split(' ')[-2::])
                    keys = sorted(self.results.keys())
                    for k in keys:
                        self.res_list_box.insert(END, k)
                    self.res_lb.config(text='%s: %d found'%(dorm_name, len(keys)))
                    return None
        except Exception as e:
            self.error_message_box('Operation Not Supported: %s'%e)
            return None

        self.res_lb.config(text='Result List: %d found'%len(search_range))
        self.res_list_box.delete(0, self.res_list_box.size())
        for s in search_range:
            self.res_list_box.insert(END, s)
            self.results[s] = self.results.get(s, self.loader.students[s])

    
    def _default_search(self, key):
        self._clear_all()
        
        try:
            assert self.loader is not None, 'Please load a dataset'
        except AssertionError as e:
            self.error_message_box(e)

        self.results = {}
        try:
            if key == 'dorm-list':
                for d in sorted(self.loader.dorms.keys()):
                    dorm = self.loader.dorms[d]
                    self.res_list_box.insert(END, d)
                    self.results[d] = self.results.get(d, dorm.show_namelist())
            elif key == 'home city':
                self.results = self.loader.home_cities
            elif key == 'class year':
                self.results = self.loader.class_years
            elif key == 'home code':
                self.results = self.loader.home_codes
        except Exception as e:
            self.error_message_box('Operation Not Supported: %s'%e)
            return None
    
        keys = self.results.keys()
        self._clean_listbox()
        self.res_lb.config(text='Result List: %d found'%len(keys))
        for s in sorted(keys):
            self.res_list_box.insert(END, s)
            
    def error_message_box(self, error):
        tkMessageBox.showinfo('Error', error)


class Loader(object):
    def __init__(self, filename):
        self.file = filename
        assert self.file.endswith('.p'), "Wrong file type loaded"
        self.load()
    
    def load(self):
        with open(self.file, 'rb') as f:
            data = pickle.load(f)
        try:
            #adjust for old version
            self.students, self.dorms, self.class_years = data
            return None
        except:
            pass
        try:
            data = data[0]
            self.students = data.students
            self.dorms = data.dorms
            self.class_years = data.class_years
            self.home_cities = data.home_cities
            self.home_codes = data.home_codes
        except Exception as e:
            print e

    def get_student_info(self, name):
        assert name in self.students, '%s is not in this dataset'%name
        return self.students[name]

    def search(self, mode, *args):
        if mode == 'lastname':
            return self._search_by_lastname(args[0], args[1])
        elif mode == 'firstname':
            return self._search_by_firstname(args[0], args[1])
        elif mode == 'class-year':
            return self._search_by_class_year(args[0], args[1])
        elif mode == 'd':
            return self._search_by_dorm(args[0])
        elif mode == 'dorm':
            return self._search_by_dorm_person(args[0], args[1])
        elif mode == 'room':
            return self._search_by_room_person(args[0], args[1])
        elif mode == 'home-city':
            return self._search_by_home_city_person(args[0], args[1])
        elif mode == 'dr':
            return self._search_by_dorm_room(args[0])
        elif mode == 'home-code':
            return self._search_by_home_code(args[0], args[1])

    def _search_by_lastname(self, ln, s_range=None):
        if not s_range:
            s_range = self.students.keys()
        ln = ln.lower().capitalize()
        res = []
        for fullname in s_range:
            lastname = fullname.split(' ').pop()
            if lastname.startswith(ln):
                res.append(fullname)
        return sorted(res)

    def _search_by_firstname(self, fn, s_range=None):
        if not s_range:
            s_range = self.students.keys()
        fn = fn.lower().capitalize()
        res = []
        for fullname in s_range:
            lastname = fullname.split(' ').pop(0)
            if lastname.startswith(fn):
                res.append(fullname)
        return sorted(res)

    def _search_by_dorm(self, dorm):
        p = re.compile('%s'%dorm, re.I)
        for d, obj in self.dorms.items():
            hit = re.search(p, d)
            if hit is not None:
                return obj
        return None

    def _search_by_room(self, dorm, room):
        dorm = self._search_by_dorm(dorm)
        assert dorm is not None, 'Please specify the dormitory name'
        p = re.compile('%s'%room, re.I)
        res = {}
        for r, obj in dorm.rooms.items():
            hit = re.search(p, r)
            if hit is not None:
                res[r] = res.get(r, obj)
        return sorted(res)

    def _search_by_class_year(self, y, s_range=None):
        if not s_range:
            s_range = self.students.keys()
        years = self.class_years.keys()
        res = []
        for yr in years:
            if yr.endswith(y):
                 res.extend([fullname for fullname in s_range if fullname in self.class_years[yr]])

        return sorted(res)

    def _search_by_dorm_person(self, dorm, s_range=None):
        if not s_range:
            s_range = self.students.keys()
        dorms = self.dorms.keys()
        res = []

        for dm in dorms:
            if dorm.lower() in dm.lower():
                res.extend([fullname for fullname in s_range if fullname in self.dorms[dm].show_namelist()])

        return sorted(res)

    def _search_by_room_person(self, room, s_range=None):
        if not s_range:
            s_range = self.students.keys()
            
        res = []
        for dm in self.dorms.keys():
            for rm in self.dorms[dm].show_roomlist():
                if rm.startswith(room):
                    res.extend([fullname for fullname in s_range if fullname in self.dorms[dm].rooms[rm].show_namelist()])

        return sorted(res)

    def _search_by_home_city_person(self, city, s_range=None):
        if not s_range:
            s_range = self.students.keys()

        res = []
        for c in self.home_cities.keys():
            if city.lower() in c.lower():
                res.extend([fullname for fullname in s_range if fullname in self.home_cities[c]])

        return res

    def _search_by_home_code(self, hc, s_range=None):
        if not s_range:
            s_range = self.students.keys()

        res = []
        for c in self.home_codes.keys():
            if c.lower().startswith(hc.lower()):
                res.extend([fullname for fullname in s_range if fullname in self.home_codes[c]])

        return res
    
def main():
    root = Tk()
    vv = Vassar_Viewer(root)
    root.geometry()    
    root.mainloop()  


if __name__ == '__main__':
    main()
    #L = Loader('test.p')
    

