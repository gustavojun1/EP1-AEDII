import java.util.Stack; //pilha 
import java.util.Scanner; //pra usar o Scanner

class Main {
  public static void main(String[] args) {
    Grafo <String> grafo = new Grafo <String> (); //criação do grafo
    int numVertices;
    int representacao;
    Scanner leitor= new Scanner(System.in);
    numVertices= leitor.nextInt();
    leitor.nextLine(); //esvazia o buffer do teclado
    String palavra= new String();
    for(int i=0;i<numVertices;i++){ //para cada vertice
      palavra= leitor.nextLine(); //pega a linha inteira referente aquele vertice
      String vetor[]= palavra.split("[;: ]+");//da split quando ve : ou ; ou espaço
      int tamanho= vetor.length; //pega o tamanho do vetor ak ao num de elementos que tem nele
      String comeco= new String(); //cria uma string começo
      comeco= vetor[0]; //comeco recebe a primeira casa do vetor, que é o vertice de partida
      grafo.adicionarVertice(comeco); //adiciono esse vertice no grafo
      //System.out.println("o tamanho do vetor e "+tamanho);
      for(int j=1;j<tamanho;j++){ // do segundo ate o ultimo elemento, sao os vertices destino da aresta q vai sair do vertice comeco
        //System.out.println("to tentando adicionar o "+vetor[j]);
        grafo.adicionarAresta(comeco,vetor[j]); //adiciono uma aresta do começo ate o vertice em questao  
      }
    }
    representacao = leitor.nextInt();
    leitor.nextLine(); //esvazia o buffer do teclado
    Grafo<String> transposta= new Grafo<String> ();
    Stack<String> pilha = new Stack<String>();
    Grafo<String> conectados= new Grafo<String> ();
    pilha= grafo.DFS(); //o DFS devolve os valores ordenados numa pilha
    transposta= grafo.criaTransposta(); //um novo grafo com as arestas inversas
    conectados= transposta.DFSTrans(pilha); //um novo grafo com os componentes fortemente conectados
    conectados.devolveGrafo(representacao); // a representacao escolhida 
    leitor.close(); 
  }
}