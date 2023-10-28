import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
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

    public void getRelatorioFederal() {
        Vector<Candidato> candidatosFederais = getCandidatosFederais();
        candidatosFederais.sort(new Candidato.ComparatorVotos());
        
        System.out.println("Número de vagas: " + getQuantidadeCandidatosFederaisEleitos(candidatosFederais));
        System.out.println("Deputados federais eleitos:\n");
        printCandidatosFederaisEleitos(candidatosFederais);
        System.out.println("Candidatos mais votados (em ordem decrescente de votação e respeitando número de vagas):");
        printCandidatosFederaisMaisVotados(candidatosFederais);
        System.out.println("Teriam sido eleitos se a votação fosse majoritária, e não foram eleitos:\r\n" + //
                "(com sua posição no ranking de mais votados)");
        printCandidatosFederaisMajoritariosNaoEleitos(candidatosFederais);
        System.out.println("Eleitos, que se beneficiaram do sistema proporcional:\r\n" + //
                "(com sua posição no ranking de mais votados)");
        printCandidatosFederaisEleitosNaoMajoritarios(candidatosFederais);

        Vector<Partido> part = getPartidos();
        part.sort(new Partido.ComparatorVotos());
        System.out.println("Votação dos partidos e número de candidatos eleitos:");
        printInfoVotacaoFederalPartidos(part);

        System.out.println("Total de votos válidos: " + part.get);
        System.out.println("Total de votos nominais: " + getVotosNominais());
        System.out.println("Total de votos de legenda: " + getVotosDeLegenda(part));
    }

    public void getRelatorioEstadual() {
        Vector<Candidato> candidatosEstaduais = getCandidatosEstaduais();
        candidatosEstaduais.sort(new Candidato.ComparatorVotos());
        
        System.out.println("Número de vagas: " + getQuantidadeCandidatosEstaduaisEleitos(candidatosEstaduais) + "\n");
        System.out.println("Deputados estaduais eleitos:");
        printCandidatosEstaduaisEleitos(candidatosEstaduais);
        System.out.println("Candidatos mais votados (em ordem decrescente de votação e respeitando número de vagas):");
        printCandidatosEstaduaisMaisVotados(candidatosEstaduais);
        System.out.println("Teriam sido eleitos se a votação fosse majoritária, e não foram eleitos:\r\n" + //
                "(com sua posição no ranking de mais votados)");
        printCandidatosEstaduaisMajoritariosNaoEleitos(candidatosEstaduais);
        System.out.println("Eleitos, que se beneficiaram do sistema proporcional:\r\n" + //
                "(com sua posição no ranking de mais votados)");
        printCandidatosEstaduaisEleitosNaoMajoritarios(candidatosEstaduais);

        Vector<Partido> part = getPartidos();
        part.sort(new Partido.ComparatorVotos());
        System.out.println("Votação dos partidos e número de candidatos eleitos:");
        printInfoVotacaoEstadualPartidos(part);

        String votosTotais = getVotosTotais(part);
        String votosNominais = getVotosNominais(part);
        String votosDeLegenda = getVotosDeLegenda(part);

        System.out.println("Total de votos válidos: " + votosTotais);
        System.out.println("Total de votos nominais: " + votosNominais);
        System.out.println("Total de votos de legenda: " + votosDeLegenda);
    }

    private String getVotosTotais(Vector<Partido> part) {
        return null;
    }

    private String getVotosDeLegenda(Vector<Partido> part){
        int votos = 0;
        for(Partido p : part){
            votos += p.getVotosLegenda();
        }
        return NumberFormat.getIntegerInstance(new Locale("pt","BR")).format(votos);
    }

    private String getVotosNominais() {
        int votos = 0;
        for(Candidato c : this.candidatos.values()){
            votos += c.getVotos();
        }
        return NumberFormat.getIntegerInstance(new Locale("pt","BR")).format(votos);
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
            c.getPartido().addVotosLegenda(votos);
        }
        else{
            if(c.isCandidaturaDeferida()){
                c.addVotos(votos);
                c.getPartido().addVotosNominais(votos);
            }
        }
    }

    public void addVotosPartido(int key, int votos){
        partidos.get(key).addVotosLegenda(votos);
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

    private Vector<Partido> getPartidos(){
        Vector<Partido> partidos = new Vector<Partido>();
        for(Partido p : this.partidos.values()){
            partidos.add(p);
        }
        return partidos;
    }

    private Vector<Candidato> getCandidatosEstaduais(){
        Vector<Candidato> candidatosEstaduais = new Vector<Candidato>();
        for(Candidato c : this.candidatos.values()){
            if(c.getCargo().toString().equals("ESTADUAL")){
                candidatosEstaduais.add(c);
            }
        }
        return candidatosEstaduais;
    }

    public int getQuantidadeCandidatosEstaduaisEleitos(Vector<Candidato> candidatos){
        int eleitos = 0;
        for(Candidato c : candidatos){
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

    public int getQuantidadeCandidatosFederaisEleitos(Vector<Candidato> candidatos){
        int eleitos = 0;
        for(Candidato c : candidatos){
            if(c.isEleito()){
                eleitos++;
            }
        }
        return eleitos;
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

    public void printCandidatosEstaduaisEleitos(Vector<Candidato> candidatos){
        int eleitos = getQuantidadeCandidatosEstaduaisEleitos(candidatos);
        String result = "";
        int i = 1;
        int idxCandidatoAtual = 0;
        while(i <= eleitos){
            Candidato c = candidatos.get(idxCandidatoAtual);
            if(c.isEleito() && c.getCargo().toString().equals("ESTADUAL")){
                Partido p = c.getPartido();
                int votos = c.getVotos();
                String votoStr = NumberFormat.getIntegerInstance(new Locale("pt","BR")).format(votos);
                result += i + " - ";
                if(c.getNumeroFederacao() != -1){
                    result += "*";
                }
                result += c.getNomeUrna() + " (" + p.getSigla() + ", " + votoStr + " votos)\n";
                i++;
            }
            idxCandidatoAtual++;
        }
        System.out.println(result);
    }

    public void printCandidatosFederaisEleitos(Vector<Candidato> candidatos){
        int eleitos = getQuantidadeCandidatosFederaisEleitos(candidatos);
        String result = "";
        int i = 1;
        int idxCandidatoAtual = 0;
        while(i <= eleitos){
            Candidato c = candidatos.get(idxCandidatoAtual);
            if(c.isEleito() && c.getCargo().toString().equals("FEDERAL")){
                Partido p = c.getPartido();
                int votos = c.getVotos();
                String votoStr = NumberFormat.getIntegerInstance(new Locale("pt","BR")).format(votos);
                result += i + " - ";
                if(c.getNumeroFederacao() != -1){
                    result += "*";
                }
                result += c.getNomeUrna() + " (" + p.getSigla() + ", " + votoStr + " votos)\n";
                i++;
            }
            idxCandidatoAtual++;
        }
        System.out.println(result);
    }

    public void printCandidatosFederaisMajoritariosNaoEleitos(Vector<Candidato> candidatos){
        int eleitos = getQuantidadeCandidatosFederaisEleitos(candidatos);
        int idxCandidatoAtual = 0;
        String result = "";
        for(int i = 1; i <= eleitos; i++){
            Candidato c = candidatos.get(idxCandidatoAtual);
            if(!c.isEleito()){
                Partido p = c.getPartido();
                int votos = c.getVotos();
                String votoStr = NumberFormat.getIntegerInstance(new Locale("pt","BR")).format(votos);
                result += i + " - ";
                if(c.getNumeroFederacao() != -1){
                    result += "*";
                }
                result += c.getNomeUrna() + " (" + p.getSigla() + ", " + votoStr + " votos)\n";
            }
            idxCandidatoAtual++;
        }
        System.out.println(result);
    }

    public void printCandidatosEstaduaisMajoritariosNaoEleitos(Vector<Candidato> candidatos){
        int eleitos = getQuantidadeCandidatosEstaduaisEleitos(candidatos);
        int idxCandidatoAtual = 0;
        String result = "";
        for(int i = 1; i <= eleitos; i++){
            Candidato c = candidatos.get(idxCandidatoAtual);
            if(!c.isEleito()){
                Partido p = c.getPartido();
                int votos = c.getVotos();
                String votoStr = NumberFormat.getIntegerInstance(new Locale("pt","BR")).format(votos);
                result += i + " - ";
                if(c.getNumeroFederacao() != -1){
                    result += "*";
                }
                result += c.getNomeUrna() + " (" + p.getSigla() + ", " + votoStr + " votos)\n";
            }
            idxCandidatoAtual++;
        }
        System.out.println(result);
    }

    public void printCandidatosFederaisMaisVotados(Vector<Candidato> candidatos){
        int eleitos = getQuantidadeCandidatosFederaisEleitos(candidatos);
        int idxCandidatoAtual = 0;
        String result = "";
        for(int i = 1; i <= eleitos; i++){
            Candidato c = candidatos.get(idxCandidatoAtual);
            Partido p = c.getPartido();
            int votos = c.getVotos();
            String votoStr = NumberFormat.getIntegerInstance(new Locale("pt","BR")).format(votos);
            result += i + " - ";
            if(c.getNumeroFederacao() != -1){
                result += "*";
            }
            result += c.getNomeUrna() + " (" + p.getSigla() + ", " + votoStr + " votos)\n";
            idxCandidatoAtual++;
        }
        System.out.println(result);
    }

    public void printCandidatosEstaduaisMaisVotados(Vector<Candidato> candidatos){
        int eleitos = getQuantidadeCandidatosEstaduaisEleitos(candidatos);
        int idxCandidatoAtual = 0;
        String result = "";
        for(int i = 1; i <= eleitos; i++){
            Candidato c = candidatos.get(idxCandidatoAtual);
            Partido p = c.getPartido();
            int votos = c.getVotos();
            String votoStr = NumberFormat.getIntegerInstance(new Locale("pt","BR")).format(votos);
            result += i + " - ";
            if(c.getNumeroFederacao() != -1){
                result += "*";
            }
            result += c.getNomeUrna() + " (" + p.getSigla() + ", " + votoStr + " votos)\n";
            idxCandidatoAtual++;
        }
        System.out.println(result);
    }

    public void printCandidatosFederaisEleitosNaoMajoritarios(Vector<Candidato> candidatos){
        int eleitos = getQuantidadeCandidatosFederaisEleitos(candidatos);
        int idxCandidatoAtual = eleitos;
        String result = "";
        for(int i = eleitos+1; i < candidatos.size(); i++){
            Candidato c = candidatos.get(idxCandidatoAtual);
            if(c.isEleito()){
                Partido p = c.getPartido();
                int votos = c.getVotos();
                String votoStr = NumberFormat.getIntegerInstance(new Locale("pt","BR")).format(votos);
                result += i + " - ";
                if(c.getNumeroFederacao() != -1){
                    result += "*";
                }
                result += c.getNomeUrna() + " (" + p.getSigla() + ", " + votoStr + " votos)\n";
            }
            idxCandidatoAtual++;
        }
        System.out.println(result);
    }

    public void printCandidatosEstaduaisEleitosNaoMajoritarios(Vector<Candidato> candidatos){
        int eleitos = getQuantidadeCandidatosEstaduaisEleitos(candidatos);
        int idxCandidatoAtual = eleitos;
        String result = "";
        for(int i = eleitos+1; i < candidatos.size(); i++){
            Candidato c = candidatos.get(idxCandidatoAtual);
            if(c.isEleito()){
                Partido p = c.getPartido();
                int votos = c.getVotos();
                String votoStr = NumberFormat.getIntegerInstance(new Locale("pt","BR")).format(votos);
                result += i + " - ";
                if(c.getNumeroFederacao() != -1){
                    result += "*";
                }
                result += c.getNomeUrna() + " (" + p.getSigla() + ", " + votoStr + " votos)\n";
            }
            idxCandidatoAtual++;
        }
        System.out.println(result);
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

    public void printInfoVotacaoEstadualPartidos(Vector<Partido> partidos){
        String result = "";
        int idxPartido = 1;
        for(Partido p : partidos){
            int votosL = p.getVotosLegenda();
            int votosN = p.getVotosNominais();
            int votosT = p.getVotosTotais();
            String votosLStr = NumberFormat.getIntegerInstance(new Locale("pt","BR")).format(votosL);
            String votosNStr = NumberFormat.getIntegerInstance(new Locale("pt","BR")).format(votosN);
            String votosTStr = NumberFormat.getIntegerInstance(new Locale("pt","BR")).format(votosT);
            result += idxPartido + " - " + p.getSigla() + " - " + p.getNumero() + ", " + votosTStr + " votos (" + votosNStr + " nominais e " + votosLStr + "de legenda), " + p.getQuantidadeCandidatosEstaduaisEleitos() + " candidatos eleitos";
            idxPartido++;
        }
        System.out.println(result);
    }

    public void printInfoVotacaoFederalPartidos(Vector<Partido> partidos){
        String result = "";
        int idxPartido = 1;
        for(Partido p : partidos){
            int votosL = p.getVotosLegenda();
            int votosN = p.getVotosNominais();
            int votosT = p.getVotosTotais();
            String votosLStr = NumberFormat.getIntegerInstance(new Locale("pt","BR")).format(votosL);
            String votosNStr = NumberFormat.getIntegerInstance(new Locale("pt","BR")).format(votosN);
            String votosTStr = NumberFormat.getIntegerInstance(new Locale("pt","BR")).format(votosT);
            result += idxPartido + " - " + p.getSigla() + " - " + p.getNumero() + ", " + votosTStr + " votos (" + votosNStr + " nominais e " + votosLStr + "de legenda), " + p.getQuantidadeCandidatosFederaisEleitos() + " candidatos eleitos";
            idxPartido++;
        }
        System.out.println(result);
    }
}
