#!/bin/bash
echo "set terminal png size 1200,600 enhanced background rgb 'white'" > gnuplot_script
echo "set output 'utilsation_op.png'" >> gnuplot_script
#echo "set autoscale" >> gnuplot_script
echo "set title 'Utilisation de chaque opérateur / Génération'" >> gnuplot_script
echo "set xlabel 'Génération'" >> gnuplot_script
echo "set ylabel 'Nombre d utilisation'" >> gnuplot_script
#echo "set yrange [0:100]" >> gnuplot_script
echo "set grid" >> gnuplot_script
#echo "set nokey" >> gnuplot_script
echo "set key outside" >> gnuplot_script
#echo "plot 'data.dat' u (column(0)):2:xtic(1) w l" >> gnuplot_script
# echo "plot 'data_fitnessClassicAG.dat' u 1:2 title'Fitness Min (AG classique)' w l lw 2, 'data_fitnessClassicAG.dat' u 1:3 title'Fitness Moyenne (AG classique)' w l lw 2, 'data_fitnessClassicAG.dat' u 1:4 title'Fitness Max (AG classique)' w l lw 2, 'data_fitness_bandit.dat' u 1:2 title'Fitness Min (Bandit)' w l lw 2, 'data_fitness_bandit.dat' u 1:3 title'Fitness Moy (Bandit)' w l lw 2, 'data_fitness_bandit.dat' u 1:4 title'Fitness Max (Bandit)' w l lw 2" >> gnuplot_script
echo "plot 'data_nbUtilisationOp.dat' u 1:2 title'Mutation 1 flip' w l lw 2, 'data_nbUtilisationOp.dat' u 1:3 title'Mutation bit-flip' w l lw 2, 'data_nbUtilisationOp.dat' u 1:4 title'Mutation 3 flip' w l lw 2,  'data_nbUtilisationOp.dat' u 1:5 title'Mutation 5 flip' w l lw 2" >> gnuplot_script
gnuplot gnuplot_script
rm gnuplot_script
gwenview utilsation_op.png
