#!/bin/bash
echo "set terminal png size 1200,600 enhanced background rgb 'white'" > gnuplot_script2
echo "set output 'histogrammeOp.png'" >> gnuplot_script2
echo "set title 'Nombre d utilisations de chaque opérateur'" >> gnuplot_script2
echo "set style data boxes" >> gnuplot_script2
echo "set key fixed right top vertical Right noreverse noenhanced autotitle nobox" >> gnuplot_script2
echo "set boxwidth 0.1" >> gnuplot_script2
echo "set style fill solid border 3" >> gnuplot_script2
echo "set xtics   ('Mutation 1 flip' 0, 'Mutation bit-flip' 1, 'Mutation 3 flip' 2, 'Mutation 5 flip' 3)" >> gnuplot_script2
echo "set xtics border in scale 0,0 nomirror rotate by -45  autojustify" >> gnuplot_script2
echo "set xlabel 'Opérateurs de mutation'" >> gnuplot_script2
echo "set ylabel 'Nombre utilisations'" >> gnuplot_script2
echo "set grid y" >> gnuplot_script2
echo "plot 'data_histogrammeOp.dat' u 1:2 t ''" >> gnuplot_script2
gnuplot gnuplot_script2
rm gnuplot_script2
gwenview histogrammeOp.png

