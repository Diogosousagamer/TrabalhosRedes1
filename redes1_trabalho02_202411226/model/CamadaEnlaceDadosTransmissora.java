/* ***************************************************************
* Autor............: Diogo Oliveira de Sousa
* Matricula........: 202411226
* Inicio...........: 13/09/2025
* Ultima alteracao.: 27/09/2025
* Nome.............: CamadaEnlaceDadosTransmissora
* Funcao...........: Esta classe tem como objetivo gerenciar os eventos e operacoes da CamadaEnlaceDadosTransmissora,
                     onde a mensagem passa pelo processo de enquadramento para garantir a eficacia de sua transmissao.
                     
*************************************************************** */

package model;

import controller.TelaPrincipalController;

public class CamadaEnlaceDadosTransmissora {
  /* Atributos */

  // Vetor de inteiros, responsavel por armazenar o quadro enquadrado
  private int[] quadroEnquadrado;

  /* Metodos de instanciacao/execucao da camada de enlace de dados */

  /*
   * ***************************************************************
   * Metodo: CamadaEnlaceDeDadosTranmissora
   * Funcao: executa as operacoes da camada de enlace de dados transmissora
   * Parametros: int[] quadro - vetor contendo a mensagem a ser enquadrada
   * Retorno: void
   ****************************************************************/

  protected void CamadaEnlaceDeDadosTransmissora(int[] quadro) {
    // Executa o enquadramento dos dados recebidos
    CamadaEnlaceDadosTransmissoraEnquadramento(quadro);

    // Inicializa a camada fisica transmissora para a codificacao final dos dados antes
    // da transmissao
    CamadaFisicaTransmissora camadaFisicaTransmissora = new CamadaFisicaTransmissora();
    camadaFisicaTransmissora.executarCamadaFisicaTransmissora(quadroEnquadrado);
  }

  /* Metodos de enquadramento */
  
  /*
   * ***************************************************************
   * Metodo: CamadaEnlaceDeDadosTranmissora
   * Funcao: executa o enquadramento do quadro para eventual transmissao
   * Parametros: int[] quadro - vetor contendo a mensagem a ser enquadrada
   * Retorno: void
   ****************************************************************/

  private void CamadaEnlaceDadosTransmissoraEnquadramento(int[] quadro) {
    // Obtem-se o tipo de enquadramento selecionado 
    int tipoEnquadramento = TelaPrincipalController.controller.getTipoEnquadramento();

    // Inicio do bloco switch/case
    // Executa-se o metodo correspondente ao tipo de enquadramento selecionado
    switch (tipoEnquadramento) {
      case 0: // Executa a contagem de caracteres
        quadroEnquadrado = CamadaEnlaceDadosTransmissoraEnquadramentoContagemCaracteres(quadro);
        break;

      case 1: // Executa a insercao de bytes
        quadroEnquadrado = CamadaEnlaceDadosTransmissoraEnquadramentoInsercaoBytes(quadro);
        break;

      case 2: // Executa a insercao de bits
        quadroEnquadrado = CamadaEnlaceDadosTransmissoraEnquadramentoInsercaoBits(quadro);
        break;

      case 3: // Executa a violacao de codificacao da camada fisica
        quadroEnquadrado = CamadaEnlaceDadosTransmissoraEnquadramentoViolacaoCamadaFisica(quadro);
        break;
    } // Fim do bloco switch/case
  }

  /*
   * ***************************************************************
   * Metodo: CamadaEnlaceDeDadosTransmissoraEnquadramentoContagemCaracteres
   * Funcao: executa a contagem de caracteres para enquadrar o vetor
   * Parametros: int[] quadro - vetor contendo a mensagem a ser enquadrada
   * Retorno: int[]
   ****************************************************************/

  private int[] CamadaEnlaceDadosTransmissoraEnquadramentoContagemCaracteres(int[] quadro) {
    // Constante contendo a quantidade de dados por quadro
    final int dadosPorQuadro = 3;

    // Calcula-se o total de bytes que compoem o quadro
    int totalBytes = quadro.length * 4;

    // Calcula-se a quantidade de quadros necessaria para o enquadramento via contagem de caracteres
    int quantidadeQuadros = (int) Math.ceil((double) totalBytes / dadosPorQuadro);

    // Cria-se um vetor que armazenara a mensagem enquadrada
    int[] resultado = new int[quantidadeQuadros];

    // Contador de posicao de bytes
    int posicaoByte = 0;

    // Contador de posicao de inteiros
    int posicaoInt = 0; 

    // Inicio do bloco for
    for (int i = 0; i < quantidadeQuadros; i++) {
      // Calcula-se o total de dados restantes para serem computados
      int dadosRestantes = totalBytes - posicaoByte;

      // Calcula-se o total de dados que ira compor o quadro a ser gerado
      int dadosQuadro = Math.min(dadosPorQuadro, dadosRestantes);

      // Inicializa a variavel que armazenara o quadro
      int quadroBits = 0;

      // Insere o cabecalho contando a quantidade de dados armazenados no quadro
      quadroBits |= (dadosQuadro & 255) << 24;

      // Inicio do bloco for
      // Este laco e executado ate inserir todos os elementos necessarios para compor o quadro
      for (int j = 0; j < dadosQuadro; j++) {
        // Calcula-se o deslocamento necessario para inserir o byte no quadro
        int shift = 16 - 8 * j;

        // Obtem-se o byte atual via deslocamento a direita e leitura (&) de bits
        int byteAtual = (quadro[posicaoInt] >> (24 - 8 * (posicaoByte % 4))) & 255;

        // O byte atual e inserido dentro do quadro via deslocamento a esquerda
        quadroBits |= (byteAtual << shift);

        // Incrementa o contador de bytes
        posicaoByte++;
        
        // Inicio do bloco if
        // Se a posicao de bytes atingir um valor divisivel por 4
        if (posicaoByte % 4 == 0) {
          // Isto sinaliza que um inteiro foi computado por completo, pois um inteiro
          // equivale a 4 bytes (32 bits) dentro da memoria
          // Logo, o contador de inteiros e incrementado
          posicaoInt++;
        } // Fim do bloco if
      } // Fim do bloco for

      // O quadro gerado e inserido no vetor
      resultado[i] = quadroBits;
    } // Fim do bloco for

    // O resultado e retornado
    return resultado;
  }

  /*
   * ***************************************************************
   * Metodo: CamadaEnlaceDeDadosTransmissoraEnquadramentoInsercaoBytes
   * Funcao: executa a insercao de bytes para enquadrar o vetor
   * Parametros: int[] quadro - vetor contendo a mensagem a ser enquadrada
   * Retorno: int[]
   ****************************************************************/

  private int[] CamadaEnlaceDadosTransmissoraEnquadramentoInsercaoBytes(int[] quadro) {
    // Caractere de flag: til
    final char FLAG = '~';

    // Caractere de escape: barra vertical
    final char ESC = '|';

    // Vetor que sera usado para delimitar os quadros
    int[] temp = new int[quadro.length * 8];

    // Contador de posicao, usado para percorrer o vetor
    int posicao = 0;

    // Inicio do bloco for
    // O quadro de inteiros e percorrido para que possa ser feita a insercao dos bytes
    // necessarios para computacao
    for (int valor : quadro) {
      // Inicio do bloco for
      // Faremos um deslocamento de 3 a 0 para inserir todos os bytes necessarios dentro do quadro
      for (int i = 3; i >= 0; i--) {
        // Um byte e extraido do valor atual via deslocamento e leitura de bits
        int b = (valor >> (i * 8)) & 0xFF;

        // Converte o byte em caractere
        char c = (char) b;

        // Inicio do bloco if
        // Se o caractere computado for igual a flag ou ao caractere de escape
        if (c == FLAG || c == ESC) {
          // Um caractere de escape e inserido para que o receptor nao o interprete
          // como o inicio/fim do quadro
          temp[posicao++] = ESC;
        } // Fim do bloco if

        // O byte e inserido dentro do vetor temp
        temp[posicao++] = b;
      } // Fim do bloco for
    } // Fim do bloco for

    // Vetor que armazenara o resultado, cujo comprimento sera a quantidade de posicoes contadas
    int[] resultado = new int[posicao];

    // Inicio do bloco for
    for (int i = 0; i < resultado.length; i++) {
      // Os elementos de temp sao transferidos para o vetor resultado
      resultado[i] = temp[i];
    } // Fim do bloco for

    // O resultado e retornado
    return resultado;
  }

  /*
   * ***************************************************************
   * Metodo: CamadaEnlaceDeDadosTransmissoraEnquadramentoInsercaoBits
   * Funcao: executa a insercao de bits para enquadrar o vetor
   * Parametros: int[] quadro - vetor contendo a mensagem a ser enquadrada
   * Retorno: int[]
   ****************************************************************/

  private int[] CamadaEnlaceDadosTransmissoraEnquadramentoInsercaoBits(int[] quadro) {
    // Caractere de FLAG: acento til escrito em hexadecimal
    final int FLAG = 0x7E;

    // Vetor temporario que guardara os bits da mensagem, sendo usado para a insercao 
    // apropriada dos bits
    int[] tempBits = new int[quadro.length * 64];

    // Contador de posicao, usado para percorrer o vetor
    int posicao = 0;

    // Contador de bits 1, usado para demarcar quando inserir um bit 0 apos 5 bits 1 forem contados
    // de forma seguida
    int contadorUns = 0;

    // Inicio do bloco for
    for (int i = 7; i >= 0; i--) {
      // Adiciona-se a flag inicial
      tempBits[posicao++] = (FLAG >> i) & 1;
    } // Fim do bloco for

    // Inicio do bloco for
    // Percorre o quadro para computar cada bit da mensagem dentro do vetor tempBits
    for (int valor : quadro) {
      // Inicio do bloco for
      // 
      for (int i = 3; i >= 0; i--) {
        // O byte e extraido do valor atual
        int b = (valor >> (i * 8)) & 0xFF;

        // Inicio do bloco if
        // Se o byte obtido for diferente de 0
        if (b != 0) {
          // Inicio do bloco for
          // 
          for (int j = 7; j >= 0; j--) {
            // Obtem-se um bit do byte atual
            int bit = (b >> j) & 1;

            // O bit e inserido dentro do vetor
            tempBits[posicao++] |= bit;

            // Inicio do bloco if/else
            // Se o bit obtido for 1
            if (bit == 1) {
              // O contador de uns consecutivos e incrementado
              contadorUns++;

              // Inicio do bloco if
              // Se cinco uns consecutivos forem lidos
              if (contadorUns == 5) {
                // Um bit 0 adicional e inserido dentro do vetor de bits   
                tempBits[posicao++] |= 0;

                // O contador de uns e zerado para recomecar a computacao de bits
                contadorUns = 0;
              } // Fim do bloco if
            }
            else {
              // Em caso contrario, apenas o contador e zerado, pois ele apenas prosseguira caso 
              // cinco bits 1 forem contados consecutivamente
              contadorUns = 0;
            } // Fim do bloco if/else
          } // Fim do bloco for
        } // Fim do bloco if
      } // Fim do bloco for
    } // Fim do bloco for

    // Inicio do bloco for
    for (int i = 7; i >= 0; i--) {
      // Adiciona-se a ultima flag
      tempBits[posicao++] = (FLAG >> i) & 1;
    } // Fim do bloco for

    int tamanho = (posicao + 31) / 32;
    int[] resultado = new int[tamanho];

    // Inicio do bloco for
    // Compacta os bits de volta para int[] para que a codificacao seja realizada
    for (int i = 0; i < posicao; i++) {
      // Obtem-se o bit na posicao indicada pelo contador
      int bit = tempBits[i];

      // Calcula a posicao do inteiro dividindo por 32 
      int posicaoInt = i / 32;

      // Calcula a posicao do bit subtraindo com o resto da divisao de i por 32
      int posicaoBit = 31 - (i % 32);

      // Insere uma parte do bit convertida para decimal a partir do deslocamento a esquerda 
      resultado[posicaoInt] |= (bit << posicaoBit);
    } // Fim do bloco for

    // O resultado e retornado
    return resultado;
  }

  /*
   * ***************************************************************
   * Metodo: CamadaEnlaceDadosTransmissoraEnquadramentoViolacaoCamadaFisica
   * Funcao: executa a violacao de codificacao da camada fisica para enquadrar o vetor
   * Parametros: int[] quadro - vetor contendo a mensagem a ser enquadrada
   * Retorno: int[]
   ****************************************************************/

  private int[] CamadaEnlaceDadosTransmissoraEnquadramentoViolacaoCamadaFisica(int[] quadro) {
    // Caractere de flag
    final int FLAG = 0x7E;

    // Comprimento de caractere de flag
    final int COMP_FLAG = 8;

    // Vetor contendo o padrao de bits
    final int[] FLAG_BITS = new int[COMP_FLAG];

    // Inicio do bloco for
    for (int i = 0; i < COMP_FLAG; i++) {
      // Insere o bit 1 dentro do padrao de bits
      FLAG_BITS[i] = 1; 
    } // Fim do bloco for

    // Total de inteiros contidos dentro do quadro
    int totalInteiros = quadro.length;

    // Vetor que armazenara os bytes do quadro
    int[] todosBytes = new int[totalInteiros * 4];

    // Contador de numero de bytes
    int nb = 0;

    // Inicio do bloco for
    for (int valor : quadro) {
      // Sao computados os bytes do quadro dentro do vetor
      int b3 = (valor >> 24) & 0xFF;
      int b2 = (valor >> 16) & 0xFF;
      int b1 = (valor >> 8) & 0xFF;
      int b0 = valor & 0xFF;
      todosBytes[nb++] = b3;
      todosBytes[nb++] = b2;
      todosBytes[nb++] = b1;
      todosBytes[nb++] = b0;
    } // Fim do bloco for

    // Obtem-se a quantidade de bytes validos atraves da quantidade de bytes contados
    int bytesValidos = nb;

    // Inicio do bloco while
    // Enquanto a quantidade de bytes validos for maior que 0 e obter um byte igual a 0
    while (bytesValidos > 0 && todosBytes[bytesValidos - 1] == 0) {
      // A quantidade de bytes validos e decrementada
      bytesValidos--;
    } // Fim do bloco while

    // Calcula-se a quantidade de bits maximos
    int bitsMaximos = COMP_FLAG + (bytesValidos * 8) + (bytesValidos * 8 / 5 + 2) + COMP_FLAG;

    // Vetor para armazenar os bits
    int[] bufferBits = new int[bitsMaximos];

    // Contador de posicao dentro do vetor
    int posicao = 0;

    // Inicio do bloco for
    for (int i = 7; i >= 0; i--) {
      // Insere a flag inicial dentro do buffer
      bufferBits[posicao++] = (FLAG >> i) & 1;
    } // Fim do bloco for

    // Variavel usada para contar bits 1 consecutivos
    int unsConsecutivos = 0;

    // Inicio do bloco for
    // Sao computados todos os bytes validos obtidos
    for (int i = 0; i < bytesValidos; i++) {
      // Extrai-se uma parte do byte atual
      int b = todosBytes[i] & 0xFF;

      // Inicio do bloco for
      for (int k = 7; k >= 0; k--) {
        // Obtem-se um bit do byte atual
        int bit = (b >> k) & 1;

        // Insere o bit dentro do buffer de bits
        bufferBits[posicao++] = bit;

        // Inicio do bloco if/else
        // Se o bit obtido for igual a 1
        if (bit == 1) {
          // Incrementa a quantidade de bits 1 consecutivos
          unsConsecutivos++;

          // Inicio do bloco if
          // Se cinco bits 1 forem contados consecutivamente
          if (unsConsecutivos == 5) {
            // Insere um bit adicional 0 dentro do buffer  
            bufferBits[posicao++] = 0;

            // Zera o contador para recomecar a contagem
            unsConsecutivos = 0;
          } // Fim do bloco if
        } 
        else {
          // Em caso contrario, apenas zera o contador de uns para recomecar a contagem,
          // pois ele so podera prosseguir caso obter bits 1 consecutivos
          unsConsecutivos = 0;
        } // Fim do bloco if/else
      } // Fim do bloco for
    } // Fim do bloco for

    // Inicio do bloco for
    for (int i = 7; i >= 0; i--) {
      // Insere a flag final dentro do buffer
      bufferBits[posicao++] = (FLAG >> i) & 1;
    } // Fim do bloco for

    // Obtem-se a quantidade de inteiros necessarios para reconstruir o quadro
    int dadosInteiros = (posicao + 31) / 32;

    // Vetor que armazenara o resultado, convertido de volta para inteiros compactados
    int[] resultado = new int[1 + dadosInteiros];

    // O primeiro elemento sera a posicao
    resultado[0] = posicao;

    // Inicio do bloco for
    // Percorre-se ate a ultima posicao registrada
    for (int i = 0; i < posicao; i++) {
      // Obtem-se um bit armazenado no buffer de bits
      int bit = bufferBits[i];

      // Calcula a posicao na sua forma inteira
      int posicaoInt = 1 + (i / 32);

      // Calcula a posicao do bit
      int posicaoBit = 31 - (i % 32);

      // Insere o bit dentro do vetor, compactado para inteiro atraves de deslocamento a esquerda
      resultado[posicaoInt] |= (bit << posicaoBit);
    } // Fim do bloco for

    // O resultado e retornado
    return resultado;
  }
}