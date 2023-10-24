import java.time.LocalDate;
import java.util.Comparator;

public class Candidato {
    private String nomeUrna;
    private LocalDate dataNascimento;
    private int numero;
    private int numeroFederacao;
    private int votos;

    private boolean candidaturaDeferida;
    private boolean eleito;
    private boolean votoLegenda;

    private Cargo cargo;
    private Genero genero;
    private Partido partido;

    /*public Candidato(String nome, String nomeUrna, int num, Cargo cargo, String dataNascimento) {
        this.nome = nome;
        this.nomeUrna = nomeUrna;
        this.cargo = cargo;
        this.dataNascimento = dataNascimento;
    }*/

    public String getNomeUrna() {
        return nomeUrna;
    }

    public void setNomeUrna(String nomeUrna) {
        this.nomeUrna = nomeUrna;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getNumeroFederacao() {
        return numeroFederacao;
    }

    public void setNumeroFederacao(int numeroFederacao) {
        this.numeroFederacao = numeroFederacao;
    }

    public int getVotos() {
        return votos;
    }

    public void setVotos(int votos) {
        this.votos = votos;
    }

    public boolean isCandidaturaDeferida() {
        return candidaturaDeferida;
    }

    public void setCandidaturaDeferida(boolean candidaturaDeferida) {
        this.candidaturaDeferida = candidaturaDeferida;
    }

    public boolean isEleito() {
        return eleito;
    }

    public void setEleito(boolean eleito) {
        this.eleito = eleito;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }

    public boolean isVotoLegenda() {
        return votoLegenda;
    }

    public void setVotoLegenda(boolean votoLegenda) {
        this.votoLegenda = votoLegenda;
    }

    @Override
    public String toString(){
        return "Candidato: " + getNomeUrna() + " (Numero de candidato: " + numero + " // Numero da federação: " + numeroFederacao + ")" + "\nNascido no dia: " + dataNascimento + "\nDeputado(a): " + cargo + "\nSexo: " + genero;
    }

    public static class ComparatorVotos implements Comparator<Candidato> {
        @Override
        public int compare(Candidato c1, Candidato c2){
            if(c1.getVotos() != c2.getVotos()){
                return c1.getVotos() - c2.getVotos();
            }
            else{
                return c1.getNomeUrna().compareTo(c2.getNomeUrna());
            }
        }
    }
}
