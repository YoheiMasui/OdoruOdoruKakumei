import java.awt.*;
import javax.swing.*;

class Array {
  private boolean Enabled;
  private int x, y;
  private ImageIcon icon;

  Array(String fileName) {
    icon = new ImageIcon("./img/" + fileName);
  }
  
  public void setEnabled(boolean Enabled) {
    this.Enabled = Enabled;
  }
  
  public boolean getEnabled() {
    return Enabled;
  }

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }
}
