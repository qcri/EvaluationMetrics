package qa.qf.qcri.iyas.evaluation.ir;

import java.util.List;
import java.util.Map;
import java.util.Set;

import qa.qf.qcri.iyas.utils.U;


/**
 * @author albarron
 * Qatar Computing Research Institute, 2016
 */
public class IrAbstract {

  /**  
   * Default theshold to compute precisions, recalls, or AveP: k=[1,...,thres] 
   */
  protected static int thres = 10;
  
  /**
   * Set a threshold different than the default one (=10). This is the number of 
   * documents that are considered as returned by the ranking.
   * @param thres value > 0
   */
  public static void setThreshold(int threshold) {
    U.ifFalseCrash(threshold>0,  
        "The threshold for the relevant documents in the ranking should be bigger than 0");
    thres = threshold;
  }
  
  /**
   * Check that all the keys in the predicted ranking exist in the gold ranking.
   * @param ranking ranking of documents
   * @param gold gold collection to compare against
   * @return true if all the keys in ranking are in gold.
   */
  protected static boolean 
  GoldContainsAllinRanking(List<String> ranking, Map<String, Boolean> gold) {
    Set<String> goldIds = gold.keySet();
    return goldIds.containsAll(ranking); 
  }
  

}
