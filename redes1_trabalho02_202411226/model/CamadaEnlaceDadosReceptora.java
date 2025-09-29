/* ***************************************************************
* Autor............: Diogo Oliveira de Sousa
* Matricula........: 202411226
* Inicio...........: 13/09/2025
* Ultima alteracao.: 27/09/2025
* Nome.............: CamadaEnlaceDadosReceptora
* Funcao...........: Esta classe tem como objetivo gerenciar os eventos e operacoes da camada de enlace de dados
                     da maquina receptora, que realizara o desenquadramento da mensagem recebida antes de envia-la
                     para a camada de aplicacao.
                     
*************************************************************** */

package model;

import controller.TelaPrincipalController;

public class CamadaEnlaceDadosReceptora {
  /* Atributos */

  // Vetor de inteiros contendo o quadro desenquadrado
  private int[] quadroDesenquadrado;

  /* Metodos de instanciacao/execucao */

  /*
   * ***************************************************************
   * Metodo: CamadaEnlaceDeDadosReceptora
   * Funcao: executa as operacoes da camada de enlace de dados receptora
   * Parametros: int[] quadro - vetor contendo a mensagem a ser desenquadrada
   * Retorno: void
   ****************************************************************/

  protected void CamadaEnlaceDeDadosReceptora(int[] quadro) {
    // Realiza o desenquadramento da mensagem
    CamadaEnlaceDeDadosReceptoraEnquadramento(quadro);

    // Inicializa a camada de aplicacao receptora, que convertera para String o quadro desenquadrado
    CamadaAplicacaoReceptora camadaAplicacaoReceptora = new CamadaAplicacaoReceptora();
    camadaAplicacaoReceptora.CamadaDeAplicacaoReceptora(quadroDesenquadrado);
  }

  /*
   * ***************************************************************
   * Metodo: CamadaEnlaceDeDadosReceptoraEnquadramento
   * Funcao: executa o desenquadramento da mensagem recebida
   * Parametros: int[] quadro - vetor contendo a mensagem a ser desenquadrada
   * Retorno: void
   ****************************************************************/

  private void CamadaEnlaceDeDadosReceptoraEnquadramento(int[] quadro) {
    // Obtem-se o tipo de enquadramento selecionado pelo usuario
    int tipoEnquadramento = TelaPrincipalController.controller.getTipoEnquadramento();

    // Inicio do bloco switch/case
    // O desenquadramento sera realizado conforme o algoritmo de enquadramento selecionado
    switch (tipoEnquadramento) {
      case 0: // Executa desenquadramento via contagem de caracteres
        quadroDesenquadrado = CamadaEnlaceDadosReceptoraEnquadramentoContagemCaracteres(quadro);
        break;

      case 1: // Executa desenquadramento via insercao de bytes
        quadroDesenquadrado = CamadaEnlaceDadosReceptoraEnquadramentoInsercaoBytes(quadro);
        break;

      case 2: // Executa desenquadramento via insercao de bits
        quadroDesenquadrado = CamadaEnlaceDadosReceptoraEnquadramentoInsercaoBits(quadro);
        break;

      case 3: // Executa desenquadramento via violacao da camada fisica
        quadroDesenquadrado = CamadaEnlaceDadosReceptoraEnquadramentoViolacaoCamadaFisica(quadro);
        break;
    } // Fim do bloco switch/case
  }

  /*
   * ***************************************************************
   * Metodo: CamadaEnlaceDeDadosReceptoraEnquadramentoContagemCaracteres
   * Funcao: executa o desenquadramento da mensagem recebida via contagem de caracteres
   * Parametros: int[] quadro - vetor contendo a mensagem a ser desenquadrada
   * Retorno: int[]
   ****************************************************************/

  private int[] CamadaEnlaceDadosReceptoraEnquadramentoContagemCaracteres(int[] quadro) {
    // Inteiro contendo o total de dados a serem recuperados
    int totalDados = 0;
    
    // Inicio do bloco for
    // Sao acessados todos os quadros do fluxo de dados
    for (int q : quadro) {
      // Obtem-se o cabecalho contendo a quantidade de caracteres dentro do quadro obtido
      int cabecalho = (q >> 24) & 0xFF;

      // Soma-se o total de dados junto com o cabecalho
      totalDados += cabecalho;
    } // Fim do bloco for

    // Cria-se um vetor para armazenar o fluxo desenquadrado
    int[] resultado = new int[totalDados];

    // Contador de posicao, para percorrer o vetor
    int posicaoByte = 0;

    // Inicio do bloco for
    // Sao acessados todos os quadro do fluxo de dados
    for (int q : quadro) {
      // Obtem-se o cabecalho contendo a quantidade de elementos dentro do quadro
      int cabecalho = (q >> 24) & 0xFF;

      // Inicio do bloco for
      // Sao obtidos todos os elementos que fazem parte do quadro
      for (int j = 0; j < cabecalho; j++) {
        // Calcula-se o deslocamento necessario para a obtencao dos dados
        int shift = 16 - 8 * j;

        // O inteiro correspondente e obtido e armazenado dentro do vetor
        resultado[posicaoByte++] = (q >> shift) & 0xFF;
      } // Fim do bloco for
    } // Fim do bloco for

    // O resultado e retornado
    return resultado;
  }

  /*
   * ***************************************************************
   * Metodo: CamadaEnlaceDeDadosReceptoraEnquadramentoInsercaoBytes
   * Funcao: executa o desenquadramento da mensagem recebida via insercao de bytes
   * Parametros: int[] quadro - vetor contendo a mensagem a ser desenquadrada
   * Retorno: int[]
   ****************************************************************/

  private int[] CamadaEnlaceDadosReceptoraEnquadramentoInsercaoBytes(int[] quadro) {
    // Caractere de escape 
    final char ESC = '|';

    // Vetor temporario
    int[] temp = new int[quadro.length];

    // Contador de posicao, usado para percorrer o vetor
    int posicao = 0;

    // Boolean que indica se ha um escape ou nao
    boolean escapando = false;

    // Inicio do bloco for
    // Percorremos todos os elementos do quadro
    for (int b : quadro) {
      // O inteiro obtido e convertido para caractere
      char c = (char) b;

      // Inicio do bloco if/else if/else
      // Se houver um escape
      if (escapando) {
        // Adicionamos o inteiro ao vetor temporario
        temp[posicao++] = b;

        // Desativamos a ocorrencia de escape
        escapando = false;
      }
      else if (c == ESC) {
        // Ativa a ocorrencia de escape caso obtivermos o caractere de escape em algum momento
        // Dessa forma, ignoramos o caractere de escape
        escapando = true;
      }
      else { 
        // Em caso contrario, apenas inserimos o inteiro dentro do vetor temp
        temp[posicao++] = b;
      } // Fim do bloco if/else if/else
    } // Fim do bloco for

    // Calcula-se a quantidade de inteiros necessarios para a restauracao do quadro original
    int qtdInteiros = (int) Math.ceil(posicao / 4.0);

    // Vetor de inteiros que armazenara o resultado do desenquadramento
    int[] resultado = new int[qtdInteiros];

    // Contador de posicao de inteiros
    int posicaoInt = 0;

    // Inicio do bloco for
    for (int i = 0; i < posicao; i += 4) {
      // Variavel que armazenara o valor obtido
      int valor = 0;

      // Inicio do bloco for
      for (int j = 0; j < 4 && (i + j) < posicao; j++) {
        // Obtem-se o valor via deslocamento e leitura de bits 
        // compactando-o em valor inteiro
        valor |= (temp[i + j] & 0xFF) << (8 * (3 - j));
      } // Fim do bloco for

      // O valor e armazenado dentro do vetor
      resultado[posicaoInt++] = valor; 
    } // Fim do bloco for

    // O resultado e retornado
    return resultado;
  }

  /*
   * ***************************************************************
   * Metodo: CamadaEnlaceDeDadosReceptoraEnquadramentoInsercaoBits
   * Funcao: executa o desenquadramento da mensagem recebida via insercao de bits
   * Parametros: int[] quadro - vetor contendo a mensagem a ser desenquadrada
   * Retorno: int[]
   ****************************************************************/

  private int[] CamadaEnlaceDadosReceptoraEnquadramentoInsercaoBits(int[] quadro) {
    // Caractere de flag
    final int FLAG = 0x7E;

    // Vetor que armazenara temporariamente o fluxo de bits que ira compor a mensagem
    int[] tempBits = new int[quadro.length * 32];

    // Contador de posicao, usado para percorrer o vetor
    int posicao = 0;

    // Inicio do bloco for
    for (int valor : quadro) {
      // Inicio do bloco for
      for (int i = 31; i >= 0; i--) {
        // O bit e lido e armazenado dentro do vetor temporario
        tempBits[posicao++] = (valor >> i) & 1;
      } // Fim do bloco for
    } // Fim do bloco for

    // Variaveis que serao usadas para localizar as flags iniciais e finais, respectivamente
    int inicio = -1;
    int fim = -1;

    // Inicio do bloco for
    // Usamos este laco para localizar o inicio e o fim do quadro
    for (int i = 0; i <= posicao - 8; i++) {
      // Variavel que armazenara o valor obtido
      int valor = 0;

      // Inicio do bloco for
      for (int j = 0; j < 8; j++) {
        // Obtem-se o valor via deslocamento a esquerda e insercao dos bits armazenados
        valor = (valor << 1) | tempBits[i + j];
      } // Fim do bloco for

      // Inicio do bloco if
      // Se o valor for igual a flag
      if (valor == FLAG) {
        // Se o inicio nao tiver sido obtido
        if (inicio == -1) {
          // Atualizamos a variavel de inicio
          inicio = i + 8;
        }
        else {
          // Caso contario, trata-se da flag de fim, atualizando sua posicao
          // e interrompendo o laco
          fim = i;
          break;
        } // Fim do bloco if/else
      } // Fim do bloco if
    } // Fim do bloco for

    // Vetor inteiro que armazenara os dados obtidos
    int[] dadosBits = new int[fim - inicio];

    // Variavel que contara ate cinco bits 1 de maneira consecutiva (11111)
    int contadorUns = 0;

    // Contador de posicao para percorrer o vetor dadosBits
    int posicaoDados = 0;

    // Inicio do bloco for
    // Percorremos todo o quadro para inserirmos os bits necessarios
    for (int i = inicio; i < fim; i++) {
      // Obtemos um bit armazenado no vetor temporario
      int bit = tempBits[i];

      // Inicio do bloco if/else
      // Se o bit for igual a 1
      if (bit == 1) {
        // Incrementamos a quantidade de uns consecutivos
        contadorUns++;

        // Inserimos um bit 1 dentro dos dados
        dadosBits[posicaoDados++] = 1;

        // Inicio do bloco if
        // Se cinco bits 1 tiverem sido contados de maneira consecutiva
        if (contadorUns == 5) {
          // Incrementamos o contador do laco, para que possamos ignorar o bit adicional
          i++;

          // Reseta o contador de bits 1, para que a contagem seja recomecada
          contadorUns = 0; 
        } // Fim do bloco if
      }
      else {
        // Em caso contrario, o contador de bits 1 e resetado, pois ele so progredira caso 
        // bits 1 sejam contados de forma consecutiva
        contadorUns = 0;

        // Insere o bit 0 dentro do vetor
        dadosBits[posicaoDados++] = 0;
      } // Fim do bloco if/else
    } // Fim do bloco for

    // Calcula-se o tamanho do vetor contendo o quadro convertido para inteiro
    int tamanho = (posicaoDados + 31) / 32;

    // Vetor que armazenara o fluxo compactado em inteiros
    int[] resultado = new int[tamanho];

    // Inicio do bloco for
    // Percorremos ate a ultima posicao de bits obtida
    for (int i = 0; i < posicaoDados; i++) {
      // Obtem-se um bit dentro do vetor
      int bit = dadosBits[i];

      // Calcula a posicao em sua forma inteira
      int posicaoInt = i / 32;

      // Calcula a posicao em sua forma bit
      int posicaoBit = 31 - (i % 32);

      // Insere o bit compactado para inteiro via deslocamento a esquerda
      resultado[posicaoInt] |= (bit << posicaoBit);
    } // Fim do bloco for

    // O resultado e retornado
    return resultado;
  }

  /*
   * ***************************************************************
   * Metodo: CamadaEnlaceDeDadosReceptoraEnquadramentoInsercaoBytes
   * Funcao: executa o desenquadramento da mensagem recebida via violacao
   *         da codificacao da camada fisica
   * Parametros: int[] quadro - vetor contendo a mensagem a ser desenquadrada
   * Retorno: int[]
   ****************************************************************/

  private int[] CamadaEnlaceDadosReceptoraEnquadramentoViolacaoCamadaFisica(int[] quadro) {
    // Caractere de flag
    final int FLAG = 0x7E;

    // Comprimento do padrao de flag
    final int COMP_FLAG = 8;

    // Obtem-se a quantidade de bits a partir do primeiro elemento do quadro
    int quantidadeBits = quadro[0];

    // Calcula-se o total de inteiros dentro do quadro
    int totalInteiros = quadro.length - 1;

    // Vetor usado para armazenar os bits que compoem a mensagem
    int[] bits = new int[quantidadeBits];

    // Contador de posicao, para percorrer o vetor
    int posicao = 0;

    // Inicio do bloco for
    for (int i = 0; i < totalInteiros && posicao < quantidadeBits; i++) {
      // Obtem-se um certo valor do quadro (cuja posicao e incrementada para desconsiderar o cabecalho)
      int valor = quadro[i + 1];

      // Inicio do bloco for
      for (int b = 31; b >= 0 && posicao < quantidadeBits; b--) {
        // Obtem-se o bit do valor via mascara e deslocamento
        bits[posicao++] = (valor >> b) & 1;
      } // Fim do bloco for
    } // Fim do bloco for

    // Flags usadas para demarcar o inicio e o fim do quadro
    int inicio = -1;
    int fim = -1;

    // Inicio do bloco for
    // Usamos este laco para delimitar o inicio do quadro
    for (int i = 0; i <= quantidadeBits - COMP_FLAG; i++) {
      // Variavel que armazenara um certo valor dentro do quadro
      int v = 0;

      // Inicio do bloco for
      for (int j = 0; j < COMP_FLAG; j++) {
        // Obtemos o valor via deslocamento a esquerda e insercao de bits
        v = (v << 1) | bits[i + j];
      } // Fim do bloco fors

      // Inicio do bloco if
      // Se o valor obtido for correspondente a flag
      if (v == FLAG) {
        // Atualizamos o inicio do quadro e interrompemos o laco
        inicio = i + COMP_FLAG;
        break;
      } // Fim do bloco if
    } // Fim do bloco for

    // Inicio do bloco for
    // Usamos este laco para delimitar o fim do quadro
    for (int i = inicio; i <= quantidadeBits - COMP_FLAG; i++) {
      // Variavel que armazenara um certo valor dentro do quadro
      int v = 0;

      // Inicio do bloco for
      for (int j = 0; j < COMP_FLAG; j++) {
        // Obtemos o valor via deslocamento a esquerda e insercao de bits
        v = (v << 1) | bits[i + j];
      } // Fim do bloco for

      // Inicio do bloco if
      // Se o valor obtido for correspondente a flag
      if (v == FLAG) {
        // Atualizamos o fim do quadro e interrompemos o laco
        fim = i;
        break;
      } // Fim do bloco if
    } // Fim do bloco for

    // Inteiro que calculara a quantidade maxima de bits existente
    // entre o inicio e o fim do quadro
    int quantMaxBits = fim - inicio;

    // Vetor que armazenara os bits a serem computados
    int[] dadosBits = new int[quantMaxBits];

    // Contador de bits armazenados
    int db = 0;

    // Contador de uns consecutivos
    int unsConsecutivos = 0;

    // Inicio do bloco for
    // Percorremos do inicio ao fim do quadro para obtermos os bits necessarios
    for (int i = inicio; i < fim; i++) {
      // Obtem-se um bit do vetor de bits
      int bit = bits[i];

      // Inicio do bloco if/else
      // Se o bit obtido for 1
      if (bit == 1) {
        // Incrementamos a quantidade de uns consecutivos
        unsConsecutivos++;

        // Inserimos o bit 1 dentro do vetor dadosBits
        dadosBits[db++] = 1;

        // Inicio do bloco if
        // Se conseguirmos obter cinco uns consecutivos
        if (unsConsecutivos == 5) {
          // Incrementa o contador do laco, para que possamos ignorar o bit adicional
          i++;

          // Resetamos o contador de bits 1, para que a contagem possa recomecar
          unsConsecutivos = 0;
        } // Fim do bloco if
      }
      else {
        // Em caso contrario, apenas resetamos o contador de bits 1, pois ele so progredira
        // caso bits 1 sejam obtidos de forma consecutiva
        unsConsecutivos = 0;

        // Inserimos o bit 0 dentro do vetor dadosBits
        dadosBits[db++] = 0;
      } // Fim do bloco if/else
    } // Fim do bloco for

    // Calcula-se a quantidade de bytes que compoem a mensagem
    int quantidadeBytes = (db + 7) / 8;

    // Vetor que armazenara os bytes necessarios para a composicao da mensagem
    int[] bytes = new int[quantidadeBytes];

    // Inicio do bloco for
    // Percorremos o laco ate obtermos a quantidade de bytes necessaria
    for (int i = 0; i < quantidadeBytes; i++) {
      // Variavl que armazenara o valor a ser inserido dentro do vetor de bytes
      int valor = 0;

      // Inicio do bloco for
      for (int j = 0; j < 8; j++) {
        // Calculamos a posicao do bit a ser obtido
        int indiceBit = i * 8 + j;

        // Variavel que armazenara o bit a ser obtido
        int bit = 0;

        // Inicio do bloco if
        if (indiceBit < db) {
          // Obtemos o bit localizado dentro do vetor
          bit = dadosBits[indiceBit];
        } // Fim do bloco if

        // O valor e obtido via deslocamento a esquerda e insercao do bit
        valor = (valor << 1) | bit;
      } // Fim do bloco for

      // O valor e inserido dentro do vetor de bytes via leitura (&) de bits
      bytes[i] = valor & 0xFF;
    }

    // Calculamos a quantidade de inteiros necessarios para recuperarmos a mensagem transmitida
    int inteirosNecessarios = (quantidadeBytes + 3) / 4;

    // Vetor que armazenara o quadro compactado em inteiros
    int[] resultado = new int[inteirosNecessarios];

    // Contador de posicao, para percorrer o vetor
    int pos = 0;

    // Inicio do bloco for
    // Executamos o laco ate obtivermos todos os inteiros necessarios para a recuperacao
    // da mensagem transmitida
    for (int i = 0; i < inteirosNecessarios; i++) {
      // Variavel que armazenara o vsl
      int valor = 0;

      // Inicio do bloco for
      for (int j = 0; j < 4; j++) {
        // Variavel que armazenara o byte obtido
        int b = 0;

        // Inicio do bloco if
        // Se a posicao for menor que a quantidade de bytes
        if (pos < quantidadeBytes) {
          // Obtem-se o byte localizado naquela posicao
          b = bytes[pos++];
        } // Fim do bloco if

        // Obtem-se o valor compactando o byte para inteiro
        valor |= (b & 0xFF) << (8 * (3 - j));
      } // Fim do bloco for

      // O valor e armazenado dentro do vetor
      resultado[i] = valor;
    } // Fim do bloco for

    // O resultado e retornado
    return resultado;
  }
}