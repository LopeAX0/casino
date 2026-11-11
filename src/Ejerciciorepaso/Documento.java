package Ejerciciorepaso;

import java.io.*;
import java.time.DayOfWeek;

public class Documento {
    File documento=new File("src/Ejerciciorepaso/leer.txt");
/*int turno=0;*/
    boolean leido=true;
    public void escribir(int idProductor) throws IOException, InterruptedException {
        synchronized (this) {
        /*while (turno!= idProductor|| !leido) {
            System.out.println("El productor "+idProductor +" contenido no a sido leido");
            wait();
        }*/
            if(documento.exists()){
                if(leido==false){
                    System.out.println("El productor "+idProductor +" contenido no a sido leido");
                    return;
                }

                try (BufferedWriter escrito=new BufferedWriter(new FileWriter(documento))){
                    ProcessBuilder pb=new ProcessBuilder("cmd.exe","/c","echo %date% %time%");
                    Process process = pb.start();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line;
                    StringBuilder output = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        output.append(line).append("\n");
                    }

                    // Esperar a que el proceso termine
                    int exitCode = process.waitFor();

                    if (exitCode == 0) {
                        System.out.println("El productor "+idProductor +"  ha escrito");
                        System.out.println("Resultado:\n" + output.toString().trim());
                    } else {
                        System.out.println("El comando falló con código: " + exitCode);
                    }
                    escrito.write(output.toString().trim());
                    escrito.flush();
                    leido=false;
                    notifyAll();

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

    }
    }
    public void lellendo(int idConsumidor) {
        synchronized (this) {
        if(documento.exists()){
            if(documento.length()>0){

                try (BufferedReader leer=new BufferedReader(new FileReader(documento))){
                    String line;
                    while ((line= leer.readLine())!=null){
                        System.out.println("El cosumidor "+idConsumidor+" a leido "+ line);
                    }

                leido=true;
                    notifyAll();


                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
            System.out.println("El consumidor "+idConsumidor+" no a encontrado nada nuevo");
        }
        }
    }
    static void main() {
    Documento d = new Documento();
    for (int i=0;i<2;i++){
    Thread pro=new Thread(new Productor(i,d));
    pro.start();
    }

    Thread con=new Thread(new Consumidor(1,d));
    con.start();

    }
}
