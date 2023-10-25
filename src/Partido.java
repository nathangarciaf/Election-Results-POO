import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class Partido {
    private String sigla;
    private int numero;
    private int votos;

    private Set<Candidato> candidatosFiliados = new HashSet<Candidato>();

    /*public Partido(String sigla, int numero) {
        this.sigla = sigla;
        this.numero = numero;
    }*/

    public void addCandidatosFiliados(Candidato c){
        this.candidatosFiliados.add(c);
    }   

    public String getSigla() {
        return sigla;
    }

    public int getNumero() {
        return numero;
    }

    public int getVotos() {
        return votos;
    }

    public void incrementVotos(int votos){
        this.votos+= votos;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    @Override
    public String toString(){
        return "Sigla do Partido: " + sigla + "\nNumero do Partido: " + numero + "\n";
    }
}

class ComparatorVotos implements Comparator<Partido> {
    @Override
    public int compare(Partido p1, Partido p2){
        if(p1.getVotos() != p2.getVotos()){
            return p1.getVotos() - p2.getVotos();
        }
        else{
            return p1.getSigla().compareTo(p2.getSigla());
        }
    }
}
