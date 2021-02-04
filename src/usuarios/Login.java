package usuarios;

import java.io.*;
import java.util.Scanner;

public class Login {
    /** Atributos */
    private String nombre;
    private String calle;
    private int noExterior;
    private int noInterior;
    private long noTarjeta;
    private int cvv;
    public static final String separador = ",";

    public Login(){
        Scanner teclado = new Scanner(System.in);
        System.out.print("Nombre: ");
        nombre = teclado.nextLine();
        System.out.println("DIRECCION");
        System.out.print("\tCalle: ");
        calle = teclado.nextLine();
        System.out.print("\tNo. Exterior: ");
        noExterior = teclado.nextInt();
        System.out.print("\tNo. Interior: ");
        noInterior = teclado.nextInt();
        System.out.println("DATOS DE PAGO");
        System.out.print("no. Tarjeta: ");
        noTarjeta = teclado.nextLong();
        System.out.print("CVV: ");
        cvv = teclado.nextInt();
    }

    public boolean verificarRutas(){
        File directorioUsers = new File("./src/usuarios/UsersInfo");
        File archivoUsuarios = new File("./src/usuarios/UsersInfo/DataBase.csv");
        if(!directorioUsers.exists()) {
            if (directorioUsers.mkdir()) {
                try {
                    if (archivoUsuarios.createNewFile()) {
                        FileWriter fw = new FileWriter(archivoUsuarios);
                        fw.append("Nombre,Calle,No. Exterior,No. Interior,No. Tarjeta,CVV\n");
                        fw.flush();
                        fw.close();
                        System.out.println("Directorio y archivo creados correctamente");
                    }
                }catch (IOException ioe) {
                    System.out.println("ERROR: No se pudo crear el archivo");
                    return false;
                }
            }
            else{
                System.out.println("ERROR: No se pudo crear el directorio");
                return false;
            }
        }
        else{
            if(!archivoUsuarios.exists()){
                try{
                    if(archivoUsuarios.createNewFile()){
                        FileWriter fw = new FileWriter(archivoUsuarios);
                        fw.append("Nombre,Calle,No. Exterior,No. Interior,No. Tarjeta,CVV\n");
                        fw.flush();
                        fw.close();
                        System.out.println("Archivo creado correctamente");
                    }
                }catch(IOException ioe){
                    System.out.println("ERROR: No se pudo crear el archivo");
                    return false;
                }
            }
            return true;
        }
        return true;
    }

    public boolean verificarContenidoArchivo(File rutaArchivo){
        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader(rutaArchivo));
            String line = br.readLine();
            while(line != null){
                String [] campos = line.split(separador);
                if(campos[0] == nombre){
                    System.out.println("Usuario ya registrado, puede iniciar sesión");
                    return true;
                }
                line = br.readLine();
            }
        }catch(FileNotFoundException fnf){
            System.out.println("ERROR: El archivo que se quiere leer no existe");
            return false;
        }catch(IOException io){
            System.out.println("ERROR: No se pudo leer la informacion del archivo");
            return false;
        }finally{
            if(br != null){
                try {
                    br.close();
                } catch(IOException io){
                    System.out.println("Problemas al cerrar el Buffer de lectura");
                }
            }
        }
        return false;
    }

    public boolean guardarInfo(){
        if(verificarRutas()) {
            try {
                File archivoUsuarios = new File("./src/usuarios/UsersInfo/DataBase.csv");
                if (!verificarContenidoArchivo(archivoUsuarios)) {
                    String infoUsuario = nombre + "," + calle + "," + noExterior + "," + noInterior + "," + noTarjeta + "," + cvv + "\n";
                    FileWriter fw = new FileWriter(archivoUsuarios,true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter textoSalida = new PrintWriter(bw);
                    textoSalida.println(infoUsuario);
                    textoSalida.close();
                    System.out.println("Informacion registrada corrctamente");
                    return true;
                }
            } catch(IOException io){
                System.out.println("ERROR: No se pudo modificar el archivo");
                return false;
            }
        }
        return true;
    }

}