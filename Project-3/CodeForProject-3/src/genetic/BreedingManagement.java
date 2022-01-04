package genetic;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import static java.util.Collections.list;
import java.util.List;
import java.util.Random;
import java.text.DecimalFormat;
/**
 *
 * @author dfellig
 */
public class BreedingManagement {
    
    //private static DecimalFormat df2 = new DecimalFormat(".####");

    private static  ArrayList<Chromosome> population = new ArrayList();
    private static Integer[] initialConf = {1, 2, 3, 4, 5, 6, 7, 8};
    private static BreedingManagement breedingManagement = new BreedingManagement();

    static Integer[] fisherYatesShuffling(Integer[] arr, int n) {
        Integer[] a = new Integer[n];
        int[] ind = new int[n];
        for (int i = 0; i < n; i++) {
            ind[i] = 0;
        }
        int index;
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            do {
                index = rand.nextInt(n);
            } while (ind[index] != 0);

            ind[index] = 1;
            a[i] = arr[index];
        }
        return a;
    }

    public void createOriginalPopulation(int populationSize) {
        Integer[] randomChromosome;
        for (int i = 0; i < populationSize; ++i) {
            randomChromosome = fisherYatesShuffling(initialConf, 8);
            population.add(new Chromosome(randomChromosome));
        }
        sort();
    }

    public void printPopulation() {
        int popCnt = population.size();
        for (int i = 0; i < popCnt; ++i) {
            System.out.println(population.get(i));   
        }
    }

    private void sort() {
        Collections.sort(population, new ChromosomeComparator());
    }

    public static Chromosome minim = null;
    public static Chromosome minim2 = null;
    
    public static void main(String agrs[]) {
        
        breedingManagement.createOriginalPopulation(30);
        breedingManagement.sort();
        breedingManagement.printPopulation();
        System.out.println(" ");
        System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||| ");
        System.out.println("RESULTADO: ");
        
        
        Chromosome resFinal = breedingManagement.GeneticAlgorithm(population);
            //System.out.println("data: "+ resFinal.toString());
            if(resFinal.fitness == 28){
                System.out.println("*: "+ resFinal.toString());
            }else{
            
                System.out.println("*: chromosoma no encontrado!");
            }
        
        
    }

    //implementacion del algoritmo 
    public Chromosome GeneticAlgorithm(ArrayList<Chromosome> population){
        int contador = 0;
        do {            
            ArrayList<Chromosome> NewPopulation = new ArrayList();
            
            for(int i = 0; i< population.size(); i++){
                Chromosome x = selectParentsToBreed(population);
                Chromosome y = selectParentsToBreed(population);
                
                Chromosome child = Reproduce(x, y);
                
                //se uso i%2 debido a que se puede mutar mas veces
                //generando cromosomas mas variados y puede que con mejor puntaje
                if(i%2==0){
                    child = Mutate(child);
                }
                NewPopulation.add(child);
            }
           
            
           population = NewPopulation;
           
           //Se ordena la poblacion
           Collections.sort(population, new ChromosomeComparator());
           contador++;
           
           //en caso de encontrar un cromosoma con fitness function de 28
           // se retorna el valor del cromosoma
           if(population.get(0).fitness == 28){
               return population.get(0);
           }
        } while (contador<5000);
        
        return population.get(0);
    }
    
    
    //Codigo para reproducir
    public Chromosome Reproduce(Chromosome x, Chromosome y){
        int n = x.getChromosome(x).length;
        int c  = (int)(Math.random() * n) + 1;
        
        //si el numero aleatorio es 8 entonces se reduce un numero
        // x = [1, 3, 7, 5, 4, 2, 6, 8] de la 1 a la posicion 8 seria todo x
        // no se podria unir el cromosoma x con y
        if(c==8){
            c--;
        }
        Integer[] combinacion = new Integer[n];
        System.arraycopy(x.getChromosome(x), 0, combinacion, 0, c);
        System.arraycopy(y.getChromosome(y), c, combinacion, c, n-c);
        return new Chromosome(combinacion);
    }
    
    //Codigo para mutación
    public Chromosome Mutate(Chromosome child){
        Integer[] newValue = child.getChromosome(child);
        List<Integer> values = Arrays.asList(newValue);
        //se genera la posicion aleatoria del 0 al 7  
        int posicionAleatoria = (int)(Math.random() * (newValue.length-1)) + 0;
        //se genera un numero entre el 1 y 8 para los posibles valore a reemplazar un gen
        int genAleatorio = (int)(Math.random() * (newValue.length)) + 1;
        values.set(posicionAleatoria, genAleatorio);
        newValue = (Integer[]) values.toArray();
        return new Chromosome(newValue);
    }

    
    // Roulette Wheel Selection Algorithm
    public Chromosome selectParentsToBreed(ArrayList<Chromosome> population) {
        
        // se puso de 0 a 0.1 debido a que p(i) 
        // retorna un valor similar a 0,01234
        double r = (double)(Math.random() * (double)0.1) + 0;
        double sum = 0;
        int detector = 0;
        for(int i = 0; i< population.size(); i++){
            sum = sum + p(i);
            if(r<sum){
                return population.get(i);
            }
        }
        return null;
    }
    
    //The probability of each chromosome of being selected to mate 
    public static double p(int i){
        int sumatoriaFi = 0;
        Chromosome m;
        for(int j = 0; j< population.size(); j++){
            m = population.get(j);
            int fitness = m.fitness;
            sumatoriaFi = sumatoriaFi + fitness;
        }  
        
        int valorFi = population.get(i).fitness;
        
        double res = (double)valorFi/(double)sumatoriaFi;
        return res;
    }
    
    
    //metodo para imprimir la poblacion
    public void PruebaImpresionValores(){
        
        Chromosome m;
        for(int i = 0; i< population.size(); i++){
            m = population.get(i);
             m.calcFitness();
             
        }   
    }
    
    
}
