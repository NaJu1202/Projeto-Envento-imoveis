public class Cliente {
    private int id;
    private String nome;
    private int telefone;
    private int codigoImovel;

    public Cliente() {
        this.id = 0;
        this.nome = "";
        this.telefone = 0;
        this.codigoImovel = 0;
    }

    public Cliente(int id, String nome, int telefone, String email, int codigoImovel, int cep) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.codigoImovel = codigoImovel;
    }

    // gets
    public int getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public int getTelefone() {
        return this.telefone;
    }

    public int getCodigoImovel() {
        return this.codigoImovel;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

    public void setCodigoImovel(int codigoImovel) {
        this.codigoImovel = codigoImovel;
    }

    public String toString() {
        return this.id + "\t" + this.nome + "\t" + this.telefone + "\t" + this.codigoImovel;
    }
}
