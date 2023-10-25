all: compile run

compile: 
	ant compile
	ant jar

run: 
	java -jar deputados.jar --federal consul_cand/consulta_cand_2022_ES.csv votacao.csv 02/10/2023