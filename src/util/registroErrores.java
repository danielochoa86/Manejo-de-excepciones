package util;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class registroErrores {
    public static void registrar(Exception e){

        try (FileWriter writer = new FileWriter("errores.log", true)){
            writer.write(
                    LocalDateTime.now()
                    + " - "
                    + e.getClass().getSimpleName()
                    + " - "
                    + e.getMessage()
                    + "\n"
            );
        } catch (IOException ioException){
            System.out.println("Error escribiendo log");
        }
    }
}
