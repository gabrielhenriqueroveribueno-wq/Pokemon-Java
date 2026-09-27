import java.util.ArrayList; 
import java.util.List; 
import java.util.Scanner; 

class Ataque {
    
    private String nome; 
    
    private int forcaBase; 
    
    private String tipo; 

    public Ataque(String nome, int forcaBase, String tipo) {
        
        this.nome = nome; 
        this.forcaBase = forcaBase;
        this.tipo = tipo;
    }

    public String getNome() { return nome; }
    public int getForcaBase() { return forcaBase; }
    public String getTipo() { return tipo; } 
}

abstract class Pokemon {
    
    protected String nome; 
    protected String tipo; 
    protected int hp;
    protected int hpMaximo;
    protected Ataque ataqueElemental;
    protected Ataque ataqueNormal;


    public Pokemon(String nome, String tipo, int hp, Ataque ataqueElemental) {
        this.nome = nome;
        this.tipo = tipo;
        this.hp = hp;
        this.hpMaximo = hp;
        this.ataqueElemental = ataqueElemental; 
        
        this.ataqueNormal = new Ataque("Investida", 10, "Normal"); 
    }

    public String getNome() { return nome; }
    public String getTipo() { return tipo; } 
    public int getHp() { return hp; }
    public int getHpMaximo() { return hpMaximo; }
    public Ataque getAtaqueElemental() { return ataqueElemental; }
    public Ataque getAtaqueNormal() { return ataqueNormal; }


    public void receberDano(int dano) {
        this.hp = this.hp - dano; 
        
        if (this.hp < 0) {
            this.hp = 0; 
        }
    }

    public abstract void apresentar();
}

class PokemonFogo extends Pokemon {

    public PokemonFogo(String nome, int hp, Ataque ataque) {
        super(nome, "Fogo", hp, ataque);
    }
    
    @Override
    public void apresentar() {
    
        System.out.println("[Fogo] " + nome + " | HP: " + hp + "/" + hpMaximo);
    }
}

class PokemonAgua extends Pokemon {
    public PokemonAgua(String nome, int hp, Ataque ataque) {
       
        super(nome, "Água", hp, ataque);
    }
    @Override
    public void apresentar() {
        System.out.println("[Água] " + nome + " | HP: " + hp + "/" + hpMaximo);
    }
}

class PokemonPlanta extends Pokemon {
    public PokemonPlanta(String nome, int hp, Ataque ataque) {
       
        super(nome, "Planta", hp, ataque);
    }
    @Override
    public void apresentar() {
        System.out.println("[Planta] " + nome + " | HP: " + hp + "/" + hpMaximo);
    }
}

class Treinador {
    private String nome; 
    private int pokebolas; 
    private List<Pokemon> time = new ArrayList<>(); 
    private List<String> pokedex = new ArrayList<>(); 

    public Treinador(String nome) {
        this.nome = nome;
        this.pokebolas = 5; 
    }

    public String getNome() { return nome; }
    public int getPokebolas() { return pokebolas; }
    public List<Pokemon> getTime() { return time; } 

    public void registrarNaPokedex(String nomePokemon) {
        pokedex.add(nomePokemon);
        
    }

    public boolean tentarCapturar(Pokemon p) {
        pokebolas--;
        System.out.println("\nVocê jogou uma Pokébola no " + p.getNome() + "...");

        int porcentagemVida = (p.getHp() * 100) / p.getHpMaximo();
        int chanceDeCaptura;

        if (porcentagemVida <= 20) {
            chanceDeCaptura = 100;
            System.out.println("O Pokémon está muito fraco! Captura garantida!");
        } else if (porcentagemVida <= 50) {
            chanceDeCaptura = 80;
            System.out.println("O Pokémon está ferido. A chance é alta (80%).");
        } else {
            chanceDeCaptura = 50;
            System.out.println("O Pokémon está com muita energia. A chance é média (50%).");
        }

        int sorte = (int) (Math.random() * 100);

        if (sorte < chanceDeCaptura) {
            time.add(p);
            registrarNaPokedex(p.getNome());
            System.out.println("SUCESSO! Você capturou o " + p.getNome() + "!");
            return true;
        } else {
            System.out.println("AH NÃO! O " + p.getNome() + " escapou da Pokébola!");
            return false;
        }
    }


    public void receberInicial(Pokemon p) {
        time.add(p);
        registrarNaPokedex(p.getNome());
    }

    public void listarTime() {
        System.out.println("\n--- SEU TIME POKÉMON ---");
        for (int i = 0; i < time.size(); i++) {
            System.out.print((i + 1) + ". ");
            time.get(i).apresentar(); 
        }
    }

    public boolean temPokemonVivo() {
        for (Pokemon p : time) {
            if (p.getHp() > 0) return true;
        }
        return false;
    }

    public void exibirPokedexFinal() {
        System.out.println("\n================ POKÉDEX ================");
        System.out.println("Pokémons registrados na sua jornada:");
        
        for (String nomeRegistro : pokedex) {
            System.out.println("- " + nomeRegistro);
        }
        System.out.println("=========================================");
    }
}


public class Main {
    
    public static int calcularDanoComVantagem(Ataque ataqueUsado, Pokemon defensor) {
        int dano = ataqueUsado.getForcaBase();
        String tipoA = ataqueUsado.getTipo();
        String tipoD = defensor.getTipo();

        // VANTAGEM
        if ((tipoA.equals("Fogo") && tipoD.equals("Planta")) || 
            (tipoA.equals("Água") && tipoD.equals("Fogo")) || 
            (tipoA.equals("Planta") && tipoD.equals("Água"))) {
            
            System.out.println("É super efetivo! O dano dobrou!");
            return dano * 2; 
        }
        
        // DESVANTAGEM
        if ((tipoA.equals("Fogo") && tipoD.equals("Água")) || 
            (tipoA.equals("Água") && tipoD.equals("Planta")) || 
            (tipoA.equals("Planta") && tipoD.equals("Fogo"))) {
            
            System.out.println("Não é muito efetivo... O dano caiu para metade.");
            return dano / 2;
        }
        
    
        return dano;
    }

   
    public static void main(String[] args) {
        
        Scanner leitor = new Scanner(System.in); 

        System.out.println("Bem-vindo ao mundo Pokémon!");
        System.out.print("Qual é o seu nome, Treinador? ");
        
        String nomeTreinador = leitor.nextLine(); 

        Treinador jogador = new Treinador(nomeTreinador);
        System.out.println("Olá " + jogador.getNome() + ", você recebeu " + jogador.getPokebolas() + " pokebolas!");

        System.out.println("\nChegou a hora de escolher o seu primeiro Pokémon!");
        System.out.println("1 - Charmander (Fogo)");
        System.out.println("2 - Squirtle (Água)");
        System.out.println("3 - Bulbasaur (Planta)");
        System.out.print("Escolha (1, 2 ou 3): ");
        
        int escolha = leitor.nextInt(); 
        Pokemon inicial = null; 

        if (escolha == 1) {
            
            inicial = new PokemonFogo("Charmander", 50, new Ataque("Brasa", 15, "Fogo"));
        } else if (escolha == 2) {
            inicial = new PokemonAgua("Squirtle", 50, new Ataque("Jato de Água", 15, "Água"));
        } else {
            inicial = new PokemonPlanta("Bulbasaur", 50, new Ataque("Chicote de Vinha", 15, "Planta"));
        }

        jogador.receberInicial(inicial); 
        jogador.listarTime(); 

        List<Pokemon> selvagens = new ArrayList<>();
        
        selvagens.add(new PokemonPlanta("Oddish", 30, new Ataque("Absorver", 10, "Planta")));
        selvagens.add(new PokemonFogo("Vulpix", 30, new Ataque("Brasa", 10, "Fogo")));
        selvagens.add(new PokemonAgua("Psyduck", 35, new Ataque("Arma de Água", 12, "Água")));
        selvagens.add(new PokemonFogo("Growlithe", 40, new Ataque("Mordida", 15, "Normal")));
        selvagens.add(new PokemonAgua("Poliwag", 40, new Ataque("Bolhas", 12, "Água")));

        for (int i = 0; i < selvagens.size(); i++) {
            Pokemon selvagemAtual = selvagens.get(i); 
            System.out.println("\n==================================");
            System.out.println("Um " + selvagemAtual.getNome() + " selvagem apareceu!");
            
            Pokemon meuPokemon = null;
            for (Pokemon p : jogador.getTime()) {
                if (p.getHp() > 0) {
                    meuPokemon = p;
                    break;
                }
            }
            
            boolean batalhaRodando = true; 

            while (batalhaRodando) {
                System.out.println("\n" + meuPokemon.getNome() + " [HP: " + meuPokemon.getHp() + "/" + meuPokemon.getHpMaximo() + "] VS " + selvagemAtual.getNome() + " [HP: " + selvagemAtual.getHp() + "/" + selvagemAtual.getHpMaximo() + "]");
                System.out.println("O que deseja fazer?");
                System.out.println("1 - Atacar");
                System.out.println("2 - Capturar (Pokébolas: " + jogador.getPokebolas() + ")");
                System.out.println("3 - Trocar de Pokémon"); 
                System.out.print("Opção: ");
                int acao = leitor.nextInt(); 

                if (acao == 1) {
                    Ataque ataqueEscolhido = null;
                    boolean escolheuAtaque = false;

                    while (!escolheuAtaque) {
                        System.out.println("\nEscolha o ataque:");
                        System.out.println("1 - " + meuPokemon.getAtaqueElemental().getNome() + " (Tipo " + meuPokemon.getAtaqueElemental().getTipo() + ")");
                        System.out.println("2 - " + meuPokemon.getAtaqueNormal().getNome() + " (Tipo " + meuPokemon.getAtaqueNormal().getTipo() + ")");
                        System.out.println("3 - Voltar");
                        System.out.print("Opção: ");
                        int opAtaque = leitor.nextInt();

                        if (opAtaque == 1) {
                            ataqueEscolhido = meuPokemon.getAtaqueElemental();
                            escolheuAtaque = true;
                        
                        } else if (opAtaque == 2) {
                            ataqueEscolhido = meuPokemon.getAtaqueNormal();
                            escolheuAtaque = true;
                        
                        } else if (opAtaque == 3) {
                            escolheuAtaque = true;
                        
                        } else {
                            System.out.println("Opção inválida!");
                        }
                    }

                    if (ataqueEscolhido == null) {
                        continue; 
                    }

                    System.out.println("\n" + meuPokemon.getNome() + " usou " + ataqueEscolhido.getNome() + "!");
                    int danoCausado = calcularDanoComVantagem(ataqueEscolhido, selvagemAtual);
                    selvagemAtual.receberDano(danoCausado); 

                    if (selvagemAtual.getHp() == 0) {
                        System.out.println("Você nocauteou o " + selvagemAtual.getNome() + "!");
                        jogador.registrarNaPokedex(selvagemAtual.getNome()); 
                        batalhaRodando = false;
                        continue; 
                    }

                    Ataque ataqueInimigo = selvagemAtual.getAtaqueElemental();
                    
                    System.out.println("\nO " + selvagemAtual.getNome() + " usou " + ataqueInimigo.getNome() + "!");
                    
                    int danoSofrido = calcularDanoComVantagem(ataqueInimigo, meuPokemon);
                    meuPokemon.receberDano(danoSofrido); 

                    if (meuPokemon.getHp() == 0) {
                        System.out.println("O seu " + meuPokemon.getNome() + " desmaiou!");
                        
                        if (jogador.temPokemonVivo()) {
                            System.out.println("Você precisa escolher outro Pokémon!");
                            acao = 3; 
                        } else {
                            System.out.println("Todos os seus Pokémon desmaiaram! Fim de jogo.");
                            jogador.exibirPokedexFinal();
                            return; 
                        }
                    }

                } else if (acao == 2) {
                    
                    if (jogador.getPokebolas() > 0) {
                        boolean capturou = jogador.tentarCapturar(selvagemAtual);
                        
                        if (capturou) {
                            batalhaRodando = false; 
                        } else {
                            Ataque ataqueInimigo = selvagemAtual.getAtaqueElemental();
                            
                            System.out.println("\nEnquanto você se lamentava pela Pokébola perdida, o " + selvagemAtual.getNome() + " contra-atacou!");
                            System.out.println("O " + selvagemAtual.getNome() + " usou " + ataqueInimigo.getNome() + "!");
                            
                            int danoSofrido = calcularDanoComVantagem(ataqueInimigo, meuPokemon);
                            meuPokemon.receberDano(danoSofrido);
                            
                            if (meuPokemon.getHp() == 0) {
                                System.out.println("O seu " + meuPokemon.getNome() + " desmaiou!");
                                if (jogador.temPokemonVivo()) {
                                    System.out.println("Você precisa escolher outro Pokémon no próximo turno.");
                                    acao = 3; 
                                } else {
                                    System.out.println("Fim de jogo.");
                                    jogador.exibirPokedexFinal();
                                    return;
                                }
                            }
                        }
                    } else {
                        System.out.println("Sem pokébolas! Você é obrigado a atacar!");
                    }
                } 
                if (acao == 3) {
                    boolean trocou = false;
                    
                    while (!trocou) {
                        jogador.listarTime();
                        System.out.print("Escolha o número do Pokémon: ");
                        int escolhaTroca = leitor.nextInt() - 1;

                        if (escolhaTroca >= 0 && escolhaTroca < jogador.getTime().size()) {
                            Pokemon escolhido = jogador.getTime().get(escolhaTroca);
                            
                            if (escolhido.getHp() > 0) {
                                meuPokemon = escolhido;
                                System.out.println("Você enviou o " + meuPokemon.getNome() + " para a batalha!");
                                trocou = true;
                            } else {
                                System.out.println("Este Pokémon está desmaiado! Escolha outro.");
                            }
                        } else {
                            System.out.println("Número inválido!");
                        }
                    }
                }
            }
        }
        System.out.println("\nPARABÉNS! Você concluiu a jornada de 5 batalhas!");
        jogador.listarTime(); 
        jogador.exibirPokedexFinal(); 
        leitor.close(); 
    }
}