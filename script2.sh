#!/bin/bash
echo "set terminal png size 1200,600 enhanced background rgb 'white'" > gnuplot_script
echo "set output 'curve2.png'" >> gnuplot_script
#echo "set autoscale" >> gnuplot_script
echo "set title 'Courbe - Fitness | opérateur / Génération'" >> gnuplot_script
echo "set xlabel 'Génération'" >> gnuplot_script
echo "set ylabel 'Taux de participation des opérateurs/fitness (%)'" >> gnuplot_script
echo "set yrange [0:100]" >> gnuplot_script
echo "set grid" >> gnuplot_script
#echo "set nokey" >> gnuplot_script
echo "set key outside" >> gnuplot_script
#echo "plot 'data.dat' u (column(0)):2:xtic(1) w l" >> gnuplot_script
echo "plot 'data2.dat' u 1:2 title'1-flip' w l, 'data2.dat' u 1:3 title'bit-flip' w l, 'data2.dat' u 1:4 title'3-flip' w l, 'data2.dat' u 1:5 title'5-flip' w l" >> gnuplot_script
gnuplot gnuplot_script
rm gnuplot_script
gwenview curve2.png
