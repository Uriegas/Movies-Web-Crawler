package com.trumam;

import java.lang.reflect.Array;
import java.util.*;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;

public class CreateCSV {

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

    public void crearCSV(ArrayList<Movie> arrayPelicula, String nombreArchivo){
        String delimitador=",";
        int contador=arrayPelicula.size();
        final String salto = "\n";

        try {
            FileWriter fw = new FileWriter(nombreArchivo);
            for(int i=0; i<contador; i++){
                /*System.out.println(arrayPelicula.get(i));
                String stringMovie = String.valueOf(arrayPelicula.get(i));
                String [] strMovie = stringMovie.split(",");
                String title = strMovie[0];
                String tags = strMovie[1];
                String synopsis = strMovie[2];*/
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
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //El path puede asignarse con un método
        final String nombreDeArchivo = "/home/marlly/Descargas/archivoCSV.csv";
        Movie peli1=new Movie("The darkest minds","Cience fiction","Ruby Daly is an orange...");
        Movie peli2=new Movie("The help","Drama","A time ago, when racism was common...");
        Movie peli3=new Movie("Luca","Comedy","Luca is a sea moster, young, curios...");
        ArrayList<Movie> arrayPeliculas = new ArrayList<>();
        arrayPeliculas.add(peli1);
        arrayPeliculas.add(peli2);
        arrayPeliculas.add(peli3);
        /*String strMovie = String.valueOf(arrayPeliculas.get(0));
        String str2Movie = String.valueOf(arrayPeliculas.get(1));
        String str3Movie = String.valueOf(arrayPeliculas.get(2));
        System.out.println("Contenido del array[0] = "+strMovie);
        System.out.println("Contenido del array[1] = "+str2Movie);
        System.out.println("Contenido del array[2] = "+str3Movie);*/
        CreateCSV crearArchivoCSV = new CreateCSV();
        crearArchivoCSV.crearCSV(arrayPeliculas, nombreDeArchivo);
    }

}
