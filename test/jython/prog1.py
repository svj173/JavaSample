# All Java imports:
from java.util import *;

# ������� � ���
LoggerFactory.debug ("log proba")
# ������� � �������
print ( "print proba" )

#returnValue = 2.5
# <- org.python.core.PyFloat
# <- java.lang.Double

#bArray=[1,2,3]
#returnValue = bArray
# <- org.python.core.PyList
# <- org.python.core.PyList

#vec = Vector()
#vec.addElement ( "21" )
#vec.addElement ( "12" )
#returnValue = vec
# <- org.python.core.PyJavaInstance
# <- java.util.Vector

returnValue = MyObject
returnValue.setName ( "������� !!!" )
# <- org.python.core.PyJavaInstance
# <- jython.MyObject
