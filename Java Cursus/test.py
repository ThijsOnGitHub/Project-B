
class Person:
    def __init__(self,first_name,last_name):
        self.first_name=first_name
        self.last_name=last_name
    
    def getInfo(self):
        pass
    
class Student(Person):
    def __init__(self,first_name,last_name,std_num):
        super().__init__(first_name,last_name)
        self.std_num=std_num
    
    def getInfo(self):
        pass

class BachelorStudent(Student):
    def __init__(self,first_name,last_name,std_num):
        super().__init__(first_name,last_name,std_num)
        self.__study_duration = 4

    def getInfo(self):
        pass

class MasterStudent(Student):
    def __init__(self,first_name,last_name,std_num):
        super().__init__(first_name,last_name,std_num)
        self.__study_duration=2

    def getInfo(self):
        pass


