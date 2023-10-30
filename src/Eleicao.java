import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

public class Eleicao {
    private LocalDate data;
    private String nivel;

    private Map<Integer,Candidato> candidatos = new HashMap<>();
    private Map<Integer,Partido> partidos = new HashMap<>();

    public Eleicao(String data, String nivel) {
        this.data = LocalDate.parse(data,DateTimeFormatter.ofPattern("dd/MM/yyyy"));;
        this.nivel = nivel;
    }

    public Vector<Candidato> getCandidatos(){
        Vector<Candidato> v = new Vector<Candidato>();
        for(Candidato c : this.candidatos.values()){
            v.add(c);
        }
        return v;
    }

    public void getRelatorio() {
        Vector<Candidato> cands = getCandidatos();
        cands.sort(new Candidato.ComparatorVotos(this.data));
        
        System.out.println("Número de vagas: " + getQuantidadeCandidatosEleitos(cands) + "\n");
        if(this.nivel.equals("FEDERAL")){
            System.out.println("Deputados federais eleitos:");
        }
        else{
            System.out.println("Deputados estaduais eleitos:");
        }
        printCandidatosEleitos(cands);

        System.out.println("Candidatos mais votados (em ordem decrescente de votação e respeitando número de vagas):");
        printCandidatosMaisVotados(cands);
        
        System.out.println("Teriam sido eleitos se a votação fosse majoritária, e não foram eleitos:\r\n" + //
                "(com sua posição no ranking de mais votados)");
        printCandidatosMajoritariosNaoEleitos(cands);
        System.out.println("Eleitos, que se beneficiaram do sistema proporcional:\r\n" + //
                "(com sua posição no ranking de mais votados)");
        printCandidatosEleitosNaoMajoritarios(cands);

        Vector<Partido> parts = getPartidos();
        parts.sort(new Partido.ComparatorVotos());
        System.out.println("Votação dos partidos e número de candidatos eleitos:");
        printInfoVotacaoPartidos(parts);

        System.out.println("Primeiro e último colocados de cada partido:");
        printPrimeiroEUltimoPorPartido(parts);

        System.out.println("Eleitos, por faixa etária (na data da eleição):");
        printCandidatosEleitosFaixaEtaria(cands);

        System.out.println("Eleitos, por gênero:");
        printEleitosPorGenero(cands);

        printTotalDeVotos(parts);
    }

    /*public void getRelatorioFederal() {
        Vector<Candidato> candidatosFederais = getCandidatosFederais();
        candidatosFederais.sort(new Candidato.ComparatorVotos());
        
        System.out.println("Número de vagas: " + getQuantidadeCandidatosFederaisEleitos(candidatosFederais) + "\n");
        System.out.println("Deputados federais eleitos:");
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

        System.out.println("Eleitos, por gênero:");
        printEleitosFederaisPorGenero(candidatosFederais);

        String votosTotais = getVotosTotais(part);
        String votosNominais = getVotosNominais(part);
        String votosDeLegenda = getVotosDeLegenda(part);

        System.out.println("Total de votos válidos: " + votosTotais);
        System.out.println("Total de votos nominais: " + votosNominais);
        System.out.println("Total de votos de legenda: " + votosDeLegenda);
    }*/

    /*public void getRelatorioEstadual() {
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

        System.out.println("Eleitos, por gênero:");
        printEleitosEstaduaisPorGenero(candidatosEstaduais);

        String votosTotais = getVotosTotais(part);
        String votosNominais = getVotosNominais(part);
        String votosDeLegenda = getVotosDeLegenda(part);

        System.out.println("Total de votos válidos: " + votosTotais);
        System.out.println("Total de votos nominais: " + votosNominais);
        System.out.println("Total de votos de legenda: " + votosDeLegenda);
    }*/

    private int getVotosTotais(Vector<Partido> part) {
        int votos = 0;
        for(Partido p : part){
            votos += p.getVotosTotais();
        }
        return votos;
    }

    private int getVotosDeLegenda(Vector<Partido> part){
        int votos = 0;
        for(Partido p : part){
            votos += p.getVotosLegenda();
        }
        return votos;
    }

    private int getVotosNominais(Vector<Partido> part) {
        int votos = 0;
        for(Partido p : part){
            votos += p.getVotosNominais();
        }
        return votos;
    }

    public void addCandidatoEPartido(Candidato c, Partido p){
        if(partidos.containsKey(p.getNumero())){
            p = partidos.get(p.getNumero());
        }

        if((this.nivel.equals("FEDERAL") && c.getCargo().toString().equals("FEDERAL")) || (c.getCargo().toString().equals("ESTADUAL") && this.nivel.equals("ESTADUAL"))){
            if(c.isCandidaturaDeferida() || (!c.isCandidaturaDeferida() && c.isVotoLegenda())){
                c.setPartido(p);
                p.addCandidatosFiliados(c);

                candidatos.put(c.getNumero(), c);
                partidos.put(p.getNumero(),p);
            }
        }
        else{
            partidos.put(p.getNumero(),p);
        }
    }

    public void addVotosCandidato(int key, int votos){
        Candidato c = candidatos.get(key);
        if(c.isVotoLegenda()){
            c.getPartido().addVotosLegenda(votos);
        }
        else{
            if(c.isCandidaturaDeferida()){
                c.addVotos(votos);
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

    public LocalDate getData() {
        return data;
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

    /*private Vector<Candidato> getCandidatosEstaduais(){
        Vector<Candidato> candidatosEstaduais = new Vector<Candidato>();
        for(Candidato c : this.candidatos.values()){
            if(c.getCargo().toString().equals("ESTADUAL")){
                candidatosEstaduais.add(c);
            }
        }
        return candidatosEstaduais;
    }*/

    public int getQuantidadeCandidatosEstaduaisEleitos(Vector<Candidato> candidatos){
        int eleitos = 0;
        for(Candidato c : candidatos){
            if(c.isEleito()){
                eleitos++;
            }
        }
        return eleitos;
    }

    /*private Vector<Candidato> getCandidatosFederais(){
        Vector<Candidato> candidatosFederais = new Vector<Candidato>();
        for(Candidato c : candidatos.values()){
            if(c.getCargo().toString().equals("FEDERAL")){
                candidatosFederais.add(c);
            }
        }
        return candidatosFederais;
    }*/

    public int getQuantidadeCandidatosEleitos(Vector<Candidato> candidatos){
        int eleitos = 0;
        for(Candidato c : candidatos){
            if(c.isEleito()){
                eleitos++;
            }
        }
        return eleitos;
    }

    /*public int getQuantidadeCandidatosFederaisEleitos(Vector<Candidato> candidatos){
        int eleitos = 0;
        for(Candidato c : candidatos){
            if(c.isEleito()){
                eleitos++;
            }
        }
        return eleitos;
    }*/

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

    /*public void printCandidatosEstaduaisEleitos(Vector<Candidato> candidatos){
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
    }*/

    private void printCandidatosEleitos(Vector<Candidato> candidatos){
        int eleitos = getQuantidadeCandidatosEleitos(candidatos);
        String result = "";
        int i = 1;
        int idxCandidatoAtual = 0;
        while(i <= eleitos){
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
                i++;
            }
            idxCandidatoAtual++;
        }
        System.out.println(result);
    }

    /*public void printCandidatosFederaisEleitos(Vector<Candidato> candidatos){
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
    }*/

    private void printCandidatosMajoritariosNaoEleitos(Vector<Candidato> candidatos){
        int eleitos = getQuantidadeCandidatosEleitos(candidatos);
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

    /*public void printCandidatosFederaisMajoritariosNaoEleitos(Vector<Candidato> candidatos){
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
    }*/

    /*public void printCandidatosEstaduaisMajoritariosNaoEleitos(Vector<Candidato> candidatos){
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
    }*/

    private void printCandidatosMaisVotados(Vector<Candidato> candidatos){
        int eleitos = getQuantidadeCandidatosEleitos(candidatos);
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

    /*public void printCandidatosFederaisMaisVotados(Vector<Candidato> candidatos){
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
    }*/

    /*public void printCandidatosEstaduaisMaisVotados(Vector<Candidato> candidatos){
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
    }*/

    private void printCandidatosEleitosNaoMajoritarios(Vector<Candidato> candidatos){
        int eleitos = getQuantidadeCandidatosEleitos(candidatos);
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

    /*public void printCandidatosFederaisEleitosNaoMajoritarios(Vector<Candidato> candidatos){
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
    }*/

    /*public void printCandidatosEstaduaisEleitosNaoMajoritarios(Vector<Candidato> candidatos){
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
    }*/

    /*public void printCandidatosFederais(){
        for(Candidato c : candidatos.values()){
            if(c.getCargo().toString().equals("FEDERAL")){
                System.out.println(c);
            }
        }
    }*/

    /*public void printCandidatosEstaduais(){
        for(Candidato c : candidatos.values()){
            if(c.getCargo().toString().equals("ESTADUAL")){
                System.out.println(c);
            }
        }
    }*/

    /*private void printEleitosEstaduaisPorGenero(Vector<Candidato> candidatos) {
        int eleitos = getQuantidadeCandidatosEstaduaisEleitos(candidatos);
        String result = "";
        int i = 1;
        int idxCandidatoAtual = 0;
        double candidatosFeminino = 0;
        double candidatosMasculino = 0;
        while(i <= eleitos){
            Candidato c = candidatos.get(idxCandidatoAtual);
            if(c.isEleito() && c.getCargo().toString().equals("FEDERAL")){
                if(c.getGenero().toString().equals("MASCULINO")){
                    candidatosMasculino++;
                }
                else{
                    if(c.getGenero().toString().equals("FEMININO")){
                        candidatosFeminino++;
                    }
                }
                i++;
            }
            idxCandidatoAtual++;
        }

        double percentFem = ((candidatosFeminino/eleitos)*100);
        double percentMasc= ((candidatosMasculino/eleitos)*100);

        //Locale brLocale = Locale.forLanguageTag("pt-BR");
		NumberFormat nf = NumberFormat.getInstance(new Locale("pt","BR"));
		nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);

        result+="Feminino: " + (int)candidatosFeminino + " (" + nf.format(percentFem) + "%)\n";
        result+="Feminino: " + (int)candidatosMasculino + " (" + nf.format(percentMasc) + "%)\n";
        System.out.println(result);
    }*/

    private void printPrimeiroEUltimoPorPartido(Vector<Partido> partidos){
        String result="";
        for(Partido p : partidos){
            p.sortCandidatosFiliados(this.data);
        }

        /*for(Partido p : partidos){
            int i = 0;
            System.out.println("CANDIDATOS FILIADOS DO PARTIDO: " + p.getSigla() + "\n");
            Vector<Candidato> candidatosFili = p.getCandidatosFiliados();
            for(Candidato c : candidatosFili){
                System.out.println(i + " - " + c);
                i++;
            }
        }*/
        partidos.sort(new Partido.ComparatorCandidatoMaisVotado(this.data));

        int idxPartido = 1;
        for(Partido p : partidos){
            List<Candidato> candidatosFiliadosPartido = p.getCandidatosFiliados();
            if(candidatosFiliadosPartido.size() >= 1 && p.getVotosNominais() > 0){
                Candidato candidatoMaisVotadoPartido = candidatosFiliadosPartido.get(0);
                result += idxPartido + " - " + p.getSigla() + " - " + p.getNumero() + ", " + candidatoMaisVotadoPartido.getNomeUrna() + " (" + candidatoMaisVotadoPartido.getNumero() + ", ";
                
                int votosCandidatoMaisVotado = candidatoMaisVotadoPartido.getVotos();
                
                if(votosCandidatoMaisVotado <= 1){
                    result += NumberFormat.getIntegerInstance(new Locale("pt","BR")).format(votosCandidatoMaisVotado) + " voto) / ";
                }
                else{
                    result += NumberFormat.getIntegerInstance(new Locale("pt","BR")).format(votosCandidatoMaisVotado) + " votos) / ";
                }

                int idxCandidatoAtual = candidatosFiliadosPartido.size()-1;
                Candidato candidatoMenosVotadoPartido = new Candidato();
                while(idxCandidatoAtual >= 0){
                    candidatoMenosVotadoPartido = candidatosFiliadosPartido.get(idxCandidatoAtual);
                    if(candidatoMenosVotadoPartido.getVotos() > 0){
                        break;
                    }
                    idxCandidatoAtual--;
                }
                
                result += candidatoMenosVotadoPartido.getNomeUrna() + " (" + candidatoMenosVotadoPartido.getNumero() + ", ";

                int votosCandidatoMenosVotado = candidatoMenosVotadoPartido.getVotos();

                if(votosCandidatoMenosVotado <= 1){
                    result += NumberFormat.getIntegerInstance(new Locale("pt","BR")).format(votosCandidatoMenosVotado) + " voto)\n";
                }
                else{
                    result += NumberFormat.getIntegerInstance(new Locale("pt","BR")).format(votosCandidatoMenosVotado) + " votos)\n";
                }
                idxPartido++;
            }
        }
        System.out.println(result);
    }

    private void printEleitosPorGenero(Vector<Candidato> candidatos){
        int eleitos = getQuantidadeCandidatosEleitos(candidatos);
        String result = "";
        int i = 1;
        int idxCandidatoAtual = 0;
        double candidatosFeminino = 0;
        double candidatosMasculino = 0;
        while(i <= eleitos){
            Candidato c = candidatos.get(idxCandidatoAtual);
            if(c.isEleito()){
                if(c.getGenero().toString().equals("MASCULINO")){
                    candidatosMasculino++;
                }
                else{
                    if(c.getGenero().toString().equals("FEMININO")){
                        candidatosFeminino++;
                    }
                }
                i++;
            }
            idxCandidatoAtual++;
        }

        double percentFem = ((candidatosFeminino/eleitos)*100);
        double percentMasc= ((candidatosMasculino/eleitos)*100);

        //Locale brLocale = Locale.forLanguageTag("pt-BR");
		NumberFormat nf = NumberFormat.getInstance(new Locale("pt","BR"));
		nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);

        result+="Feminino: " + (int)candidatosFeminino + " (" + nf.format(percentFem) + "%)\n";
        result+="Masculino: " + (int)candidatosMasculino + " (" + nf.format(percentMasc) + "%)\n";
        System.out.println(result);
    }

    /*private void printEleitosFederaisPorGenero(Vector<Candidato> candidatos) {
        int eleitos = getQuantidadeCandidatosFederaisEleitos(candidatos);
        String result = "";
        int i = 1;
        int idxCandidatoAtual = 0;
        double candidatosFeminino = 0;
        double candidatosMasculino = 0;
        while(i <= eleitos){
            Candidato c = candidatos.get(idxCandidatoAtual);
            if(c.isEleito() && c.getCargo().toString().equals("FEDERAL")){
                if(c.getGenero().toString().equals("MASCULINO")){
                    candidatosMasculino++;
                }
                else{
                    if(c.getGenero().toString().equals("FEMININO")){
                        candidatosFeminino++;
                    }
                }
                i++;
            }
            idxCandidatoAtual++;
        }

        double percentFem = ((candidatosFeminino/eleitos)*100);
        double percentMasc= ((candidatosMasculino/eleitos)*100);

        //Locale brLocale = Locale.forLanguageTag("pt-BR");
		NumberFormat nf = NumberFormat.getInstance(new Locale("pt","BR"));
		nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);

        result+="Feminino: " + (int)candidatosFeminino + " (" + nf.format(percentFem) + "%)\n";
        result+="Feminino: " + (int)candidatosMasculino + " (" + nf.format(percentMasc) + "%)\n";
        System.out.println(result);
    }*/

    /*public void printInfoVotacaoEstadualPartidos(Vector<Partido> partidos){
        String result = "";
        int idxPartido = 1;
        for(Partido p : partidos){
            int votosL = p.getVotosLegenda();
            int votosN = p.getVotosNominais();
            int votosT = p.getVotosTotais();
            String votosLStr = NumberFormat.getIntegerInstance(new Locale("pt","BR")).format(votosL);
            String votosNStr = NumberFormat.getIntegerInstance(new Locale("pt","BR")).format(votosN);
            String votosTStr = NumberFormat.getIntegerInstance(new Locale("pt","BR")).format(votosT);
            result += idxPartido + " - " + p.getSigla() + " - " + p.getNumero() + ", " + votosTStr + " votos (" + votosNStr + " nominais e " + votosLStr + " de legenda), ";
            if(p.getQuantidadeCandidatosEstaduaisEleitos() <= 1){
                result+= p.getQuantidadeCandidatosEstaduaisEleitos() + " candidato eleito\n";
            }
            else{
                result+= p.getQuantidadeCandidatosEstaduaisEleitos() + " candidatos eleitos\n";
            }
            idxPartido++;
        }
        System.out.println(result);
    }*/

    private void printCandidatosEleitosFaixaEtaria(Vector<Candidato> candidatos){
        String result = "";
        int eleitos = getQuantidadeCandidatosEleitos(candidatos);
        int idxCandidatoAtual = 0;
        int idxAtual = 0;
        double abaixo30 = 0;
        double entre30e40 = 0;
        double entre40e50 = 0;
        double entre50e60 = 0;
        double acima60 = 0;

        while(idxAtual < eleitos){
            Candidato c = candidatos.get(idxCandidatoAtual);
            if(c.isEleito()){
                if(c.getIdade(this.data) < 30){
                    abaixo30++;
                }
                else if(c.getIdade(this.data) >= 30 && c.getIdade(this.data) < 40){
                    entre30e40++;
                }
                else if(c.getIdade(this.data) >= 40 && c.getIdade(this.data) < 50){
                    entre40e50++;
                }
                else if(c.getIdade(this.data) >= 50 && c.getIdade(this.data) < 60){
                    entre50e60++;
                }
                else{
                    acima60++;
                }
                idxAtual++;
            }
            idxCandidatoAtual++;
        }

        double percentAbaixo30 = (abaixo30/eleitos)*100;
        double percentEntre30e40 = (entre30e40/eleitos)*100;
        double percentEntre40e50 = (entre40e50/eleitos)*100;
        double percentEntre50e60 = (entre50e60/eleitos)*100;
        double percentAcima60 = (acima60/eleitos)*100;

        NumberFormat nf = NumberFormat.getInstance(new Locale("pt","BR"));
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);

        result += "      Idade < 30: " + (int)abaixo30 + " (" + nf.format(percentAbaixo30) + "%)\n";
        result += "30 <= Idade < 40: " + (int)entre30e40 + " (" + nf.format(percentEntre30e40) + "%)\n";
        result += "40 <= Idade < 50: " + (int)entre40e50 + " (" + nf.format(percentEntre40e50) + "%)\n";
        result += "50 <= Idade < 60: " + (int)entre50e60 + " (" + nf.format(percentEntre50e60) + "%)\n";
        result += "60 <= Idade     : " + (int)acima60 + " (" + nf.format(percentAcima60) + "%)\n";
        System.out.println(result);
    }

    private void printInfoVotacaoPartidos(Vector<Partido> partidos){
        String result = "";
        int idxPartido = 1;
        for(Partido p : partidos){
            int votosL = p.getVotosLegenda();
            int votosN = p.getVotosNominais();
            int votosT = p.getVotosTotais();

            String votosLStr = NumberFormat.getIntegerInstance(new Locale("pt","BR")).format(votosL);
            String votosNStr = NumberFormat.getIntegerInstance(new Locale("pt","BR")).format(votosN);
            String votosTStr = NumberFormat.getIntegerInstance(new Locale("pt","BR")).format(votosT);

            result += idxPartido + " - " + p.getSigla() + " - " + p.getNumero() + ", ";
            if(votosT <= 1){
                result+= votosTStr + " voto (";
            }
            else{
                result += votosTStr + " votos (";
            }

            if(votosN <= 1){
                result+= votosNStr + " nominal e ";
            }
            else{
                result+= votosNStr + " nominais e ";
            }

            result+=votosLStr + " de legenda), ";

            if(p.getQuantidadeCandidatosEleitos() <= 1){
                result+= p.getQuantidadeCandidatosEleitos() + " candidato eleito\n";
            }
            else{
                result+= p.getQuantidadeCandidatosEleitos() + " candidatos eleitos\n";
            }
            idxPartido++;
        }
        System.out.println(result);
    }

    private void printTotalDeVotos(Vector<Partido> partidos){
        double votosTotais = (double)getVotosTotais(partidos);
        double votosNominais = (double)getVotosNominais(partidos);
        double votosDeLegenda = (double)getVotosDeLegenda(partidos);

        double percentN = (votosNominais/votosTotais)*100;
        double percentL = (votosDeLegenda/votosTotais)*100;

        NumberFormat nf = NumberFormat.getInstance(new Locale("pt","BR"));
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);

        System.out.println("Total de votos válidos: " + NumberFormat.getIntegerInstance(new Locale("pt","BR")).format(votosTotais));
        System.out.println("Total de votos nominais: " + NumberFormat.getIntegerInstance(new Locale("pt","BR")).format(votosNominais) + " (" + nf.format(percentN) + "%)");
        System.out.println("Total de votos de legenda: " + NumberFormat.getIntegerInstance(new Locale("pt","BR")).format(votosDeLegenda) + " (" + nf.format(percentL) + "%)");
    }

    /*public void printInfoVotacaoFederalPartidos(Vector<Partido> partidos){
        String result = "";
        int idxPartido = 1;
        for(Partido p : partidos){
            int votosL = p.getVotosLegenda();
            int votosN = p.getVotosNominais();
            int votosT = p.getVotosTotais();

            String votosLStr = NumberFormat.getIntegerInstance(new Locale("pt","BR")).format(votosL);
            String votosNStr = NumberFormat.getIntegerInstance(new Locale("pt","BR")).format(votosN);
            String votosTStr = NumberFormat.getIntegerInstance(new Locale("pt","BR")).format(votosT);

            result += idxPartido + " - " + p.getSigla() + " - " + p.getNumero() + ", ";
            if(votosT <= 1){
                result+= votosTStr + " voto (";
            }
            else{
                result += votosTStr + " votos (";
            }

            result+= votosNStr + " nominais e " + votosLStr + " de legenda), ";

            if(p.getQuantidadeCandidatosFederaisEleitos() <= 1){
                result+= p.getQuantidadeCandidatosFederaisEleitos() + " candidato eleito\n";
            }
            else{
                result+= p.getQuantidadeCandidatosFederaisEleitos() + " candidatos eleitos\n";
            }
            idxPartido++;
        }
        System.out.println(result);
    }*/
    
    public void printPartidos(){
        for(Partido p : this.partidos.values()){
            if(p.getSigla().equals("REPUBLICANOS")){
                for(Candidato c : p.getCandidatosFiliados()){
                    if(c.getNumero() == 10345){
                        System.out.println(c);
                    }
                }
            }
        }
    }
}
