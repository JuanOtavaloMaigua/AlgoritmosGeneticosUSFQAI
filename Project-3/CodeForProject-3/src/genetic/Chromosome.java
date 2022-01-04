package genetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import static javafx.scene.input.KeyCode.T;


public class Chromosome {

    private Integer[] chromosome;
    int fitness ;

    public Chromosome(Integer[] chromosome) {
        this.chromosome = chromosome;
        setFitness(calcFitness()); // se va cambiando los valores del fitness function 
    }

    public String toString() {
        //setFitness(calcFitness());
        return "Fitness Value: "+ fitness + " " + "Cromosoma:" +Arrays.toString(chromosome);
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(Integer value) {
        fitness = value;
    }

    public boolean equals(Chromosome c) {
        return chromosome.equals(c.chromosome);
    }
    
    //Se creo este metodo para obtner el valor de un chromosoma en especifico
    public Integer[] getChromosome(Chromosome data){
       return data.chromosome;
    }
    

    // Rellenar con el codigo apropiado 
    // fitness function
    public int calcFitness() {
        
        
        //Obtiene todos los cromosomas como listas
        List<Integer> datos = Arrays.asList(chromosome);

        Map<Integer, ArrayList<Integer>> contenido = new HashMap<Integer, ArrayList<Integer>>();
        ArrayList<Integer> numeros = new ArrayList<Integer>();
        
        
        
        int fila = 1;
        int conColum = 1;
        
        //Separa Los chromosomas de la lista extraida
       for(int i = 0; i< datos.size(); i++){
            
            int maxim = datos.get(i);
            numeros.add(maxim);
            
            if((i+1)%8==0){
               contenido.put(fila, numeros);
               fila++;
            }
        }
       //
       
       int valor = 0;

       
       for(int i = 1; i<=contenido.size();i++){
           int calculo = 1; //Dirferencia de valores de genes en cada columna
           int result = 9; //numero de columnas inicial
           int sumaTotal = 0; // suma el numero de 
           for(int j = 0; j<contenido.get(i).size();j++){
               
               int posicionInicial = contenido.get(i).get(j); // valor cada gen en cada columna

               result--; // se reduce el numero de columnas
               calculo = 1; // cada vez que cambia de columna el valor sigue siendo 1
               int parcialResult = 0; // resultado parcial despues del analisis de cada columna
               int xEcontrados = 1; // almacena el numero de pares de reina encontrados
               
                for(int k = j+1;k< contenido.get(i).size();k++){
                    int posicionFinal = contenido.get(i).get(k); // valores de las columnas mas analizadas
                    int diferencia = posicionInicial - posicionFinal; //
                   if(diferencia == calculo || diferencia == -calculo || posicionFinal == posicionInicial){
                        xEcontrados++; // si se encuentra reinas que se atacan
                 }
                  
                   calculo++; // cambia la calumna
               }
               parcialResult = result - xEcontrados; // resultado del fitness function en cada columna
               sumaTotal = sumaTotal + parcialResult; // valor del fitness function en cada cromosoma
           }
           return sumaTotal;
       }
        return 5; 
    }
}

class ChromosomeComparator implements Comparator<Chromosome> {

    public int compare(Chromosome o1, Chromosome o2) {
        return (o2.getFitness() - o1.getFitness());
    }
}
