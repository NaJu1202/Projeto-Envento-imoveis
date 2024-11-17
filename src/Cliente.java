public class Cliente {
    private int id;
    private String nome;
    private int telefone;
    private String email;
    private int cpf;
    private int cep;

    public Cliente() {
        this.id = 0;
        this.nome = "";
        this.telefone = 0;
        this.email = "";
        this.cpf = 0;
        this.cep = 0;
    }

    public Cliente(int id, String nome, int telefone, String email, int cpf, int cep) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.cpf = cpf;
        this.cep = cep;
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

    public String getEmail() {
        return this.email;
    }

    public int getCpf() {
        return this.cpf;
    }

    public int getCep() {
        return this.cep;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCpf(int cpf) {
        this.cpf = cpf;
    }

    public void setCep(int cep) {
        this.cep = cep;
    }

    @Override
    public String toString() {
        return "Cliente [id=" + id + ", nome=" + nome + ", telefone=" + telefone + ", email=" + email + ", cpf=" + cpf
                + ", cep=" + cep + "]";
    }
}
