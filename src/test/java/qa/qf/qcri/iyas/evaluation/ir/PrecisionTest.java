package qa.qf.qcri.iyas.evaluation.ir;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test the two public methods from {@code qa.qcri.iyas.evaluation.ir.Precision}.
 * The rankings and golds are set up in IrAbstractTest
 * 
 * @author albarron
 * Qatar Computing Research Institute, 2016
 */
public class PrecisionTest extends IrAbstractTest {

  /**
   * We test that the precisions of a given ranking are the expected ones. In this
   * exercise, the actual ranking contains the following sequence:
   * <br/>
   * relevant, relevant, irrelevant, relevant, irrelevant.
   * <br/> 
   * Therefore the expected precisions at each k are:
   * <br/>
   * 1.0, 1.0, 0.66666, 0.75, 0.62. 
   */
  @Test
  public void testComputePrecisionsListOfStringMapOfStringBoolean() {
      double[] expected = {1.0, 1.0, 0.666, 0.75, 0.6};
      double[] actual = Precision.computePrecisions(ranking, gold);
      Assert.assertArrayEquals(expected, actual, delta );
  }

  /**A ranking of five documents is given but only the top 4 are considered 
   * relevant.
   */
  @Test
  public void testComputePrecisionsListOfStringMapOfStringBooleanInt() {
    double[] expected = {1.0, 1.0, 0.666, 0.75};
    double[] actual = Precision.computePrecisions(ranking, gold, 4);
    Assert.assertArrayEquals(expected, actual, delta );
  }

}
