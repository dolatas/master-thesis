@echo off

pdflatex thesis-master-english.tex 
bibtex   thesis-master-english
pdflatex thesis-master-english.tex 
pdflatex thesis-master-english.tex 


del *.aux *.bak *.log *.blg *.bbl *.toc *.out