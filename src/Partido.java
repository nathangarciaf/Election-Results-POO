import java.time.LocalDate;
import java.util.Comparator;
import java.util.Vector;

public class Partido {
    private String sigla;
    private int numero;
    private int votosLegenda;
    private int votosNominais;
    private int votosTotais;

    private Vector<Candidato> candidatosFiliados = new Vector<Candidato>();

    public void addCandidatosFiliados(Candidato c){
        this.candidatosFiliados.add(c);
    }

    public void sortCandidatosFiliados(LocalDate ld){
        this.candidatosFiliados.sort(new Candidato.ComparatorVotos(ld));
    }

    public Vector<Candidato> getCandidatosFiliados(){
        return new Vector<Candidato>(this.candidatosFiliados);
    }

    public String getSigla() {
        return sigla;
    }

    public int getNumero() {
        return numero;
    }

    public int getVotosLegenda() {
        return votosLegenda;
    }

    public int getVotosNominais() {
        return votosNominais;
    }

    public void addVotosNominais(int votos) {
        this.votosNominais += votos;
        this.votosTotais += votos;
    }

    public int getVotosTotais() {
        return votosTotais;
    }

    public void addVotosLegenda(int votos){
        this.votosLegenda += votos;
        this.votosTotais += votos;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getVotosCandidatoFiliado(int idx){
        if(this.candidatosFiliados.size() > 0){
            Candidato c = this.candidatosFiliados.get(idx);
            return c.getVotos();
        }
        else{
            return 0;
        }
    }

    public int getIdadeCandidatoFiliado(int idx, LocalDate ld){
        if(this.candidatosFiliados.size() > 0){
            Candidato c = this.candidatosFiliados.get(idx);
            return c.getIdade(ld);
        }
        else{
            return 0;
        }
    }

    public int getQuantidadeCandidatosEleitos(){
        int eleitos = 0;
        for(Candidato c : this.candidatosFiliados){
            if(c.isEleito()){
                eleitos++;
            }
        }
        return eleitos;
    }

    public int getQuantidadeCandidatosEstaduaisEleitos(){
        int eleitos = 0;
        for(Candidato c : this.candidatosFiliados){
            if(c.isEleito() && c.getCargo().toString().equals("ESTADUAL")){
                eleitos++;
            }
        }
        return eleitos;
    }

    public int getQuantidadeCandidatosFederaisEleitos(){
        int eleitos = 0;
        for(Candidato c : this.candidatosFiliados){
            if(c.isEleito() && c.getCargo().toString().equals("FEDERAL")){
                eleitos++;
            }
        }
        return eleitos;
    }

    public void printCandidatos(){
        for(int i = 0; i < this.candidatosFiliados.size(); i++){
            Candidato c = this.candidatosFiliados.get(i);
            System.out.println(i + " - " + c);
        }
    }

    @Override
    public String toString(){
        return "Sigla do Partido: " + sigla + "\nNumero do Partido: " + numero + "\n";
    }

    public static class ComparatorVotos implements Comparator<Partido> {
        @Override
        public int compare(Partido p1, Partido p2){
            if(p1.getVotosTotais() != p2.getVotosTotais()){
                return p2.getVotosTotais() - p1.getVotosTotais();
            }
            else{
                return p1.getNumero() - p2.getNumero();
            }
        }
    }

    public static class ComparatorCandidatoMaisVotado implements Comparator<Partido> {
        LocalDate data;
        public ComparatorCandidatoMaisVotado(LocalDate dataEleicao){
            this.data = dataEleicao;
        }

        @Override
        public int compare(Partido p1, Partido p2){
            if(p2.getVotosCandidatoFiliado(0) != p1.getVotosCandidatoFiliado(0)){
                return p2.getVotosCandidatoFiliado(0) - p1.getVotosCandidatoFiliado(0);
            }
            else{
                return p2.getIdadeCandidatoFiliado(0,data) - p1.getIdadeCandidatoFiliado(0,data);
            }
        }
    }
}


