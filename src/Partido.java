import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class Partido {
    private String sigla;
    private int numero;
    private int votosLegenda;
    private int votosNominais;
    private int votosTotais;

    private Set<Candidato> candidatosFiliados = new HashSet<Candidato>();

    public void addCandidatosFiliados(Candidato c){
        this.candidatosFiliados.add(c);
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
        for(Candidato c : this.candidatosFiliados){
            System.out.println(c);
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
}


