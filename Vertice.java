import java.util.ArrayList;

public class Vertice <T>{
  private T dado; //valor presente no vertice
  private ArrayList<Aresta<T>> arestasEntrada; //o vertice tem uma lista de tipo T com as arestas que entram nele
  private ArrayList<Aresta<T>> arestasSaida; //o vertice tem uma lista com as arestas que saem dele
  private Vertice <T> pai;
  
  public enum CorEnum{
    PRETO,
    BRANCO,
    CINZA;
  }

  private CorEnum cor; 
  
  public void setCor(CorEnum cor){
    this.cor= cor;
  }
  public CorEnum getCor(){
    return cor;
  }
  public Vertice(T dado){
    this.dado= dado;
    this.arestasEntrada= new ArrayList<Aresta<T>>(); //ambas as listas começam vazias
    this.arestasSaida= new ArrayList<Aresta<T>>();
    this.cor=Vertice.CorEnum.BRANCO;
  }
  public Vertice <T> getPai(){
    return pai;
  }
  public void setPai( Vertice <T> pai){
    this.pai= pai;
  }
  
  public T getDado(){
    return dado;
  }
  public void setDado(T dado){
    this.dado=dado;
  }
  public void adicionarArestaEntrada(Aresta <T> aresta){
    this.arestasEntrada.add(aresta); //adiciona uma aresta na lista arestasEntrada
  }
  public void adicionarArestaSaida(Aresta <T> aresta){
    this.arestasSaida.add(aresta);// adiciona uma aresta na lista arestasSaida
  }
  public ArrayList<Aresta<T>> getArestasEntrada(){ 
    return arestasEntrada;
  }
  public ArrayList<Aresta<T>> getArestasSaida(){
    return arestasSaida;
  }
}