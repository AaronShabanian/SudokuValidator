import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class validator extends Thread {
    public static int rowErrors=-1;
    public static int columnErrors=-1;
    public static int boxnum=-1;
    public static void main(String[] args){
        int[][] soduku = new int[9][9];
        //input parsing (converting file to 2d array)
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
        validator row = new validator(){
            public void run(){
                int rowError=rows(soduku);
            }
        };
        row.setName("Rows ");
        validator column =new validator(){
            public void run(){
                int columnError=columns(soduku);
            }
        };
        column.setName("column");
        validator boxes = new validator(){
            public void run(){
                boxes(soduku);
            }
        };
        boxes.setName("Boxes");
        row.start();
        column.start();
        boxes.start();
        try{
            row.join();
            column.join();
        }
        catch(Exception e){
            System.out.println("error");
        }
        System.out.println("The error is at row:"+ rowErrors+ " Column: "+ columnErrors);
        
    }
    public static int rows(int[][] arr){
        System.out.println("rows");
        ArrayList <Integer> oneRow = new ArrayList(10);
        for(int i=0; i<9; i++){
            oneRow.clear();
            for(int j=0; j<9; j++){
                if(oneRow.contains(arr[i][j])){
                    rowErrors=i;
                    return i;
                }
                else{
                    oneRow.add(arr[i][j]);
                }
            }
        }
        return -1;
    }
    public static int columns(int[][] arr){
    ArrayList <Integer> oneColumn = new ArrayList(10);
        for(int i=0; i<9; i++){
            oneColumn.clear();
            for(int j=0; j<9; j++){
                if(oneColumn.contains(arr[j][i])){
                    columnErrors=i;
                    return i;
                }
                else{
                    oneColumn.add(arr[j][i]);
                }
            }
        }
        return -1;
    }
    public static void boxes(int[][] arr){
        ArrayList <Integer> singleBox = new ArrayList(9);
        int boxnumtemp=1;
        //checks each box individually
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                if(singleBox.contains(arr[i][j])){
                    boxnum=boxnumtemp;
                }
                else{
                    singleBox.add(arr[i][j]);
                }
                
            }
        }
        singleBox.clear();
        boxnumtemp++;
        for(int i=0; i<3; i++){
            for(int j=3; j<6; j++){
                if(singleBox.contains(arr[i][j])){
                    boxnum=boxnumtemp;
                }
                else{
                    singleBox.add(arr[i][j]);
                }
                
            }
        }
        singleBox.clear();
        boxnumtemp++;
        for(int i=0; i<3; i++){
            for(int j=6; j<9; j++){
                if(singleBox.contains(arr[i][j])){
                    boxnum=boxnumtemp;
                }
                else{
                    singleBox.add(arr[i][j]);
                }
                
            }
        }
        singleBox.clear();
        boxnumtemp++;
        for(int i=3; i<6; i++){
            for(int j=0; j<3; j++){
                if(singleBox.contains(arr[i][j])){
                    boxnum=boxnumtemp;
                }
                else{
                    singleBox.add(arr[i][j]);
                }
                
            }
        }
        singleBox.clear();
        boxnumtemp++;
        for(int i=3; i<6; i++){
            for(int j=3; j<6; j++){
                if(singleBox.contains(arr[i][j])){
                    boxnum=boxnumtemp;
                }
                else{
                    singleBox.add(arr[i][j]);
                }
                
            }
        }
        singleBox.clear();
        boxnumtemp++;
        for(int i=3; i<6; i++){
            for(int j=6; j<9; j++){
                if(singleBox.contains(arr[i][j])){
                    boxnum=boxnumtemp;
                }
                else{
                    singleBox.add(arr[i][j]);
                }
                
            }
        }
        singleBox.clear();
        boxnumtemp++;
        for(int i=6; i<9; i++){
            for(int j=0; j<3; j++){
                if(singleBox.contains(arr[i][j])){
                    boxnum=boxnumtemp;
                }
                else{
                    singleBox.add(arr[i][j]);
                }
                
            }
        }
        singleBox.clear();
        boxnumtemp++;
        for(int i=6; i<9; i++){
            for(int j=3; j<6; j++){
                if(singleBox.contains(arr[i][j])){
                    boxnum=boxnumtemp;
                }
                else{
                    singleBox.add(arr[i][j]);
                }
                
            }
        }
        singleBox.clear();
        boxnumtemp++;
        for(int i=6; i<9; i++){
            for(int j=3; j<6; j++){
                if(singleBox.contains(arr[i][j])){
                    boxnum=boxnumtemp;
                }
                else{
                    singleBox.add(arr[i][j]);
                }
                
            }
        }
    }
}