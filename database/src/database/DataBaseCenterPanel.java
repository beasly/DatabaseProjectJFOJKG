package database;


import javax.swing.*;
import java.awt.*;

class DataBaseCenterPanel extends JPanel{
  @Override
  public void paint( Graphics g ) {
    super.paint( g );
    g.setColor( Color.black );
    int x = getWidth();
    int y = getHeight();

    // resizableLine(g, x, y);
    drawHorizontal( g, x, y );
    drawVertical( g, x, y );
  }

  private void drawVertical( Graphics g, int x, int y ) {
    int abstand = 20;
    for ( int width = 0; width <= x; width = width + abstand ) {
      g.drawLine( width, 0, width, y );
    }
  }

  private void drawHorizontal( Graphics g, int x, int y ) {
    int abstand = 20;
    for ( int height = 0; height <= y; height = height + abstand ) {
      g.drawLine( 0, height, x, height );
    }
  }

  private void resizableLine( Graphics g, int x, int y ) {
    g.drawLine( 100, 100, x - 100, y - 100 );
  }
}

