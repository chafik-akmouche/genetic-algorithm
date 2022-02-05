#!/bin/bash
echo "set terminal png size 1200,600 enhanced background rgb 'white'" > gnuplot_script
echo "set output 'curve_fitness.png'" >> gnuplot_script
#echo "set autoscale" >> gnuplot_script
echo "set title 'Fitness moyenne / Génération en fonction des opérateurs de mutation'" >> gnuplot_script
echo "set xlabel 'Génération'" >> gnuplot_script
echo "set ylabel 'Fitness'" >> gnuplot_script
#echo "set yrange [0:100]" >> gnuplot_script
echo "set grid" >> gnuplot_script
#echo "set nokey" >> gnuplot_script
echo "set key outside" >> gnuplot_script
#echo "plot 'data.dat' u (column(0)):2:xtic(1) w l" >> gnuplot_script
echo "plot 'data_fitnessClassicAG1.dat' u 1:2 title'Mutation 1 Flip' w l lw 2, 'data_fitnessClassicAG2.dat' u 1:2 title'Mutation Bit-Flip' w l lw 2, 'data_fitnessClassicAG3.dat' u 1:2 title'Mutation 3 Flip' w l lw 2, 'data_fitnessClassicAG5.dat' u 1:2 title'Mutation 5 Flip' w l lw 2" >> gnuplot_script
gnuplot gnuplot_script
rm gnuplot_script
gwenview curve_fitness.png
