package com.my.labseq.resources;

import com.google.common.cache.CacheBuilder;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/*
 * @author Samuel Martins
*/


/*
    Como funciona Labseq Sequence:
        l(0) = 0
        l(1) = 1
        l(2) = 0
        l(3) = 1
        l(n > 3) -> l(n) = l(n-4) + l(n-3)
*/

@Path("/labseq/{n}")
@Produces(MediaType.APPLICATION_JSON)
public class labseqResource {
    
    // Vector é thread safe, portanto não vai ocorrer nenhum problema de concorrência
    private Vector<BigInteger> vecSequence = new Vector<>();
    
    @GET
    public Response getLabseqSequenceIndex(@PathParam("n") int n){
        Map<String,String> mapResponse = new HashMap<>();
        try{
            if(n < 0){
                mapResponse.put("Status: ", "400");
                return Response.status(Response.Status.CONFLICT).type(MediaType.APPLICATION_JSON).entity(mapResponse)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .build();
            }
            else{
                generateSequence(n);
                mapResponse.put("Value: ", ""+vecSequence.get(n));
                return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(mapResponse)                
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .build();
            }
        }catch(Exception e){
            mapResponse.put("Status: ", "500");            
            return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(mapResponse)                
            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
            .build();
        }
    }

    //Função 
    public synchronized void generateSequence(int n){
        try{
            int iSequenceSize = vecSequence.size();

            // Se o valor do tamanho da sequencia for 0 vamos ter de adicionar os primeiros 4 valores (já predefinidos)
            if(iSequenceSize == 0){
                vecSequence.add(new BigInteger("0"));
                vecSequence.add(new BigInteger("1"));
                vecSequence.add(new BigInteger("0"));
                vecSequence.add(new BigInteger("1"));
                iSequenceSize = 4;
            }

            // Caso o valor seja maior que 3, vamos ter de o calcular através do ciclo que se segue
            if(iSequenceSize <= n){       
                BigInteger biFirstValue;
                BigInteger biSecondValue;
                BigInteger biFinalValue;

                    for(int i = iSequenceSize; i <= n; i++){
                        biFirstValue = vecSequence.get(i-4);
                        biSecondValue = vecSequence.get(i-3);
                        biFinalValue = biFirstValue.add(biSecondValue);
                        vecSequence.add(biFinalValue);
                }
            }

            // Se o valor o valor n for menor que o tamanho do vector, então não é necessário fazer nada
        }catch(Exception e){
            System.out.println("Server error: "+e);
        }
    }
}
