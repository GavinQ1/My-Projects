#!usr/bin/env python

from __future__ import division
import sys, time
__author__ = 'Gavin'


class Progress_bar(object):
    def __init__(self, total, info=None):
        '''Total represents the total work.
           Step represents the work done by the main program per time it is called. 
        '''
        self.total = total      
        self.n = 1 #the reason that self.n does not start as zero is that this module functions along the main program -->
                           #when the main program starts, it is already called and has done work.
        self.info = info
        self.infoprinted = False
        self.startprinted = False
        self.barIndex = 0
        self.barPointer = range(0, 100, 5)
        self.percent = range(100)
        self.screen = sys.stdout

    def animation(self, step=1):
        '''
        Put it along with the main program
        '''
        if self.info:
            if not self.infoprinted:
                self.screen.write(self.info+'\n|')
                self.screen.flush()
                self.infoprinted = True
        else:
            if not self.startprinted:
                self.screen.write('|')
                self.screen.flush()
                self.startprinted = True

        bar_pointer = self.barPointer
        len_bar_pointer = len(bar_pointer)
        
        progress = 100 * self.n/self.total
        
        percent = ' %.2f%%'%(progress)
        len_percent = len(percent)
        backspace = '\b'*len_percent

        for i in range(len_bar_pointer-1):
            if bar_pointer[i] < progress <= bar_pointer[i+1]:
                index = i
                break
        else:
            index = len_bar_pointer
        
        if index > self.barIndex:
            self.screen.write('='*(index-self.barIndex)+percent+backspace)
            self.screen.flush()
            self.barIndex = index
        else:
            self.screen.write(percent+backspace)
            self.screen.flush()

        if progress == 100:
            self.screen.write('|'+'  100.00%'+'  ***Complete***\n')
            self.screen.flush()

        self.n += step
        


#test
if __name__ == '__main__':
    n = 0
    total = 20
    test = Progress_bar(total=total, info='FOR TEST')
    while n<total:
        test.animation()
        n+=1
        time.sleep(0.05)
    input()
        


