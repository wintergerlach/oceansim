package oceansimulationwrg;

import java.io.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.awt.Point;

/**
 * 
 * @author wrger
 */

public class OceanSimulationWRG {
    //global random
    public static Random r1;
    
    //global item count variable
    public static int totalItems = 0;
    
    //global row/col nums
    public static int rows = 0;
    public static int cols = 0;
    
    //global chance of object being destroyed
    public static int chance = 0;
    
    private static final char EMPTY = '^';
    private static final char FISH = 'F';
    private static final char SHARK = 'S';
    private static final char BOAT = 'B';
    private static final char ICEBURG = 'I';
    private static final char KRAKEN = 'K';
    private static final char OILRIG = 'O';
    private static final char BIRD = 'A';
    private static final char LOBSTER = 'L';
    private static final char ROCK = 'R';
    private static final char JELLYFISH = 'J'; 
   
    /**
     * puts in start values for ocean 
     * @param ocean ocean to initialize
     */
    public static void initOcean(char[][] ocean){
        int x = 0;
        int y = 0;
        int count = (rows*cols)/10;
        Point point = new Point(r1.nextInt(rows), r1.nextInt(cols));
        
        //places blank objects in all spots
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                ocean[i][j] = EMPTY;
            }
        }
        
        //randomly places fish in 10% of the board.....CHANGE LATER
        for(int i = 0; i<count; i++){ 
            if(ocean[point.x][point.y] == EMPTY){
                ocean[x][y] = FISH;
            }
            point.x = r1.nextInt(rows);
            point.y = r1.nextInt(cols);
        }
        
        //sets the total items to the count which is the number added intitally 
        totalItems = count;
        
        //if count(10%) is 0 place 1 random fish
        if(count == 0){
            ocean[point.x][point.y] = FISH;
            totalItems = 1;
        }
    }
    
    //prints the ocean to the screen
    public static void printOcean(char[][] ocean){
        int n = 0;
        //prints out the column nums across top
        while(n <= cols){
            if(n == 0) {
                System.out.print("   ");
            } else if((n > 9) && (n < 100)){
               System.out.print(" " + n + " "); 
            } else if(n < 10){
              System.out.print("  " + n + " ");
            } else{
                System.out.print(n + " ");
            } 
            ++n;
        }
        
        System.out.println("");
        //prints out row nums and items
        for(int i = 1; i < rows + 1; i++){
            if((i > 9) && (i < 100)){
               System.out.print(i+"  "); 
            } else if(i < 10){
                System.out.print(i+"   ");  
            } else{
                System.out.print(i+" ");
            }
            
            for(int j = 0; j < cols; j++){
                System.out.print(" "+ocean[i-1][j]+"  ");
            }
            System.out.println("");
        }
    }
    
    //adds ultiple items to the ocean
    public static void addItems(char[][]ocean){
        char item = 'z';
        int horizontal = 0;
        int vertical = 0;
        int choice = 0;
        int numToAdd = 0;
        int numAdded = 0;
        int space = (rows * cols) - totalItems;
        
        //gets random or not
        System.out.println("Do you want to pick a spot or do random.");
        System.out.println("Enter 1 for pick and 2 for random");
        
        //checks valid input
        while((choice < 1) || (choice > 2)){
            choice = SavitchIn.readLineInt();
        }
        
        //gets num of objects
        System.out.println("How many objects do you want to add?");
        numToAdd = SavitchIn.readLineInt();
        
        switch(choice){
            //user picks spots for items
            case 1:
                //repeats till the num of items user wants to place is met.
                while(numAdded < numToAdd){
                    //gets the row to place the item
                    System.out.println("What row do you want to place the object in?");
                    horizontal = SavitchIn.readLineInt();
                    
                    //checks for valid input
                    while((horizontal-1) > rows){
                        System.out.println("That number is too big. Try again.");
                        horizontal = SavitchIn.readLineInt();
                    }
                    
                    while((horizontal-1) < 0){
                        System.out.println("That number is too small. Try again.");
                        horizontal = SavitchIn.readLineInt();
                    }
                    
                    //gets what column the user wants to place in
                    System.out.println("What collumn do you want to place the object in?");
                    vertical = SavitchIn.readLineInt();
                    
                    //checks for valid input
                    while((vertical-1) > cols){
                        System.out.println("That number is too big. Try again.");
                        vertical = SavitchIn.readLineInt();
                    }
                    
                    while((vertical-1) < 0){
                        System.out.println("That number is too small. Try again.");
                        vertical = SavitchIn.readLineInt();
                    }
                    
                    //checks for valid item input
                    while(!((item == FISH) || (item == SHARK) || 
                            (item == BOAT) || (item == ICEBURG) ||
                            (item == KRAKEN) || (item == ROCK) || 
                            (item == JELLYFISH) || (item == LOBSTER) ||
                            (item == OILRIG) || (item == BIRD))){
                        System.out.println("What do you want to add?");
                        item = SavitchIn.readLineNonwhiteChar();
                        item = Character.toUpperCase(item);
                    }
                    
                    //if spot is empty - place item
                    if(ocean[horizontal-1][vertical-1] == EMPTY){
                        //places item
                        ocean[horizontal-1][vertical-1]=item;
                        //increases the total item count by one
                        totalItems++;
                        //increases the number of items added 
                        numAdded++;
                        //spot is not empty
                    } else{
                        //asks to overwrite
                        System.out.println("Something is already there.");
                        System.out.println("To delete and replace it enter 1.");
                        choice = SavitchIn.readLineInt();
                        //overwrites if 1
                        if(choice==1){
                            //replaces item
                           ocean[horizontal-1][vertical-1] = item;
                           //increase total items count
                           totalItems++;
                           //increase the number of items added
                           numAdded++;
                        }
                    }
                    //resets item to z for next loop through.
                    item = 'z';
                }
                
                //print the ocean
                printOcean(ocean);
                break;
                
            //random placement
            case 2:
                //checks to make sure there are enough spots to randomly
                //place objects without overwriting any that are already there.
                while(numToAdd > space){ 
                    System.out.println("You cannot add that many objects.");
                    System.out.println("Please pick a smaller number.");
                    numToAdd = SavitchIn.readLineInt();
                }
                
                //checks valid input of item
                while(!((item == FISH) || (item == SHARK) || (item == BOAT) || 
                       (item == ICEBURG) || (item == KRAKEN) || (item == ROCK) 
                       || (item == JELLYFISH) || (item == LOBSTER) || 
                       (item == OILRIG) || (item == BIRD))){
                    System.out.println("What do you want to add?");
                    item = SavitchIn.readLineNonwhiteChar();
                    item = Character.toUpperCase(item);
                }
                
                //runs till number user wanted to add is reached
                while(numAdded < numToAdd){
                    //picks a random spot
                    horizontal = r1.nextInt(cols);
                    vertical = r1.nextInt(rows);
                    
                    //checks if random spot is open- if not picks new one till one is open
                    while(ocean[horizontal][vertical] != EMPTY){
                        horizontal = r1.nextInt(cols);
                        vertical = r1.nextInt(rows);
                    }
                    
                    //places item
                    ocean[horizontal][vertical]=item;
                    
                    //increase the total number of items
                    totalItems++;
                    
                    //increases the number added
                    numAdded++; 
                }
                
                //print the ocean
                printOcean(ocean);
                break;
        }
    }
    
    //adds one item to the ocean
    public static void addItem(char[][]ocean){
        char item = 'z';
        int horizontal = 0;
        int vertical = 0;
        int choice = 0;
        
        //gets random or not 
        System.out.println("Do you want to pick a spot or do random.");
        System.out.println("Enter 1 for pick and 2 for random");
        
        while((choice < 1)||(choice>2)){
            choice = SavitchIn.readLineInt();
        }
        
        switch(choice){
            case 1://user picks position
                //gets and checks for valid row
                System.out.println("What row do you want to place the object in?");
                horizontal = SavitchIn.readLineInt();
                while(((horizontal-1) > rows) || ((horizontal-1) < 0)){
                    //if the nuber is too small
                    if((horizontal-1) < 0){
                        System.out.println("That number is too small. Try again.");
                    }else{//if the num too big
                        System.out.println("That number is too big. Try again.");
                    }
                    horizontal = SavitchIn.readLineInt();
                }
                
                //gets and checks for a valid column
                System.out.println("What collumn do you want to place the object in?");
                vertical = SavitchIn.readLineInt();
                while(((vertical - 1) > cols)||((vertical-1)<0)){
                    //if the num too big
                    if((vertical - 1) > cols){
                        System.out.println("That number is too big. Try again.");
                    }else{//if num too small
                        System.out.println("That number is too small. Try again.");
                    }
                    
                    vertical = SavitchIn.readLineInt();
                }
                
                //checks for valied Item input
                while(!((item == FISH) || (item == SHARK) || (item == BOAT) || 
                       (item == ICEBURG) || (item == KRAKEN) || (item == ROCK) 
                       || (item == JELLYFISH) || (item == LOBSTER) || 
                       (item == OILRIG) || (item == BIRD))){
                    System.out.println("What do you want to add?");
                    item = SavitchIn.readLineNonwhiteChar();
                    item = Character.toUpperCase(item);
                }
                
                //if the spot is empty- empty =^
                if(ocean[horizontal - 1][vertical - 1] == '^'){ 
                    //sets spot to the item
                    ocean[horizontal - 1][vertical - 1] = item;
                    
                    //increases items by 1
                    totalItems++;
                    
                    //print the ocean
                    printOcean(ocean);
                    
                    //if spot is not open
                } else{
                    System.out.println("Something is already there.");
                    System.out.println("To delete and replace it enter 1.");
                    choice = SavitchIn.readLineInt();
                    
                    //overwites if choice is 1 else returns to main menu
                    if(choice==1){
                        //replaces the item in that spot with the user's item
                        ocean[horizontal-1][vertical-1] = item;
                        
                        //adds one to the count of total items
                        totalItems++;
                        
                        //print the ocean
                        printOcean(ocean);
                    }
                }
                break;
                
            //random placement
            case 2:
                //checks if there is space
                if(totalItems == (rows*cols)){
                    System.out.println("You cannot add any items. Please remove one first.");
                }else{
                    //checks for valid input of item
                    while(!((item == FISH) || (item == SHARK) || (item == BOAT) || 
                       (item == ICEBURG) || (item == KRAKEN) || (item == ROCK) 
                       || (item == JELLYFISH) || (item == LOBSTER) || 
                       (item == OILRIG) || (item == BIRD))){
                        System.out.println("What do you want to add?");
                        item = SavitchIn.readLineNonwhiteChar();
                        item = Character.toUpperCase(item);
                    }
                    
                    //randomly selects a location
                    horizontal = r1.nextInt(cols);
                    vertical = r1.nextInt(rows);
                    
                    //checks for empty- if not empty picks another spot
                    while(ocean[horizontal][vertical] != EMPTY){
                        horizontal = r1.nextInt(cols);
                        vertical = r1.nextInt(rows);
                    }
                    
                    //places item
                    ocean[horizontal][vertical] = item;
                    
                    //increases total items by 1
                    totalItems++;
                    
                    //print the ocean
                    printOcean(ocean);
                }
                
                break;
        } 
    }
    
    //removes all the items from the ocean
    public static void removeAllItems(char[][]ocean){
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                //sets the spot to a ^ which is a blank space
                ocean[i][j]= EMPTY;
            }
        }
        
        //sets the number of items in world to zero since all were deleted
        totalItems = 0;
    }
    
    //removes one item from the ocean
    public static void removeItem(char[][]ocean){
        int rowLocation = 0;
        int colLocation = 0;
        
        //print the ocean
        printOcean(ocean);
        
        //gets the row for the item
        System.out.println("What row is the item in?");
        rowLocation = SavitchIn.readLineInt();
        
        //checks for valid input
        while(((rowLocation - 1) >rows) || ((rowLocation - 1)<0)){
            //if the nuber is too small
            if((rowLocation - 1) < 0){
                System.out.println("That number is too small. Try again.");
            }else{//if the num too big
                System.out.println("That number is too big. Try again.");
            }
            
            rowLocation = SavitchIn.readLineInt();
        }
        
        //gets the column for the item
        System.out.println("What column is the item in?");
        colLocation = SavitchIn.readLineInt();
        
        //checks for valid input
        while(((colLocation - 1) > cols) || ((colLocation-1)<0)){
            //if the num too big
            if((colLocation - 1) > cols){
                System.out.println("That number is too big. Try again.");
            }else{//if num too small
                System.out.println("That number is too small. Try again.");
            }
            
            colLocation = SavitchIn.readLineInt();
        }
        
        System.out.println("Deleting the item in row "+rowLocation+" column "+colLocation);
        
        //sets the spot to ^ which is a blank space
        ocean[rowLocation - 1][colLocation - 1] = '^';
        
        //subtracts one from the total items
        totalItems--;
        
        //print the ocean
        printOcean(ocean);
    }
    //removes all of one type of item from the ocean
    public static void removeAllTypeItems(char[][]ocean){
        char item = 'z';
        
        //prints ocean
        printOcean(ocean);
        
        //gets what user wants to remove
        System.out.println("What type of item do you want to remove?");
        item = SavitchIn.readLineNonwhiteChar();
        item = Character.toUpperCase(item);
        
        //checks for valid input
        while(!((item == FISH) || (item == SHARK) || (item == BOAT) || 
                       (item == ICEBURG) || (item == KRAKEN) || (item == ROCK) 
                       || (item == JELLYFISH) || (item == LOBSTER) || 
                       (item == OILRIG) || (item == BIRD))){
            System.out.println("What type of item do you want to remove?");
            item = SavitchIn.readLineNonwhiteChar();
            item = Character.toUpperCase(item);
        }
        
        //runs loop to remove
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                //if item in that location matches type to remove
                if(ocean[i][j] == item){
                    //change to ^- empty space
                   ocean[i][j] = EMPTY;
                   
                   //subtract 1 from total items
                   totalItems--;
                }
            }
        }
    }
    
    public static void writeFile(char[][]ocean, String file){
        //try to create a print writer and a file
        try(PrintWriter writer = new PrintWriter(new File(file))){
            //creates string containing rows, columns and ocean array
            StringBuilder oceanString = new StringBuilder();
            oceanString.append("" + rows);
            oceanString.append(",");
            oceanString.append("" + cols);
            oceanString.append('\n');

            for(int i = 0; i < rows; i++){
                for(int j = 0; j < cols; j++){
                    oceanString.append(ocean[i][j]);
                    if(j != cols - 1){
                        oceanString.append(",");
                    }
                }
                oceanString.append('\n');
            }
            
            //writes to file
            writer.write(oceanString.toString());
            System.out.println("Saving Complete");
        }
        
        //if it didnt work
        catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }
    }
    
    //saves the ocean simulation array to a file
    public static void saveSim(char[][]ocean) throws FileNotFoundException{
        int choice = 0;
        String file = "";
        
        //switchboard to pick save file 
        System.out.println("Do you want to save to save spot 1, 2, or 3.");
        System.out.println("Enter the number of the save slot you want:");
        choice = SavitchIn.readLineInt();
        
        //checks for valid input
        while((choice < 1) || (choice > 3)){
            System.out.println("Please pick 1, 2, or 3");
            choice = SavitchIn.readLineInt();
        }
        
        //switches on save spot choice
        switch(choice){
            case 1:
                file = "C:\\Users\\wrger\\oceanSave1" ;
                writeFile(ocean, file);
                break;
                
            case 2:
                file = "C:\\Users\\wrger\\oceanSave2";
                writeFile(ocean, file);
                break;
                
            case 3:
                file = "C:\\Users\\wrger\\oceanSave3";
                writeFile(ocean, file);
                break;
                
            //default error 
            default:
                System.out.print("Error");
                break;
        }
    }
    
    public static void readFile(char[][]ocean, String file){
        BufferedReader  bufferedReader= null;
        
        try{
            //sets total ocean items to 0 
            totalItems = 0;
            String sCurrentLine;
            
            //creates a bufferreader to read the saved file
            bufferedReader = new BufferedReader(new FileReader(file));
            sCurrentLine = bufferedReader.readLine();
            
            //creates an array of the contents of file
            String[] arrOfStr = sCurrentLine.split(",");
            
            //parses out the row and col data
            rows = Integer.parseInt(arrOfStr[0]);
            cols = Integer.parseInt(arrOfStr[1]);
            
            //sets the ocean array to that many rows and cols
            ocean = new char[rows][cols];
            String[] arrOfStr2;
            int j = 0;
            
            //fills in the array based off data from file
            while((sCurrentLine = bufferedReader.readLine())!=null){
                arrOfStr2 = sCurrentLine.split(",");
                for(int i=0; i<arrOfStr2.length; i++){
                    ocean[j][i]=arrOfStr2[i].charAt(0);
                }
                j++;
                //increases total items in ocean
                totalItems++;
            }
        }
        
        //if it didnt work
        catch(Exception ex){
            System.out.println(ex.toString());
        }
    }
    
    //restores a saved ocean simulation file
    public static void restoreSim(char[][]ocean){
        int choice = 0;
        String file = "";
        
        //gets the save slot the player wants to load
        System.out.println("Do you want to load from save spot 1, 2, or 3.");
        System.out.println("Enter the number of the save slot you want:");
        choice = SavitchIn.readLineInt();
        
        //checks for valid input
        while((choice < 1) || (choice > 3)){
            System.out.println("Please pick 1, 2, or 3");
            choice = SavitchIn.readLineInt();
        }
        
        //switches on sabe slot choice
        switch(choice){
            case 1:
                file = "C:\\Users\\wrger\\oceanSave1";
                readFile(ocean, file);
                
                //prints ocean
                printOcean(ocean);
                break;
                
            case 2:
                file = "C:\\Users\\wrger\\oceanSave2";
                readFile(ocean, file);
                
                //prints ocean
                printOcean(ocean);
                break;
                
            case 3:
                file = "C:\\Users\\wrger\\oceanSave3";
                readFile(ocean, file);
                
                //prints ocean
                printOcean(ocean);
                break;
                
            default:
                System.out.println("error");
        } 
    }
    
    //checks to see if an item in the ocean dies when it is it's turn to move
    public static boolean doesItDie(char item){
        boolean die = false;
        
        //if item is fish, shark, jellyfish or bird it has 5% death chance (5/100)
        if((item == FISH) || (item == SHARK) || 
                (item == SHARK) || (item == BIRD)){
            //if it dies return true
            if(r1.nextInt(100) < 5){
                totalItems--;
                die = true;
            }
        } else if((item == ICEBURG) || (item == BOAT)){//if item is iceburg or boat it has .5% death chance(5/1000)
            //if it dies return true
            if(r1.nextInt(1000) < 5){
                totalItems--;
                die = true;
            }
        } else if((item == OILRIG) || (item == KRAKEN)){//if item is oil rig or kracken it has a .1% chance of death(1/1000)
            //if it dies return true
            if(r1.nextInt(1000) < 1){
                totalItems--;
                die = true;
            }
        } else if(item == LOBSTER){//if item is lobster it has a 10% chance of death (10/100)
            //if it dies return true
            if(r1.nextInt(100) < 10){
                totalItems--;
                die = true;
            }
        } else{
            System.out.println("error");
        }
        
        return die;
    }
    
    //checks to see if an item moves
    public static boolean doesItMove(char item){
        boolean move = false;
        //if the item is a rock or an oil rig it does not move ever 
        
        switch(item){
            case FISH://fish has a 50% chance of moving- if it moves return true
                if(r1.nextInt(100) < 50){
                    move = true;
                }
                
                break;
            case SHARK://shark has a 50% chance of moving- if it moves return true
                if(r1.nextInt(100) < 50){
                    move = true;
                }
                
                break;
            case LOBSTER://lobster has a 10% chance of moving- if it moves return true
                if(r1.nextInt(100) < 10){
                    move = true;
                }
                
                break;
            case KRAKEN://kracken has a 85% chance of moving- if it moves return true
                if(r1.nextInt(100) < 85){
                    move = true;
                }
                
                break;
            case BIRD://bird has a 90% chance of moving- if it moves return true
                if(r1.nextInt(100) < 90){
                    move = true;
                }
                
                break;
            case BOAT://boat has a 80% chance of moving- if it moves return true
                if(r1.nextInt(100) < 80){
                    move = true;
                }
                
                break;
            case ICEBURG://iceburg has a 5% chance of moving- if it moves return true
                if(r1.nextInt(100) < 5){
                    move = true;
                }
                
                break;
            case JELLYFISH://jellyfish has a 15% chance of moving- if it moves return true
                if(r1.nextInt(100) < 15){
                    move = true;
                }
                
                break;
            //error
            default:
                System.out.println("error");
                break;
        }
        
        return move;
    }
    
    //deletes the eaten item based off the direction of the move
    public static void eaten(char[][]ocean, char item, int row, int col, int direction, boolean[][]checked){
        //north
        if(direction == 0){
            ocean[row+1][col] = EMPTY;
        }
        
        //east
        if(direction == 1){
            ocean[row][col-1] = EMPTY;
        }
        
        //south
        if(direction == 2){
            ocean[row-1][col] = EMPTY;
        }
        
        //west
        if(direction == 3){
            ocean[row][col+1] = EMPTY;
        }
        
        totalItems--;
    }
    
    //eat-in some cases destroy
    public static void calculateEat(char[][]ocean, char item, int row, int col, int direction, boolean[][]checked){
       //boat crash- eat- other crashes
        char movedTo = ocean[row][col];
        int destroyed = r1.nextInt(100);
        int bothDestroyed = r1.nextInt(100);
        int lobsterEaten = r1.nextInt(100);
        
        //calcuates chance of fish being eaten or eating something
        //if it is eaten/eats calls the eaten function to remove what has been eaten from the world.
        if(item == 'F'){
            if((movedTo == 'S') && (r1.nextInt(100) < 25)){ //25% chance shark eats fish
                eaten(ocean, item, row, col, direction, checked);
            } else if((movedTo == 'B') && (r1.nextInt(100) < 25)){//s5% chance boat catches fish
                eaten(ocean, item, row, col, direction, checked);
            } else if((movedTo == 'J') && (r1.nextInt(100) < 10)){//10% chance jelly eats fish
                eaten(ocean, item, row, col, direction, checked);
            } else if((movedTo == 'J') && (r1.nextInt(100) < 25)){//25% chance fish eats jelly
                ocean[row][col] = 'F';
                eaten(ocean, item, row, col, direction, checked);
            } else if((movedTo == 'K') && (r1.nextInt(100) < 90)){//90% chsnce kraken eats fish
                eaten(ocean, item, row, col, direction, checked);
            }  else if((movedTo == 'A') && (r1.nextInt(100) < 75)){//75% chance bird eats fish 
                eaten(ocean, item, row, col, direction, checked);
            } else{//marks the spot as checked 
                if(direction == 0){
                    checked[row + 1][col] = true;
                }
                
                if(direction == 1){
                    checked[row][col - 1] = true;
                }
                
                if(direction == 2){
                    checked[row - 1][col] = true;
                }
                
                if(direction == 3){
                    checked[row][col + 1] = true;
                }
            }
        }
        
        //calcuates chance of shark being eaten or eating something
        //if it is eaten/eats calls the eaten function to remove what has been eaten from the world.
        if(item == 'S'){
            if((movedTo == 'F') && (r1.nextInt(100) < 75)){//75% chance shark eats fish
                ocean[row][col] = 'S';
                eaten(ocean, item, row, col, direction, checked);
            } else if((movedTo == 'B') && (r1.nextInt(100) < 5)){//5% chance shark breaks boat
                ocean[row][col] = 'S';
                eaten(ocean, item, row, col, direction, checked);
            } else if((movedTo == 'B') && (r1.nextInt(100) < 10)){//10% chance boat catches shark
                eaten(ocean, item, row, col, direction, checked);
            } else if((movedTo == 'J') && (r1.nextInt(100) < 75)){//75% chance shark eats jelly
                ocean[row][col] = 'S';
                eaten(ocean, item, row, col, direction, checked);
            } else if((movedTo == 'K') && (r1.nextInt(100) < 90)){//90% chance kraken eats shark
                eaten(ocean, item, row, col, direction, checked);
            } else if((movedTo == 'L') && (r1.nextInt(100)<75)){//75% chance shark eats lobster
                ocean[row][col] = 'S';
                eaten(ocean, item, row, col, direction, checked);
            } else if((movedTo == 'A') && (r1.nextInt(100)<25)){//25% chance shark eats bird
                ocean[row][col] = 'S';
                eaten(ocean, item, row, col, direction, checked);
            }else{//nothing happens - marks spot as checked 
                if(direction == 0){
                    checked[row + 1][col] = true;
                }
                
                if(direction == 1){
                    checked[row][col - 1] = true;
                }
                
                if(direction == 2){
                    checked[row - 1][col] = true;
                }
                
                if(direction == 3){
                    checked[row][col+1] = true;
                }
            }
        }
        
        //calcuates chance of the iceburg being destroyed or destroying something
        //if it is destoryed/destroys calls the eaten function to remove what has been destroyed from the world.
        if(item == 'I'){
            if((movedTo == 'B') && (r1.nextInt(100) < 55)){//55% chance iceburg destroys boat
                ocean[row][col] = 'I';
                eaten(ocean, item, row, col, direction, checked);
            } else if((movedTo == 'R') && (r1.nextInt(100) < 20)){//20% chance iceburg destroys rock
                ocean[row][col] = 'I';
                eaten(ocean, item, row, col, direction, checked);
            } else if((movedTo == 'K') && (r1.nextInt(100) < 10)){//10% chance iceburg kills kraken
                ocean[row][col] = 'I';
                eaten(ocean, item, row, col, direction, checked);
            } else if((movedTo == 'O') && (r1.nextInt(100) < 95)){//95% chance iceburg destroys oilrig
                ocean[row][col] = 'I';
                eaten(ocean, item, row, col, direction, checked);
            }else{//marks spot as checked
                if(direction == 0){
                    checked[row + 1][col] = true;
                }
                
                if(direction == 1){
                    checked[row][col - 1] = true;
                }
                
                if(direction == 2){
                    checked[row - 1][col] = true;
                }
                
                if(direction == 3){
                    checked[row][col + 1] = true;
                }
            }
        }
        
        //calcuates chance of the boat being destroyed or destroying something
        //if it is destoryed/destroys calls the eaten function to remove what has been destroyed from the world.
        if(item=='B'){
            if((movedTo=='F')&&(r1.nextInt(100)<75)){//75% chance boat catches fish
                ocean[row][col]='B';
                eaten(ocean, item, row, col, direction, checked);
            }
            else if((movedTo=='S')&&(r1.nextInt(100)<5)){//5% chance shark breaks boat
                eaten(ocean, item, row, col, direction, checked);
            }
            else if((movedTo=='S')&&(r1.nextInt(100)<25)){//25% chance boat catches shark
                ocean[row][col]='B';
                eaten(ocean, item, row, col, direction, checked);
            }
            else if((movedTo=='I')&&(r1.nextInt(100)<25)){//25% chance iceburg destroys boat
                eaten(ocean, item, row, col, direction, checked);
            }
            else if((movedTo=='R')&&(r1.nextInt(100)<25)){//25% chance rock destroys boat 
                eaten(ocean, item, row, col, direction, checked);
            }
            else if((movedTo=='J')&&(r1.nextInt(100)<25)){//25% chance boat catches jelly
                ocean[row][col]='B';
                eaten(ocean, item, row, col, direction, checked);
            }
            else if((movedTo=='K')&&(r1.nextInt(100)<90)){//90% chance kraken destroys boat
                eaten(ocean, item, row, col, direction, checked);
            }
            else if((movedTo=='L')&&(r1.nextInt(100)<25)){//25% chance boat catches lobster
                ocean[row][col]='B';
                eaten(ocean, item, row, col, direction, checked);
            }
            else if((movedTo=='A')&&(r1.nextInt(100)<5)){//5% chance boat kills bird
                ocean[row][col]='B';
                eaten(ocean, item, row, col, direction, checked);
            }
            else if((movedTo=='O')&&(r1.nextInt(100)<25)){//25% chance boat kills oil rig
                ocean[row][col]='B';
                eaten(ocean, item, row, col, direction, checked);
            }
            else if((movedTo=='O')&&(destroyed <25)){//25% chance oil rig destroys boat
                eaten(ocean, item, row, col, direction, checked);
            }
            else if((movedTo=='O')&&(bothDestroyed <25)){ //25% chance both are destroyed
                ocean[row][col]='^';
                eaten(ocean, item, row, col, direction, checked);
            }else{//marks spot as checked
                if(direction==0){
                    checked[row+1][col]=true;
                }
                
                if(direction==1){
                    checked[row][col-1]=true;
                }
                
                if(direction==2){
                    checked[row-1][col]=true;
                }
                
                if(direction==3){
                    checked[row][col+1]=true;
                }
            }
        }
        
        //calcuates chance of kraken being eaten or eating something
        //if it is eaten/eats calls the eaten function to remove what has been eaten from the world.
        if(item=='K'){
            if((movedTo=='F')&&(r1.nextInt(100)<98)){//98%chance kraken eats fish
                ocean[row][col]='K';
                eaten(ocean, item, row, col, direction, checked);
            }
            else if((movedTo=='S')&&(r1.nextInt(100)<98)){//98% chance kraken eats shark
                ocean[row][col]='K';
                eaten(ocean, item, row, col, direction, checked);
            }
            else if((movedTo=='B')&&(r1.nextInt(100)<98)){//98% chance kraken destorys boat
                ocean[row][col]='K';
                eaten(ocean, item, row, col, direction, checked);
            }
            else if((movedTo=='L')&&(r1.nextInt(100)<98)){//98% chance kraken eats lobster
                ocean[row][col]='K';
                eaten(ocean, item, row, col, direction, checked);
            }
            else if((movedTo=='J')&&(r1.nextInt(100)<98)){//98% chance kraken eats jelly
                ocean[row][col]='K';
                eaten(ocean, item, row, col, direction, checked);
            }
            else if((movedTo=='A')&&(r1.nextInt(100)<80)){//80% chance kraken eats bird
                ocean[row][col]='K';
                eaten(ocean, item, row, col, direction, checked);
            }
            else if((movedTo=='O')&&(r1.nextInt(100)<98)){//98% chance kraken destroys oil rig
                ocean[row][col]='K';
                eaten(ocean, item, row, col, direction, checked);
            }else{//marked as checked
                if(direction==0){
                    checked[row+1][col]=true;
                }
                
                if(direction==1){
                    checked[row][col-1]=true;
                }
                
                if(direction==2){
                    checked[row-1][col]=true;
                }
                
                if(direction==3){
                    checked[row][col+1]=true;
                }
            }
        }
        
        //calcuates chance of jellyfish being eaten or eating something
        //if it is eaten/eats calls the eaten function to remove what has been eaten from the world.
        if(item=='J'){
            if((movedTo=='F')&&(r1.nextInt(100)<25)){//25% chance fish eats jelly
                eaten(ocean, item, row, col, direction, checked);
            }
            else if((movedTo=='S')&&(r1.nextInt(100)<75)){//75% chance shark eats jelly
                eaten(ocean, item, row, col, direction, checked);
            }
            else if((movedTo=='B')&&(r1.nextInt(100)<25)){//25% chance boat captures jelly
                eaten(ocean, item, row, col, direction, checked);
            }
            else if((movedTo=='K')&&(r1.nextInt(100)<90)){//90% chance kraken eats jelly
                eaten(ocean, item, row, col, direction, checked);
            }
            else if((movedTo=='A')&&(r1.nextInt(100)<75)){//75% chance bird eats jelly
                eaten(ocean, item, row, col, direction, checked);
            }else{//mark as checked
                if(direction==0){
                    checked[row+1][col]=true;
                }
                
                if(direction==1){
                    checked[row][col-1]=true;
                }
                
                if(direction==2){
                    checked[row-1][col]=true;
                }
                
                if(direction==3){
                    checked[row][col+1]=true;
                }
            }
        }
        
        //calcuates chance of lobster being eaten or eating something
        //if it is eaten/eats calls the eaten function to remove what has been eaten from the world.
        if(item=='L'){
            if((movedTo=='F')&&(r1.nextInt(100)<25)){//25% chance lobster eats fish
                ocean[row][col]='L';
                eaten(ocean, item, row, col, direction, checked);
            }
            else if((movedTo=='F')&&(lobsterEaten<50)){//50% chance fish eats lobster
                eaten(ocean, item, row, col, direction, checked);
            }
            else if((movedTo=='S')&&(r1.nextInt(100)<25)){//25% chance shark eats lobster
                eaten(ocean, item, row, col, direction, checked);
            }
            else if((movedTo=='B')&&(r1.nextInt(100)<25)){//25% chance boat captures lobster
                eaten(ocean, item, row, col, direction, checked);
            }
            else if((movedTo=='J')&&(r1.nextInt(100)<25)){//25% chance lobster eats jelly
                ocean[row][col]='L';
                eaten(ocean, item, row, col, direction, checked);
            }
            else if((movedTo=='K')&&(r1.nextInt(100)<90)){//90% chance kraken eats lobster
                eaten(ocean, item, row, col, direction, checked);
            }else{//marks as checked
                if(direction==0){
                    checked[row+1][col]=true;
                }
                
                if(direction==1){
                    checked[row][col-1]=true;
                }
                
                if(direction==2){
                    checked[row-1][col]=true;
                }
                
                if(direction==3){
                    checked[row][col+1]=true;
                }
            }
        }
        //calcuates chance of bird being eaten or eating something
        //if it is eaten/eats calls the eaten function to remove what has been eaten from the world.
        if(item=='A'){
            if((movedTo=='F')&&(r1.nextInt(100)<50)){//50% chance bird eats fish
                ocean[row][col]='A';
                eaten(ocean, item, row, col, direction, checked);
            }
            else if((movedTo=='S')&&(r1.nextInt(100)<25)){//25% chance shark eats bird
                eaten(ocean, item, row, col, direction, checked);
            }
            else if((movedTo=='K')&&(r1.nextInt(100)<80)){//80% chance kraken eats bird
                eaten(ocean, item, row, col, direction, checked);
            }else{//marked as checked
                if(direction==0){
                    checked[row+1][col]=true;
                }
                
                if(direction==1){
                    checked[row][col-1]=true;
                }
                
                if(direction==2){
                    checked[row-1][col]=true;
                }
                
                if(direction==3){
                    checked[row][col+1]=true;
                }
            }
        }
    }
    //logic for if something occurs if 2 of same object meet 
    public static boolean doesItMate(char item){
        boolean mate = false;
        
        //used for checking range with mate chance
        chance = r1.nextInt(100);
        
        //if the object is a creature that mates it has a 50% chance of mating
        if(((item == FISH) || (item == SHARK) || (item == KRAKEN) || 
            (item == LOBSTER) || (item == BIRD ))&&(r1.nextInt(100) < 50)){
            mate = true;
        } else if(((item == ICEBURG) && (r1.nextInt(100) < 25)) || 
                ((item == ICEBURG) && (chance>24) && (chance < 50))){//if it is an iceburg it has a 25% chance of making a new one or being destroyed
            mate = true;
        } else if(((item == BOAT) && (r1.nextInt(100) < 25))
                || ((item == BOAT) && (chance > 24) && (chance < 50))){//if it is a boat it has a 25% chance of sinking it or both boats
            mate = true;
        }else{
            mate = false; //change??????
        }
        
        return mate;
    }
    
    public static void mate(char[][]ocean, boolean[][]checked, char item, int row, int col, int direction){
        int spot = r1.nextInt(4);
        //colcheck/rowcheck - checks that col/row is not outside the array on the negative side
        int colCheck = col-1;
        int rowCheck = row-1;
        int rowNum = rows-1;
        int colNum = cols-1;
       
        //checks if something new can be spawned
        if(((item != BOAT) && (item != JELLYFISH)) || ((item == ICEBURG) && 
                ((chance > 24) && (chance < 50)))){
            //north
            if(spot == 0){
                //checks empty- if so places there
                if(rowCheck > -1){
                    if(ocean[rows-1][col] == EMPTY){
                        ocean[row-1][col]=item;
                        totalItems++;
                    }
                } else if((col < colNum) && (ocean[row][col+1]== EMPTY)){//checks east
                   ocean[row][col+1] = item;
                   checked[row][col+1] = true;
                   totalItems++;
                } else if(row < rowNum){//checks south
                    if(ocean[row+1][col] == EMPTY){
                        ocean[row+1][col] = item;
                        checked[row+1][col]=true;
                        totalItems++;
                    }
                } else if(colCheck > -1){//checks west
                    if(ocean[row][col-1]=='^'){
                        ocean[row][col-1] = item;
                        totalItems++;
                    }
                } else{//no spots = no placement
                    checked[row][col]=true;
                }   
            }
            
            //east
            if(spot == 1){
                //checks empty - if so places
                if(col < colNum){
                    if(ocean[row][col+1] == EMPTY){
                        ocean[row][col+1] = item;
                        checked[row][col+1] = true;
                        totalItems++;
                    }
                } else if(row < rowNum){//checks south
                    if(ocean[row+1][col] == EMPTY){
                        ocean[row+1][col] = item;
                        checked[row+1][col] = true;
                        totalItems++;
                    }
                } else if(colCheck > -1){//checks west
                    if(ocean[row][col-1] == EMPTY){
                        ocean[row][col-1] = item;
                        totalItems++;
                    }
                } else if(rowCheck > -1){//checks north
                    if(ocean[row-1][col] == EMPTY){
                        ocean[row-1][col] = item;
                        totalItems++;
                    }
                } else{//no open spot - no item placed
                    checked[row][col] = true;
                }
            } 
            
            //south
            if(spot == 2){
                //checks if open
                if(row < rowNum){
                    if(ocean[row+1][col] == EMPTY){
                        ocean[row+1][col] = item;
                        checked[row+1][col] = true;
                        totalItems++;
                    }
                } else if(colCheck > -1){//checks west
                    if(ocean[row][col-1] == EMPTY){ 
                        ocean[row][col-1] = item;
                        totalItems++;
                    }
                } else if(rowCheck > -1){//checks north
                    if(ocean[row-1][col] == EMPTY){
                        ocean[row-1][col] = item;
                        totalItems++;
                    }
                } else if(col < colNum){//checks east
                    if(ocean[row][col+1] == EMPTY){
                        ocean[row][col+1] = item;
                        checked[row][col+1] = true;
                        totalItems++;
                    }
                } else{//no open spot- does not place item
                    checked[row][col] = true;
                }   
            } 
            
            //west
            if(spot == 3){
                //checks for open spot
                if(colCheck > -1){
                    if(ocean[row][col-1] == EMPTY){
                        ocean[row][col-1] = item;
                        totalItems++;
                    }
                } else if(rowCheck > -1){//check north
                    if(ocean[row-1][col] == EMPTY){
                        ocean[row-1][col] = item;
                        totalItems++;
                    }
                } else if(col < colNum){//check east
                    if(ocean[row][col+1] == EMPTY){
                        ocean[row][col+1] = item;
                        checked[row][col+1] = true;
                        totalItems++;
                    }
                } else if(row < rowNum){//check south
                    if(ocean[row+1][col] == EMPTY){
                        ocean[row+1][col] = item;
                        checked[row+1][col] = true;
                        totalItems++;
                    }
                } else{//no open spot- item not placed
                    checked[row][col] = true;
                }    
            } 
        }
        
        //if iceburg hits iceburg and both destroyed
        if(item == ICEBURG){
            if(chance < 25){
                //destroys the moved onto iceburg
                ocean[row][col]= EMPTY;
                //destroys the moving iceburg based off direction it came from
                if(direction == 0){
                    ocean[row-1][col]= EMPTY;
                }
                
                if(direction == 1){
                    ocean[row][col+1] = EMPTY;
                }
                
                if(direction == 2){
                    ocean[row+1][col] = EMPTY;
                }
                
                if(direction == 3){
                    ocean[row][col-1] = EMPTY;
                }
                
                totalItems=totalItems-2;
            }
        }
        
        //if boat moves onto boat and both are destroyed
        if(item == BOAT){
            if(chance < 25){
                //destroys moved onto boat
                ocean[row][col] = EMPTY;
                //detroys moving boat based off direction it comes from
                if(direction == 0){
                    ocean[row-1][col] = EMPTY;
                }
                
                if(direction == 1){
                    ocean[row][col+1] = EMPTY;
                }
                
                if(direction == 2){
                    ocean[row+1][col] = EMPTY;
                }
                
                if(direction == 3){
                    ocean[row][col-1] = EMPTY;
                }
                
                totalItems=totalItems-2;
            } else{//one boat is destroyed- the moving boat
                //makes sure the boat that is moved onto is kept 
                ocean[row][col] = BOAT;
                //destroys moving boat based off direction it comes from
                if(direction == 0){
                    ocean[row-1][col] = EMPTY;
                }
                
                if(direction == 1){
                    ocean[row][col+1] = EMPTY;
                }
                
                if(direction == 2){
                    ocean[row+1][col] = EMPTY;
                }
                
                if(direction == 3){
                    ocean[row][col-1] = EMPTY;
                }
                
                totalItems--;
            }
        }
    }
    
    //checks if the creature escapes the iceburg or is crushed by it- handles crushing and moving of iceburg
    public static void crushed(char[][] ocean, char item, int row, int col, int direction, boolean[][]checked){
        char movedOnto = ocean[row][col];
        
        //sees if creature can escape
        if(doesItMove(item)){
            move(ocean, item, checked, row, col);
            
            //if it mated or was unsuccessful in move or eat
            if(ocean[row][col] == movedOnto){ 
                //sets spot to iceburg
                ocean[row][col] = item;
                
                //subtracts one item from the total
                totalItems--;
                
                //sets the spot to be marcked as checked
                checked[row][col] = true;
            } else{//if the spot became empty set it to an iceburg
                ocean[row][col] = item;
                
                //sets the spot to be marched as checked
                checked[row][col] = true;
            }
        }else{
            //set the spot to iceburg and subtract 1 item
            ocean[row][col] = item;
            totalItems--;
            
            //sets the spot to be marked as checked
            checked[row][col] = true;
        }
    }
    
    //move west - direction = 3
    public static void moveWest(char[][]ocean, boolean[][]checked, char item, int row, int col, int direction){
        //Sets a number to test that collumn to left exsists without changing collumn number
        int colCheck = col-1;
        
        //checks if the spot is within the array
        if(colCheck > -1){
            //checks if the spot is open - if so moves there
            if(ocean[row][col-1] == EMPTY){
                ocean[row][col-1] = item;
                ocean[row][col]= EMPTY;
                checked[row][col-1] = true;
                checked[row][col] = true;
            } else if(ocean[row][col-1] == item){//checks if the spot containst the same type of item as is moving there 
                //if so checks to see if it mates/interacts - if so calls mate message
                if(doesItMate(item)){
                    mate(ocean, checked, item, row, col-1, direction);
                }
                checked[row][col-1] = true;
                checked[row][col] = true;
            } else if(((ocean[row][col-1] == OILRIG) && (item != KRAKEN)) || 
                    ((ocean[row][col-1] == OILRIG) && (item != ICEBURG))
                    ||((ocean[row][col-1] == OILRIG) && (item != BOAT)) || 
                    ((ocean[row][col-1] == ROCK))){//checks to see if it is a different item it can eat/destroy
                //handles special interaction for iceburgs
                if(item == ICEBURG){
                    if((ocean[row][col-1] == FISH) || (ocean[row][col-1] == SHARK)
                        ||(ocean[row][col-1] == BIRD) || (ocean[row][col-1] == JELLYFISH)
                        ||(ocean[row][col-1] == LOBSTER)){
                        crushed(ocean, item, row-1, col, direction, checked);
                    } else if((ocean[row][col-1] == OILRIG) || (ocean[row][col-1] == BOAT)
                            ||(ocean[row][col-1] == ROCK) || (ocean[row][col-1] == KRAKEN)){
                        calculateEat(ocean, item, row, col-1, direction, checked);
                        checked[row][col-1] = true;
                        checked[row][col] = true;
                    } else{
                        System.out.println("Error");
                    }
                }
                
                //if so it calls eat method -------change to check if eat then eat????????????
                calculateEat(ocean, item, row, col-1, direction, checked);
                checked[row][col-1] = true;
                checked[row][col] = true;
            } else if(direction != 0) {//checks if it has not started at the next poition(north) - if not it calls check of next position
                moveEast(ocean, checked, item, row, col, direction);
                checked[row][col] = true;
            }else{
                //marks the spot as checked- nothing happens 
                checked[row][col] = true;
            }
       }
    }
    
    //move south - direction =2
    public static void moveSouth(char[][]ocean, boolean[][]checked, char item, 
            int row, int col, int direction){
        int rowNum = rows-1;
        
        //checks if the spot is within the array
        if(rowNum > row){
            //checks if the spot is open - if so moves there
            if(ocean[row+1][col] == EMPTY){
                ocean[row+1][col]=item;
                ocean[row][col] = EMPTY;
                checked[row+1][col] = true;
                checked[row][col] = true;
            } else if(ocean[row+1][col] == item){//checks if the spot containst the same type of item as is moving there 
                //if so checks to see if it mates/interacts - if so calls mate message
                if(doesItMate(item)){
                  mate(ocean, checked, item, row+1, col, direction);  
                }
                checked[row+1][col] = true;
                checked[row][col] = true;
            } else if(((ocean[row+1][col] == OILRIG) && (item != KRAKEN)) ||
                    ((ocean[row+1][col] == OILRIG) && (item != ICEBURG)) ||
                    ((ocean[row+1][col] == OILRIG) && (item != BOAT))||
                    ((ocean[row+1][col] == ROCK))){//checks to see if it is a different item it can eat/destroy
                //handles special interactions for iceburgs
                if(item == ICEBURG){
                    if((ocean[row+1][col] == FISH) || (ocean[row+1][col] == SHARK) ||
                    (ocean[row+1][col] == BIRD) || (ocean[row+1][col] == JELLYFISH)
                    || (ocean[row+1][col] == LOBSTER)){
                        crushed(ocean, item, row-1, col, direction, checked);
                    } else if((ocean[row+1][col] == OILRIG) || 
                        (ocean[row+1][col] == BOAT) || (ocean[row+1][col] == ROCK)
                        || (ocean[row+1][col] == KRAKEN)){
                        calculateEat(ocean, item, row+1, col, direction, checked);
                        checked[row+1][col]=true;
                        checked[row][col]=true;
                    } else{
                        System.out.println("Error");
                    }
                }
                
                calculateEat(ocean, item, row+1, col, direction, checked);
                checked[row+1][col] = true;
                checked[row][col] = true;
            } else if(direction != 3) {//checks if it has not started at the next poition(west) - if not it calls check of next position
                    moveEast(ocean, checked, item, row, col, direction);
                    checked[row][col] = true;
            }else{
                //marks the spot as checked- nothing happens 
                checked[row][col] = true;
            }
        }
    }
    
    //move east- doirection = 1
    public static void moveEast(char[][]ocean, boolean[][]checked, char item, int row, int col, int direction){
        int colNum = cols-1;
        //checks if the spot is within the array
        if(col < colNum){
            //checks if the spot is open - if so moves there
            if(ocean[row][col+1] == EMPTY){
                ocean[row][col+1] = item;
                ocean[row][col] = EMPTY;
                checked[row][col+1] = true;
                checked[row][col] = true;
            } else if(ocean[row][col+1] == item){//checks if the spot containst the same type of item as is moving there 
                //if so checks to see if it mates/interacts - if so calls mate message
                if(doesItMate(item)){
                    mate(ocean, checked, item, row, col+1, direction);
                }
                checked[row][col+1] = true;
                checked[row][col] = true;
            } else if(((ocean[row][col+1] == OILRIG) && (item != KRAKEN)) ||
                    ((ocean[row][col+1] == OILRIG) && (item != ICEBURG)) ||
                    ((ocean[row][col+1] == OILRIG) && (item != BOAT)) || 
                    ((ocean[row][col+1] == ROCK))){ //checks to see if it is a different item it can eat/destroy
                if(item == ICEBURG){
                    if((ocean[row][col+1] == FISH) || (ocean[row][col+1] == SHARK) ||
                      (ocean[row][col+1] == BIRD) || (ocean[row][col+1] == JELLYFISH)||
                      (ocean[row][col+1] == LOBSTER)){
                        crushed(ocean, item, row-1, col, direction, checked);
                    } else if((ocean[row][col+1] == OILRIG) || (ocean[row][col+1] == BOAT)
                            ||(ocean[row][col+1] == ROCK) || (ocean[row][col+1] == KRAKEN)){
                        calculateEat(ocean, item, row, col+1, direction, checked);
                        checked[row][col+1] = true;
                        checked[row][col] = true;
                    } else{
                        System.out.println("Error");
                    }
                }
                
                //if so it calls eat method -------change to check if eat then eat????????????
                calculateEat(ocean, item, row, col+1, direction, checked);
                checked[row][col+1] = true;
                checked[row][col] = true;
            } else if(direction != 2) {//checks if it has not started at the next poition(south) - if not it calls check of next position
                    moveSouth(ocean, checked, item, row, col, direction);
                    checked[row][col] = true;
            }else{
                //marks the spot as checked- nothing happens 
                checked[row][col] = true;
            }
        }
    }
    
    //move north - deirection = 0
    public static void moveNorth(char[][]ocean, boolean[][]checked, char item, int row, int col, int direction){
        //Sets a number to test that row above exsists without changing row number
        int rowCheck = row-1; 
        
        //checks if the spot is within the array
        if(rowCheck > -1){
            //checks if the spot is open - if so moves there
            if(ocean[row-1][col] == EMPTY){
                ocean[row-1][col] = item;
                ocean[row][col] = EMPTY;
                checked[row-1][col] = true;
                checked[row][col] = true;
            } else if(ocean[row-1][col] == item){//checks if the spot containst the same type of item as is moving there 
                //if so checks to see if it mates/interacts - if so calls mate message
                if(doesItMate(item)){
                    mate(ocean, checked, item, row-1, col, direction);
                }
                checked[row-1][col] = true;
                checked[row][col] = true;
            } else if(((ocean[row-1][col] == OILRIG) && (item != KRAKEN)) ||
                    ((ocean[row-1][col] == OILRIG) && (item != ICEBURG)) ||
                    ((ocean[row-1][col] == OILRIG) && (item != BOAT)) ||
                    ((ocean[row-1][col] == ROCK))){
                //handles special interactions for iceburgs
                if(item == 'I'){
                    if((ocean[row-1][col] == FISH) || (ocean[row-1][col] == SHARK) ||
                      (ocean[row-1][col]== BIRD) || (ocean[row-1][col] == JELLYFISH) ||
                      (ocean[row-1][col] == LOBSTER)){//checks for certian combos that cannot occur
                        crushed(ocean, item, row-1, col, direction, checked);
                    }else if((ocean[row-1][col] == OILRIG) || 
                            (ocean[row-1][col] == BOAT) || (ocean[row-1][col] == ROCK)
                            ||(ocean[row-1][col] == KRAKEN)){
                        calculateEat(ocean, item, row-1, col, direction, checked);
                        checked[row-1][col] = true;
                        checked[row][col] = true;
                    } else{
                        System.out.println("Error");
                    }
                }
                
                //if so it calls method to see if eat is succesful
                calculateEat(ocean, item, row-1, col, direction, checked);
                checked[row-1][col] = true;
                checked[row][col] = true;
            } else if(direction != 1) {//checks if it has not started at the next poition(south) - if not it calls check of next position
                moveEast(ocean, checked, item, row, col, direction);
                checked[row-1][col] = true;
                checked[row][col] = true;
            }else{
                //marks the spot as checked- nothing happens 
               checked[row][col] = true; 
            }
        }
    }
    
    public static void move(char[][] ocean, char item, boolean[][]checked, int row, int col){
        int moveDirection = 4; //0 is north, 1 is eat, 2 is south, 3 is west 
        
        //gets a random number(0, 1, 2, 3) to pick random movement direction
        moveDirection=r1.nextInt(4);
        
        //calls move method based off number
        if(moveDirection == 0){
            moveNorth(ocean, checked, item, row, col, moveDirection);
        } else if(moveDirection == 1){
            moveEast(ocean, checked, item, row, col, moveDirection);
        } else if(moveDirection == 2){
            moveSouth(ocean, checked, item, row, col, moveDirection);
        }else{
            moveWest(ocean, checked, item, row, col, moveDirection);
        }
    }
    
    //advances time once
    public static void advanceOneTime(char[][]ocean){
        //what is in the array spot being checked
        char item = 'z';
        
        //position of item
        int row = 0;
        int col = 0;
        
        //array to check what spots have been checked 
        boolean [][] checked = new boolean[rows][cols];
        
        //runs through ocean
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                item = ocean[i][j];
                //if item is an empty spot - set checked array to true and move to next spot
                if(item == EMPTY){
                  checked[i][j] = true;  
                } else if(checked[i][j] == false){//if not empty and has not been checked 
                    //check for random death
                    if(doesItDie(item)){
                        //if dies set to empty and marck spot checked
                        ocean[i][j]= EMPTY;
                        checked[i][j] = true;
                    }else{
                        //checks if jellysish spawns a new jellyfish 
                        if((item == JELLYFISH) && (r1.nextInt(100) < 50)){
                            row = i;
                            col = j;
                            if((i < rows) && (ocean[i+1][j]== EMPTY)){
                                ocean[i+1][j] = JELLYFISH;
                                totalItems++;
                                checked[i+1][j] = true;
                            } else if((j < cols) && (ocean[i][j+1] == EMPTY)){
                                ocean[i][j+1] = JELLYFISH;
                                totalItems++;
                                checked[i][j+1] = true;
                            } else if((col-1>-1) && (ocean[i][j-1] == EMPTY)){
                                ocean[i][j-1] = JELLYFISH;
                                totalItems++;
                                checked[i][j-1] = true;
                            } else if((row-1 > -1)&&(ocean[i-1][j] == EMPTY)){
                                ocean[i-1][j] = JELLYFISH;
                                totalItems++;
                                checked[i-1][j] = true;
                            } else{
                                checked[i][j] = true;
                            }
                        } else if(doesItMove(item)){//if not check for move and move if possible
                            row = i;
                            col = j;
                            move(ocean, item, checked, row, col);
                            checked[i][j] = true;
                        }else{
                            checked[i][j] = true;
                        }
                    }
                }else{
                    checked[i][j] = true;
               }
            }
        }
        
        //prints ocean
        printOcean(ocean);
    }
    
    //advances time muliple times
    public static void advanceMultiTime(char[][]ocean){
        int time = 0;
        
        //how many times ocean has been gone through
        int timePassed =0;
        
        //item in array spot
        char item = 'z';
        
        //location of item
        int row = 0;
        int col = 0;
        
        //array to know which positions has been checked
        boolean[][] checked = new boolean[rows][cols];
        
        //gets user input for time to pass
        System.out.println("How many units of time would you like to pass?");
        time = SavitchIn.readLineInt();
        
        //checks that time is greater than 0
        while(time < 0){
            System.out.println("The number of time unites must be 1 or greater. Try again.");
            time = SavitchIn.readLineInt();   
        }
        
        //while the number of times the simulation has been gone through is less than the number of 
        //times entered by the user
        while(timePassed < time){
            for(int i = 0; i < rows; i++){
                for(int j = 0; j < cols; j++){
                    item = ocean[i][j];
                    //if spot is empty- set it to checked
                    if(item == EMPTY){
                        checked[i][j] = true;
                    } else if(checked[i][j] == false){//else if spot has not been checked yet
                        //check if it dies - if it does set the spot empty and checked to true
                        if(doesItDie(item)){
                            ocean[i][j] = EMPTY;
                            checked[i][j] = true;
                        //if it lives
                        } else{
                            //checks to see if jellyfish spawns a new jellyfish 
                            if((item == JELLYFISH) && (r1.nextInt(100) < 50)){
                                row = i;
                                col = j;
                                if((i < rows) && (ocean[i+1][j] == EMPTY)){
                                    ocean[i+1][j] = JELLYFISH;
                                    totalItems++;
                                    checked[i+1][j] = true;
                                } else if((j < cols) && (ocean[i][j+1] == EMPTY)){
                                    ocean[i][j+1] = JELLYFISH;
                                    totalItems++;
                                    checked[i][j+1] = true;
                                } else if((col-1 > -1) && (ocean[i][j-1] == EMPTY)){
                                    ocean[i][j-1] = JELLYFISH;
                                    totalItems++;
                                    checked[i][j-1] = true;
                                } else if((row-1 > -1) && (ocean[i-1][j] == EMPTY)){
                                    ocean[i-1][j] = JELLYFISH;
                                    totalItems++;
                                    checked[i-1][j] = true;
                                } else{
                                    checked[i][j] = true;
                                }
                            } else if(doesItMove(item)){//check if it moves - if so call move
                                row = i;
                                col = j;
                                move(ocean, item, checked, row, col);
                            }else{
                                checked[i][j] = true;
                            }
                        }
                    } else{//if it has been checked- just marks it as checked again
                        checked[i][j]=true;
                    }
                    
                    //increases the count of the run throughs
                    timePassed++;
                }
            }
        }
        
        //prints the ocean 
        printOcean(ocean);
    }
    
    //runs the core code for the ocean simulator
    public static void runOceanSim() throws FileNotFoundException{
        int choice = 0 ;
        int subChoice = 0;
        
        System.out.println("How many rows do you want? You can have up to 999 rows.");
        rows =SavitchIn.readLineInt();
        System.out.println("How many Collumns do you want? You can have up to 999 collumns");
        cols= SavitchIn.readLineInt();
        
        //creates ocean array
        char [][] ocean = new char[rows][cols];
        
        //puts in default values for ocean array
        initOcean(ocean);
        
        //prints ocean
        printOcean(ocean);
        
        //runs the simulator based off user choices
        while(choice != 6){
            System.out.println("What do you want to do?");
            System.out.println("Enter 1 to make time advance.");
            System.out.println("Enter 2 to add one or more items.");
            System.out.println("Enter 3 to remove one or more items.");
            System.out.println("Enter 4 to save the ocean simulation.");
            System.out.println("Enter 5 to restore a previous ocean simulation.");
            System.out.println("Enter 6 to quit the program.");
            choice = SavitchIn.readLineInt();
            
            switch (choice){
                //advance time
                case 1:
                    //gets if they one one time unit or many
                    System.out.println("Do you want to advance time once or multiple times?");
                    System.out.println("Enter 1 for one time and 2 for many");
                    
                    //checks for valid input
                    while((subChoice < 1)||(subChoice > 2)){
                            subChoice = SavitchIn.readLineInt();
                    }
                    
                    switch(subChoice){
                        //one unit of time
                        case 1:
                            advanceOneTime(ocean);
                            
                            //Resets subchoice
                            subChoice = 0;
                            break;
                            
                        //many units of time
                        case 2:
                            advanceMultiTime(ocean);
                            
                            //Resets subchoice
                            subChoice = 0;
                            break;
                            
                        default:
                            System.out.println("Error 1");
                            
                            //Resets subchoice
                            subChoice = 0;
                            break;
                    }
                    break;
                    
                //add item(s)
                case 2:
                    //as if they want to add one or many items
                    System.out.println("Do you want to add one item or multiple items?");
                    System.out.println("Enter 1 for one item, and 2 for many items");
                    
                    //checks for valid input
                    while((subChoice < 1)||(subChoice > 2)){
                            subChoice = SavitchIn.readLineInt();
                    }
                    
                    switch(subChoice){
                        //one item
                        case 1:
                            addItem(ocean);
                            
                            //resets subchoice for next loop
                            subChoice = 0;
                            break;
                            
                        //many items
                        case 2:
                            addItems(ocean);
                            
                            //resets subchoice for next loop
                            subChoice = 0;
                            break;
                            
                        default:
                            System.out.println("Error");
                            
                            //Resets subchoice
                            subChoice = 0;
                            break;
                    }
                    break; 
                    
                //remove item(s)
                case 3:
                    //gets if they want to remove all, a type or one item. 
                    System.out.println("Do you want to remove all ites, one ites, or all of one type of item?");
                    System.out.println("Enter 1 for all items, 2 for one item, and 3 all of one item.");
                    
                    //checks for valid input
                    while((subChoice < 1)||(subChoice > 3)){
                            subChoice = SavitchIn.readLineInt();
                    }
                    
                    switch(subChoice){
                        //all items
                        case 1:
                            removeAllItems(ocean);
                            
                            //resets subchoice
                            subChoice = 0;
                            break;
                            
                        //one item
                        case 2:
                            removeItem(ocean);
                            
                            //resets subchoice
                            subChoice = 0;
                            break;
                            
                        //type of item
                        case 3:
                            removeAllTypeItems(ocean);
                            
                            //resets subchoice
                            subChoice = 0;
                            break;
                            
                        default:
                            System.out.println("Error");
                            
                            //Resets subchoice
                            subChoice = 0;
                            break;
                    }
                    break;
                    
                //save the ocean to a file
                case 4:
                    saveSim(ocean);
                    break;
                    
                //loads a previous ocean from a file
                case 5:
                    restoreSim(ocean);
                    break;
                    
                //exits the simmulation
                case 6:
                    System.out.println("Thank you for using the ocean simulation program.");
                    System.out.println("Goodbye!");
                    break;
                    
                //default- should not hit
                default:
                    System.out.println("Error");
                    //Resets subchoice
                    subChoice = 0;
                    break;
            }
        }       
    }
    
    //main 
    public static void main(String[] args) throws FileNotFoundException {
        //creates the random
        r1 = new Random();
        
        //calls the core function of the program 
        runOceanSim();
    }
}