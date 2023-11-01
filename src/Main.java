import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        String nivelEleicao = "";
        if(args[0].equals("--federal")){
            nivelEleicao="FEDERAL";
        }
        else if(args[0].equals("--estadual")){
            nivelEleicao="ESTADUAL";
        }
        else{

        }

        Eleicao eleicao = new Eleicao(args[3], nivelEleicao);
        boolean headerVerify=true;

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
                    eleicao.addHeaderIdxCandidato(headerAttributesIndexes, lineFormat.split(";"));
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
                            try {
                                int nrCand = Integer.parseInt(lineAttributes[i]);
                                cand.setNumero(nrCand);
                            } catch (NumberFormatException nfe) {
                                System.out.println("Numero do candidato inválido");
                            }
                                
                        }
                        //NOME URNA
                        else if(idxLineAttributes == 2){
                            cand.setNomeUrna(lineAttributes[i].trim());
                        }
                        //NUMERO PARTIDO
                        else if(idxLineAttributes == 3){
                            try {
                                int nrPartido = Integer.parseInt(lineAttributes[i]);
                                partido.setNumero(nrPartido);
                            } catch (NumberFormatException nfe) {
                                System.out.println("Numero de partido inválido");
                            }
                        }
                        //SIGLA PARTIDO
                        else if(idxLineAttributes == 4){
                            partido.setSigla(lineAttributes[i]);
                        }
                        //NUMERO FEDERACAO
                        else if(idxLineAttributes == 5){
                            try {
                                int nrFederacao = Integer.parseInt(lineAttributes[i]);
                                cand.setNumeroFederacao(nrFederacao);
                            } catch (NumberFormatException nfe) {
                                System.out.println("Numero de federação inválido");
                            }
                        }
                        //DATA NASCIMENTO
                        else if(idxLineAttributes == 6){
                            try {
                                LocalDate ld = LocalDate.parse(lineAttributes[i],DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                                cand.setDataNascimento(ld);
                            } catch (DateTimeParseException dt) {
                                System.out.println("Data inválida!");
                            }
                        }
                        //CODIGO GENERO
                        else if(idxLineAttributes == 7){
                            Genero g = Genero.getGenero(lineAttributes[i]);
                            cand.setGenero(g);
                        }
                        //CODIGO SITUACAO TOTAL TURNO
                        else if(idxLineAttributes == 8){
                            try {
                                int codigoEleito = Integer.parseInt(lineAttributes[i]);
                                if(codigoEleito == 2 || codigoEleito == 3)
                                    cand.setEleito(true);
                            } 
                            catch (NumberFormatException nfe) {
                                System.out.println("O número referente ao código de situacao do candidato é inválido");
                            }
                        }
                        //NOME DESTINACAO VOTOS
                        else if(idxLineAttributes == 9){
                            if(lineAttributes[i].contains("legenda")){
                                cand.setVotoLegenda(true);
                            }
                        }
                        //CODIGO SITUACAO TOTAL CANDIDATO
                        else if(idxLineAttributes == 10){
                            try {
                                int codigoDeferimento = Integer.parseInt(lineAttributes[i]);
                                if(codigoDeferimento == 2 || codigoDeferimento == 16)
                                    cand.setCandidaturaDeferida(true);
                            } 
                            catch (NumberFormatException nfe) {
                                System.out.println("O número referente ao deferimento do candidato é inválido");
                            }
                        }
                        idxLineAttributes++;
                    }
                    eleicao.addCandidatoEPartido(cand,partido);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        //eleicao.printPartidos();

        headerVerify=true;

        try (FileInputStream fin2 = new FileInputStream(args[2]);
            Scanner s = new Scanner(fin2, "ISO-8859-1")) {
            Vector<Integer> headerAttributesIndexes = new Vector<Integer>();
            while(s.hasNextLine()){
                String line = s.nextLine();
                String lineFormat = line.replaceAll("\"", "");
                
                String codigoCargo = "";
                int numVotavel = 0;
                int votos = 0;

                if(headerVerify != false){
                    eleicao.addHeaderIdxElection(headerAttributesIndexes, lineFormat.split(";"));
                    headerVerify=false;
                }
                else{
                    String[] lineAttributes = lineFormat.split(";");
                    int idxLineAttributes = 0;
                    for(int i : headerAttributesIndexes){
                        //CODIGO CARGO
                        if(idxLineAttributes == 0){
                            codigoCargo = lineAttributes[i];
                        }
                        //NUMERO DO VOTO
                        else if(idxLineAttributes == 1){
                            int nrVotavel = Integer.parseInt(lineAttributes[i]);
                            if(nrVotavel != 95 ||
                               nrVotavel != 96 ||
                               nrVotavel != 97 ||
                               nrVotavel != 98){
                                numVotavel = nrVotavel;
                            }
                        }
                        //QUANTIDADE DE VOTOS
                        else if(idxLineAttributes == 2){
                            votos = Integer.parseInt(lineAttributes[i]);
                        }
                        idxLineAttributes++;
                    }
                    Cargo c = Cargo.getCargo(codigoCargo);
                    if(nivelEleicao.equals("FEDERAL")){
                        if((c.toString()).equals("FEDERAL")){
                            if(eleicao.constainsCandidatoKey(numVotavel)){
                                eleicao.addVotosCandidato(numVotavel, votos);
                            }
                            else{
                                if(eleicao.constainsPartidoKey(numVotavel)){
                                    eleicao.addVotosPartido(numVotavel, votos);
                                }
                            }
                        }
                    }
                    else if(nivelEleicao.equals("ESTADUAL")){
                        if((c.toString()).equals("ESTADUAL")){
                            //System.out.println("VOTE:" + numVotavel + "/");
                            if(eleicao.constainsCandidatoKey(numVotavel)){
                                eleicao.addVotosCandidato(numVotavel, votos);
                            }
                            else{
                                if(eleicao.constainsPartidoKey(numVotavel)){
                                    //System.out.println("VOTO EM PARTIDO: " + numVotavel + "\n");
                                    eleicao.addVotosPartido(numVotavel, votos);
                                }
                            }
                        }
                    }
                }
            }
        }
        catch(NumberFormatException nfe){
            System.out.println("Numero processo em formato inválido");
        }
        catch(Exception e){
            e.printStackTrace();
        }

        eleicao.getRelatorio();
        //eleicao.printPartidos();
    }
}
