package projeto_de_cria;
public class Monstro {
    private int HP, AGI, XP, dano;
    private String nome;

    public int getHP()      {return HP;}    ; public void setHP(int HP)         {this.HP = HP;}
    public int getAGI()     {return AGI;}   ; public void setAGI(int AGI)       {this.AGI = AGI;}
    public int getXP()      {return XP;}    ; public void setXP(int XP)         {this.XP = XP;}
    public int getDano()    {return dano;}  ; public void setDano(int dano)     {this.dano = dano;}
    public String getNome() {return nome;}  ; public void setNome(String nome)  {this.nome = nome;}

    public Monstro(String nome, int HP, int AGI, int XP, int dano) {
        this.HP     = HP    ;
        this.AGI    = AGI   ;
        this.XP     = XP    ;
        this.dano   = dano  ;
        this.nome   = nome  ;
    }
}
