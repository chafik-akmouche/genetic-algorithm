#!/bin/bash
echo "set terminal png size 1200,600 enhanced background rgb 'white'" > gnuplot_script
echo "set output 'curve_fitness.png'" >> gnuplot_script
#echo "set autoscale" >> gnuplot_script
echo "set title 'Fitness moyenne PM et UCB'" >> gnuplot_script
echo "set xlabel 'Génération'" >> gnuplot_script
echo "set ylabel 'Fitness'" >> gnuplot_script
#echo "set yrange [0:100]" >> gnuplot_script
echo "set grid" >> gnuplot_script
#echo "set nokey" >> gnuplot_script
echo "set key outside" >> gnuplot_script
#echo "plot 'data.dat' u (column(0)):2:xtic(1) w l" >> gnuplot_script
echo "plot 'data_fitness_pm.dat' u 1:2 title'PM' w l lw 2, 'data_fitness_ucb.dat' u 1:2 title'UCB' w l lw 2" >> gnuplot_script
gnuplot gnuplot_script
rm gnuplot_script
gwenview curve_fitness.png
