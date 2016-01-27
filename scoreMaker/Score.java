import java.util.*;
import java.io.*;

class Score {
  public String[] left_arrows;
  public String[] down_arrows;
  public String[] up_arrows;
  public String[] right_arrows;
  
  Score(String fileName) {
    FileReader fr = null;
    BufferedReader br = null;
    try {
      fr = new FileReader("../music/" + fileName + "/preview.tmp");
      br = new BufferedReader(fr);

      left_arrows = br.readLine().split(",", 0);
      down_arrows = br.readLine().split(",", 0);
      up_arrows = br.readLine().split(",", 0);
      right_arrows = br.readLine().split(",", 0);
    } catch (FileNotFoundException e) {
      System.err.println("FILE NOT FOUND!!");
    } catch (IOException e) {
      System.err.println("IOException!!");
    } finally {
      try {
        br.close();
        fr.close();
      } catch (IOException e) {
        System.err.println("IOException!!");
      }
    }
  }  
}
