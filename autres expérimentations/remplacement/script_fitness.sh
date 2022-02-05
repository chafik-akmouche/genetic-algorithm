#!/bin/bash
echo "set terminal png size 1200,600 enhanced background rgb 'white'" > gnuplot_script
echo "set output 'curve_fitness.png'" >> gnuplot_script
#echo "set autoscale" >> gnuplot_script
echo "set title 'Fitness moyenne / Génération en fonction des opérateurs de remplacement'" >> gnuplot_script
echo "set xlabel 'Génération'" >> gnuplot_script
echo "set ylabel 'Fitness'" >> gnuplot_script
#echo "set yrange [0:100]" >> gnuplot_script
echo "set grid" >> gnuplot_script
#echo "set nokey" >> gnuplot_script
echo "set key outside" >> gnuplot_script
#echo "plot 'data.dat' u (column(0)):2:xtic(1) w l" >> gnuplot_script
echo "plot 'data_fitnessClassicAGr2m.dat' u 1:2 title'Remplacement 2 meilleurs' w l lw 2, 'data_fitnessClassicAGr2ma.dat' u 1:2 title'Remplacement 2 plus mauvais' w l lw 2" >> gnuplot_script
gnuplot gnuplot_script
rm gnuplot_script
gwenview curve_fitness.png
