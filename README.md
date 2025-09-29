# Sobre os Trabalhos

Estes trabalhos foram elaborados como parte da avaliação parcial da disciplina Redes de Computadores I, ministrada pelo professor Marlos Marques durante o IV Semestre 2025.2 do curso de Ciências da Computação da Universidade Estadual do Sudoeste da Bahia (UESB). Cada projeto apresentado visa simular algum aspecto do funcionamento de uma rede computacional, desde a transmissão de sinais até as suas camadas mais profundas. 

## Trabalho 1 - Transmissão de Sinais 

Este trabalho visa simular a transmissão de sinais entre computadores interligados por uma rede através de três algoritmos de codificação: binária, Manchester e Manchester Diferencial. Para tal, foram utilizados operadores que permitem a manipulação direta dos bits que compoem a mensagem para dessa forma, gerar a animação que simula o sinal contendo os bits sendo passado de uma máquina para outra. 

## Trabalho 2 - Transmissão de Sinais via Camada de Enlace de Dados

Este trabalho, além de retomar conceitos trabalhados no trabalho anterior junto de uma interface retrabalhada, foca em demonstrar na prática o funcionamento dos métodos de enquadramento realizados a partir da camada de enlace de dados, responsável por garantir um controle dos dados a serem enviados de modo a mitigar possíveis erros. Os quatro principais métodos são:

* **Contagem de caracteres:** o fluxo de bits é dividido em quadros de até 4 bytes (equivalente a um inteiro dentro da memória), reservando um dos bytes para contar os caracteres (também contando ele mesmo) presentes no quadro.
* **Inserção de bytes:** um caractere especial (chamado de flag) é reservado para delimitar o início e o fim de cada quadro. Caso exista um caractere igual à flag dentro do quadro, é utilizado um caractere de escape para que o receptor não o interprete como o início/fim de um quadro.
* **Inserção de bits:** um bit adicional 0 é inserido a cada cinco bits 1 consecutivos para formar o caractere de flag, sendo eles removidos pelo receptor.
* **Violação de codificação da camada física:** padrões (sequências de bits) são inseridos dentro do fluxo de bits de modo a burlar os métodos de codificação da camada física (Binária, Manchester e Manchester Diferencial).

# Construído com
* Bloco de Notas
* Visual Studio Code
* Java 1.8.431

# Tecnologias Utilizadas
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![JavaFX](https://img.shields.io/badge/javafx-%23FF0000.svg?style=for-the-badge&logo=javafx&logoColor=white)
