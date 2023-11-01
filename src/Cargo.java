public enum Cargo {
    FEDERAL("6"),
    ESTADUAL("7"),
    ERROR("0");

    private String tipo;

    private Cargo(String tipoCargo){
        this.tipo = tipoCargo;
    }

    public String getTipo() {
        return tipo;
    }

    public static Cargo getCargo(String linha){
        for(Cargo c : Cargo.values()){
            if(c.tipo.equals(linha)){
                return c;
            }
        }
        return ERROR;
    }
}
