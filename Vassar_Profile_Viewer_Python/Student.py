import re
import base64

_UNKNOWN = 'Unspecified'


class Student(object):
    other = _UNKNOWN
    def __init__(self, name='Unspecified Person'):
        self.img = None
        self.name = name
        self.email= None
        self.box_number = None
        self.room = None
        self.class_year = None
        self.cell_phone = None
        self.dorm = None
        self.home_phone = None
        self.home_addr = None
        self.home_city = None
        self.home_state = None
        self.home_zip = None
        self.status = None

    @property
    def comment(self):
        return self.comment

    @comment.setter
    def comment(self, comment):
        self.comment = comment

    def add_pic(self, img):
        p_img = re.compile('(src="data:image/jpeg;base64,)(.*?)(")')
        img = re.search(p_img, img).group(2).strip()
        self.img = img.decode('base64')

    def export_pic(self):
        with open('%s.jpeg'%self.name, 'wb') as f:
            f.write(self.img)

    def dorm_info(self):
        return self.dorm, self.room
        
    def handler(self, line, n):
        if n == 1:
            self.__handler_line1(line)
        if n == 3:
            self.__handler_line2(line)
        if n == 5:
            self.__handler_line3(line)
        if n == 6:
            self.__handler_line4(line)

    def __handler_line1(self, line1):
        p_email = re.compile('(mailto:)(.*)(">)')
        name, email, box_number, room, class_year = line1
        
        if room == '':
            room = self.other
        if class_year == '':
            class_year = self.other
        self.name = name.replace('  ', ' ')

        try:
            email = re.search(p_email, email).group(2)
        except:
            email = self.other
        self.email = email
        self.room = room
        try:
            self.box_number = box_number.split(' ')[1]
        except:
            self.box_number = self.other
        self.class_year = class_year
        
    def __handler_line2(self, line2):
        pos_holder, cell_phone, dorm = line2
        if cell_phone == '':
            cell_phone = self.other
        if dorm == '':
            dorm = self.other
        self.cell_phone = cell_phone
        self.dorm = dorm

    def __handler_line3(self, line3):
        for i in range(len(line3)):
            if line3[i] == '':
                line3[i] = self.other
        self.home_phone, self.home_addr, self.home_city, self.home_state, self.home_zip = line3

    def __handler_line4(self, line4):
        if line4[1] == '':
            line4[1] = self.other
        else:
            line4[1] = line4[1].split('  ')[1]
        self.status = line4[1]

    def __str__(self):
        return '''Name: %s
Email: %s
Box: %s
Class: %s
Dormitory: %s
Room: %s
Cell Phone: %s
Home Phone: %s
Home Street Address: %s
Home City: %s
Nation/State Code: %s
Home Zip: %s
Status: %s
'''%(self.name, self.email, self.box_number, self.class_year,
     self.dorm, self.room, self.cell_phone,
     self.home_phone, self.home_addr, self.home_city,
     self.home_state, self.home_zip, self.status)

class Room(object):
    def __init__(self, name=None):
        self.name = name
        self.persons = []

    def __str__(self):
        res = '%s:\n'%self.name.upper()
        for p in sorted(self.persons):
            res += '        --> %s\n'%p
        return res


    def add_student(self, student):
        self.persons.append(student)
        
    def show_namelist(self):
        return sorted(self.persons)

class Dormitory(Room):
    def __init__(self, name):
        Room.__init__(self)
        self.name = name
        self.rooms = {}

    def __str__(self):
        name = self.name
        if name == _UNKNOWN:
            name += ' (Probably Away)'
        res = '%s\n'%name
        for r in sorted(self.rooms.keys()):
            res += '    %s\n'%str(self.rooms[r])
        return res

    def add_student(self, student, room):
        self.persons.append(student)
        self.rooms[room] = self.rooms.get(room, Room(room))
        self.rooms[room].add_student(student)

    def find_room(self, room):
        assert room in self.rooms
        return self.rooms[room]

    def show_roomlist(self):
        return sorted(self.rooms.keys())

        
def test():
    t_student = Student()
    t_dorm = Dormitory('TEST')
    t_dorm.add_student(t_student.name, 'test_room')
    print t_dorm
    
if __name__ == '__main__':
   # test()
    t_student = Student()
    t_dorm = Dormitory('TEST')
    t_dorm.add_student(t_student.name, 'test_room')
        
        
