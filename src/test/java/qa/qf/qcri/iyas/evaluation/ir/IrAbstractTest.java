package qa.qf.qcri.iyas.evaluation.ir;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;

/**
 * @author albarron
 * Qatar Computing Research Institute, 2016
 */
public abstract class IrAbstractTest {

  protected static Map<String, Double> unsortedMap;
  protected static  List<String> expected;
  
  public static  List<String> ranking;
  protected static Map<String, Boolean> gold;
  
  protected static final double delta = 0.001;
  
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    //The unsorted map with the weights
    unsortedMap = new HashMap<String, Double>();
    unsortedMap.put("a", 1.0);
    unsortedMap.put("b", 2.0);
    unsortedMap.put("c", 1.5);
    unsortedMap.put("d", 2.5);
    
    // The expected key order
    expected = new ArrayList<String>();
    expected.add("d");
    expected.add("b");
    expected.add("c");
    expected.add("a");
    
    //A ranking of documents and the gold map, with the relevance included. 
    ranking = Arrays.asList(new String[]{"rel1", "rel2", "irrel1", "rel3", "irrel2"});
    gold = new HashMap<String, Boolean>();
    gold.put("rel1", true);
    gold.put("rel2", true);
    gold.put("irrel1", false);
    gold.put("rel3", true);
    gold.put("irrel2", false);
  }
  
}
