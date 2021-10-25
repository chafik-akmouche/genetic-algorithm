#!/bin/bash
echo "set terminal png size 1200,600 enhanced background rgb 'white'" > gnuplot_script
echo "set output 'curve.png'" >> gnuplot_script
echo "set autoscale" >> gnuplot_script
echo "set title 'Courbe - fitness / iteration'" >> gnuplot_script
echo "set xlabel 'Iteration'" >> gnuplot_script
echo "set ylabel 'Fitness'" >> gnuplot_script
echo "set grid" >> gnuplot_script
echo "set nokey" >> gnuplot_script
echo "plot 'data.dat' u (column(0)):2:xtic(1) w l" >> gnuplot_script
gnuplot gnuplot_script
rm gnuplot_script
gwenview curve.png
