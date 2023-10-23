import java.time.LocalDate;

public class Candidato {
    private String nomeUrna;
    private LocalDate dataNascimento;
    private int numero;
    private int numeroFederacao;
    private int votos;

    private Cargo cargo;
    private Genero genero;
    private Partido partido;

    /*public Candidato(String nome, String nomeUrna, int num, Cargo cargo, String dataNascimento) {
        this.nome = nome;
        this.nomeUrna = nomeUrna;
        this.cargo = cargo;
        this.dataNascimento = dataNascimento;
    }*/

    public void setNomeUrna(String nomeUrna) {
        this.nomeUrna = nomeUrna;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setNumeroFederacao(int numeroFederacao) {
        this.numeroFederacao = numeroFederacao;
    }

    public int getVotos() {
        return votos;
    }

    public int getNumeroFederacao() {
        return numeroFederacao;
    }

    public int getNumero() {
        return numero;
    }

    public String getNomeUrna() {
        return nomeUrna;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public Genero getGenero() {
        return genero;
    }

    public LocalDate getdataNascimento() {
        return dataNascimento;
    }
    
    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }

    @Override
    public String toString(){
        return "Candidato: " + getNomeUrna() + " (Numero de candidato: " + numero + " // Numero da federação: " + numeroFederacao + ")" + "\nNascido no dia: " + dataNascimento + "\nDeputado(a): " + cargo + "\nSexo: " + genero;
    }
}
