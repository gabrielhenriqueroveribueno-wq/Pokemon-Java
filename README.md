# Pokémon Java

Trabalho Programação Orientada Objetos

EQUIPE:


Gabriel Henrique Roveri Bueno - RA: 2616285


João Victor Bastos Viseli - RA 2617956


Matheus André Villares Miranda - 2603308


Nicollas Matos - 2600977

​🎮 Jornada Pokémon: Jogo de Console em Java
​Fala, Treinador! Bem-vindo ao Jornada Pokémon, um RPG de texto jogável direto no terminal.
​Este projeto foi desenvolvido como avaliação prática para a disciplina de Programação Orientada a Objetos (POO). O objetivo aqui não foi apenas fazer um joguinho legal, mas sim aplicar os pilares fundamentais da POO em um cenário divertido e familiar.
​🕹️ O que dá para fazer no jogo?
​Escolha seu Inicial: Comece sua jornada escolhendo entre os clássicos Charmander, Squirtle ou Bulbasaur.
​Sistema de Batalha: Enfrente uma sequência de 5 Pokémon selvagens com mecânicas de turno.
​Vantagens Elementais: O dano é calculado com base no famoso triângulo de tipos (Fogo bate Planta, Planta bate Água, Água bate Fogo).
​Captura Inteligente: Você tem 5 Pokébolas. A chance de capturar um Pokémon aumenta conforme a vida dele diminui (20% de HP = Captura garantida!).
​Gestão de Equipe: Seu Pokémon desmaiou? O jogo obriga você a trocar por outro que ainda tenha vida.
​Pokédex: No final da campanha, o jogo exibe um relatório com todos os Pokémon registrados/capturados.
​📝 Checklist de Avaliação (Para o Professor)
​Para facilitar a correção, mapeamos onde os 12 Requisitos Técnicos Obrigatórios foram implementados no código:
​Mínimo de 5 classes próprias: Implementamos 6 (Ataque, Pokemon, PokemonFogo, PokemonAgua, PokemonPlanta, Treinador).
​Hierarquia de herança: Pokemon (superclasse abstrata) com 3 subclasses que usam extends e super(...) no construtor.
​Encapsulamento (private + validação): Atributos fechados nas classes e implementação dos setters setNome() e setHp() na classe Pokemon, que validam se o nome está vazio e impedem HP negativo.
​Atributos protected: Utilizados na superclasse Pokemon (nome, tipo, hp, etc.) para permitir acesso direto pelas subclasses elementais.
​Polimorfismo e Coleção: Método @Override apresentar() formatando a saída de cada tipo. Ele é chamado polimorficamente dentro de um for-each no método listarTime() da classe Treinador.
​Sobrecarga (Overload): A classe Pokemon possui duas versões do método receberDano (uma normal recebendo apenas o dano, e outra recebendo boolean ignorarDefesa para simular golpes críticos).
​instanceof e Downcasting: Utilizado no método listarTime() para verificar se o Pokémon é de Fogo (instanceof PokemonFogo) e convertê-lo (downcasting) para chamar o método exclusivo aquecerArena().
​Tipos primitivos e String: Uso consistente de int (dano/HP), boolean (estados de captura/loop) e String (nomes/tipos).
​Menu interativo (CRUD): Implementado no Main com opções de atacar (Atualizar), capturar (Criar/Cadastrar), trocar/listar (Ler) e controle de fim de jogo/desmaio.
​Tratamento de Scanner: Utilização limpa de .nextLine() e .nextInt(), com laço while impedindo o avanço do turno caso o usuário digite uma opção de ataque inválida (else de erro).
​Compilação limpa: O código roda integralmente no terminal sem falhas de sintaxe.
​Convenções: Código comentado, classes em PascalCase e variáveis/métodos em camelCase.

Como rodar o jogo na sua máquina:

​Para jogar, você só precisa ter o Java (JDK) instalado no seu computador.


Baixe o arquivo Main.java (ou clone este repositório).


Abra o terminal (ou prompt de comando) e navegue até a pasta onde o arquivo está salvo.


Compile o código rodando o comando:
javac Main.java


Em seguida, execute o jogo com:
java Main


Pronto! Agora é só interagir com os menus digitando os números correspondentes.
