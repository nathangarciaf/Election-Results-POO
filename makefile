all: compile run

compile: 
	ant compile
	ant jar

run: 
	java -jar deputados.jar --estadual consul_cand/consulta_cand_2022_MG.csv votacao_secao/votacao_secao_2022_MG.csv 02/10/2022 > relatorios/relat_MG_estadual.txt