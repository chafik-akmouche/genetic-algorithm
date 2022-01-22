#!/bin/bash
echo "set terminal png size 1200,600 enhanced background rgb 'white'" > gnuplot_script
echo "set output 'curve3.png'" >> gnuplot_script
#echo "set autoscale" >> gnuplot_script
echo "set title 'Nombre d utilisation de chaque opérateur / Génération'" >> gnuplot_script
echo "set xlabel 'Génération'" >> gnuplot_script
echo "set ylabel 'Moyenne d utilisation par opérateur'" >> gnuplot_script
#echo "set yrange [0:100]" >> gnuplot_script
echo "set grid" >> gnuplot_script
#echo "set nokey" >> gnuplot_script
echo "set key outside" >> gnuplot_script
#echo "plot 'data.dat' u (column(0)):2:xtic(1) w l" >> gnuplot_script
echo "plot 'data3.dat' u 1:2 title'1-flip' w l lw 2, 'data3.dat' u 1:3 title'bit-flip' w l lw 2, 'data3.dat' u 1:4 title'3-flip' w l lw 2, 'data3.dat' u 1:5 title'5-flip' w l lw 2" >> gnuplot_script
gnuplot gnuplot_script
rm gnuplot_script
gwenview curve3.png
