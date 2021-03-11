import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class validator extends Thread {
   // public static int rowErrors=-1;
    public static ArrayList <String> rowErrors = new ArrayList(2);
    public static ArrayList <String> columnErrors=new ArrayList<>(2);
    public static ArrayList <String> boxErrors=new ArrayList<>(2);
    public static ArrayList <String> confirmedErrors=new ArrayList<>(2);
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
                rows(soduku);
            }
        };
        row.setName("Rows ");
        validator column =new validator(){
            public void run(){
                columns(soduku);
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
            boxes.join();
        }
        catch(Exception e){
            System.out.println("error");
        }

        //Comparing rows to columns
        for(int i=0; i<rowErrors.size(); i++){
            System.out.println(rowErrors.get(i));
            for(int j=0; j<columnErrors.size(); j++){
                System.out.println(columnErrors.get(j));
                if((rowErrors.get(i)).equals(columnErrors.get(j))){
                    if(!confirmedErrors.contains(rowErrors.get(i).substring(0,2))){
                        confirmedErrors.add(rowErrors.get(i));
                    }
                }
            }
        }
        for(int i=0; i<rowErrors.size(); i++){
            for(int j=0; j<boxErrors.size(); j++){
                //System.out.println(boxErrors.get());
                if((rowErrors.get(i)).equals(boxErrors.get(j))){
                    if(!confirmedErrors.contains(rowErrors.get(i).substring(0,2))){
                        confirmedErrors.add(rowErrors.get(i));
                    }
                }
            }
        }
        for(int i=0; i<columnErrors.size(); i++){
            for(int j=0; j<boxErrors.size(); j++){
                if((columnErrors.get(i)).equals(boxErrors.get(j))){
                    if(!confirmedErrors.contains(columnErrors.get(i).substring(0,2))){
                        confirmedErrors.add(columnErrors.get(i));
                    }
                }
            }
        }
        System.out.println("Confirmed Errors are");
        for(int i =0; i<confirmedErrors.size(); i++){
            System.out.println(confirmedErrors.get(i));
        }
    }
    
    //This function is called when an error is found and it wants to find which number is missing
    public static int missingNumber(int[] arr){
        for(int i =1; i<10; i++){
            for(int j=0; j<9; j++){
                if(i==arr[j]){
                    break;
                }
                else if(j==8){
                    return i;
                }
            }
        }
        return -1;

    }
    public static void rows(int[][] arr){
        String row="";
        String column="";
        String missingNum="";
        ArrayList <Integer> oneRow = new ArrayList(10);
        for(int i=0; i<9; i++){
            oneRow.clear();
            for(int j=0; j<9; j++){
                if(oneRow.contains(arr[i][j])){
                    row=Integer.toString(i);
                    column=Integer.toString(j);
                    missingNum=Integer.toString(missingNumber(arr[i]));

                    //adds row and column of error to universal arraylist to be compared later
                    rowErrors.add(row+column+missingNum);
                }
                else{
                    oneRow.add(arr[i][j]);
                }
            }
        }
    }
    public static void columns(int[][] arr){
    String row="";
    String column="";
    String missingNum="";
    int [] col=new int[9];
    ArrayList <Integer> oneColumn = new ArrayList(10);
        for(int i=0; i<9; i++){
            oneColumn.clear();
            for(int j=0; j<9; j++){
                if(oneColumn.contains(arr[j][i])){
                    row=Integer.toString(j);
                    column=Integer.toString(i);
                    for(int k=0; k<9; k++){
                        col[k]=arr[k][i];
                    }
                    missingNum=Integer.toString(missingNumber(col));
                    //adds row and column of error to universal arraylist to be compared later
                    columnErrors.add(row+column+missingNum);
                }
                else{
                    oneColumn.add(arr[j][i]);
                }
            }
        }
    }
    public static void boxes(int[][] arr){
        ArrayList <Integer> singleBox = new ArrayList(9);
        int boxnumtemp=1;
        String row="";
        String column="";
        String missingNum="";
        int [] box=new int[9];
        //checks each box individually
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                if(singleBox.contains(arr[i][j])){
                    row=Integer.toString(i);
                    column=Integer.toString(j);
                    for(int k=0; k<3; k++){
                        for(int l=0; l<3; l++){
                            box[k*l]=arr[k][l];
                        }
                    }
                    missingNum=Integer.toString(missingNumber(box));
                    columnErrors.add(row+column+missingNum);
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
                    row=Integer.toString(i);
                    column=Integer.toString(j);
                    for(int k=0; k<3; k++){
                        for(int l=3; l<6; l++){
                            box[k*(l-3)]=arr[k][l];
                        }
                    }
                    missingNum=Integer.toString(missingNumber(box));
                    columnErrors.add(row+column+missingNum);
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
                    row=Integer.toString(i);
                    column=Integer.toString(j);
                    for(int k=0; k<3; k++){
                        for(int l=6; l<9; l++){
                            box[k*(l-6)]=arr[k][l];
                        }
                    }
                    missingNum=Integer.toString(missingNumber(box));
                    columnErrors.add(row+column+missingNum);
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
                    row=Integer.toString(i);
                    column=Integer.toString(j);
                    for(int k=3; k<6; k++){
                        for(int l=0; l<3; l++){
                            box[(k-3)*l]=arr[k][l];
                        }
                    }
                    missingNum=Integer.toString(missingNumber(box));
                    columnErrors.add(row+column+missingNum);
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
                    row=Integer.toString(i);
                    column=Integer.toString(j);
                    for(int k=3; k<6; k++){
                        for(int l=3; l<6; l++){
                            box[(k-3)*(l-3)]=arr[k][l];
                        }
                    }
                    missingNum=Integer.toString(missingNumber(box));
                    columnErrors.add(row+column+missingNum);
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
                    row=Integer.toString(i);
                    column=Integer.toString(j);
                    for(int k=3; k<6; k++){
                        for(int l=6; l<9; l++){
                            box[(k-3)*(l-6)]=arr[k][l];
                        }
                    }
                    missingNum=Integer.toString(missingNumber(box));
                    columnErrors.add(row+column+missingNum);
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
                    row=Integer.toString(i);
                    column=Integer.toString(j);
                    for(int k=6; k<9; k++){
                        for(int l=0; l<3; l++){
                            box[(k-6)*l]=arr[k][l];
                        }
                    }
                    missingNum=Integer.toString(missingNumber(box));
                    columnErrors.add(row+column+missingNum);
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
                    row=Integer.toString(i);
                    column=Integer.toString(j);
                    for(int k=6; k<9; k++){
                        for(int l=3; l<6; l++){
                            box[(k-6)*(l-3)]=arr[k][l];
                        }
                    }
                    missingNum=Integer.toString(missingNumber(box));
                    columnErrors.add(row+column+missingNum);
                }
                else{
                    singleBox.add(arr[i][j]);
                }
                
            }
        }
        singleBox.clear();
        boxnumtemp++;
        for(int i=6; i<9; i++){
            for(int j=6; j<9; j++){
                if(singleBox.contains(arr[i][j])){
                    row=Integer.toString(i);
                    column=Integer.toString(j);
                    for(int k=6; k<9; k++){
                        for(int l=6; l<9; l++){
                            box[(k-6)*(l-6)]=arr[k][l];
                        }
                    }
                    missingNum=Integer.toString(missingNumber(box));
                    columnErrors.add(row+column+missingNum);
                }
                else{
                    singleBox.add(arr[i][j]);
                }
                
            }
        }
    }
}