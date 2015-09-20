"""
For strings containing the letters ‘a’, ‘b’, ‘c’, and ‘d’ give a regular expression that
captures all strings that use their letters in reverse alphabetical order, but use at most
three of the four possible letters (note that the strings themselves can be longer than
3 letters long, since letters can repeat).
"""

import re

pattern = r'(d+c+b+a*)|(d+b+a+)|(d+c+a+)|(c+b+a+)'

true_testcases, false_testcases = [], []

true_testcases = ['dcba', 'cba', 'ddccaa', 'dca', 'ccbaaa', 'dddbbaa']
false_testcases = ['d', 'abc', 'dcbabcb', 'dbca', 'cbb']

print('True Testcases')
for t in true_testcases:
	if re.match(pattern, t):
		print('Passed')
	else:
		print('Failed')

print('\nFalse Testcases')
for f in false_testcases:
	if re.match(pattern, f):
		print('Failed')
	else:
		print('Passed')

