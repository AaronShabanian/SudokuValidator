import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class validator extends Thread {
    public static void main(String[] args){
        int[][] soduku = new int[9][9];
        try{
            File file = new File("tester.txt");
            Scanner reader = new Scanner(file);
            int counter=-1;
            while (reader.hasNextLine()){
                counter++;
                String data = reader.nextLine();
                char checker = data.charAt(data.length()-1);
                if(checker==','){
                    data = data.substring(0, data.length() - 1);
                }
                String[] temp = data.split(",");
                for(int i=0; i<temp.length; i++){
                    soduku[counter][i]=Integer.parseInt(temp[i]);
                }
            }
            reader.close();
        }
        catch(FileNotFoundException e){
            System.out.println("Error");
        }
        for (int i = 0; i < 9; i++){ 
            for (int j = 0; j < 9; j++){
                System.out.print(soduku[i][j]);    
            } 
            System.out.println(" ");
        }
    }
}