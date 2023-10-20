public enum Genero {
    FEMININO("4"), 
    MASCULINO("2"),
    ERROR("0");

    private String codigoGenero;

    private Genero(String gen){
        this.codigoGenero = gen;
    }

    public String getCodigoGenero() {
        return codigoGenero;
    }

    public static Genero getGenero(String linha){
        for(Genero g : Genero.values()){
            if(g.codigoGenero.equals(linha)){
                return g;
            }
        }
        return ERROR;
    }
}
