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
    // The following 2 lines are for the edge case of case 5 as there is 2 missing numbers in the same row
    public static ArrayList <String> confirmedcoordinates=new ArrayList<>(2);
    public static int secondMissingNum;
    public static void main(String[] args){
        int[][] soduku = new int[9][9];
        //input parsing (converting file to 2d array)
        try{
            File file = new File("Testfile5.txt");
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
            for(int j=0; j<columnErrors.size(); j++){
                if((rowErrors.get(i)).equals(columnErrors.get(j))){
                    if((!confirmedErrors.contains(rowErrors.get(i)))&&(!confirmedcoordinates.contains(rowErrors.get(i).substring(0,2)))){
                        confirmedErrors.add(rowErrors.get(i));
                        confirmedcoordinates.add(rowErrors.get(i).substring(0,2));
                    }
                }
            }
        }
        //Comparing rows to boxes
        for(int i=0; i<rowErrors.size(); i++){
            for(int j=0; j<boxErrors.size(); j++){
                if(rowErrors.get(i).equals(boxErrors.get(j))){
                    if((!confirmedErrors.contains(rowErrors.get(i)))&&(!confirmedcoordinates.contains(rowErrors.get(i).substring(0,2)))){
                        confirmedErrors.add(rowErrors.get(i));
                    }
                }
            }
        }
        System.out.println(rowErrors);
        System.out.println(columnErrors);
        System.out.println(boxErrors);
        // Comparing columns to boxes
        for(int i=0; i<columnErrors.size(); i++){
            for(int j=0; j<boxErrors.size(); j++){
                if((columnErrors.get(i)).equals(boxErrors.get(j))){
                    if((!confirmedErrors.contains(columnErrors.get(i)))&&(!confirmedcoordinates.contains(columnErrors.get(i).substring(0,2)))){
                        confirmedErrors.add(columnErrors.get(i));
                    }
                }
            }
        }
        System.out.println("Confirmed Errors are");
        System.out.println(confirmedErrors);
    }
    
    //This function is called when an error is found and it wants to find which number is missing
    public static int missingNumber(int[] arr){
        int checker=0;
        int firstNum=-1;
        for(int i =1; i<10; i++){
            for(int j=0; j<9; j++){
                if(i==arr[j]){
                    j=50;
                }
                else if(j==8 && checker==0){
                    firstNum=i;
                    checker++;
                }
                else if(j==8 && checker==1){
                    //gets the second missing number if there are 2
                    secondMissingNum=i;
                }
            }
        }
        return firstNum;

    }
    public static void rows(int[][] arr){
        String row="";
        String column="";
        String missingNum="";
        ArrayList <Integer> oneRow = new ArrayList(10);
        ArrayList <String> onerowcoordinates = new ArrayList(10);
        for(int i=0; i<9; i++){
            oneRow.clear();
            onerowcoordinates.clear();
            for(int j=0; j<9; j++){
                if(oneRow.contains(arr[i][j])){
                    row=Integer.toString(i);
                    column=Integer.toString(j);
                    missingNum=Integer.toString(missingNumber(arr[i]));
                    //adds row and column of error to universal arraylist to be compared later
                    rowErrors.add(row+column+missingNum);
                    rowErrors.add(row+column+secondMissingNum);
                    for(int k=0; k<oneRow.size(); k++){
                        if(oneRow.get(k)==arr[i][j]){
                            rowErrors.add(onerowcoordinates.get(k)+missingNum);
                            rowErrors.add(onerowcoordinates.get(k)+secondMissingNum);
                        }
                    }
                }
                else{
                    oneRow.add(arr[i][j]);
                    onerowcoordinates.add(Integer.toString(i)+Integer.toString(j));
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
    ArrayList <String> onecolumncoordinates= new ArrayList<>(10);
        for(int i=0; i<9; i++){
            oneColumn.clear();
            onecolumncoordinates.clear();
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
                    for(int k=0; k<oneColumn.size(); k++){
                        if(oneColumn.get(k)==arr[j][i]){
                            columnErrors.add(onecolumncoordinates.get(k)+missingNum);
                        }
                    }

                }
                else{
                    oneColumn.add(arr[j][i]);
                    onecolumncoordinates.add(Integer.toString(j)+Integer.toString(i));
                }
            }
        }
    }
    public static void boxes(int[][] arr){
        ArrayList <Integer> singleBox = new ArrayList(9);
        ArrayList <String> singleboxcoordinates = new ArrayList<>(9);
        int boxnumtemp=1;
        String row="";
        String column="";
        String missingNum="";
        int counter=0;
        int [] box=new int[9];
        //checks each box individually
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                if(singleBox.contains(arr[i][j])){
                    row=Integer.toString(i);
                    column=Integer.toString(j);
                    counter=0;
                    for(int k=0; k<3; k++){
                        for(int l=0; l<3; l++){
                            box[counter]=arr[k][l];
                            counter++;
                        }
                    }
                    missingNum=Integer.toString(missingNumber(box));
                    boxErrors.add(row+column+missingNum);
                    for(int k=0; k<singleBox.size(); k++){
                        if(singleBox.get(k)==arr[i][j]){
                            boxErrors.add(singleboxcoordinates.get(k)+missingNum);
                        }
                    }
                }
                else{
                    singleBox.add(arr[i][j]);
                    singleboxcoordinates.add(Integer.toString(i)+Integer.toString(j));
                }
                
            }
        }
        singleBox.clear();
        singleboxcoordinates.clear();
        counter=0;
        boxnumtemp++;
        for(int i=0; i<3; i++){
            for(int j=3; j<6; j++){
                if(singleBox.contains(arr[i][j])){
                    row=Integer.toString(i);
                    column=Integer.toString(j);
                    counter=0;
                    for(int k=0; k<3; k++){
                        for(int l=3; l<6; l++){
                            box[counter]=arr[k][l];
                            counter++;
                        }
                    }
                    missingNum=Integer.toString(missingNumber(box));
                    boxErrors.add(row+column+missingNum);
                    for(int k=0; k<singleBox.size(); k++){
                        if(singleBox.get(k)==arr[i][j]){
                            boxErrors.add(singleboxcoordinates.get(k)+missingNum);
                        }
                    }
                }
                else{
                    singleBox.add(arr[i][j]);
                    singleboxcoordinates.add(Integer.toString(i)+Integer.toString(j));
                }
                
            }
        }
        singleBox.clear();
        singleboxcoordinates.clear();
        boxnumtemp++;
        counter=0;
        for(int i=0; i<3; i++){
            for(int j=6; j<9; j++){
                if(singleBox.contains(arr[i][j])){
                    row=Integer.toString(i);
                    column=Integer.toString(j);
                    counter=0;
                    for(int k=0; k<3; k++){
                        for(int l=6; l<9; l++){
                            box[counter]=arr[k][l];
                            counter++;
                        }
                    }
                    missingNum=Integer.toString(missingNumber(box));
                    boxErrors.add(row+column+missingNum);
                    for(int k=0; k<singleBox.size(); k++){
                        if(singleBox.get(k)==arr[i][j]){
                            boxErrors.add(singleboxcoordinates.get(k)+missingNum);
                        }
                    }
                }
                else{
                    singleBox.add(arr[i][j]);
                    singleboxcoordinates.add(Integer.toString(i)+Integer.toString(j));
                }
                
            }
        }
        singleBox.clear();
        singleboxcoordinates.clear();
        boxnumtemp++;
        counter=0;
        for(int i=3; i<6; i++){
            for(int j=0; j<3; j++){
                if(singleBox.contains(arr[i][j])){
                    row=Integer.toString(i);
                    column=Integer.toString(j);
                    counter=0;
                    for(int k=3; k<6; k++){
                        for(int l=0; l<3; l++){
                            box[counter]=arr[k][l];
                            counter++;
                        }
                    }
                    missingNum=Integer.toString(missingNumber(box));
                    boxErrors.add(row+column+missingNum);
                    for(int k=0; k<singleBox.size(); k++){
                        if(singleBox.get(k)==arr[i][j]){
                            boxErrors.add(singleboxcoordinates.get(k)+missingNum);
                        }
                    }
                }
                else{
                    singleBox.add(arr[i][j]);
                    singleboxcoordinates.add(Integer.toString(i)+Integer.toString(j));
                }
                
            }
        }
        singleBox.clear();
        singleboxcoordinates.clear();
        boxnumtemp++;
        counter=0;
        for(int i=3; i<6; i++){
            for(int j=3; j<6; j++){
                if(singleBox.contains(arr[i][j])){
                    row=Integer.toString(i);
                    column=Integer.toString(j);
                    counter=0;
                    for(int k=3; k<6; k++){
                        for(int l=3; l<6; l++){
                            box[counter]=arr[k][l];
                            counter++;
                        }
                    }
                    missingNum=Integer.toString(missingNumber(box));
                    boxErrors.add(row+column+missingNum);
                    for(int k=0; k<singleBox.size(); k++){
                        if(singleBox.get(k)==arr[i][j]){
                            boxErrors.add(singleboxcoordinates.get(k)+missingNum);
                        }
                    }
                }
                else{
                    singleBox.add(arr[i][j]);
                    singleboxcoordinates.add(Integer.toString(i)+Integer.toString(j));
                }
                
            }
        }
        singleBox.clear();
        singleboxcoordinates.clear();
        boxnumtemp++;
        counter=0;
        for(int i=3; i<6; i++){
            for(int j=6; j<9; j++){
                if(singleBox.contains(arr[i][j])){
                    row=Integer.toString(i);
                    column=Integer.toString(j);
                    counter=0;
                    for(int k=3; k<6; k++){
                        for(int l=6; l<9; l++){
                            box[counter]=arr[k][l];
                            counter++;
                        }
                    }
                    missingNum=Integer.toString(missingNumber(box));
                    boxErrors.add(row+column+missingNum);
                    for(int k=0; k<singleBox.size(); k++){
                        if(singleBox.get(k)==arr[i][j]){
                            boxErrors.add(singleboxcoordinates.get(k)+missingNum);
                        }
                    }
                }
                else{
                    singleBox.add(arr[i][j]);
                    singleboxcoordinates.add(Integer.toString(i)+Integer.toString(j));
                }
                
            }
        }
        singleBox.clear();
        singleboxcoordinates.clear();
        boxnumtemp++;
        counter=0;
        for(int i=6; i<9; i++){
            for(int j=0; j<3; j++){
                if(singleBox.contains(arr[i][j])){
                    row=Integer.toString(i);
                    column=Integer.toString(j);
                    counter=0;
                    for(int k=6; k<9; k++){
                        for(int l=0; l<3; l++){
                            box[counter]=arr[k][l];
                            counter++;
                        }
                    }
                    missingNum=Integer.toString(missingNumber(box));
                    boxErrors.add(row+column+missingNum);
                    for(int k=0; k<singleBox.size(); k++){
                        if(singleBox.get(k)==arr[i][j]){
                            boxErrors.add(singleboxcoordinates.get(k)+missingNum);
                        }
                    }
                }
                else{
                    singleBox.add(arr[i][j]);
                    singleboxcoordinates.add(Integer.toString(i)+Integer.toString(j));
                }
                
            }
        }
        singleBox.clear();
        singleboxcoordinates.clear();
        boxnumtemp++;
        counter=0;
        for(int i=6; i<9; i++){
            for(int j=3; j<6; j++){
                if(singleBox.contains(arr[i][j])){
                    row=Integer.toString(i);
                    column=Integer.toString(j);
                    counter=0;
                    for(int k=6; k<9; k++){
                        for(int l=3; l<6; l++){
                            box[counter]=arr[k][l];
                        }
                    }
                    missingNum=Integer.toString(missingNumber(box));
                    boxErrors.add(row+column+missingNum);
                    for(int k=0; k<singleBox.size(); k++){
                        if(singleBox.get(k)==arr[i][j]){
                            boxErrors.add(singleboxcoordinates.get(k)+missingNum);
                        }
                    }
                }
                else{
                    singleBox.add(arr[i][j]);
                    singleboxcoordinates.add(Integer.toString(i)+Integer.toString(j));
                }
                
            }
        }
        singleBox.clear();
        singleboxcoordinates.clear();
        boxnumtemp++;
        counter=0;
        for(int i=6; i<9; i++){
            for(int j=6; j<9; j++){
                if(singleBox.contains(arr[i][j])){
                    row=Integer.toString(i);
                    column=Integer.toString(j);
                    counter=0;
                    for(int k=6; k<9; k++){
                        for(int l=6; l<9; l++){
                            box[counter]=arr[k][l];
                            counter++;
                        }
                    }
                    missingNum=Integer.toString(missingNumber(box));
                    boxErrors.add(row+column+missingNum);
                    for(int k=0; k<singleBox.size(); k++){
                        if(singleBox.get(k)==arr[i][j]){
                            boxErrors.add(singleboxcoordinates.get(k)+missingNum);
                        }
                    }
                }
                else{
                    singleBox.add(arr[i][j]);
                    singleboxcoordinates.add(Integer.toString(i)+Integer.toString(j));
                }
                
            }
        }
    }
}