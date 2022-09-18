package projeto_de_cria;
public class Item {
    private String  nome, descricao;
    private int     tipo, modificador, raridade;

    public String getNome()         {return nome;}           ; public void setNome(String nome)                 {this.nome = nome;}
    public String getDescricao()    {return descricao;}      ; public void setDescricao(String descricao)       {this.descricao = descricao;}
    public int getTipo()            {return tipo;}           ; public void setTipo(int tipo)                    {this.tipo = tipo;}
    public int getModificador()     {return modificador;}    ; public void setModificador(int modificador)      {this.modificador = modificador;}
    public int getRaridade()        {return raridade;}       ; public void setRaridade(int raridade)            {this.raridade = raridade;}

    public Item(String nome, String descricao, int tipo, int modificador, int raridade) {
        this.nome           = nome          ;
        this.descricao      = descricao     ;
        this.tipo           = tipo          ;
        this.modificador    = modificador   ;
        this.raridade       = raridade      ;
    }
}

/*
nome do item                        - linha 1
tipo (ofensivo/defensivo)           - linha 2   *OBS*
modificador (dano/defesa)           - linha 3
raridade (1-5)                      - linha 4
descrição (o resto do arquivo)      - linha 5 em diante

*OBS*:  1: capacete                 defesa
        2: peitoral                 defesa
        3: manopla                  defesa
        4: calça                    defesa
        5: bota                     defesa
        6: jóia                     defesa 
        7: escudo                   defesa
        10: armas STR/DEX           ataque
        11: armas INT               ataque
        12: armas FTH               ataque
        13: armas CHA               ataque
        14: armas 2 mãos STR        ataque
        15: armas à distância DEX   ataque
        16: outros                  neutro (modificador = 0)
*/
