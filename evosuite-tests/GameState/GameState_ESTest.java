/*
 * This file was automatically generated by EvoSuite
 * Tue Dec 06 22:48:03 EET 2016
 */

package GameState;

import static org.evosuite.runtime.EvoAssertions.assertThrownBy;
import static org.junit.Assert.fail;

import java.awt.Graphics2D;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(useVNET = true) 
public class GameState_ESTest extends GameState_ESTest_scaffolding {

  @Test
  public void test0()  throws Throwable  {
      GameStateManager gameStateManager0 = new GameStateManager();
      Level1State level1State0 = new Level1State(gameStateManager0);
      level1State0.update();
  }

  @Test
  public void test1()  throws Throwable  {
      GameStateManager gameStateManager0 = new GameStateManager();
      MenuState menuState0 = new MenuState(gameStateManager0);
      menuState0.keyReleased((-2188));
  }

  @Test
  public void test2()  throws Throwable  {
      GameStateManager gameStateManager0 = new GameStateManager();
      Level1State level1State0 = new Level1State(gameStateManager0);
      level1State0.keyPressed(0);
  }

  @Test
  public void test3()  throws Throwable  {
      GameStateManager gameStateManager0 = new GameStateManager();
      MenuState menuState0 = new MenuState(gameStateManager0);
      menuState0.init();
  }

  @Test
  public void test4()  throws Throwable  {
      GameStateManager gameStateManager0 = new GameStateManager();
      Level1State level1State0 = new Level1State(gameStateManager0);
      // Undeclared exception!
      try { 
        level1State0.draw((Graphics2D) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         assertThrownBy("TileMap.Background", e);
      }
  }
}
