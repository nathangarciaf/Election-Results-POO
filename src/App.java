import java.io.FileInputStream;
import java.util.HashSet;
/*import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;*/
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

public class App {
    public static void main(String[] args) {
        boolean headerVerify=true;
        Set<Candidato> candidatos = new HashSet<Candidato>();
        Set<Partido> partidos = new HashSet<Partido>();

        try (FileInputStream fin = new FileInputStream("teste.csv");
            Scanner s = new Scanner(fin, "ISO-8859-1")) {     
            Vector<Integer> headerAtributteVector = new Vector<Integer>();       
            while (s.hasNextLine()) {
                String line = s.nextLine();
                String lineFormat = line.replaceAll("\"", "");
                if(headerVerify != false){
                    String header = lineFormat;
                    try(Scanner headerScanner = new Scanner(header);){
                        headerScanner.useDelimiter(";");
                        boolean vecAdd = false;
                        int idxAtributte = 0;
                        int idxVec = 0;

                        while(headerScanner.hasNextLine()){
                            String headerAtributte = headerScanner.next();

                            if(headerAtributte.equals("CD_CARGO")){
                                vecAdd=true;
                            }
                            else if(headerAtributte.equals("CD_SITUACAO_CANDIDADO_TOT")){
                                vecAdd=true;
                            }
                            else if(headerAtributte.equals("NR_CANDIDATO")){
                                vecAdd=true;
                            }
                            else if(headerAtributte.equals("NM_URNA_CANDIDATO")){
                                vecAdd=true;
                            }
                            else if(headerAtributte.equals("NR_PARTIDO")){
                                vecAdd=true;
                            }
                            else if(headerAtributte.equals("SG_PARTIDO")){
                                vecAdd=true;
                            }
                            else if(headerAtributte.equals("DT_NASCIMENTO:")){
                                vecAdd=true;
                            }
                            else if(headerAtributte.equals("CD_SIT_TOT_TURNO")){
                                vecAdd=true;
                            }
                            else if(headerAtributte.equals("CD_GENERO")){
                                vecAdd=true;
                            }
                            else if(headerAtributte.equals("NM_TIPO_DESTINACAO_VOTOS")){
                                vecAdd=true;
                            }

                            if(vecAdd != false){
                                System.out.println(headerAtributte);
                                System.out.println();
                                headerAtributteVector.add(idxVec, idxAtributte);
                                vecAdd=false;
                                idxVec++;
                            }
                            idxAtributte++;
                        }
                    }
                    headerVerify=false;
                    continue;
                }
                
                System.out.println("GG");
            }
            System.out.println(headerAtributteVector);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*Date d = new Date();
        Locale brLocale = Locale.forLanguageTag("pt-BR");
		DateFormat data = DateFormat.getDateInstance(DateFormat.SHORT, brLocale);
        String dataFormat = data.format(d);

        Cargo c = Cargo.getCargo("7");

        Candidato jorge = new Candidato("Jorge Aragao Fonseca", "Didi",1313,c,dataFormat);

        System.out.println(jorge);*/
    }
}
