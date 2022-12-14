package projeto_de_cria ; //Total de horas gastas nesse código: 78

import java.awt.HeadlessException                                       ;
import java.io.BufferedReader                                           ;
import java.io.BufferedWriter                                           ;
import java.io.File                                                     ;
import java.io.FileNotFoundException                                    ;
import java.io.FileReader                                               ;
import java.io.FileWriter                                               ;
import java.io.IOException                                              ;
import java.util.ArrayList                                              ;
import java.util.Collections                                            ;
import java.util.Random                                                 ;
import java.util.Scanner                                                ;
import static java.lang.Integer.parseInt                                ;
import java.util.List                                                   ;
import java.util.logging.Level                                          ;
import java.util.logging.Logger                                         ;
import javax.security.auth.login.LoginException                         ;
import net.dv8tion.jda.api.*                                            ;
import net.dv8tion.jda.api.entities.Activity                            ;
import net.dv8tion.jda.api.entities.Message                             ;
import net.dv8tion.jda.api.entities.User                                ;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion  ;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent          ;
import net.dv8tion.jda.api.hooks.ListenerAdapter                        ;
import org.jetbrains.annotations.NotNull                                ;

public class Projeto_de_Cria extends ListenerAdapter{
    public static final String  CAMINHOJOGADORES   = ""; //PATH OF PLAYER FILES
    public static final String  CAMINHOMONSTROS    = ""; //PATH OF MOBS FILES
    public static final String  CAMINHOITENS       = ""; //PATH OF ITEN FILES
    public static final String  CAMINHOHABILIDADES = ""; //PATH OF SKILL FILES
    public static final String  TOKEN              = ""; //BOT DISCORD TOKEN
    public static       int     action             = 0                                                                       ;
    public static       int     vez                = 0                                                                       ;
    public static       int     alvo               = 0                                                                       ;
    public static       boolean emCombate          = false                                                                   ;
    public static List<PrioridadeAGI> OrdemATKj    = new ArrayList<>()                                                       ;
    
    public static int lançarDado              (int lados){
        Random gerador = new Random();

        return (1 + gerador.nextInt(lados));
    }
    
    public static void criaFichaPersonagem    (String nome, String nomeItem, String nomeHab) throws IOException{
        Personagem jogador   = new Personagem(nome,1,1,1,1,1,1,1,1,1,0,1,0);
        File arquivo         = new File(CAMINHOJOGADORES + "\\" + nome + ".txt");
        try{ if (arquivo.createNewFile()){
                try {
                    try (
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        FileWriter escritor = new FileWriter(CAMINHOJOGADORES + "\\" + nome + ".txt")) {
                        escritor.write(""   + jogador.getNome())                                        ; //linha 1
                        escritor.write("\n" + jogador.getSTR())                                         ; //linha 2
                        escritor.write("\n" + jogador.getDEX())                                         ; //linha 3
                        escritor.write("\n" + jogador.getINT())                                         ; //linha 4
                        escritor.write("\n" + jogador.getFTH())                                         ; //linha 5
                        escritor.write("\n" + jogador.getCHA())                                         ; //linha 6
                        escritor.write("\n" + (jogador.getHP() * 10) + "/" + (jogador.getHP() * 10))    ; //linha 7
                        escritor.write("\n" + (jogador.getMP() * 10) + "/" + (jogador.getMP() * 10))    ; //linha 8
                        escritor.write("\n" + jogador.getAGI())                                         ; //linha 9
                        escritor.write("\n" + jogador.getE())                                           ; //linha 10
                        escritor.write("\n" + jogador.getEXA())                                         ; //linha 11
                        escritor.write("\n" + jogador.getXP())                                          ; //linha 12
                        escritor.write("\n" + jogador.getLV())                                          ; //linha 13
                        escritor.write("\n" + jogador.getMoedas())                                      ; //linha 14
                        
                        // Espaço para os itens e habilidades:
                        int quantE  =   ((jogador.getE()    * 10)/2);
                        int quantMP =   ((jogador.getMP()   * 10)/2);
                        int cont    =   0;
                        
                        while (cont != quantE){ //itens
                            if (cont == 0){escritor.write("\n*" + nomeItem);}
                            else{escritor.write("\n*");}
                            cont++;
                        }
                        
                        cont = 0;
                        while (cont != quantMP){ //habilidades
                            if (cont == 0)  {escritor.write("\n#" + nomeHab);}
                            else            {escritor.write("\n#");}
                            cont++;
                        }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    }
                }   catch (IOException e){{}}
            }       else  {}
        }           catch (HeadlessException | IOException e) {}        
    }
    
    public static void criaItem               (String caminho, String nome, String descricao, int tipo, int modificador, int raridade){
        Item item       = new Item(nome, descricao, tipo, modificador, raridade);
        File arquivo    = new File(caminho + "\\" + nome + ".txt");
        
        try{ if (arquivo.createNewFile()){
                try {
                    try (
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        FileWriter escritor = new FileWriter(caminho + "\\" + nome + ".txt")) {
                        escritor.write(""   + item.getNome())        ; //linha 1
                        escritor.write("\n" + item.getTipo())        ; //linha 2
                        escritor.write("\n" + item.getModificador()) ; //linha 3
                        escritor.write("\n" + item.getRaridade())    ; //linha 4
                        escritor.write("\n" + item.getDescricao())   ; //linha 5
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    }
                }   catch (IOException e){} //erro ao escrever
            }       else  {} //arquivo já existe
        }           catch (HeadlessException | IOException e) {} //erro ao criar o arquivo        
    }
    
    public static void criaMonstro            (String caminho, String nome, int hp, int agi, int xp, int dano){
        Monstro monstro   = new Monstro(nome, hp, agi, xp, dano);
        File arquivo      = new File(caminho + "\\" + nome + ".txt");
        
        try{ if (arquivo.createNewFile()){
                try {
                    try (
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        FileWriter escritor = new FileWriter(caminho + "\\" + nome + ".txt")) {
                        escritor.write(""   + monstro.getNome())                                    ;   //linha 1
                        escritor.write("\n" + monstro.getHP() * 10 + "/" + monstro.getHP() * 10)    ;   //linha 2
                        escritor.write("\n" + monstro.getAGI())                                     ;   //linha 3
                        escritor.write("\n" + monstro.getXP())                                      ;   //linha 4
                        escritor.write("\n" + monstro.getDano())                                    ;   //linha 5
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    }
                }   catch (IOException e){} //erro ao escrever no arquivo
            }       else  {} //arquivo ja existe
        }           catch (HeadlessException | IOException e) {}//erro ao criar o arquivo        
    }
    
    public static void criaHabilidade         (String caminho, String nome, int incremento, int QI, int custo, String descricao){
        Habilidade h1       = new Habilidade(nome, descricao, incremento, QI, custo);
        File arquivo        = new File(caminho + "\\" + nome + ".txt");
        
        try{if (arquivo.createNewFile()){
                try {
                    try (
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        FileWriter escritor = new FileWriter(caminho + "\\" + nome + ".txt")) {
                        escritor.write(""   + h1.getNome())                 ; //linha 1
                        escritor.write("\n" + h1.getCusto())                ; //linha 2
                        escritor.write("\n" + h1.getIncremento())           ; //linha 3
                        escritor.write("\n" + h1.getQuantIncremento())      ; //linha 4
                        escritor.write("\n" + h1.getDescricao())            ; //linha 5
                    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                }   catch (IOException e){}
            }       else  {}
        }           catch (HeadlessException | IOException e) {}        
    }
    
    public static void arih                   (boolean verificador, String caminho, String nomeJogador, String nomeIH, boolean IouH) throws FileNotFoundException, IOException{
        //Adicionar ou remover item/habilidade da ficha do jogador (arquivo.txt):
        char simboloItem = '*' , simboloHab = '#';
        
        File file       = new File (caminho + "\\" + nomeJogador  + ".txt");
        Scanner scan2;
        Scanner scan3;
        Scanner scan4;
        
        if (file.exists() == false){}
        
        else {
            int QI, QH;

            int i;
            try (Scanner scan = new Scanner(file)) {
                scan2 = new Scanner(file);
                scan3 = new Scanner(file);
                scan4 = new Scanner(file);

                QI = 0;
                QH = 0;
                i = 0;
                while (scan.hasNextLine()){
                    scan.nextLine();
                    i++;
                }
                i = i - 14 ;
            }
            String arrayIH[] = new String [i];                      //array vazio com a quantidade de itens/habilidade
            i = 0;

            while (scan2.hasNextLine()){
                char[] x = scan2.nextLine().toCharArray();
                if(scan2.hasNextLine() == false){break;}
                if (x[0] ==  simboloHab || x[0] == simboloItem){
                    if (x[0] ==  simboloHab)    {QH++;}
                    if (x[0] ==  simboloItem)   {QI++;}

                    arrayIH[i] = String.valueOf(x);                 //o array vai receber todos os itens e habilidades
                    i++;
                }
        }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Verificar se a quantidade de Itens e Habilidades é condizente com o calculo (E * 10)/2 ou (MP * 10)/2
        String STRE = "0", STRMP = "0";
        
        i = 0 ;
        while (scan3.hasNextLine()){
                STRE = scan3.nextLine();
                i++;
                if (i == 10){break;}
            }
        
        i = 0;
        while (scan4.hasNextLine()){
                STRMP = scan4.nextLine();
                i++;
                if (i == 8){break;}
            }
        
        char mpArray[]  = STRMP.toCharArray()   ;
        STRMP           = "";
        
        i = 0;
        while ( i != mpArray.length){
                if (mpArray[i] == '/'){break;}
                STRMP += mpArray[i];
                i++;
            }
        
        ArrayList<String> arrayIH2 = new ArrayList();
        
        i = 0;
        while (i < arrayIH.length){
            arrayIH2.add(arrayIH[i]);
            i++;
        }
        
        i = 0;
        while((parseInt(STRE) * 10)/2 > QI){
            arrayIH2.add("*");
            i++;
            QI++;
        }
        
        while((parseInt(STRMP))/2 > QH){
            arrayIH2.add("#");
            i++;
            QH++;
        }
    
        
        i = 0;
        while (i < arrayIH2.size()){
            if(arrayIH2.get(i) == null){arrayIH2.remove(i);}
            i++;
        }
        
        
    Collections.sort(arrayIH2);
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        if (verificador == true)   { //adicionar     <===
            i = 0;
            boolean espaco = false;
            
            if (IouH == true){ // item
                while (i != arrayIH2.size()){
                    if ("*".equals(arrayIH2.get(i))){
                        arrayIH2.remove (i);
                        arrayIH2.add    ("*" + nomeIH);
                        espaco =        true;
                        break;
                    }
                    i++;
                }
            }
            
            else { //habilidade
                while (i != arrayIH2.size()){
                    if ("#".equals(arrayIH2.get(i))){
                        arrayIH2.remove (i);
                        arrayIH2.add    ("#" + nomeIH);
                        espaco =        true;
                        break;
                    }
                    i++;
                }
            }
            
            Collections.sort(arrayIH2);
            if (espaco == false){}
        }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////            
        else if (verificador == false)  { //deletar     <===
            i = 0;
            if (IouH == true){ //item
                while (i != arrayIH2.size()){
                    if (("*"+nomeIH).equals(arrayIH2.get(i))){
                        arrayIH2.remove (i);
                        arrayIH2.add    ("*");
                        break;
                    }
                    i++;
                }
            } 

            else { //habilidade
                while (i != arrayIH2.size()){
                    if (("#"+nomeIH).equals(arrayIH2.get(i))){
                        arrayIH2.remove (i);
                        arrayIH2.add    ("#");
                        break;
                    }
                    i++;
                }
            }
            Collections.sort(arrayIH2);
        }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    Collections.reverse(arrayIH2);
    ArrayList<String> salvar;
    
        try (FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr)) {
                    salvar = new ArrayList(); //ArrayList que recebe todos os valores
                    String linha = br.readLine();
                    i = 0;
                    while (i != 14){
                        salvar.add(linha);
                        i++;
                        linha = br.readLine();
                    }       i = 0;
                    while (i != arrayIH2.size()){
                        salvar.add(arrayIH2.get(i));
                        i++;
                    }
                }
    
        try (FileWriter fw = new FileWriter     (file);
                BufferedWriter bw = new BufferedWriter (fw)) {
            
                i = 0;
                while (i != salvar.size()){
                    bw.write(salvar.get(i));
                    bw.newLine();
                    i++;
                }
        }
    }
    }
    
    public static String procuraModificaLinha (int Qlinha, String caminho, String nome, String valor) throws FileNotFoundException, IOException{        
        File file       = new File (caminho + "\\" + nome  + ".txt");   //Procura uma linha no arquivo e a retorna:
        Qlinha--;
        
        FileReader fr               = new FileReader(file);
        BufferedReader br           = new BufferedReader(fr);
        ArrayList<String> salvar    = new ArrayList();
        String linha                = br.readLine();
        Scanner scan                = new Scanner(file);
        
        while (scan.hasNextLine()){
            salvar.add          (linha);
            linha = br.readLine ();
            scan.nextLine       ();
        }
        
        int i = 0 ; i++; i--;
        
        if (valor != null){salvar.set(Qlinha, valor);}
        
        try (   FileWriter      fw  = new FileWriter     (file);
                BufferedWriter  bw  = new BufferedWriter (fw)) {
                    
            i = 0;

            while (i != salvar.size()){
                bw.write    (salvar.get(i));
                bw.newLine  ();
                i++;
            }
        }
        
        return salvar.get(Qlinha);
    }
    
    public static boolean verificaHP          (String nome) throws IOException{
        boolean jogador = true;
        
        File arquivo = new File(CAMINHOJOGADORES + "\\" + nome + ".txt");
        File arquivo2 = new File(CAMINHOMONSTROS + "\\" + nome + ".txt"); 
        
        if (arquivo.exists()){jogador = true;}
        if (arquivo2.exists()){jogador = false;}
        
        
        //Se retornar falso, está morto
        int i             = 0;
        String STRhp      = "0";
        
        if (jogador == true){ //jogador
            STRhp             =  procuraModificaLinha   (8, CAMINHOJOGADORES, nome, null);
            char hpArray[]    = STRhp.toCharArray()   ;
            STRhp             = "";
            
            while ( i != hpArray.length){
                if (hpArray[i] == '/'){break;}
                STRhp += hpArray[i];
                i++;
            }
        }
        
        else { //monstro
            STRhp             =  procuraModificaLinha   (2, CAMINHOMONSTROS, nome, null);
            char hpArray[]    = STRhp.toCharArray()   ;
            STRhp             = "";
            
            while ( i != hpArray.length){
                if (hpArray[i] == '/'){break;}
                STRhp += hpArray[i];
                i++;
            }
        }
        
        int hp          = Integer.parseInt(STRhp);
        if (hp <= 0)    {return false;}
        
        return true;
    }
    
    public static boolean verificaXP          (String nomeJogador) throws IOException{
        //se retornar falso, tem pontos de nível para add
        String STRxp = procuraModificaLinha   (12, CAMINHOJOGADORES, nomeJogador, null);
        String STRlv = procuraModificaLinha   (13, CAMINHOJOGADORES, nomeJogador, null);
        
        int xp          = Integer.parseInt(STRxp);
        int lv          = Integer.parseInt(STRlv);
        
        if (xp >= lv * 10) {xp = xp - lv*10 ; lv++;
                            procuraModificaLinha  (12, CAMINHOJOGADORES, nomeJogador, "" + xp);
                            procuraModificaLinha  (13, CAMINHOJOGADORES, nomeJogador, "" + lv);
                            return false;}
        
        return true;
    }
    
    public static boolean jogMon              (String nome){
        //true - jogador
        File arquivo = new File(CAMINHOJOGADORES + "\\" + nome + ".txt");
                
        if (arquivo.exists()) {return true;}
        
        File arquivo2 = new File(CAMINHOMONSTROS + "\\" + nome + ".txt");
        
        if (arquivo2.exists()) {return false;}
                
        return false;
    }
    
    @Override
    public void onMessageReceived             (@NotNull MessageReceivedEvent event){
        Message msg                 = event .getMessage()        ;
        String msgCD                = msg   .getContentDisplay() ; //Contexto STR da mensagem
        User autor                  = event .getAuthor()         ; 
        String nomeAutor            = autor .getName()           ;
        boolean bot                 = autor .isBot()             ;
        MessageChannelUnion canal   = event .getChannel()        ;
        if (bot == true)            {} //                        ;
        else{char msgArray[]     = msgCD.toCharArray()           ;
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Mostra os comandos (Ajuda)
            if ("+Ajuda".equals(msgCD)){canal.sendMessage("""
                                                          Meus comandos: 
                                                          :radio_button:  **+1d**<Número>
                                                          :radio_button:  **+CriaFicha** <Nome>
                                                          :radio_button:  **+VisuFicha** <Nome>
                                                          :radio_button:  **+AddStatus** <Nome>/<Status>
                                                          :radio_button:  **+CriaItem** <Nome>/<Tipo>/<Modificador>/<Raridade>/<Descricao>
                                                          :radio_button:  **+VisuItem** <Nome>
                                                          :radio_button:  **+DarItem** <Nome>/<NomeJogador>
                                                          :radio_button:  **+RemoveItem** <NomeJogador>/<NomeItem>
                                                          :radio_button: **+CriaHab** <Nome>/<Tipo>/<Modificador>/<Custo>/<Descrição>
                                                          :radio_button: **+VisuHab** <Nome>
                                                          :radio_button: **+DarHab** <NomeJogador>/<NomeHab>
                                                          :radio_button: **+RemoveHab** <NomeJogador>/<NomeHab>
                                                          :radio_button: **+CriaMonstro** <Nome>/<HP>/<AGI>/<XP>/<Dano>
                                                          :radio_button: **+Batalha** <J1>,<J2>,...<Jn>/<M1>,<M2>,...<Mn>
                                                          :radio_button: **+Ação** (Necessário estar em batalha)
                                                          :radio_button: **+ResetaHP** <Nome>

                                                          OBS: O <> é ilustrativo.
                                                          """).queue();}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Rola um dado
            else if (msgArray[0] == '+' && msgArray[1] == '1' && msgArray[2] == 'd'){
                String dado = "";
                
                int contador = 0;
                while (contador != msgArray.length){
                    if (msgArray[contador] == '+' || msgArray[contador] == '1' || msgArray[contador] == 'd'|| msgArray[contador] == ' '){}
                    
                    else {dado = dado + msgArray[contador];}
                    
                    contador++;
                }
                int dadoInt = lançarDado(Integer.parseInt(dado));
                
                canal.sendMessage(":game_die:: "+dadoInt).queue();
            }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Cria uma ficha
            else if (msgArray[0] == '+' && msgArray[1] == 'C' && msgArray[2] == 'r' && msgArray[3] == 'i' && msgArray[4] == 'a' && msgArray[5] == 'F' && msgArray[6] == 'i' && msgArray[7] == 'c' && msgArray[8] == 'h' && msgArray[9] == 'a' && msgArray[10] == ' '){
                String nomeJogador = "";
                
                int contador = 0;
                while (contador != msgArray.length){
                    if (contador <= 10){}
                    else {nomeJogador = nomeJogador + msgArray[contador];}
                    
                    contador ++;
                }
                
                try {criaFichaPersonagem(nomeJogador,"Espada Comum", "Ataque Basico"); canal.sendMessage("Ficha Criada :white_check_mark:").queue();}
                catch (IOException ex) {Logger.getLogger(Projeto_de_Cria.class.getName()).log(Level.SEVERE, null, ex);}
                
                try {procuraModificaLinha(1, CAMINHOJOGADORES, nomeJogador, nomeJogador + " | " + nomeAutor);}
                catch (IOException ex) {Logger.getLogger(Projeto_de_Cria.class.getName()).log(Level.SEVERE, null, ex);}
            }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Visu Ficha
            else if (msgArray[0] == '+' && msgArray[1] == 'V' && msgArray[2] == 'i' && msgArray[3] == 's' && msgArray[4] == 'u' && msgArray[5] == 'F' && msgArray[6] == 'i' && msgArray[7] == 'c' && msgArray[8] == 'h' && msgArray[9] == 'a' && msgArray[10] == ' '){
                String nomeJogador = "";
                
                int contador = 0;
                while (contador != msgArray.length){
                    if (contador <= 10){}
                    else {nomeJogador = nomeJogador + msgArray[contador];}
                    contador ++;
                }

                String fichaFormatada = "";

                try { fichaFormatada =
                                  "P. | J.: "           + procuraModificaLinha( 1, CAMINHOJOGADORES, nomeJogador, null) + "\n"
                                + "     " + ":star:"                + "LV: "      + procuraModificaLinha(13, CAMINHOJOGADORES, nomeJogador, null) + "\n"
                                + "     " + ":coin:"                + "Moedas: "  + procuraModificaLinha(14, CAMINHOJOGADORES, nomeJogador, null) + "\n"
                                + "\n"
                                + "Atributos: \n"
                                + "     " + ":muscle:"              + "STR: "     + procuraModificaLinha(2,  CAMINHOJOGADORES, nomeJogador, null) + "\n"
                                + "     " + ":open_hands:"          + "DEX: "     + procuraModificaLinha(3,  CAMINHOJOGADORES, nomeJogador, null) + "\n"
                                + "     " + ":brain:"               + "INT: "     + procuraModificaLinha(4,  CAMINHOJOGADORES, nomeJogador, null) + "\n"
                                + "     " + ":pray:"                + "FTH: "     + procuraModificaLinha(5,  CAMINHOJOGADORES, nomeJogador, null) + "\n"
                                + "     " + ":bust_in_silhouette:"  + "CHA: "     + procuraModificaLinha(6,  CAMINHOJOGADORES, nomeJogador, null) + "\n"
                                + "     " + ":anatomical_heart:"    + "HP:  "     + procuraModificaLinha(7,  CAMINHOJOGADORES, nomeJogador, null) + "\n"
                                + "     " + ":book:"                + "MP:  "     + procuraModificaLinha(8,  CAMINHOJOGADORES, nomeJogador, null) + "\n"
                                + "     " + ":zap:"                 + "AGI: "     + procuraModificaLinha(9,  CAMINHOJOGADORES, nomeJogador, null) + "\n"
                                + "     " + ":leg:"                 + "E:   "     + procuraModificaLinha(10, CAMINHOJOGADORES, nomeJogador, null) + "\n"
                                + "     " + ":lungs:"               + "EXA: "     + procuraModificaLinha(11, CAMINHOJOGADORES, nomeJogador, null) + "\n"
                                + "     " + ":up:"                  + "XP:  "     + procuraModificaLinha(12, CAMINHOJOGADORES, nomeJogador, null) + "\n"
                                + "\n"
                                + ":radio_button:*Itens e #Ações: \n";
                
                        int i           = 0;
                        int e           = Integer.parseInt(procuraModificaLinha(10, CAMINHOJOGADORES, nomeJogador, null));
                        String mp       = procuraModificaLinha(8 , CAMINHOJOGADORES, nomeJogador, null);
                        char mpArray[]  = mp.toCharArray()   ;
                        mp              = "";
                        
                        while ( i != mpArray.length){
                            if (mpArray[i] == '/'){break;}
                            mp += mpArray[i];
                            i++;
                        }
                        
                        int mpI = Integer.parseInt(mp);
                        
                        e   = (e *10)/2;
                        mpI = (mpI)/2;
                        
                        i = 0;
                        int acao = 1;
                        
                        while (i != ((e+mpI) + 15)){
                            
                            if (i < 15){}
                            else {
                                switch (procuraModificaLinha(i, CAMINHOJOGADORES, nomeJogador, null).charAt(0)) {
                                    case '#' -> {
                                        fichaFormatada += "     " + procuraModificaLinha(i, CAMINHOJOGADORES, nomeJogador, null) + "   (" + acao +"ª Ação)"+"\n";
                                        acao++;
                                    }
                                    case '*' -> {
                                        String item =  procuraModificaLinha(i, CAMINHOJOGADORES, nomeJogador, null).substring(1);
                                        if ("".equals(item)){fichaFormatada += "     " + procuraModificaLinha(i, CAMINHOJOGADORES, nomeJogador, null) + "\n";}
                                        else                {fichaFormatada += "     " + procuraModificaLinha(i, CAMINHOJOGADORES, nomeJogador, null) + " :gear:: " + procuraModificaLinha(3, CAMINHOITENS, item, null) + "\n";}
                                    }
                                    default -> fichaFormatada += "     " + procuraModificaLinha(i, CAMINHOJOGADORES, nomeJogador, null) + "\n";
                                }
                            }
                            i++;
                        }
                        
                } catch (IOException ex) {Logger.getLogger(Projeto_de_Cria.class.getName()).log(Level.SEVERE, null, ex);}
                
                canal.sendMessage(fichaFormatada).queue();
            }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Adiciona status
            else if (msgArray[0] == '+' && msgArray[1] == 'A' && msgArray[2] == 'd' && msgArray[3] == 'd' && msgArray[4] == 'S' && msgArray[5] == 't' && msgArray[6] == 'a' && msgArray[7] == 't' && msgArray[8] == 'u' && msgArray[9] == 's' && msgArray[10] == ' '){
               String nome = "";
               String status = "";
               boolean ns = false;
               
                int contador = 0;
                while (contador != msgArray.length){
                    if (contador <= 10){}
                    else {
                        if (ns == false){
                            if (msgArray[contador] == '/') {ns = true;}
                            else {nome = nome + msgArray[contador];}
                            
                        } else {
                            if (msgArray[contador] == '/') {}
                            else {status = status + msgArray[contador];} }
                        }
                    
                    contador++;    
                    }
                
            try {
                if (verificaXP(nome) == false){
                    
                    switch(status){
                        case "STR" -> procuraModificaLinha(2 , CAMINHOJOGADORES, nome, ("" + (Integer.parseInt(procuraModificaLinha(2 , CAMINHOJOGADORES, nome, null)) + 1))) ;
                        case "DEX" -> procuraModificaLinha(3 , CAMINHOJOGADORES, nome, ("" + (Integer.parseInt(procuraModificaLinha(3 , CAMINHOJOGADORES, nome, null)) + 1))) ;
                        case "INT" -> procuraModificaLinha(4 , CAMINHOJOGADORES, nome, ("" + (Integer.parseInt(procuraModificaLinha(4 , CAMINHOJOGADORES, nome, null)) + 1))) ;
                        case "FTH" -> procuraModificaLinha(5 , CAMINHOJOGADORES, nome, ("" + (Integer.parseInt(procuraModificaLinha(5 , CAMINHOJOGADORES, nome, null)) + 1))) ;
                        case "CHA" -> procuraModificaLinha(6 , CAMINHOJOGADORES, nome, ("" + (Integer.parseInt(procuraModificaLinha(6 , CAMINHOJOGADORES, nome, null)) + 1))) ;
                        
                        case "HP" -> { 
                            status = procuraModificaLinha(7 , CAMINHOJOGADORES, nome, null) ;
                            char array[] = status.toCharArray();
                            status = "";
                            
                            int i = 0;
                            while (i != array.length){
                                if (array[i] == '/'){break;}
                                status += array[i];
                                i++;
                            }
                            
                            int statusI = Integer.parseInt(status) + 10;
                            
                            status = "" + statusI + "/" + statusI;
                            
                            procuraModificaLinha(7 , CAMINHOJOGADORES, nome, status) ;
                            
                            status = "HP";
                        }
                        
                        case "MP" -> {
                            status = procuraModificaLinha(8 , CAMINHOJOGADORES, nome, null) ;
                            char array2[] = status.toCharArray();
                            status = "";
                            
                            int j = 0;
                            while (j != array2.length){
                                if (array2[j] == '/'){break;}
                                status += array2[j];
                                j++;
                            }
                            
                            int statusI2 = Integer.parseInt(status) + 10;
                            String statusHP = "" + statusI2 + "/" + statusI2;
                            
                            procuraModificaLinha(8 , CAMINHOJOGADORES, nome, statusHP) ;
                            
                            status = "MP";
                        }
                        
                        case "AGI" -> procuraModificaLinha(9 , CAMINHOJOGADORES, nome, "" + ((Integer.parseInt(procuraModificaLinha(9 , CAMINHOJOGADORES, nome, null)) + 1))) ;
                        case "E"   -> procuraModificaLinha(10, CAMINHOJOGADORES, nome, "" + ((Integer.parseInt(procuraModificaLinha(10, CAMINHOJOGADORES, nome, null)) + 1))) ;
                    }
                    canal.sendMessage("1 ponto adicionado em " + status + " na ficha de " + nome + ":white_check_mark:").queue();
                    
                } else{canal.sendMessage("Não há pontos disponíveis").queue();}
                
            } catch (IOException ex) {Logger.getLogger(Projeto_de_Cria.class.getName()).log(Level.SEVERE, null, ex);}
        }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Cria item
            else if (msgArray[0] == '+' && msgArray[1] == 'C' && msgArray[2] == 'r' && msgArray[3] == 'i' && msgArray[4] == 'a' && msgArray[5] == 'I' && msgArray[6] == 't' && msgArray[7] == 'e' && msgArray[8] == 'm' && msgArray[9] == ' '){
                String nome         = "";
                String descricao    = "";
                String STRtipo      = "";
                String STRmod       = "";
                int tipo            = 0;
                int modificador     = 0;
                int raridade        = 0;
                int categoria       = 0;
                
                int contador = 0;
                while (contador != msgArray.length){
                    if (contador <= 9){}
                    else {
                        if (msgArray[contador] == '/') {categoria++; contador++;}
                            
                        switch (categoria) {
                            case 0 -> nome        = nome + msgArray[contador]                ;
                            case 1 -> STRtipo     = STRtipo + msgArray[contador]                ;
                            case 2 -> STRmod      = STRmod + msgArray[contador]         ;
                            case 3 -> raridade    = Integer.parseInt("" + msgArray[contador]);
                            case 4 -> descricao   = descricao + msgArray[contador]           ;
                            default -> {}
                        }
                    }
                    contador ++;
                }
                tipo        = Integer.parseInt(STRtipo);
                modificador = Integer.parseInt(STRmod);
                
                criaItem (CAMINHOITENS, nome, descricao, tipo, modificador, raridade);
                canal.sendMessage("Item " +nome+" criado :white_check_mark:").queue();
            }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Visu item
            else if (msgArray[0] == '+' && msgArray[1] == 'V' && msgArray[2] == 'i' && msgArray[3] == 's' && msgArray[4] == 'u' && msgArray[5] == 'I' && msgArray[6] == 't' && msgArray[7] == 'e' && msgArray[8] == 'm' && msgArray[9] == ' '){
                String nomeItem = "";
                
                int contador = 0;
                while (contador != msgArray.length){
                    if (contador <= 9){}
                    else {nomeItem = nomeItem + msgArray[contador];}
                    contador ++;
                }
                
                String fichaFormatada = "";
                String tipo = "0";
                String modificador = "";

                try { tipo = procuraModificaLinha( 2, CAMINHOITENS, nomeItem, null);
                    switch(tipo){
                        case("1" ) -> {tipo = "Capacete";                    modificador = "Defesa: "     ;}
                        case("2" ) -> {tipo = "Peitoral";                    modificador = "Defesa: "     ;}
                        case("3" ) -> {tipo = "Manopla";                     modificador = "Defesa: "     ;}
                        case("4" ) -> {tipo = "Perneiras";                   modificador = "Defesa: "     ;}
                        case("5" ) -> {tipo = "Botas";                       modificador = "Defesa: "     ;}
                        case("6" ) -> {tipo = "Joalheria";                   modificador = "Defesa: "     ;}
                        case("7" ) -> {tipo = "Escudo";                      modificador = "Defesa: "     ;}
                        case("8" ) -> {tipo = "Arma convencional (STR/DEX)"; modificador = "Dano: "       ;}
                        case("9" ) -> {tipo = "Arma catalizadora (INT)";     modificador = "Dano: "       ;}
                        case("10") -> {tipo = "Arma religiosa (FTH)";        modificador = "Dano: "       ;}
                        case("11") -> {tipo = "Arma instrumental (CHA)";     modificador = "Dano: "       ;}
                        case("12") -> {tipo = "Arma pesada (FTH)";           modificador = "Dano: "       ;}
                        case("13") -> {tipo = "Arma à longo alcance (DEX)";  modificador = "Dano: "       ;}
                        case("14") -> {tipo = "Outros";                      modificador = "Modificador: ";}}
                } catch (IOException ex) { Logger.getLogger(Projeto_de_Cria.class.getName()).log(Level.SEVERE, null, ex);}

                try { fichaFormatada       += "Nome: "     + procuraModificaLinha( 1, CAMINHOITENS, nomeItem, null)  + "\n"
                      + ":wrench: "         + "Tipo: "      + tipo                                                   + "\n"
                      + ":crossed_swords: " + modificador   + procuraModificaLinha( 3, CAMINHOITENS, nomeItem, null) + "\n"
                      + ":star: "           + "Raridade: "  + procuraModificaLinha( 4, CAMINHOITENS, nomeItem, null) + "\n"
                      + ":scroll: "         + "Descrição: " + procuraModificaLinha( 5, CAMINHOITENS, nomeItem, null) + "\n";
                } catch (IOException e){System.out.println("Erro");}
                
                canal.sendMessage(fichaFormatada).queue();
            }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Dar item
            else if (msgArray[0] == '+' && msgArray[1] == 'D' && msgArray[2] == 'a' && msgArray[3] == 'r' && msgArray[4] == 'I' && msgArray[5] == 't' && msgArray[6] == 'e' && msgArray[7] == 'm' && msgArray[8] == ' '){
                String nomeh         = "";
                String nomej         = "";
                int categoria       = 0;
                
                int contador = 0;
                while (contador != msgArray.length){
                    if (contador <= 8){}
                    else {
                        if (msgArray[contador] == '/') {categoria++; contador++;}
                            
                        switch (categoria) {
                            case 0 -> nomeh = nomeh + msgArray[contador] ;
                            case 1 -> nomej = nomej + msgArray[contador] ;
                            default -> {}
                        }
                    }
                    contador ++;
                }
                File arquivo = null;
                File arquivo2 = null;
                    
                System.out.println(nomeh + " " + nomej);
                
                System.out.println(CAMINHOITENS + "\\" + nomeh + ".txt");
                
                try {
                    arquivo = new File(CAMINHOJOGADORES + "\\" + nomej + ".txt");
                    arquivo2 = new File(CAMINHOITENS + "\\" + nomeh + ".txt");
                }
                catch (Exception e) {canal.sendMessage("Arquivo (Item ou Jogador) não existe(m).").queue();}
                
                if (arquivo.exists() == true && arquivo2.exists() == true ) {
                    
                    try{arih(true,CAMINHOJOGADORES,nomej,nomeh,true);}
                    
                    catch (IOException ex) {Logger.getLogger(Projeto_de_Cria.class.getName()).log(Level.SEVERE, null, ex);}
                    
                    try {
                        int tipo = Integer.parseInt(procuraModificaLinha( 2, CAMINHOITENS, nomeh, null));
                        int mod  = Integer.parseInt(procuraModificaLinha( 3, CAMINHOITENS, nomeh, null));
                        
                        if (tipo > 0 && tipo <=7){
                            String strHP =  procuraModificaLinha( 7, CAMINHOJOGADORES, nomej, null);
                            
                            char charHP[] = strHP.toCharArray();
                            
                            String strHP2 = ""; strHP = "";
                            
                            boolean a = true;
                            
                            int i = 0;
                            while (i < charHP.length){
                                if (charHP[i] == '/'){i++; a = false;}
                                
                                if (a == true){strHP += charHP[i];}
                                
                                else {strHP2 += charHP[i];}
                                i++;
                            }
                            
                            strHP2 = "" + (Integer.parseInt(strHP2) + mod);
                            
                            procuraModificaLinha( 7, CAMINHOJOGADORES, nomej, strHP + "/" + strHP2);
                        }
                    
                    }catch (IOException ex) {Logger.getLogger(Projeto_de_Cria.class.getName()).log(Level.SEVERE, null, ex);}
                    
                    canal.sendMessage("Item " + nomeh + " dada à " + nomej + " :white_check_mark:").queue();}
                
                else{}
            }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Remove Item
            else if (msgArray[0] == '+' && msgArray[1] == 'R' && msgArray[2] == 'e' && msgArray[3] == 'm' && msgArray[4] == 'o' && msgArray[5] == 'v' && msgArray[6] == 'e' && msgArray[7] == 'I' && msgArray[8] == 't' && msgArray[9] == 'e' && msgArray[10] == 'm' && msgArray[11] == ' '){
                String nomeh         = "";
                String nomej         = "";
                int categoria       = 0;
                
                int contador = 0;
                while (contador != msgArray.length){
                    if (contador <= 11){}
                    else {
                        if (msgArray[contador] == '/') {categoria++; contador++;}
                            
                        switch (categoria) {
                            case 0 -> nomej = nomej + msgArray[contador] ;
                            case 1 -> nomeh = nomeh + msgArray[contador] ;
                            default -> {}
                        }
                    }
                    contador ++;
                }
                File arquivo = null;
                File arquivo2 = null;
                
                try {
                    arquivo = new File(CAMINHOJOGADORES + "\\" + nomej + ".txt");
                    arquivo2 = new File(CAMINHOITENS + "\\" + nomeh + ".txt");
                }
                catch (Exception e) {canal.sendMessage("Arquivo (Item ou Jogador) não existe(m).").queue();}
                
                if (arquivo.exists() == true && arquivo2.exists() == true ) {
                    try{arih(false,CAMINHOJOGADORES,nomej,nomeh,true);}
                    catch (IOException ex) {Logger.getLogger(Projeto_de_Cria.class.getName()).log(Level.SEVERE, null, ex);}
                    
                    try {
                        int tipo = Integer.parseInt(procuraModificaLinha( 2, CAMINHOITENS, nomeh, null));
                        int mod  = Integer.parseInt(procuraModificaLinha( 3, CAMINHOITENS, nomeh, null));
                        
                        if (tipo > 0 && tipo <=7){
                            String strHP =  procuraModificaLinha( 7, CAMINHOJOGADORES, nomej, null);
                            
                            char charHP[] = strHP.toCharArray();
                            
                            String strHP2 = ""; strHP = "";
                            
                            boolean a = true;
                            
                            for (int i = 0 ; i < charHP.length; i++){
                                if (charHP[i] == '/'){i++; a = false;}
                                
                                if (a == true){strHP += charHP[i];}
                                
                                else {strHP2 += charHP[i];}
                            }
                            
                            strHP2 = "" + (Integer.parseInt(strHP2) - mod);
                            
                            procuraModificaLinha( 7, CAMINHOJOGADORES, nomej, strHP + "/" + strHP2);
                        }
                    
                    }catch (IOException ex) {Logger.getLogger(Projeto_de_Cria.class.getName()).log(Level.SEVERE, null, ex);}
                    
                    canal.sendMessage("Item " + nomeh + " removido de " + nomej + " :white_check_mark:").queue();}
                
                else{ canal.sendMessage("Arquivo de item não existe").queue(); }
            }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Cria monstro
            else if (msgArray[0] == '+' && msgArray[1] == 'C' && msgArray[2] == 'r' && msgArray[3] == 'i' && msgArray[4] == 'a' && msgArray[5] == 'M' && msgArray[6] == 'o' && msgArray[7] == 'n' && msgArray[8] == 's' && msgArray[9] == 't' && msgArray[10] == 'r' && msgArray[11] == 'o' && msgArray[12] == ' '){
                String nome         = "";
                String STRhp        = "0";
                String STRagi       = "0";
                String STRxp        = "0";
                String STRdano      = "0";
                int categoria       = 0;
                int hp, agi, xp, dano;
                
                int contador = 0;
                while (contador != msgArray.length){
                    if (contador <= 12){}
                    else {
                            if (msgArray[contador] == '/') {categoria++; contador++;}
                            
                        switch (categoria) {
                            case 0 -> {nome    = nome    + msgArray[contador];}
                            case 1 -> STRhp   = STRhp   + msgArray[contador];
                            case 2 -> STRagi  = STRagi  + msgArray[contador];
                            case 3 -> STRxp   = STRxp   + msgArray[contador];
                            case 4 -> STRdano = STRdano + msgArray[contador];
                            default -> {}
                        }
                    }
                    contador ++;
                }
                hp   = Integer.parseInt(STRhp);
                agi  = Integer.parseInt(STRagi);
                xp   = Integer.parseInt(STRxp);
                dano = Integer.parseInt(STRdano);
                
                criaMonstro (CAMINHOMONSTROS, nome, hp, agi, xp, dano);
                canal.sendMessage("Monstro " +nome+" criado" + " :white_check_mark:").queue();
            }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Cria habilidade: EX: +CriaHab Ataque Basico/1/1/1/Se a vioLência não resolveu, é porque foi pouca.
            else if (msgArray[0] == '+' && msgArray[1] == 'C' && msgArray[2] == 'r' && msgArray[3] == 'i' && msgArray[4] == 'a' && msgArray[5] == 'H' && msgArray[6] == 'a' && msgArray[7] == 'b' && msgArray[8] == ' '){
                String nome            = "";
                String descricao       = "";
                String STRincremento   = "0";
                String STRmodificador  = "0";
                String STRcusto        = "0";
                int categoria       = 0;
                int incremento, modificador, custo;
                
                int contador = 0;
                while (contador != msgArray.length){
                    if (contador <= 8){}
                    else {
                            if (msgArray[contador] == '/') {categoria++; contador++;}
                            
                            switch (categoria) {
                                case 0 -> nome           = nome    + msgArray[contador];
                                case 1 -> STRincremento  = STRincremento   + msgArray[contador];
                                case 2 -> STRmodificador = STRmodificador  + msgArray[contador];
                                case 3 -> STRcusto       = STRcusto   + msgArray[contador];
                                case 4 -> descricao      = descricao + msgArray[contador];
                                default -> {}
                        }
                    }
                    contador ++;
                }
                incremento  = Integer.parseInt(STRincremento);
                modificador = Integer.parseInt(STRmodificador);
                custo       = Integer.parseInt(STRcusto);
                
                criaHabilidade (CAMINHOHABILIDADES, nome, incremento, modificador, custo, descricao);
                canal.sendMessage("Habilidade " +nome+" criada :white_check_mark:").queue();
            }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Visu habilidade
            else if (msgArray[0] == '+' && msgArray[1] == 'V' && msgArray[2] == 'i' && msgArray[3] == 's' && msgArray[4] == 'u' && msgArray[5] == 'H' && msgArray[6] == 'a' && msgArray[7] == 'b' && msgArray[8] == ' '){
                String nomeItem = "";
                
                int contador = 0;
                while (contador != msgArray.length){
                    if (contador <= 8){}
                    else {nomeItem = nomeItem + msgArray[contador];}
                    contador ++;
                }

                String fichaFormatada = "";
                String tipo = "0";

                try { tipo = procuraModificaLinha( 3, CAMINHOHABILIDADES, nomeItem, null);
                    switch(tipo){
                        case("1" ) -> {tipo = "Dano";}
                        case("2" ) -> {tipo = "Cura";}
                        case("3" ) -> {tipo = "Buff";}
                        case("4" ) -> {tipo = "Atordoamento";}
                        default -> {tipo = "???";}
                    }
                } catch (IOException ex) { Logger.getLogger(Projeto_de_Cria.class.getName()).log(Level.SEVERE, null, ex);}

                try { fichaFormatada+= "Nome: "      + procuraModificaLinha( 1, CAMINHOHABILIDADES, nomeItem, null)         + "\n"
                    + ":book: "     +  "Custo: "     + procuraModificaLinha( 2, CAMINHOHABILIDADES, nomeItem, null) + " MP" + "\n"
                    + ":wrench: "   +  "Tipo: "      + "Habilidade para causar " + tipo                                     + "\n"   
                    + ":sparkles: " +  tipo +": "    + procuraModificaLinha( 4, CAMINHOHABILIDADES, nomeItem, null)         + "\n"
                    + ":scroll: "   +  "Descrição: " + procuraModificaLinha( 5, CAMINHOHABILIDADES, nomeItem, null)         + "\n";
                } catch (IOException e){System.out.println("Erro");}
                
                canal.sendMessage(fichaFormatada).queue();
            }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Dar habilidade
            else if (msgArray[0] == '+' && msgArray[1] == 'D' && msgArray[2] == 'a' && msgArray[3] == 'r' && msgArray[4] == 'H' && msgArray[5] == 'a' && msgArray[6] == 'b' && msgArray[7] == ' '){
                String nomeh         = "";
                String nomej         = "";
                int categoria       = 0;
                
                int contador = 0;
                while (contador != msgArray.length){
                    if (contador <= 7){}
                    else {
                        if (msgArray[contador] == '/') {categoria++; contador++;}
                            
                        switch (categoria) {
                            case 0 -> nomej = nomej + msgArray[contador] ;
                            case 1 -> nomeh = nomeh + msgArray[contador] ;
                            default -> {}
                        }
                    }
                    contador ++;
                }
                
                File arquivo = null;
                File arquivo2 = null;
                
                try {
                    arquivo = new File(CAMINHOHABILIDADES + "\\" + nomeh + ".txt");
                    arquivo2 = new File(CAMINHOHABILIDADES + "\\" + nomeh + ".txt");
                }
                catch (Exception e) {canal.sendMessage("Arquivo (Habilidade ou Jogador) não existe(m).").queue();}
                
                if (arquivo.exists() == true && arquivo2.exists() == true ) {
                    try{arih(true,CAMINHOJOGADORES,nomej,nomeh,false);}
                    catch (IOException ex) {Logger.getLogger(Projeto_de_Cria.class.getName()).log(Level.SEVERE, null, ex);}
                    
                    canal.sendMessage("Habilidade " + nomeh + " dada à " + nomej + " :white_check_mark:").queue();}
                
                else{ canal.sendMessage("Arquivo de habilidade não existe").queue(); }
            }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Remover habilidade
            else if (msgArray[0] == '+' && msgArray[1] == 'R' && msgArray[2] == 'e' && msgArray[3] == 'm' && msgArray[4] == 'o' && msgArray[5] == 'v' && msgArray[6] == 'e' && msgArray[7] == 'H' && msgArray[8] == 'a'&& msgArray[9] == 'b'&& msgArray[10] == ' '){
                String nomeh         = "";
                String nomej         = "";
                int categoria       = 0;
                
                int contador = 0;
                while (contador != msgArray.length){
                    if (contador <= 10){}
                    else {
                        if (msgArray[contador] == '/') {categoria++; contador++;}
                            
                        switch (categoria) {
                            case 0 -> nomej = nomej + msgArray[contador] ;
                            case 1 -> nomeh = nomeh + msgArray[contador] ;
                            default -> {}
                        }
                    }
                    contador ++;
                }
                
                File arquivo = null;
                File arquivo2 = null;
                
                try {
                    arquivo = new File(CAMINHOHABILIDADES + "\\" + nomeh + ".txt");
                    arquivo2 = new File(CAMINHOHABILIDADES + "\\" + nomeh + ".txt");
                }
                catch (Exception e) {canal.sendMessage("Arquivo (Habilidade ou Jogador) não existe(m).").queue();}
                
                if (arquivo.exists() == true && arquivo2.exists() == true ) {
                    try{arih(false,CAMINHOJOGADORES,nomej,nomeh,false);}
                    catch (IOException ex) {Logger.getLogger(Projeto_de_Cria.class.getName()).log(Level.SEVERE, null, ex);}
                    
                    canal.sendMessage("Habilidade " + nomeh + " removida de " + nomej + " :white_check_mark:").queue();}
                
                else{ canal.sendMessage("Arquivo de habilidade não existe").queue(); }
            }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//ResetaHP
            else if (msgArray[0] == '+' && msgArray[1] == 'R' && msgArray[2] == 'e' && msgArray[3] == 's' && msgArray[5] == 'e' && msgArray[6] == 't' && msgArray[7] == 'a' && msgArray[8] == ' '){
                String nome = "" ; String STRhp = "";
                
                int contador = 0;
                while (contador != msgArray.length){
                    if (contador <= 8){}
                    else {nome = nome + msgArray[contador];}
                    contador ++;
                }
                
                try { STRhp = procuraModificaLinha( 9, CAMINHOJOGADORES, nome, null); }
                catch (IOException ex) { Logger.getLogger(Projeto_de_Cria.class.getName()).log(Level.SEVERE, null, ex); }
                
                String STRantes = "", STRdps = "";
                char CHARhp[] = STRhp.toCharArray();
                boolean antesBarra = true;
                
                for (int i = 0; i != CHARhp.length; i++){
                    if (CHARhp[i] == '/'){antesBarra = false;i++;}
                    
                    if (antesBarra = true)      {STRantes += CHARhp[i];}
                    else if (antesBarra = false){STRdps   += CHARhp[i];}
                }
                
            try { procuraModificaLinha ( 9, CAMINHOJOGADORES, nome, STRdps + "/" + STRdps); }
            catch (IOException ex) { Logger.getLogger(Projeto_de_Cria.class.getName()).log(Level.SEVERE, null, ex); }
            }
            
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Ação (Escolha)
            else if (msgArray[0] == '+' && msgArray[1] == 'A' && msgArray[2] == 'ç' && msgArray[3] == 'ã' && msgArray[4] == 'o'){
                
                String STRaction  = "";
                //String STRalvo    = "";
                boolean primeiroM = true;
                
                int contador = 0;
                while (contador != msgArray.length){
                    if (contador <= 5){}
                    else {
                        STRaction += msgArray[contador];
                        }
                    contador ++;
                }
                
                //action = Integer.parseInt(STRaction) ;
                //alvo   = Integer.parseInt(STRalvo)   ;
                vez++                                ;
                
                String voltarParaBatalha = "+Batalha ";
                
                if (OrdemATKj.size() == 0){}
                
                else{
                    primeiroM = true;
                    for (int i = 0; i == OrdemATKj.size(); i++){
                        voltarParaBatalha += OrdemATKj.get(i);

                        if (i == OrdemATKj.size()){voltarParaBatalha += OrdemATKj.get(i + 1); break;}

                        else { //se o prox é jogador add , se é monstro, add /
                            if (jogMon(OrdemATKj.get(i).toString()) == true){ voltarParaBatalha +=',';}
                            else {
                                if (primeiroM == true){voltarParaBatalha +='/';}
                                else {voltarParaBatalha +=',';}
                                }
                        }
                    }

                    msgArray = voltarParaBatalha.toCharArray();
                }
            }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Batalha
            if (msgArray[0] == '+' && msgArray[1] == 'B' && msgArray[2] == 'a' && msgArray[3] == 't' && msgArray[4] == 'a' && msgArray[5] == 'l' && msgArray[6] == 'h' && msgArray[7] == 'a' && msgArray[8] == ' '){
                if (vez >= OrdemATKj.size()) {vez = 0;} //vez = 0 - é a vez do primeiro jogador da lista0
                
                ArrayList<String> jogadores = new ArrayList<>();
                ArrayList<String> monstros  = new ArrayList<>();
                String nome                 = "";
                boolean JouM                = true; //true para J e false para M
                int cont                    = 9;
                emCombate                   = true;
                //action                      = 0;
                int xp                      = 0;
                
                while (cont != msgArray.length){
                    if (msgArray[cont] == '/') {JouM = false; cont++; jogadores.add(nome);nome = "";}
                    
                    else if (msgArray[cont] == ',') { cont++;
                        if      (JouM == true) {jogadores.add(nome);nome = "";}
                        else                   {monstros .add(nome);nome = "";}
                    }
                    
                    nome += msgArray[cont]; cont++;
                    
                    if (cont == msgArray.length){
                        if (JouM == true){jogadores.add(nome);nome = "";}
                        else             {monstros .add(nome);nome = "";}
                        break;
                    }
                }
                
                
                cont = 0;
                while (cont != jogadores.size()){
                    try {
                        String j = jogadores.get(cont);
                        int agi = Integer.parseInt(procuraModificaLinha( 9, CAMINHOJOGADORES, jogadores.get(cont), null));
                        
                        PrioridadeAGI j1 = new PrioridadeAGI (j,agi);
                        OrdemATKj.add(j1);
                    
                    } catch (IOException ex) {Logger.getLogger(Projeto_de_Cria.class.getName()).log(Level.SEVERE, null, ex);}
                    
                    cont++;
                }
                
                cont = 0;
                while (cont != monstros.size()){
                    try {
                        String j = monstros.get(cont);
                        int agi = Integer.parseInt(procuraModificaLinha( 3, CAMINHOMONSTROS, monstros.get(cont), null));
                        
                        PrioridadeAGI j1 = new PrioridadeAGI (j,agi);
                        OrdemATKj.add(j1);
                    
                    } catch (IOException ex) {Logger.getLogger(Projeto_de_Cria.class.getName()).log(Level.SEVERE, null, ex);}
                    
                    cont++;
                }
                
                Collections.sort (OrdemATKj);
                
                
                String string = "Ordem de ataque com base na AGI: \n";
                String hp = "", agi = "", icone = "";
                
                for (int i = 0; i < OrdemATKj.size() ; i++){
                    try { 
                        if (jogMon(OrdemATKj.get(i).toString()) == true) { //jogadores
                                icone = ":slight_smile:";
                                hp = procuraModificaLinha ( 7, CAMINHOJOGADORES, OrdemATKj.get(i).toString(), null);
                                agi = procuraModificaLinha ( 9, CAMINHOJOGADORES, OrdemATKj.get(i).toString(), null);}
                        
                        else if (jogMon(OrdemATKj.get(i).toString()) == false){ //monstros
                                icone = ":smiling_imp:";
                                hp = procuraModificaLinha ( 2, CAMINHOMONSTROS, OrdemATKj.get(i).toString(), null);
                                agi = procuraModificaLinha ( 3, CAMINHOMONSTROS, OrdemATKj.get(i).toString(), null);}
                        
                    } catch (IOException ex) {Logger.getLogger(Projeto_de_Cria.class.getName()).log(Level.SEVERE, null, ex);}
                    
                    string += (i+1) + ")  " + icone + " " + OrdemATKj.get(i) + "  :anatomical_heart::" + hp + "  :zap::" + agi + "\n";}
                    canal.sendMessage(string).queue();
                

                
                canal.sendMessage(":o:> Vez de: " + OrdemATKj.get(vez) + " <:o:    :eyes:OBS: +VisuFicha <Nome> p/ ver seus status").queue();
                
                
                File         file = new File (CAMINHOJOGADORES + "\\" + OrdemATKj.get(vez).toString()  + ".txt");
                List<String> habs = new ArrayList<>();
                String item = "";
                
                try {Scanner scan = new Scanner(file); //pegar as ações do jogador e colocar em uma lista
                
                    for (int j = 1; scan.hasNextLine(); j++)
                        try {
                            if (scan.nextLine() != ""){
                                item = procuraModificaLinha(j,CAMINHOJOGADORES,OrdemATKj.get(vez).toString(),null);
                                }
                            
                            if (item.charAt(0) == '#'){
                                item = item.substring(1);
                                
                                if ("".equals(item)){}
                                
                                else{habs.add(item);}
                            }
                        }
                        
                        catch (IOException ex) { Logger.getLogger(Projeto_de_Cria.class.getName()).log(Level.SEVERE, null, ex);}
                } catch (FileNotFoundException ex) {Logger.getLogger(Projeto_de_Cria.class.getName()).log(Level.SEVERE, null, ex);}
                
                
                //lista habs tem todas as ações do jogador da vez atual.
                
                if (jogMon(OrdemATKj.get(vez).toString()) == true){ //se for jogador
                    try {
                    String STRdano = procuraModificaLinha(3,CAMINHOHABILIDADES,habs.get(action),null);
                    
                    int posicao = lançarDado(OrdemATKj.size() - 1);
                    
                    while (jogMon(OrdemATKj.get(posicao).toString()) == true) {posicao = lançarDado(OrdemATKj.size() - 1);}
                    
                    canal.sendMessage(OrdemATKj.get(vez) + ":angry::crossed_swords::imp:" + OrdemATKj.get(posicao)).queue();
                    
                    int dano = 0;
                    
                    try { dano = Integer.parseInt(STRdano);
                          String hpJ = procuraModificaLinha(2,CAMINHOMONSTROS,OrdemATKj.get(posicao).toString(),null);
                          char charhp[] = hpJ.toCharArray();
                          hpJ = "";
                          String hpJ2 = "" ;
                          boolean antesDepoisBarra = false;
                        
                        
                        for(int i = 0; i != charhp.length ; i++){
                            if (charhp[i] == '/'){antesDepoisBarra = true; i++;}
                            if (antesDepoisBarra == false) {hpJ += charhp[i];}
                            else {hpJ2 += charhp[i];}
                        }
                        
                        int incremento = lançarDado(6);
                        
                        if (incremento == 1){ dano = 0;  canal.sendMessage(OrdemATKj.get(vez) + " errou!").queue();}
                        else {dano += incremento; canal.sendMessage(OrdemATKj.get(vez) + " causou " + dano + " de dano").queue();}
                        
                        int ihpj = Integer.parseInt(hpJ) - dano ;
                        
                        procuraModificaLinha(2,CAMINHOMONSTROS,OrdemATKj.get(posicao).toString(), "" + ihpj + "/" + hpJ2);
                        
                        int varaux = 0;
                        varaux = vez + 1;
                        if (varaux == OrdemATKj.size()){varaux = 0;}
                        
                        canal.sendMessage(":o:> Proximo jogador: " + OrdemATKj.get(vez + 1).toString() + "<:o:    :eyes:OBS: +VisuFicha <Nome> p/ ver seus status").queue();
                        canal.sendMessage("Digite '+Ação' para prosseguir").queue();
                        
                    }catch (IOException ex) {Logger.getLogger(Projeto_de_Cria.class.getName()).log(Level.SEVERE, null, ex);}
                    
                } catch (IOException ex) {Logger.getLogger(Projeto_de_Cria.class.getName()).log(Level.SEVERE, null, ex);}
                }
                
                else { //se for monstro
                    int posicao = lançarDado(OrdemATKj.size() - 1);
                    
                    while (jogMon(OrdemATKj.get(posicao).toString()) == false) {posicao = lançarDado(OrdemATKj.size() - 1);}
                    
                    canal.sendMessage(OrdemATKj.get(vez) + ":smiling_imp::crossed_swords::confounded:" + OrdemATKj.get(posicao)).queue();
                    
                    int dano = 0;
                    
                    try { dano = Integer.parseInt(procuraModificaLinha(5,CAMINHOMONSTROS,OrdemATKj.get(vez).toString(),null));
                          String hpJ = procuraModificaLinha(7,CAMINHOJOGADORES,OrdemATKj.get(posicao).toString(),null);
                          char charhp[] = hpJ.toCharArray();
                          hpJ = "";
                          String hpJ2 = "" ;
                          boolean antesDepoisBarra = false;
                        
                        
                        for(int i = 0; i != charhp.length ; i++){
                            if (charhp[i] == '/'){antesDepoisBarra = true; i++;}
                            if (antesDepoisBarra == false) {hpJ += charhp[i];}
                            else {hpJ2 += charhp[i];}
                        }
                        
                        int incremento = lançarDado(6);
                        
                        if (incremento == 1){ dano = 0;  canal.sendMessage(OrdemATKj.get(vez) + " errou!").queue();}
                        else {dano += incremento; canal.sendMessage(OrdemATKj.get(vez) + " causou " + dano + " de dano").queue();}
                        
                        int ihpj = Integer.parseInt(hpJ) - dano ;
                        
                        procuraModificaLinha(7,CAMINHOJOGADORES,OrdemATKj.get(posicao).toString(), "" + ihpj + "/" + hpJ2);
                        
                        int varaux = 0;
                        varaux = vez + 1;
                        if (varaux == OrdemATKj.size()){varaux = 0;}
                        
                        canal.sendMessage(":o:> Proximo jogador: " + OrdemATKj.get(vez + 1).toString() + "<:o:    :eyes:OBS: +VisuFicha <Nome> p/ ver seus status").queue();
                        canal.sendMessage("Digite '+Ação' para prosseguir").queue();
                         
                    } catch (IOException ex) {Logger.getLogger(Projeto_de_Cria.class.getName()).log(Level.SEVERE, null, ex);}
                }
                
///////// remover os hp = 0 da lista
                try {
                    for(int i = 0 ; i != OrdemATKj.size();i++) {
                        
                        if (verificaHP(OrdemATKj.get(i).toString()) == false){ //se o hp for 0, remover o jogador/monstro e incrementar o xp
                            if (jogMon(OrdemATKj.get(i).toString()) == false){xp += Integer.parseInt(procuraModificaLinha ( 4, CAMINHOMONSTROS, OrdemATKj.get(i).toString(), null)); }
                            
                            OrdemATKj.remove(i);
                            
                            boolean existeMonstro = true;
                            //se não houverem mais monstros, remover todos os itens
                            for(int j = 0 ; j != OrdemATKj.size();j++) {
                                if (jogMon(OrdemATKj.get(j).toString()) == false){existeMonstro = true;}
                                else {existeMonstro = false;}
                            }
                             
                            if (existeMonstro == false){OrdemATKj.removeAll(OrdemATKj); 
                                canal.sendMessage("Monstros derrotados, batalha finalizada. :fireworks: \n" +
                                                  "Digite '+Ação' para dar " + xp + "xp aos jogadores").queue();
                                
                                for (int k = 0; k != jogadores.size();k++){
                                    int qxp = Integer.parseInt(procuraModificaLinha(12,CAMINHOJOGADORES,jogadores.get(k), null));
                                    procuraModificaLinha(12,CAMINHOJOGADORES,jogadores.get(k), "" + (xp + qxp));
                                }
                                
                                break;}
                        }
                    }
                } catch (IOException ex) { Logger.getLogger(Projeto_de_Cria.class.getName()).log(Level.SEVERE, null, ex);}
                
                // ao acabar, adicionar o xp aos jogadores
            }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        }
    }
    
    public static void main                   (String[] args) throws FileNotFoundException, IOException, LoginException, InterruptedException {
        JDA bot = JDABuilder.createDefault      (TOKEN)
                            .addEventListeners  (new Projeto_de_Cria())
                            .setActivity        (Activity.listening("Suas mensagens. Digite ''+Ajuda''"))
                            .build              ()
                            .awaitReady         ();
    }
}
