package com.trumam;

import java.util.*;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;

public class CreateCSV {
    String path;

    /**
     * Constructor de la clase CreateCSV
     * @param direccion
     */
    public CreateCSV(String direccion){
        this.setPath(direccion);
    }

    /**
     * Método get para el atributo path
     */
    public String getPath(){
        return path;
    }

    /**
     * Método set para el atributo path
     * @param direccion
     */
    public void setPath(String direccion){
        this.path = direccion;
    }

    /**
     * Método que genera el archivo CSV a partir de un objeto de tipo Movie
     * @param pelicula
     * @param nombreArchivo
     */
    public void crearCSV(Movie pelicula, String nombreArchivo){
        String delimitador=",";
        final String salto = "\n";
        try {
            FileWriter fw = new FileWriter(nombreArchivo);

            fw.append(pelicula.getTitle()).append(delimitador);
            fw.append(pelicula.getTag()).append(delimitador);
            fw.append(pelicula.getSynopsis()).append(salto);

            fw.flush();
            fw.close();
        } catch (IOException e) {
            // Error al crear el archivo
            e.printStackTrace();
        }
    }

    /**
     * Método que genera un archivo CSV a partir de un Array de tipo Movie
     * @param arrayPelicula
     * @param nombreArchivo
     */
    public void crearCSV(ArrayList<Movie> arrayPelicula, String nombreArchivo){
        String delimitador=",";
        int contador=arrayPelicula.size();
        final String salto = "\n";

        try {
            FileWriter fw = new FileWriter(nombreArchivo);
            for(int i=0; i<contador; i++){
                Movie currentMovie = new Movie(arrayPelicula.get(i));
                //Movie currentMovie = new Movie(arrayPelicula.get(i).getTitle(), arrayPelicula.get(i).getTag(),
                 //       arrayPelicula.get(i).getSynopsis());

                fw.append(currentMovie.getTitle()).append(delimitador);
                fw.append(currentMovie.getTag()).append(delimitador);
                fw.append(currentMovie.getSynopsis()).append(salto);
                fw.flush();
                //fw.close();
            }

            fw.flush();
            fw.close();
        } catch (IOException e) {
            // Error al crear el archivo
            System.out.println("Verifique que el archivo no esté abrierto anteriormente.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Movie peli1=new Movie("The darkest minds","Cience fiction","Ruby Daly is an orange...");
        Movie peli2=new Movie("The help","Drama","A time ago, when racism was common...");
        Movie peli3=new Movie("Luca","Comedy","Luca is a sea moster, young, curios...");
        ArrayList<Movie> arrayPeliculas = new ArrayList<>();
        arrayPeliculas.add(peli1);
        arrayPeliculas.add(peli2);
        arrayPeliculas.add(peli3);
        CreateCSV crearArchivoCSV = new CreateCSV("/home/marlly/Descargas/archivoCSV.csv");
        crearArchivoCSV.crearCSV(arrayPeliculas, crearArchivoCSV.getPath());
    }

}
