import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        //System.out.println(args.length);
        boolean headerVerify=true;
        Map<Integer,Candidato> candidatos = new HashMap<>();
        Map<Integer,Partido> partidos = new HashMap<>();

        /*args[0] = federal ou estadual
        args[1] = path pro candidatos
        args[2] = path pra votacao
        args[3] = data da votacao
        */
        try (FileInputStream fin = new FileInputStream(args[1]);
            Scanner s = new Scanner(fin, "ISO-8859-1")) {
            Vector<Integer> headerAttributesIndexes = new Vector<Integer>();
                   
            while (s.hasNextLine()) {
                String line = s.nextLine();
                String lineFormat = line.replaceAll("\"", "");
                if(headerVerify != false){
                    String[] headers = lineFormat.split(";");
                    int idxAttributes = 0;
                    for(String headerAttribute : headers){
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
                                headerAttributesIndexes.add(idxAttributes);
                            }
                        idxAttributes++;
                    }
                    headerVerify=false;
                }
                else{
                    String[] lineAttributes = lineFormat.split(";");
                    int idxLineAttributes = 0;
                    Candidato cand = new Candidato();
                    Partido partido = new Partido();
                    for(int i : headerAttributesIndexes){
                        //CODIGO CARGO
                        if(idxLineAttributes == 0){
                            Cargo c = Cargo.getCargo(lineAttributes[i]);
                            cand.setCargo(c);
                        }
                        //NUMERO CANDIDATO
                        else if(idxLineAttributes == 1){
                            int nrCand = Integer.parseInt(lineAttributes[i]);
                            cand.setNumero(nrCand);
                        }
                        //NOME URNA
                        else if(idxLineAttributes == 2){
                            cand.setNomeUrna(lineAttributes[i]);
                        }
                        //NUMERO PARTIDO
                        else if(idxLineAttributes == 3){
                            int nrPartido = Integer.parseInt(lineAttributes[i]);
                            partido.setNumero(nrPartido);
                        }
                        //SIGLA PARTIDO
                        else if(idxLineAttributes == 4){
                            partido.setSigla(lineAttributes[i]);
                        }
                        //NUMERO FEDERACAO
                        else if(idxLineAttributes == 5){
                            int nrFederacao = Integer.parseInt(lineAttributes[i]);
                            cand.setNumeroFederacao(nrFederacao);
                        }
                        //DATA NASCIMENTO
                        else if(idxLineAttributes == 6){
                            LocalDate ld = LocalDate.parse(lineAttributes[i],DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                            cand.setDataNascimento(ld);
                        }
                        //CODIGO GENERO
                        else if(idxLineAttributes == 7){
                            Genero g = Genero.getGenero(lineAttributes[i]);
                            cand.setGenero(g);
                        }
                        //CODIGO SITUACAO TOTAL TURNO
                        else if(idxLineAttributes == 8){
                            int codigoEleito = Integer.parseInt(lineAttributes[i]);
                            if(codigoEleito == 2 || codigoEleito == 3){
                                cand.setEleito(true);
                            }        
                        }
                        //NOME DESTINACAO VOTOS
                        else if(idxLineAttributes == 9){
                            //System.out.println(lineAttributes[i]);
                            if(lineAttributes[i].contains("legenda")){
                                cand.setVotoLegenda(true);
                            }
                        }
                        //CODIGO SITUACAO TOTAL CANDIDATO
                        else if(idxLineAttributes == 10){
                            int codigoDeferimento = Integer.parseInt(lineAttributes[i]);
                            if(codigoDeferimento == 2 || codigoDeferimento == 16){
                                cand.setCandidaturaDeferida(true);
                            }
                        }
                        idxLineAttributes++;
                    }
                    cand.setPartido(partido);
                    partido.addCandidatosFiliados(cand);

                    partidos.put(partido.getNumero(),partido);
                    candidatos.put(cand.getNumero(), cand);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        headerVerify=true;

        try (FileInputStream fin2 = new FileInputStream(args[2]);
            Scanner s = new Scanner(fin2, "ISO-8859-1")) {
            Vector<Integer> headerAttributesIndexes = new Vector<Integer>();

            while(s.hasNextLine()){
                String line = s.nextLine();
                String lineFormat = line.replaceAll("\"", "");
                if(headerVerify != false){
                    String[] headers = lineFormat.split(";");
                    int idxAttributes = 0;
                    for(String headerAttribute : headers){
                        if(headerAttribute.equals("CD_CARGO") || 
                           headerAttribute.equals("QT_VOTOS") ||
                           headerAttribute.equals("NR_VOTAVEL")){
                            System.out.println(headerAttribute);
                            headerAttributesIndexes.add(idxAttributes);
                        }
                        idxAttributes++;
                    }
                    headerVerify=false;
                }
                else{
                    String[] lineAttributes = lineFormat.split(";");
                    int idxLineAttributes = 0;
                    int numVotavel = 0;
                    for(int i : headerAttributesIndexes){
                        //CODIGO CARGO
                        if(idxLineAttributes == 0){

                        }
                        //NUMERO DO VOTO
                        else if(idxLineAttributes == 1){
                            int nrVotavel = Integer.parseInt(lineAttributes[i]);
                            if(nrVotavel != 95){
                                numVotavel = nrVotavel;
                            }
                        }
                        //QUANTIDADE DE VOTOS
                        else if(idxLineAttributes == 2){

                        }
                        idxLineAttributes++;
                    }
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        // IMPLEMENTAR UMA INTERFACE DE COMPARATOR???
        if(args[0] == "--federal"){

        }
        else if(args[0] == "--estadual"){

        }
        else{
            
        }
    }
}
