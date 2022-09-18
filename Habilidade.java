package projeto_de_cria;
public class Habilidade {
    String nome, descricao;
    int incremento, quantIncremento, custo;

    public String getNome()         {return nome;}              ; public void setNome(String nome)                    {this.nome = nome;}
    public String getDescricao()    {return descricao;}         ; public void setDescricao(String descricao)          {this.descricao = descricao;}
    public int getIncremento()      {return incremento;}        ; public void setIncremento(int incremento)           {this.incremento = incremento;}
    public int getQuantIncremento() {return quantIncremento;}   ; public void setQuantIncremento(int quantIncremento) {this.quantIncremento = quantIncremento;}
    public int getCusto()           {return custo;}             ; public void setCusto(int custo)                     {this.custo = custo;}

    public Habilidade(String nome, String descricao, int incremento, int quantIncremento, int custo) {
        this.nome               = nome;
        this.descricao          = descricao;
        this.incremento         = incremento;
        this.quantIncremento    = quantIncremento;
        this.custo              = custo;
    }
    
}
/*
Incremento:
    1. Dano         (decrementar hp do alvo-[String "nome"])
    2. Cura         (incrementa pontos de vida do alvo-[String "nome"])
    3. Buff         (incrementa pontos no atributo de um alvo-[String "nome"])
    4. Debuff       (decrementa pontos no atributo de um alvo-[String "nome"])
    
(nome, incremento(local onde será incrementado), quantIncremento, custo (Quantidade de MP que será descontado), descricao)
*/
