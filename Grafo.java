import java.util.ArrayList; //vamos usar listas
import java.util.Stack; //pilha 

public class Grafo <T>{ //tipo genérico pois n especificamos 
  private ArrayList<Vertice<T>> vertices; //uma lista de vertices do tipo Vertice
  private ArrayList<Aresta<T>> arestas; //uma lista de arestas do tipo Aresta
  private Stack <T> pilha; //uma pilha onde vamos armazenar os valores dos vertices
  ArrayList<T> destinos = new ArrayList<>();
  String resultado= new String();
  
  public Grafo(){
    this.vertices= new ArrayList<Vertice<T>>(); //inicializa a lista de vertices
    this.arestas= new ArrayList<Aresta<T>>(); //inicializa a lista de arestas
    pilha= new Stack<>(); //inicializa a pilha
  }

  public void adicionarVertice(T dado){ //recebe o dado do tipo T a ser adicionado
    if(this.vertices.size()==0){ //se o tamanho for 0, é o primeiro vertice
      Vertice <T> newVertice = new Vertice <T>(dado); //chama o construtor de vertice, portanto, entra com o valor
      this.vertices.add(newVertice); //colocamos o vértice no grafo
    }
    else{
      achaVertice(dado);
    }  
  }
  
  public void adicionarAresta(T noInicio, T noFim){ //inicio= de onde sai, fim= pra onde chega
    Vertice <T> inicio = this.achaVertice(noInicio); //procura o vertice inicial
    Vertice <T> fim= this.achaVertice(noFim); //procura o vertice final
    if(fim==null){  //se retornou null, é pq n tinha o vertice e acabamos de criar, então vamos procurar de novo
      fim= this.achaVertice(noFim);
    }
    int numArestas= inicio.getArestasSaida().size(); //pega o numero de arestas do vertice q queremos partir
    if(numArestas==0){ //é a primeira, podemos criar sem medo de repetição
       Aresta <T> aresta= new Aresta<T>(inicio,fim); //cria a aresta com os vertices encontrados
      inicio.adicionarArestaSaida(aresta); //add no vertice inicial uma uma aresta saindo dele
      fim.adicionarArestaEntrada(aresta); //add no vertice destino uma aresta chegando nele, e assim ligamos os 2 vértices
    this.arestas.add(aresta); //adiciona a aresta na lista de arestas
    }
    else{ //senão, ela n é a primeira, vamos checar se n queremos fazer uma ligação ja existente
      int count=0;
      for(int i=0;i<numArestas;i++){ 
        if(inicio.getArestasSaida().get(i).getFim().getDado() != fim.getDado()){ //se o vertice destino da aresta q queremos sair ja é o proprio dado, n fazemos nada. Se for diferente, adicionamos
        count++;
      }
     }
      if(count==numArestas){ //se o count for igual ao numArestas é pq percorreu todos os destinos e o que queremos inserir ainda não está conectado, então podemos ligar
        Aresta <T> aresta= new Aresta<T>(inicio,fim); //cria a aresta com os vertices encontrados
        inicio.adicionarArestaSaida(aresta); //add no vertice inicial uma uma aresta saindo dele
        fim.adicionarArestaEntrada(aresta); //add no vertice destino uma aresta chegando nele, e assim ligamos os 2 vértices
        this.arestas.add(aresta); //adiciona a aresta na lista de arestas
      }
    }
  }
  //Quando adicionamos um elemento ao grafo, ele entra tbm na lista de vertices. Por isso, pra adicionar uma aresta a um  vertice, procuramos esse vertice na lista 
  
  public Vertice <T> achaVertice(T dado){ //procura no grafo (pelo dado) o vertice o qual que queremos adicionar uma aresta. 
    int count=0; //contador
    Vertice <T> vertice= null; //se não encontrar retorna null
    for(int i=0;i<this.vertices.size();i++){ //percorre a lista de vertices até o final (vertices.size())
      if(this.vertices.get(i).getDado().equals(dado)){ //se o dado na posição i da lista de vertices for igual ao dado que          estamos procurando é pq achamos o vertice 
        vertice= this.vertices.get(i); //pega o vertice
        break; //sai do for
      }
      count++;  //incrementa o contador a cada for
    }
    if(count==this.vertices.size()){ //se o count for igual ao tam de vertices é pq percorreu a lista toda e n achou, logo, ele n existe
       Vertice <T> newVertice = new Vertice <T>(dado); //chama o construtor de vertice, portanto, entra com o valor
    this.vertices.add(newVertice); //colocamos o vértice no grafo 
    }
    return vertice; //se encontrar retorna o vertice, senão vai retornar null
  }

  public Stack<T> DFS(){
    for(Vertice<T> item: vertices){ //para cada vértice do grafo
      item.setCor(Vertice.CorEnum.BRANCO);  //pinta ele de branco
    }
    for(Vertice <T> item: vertices){ //pra cada vertice do grafo
      if(item.getCor().equals(Vertice.CorEnum.BRANCO)){ //se sua cor for branca, ele n foi visitado
        DfsVisit(item); //entao o visitamos
      }
    }
    return pilha;
  }

  public void DfsVisit(Vertice <T> u){
    u.setCor(Vertice.CorEnum.CINZA); //pinta o que acabamos de visitar de cinza
    for(int i=0;i<u.getArestasSaida().size();i++){ //ve todos os vertices adjacentes de u, ou seja, todos os destinos das arestas que saem dele
     Vertice <T> aux = u.getArestasSaida().get(i).getFim(); //cria um auxiliar que recebe o vertice adjacente (so para facilitar a leitura)
      if(aux.getCor().equals(Vertice.CorEnum.BRANCO)){ //pega o aux e ve se sua cor é branca
        DfsVisit(aux); //se for, ele ainda n foi visitado, então visita
      } 
    } 
    u.setCor(Vertice.CorEnum.PRETO); //depois que todos os adjacentes a ele foram visitados, pinta ele de preto
    T auxiliar= u.getDado(); //cria um auxiliar que vai armazenar seu conteudo
    pilha.add(auxiliar); //e adiciona ele na pilha 
  } 

  public Grafo<T> criaTransposta(){
    Grafo <T> trans = new Grafo <T> (); //criação do grafo
    for(Vertice<T> item: vertices){ //para cada vértice do grafo
      trans.adicionarVertice(item.getDado()); //adiciona os vertices no novo grafo trans
    }
    for(Vertice<T> item: vertices){ //pega todos os vertices do grafo original
      int sai= item.getArestasSaida().size(); //guarda o numero de arestas de saida do vertice
      for(int i=0;i<sai;i++){ //vai passar por todas as arestas de saida
        Vertice <T> aux= item.getArestasSaida().get(i).getFim(); //aux recebe o vertice destino da aresta em que estamos
        trans.adicionarAresta(aux.getDado(),item.getDado()); //adiciona na transposta a ligação do antigo destino para onde estamos, invertendo o sentido
      }
    }
    return trans; //retorna o grafo transposto
  }

  public Grafo<String> DFSTrans(Stack<T> pilha){ //entramos com a pilha do grafo original
     Grafo <String> componentes = new Grafo<String> (); //cria um novo grafo
    Stack<T> pilhaAux = new Stack<T>(); //criamos uma pilha auxiliar
    pilhaAux= pilha; //armazenamos nessa pilha a pilha do grafo original
    int cont=0; //contador dos componentes fortemente conectados
    String verfortc = new String(); //inicializa a String
    ArrayList <String> vetor = new ArrayList<>();
    for(Vertice<T> item: vertices){ //para cada vertice do grafo
      item.setCor(Vertice.CorEnum.BRANCO); //pinta de branco
    }
    while(!pilhaAux.empty()){ //enquanto a pilha nao estiver vazia
      T aux= pilha.pop(); // aux recebe o elemento do topo da pilha
      Vertice<T> item= achaVertice(aux); //procura um vertice com o mesmo conteudo de aux que pegamos 
      if(item.getCor().equals(Vertice.CorEnum.BRANCO)){ //se a cor desse vertice for branca
        resultado=""; //reseta a string resultado
        verfortc= DfsVisitTrans(item); //entao o visitamos e é retornada uma string com os componentes fortemente conectados a ele
         Vertice<String> auxiliar1= componentes.achaVertice(verfortc); //cria um vertice auxiliar1 que procura no grafo o vertice fortemente conectado que queremos inserir
        auxiliar1= componentes.achaVertice(verfortc); //procura novamente, pois caso nao havia antes, agora ele ja foi criado e podemos usa-lo
        if(auxiliar1==null){ //se mesmo apos duas b
          componentes.adicionarVertice(verfortc); //adiciona o vertice das componentes conectadas
        }
        vetor.add(verfortc); //adiciono o ocmponente no ArrayList chamado vetor
        //destinos.clear();
        cont++; //mais um conjunto de componentes
      }
    }
    if(cont==1){ //se so tem um conjunto de componentes, é o proprio grafo, logo ele é fortemente conectado
      System.out.println("Sim");
    }
    else{
      System.out.println("Nao");
    }
    System.out.println(cont); //printa o numero de componentes
      for(Vertice<T> item: vertices){ //percorre todos os vertices
        for(int i=0;i<vetor.size();i++){
          if(vetor.get(i).contains((String)item.getDado())){ //se o vetor contem o dado
            int sai= item.getArestasSaida().size(); //guarda o numero de arestas de saida do vertice
            for(int j=0;j<sai;j++){
              String destino= (String)item.getArestasSaida().get(j).getFim().getDado(); //destino recebe o dado destino
              for(int k=0;k<vetor.size();k++){
                if((vetor.get(k).contains(destino))&&(!vetor.get(k).contains((String)item.getDado()))){ //se o vetor contem o destino e se o vetor nao contem o proprio item
             componentes.adicionarAresta(vetor.get(k),vetor.get(i)); //adicionamos no grafo componentes uma aresta do vertice na posicao k do vetor ate o vertice na posicao i
                }
              }
            }
          }
        }
      }
    return componentes; //retorna esse grafo
  }

  public String DfsVisitTrans(Vertice <T> u){
    u.setCor(Vertice.CorEnum.CINZA); //pinta o que acabamos de visitar para cinza
    for(int i=0;i<u.getArestasSaida().size();i++){ //ve todos os vertices
      Vertice <T> aux= u.getArestasSaida().get(i).getFim(); //cria um auxiliar que recebe os vertices adjacentes de u, ou seja, todos os destinos das arestas que saem dele 
      if(aux.getCor().equals(Vertice.CorEnum.BRANCO)){ //ve se a cor do aux é branca
        DfsVisitTrans(aux); //se for, ele ainda n foi visitado, entao visita
      }
    }
    u.setCor(Vertice.CorEnum.PRETO); //depois q todos os adjacentes foram visitados, pinta ele de preto
    int entra= u.getArestasEntrada().size(); //entra vai receber o numero de arestas de entrada do vertice u
    for(int i=0;i<entra;i++){ //vai passar por todas as arestas de entrada
      Vertice <T> ent= u.getArestasEntrada().get(i).getInicio(); //pega a origem da aresta que ta entrando nele
      if(ent.getCor().equals(Vertice.CorEnum.BRANCO)){ //ve se a cor dessa aresta é branca
        destinos.add(ent.getDado()); //coloca esse dado no array destinos
      }
    }
    T valor = u.getDado(); //pega seu dado
    resultado= resultado + valor; //adiciona na string pois sao fortemente conectados
    return resultado; //retorna os componentes juntos
  }

    public void devolveGrafo(int representacao){
      for(Vertice<T> item : vertices){
        System.out.print(item.getDado()+" ");
      }
      System.out.println("");
      if(representacao == 1 ){ //colecao de listas adjacentes
        for(int i=0;i<vertices.size();i++){ //percorre cada vertices do grafo
          System.out.print(vertices.get(i).getDado()+":"); //printa o primeiro seguido de :  (obs: usamos print pois ele n pula linha)
          int sai= vertices.get(i).getArestasSaida().size();
          for(int j=0; j<sai ;j++){
            System.out.print(" "+vertices.get(i).getArestasSaida().get(j).getFim().getDado()+";"); //printa  seus adjacentes seguidos de ;
            /* aqui acima, estamos pegando o dado do vertice que esta no fim da aresta de saida j do vertice i*/
          }
          System.out.println(" "); //println vazio para pular para a proxima linha   
        }   
      }
      if(representacao == 2){ //matriz
        int numvert= vertices.size(); //pega o numero de vertices do grafo
        int matriz[][] = new int [numvert][numvert]; //cria uma matriz quadrada do tamanho do numero de vertices
        ArrayList <T> listaAux = new ArrayList<>(); //cria uma lista auxiliar que sera usada para comparação
          for(int i=0;i<numvert;i++){
            listaAux.add(i,vertices.get(i).getDado()); //insere na lista auxiliar os valores dos vertices do grafo
          }
          for(int i=0;i<numvert;i++){ //para cada vertice
            int sai= vertices.get(i).getArestasSaida().size(); //sai recebe o numero de arestas de saida que o vertice i tem
            for(int k=0; k<sai ;k++){ //percorre cada uma dessas arestas
              T conteudo= vertices.get(i).getArestasSaida().get(k).getFim().getDado(); //conteudo recebe o valor do vertice que esta no final da aresta k saida do vertice i 
              for(int j=0;j<numvert;j++){      
              /*vai analisar cada aresta de saida de cada vertice e comparar com cada outro vertice*/
                if(conteudo.equals(listaAux.get(j))){ //se o conteudo for igual ao valor na posicao j da lista auxiliar é pq ha uma aresta conectando o vertice i que analizamos com o vertice j
                  matriz[i][j]=1; //coloca o valor 1 na matriz mostrando que ha conexao 
                }
                else{ //se nao for igual, coloca o valor 0
                  if(matriz[i][j]!=1){ //insere 0 apenas se não tiver 1 quando passamos anteriormente por essa posição, pois não queremos sobrescrever
                  matriz[i][j]=0;
                    }
                }
                if(sai==0){ //se o vertice nao possui arestas de saida, colocamos 0 em toda sua linha da matriz pois ele nao se conecta a nenhum outro vertice
                  if(matriz[i][j]!=1){
                matriz[i][j]=0;
                    }
              }
            }   
          }
        }
        System.out.print("\t"); //da um tab para facilitar a visualizacao
        for(int i=0;i<numvert;i++){
          System.out.print(vertices.get(i).getDado()+" "); //para cada vertice, printa seu valor
          }  
        System.out.println(""); //pula linha
        for(int i=0;i<numvert;i++){
          System.out.print(vertices.get(i).getDado()+"\t"); //printa cada valor da matriz referente a linha em que estamos
          for(int j=0;j<numvert;j++){
            System.out.print(matriz[i][j]+"\t"); //printa o valor na posicao referente
          }
          System.out.println(""); //pula linha
        }
      }
    }   
} 