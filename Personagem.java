package projeto_de_cria;
public class Personagem {
    private int     STR, DEX, INT, FTH, CHA, HP, MP, AGI, E, EXA, XP, LV, Moedas;
    private String  Nome;
    public int x;

/*
    STR:    Força
    DEX:    Destreza
    INT:    Inteligência
    FTH:    Fé
    CHA:    Carisma
    HP:     Saúde
    MP:     Conhecimento
    AGI:    Agilidade
    E:      Energia
    EXA:    Exaustão
    XP:     Experiência
    LV:     Nível
    Moedas: Dinheiro
*/

    public String getNome() {return Nome;}  ;        public void setNome  (String Nome) {this.Nome = Nome;}
    public int getSTR()     {return STR;}   ;        public void setSTR   (int STR)     {this.STR = STR;}
    public int getDEX()     {return DEX;}   ;        public void setDEX   (int DEX)     {this.DEX = DEX;}
    public int getINT()     {return INT;}   ;        public void setINT   (int INT)     {this.INT = INT;}
    public int getFTH()     {return FTH;}   ;        public void setFTH   (int FTH)     {this.FTH = FTH;}
    public int getCHA()     {return CHA;}   ;        public void setCHA   (int CHA)     {this.CHA = CHA;}
    public int getHP()      {return HP;}    ;        public void setHP    (int HP)      {this.HP = HP;}
    public int getMP()      {return MP;}    ;        public void setMP    (int MP)      {this.MP = MP;}
    public int getAGI()     {return AGI;}   ;        public void setAGI   (int AGI)     {this.AGI = AGI;}
    public int getE()       {return E;}     ;        public void setE     (int E)       {this.E = E;}
    public int getEXA()     {return EXA;}   ;        public void setEXA   (int EXA)     {this.EXA = EXA;}
    public int getXP()      {return XP;}    ;        public void setXP    (int XP)      {this.XP = XP;}
    public int getLV()      {return LV;}    ;        public void setLV    (int LV)      {this.LV = LV;}
    public int getMoedas()  {return Moedas;};        public void setMoedas(int Moedas)  {this.Moedas = Moedas;}
    
    public Personagem (String nome , int STR, int DEX, int INT, int FTH, int CHA, int HP, int MP, int AGI, int E, int XP, int lv, int Moedas) {
        this.Nome   = nome;     //              linha 1
        this.STR    = STR;      //1 (Padrões)   linha 2
        this.DEX    = DEX;      //1             linha 3
        this.INT    = INT;      //1             linha 4
        this.FTH    = FTH;      //1             linha 5
        this.CHA    = CHA;      //1             linha 6
        this.HP     = HP;       //1 (10)        linha 7
        this.MP     = MP;       //1 (10)        linha 8
        this.AGI    = AGI;      //1             linha 9
        this.E      = E;        //1             linha 10
        //EXAUSTÃO EXA          //0             linha 11
        this.XP     = XP;       //0             linha 12
        this.LV     = lv;       //1             linha 13
        this.Moedas = Moedas;   //0             linha 14
    }
}
