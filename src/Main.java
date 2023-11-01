public class Main {
    public static void main(String[] args) throws Exception {
        Eleicao eleicao = new Eleicao(args[3], args[0]);

        Leitor leitor = new Leitor();
        leitor.readConsultaCandidato(eleicao,args[1]);
        leitor.readSecaoVotacao(eleicao, args[2]);

        eleicao.getRelatorio();
    }
}