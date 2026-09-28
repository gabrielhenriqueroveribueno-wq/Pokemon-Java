# Pokémon Java

Trabalho Programação Orientada Objetos

EQUIPE:


Gabriel Henrique Roveri Bueno - RA: 2616285


João Victor Bastos Viseli - RA 2617956


Matheus André Villares Miranda - 2603308


Nicollas Matos - 2600977

Jornada Pokémon: Jogo de Console em Java


​Fala, Treinador! Bem-vindo ao Jornada Pokémon, um RPG de texto jogável direto no terminal.

​Este projeto foi desenvolvido como avaliação prática para a disciplina de Programação Orientada a Objetos (POO). O objetivo aqui não foi apenas fazer um joguinho legal, mas sim aplicar os pilares fundamentais da POO em um cenário divertido e familiar.


​O que dá para fazer no jogo?


​Escolha seu Inicial: Comece sua jornada escolhendo entre os clássicos Charmander, Squirtle ou Bulbasaur.


​Sistema de Batalha de 5 Turnos: Enfrente uma sequência de 5 Pokémon selvagens com mecânicas de turno.


​Vantagens Elementais: O dano é calculado com base no famoso triângulo de tipos (Fogo bate Planta, Planta bate Água, Água bate Fogo).


​Captura Inteligente: Você tem 5 Pokébolas. A chance de capturar um Pokémon aumenta conforme a vida dele diminui (20% de HP = Captura garantida!).


​Gestão de Equipe: Seu Pokémon desmaiou? O jogo obriga você a trocar por outro que ainda tenha vida.


​Pokédex: No final da campanha (seja por vitória ou "Game Over"), o jogo exibe um relatório com todos os Pokémon registrados/capturados.


​Conceitos de POO Aplicados

​Para atender aos requisitos técnicos do projeto, o código foi estruturado utilizando:


​Encapsulamento: Atributos protegidos e privados, acessados via Getters e validados via Setters (ex: impedindo que um Pokémon nasça com HP negativo).


​Herança: Uma superclasse abstrata Pokemon que empresta seus atributos e métodos básicos para as subclasses PokemonFogo, PokemonAgua e PokemonPlanta.


​Polimorfismo: Sobrescrita de métodos com @Override (como o método apresentar()), permitindo que a classe Treinador liste a equipe de forma limpa, sem precisar usar dezenas de if/else.


​Sobrecarga (Overloading): Múltiplas versões do método receberDano (uma simples e outra que simula um "Golpe Crítico" ignorando a defesa).


​Downcasting & instanceof: Verificação de tipo em tempo de execução para ativar métodos exclusivos de subclasses específicas (ex: aquecerArena() apenas para tipos Fogo).


​Como rodar o jogo aí na sua máquina


​Para jogar, você só precisa ter o Java (JDK) instalado no seu computador.


Baixe o arquivo Main.java (ou clone este repositório).


Abra o terminal (ou prompt de comando) e navegue até a pasta onde o arquivo está salvo.


Compile o código rodando o comando:
javac Main.java


Em seguida, execute o jogo com:
java Main


Pronto! Agora é só interagir com os menus digitando os números correspondentes.
