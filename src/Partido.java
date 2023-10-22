import java.util.HashSet;
import java.util.Set;

public class Partido {
    private String sigla;
    private int numero;
    private Set<Candidato> candidatosFiliados = new HashSet<Candidato>();

    /*public Partido(String sigla, int numero) {
        this.sigla = sigla;
        this.numero = numero;
    }

    public Partido(){
        
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

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
}
