package qa.qf.qcri.iyas.evaluation.ir;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
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
   * Default threshold to compute precisions, recalls, or AveP: k=[1,...,thres] 
   */
  protected static int thres = 10;
  
  /**
   * Maximum number of ids to report missing entries between ranking and gold
   */
  private final static int MAX_DIFFERENCE_SIZE = 5;
  
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
    boolean correct = true;
//    if (ranking.size() != gold.size()) {
//      System.out.format("The size of the gold and ranking collections is different: %s vs %s%n", 
//          gold.size(), ranking.size());
//      correct = false;
//    };
//    
//    Set<String> goldNotInRanking = getDiff(gold.keySet(), ranking);
//    if (! goldNotInRanking.isEmpty()) {
//      System.out.format("There are ids in gold not in the ranking. For instance: %s%n", 
//          missingSample(goldNotInRanking));
//      correct = false;
//    }
    
    Set<String> rankingNotInGold = getDiff(new HashSet<String>(ranking), gold.keySet());
    if (! rankingNotInGold.isEmpty()) {
      System.out.format("There are ids in the ranking not in gold. For instance: %s%n", 
          missingSample(rankingNotInGold));
      correct = false;
    }
    return correct;
     
  }
  
  private static Set<String> getDiff(Set<String> set1, Collection<String> set2) {
    Set<String> diff = new HashSet<String>(set1);
    diff.removeAll(set2);
    return diff;
  }
  
  private static String missingSample(Set<String> set) {
    StringBuffer sb = new StringBuffer();
    int i = 0;
    Iterator<String> it = set.iterator();
    while(it.hasNext() && i < Math.min(set.size(), MAX_DIFFERENCE_SIZE)){
      sb.append(it.next())
        .append(", ");
      i++;
    }
    return sb.toString();
  }
  
//  protected static String getDifferenceIds(Set<String> ids) {
//    
//    
//    return sb.toString(); 
//  }

}
