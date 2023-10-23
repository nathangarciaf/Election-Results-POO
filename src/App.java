import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

public class App {
    public static void main(String[] args) {
        boolean headerVerify=true;
        Set<Candidato> candidatos = new HashSet<Candidato>();
        Set<Partido> partidos = new HashSet<Partido>();

        try (FileInputStream fin = new FileInputStream("candidatosES.csv");
            Scanner s = new Scanner(fin, "ISO-8859-1")) {
            Vector<Integer> headerAttributesVector = new Vector<Integer>();
                   
            while (s.hasNextLine()) {
                String line = s.nextLine();
                String lineFormat = line.replaceAll("\"", "");
                if(headerVerify != false){
                    //String header = lineFormat;
                    String[] headers = lineFormat.split(";");
                    int idxAttributes = 0;
                    for(String headerAttribute : headers){
                        //System.out.println(headerAttribute);
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
                                headerAttributesVector.add(idxAttributes);
                            }
                        idxAttributes++;
                    }

                    /*try(Scanner headerScanner = new Scanner(header);){
                        headerScanner.useDelimiter(";");
                        boolean vecAdd = false;
                        int idxAttriibutes = 0;

                        while(headerScanner.hasNextLine()){
                            String headerAttributes = headerScanner.next();

                            if(headerAttributes.equals("CD_CARGO") || 
                               headerAttributes.equals("CD_SITUACAO_CANDIDATO_TOT") ||
                               headerAttributes.equals("NR_CANDIDATO") || 
                               headerAttributes.equals("NM_URNA_CANDIDATO") ||
                               headerAttributes.equals("NR_PARTIDO") ||
                               headerAttributes.equals("SG_PARTIDO") ||
                               headerAttributes.equals("DT_NASCIMENTO") ||
                               headerAttributes.equals("CD_SIT_TOT_TURNO") ||
                               headerAttributes.equals("CD_GENERO") ||
                               headerAttributes.equals("NM_TIPO_DESTINACAO_VOTOS") ||
                               headerAttributes.equals("NR_FEDERACAO")){
                                vecAdd=true;
                            }

                            if(vecAdd != false){
                                System.out.println(headerAttributes);
                                System.out.println();
                                headerAttributesVector.add(idxAttriibutes);
                                vecAdd=false;
                            }
                            idxAttriibutes++;
                        }
                    }*/
                    headerVerify=false;
                }
                else{
                    String[] lineAttributes = lineFormat.split(";");
                    int idxLineAttributes = 0;
                    Candidato cand = new Candidato();
                    Partido partido = new Partido();
                    for(int i : headerAttributesVector){
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
                            
                        }
                        //NOME DESTINACAO VOTOS
                        else if(idxLineAttributes == 9){
                            
                        }
                        //CODIGO SITUACAO TOTAL CANDIDATO
                        else if(idxLineAttributes == 10){
                            
                        }
                        idxLineAttributes++;
                        //System.out.println(lineAttributes[i]);
                    }
                    /*boolean partidoVerify = false;
                    for(Partido p : partidos){

                    }*/
                    cand.setPartido(partido);
                    //System.out.println(partido);
                    partido.addCandidatosFiliados(cand);

                    candidatos.add(cand);
                    partidos.add(partido);

                    //System.out.println();
                }
            }
            /*for(Partido p : partidos){
                System.out.println(p);
            }*/
            //System.out.println(headerAttributesVector);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // IMPLEMENTAR UMA INTERFACE DE COMPARATOR???
        /*Set<Partido> fili = new HashSet<Partido>();
        Partido andre = new Partido();
        andre.setSigla("SGA");
        fili.add(andre);

        Partido claudio = new Partido();
        claudio.setSigla("SGB");
        boolean verifyPartido = true;
        for(Partido part : fili){
            if(part.getSigla().equals(claudio.getSigla())){
                verifyPartido=false;
            }
        }
        if(verifyPartido != false){
            fili.add(claudio);
        }

        System.out.println(fili);*/
    }
}
