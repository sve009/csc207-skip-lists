Skip Lists Assignment
=====================

Some starter code for the skip list assignment.

Note: In the situation where all the values are added onto the end of the list, the correct model seems to be O(n). Notice the scatterplot, which has a correlation coefficient of r = .9996.

The data set was:
  100     500
  1000    9000
  10000   225000
  100000  2400000
  1000000 34000000

The data for remove was approximately the same (not far enough off to be significant).

This is pretty interesting because it shows that if the skip list is used to store values in a specific order the efficiency can be either reduced or improved.

In this case we chose to add and remove the values to/from the end, which should be the least efficent use of the set remove methods because they have to traverse both the height and the length of the skip list.

I also ran tests for the best case senario where values are added to the front and values are removed from the front. While the numbers were considerably smaller, it was still surprisingly a linear curve, with r = .9999.

The data set:
  100     40
  1000    900
  10000   30000
  100000  400000
  1000000 4700000
