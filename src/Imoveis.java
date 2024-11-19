public class Imoveis {
    private int codigo;
    private String cidade;
    private String uf;
    private String tipoImovel;

    public Imoveis() {
        this.codigo = 0;
        this.cidade = "";
        this.uf = "";
        this.tipoImovel = "";
    }

    public Imoveis(int id, String cidade, String uf, String tipoImovel) {
        this.codigo = id;
        this.cidade = cidade;
        this.uf = uf;
        this.tipoImovel = tipoImovel;
    }

    public int getCodigo() {
        return this.codigo;
    }

    public void setCodigo(int id) {
        this.codigo = id;
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

    public String getTipoImovel() {
        return this.tipoImovel;
    }

    public void setTipoImovel(String tipoImovel) {
        this.tipoImovel = tipoImovel;
    }

    public String toString() {
        return codigo + "\t" + cidade + "\t" + uf + "\t" + tipoImovel;
    }
}
