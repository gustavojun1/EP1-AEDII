public class Aresta <T>{
  private Vertice <T> inicio; //um vertice q é o inicio da aresta
  private Vertice <T> fim; //um vertice que é o fim da aresta
  public Aresta(Vertice<T> inicio, Vertice<T> fim){ //Construtor
    this.inicio=inicio;
    this.fim=fim;
  }
  public Vertice <T> getInicio(){
    return inicio;
  }
  public Vertice <T> getFim(){
    return fim;
  }
  public void setInicio(Vertice <T> inicio){
    this.inicio=inicio;
  }
  public void setFim(Vertice <T> fim){
    this.fim=fim;
  }
}