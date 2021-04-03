NAME=output

ALLFILES=$(NAME).tex makefile

pdf: ALL

ALL: $(NAME).tex
	latex $(NAME).tex
	pdflatex $(NAME).tex

clean:
	rm -f $(NAME).log $(NAME).dvi $(NAME).pdf $(NAME).aux $(NAME).out texput.log $(LOGIN).zip
