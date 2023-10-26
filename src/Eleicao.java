import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Eleicao {
    private String data;
    private String nivel;

    private Map<Integer,Candidato> candidatos = new HashMap<>();
    private Map<Integer,Partido> partidos = new HashMap<>();

    public Eleicao(String data, String nivel) {
        this.data = data;
        this.nivel = nivel;
    }

    public Map<Integer,Candidato> getMapCandidatos(){
        return new HashMap<Integer,Candidato>(candidatos);
    }

    public Map<Integer,Partido> getMapPartidos(){
        return new HashMap<Integer,Partido>(partidos);
    }

    public void addCandidatos(int key, Candidato c){
        candidatos.put(key, c);
    }

    public void addPartido(int key, Partido p){
        partidos.put(key, p);
    }

    public void addVotosCandidato(int key, int votos){
        Candidato c = candidatos.get(key);
        if(c.isVotoLegenda()){
            c.getPartido().addVotos(votos);
        }
        else{
            c.addVotos(votos);
        }
    }

    public void addVotosPartido(int key, int votos){
        partidos.get(key).addVotos(votos);
    }

    public boolean constainsCandidatoKey(int key){
        return candidatos.containsKey(key);
    }

    public boolean constainsPartidoKey(int key){
        return partidos.containsKey(key);
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    private Vector<Candidato> getCandidatosEstaduais(){
        Vector<Candidato> candidatosEstaduais = new Vector<Candidato>();
        for(Candidato c : candidatos.values()){
            if(c.getCargo().toString().equals("ESTADUAL")){
                candidatosEstaduais.add(c);
            }
        }
        return candidatosEstaduais;
    }

    public int getCandidatosEstaduaisEleitos(){
        Vector<Candidato> candidatosEstaduais = getCandidatosEstaduais();
        int eleitos = 0;
        for(Candidato c : candidatosEstaduais){
            if(c.isEleito()){
                eleitos++;
            }
        }
        return eleitos;
    }

    private Vector<Candidato> getCandidatosFederais(){
        Vector<Candidato> candidatosFederais = new Vector<Candidato>();
        for(Candidato c : candidatos.values()){
            if(c.getCargo().toString().equals("FEDERAL")){
                candidatosFederais.add(c);
            }
        }
        return candidatosFederais;
    }

    public int getCandidatosFederaisEleitos(){
        Vector<Candidato> candidatosFederais = getCandidatosFederais();
        int eleitos = 0;
        for(Candidato c : candidatosFederais){
            if(c.isEleito()){
                eleitos++;
            }
        }
        return eleitos;
    }

    public String getCandidatosFederaisMaisVotados(){
        Vector<Candidato> candidatosFederais = getCandidatosFederais();
        candidatosFederais.sort(new Candidato.ComparatorVotos());

        int eleitos = getCandidatosFederaisEleitos();
        int idxCandidatoAtual = 0;
        String result = "";
        for(int i = 1; i <= eleitos; i++){
            Candidato c = candidatosFederais.get(idxCandidatoAtual);
            Partido p = c.getPartido();
            result += i + " - ";
            result += c.getNomeUrna() + " (" + p.getSigla() + ", " + c.getVotos() + " votos)\n";
        }
        return result;
    }

    public String getCandidatosEstaduaisMaisVotados(){
        Vector<Candidato> candidatosEstaduais = getCandidatosEstaduais();
        candidatosEstaduais.sort(new Candidato.ComparatorVotos());

        int eleitos = getCandidatosEstaduaisEleitos();
        int idxCandidatoAtual = 0;
        String result = "";
        for(int i = 1; i <= eleitos; i++){
            Candidato c = candidatosEstaduais.get(idxCandidatoAtual);
            Partido p = c.getPartido();
            result += i + " - ";
            if(c.getNumeroFederacao() != -1){
                result += "*";
            }
            result += c.getNomeUrna() + " (" + p.getSigla() + ", " + c.getVotos() + " votos)\n";
            idxCandidatoAtual++;
        }
        return result;
    }

    public void addHeaderIdxElection(Vector<Integer> v, String[] lines){
        int idxAttributes = 0;
        for(String attribute : lines){
            if(attribute.equals("CD_CARGO") || 
              attribute.equals("QT_VOTOS") ||
              attribute.equals("NR_VOTAVEL")){
                v.add(idxAttributes);
            }
            idxAttributes++;
        }
    }
    
    public void addHeaderIdxCandidato(Vector<Integer> v, String[] lines){
        int idxAttributes = 0;
        for(String headerAttribute : lines){
            if(headerAttribute.equals("CD_CARGO") || 
                headerAttribute.equals("CD_SITUACAO_CANDIDATO_TOT") ||
                headerAttribute.equals("NR_CANDIDATO") || 
                headerAttribute.equals("NM_URNA_CANDIDATO") ||
                headerAttribute.equals("NR_PARTIDO") ||
                headerAttribute.equals("SG_PARTIDO") ||
                headerAttribute.equals("DT_NASCIMENTO") ||
                headerAttribute.equals("CD_SIT_TOT_TURNO") ||
                headerAttribute.equals("CD_GENERO") ||
                headerAttribute.equals("NM_TIPO_DESTINACAO_VOTOS") ||
               headerAttribute.equals("NR_FEDERACAO")){
                v.add(idxAttributes);
            }
            idxAttributes++;
        }
    }

    public void printCandidatosFederais(){
        for(Candidato c : candidatos.values()){
            if(c.getCargo().toString().equals("FEDERAL")){
                System.out.println(c);
            }
        }
    }

    public void printCandidatosEstaduais(){
        for(Candidato c : candidatos.values()){
            if(c.getCargo().toString().equals("ESTADUAL")){
                System.out.println(c);
            }
        }
    }
}
