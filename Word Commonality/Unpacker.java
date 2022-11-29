import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Unpacker {
    private int wordLength;
    
    public Unpacker(int wL) {
    	wordLength = wL;
    }
    public void runner() throws FileNotFoundException {
    	Scanner wordDoc = new Scanner(new File("C:\\Users\\belkn\\eclipse-workspace\\WordCommonality\\src\\Words.txt"));
    	FileOutputStream fos = null;
        File file;
        String word = "";
        try {
            //Specify the file path here
  	  file = new File("C:\\Users\\belkn\\eclipse-workspace\\WordCommonality\\src\\myfile.txt");
  	  fos = new FileOutputStream(file);

            /* This logic will check whether the file
  	   * exists or not. If the file is not found
  	   * at the specified location it would create
  	   * a new file*/
  	  if (!file.exists()) {
  	     file.createNewFile();
  	  }

  	  /*String content cannot be directly written into
  	   * a file. It needs to be converted into bytes
  	   */
  	  
  	  LineUnpack t; //separates the word and int
  	  Word temp; //stores current word data
  	  String st; //word and commonality in one string
  	  byte[] bytesArray;
  	  while(wordDoc.hasNext()) {
  		  st = wordDoc.nextLine();
  		  t = new LineUnpack(st);
  		  temp = new Word(t.getWord(),t.getCommon());
  		  if(temp.getLength()==wordLength) {
  			  word = temp.getWord() + " " + temp.getCommon()+"\n";
  			  bytesArray = word.getBytes();
  			  fos.write(bytesArray);
  			  fos.flush();
  		  }
  	  }
  	  
  	  System.out.println("File Written Successfully");
         } 
         catch (IOException ioe) {
  	  ioe.printStackTrace();
         } 
         finally {
  	  try {
  	     if (fos != null) 
  	     {
  		 fos.close();
  	     }
            } 
  	  catch (IOException ioe) {
  	     System.out.println("Error in closing the Stream");
  	  }
         }
    }
    
}
