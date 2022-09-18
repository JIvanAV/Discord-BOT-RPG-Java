package projeto_de_cria;
public class PrioridadeAGI implements Comparable<PrioridadeAGI> {
    private final String Nome;
    private int agi;

    public PrioridadeAGI(String Nome, int agi) {
        this.Nome = Nome;
        this.agi = agi;
    }

    public int getAgi() {return agi;}
    
    @Override
    public String toString() {return Nome;}
    
    @Override public int compareTo(PrioridadeAGI outroAluno) {
        if (this.agi > outroAluno.getAgi()) { return -1; }
        if (this.agi < outroAluno.getAgi()) { return 1 ; }
        return 0; 
    }
}
