public class Agenda {
    private int codigo;
    private String nome;
    private String tel;
    
    public Agenda (int codigo, String nome, String tel){
        this.codigo = codigo;
        this.nome = nome;
        this.tel = tel;
    }
    public int getCodigo(){
        return this.codigo;
    }
    public String getNome(){
        return this.nome;
    }
    public String getTel (){
        return this.tel;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
    public String toString() {
        return this.codigo+ "\t"+this.nome + "\t" +this.tel+"\n";
    }
}

