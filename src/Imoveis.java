public class Imoveis {
    private int id;
    private String rua;
    private int numero;
    private String bairro;
    private String cidade;
    private String uf;
    private int cep;

    public Imoveis() {
        this.id = 0;
        this.rua = "";
        this.numero = 0;
        this.bairro = "";
        this.cidade = "";
        this.uf = "";
        this.cep = 0;
    }

    public Imoveis(int id, String rua, int numero, String bairro, String cidade, String uf, int cep) {
        this.id = id;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.cep = cep;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRua() {
        return this.rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public int getNumero() {
        return this.numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return this.bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return this.cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return this.uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public int getCep() {
        return this.cep;
    }

    public void setCep(int cep) {
        this.cep = cep;
    }
}
