// Importa a classe ArrayList, a ferramenta que permite criar listas que crescem ou diminuem de tamanho dinamicamente.
import java.util.ArrayList; 
// Importa a interface List, que define as regras básicas de como uma lista deve funcionar no Java.
import java.util.List; 
// Importa o Scanner, a ferramenta usada para capturar o que o jogador digita no teclado do terminal.
import java.util.Scanner; 

// 1. CLASSE ATAQUE: É o "molde" ou "planta" para criar os golpes do jogo.
class Ataque {
    // 'private' protege a variável. Apenas a própria classe Ataque consegue alterar estes valores diretamente.
    private String nome; 
    // Guarda o valor matemático do dano base do golpe (ex: 15).
    private int forcaBase; 
    // Guarda o elemento do golpe (ex: "Fogo", "Normal", "Água").
    private String tipo; 

    // Construtor: É o método acionado automaticamente quando usas o comando 'new Ataque(...)'. Ele constrói o objeto na memória.
    public Ataque(String nome, int forcaBase, String tipo) {
        // O 'this.' avisa o Java que te referes à variável da classe lá em cima, e não ao parâmetro que chegou nos parênteses.
        this.nome = nome; 
        this.forcaBase = forcaBase;
        this.tipo = tipo;
    }

    // 'getters': Como as variáveis estão fechadas como 'private', usamos estas funções públicas para deixar que outras classes (como o Main) consigam LER os dados.
    public String getNome() { return nome; }
    public int getForcaBase() { return forcaBase; }
    public String getTipo() { return tipo; } 
}

// 2. SUPERCLASSE ABSTRATA POKEMON: É a classe "Pai" geral.
// 'abstract' significa que é apenas um conceito. Não podes usar 'new Pokemon()', és obrigado a criar um tipo específico (Fogo, Água, etc.).
abstract class Pokemon {
    // 'protected' significa que esta variável está protegida do mundo exterior, mas as classes "Filhas" (que usam extends) têm permissão para usá-la.
    protected String nome; 
    protected String tipo; 
    protected int hp; // Vida atual que sobe e desce na batalha.
    protected int hpMaximo; // Vida total original (essencial para a matemática da percentagem de captura).
    protected Ataque ataqueElemental; // O ataque principal e forte do Pokémon.
    protected Ataque ataqueNormal; // O ataque secundário básico.

    // Construtor da classe pai. Recebe as informações básicas no momento da criação.
    public Pokemon(String nome, String tipo, int hp, Ataque ataqueElemental) {
        this.nome = nome;
        this.tipo = tipo;
        this.hp = hp;
        this.hpMaximo = hp; // No instante em que o Pokémon nasce, a sua vida atual é idêntica à vida máxima.
        this.ataqueElemental = ataqueElemental; 
        
        // O Truque Automático: Todo e qualquer Pokémon instanciado cria automaticamente o seu próprio ataque "Investida". Não precisas de o passar nos parênteses.
        this.ataqueNormal = new Ataque("Investida", 10, "Normal"); 
    }

    // Mais métodos 'getters' para permitir que o ecrã de batalha leia a vida, nome e ataques do Pokémon.
    public String getNome() { return nome; }
    public String getTipo() { return tipo; } 
    public int getHp() { return hp; }
    public int getHpMaximo() { return hpMaximo; }
    public Ataque getAtaqueElemental() { return ataqueElemental; }
    public Ataque getAtaqueNormal() { return ataqueNormal; }

    // Método que aplica o dano sofrido. Quem bater neste Pokémon vai chamar esta função.
    public void receberDano(int dano) {
        this.hp = this.hp - dano; // Pega na vida atual e subtrai o estrago.
        
        // Trava de segurança: se o dano for tão grande que a vida caia para baixo de zero (ex: -12), o sistema trava o número no 0.
        if (this.hp < 0) {
            this.hp = 0; 
        }
    }

    // Método 'abstract': A superclasse não sabe como se apresentar. Isto obriga todas as subclasses a criarem obrigatoriamente a sua própria versão de apresentação.
    public abstract void apresentar();
}

// 3. SUBCLASSES DE POKEMON: As classes "Filhas" que herdam a estrutura do Pai.
// 'extends Pokemon' faz a classe PokemonFogo herdar automaticamente o nome, hp e métodos da classe Pokemon.
class PokemonFogo extends Pokemon {
    // Construtor do Pokémon de Fogo. Recebe apenas nome, vida e o golpe principal.
    public PokemonFogo(String nome, int hp, Ataque ataque) {
        // 'super' é uma chamada direta ao construtor da classe Pai (Pokemon). Ele entrega os dados para cima e injeta a palavra fixa "Fogo" como o tipo.
        super(nome, "Fogo", hp, ataque);
    }
    
    // '@Override' é um aviso: "Estou a sobrescrever (esmagar) aquele método apresentar() genérico que o meu Pai me obrigou a ter".
    @Override
    public void apresentar() {
        // Imprime o texto formatado no terminal com a tag visual [Fogo].
        System.out.println("[Fogo] " + nome + " | HP: " + hp + "/" + hpMaximo);
    }
}

class PokemonAgua extends Pokemon {
    public PokemonAgua(String nome, int hp, Ataque ataque) {
        // O 'super' desta classe encarrega-se de avisar o Pai que o tipo definitivo é "Água".
        super(nome, "Água", hp, ataque);
    }
    @Override
    public void apresentar() {
        System.out.println("[Água] " + nome + " | HP: " + hp + "/" + hpMaximo);
    }
}

class PokemonPlanta extends Pokemon {
    public PokemonPlanta(String nome, int hp, Ataque ataque) {
        // Idem, avisa o Pai que o tipo é "Planta".
        super(nome, "Planta", hp, ataque);
    }
    @Override
    public void apresentar() {
        System.out.println("[Planta] " + nome + " | HP: " + hp + "/" + hpMaximo);
    }
}

// 4. CLASSE TREINADOR: É o gestor da sessão do jogador.
class Treinador {
    private String nome; 
    private int pokebolas; 
    // Cria uma lista vazia configurada para armazenar exclusivamente objetos da família 'Pokemon'. É o teu time.
    private List<Pokemon> time = new ArrayList<>(); 
    // Cria uma lista vazia configurada para armazenar textos simples (Strings). É o bloco de notas da tua Pokédex.
    private List<String> pokedex = new ArrayList<>(); 

    // Construtor do Treinador: Só pede o nome quando inicias a jornada.
    public Treinador(String nome) {
        this.nome = nome;
        // Todo o treinador começa o jogo com 5 Pokébolas garantidas no bolso.
        this.pokebolas = 5; 
    }

    // Métodos 'getters' básicos que permitem a outras partes do código lerem as informações privadas do Treinador.
    public String getNome() { return nome; }
    public int getPokebolas() { return pokebolas; }
    public List<Pokemon> getTime() { return time; } 

    // Método responsável por adicionar o nome de um Pokémon à lista de textos da Pokédex.
    public void registrarNaPokedex(String nomePokemon) {
        // O sinal '!' significa "NÃO". O comando '.contains()' verifica se a lista já tem esse nome.
        // Ou seja: "Se a Pokédex NÃO contiver este nome, então adiciona-o". Isto impede nomes repetidos.
        if (!pokedex.contains(nomePokemon)) {
            pokedex.add(nomePokemon);
        }
    }

    // Método que tenta capturar o Pokémon. Ele retorna um 'boolean' (true se capturar, false se falhar).
    public boolean tentarCapturar(Pokemon p) {
        pokebolas--; // Subtrai 1 ao número total de pokébolas do treinador.
        System.out.println("\nVocê jogou uma Pokébola no " + p.getNome() + "...");

        // Regra de três simples para descobrir a percentagem exata de vida atual do Pokémon selvagem.
        int porcentagemVida = (p.getHp() * 100) / p.getHpMaximo();
        int chanceDeCaptura; // Variável vazia que vai guardar a nossa probabilidade de sucesso.

        // Estrutura de decisão para definir a facilidade da captura com base na vida.
        if (porcentagemVida <= 20) {
            chanceDeCaptura = 100; // Se tiver 20% ou menos de vida, a captura não falha.
            System.out.println("O Pokémon está muito fraco! Captura garantida!");
        } else if (porcentagemVida <= 50) {
            chanceDeCaptura = 80; // Se tiver 50% ou menos, falha raramente.
            System.out.println("O Pokémon está ferido. A chance é alta (80%).");
        } else {
            chanceDeCaptura = 50; // Se estiver com mais de metade da vida, é o mais difícil (cara ou coroa).
            System.out.println("O Pokémon está com muita energia. A chance é média (50%).");
        }

        // Math.random() gera um decimal de 0.0 a 0.99. Multiplicar por 100 e forçar a ser '(int)' cria um sorteio de 0 a 99.
        int sorte = (int) (Math.random() * 100);

        // Se o número tirado na sorte for menor que a nossa chance, capturamos!
        if (sorte < chanceDeCaptura) {
            time.add(p); // Adiciona o objeto Pokémon à equipa do Treinador.
            registrarNaPokedex(p.getNome()); // Regista o nome como 'visto/capturado'.
            System.out.println("SUCESSO! Você capturou o " + p.getNome() + "!");
            return true; // Devolve verdadeiro para a Main saber que a batalha acabou.
        } else {
            System.out.println("AH NÃO! O " + p.getNome() + " escapou da Pokébola!");
            return false; // Devolve falso para a Main saber que a batalha tem de continuar.
        }
    }

    // Método de atalho usado apenas no início do jogo para te dar o Bulbasaur, Charmander ou Squirtle.
    public void receberInicial(Pokemon p) {
        time.add(p); // Adiciona à equipa diretamente, sem sorteios.
        registrarNaPokedex(p.getNome()); // Regista logo na Pokédex.
    }

    // Método para imprimir todos os Pokémons que tens contigo.
    public void listarTime() {
        System.out.println("\n--- SEU TIME POKÉMON ---");
        // Um laço 'for' tradicional. Começa no 0 e vai até ao tamanho exato da tua lista (time.size()).
        for (int i = 0; i < time.size(); i++) {
            System.out.print((i + 1) + ". "); // Imprime o número (ex: "1. ")
            // Pega no Pokémon que está na posição 'i' e executa a função polimórfica que o desenha no ecrã.
            time.get(i).apresentar(); 
        }
    }

    // Função vital para saber se ainda podes lutar ou se tomaste "Game Over". Retorna verdadeiro ou falso.
    public boolean temPokemonVivo() {
        // Laço 'for-each' moderno: "Para cada Pokémon 'p' dentro da lista 'time'..."
        for (Pokemon p : time) {
            // Se encontrar pelo menos um com vida acima de 0, devolve logo verdadeiro e para a pesquisa.
            if (p.getHp() > 0) return true;
        }
        // Se o laço for até ao fim e não encontrar ninguém vivo, devolve falso.
        return false;
    }

    // Função usada no final do jogo para mostrar tudo o que registaste.
    public void exibirPokedexFinal() {
        System.out.println("\n================ POKÉDEX ================");
        System.out.println("Pokémons registrados na sua jornada:");
        // Outro 'for-each', mas desta vez a varrer a lista de textos (String) e não a lista de objetos (Pokemon).
        for (String nomeRegistro : pokedex) {
            System.out.println("- " + nomeRegistro);
        }
        System.out.println("=========================================");
    }
}

// 5. CLASSE PRINCIPAL
public class Main {
    
    // MÉTODO ESTÁTICO (static): Pertence à classe em si, não precisas de criar um 'new Main()' para o usar.
    // Ele recebe o Ataque usado e o Pokémon que vai levar a pancada, e decide o dano final.
    public static int calcularDanoComVantagem(Ataque ataqueUsado, Pokemon defensor) {
        int dano = ataqueUsado.getForcaBase(); // Descobre o valor original do estrago.
        String tipoA = ataqueUsado.getTipo(); // Descobre o elemento do ataque.
        String tipoD = defensor.getTipo(); // Descobre o elemento do corpo do defensor.

        // VANTAGEM: Se o tipo do ataque bater na fraqueza do defensor...
        // Os '||' significam 'OU'. E os '&&' significam 'E'.
        if ((tipoA.equals("Fogo") && tipoD.equals("Planta")) || 
            (tipoA.equals("Água") && tipoD.equals("Fogo")) || 
            (tipoA.equals("Planta") && tipoD.equals("Água"))) {
            
            System.out.println("É super efetivo! O dano dobrou!");
            return dano * 2; // Retorna imediatamente a matemática resolvida (dano a dobrar).
        }
        
        // DESVANTAGEM: Se o elemento do defensor resistir bem ao elemento do ataque...
        if ((tipoA.equals("Fogo") && tipoD.equals("Água")) || 
            (tipoA.equals("Água") && tipoD.equals("Planta")) || 
            (tipoA.equals("Planta") && tipoD.equals("Fogo"))) {
            
            System.out.println("Não é muito efetivo... O dano caiu para metade.");
            return dano / 2; // Retorna imediatamente o estrago cortado ao meio.
        }
        
        // NEUTRO: Se o ataque for "Normal" (ex: Investida) ou se for Água contra Água, o código ignora os 'ifs' acima.
        return dano; // Retorna a força base limpa, sem bónus nem punições.
    }

    // Método principal: é aqui que o programa começa a ser executado pelo Java.
    public static void main(String[] args) {
        // Cria o 'Scanner', ferramenta essencial para ler o que o jogador digita no terminal.
        Scanner leitor = new Scanner(System.in); 

        // Imprime as mensagens iniciais de boas-vindas no ecrã.
        System.out.println("Bem-vindo ao mundo Pokémon!");
        System.out.print("Qual é o seu nome, Treinador? ");
        
        // Fica à espera que o utilizador digite algo e prima Enter, guardando esse texto na variável 'nomeTreinador'.
        String nomeTreinador = leitor.nextLine(); 

        // Usa o nome acabado de digitar para criar (instanciar) um novo objeto da classe Treinador.
        Treinador jogador = new Treinador(nomeTreinador);
        // Usa os 'getters' do Treinador para lhe dar as boas-vindas personalizadas mostrando as suas pokébolas iniciais.
        System.out.println("Olá " + jogador.getNome() + ", você recebeu " + jogador.getPokebolas() + " pokebolas!");

        // Bloco de texto que exibe as três opções clássicas de Pokémon iniciais.
        System.out.println("\nChegou a hora de escolher o seu primeiro Pokémon!");
        System.out.println("1 - Charmander (Fogo)");
        System.out.println("2 - Squirtle (Água)");
        System.out.println("3 - Bulbasaur (Planta)");
        System.out.print("Escolha (1, 2 ou 3): ");
        
        // Lê o número inteiro que o utilizador digitou.
        int escolha = leitor.nextInt(); 
        
        // Cria uma variável do tipo Pokemon, mas deixa-a vazia (null) por enquanto, à espera da decisão.
        Pokemon inicial = null; 

        // Estrutura de decisão (if/else) baseada no número digitado.
        if (escolha == 1) {
            // Se digitou 1, o espaço vazio 'inicial' passa a ser um novo objeto da subclasse PokemonFogo.
            // Repare que passamos o nome, a vida (50) e criamos o Ataque ("Brasa", 15, "Fogo") logo na mesma linha.
            inicial = new PokemonFogo("Charmander", 50, new Ataque("Brasa", 15, "Fogo"));
        } else if (escolha == 2) {
            inicial = new PokemonAgua("Squirtle", 50, new Ataque("Jato de Água", 15, "Água"));
        } else {
            inicial = new PokemonPlanta("Bulbasaur", 50, new Ataque("Chicote de Vinha", 15, "Planta"));
        }

        // Chama o método do Treinador que adiciona este Pokémon recém-criado à lista de equipa e à Pokédex.
        jogador.receberInicial(inicial); 
        // Chama o método para imprimir a equipa no ecrã para confirmar a escolha.
        jogador.listarTime(); 

        // Cria a lista dos Pokémon selvagens que vão servir de "campanha" ou inimigos controlados pelo jogo.
        List<Pokemon> selvagens = new ArrayList<>();
        // Adiciona 5 inimigos diferentes usando 'new', definindo os seus nomes, vida, e ataques com os respetivos tipos.
        selvagens.add(new PokemonPlanta("Oddish", 30, new Ataque("Absorver", 10, "Planta")));
        selvagens.add(new PokemonFogo("Vulpix", 30, new Ataque("Brasa", 10, "Fogo")));
        selvagens.add(new PokemonAgua("Psyduck", 35, new Ataque("Arma de Água", 12, "Água")));
        selvagens.add(new PokemonFogo("Growlithe", 40, new Ataque("Mordida", 15, "Normal")));
        selvagens.add(new PokemonAgua("Poliwag", 40, new Ataque("Bolhas", 12, "Água")));

        // LAÇO EXTERNO (FOR): Este laço percorre a lista de selvagens do início ao fim (índices 0 a 4). 
        // Ele controla as 5 batalhas da campanha.
        for (int i = 0; i < selvagens.size(); i++) {
            // Pega no inimigo da posição atual (ex: se i = 0, pega o Oddish) e guarda-o na variável 'selvagemAtual'.
            Pokemon selvagemAtual = selvagens.get(i); 
            System.out.println("\n==================================");
            System.out.println("UM " + selvagemAtual.getNome().toUpperCase() + " SELVAGEM APARECEU!");
            
            // Variável vazia para guardar qual o Pokémon da nossa equipa que vai lutar agora.
            Pokemon meuPokemon = null;
            // LAÇO FOR-EACH: Passa por todos os Pokémon da nossa equipa.
            for (Pokemon p : jogador.getTime()) {
                // Procura o primeiro Pokémon que tenha vida maior que zero (que não esteja desmaiado).
                if (p.getHp() > 0) {
                    meuPokemon = p; // Define-o como o lutador ativo.
                    break; // Interrompe a busca assim que encontrar o primeiro vivo.
                }
            }
            
            // Variável de controlo (bandeira) que indica se a luta atual está a acontecer.
            boolean batalhaRodando = true; 

            // LAÇO INTERNO (WHILE): Mantém o turno da luta a repetir infinitamente até que a variável de cima vire 'false'.
            while (batalhaRodando) {
                // Imprime a barra de estado com o HP atual e o HP máximo dos dois combatentes.
                System.out.println("\n" + meuPokemon.getNome() + " [HP: " + meuPokemon.getHp() + "/" + meuPokemon.getHpMaximo() + "] VS " + selvagemAtual.getNome() + " [HP: " + selvagemAtual.getHp() + "/" + selvagemAtual.getHpMaximo() + "]");
                // Imprime o menu de batalha principal.
                System.out.println("O que deseja fazer?");
                System.out.println("1 - Atacar");
                System.out.println("2 - Capturar (Pokébolas: " + jogador.getPokebolas() + ")");
                System.out.println("3 - Trocar de Pokémon"); 
                System.out.print("Opção: ");
                
                // Lê a decisão do jogador.
                int acao = leitor.nextInt(); 

                // INÍCIO DO BLOCO DE ATAQUE
                if (acao == 1) {
                    // Variável vazia para guardar qual dos dois ataques o jogador vai escolher.
                    Ataque ataqueEscolhido = null;
                    // Variável de controlo para manter o utilizador preso no submenu até escolher algo válido.
                    boolean escolheuAtaque = false;

                    // LAÇO DO SUBMENU: Repete até 'escolheuAtaque' virar verdadeiro.
                    while (!escolheuAtaque) {
                        System.out.println("\nEscolha o ataque:");
                        // Imprime dinamicamente o nome e o tipo do ataque forte (elemental).
                        System.out.println("1 - " + meuPokemon.getAtaqueElemental().getNome() + " (Tipo " + meuPokemon.getAtaqueElemental().getTipo() + ")");
                        // Imprime o nome e o tipo do ataque fraco (normal).
                        System.out.println("2 - " + meuPokemon.getAtaqueNormal().getNome() + " (Tipo " + meuPokemon.getAtaqueNormal().getTipo() + ")");
                        System.out.println("3 - Voltar");
                        System.out.print("Opção: ");
                        int opAtaque = leitor.nextInt();

                        // O jogador escolheu o Ataque 1.
                        if (opAtaque == 1) {
                            ataqueEscolhido = meuPokemon.getAtaqueElemental();
                            escolheuAtaque = true; // Liberta o utilizador deste laço while.
                        
                        // O jogador escolheu o Ataque 2.
                        } else if (opAtaque == 2) {
                            ataqueEscolhido = meuPokemon.getAtaqueNormal();
                            escolheuAtaque = true; // Liberta o utilizador deste laço while.
                        
                        // O jogador decidiu voltar atrás no menu.
                        } else if (opAtaque == 3) {
                            escolheuAtaque = true; // Liberta o utilizador, mas a variável 'ataqueEscolhido' continua null.
                        
                        // O utilizador digitou um número inexistente (ex: 9).
                        } else {
                            System.out.println("Opção inválida!");
                        }
                    }

                    // Se a variável continuou null, é porque a pessoa escolheu a opção 3 (Voltar).
                    if (ataqueEscolhido == null) {
                        // O comando 'continue' é um atalho especial: ele interrompe o que está a ser feito e obriga o programa a voltar ao início imediato do 'while (batalhaRodando)'.
                        continue; 
                    }

                    // EXECUÇÃO DO DANO:
                    System.out.println("\n" + meuPokemon.getNome() + " usou " + ataqueEscolhido.getNome() + "!");
                    // Chama o método estático que calcula o bónus ou penalidade baseado nos tipos, devolvendo o número exato de HP a retirar.
                    int danoCausado = calcularDanoComVantagem(ataqueEscolhido, selvagemAtual);
                    // Pede ao inimigo para reduzir a própria vida com o dano calculado acima.
                    selvagemAtual.receberDano(danoCausado); 

                    // VERIFICAÇÃO DE VITÓRIA:
                    if (selvagemAtual.getHp() == 0) {
                        System.out.println("Você nocauteou o " + selvagemAtual.getNome() + "!");
                        // Como o inimigo desmaiou, regista-o na Pokédex como um pokémon visto.
                        jogador.registrarNaPokedex(selvagemAtual.getNome()); 
                        // Coloca a variável do laço principal como falsa, o que vai acabar com o 'while (batalhaRodando)'.
                        batalhaRodando = false; 
                        // O 'continue' aqui faz o laço 'for' (das campanhas) ignorar o resto do código e avançar para o próximo inimigo da lista.
                        continue; 
                    }

                    // INIMIGO ATACA
                    // Declara uma variável vazia para guardar qual dos dois ataques o inimigo vai escolher.
                    Ataque ataqueInimigo;
                    
                    // Math.random() gera um número decimal entre 0.0 e 0.99. 
                    // Se o número for maior que 0.5 (ou seja, 50% de probabilidade), cai no bloco 'if'.
                    if (Math.random() > 0.5) {
                        // O inimigo decide usar o ataque forte (elemental).
                        ataqueInimigo = selvagemAtual.getAtaqueElemental();
                    } else {
                        // Caso contrário (os outros 50%), o inimigo usa o ataque fraco (Investida).
                        ataqueInimigo = selvagemAtual.getAtaqueNormal();
                    }
                    
                    // Imprime o nome do ataque escolhido aleatoriamente pela Inteligência Artificial.
                    System.out.println("\nO " + selvagemAtual.getNome() + " usou " + ataqueInimigo.getNome() + "!");
                    
                    // Usa a função de vantagem para calcular se o ataque do inimigo é forte ou fraco contra ti.
                    int danoSofrido = calcularDanoComVantagem(ataqueInimigo, meuPokemon);
                    // Subtrai o estrago calculado à vida do teu Pokémon que está na arena.
                    meuPokemon.receberDano(danoSofrido); 

                    // Verifica se o ataque do inimigo foi suficiente para zerar a tua vida.
                    if (meuPokemon.getHp() == 0) {
                        System.out.println("O seu " + meuPokemon.getNome() + " desmaiou!");
                        
                        // Pergunta ao Treinador se ainda sobra algum Pokémon com vida na equipa.
                        if (jogador.temPokemonVivo()) {
                            System.out.println("Você precisa escolher outro Pokémon!");
                            // Muda forçadamente a tua 'acao' para 3. Isto faz o código entrar automaticamente no BLOCO DE TROCA mais abaixo.
                            acao = 3; 
                        } else {
                            // Se a equipa inteira estiver a zero, é "Game Over".
                            System.out.println("Todos os seus Pokémon desmaiaram! Fim de jogo.");
                            // Mostra a Pokédex que conseguiste preencher até este momento.
                            jogador.exibirPokedexFinal(); 
                            // O comando 'return' encerra o método main() imediatamente, desligando o programa.
                            return; 
                        }
                    }

                } else if (acao == 2) { // Se escolheste "2 - Capturar" no menu de ações principal...
                    
                    // TENTATIVA DE CAPTURA
                    // Só permite a captura se ainda tiveres itens disponíveis.
                    if (jogador.getPokebolas() > 0) {
                        // Tenta capturar e guarda o resultado (true ou false) na variável 'capturou'.
                        boolean capturou = jogador.tentarCapturar(selvagemAtual);
                        
                        if (capturou) {
                            // Se der 'true', muda a bandeira para false, o que quebra o laço 'while' e encerra esta batalha.
                            batalhaRodando = false; 
                        } else {
                            // Se der 'false', o Pokémon escapa e ataca-te como punição por teres falhado!
                            // Esta linha usa o Operador Ternário (uma forma resumida do if/else). 
                            // Tradução: "Atira a moeda (> 0.5) ? Se sim, usa ataque elemental : Se não, usa ataque normal".
                            Ataque ataqueInimigo = (Math.random() > 0.5) ? selvagemAtual.getAtaqueElemental() : selvagemAtual.getAtaqueNormal();
                            
                            System.out.println("\nEnquanto você se lamentava pela Pokébola perdida, o " + selvagemAtual.getNome() + " contra-atacou!");
                            System.out.println("O " + selvagemAtual.getNome() + " usou " + ataqueInimigo.getNome() + "!");
                            
                            // Reaproveita a mesma matemática de dano explicada lá em cima.
                            int danoSofrido = calcularDanoComVantagem(ataqueInimigo, meuPokemon);
                            meuPokemon.receberDano(danoSofrido);
                            
                            // Volta a verificar se o teu Pokémon resistiu a este contra-ataque.
                            if (meuPokemon.getHp() == 0) {
                                System.out.println("O seu " + meuPokemon.getNome() + " desmaiou!");
                                if (jogador.temPokemonVivo()) {
                                    System.out.println("Você precisa escolher outro Pokémon no próximo turno.");
                                    acao = 3; // Força a troca.
                                } else {
                                    System.out.println("Fim de jogo.");
                                    jogador.exibirPokedexFinal();
                                    return; // Desliga o jogo.
                                }
                            }
                        }
                    } else {
                        // Aviso caso escolhas a opção 2 mas não tenhas pokébolas. O turno não é gasto e o menu repete.
                        System.out.println("Sem pokébolas! Você é obrigado a atacar!");
                    }
                } 
                
                // BLOCO DE TROCA
                // O código entra aqui se escolheres 3 no menu OU se o programa forçar 'acao = 3' quando um Pokémon desmaia.
                if (acao == 3) {
                    // Bandeira que controla o ecrã de troca.
                    boolean trocou = false;
                    
                    // Repete a pergunta até digitares um número válido.
                    while (!trocou) {
                        jogador.listarTime(); // Mostra a equipa.
                        System.out.print("Escolha o número do Pokémon: ");
                        // Lê o número e subtrai 1. Porquê? Para os humanos a equipa é 1, 2, 3. Mas para as listas (ArrayList) do Java, a contagem começa sempre no índice 0. 
                        int escolhaTroca = leitor.nextInt() - 1;

                        // Valida a entrada: o número não pode ser menor que 0 nem maior/igual ao tamanho da lista.
                        if (escolhaTroca >= 0 && escolhaTroca < jogador.getTime().size()) {
                            // Vai à lista 'time' e extrai o Pokémon exato daquela posição.
                            Pokemon escolhido = jogador.getTime().get(escolhaTroca);
                            
                            // Impede-te de enviar para a arena um Pokémon que já esteja desmaiado.
                            if (escolhido.getHp() > 0) {
                                // Troca o lutador atual na arena pelo Pokémon que acabaste de escolher.
                                meuPokemon = escolhido;
                                System.out.println("Você enviou o " + meuPokemon.getNome() + " para a batalha!");
                                // A troca foi um sucesso. Muda a bandeira para 'true' para sairmos deste laço while.
                                trocou = true;
                            } else {
                                System.out.println("Este Pokémon está desmaiado! Escolha outro.");
                            }
                        } else {
                            System.out.println("Número inválido!");
                        }
                    }
                }
            } // Fecho do laço 'while (batalhaRodando)'. Se ninguém morreu e não houve captura, volta ao topo do menu de ações.
        } // Fecho do laço 'for' (das 5 batalhas selvagens). Se a batalha atual acabou, o 'for' avança para puxar o próximo inimigo.

        // ---------------- O FINAL DO JOGO ----------------
        // O código só chega a este ponto se o jogador passar vivo pelos 5 ciclos do laço 'for' acima.
        System.out.println("\nPARABÉNS! Você concluiu a jornada de 5 batalhas!");
        
        // Mostra como ficou a tua equipa no final (quem sobreviveu e as capturas recentes).
        jogador.listarTime(); 
        
        // Imprime no ecrã todos os nomes de Pokémon que registaste na tua jornada.
        jogador.exibirPokedexFinal(); 
        
        // Fecha o leitor de teclado. É uma boa prática em Java libertar o recurso do Scanner quando o programa acaba.
        leitor.close(); 
    }
}