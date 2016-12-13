/*
 * This file was automatically generated by EvoSuite
 * Tue Dec 06 22:56:17 EET 2016
 */

package Main;

import static org.evosuite.runtime.EvoAssertions.assertThrownBy;
import static org.junit.Assert.fail;

import java.awt.HeadlessException;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(useVNET = true) 
public class Game_ESTest extends Game_ESTest_scaffolding {

  @Test
  public void test0()  throws Throwable  {
      Game game0 = new Game();
  }

  @Test
  public void test1()  throws Throwable  {
      String[] stringArray0 = new String[1];
      // Undeclared exception!
      try { 
        Game.main(stringArray0);
        fail("Expecting exception: HeadlessException");
      
      } catch(HeadlessException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         assertThrownBy("java.awt.GraphicsEnvironment", e);
      }
  }
}