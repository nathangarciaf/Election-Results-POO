public class Main {
    public static void main(String[] args) {
        /*args[0] = federal ou estadual
        args[1] = path pro candidatos
        args[2] = path pra votacao
        args[3] = data da votacao
        */

        String nivelEleicao = "";
        if(args[0].equals("--federal")){
            nivelEleicao="FEDERAL";
        }
        else if(args[0].equals("--estadual")){
            nivelEleicao="ESTADUAL";
        }
        else{

        }

        Eleicao eleicao = new Eleicao(args[3], nivelEleicao);

        Leitor leitor = new Leitor();
        leitor.readConsultaCandidato(eleicao,args[1]);
        leitor.readSecaoVotacao(eleicao, args[2], nivelEleicao);

        eleicao.getRelatorio();
        //eleicao.printPartidos();
    }
}
