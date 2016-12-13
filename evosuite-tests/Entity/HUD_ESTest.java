/*
 * This file was automatically generated by EvoSuite
 * Tue Dec 06 23:35:32 EET 2016
 */

package Entity;

import static org.evosuite.runtime.EvoAssertions.assertThrownBy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import TileMap.TileMap;
import sun.java2d.SunGraphics2D;

@RunWith(EvoRunner.class) @EvoRunnerParameters(useVNET = true) 
public class HUD_ESTest extends HUD_ESTest_scaffolding {

  @Test
  public void test0()  throws Throwable  {
      TileMap tileMap0 = new TileMap(3304);
      Player player0 = new Player(tileMap0);
      HUD hUD0 = new HUD(player0);
      // Undeclared exception!
      try { 
        hUD0.draw((Graphics2D) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         assertThrownBy("Entity.HUD", e);
      }
  }

  @Test
  public void test1()  throws Throwable  {
      TileMap tileMap0 = new TileMap(2712);
      Player player0 = new Player(tileMap0);
      HUD hUD0 = new HUD(player0);
      Animation animation0 = player0.animation;
      BufferedImage bufferedImage0 = animation0.getImage();
      SunGraphics2D sunGraphics2D0 = (SunGraphics2D)bufferedImage0.createGraphics();
      hUD0.draw(sunGraphics2D0);
      assertEquals(0, sunGraphics2D0.renderHint);
  }
}