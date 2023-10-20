public class Candidato {
    private String nome;
    private String nomeUrna;
    private int numeroUrna;
    private String dataNascimento;

    private Cargo cargo;
    private Genero genero;
    private Partido partido;

    public Candidato(String nome, String nomeUrna, int num, Cargo cargo, String dataNascimento) {
        this.nome = nome;
        this.nomeUrna = nomeUrna;
        this.numeroUrna = num;
        this.cargo = cargo;
        this.dataNascimento = dataNascimento;
    }
    
    public String getNome() {
        return nome;
    }

    public String getNomeUrna() {
        return nomeUrna;
    }

    public int getNumeroUrna() {
        return numeroUrna;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public Genero getGenero() {
        return genero;
    }

    public String getdataNascimento() {
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
        return "Candidato: " + getNome() + "/" + getNomeUrna() + " (" + numeroUrna + ")" + "\nNascido no dia: " + dataNascimento + "\nDeputado: " + cargo;
    }
}
