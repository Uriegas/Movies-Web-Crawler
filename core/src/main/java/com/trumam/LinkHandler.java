package com.trumam;

import java.net.URL;

public interface LinkHandler<T> {

    /**
     * Guarda el link en la cosa
     * @param link
     * @throws Exception
     */
    void queueLink(URL link) throws Exception;

    /**
     * Retorna el número de links visitados
     * @return
     */
    int size();

    /**
     * Verifica si el link ya ha sido visitado
     * @param link
     * @return
     */
    boolean visited(URL link);

    /**
     * Marca el link como visitado
     * @param link
     */
    void addVisited(URL link, T datatype);
    
}