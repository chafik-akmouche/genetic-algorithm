#!/bin/bash
echo "set terminal png size 1200,600 enhanced background rgb 'white'" > gnuplot_script
echo "set output 'curve_fitness.png'" >> gnuplot_script
#echo "set autoscale" >> gnuplot_script
echo "set title 'Fitness (AG classique - Bandit) / Génération'" >> gnuplot_script
echo "set xlabel 'Génération'" >> gnuplot_script
echo "set ylabel 'Fitness'" >> gnuplot_script
#echo "set yrange [0:100]" >> gnuplot_script
echo "set grid" >> gnuplot_script
#echo "set nokey" >> gnuplot_script
echo "set key outside" >> gnuplot_script
#echo "plot 'data.dat' u (column(0)):2:xtic(1) w l" >> gnuplot_script
# echo "plot 'data_fitnessClassicAG.dat' u 1:2 title'Fitness Min (AG classique)' w l lw 2, 'data_fitnessClassicAG.dat' u 1:3 title'Fitness Moyenne (AG classique)' w l lw 2, 'data_fitnessClassicAG.dat' u 1:4 title'Fitness Max (AG classique)' w l lw 2, 'data_fitness_bandit.dat' u 1:2 title'Fitness Min (Bandit)' w l lw 2, 'data_fitness_bandit.dat' u 1:3 title'Fitness Moy (Bandit)' w l lw 2, 'data_fitness_bandit.dat' u 1:4 title'Fitness Max (Bandit)' w l lw 2" >> gnuplot_script
echo "plot 'data_fitnessClassicAG.dat' u 1:4 title'Fitness moyenne (AG classique)' w l lw 2, 'data_fitness_bandit.dat' u 1:4 title'Fitness moyenne (Bandit)' w l lw 2" >> gnuplot_script
gnuplot gnuplot_script
rm gnuplot_script
gwenview curve_fitness.png
