import numpy as np
import matplotlib.pyplot as plt

#plot the graph
	fig = plt.figure()

	ax1 = fig.add_subplot(111)
	#print max_x_axis[len(d[0])-1], ' max_x_axis'

	#max = max_x_axis[len(d[0])-1] + 10
	x = list(range(len(min_x_axis)))

	ax1.set_title(file)
	ax1.set_xlabel('time')
	ax1.set_ylabel('value')

	ax1.plot(x,min_x_axis, c='r', label='min')
	ax1.plot(x,max_x_axis, c='b', label='max')
	ax1.plot(x,med_x_axis, c='g', label='med')
	ax1.plot(x,first_x_axis, c='k', label='1st quartile')
	ax1.plot(x,third_x_axis, c='m', label='3rd quartile')

	leg = ax1.legend()

	#plt.show()
	plt.savefig(file+'.png')
