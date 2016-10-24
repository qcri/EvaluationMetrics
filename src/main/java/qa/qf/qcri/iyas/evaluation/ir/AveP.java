package qa.qf.qcri.iyas.evaluation.ir;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**
 * Implementation of Average Precision for a given ranking exercise. The used
 * definitions come from 
 * {@link https://en.wikipedia.org/wiki/Information_retrieval#Mean_average_precision} 
 * and {@link https://sanchom.wordpress.com/tag/average-precision/}
 * 
 * @author albarron
 * Qatar Computing Research Institute, 2016
 */
public class AveP extends IrAbstract{

  /**Use if the order of the elements should be ascending  */
  public static final boolean ASCENDING = true;
  
  /**Use if the order of the elements should be descending. This is the right one for rankings!  */
  public static final boolean DESCENDING = false;
  
  
  /**
   * Value assigned to rankings in which there are no relevant documents at all.
   * There are three options that affect in different ways other computations, such 
   * as MAP (see {@link MeanAvgPrecision}):
   * <ul>
   *  <li>0.0. Pessimistic. For the ECML 2016, opt for 0.0
   *  <li>1.0. Optimistic. It would lift up the evaluation results.
   *  <li> Undefined. Not implemented; still should be the right way and these 
   *  instances should be discarded in advance to the evaluation.  
   * </ul> 
   */
  private static double P_FOR_RANKINGS_WITHOUT_RELEVANTS = 0.0; 
  
  private static boolean ACCEPT_RANKINKS_WITHOUT_RELEVANTS = true;
  
  /**
   * Computation of average precision in which the change in recall between one
   * rank and the other is considered. This should give exactly the best result 
   * as {@code getAveragePrecision}. As that one is slightly less expensive,
   * using it instead of this one is preferable. 
   * @param predictions
   * @param gold
   * @return
   */
  public static double getAveragePrecision2(
    Map<String, Double> predictions, Map<String, Boolean> gold) {
    
    double avPrec = 0.0;
    double cRec=0;
    
    //Ranking generation of the ids on the basis of the predictions
    List<String> ranking = getKeysSortedByValue(predictions, DESCENDING);
     
    //Computation of P at the different levels
    double[] precisions = Precision.computePrecisions(ranking, gold, thres);
    //Computation of R at the different levels
    double[] recalls = Recall.computeRecalls(ranking, gold, thres);
    
    //Looping over all the instances
    for (int i=0; i < precisions.length; i++) {
      //logger.debug(recalls[i] -cRec);
      avPrec += precisions[i] * (recalls[i] - cRec);
      cRec = recalls[i];    
    }
    //logger.debug("Average precision: " +avPrec);
    return avPrec;
  }



  /**
   * Sorts a map on the basis of its values.
   * @param unsortedMap
   * @param order true if ascending; false if descending
   * @return LinkedHashMap sorted by value.
   */
  protected static Map<String, Double> sortMapByValues(Map<String, Double> unsortedMap, 
      final boolean order) {
    List<Entry<String, Double>> list = 
        new LinkedList<Entry<String, Double>>(unsortedMap.entrySet());

    // Sorting based on values
    Collections.sort(list, new Comparator<Entry<String, Double>>()
    {
        public int compare(Entry<String, Double> o1,
                Entry<String, Double> o2)
        {
            if (order) {
                return o1.getValue().compareTo(o2.getValue());
            } else {
                return o2.getValue().compareTo(o1.getValue());
            }
        }
    });

    // Maintaining insertion order with the help of LinkedList
    Map<String, Double> sortedMap = new LinkedHashMap<String, Double>();
    for (Entry<String, Double> entry : list) {
        sortedMap.put(entry.getKey(), entry.getValue());
    }
    return sortedMap; 
  }
  
  /**
   * Sort the elements in the map according to the value, either in ascending or
   * descending order, and 
   * @param unsortedMap
   * @param order true for ascending; false for descending
   * @return a list ordered according to the values in the map
   */
  public static List<String> getKeysSortedByValue (
                          Map<String, Double> unsortedMap, final boolean  order) {
    List<Entry<String, Double>> list = 
        new LinkedList<Entry<String, Double>>(unsortedMap.entrySet());

    // Sorting based on values
    Collections.sort(list, new Comparator<Entry<String, Double>>()
    {
        public int compare(Entry<String, Double> o1,
                Entry<String, Double> o2)
        {
            if (order) {
                return o1.getValue().compareTo(o2.getValue());
            } else {
                return o2.getValue().compareTo(o1.getValue());
            }
        }
    });

    // Maintaining insertion order with the help of LinkedList
    List<String> sortedList = new ArrayList<String>();
    for (Entry<String, Double> entry : list) {
        sortedList.add(entry.getKey());
    }
    return sortedList;
  }
  
  /**
   * The default value is true. If this is the case, average precision for a 
   * ranking without relevant instances will be 0.0. The value can be changed with 
   * {@code setScoreForAllIrrelevant()}.
   * <br/>
   * The default value is true.
   * 
   * @param accept false if you want the code to crash in such a case.
   */
  public static void setAcceptRankingsAllIrrelevant(boolean accept) {
    ACCEPT_RANKINKS_WITHOUT_RELEVANTS = accept;
  }
  
  /** The average precision value for rankings with all irrelevant is set to 0.0. */
  public static void setScoreForAllIrrelevantECML2016() {
    setScoreForAllIrrelevant(0.0);
  }
  
  /** 
   * The average precision value for rankings with all irrelevant is set to -1.0.
   * This value should be checked in the significance test to discard the instance. 
   */
  public static void setScoreForAllIrrelevantStatisticalSignificance() {
    setScoreForAllIrrelevant(-1.0);
  }
  
  /**
   * Value assigned to rankings in which there are no relevant documents at all.
   * There are three options that affect in different ways other computations, 
   * such as MAP (see {@link MeanAvgPrecision}):
   * <ul>
   *  <li>0.0 is pessimistic as it claims that the ranker performs really bad, 
   *  even if there is nothing to retrieve. <b>If the evaluation values are 
   *  supposed to be comparable against those from the ECML 2016 cQA 
   *  challenge, choose 0.0, as this is the value used there.</b>
   *  <li>1.0 is optimistic, as it claims that the ranker is perfect, even if 
   *  there is nothing to retrieve. It would lift up the evaluation results.
   *  <li> Undefined. <b>Not implemented</b>; still should be the right way and 
   *  these instances should be discarded in advance to the evaluation.  
   * </ul> 
   * @param s
   */
  public static void setScoreForAllIrrelevant(double s) {
    P_FOR_RANKINGS_WITHOUT_RELEVANTS = s;
  }
  
  /**
   * Computation of average precision in which a Kronecker delta, looking  
   * at whether the current document in the ranking is relevant, is considered.
   * @param predictions map with documents and relevance scores
   * @param gold map with documents and gold relevance
   * @return average precision for k=[1,...k]
   */
  public static double 
  getAveragePrecision(Map<String, Double> predictions, Map<String, Boolean> gold) {
      //Ranking generation of the ids on the basis of the predictions
      List<String> ranking = getKeysSortedByValue(predictions, DESCENDING);
      return getAveragePrecision(ranking,  gold);
    }
  
//  public static double
//  getAveragePrecisionAtK(Map<String, Double> predictions, Map<String, Boolean> gold, int k) {
//    List<String> ranking = getKeysSortedByValue(predictions, DESCENDING);
//    return getAveragePrecision(ranking.subList(0, Math.min(ranking.size(), k)),  gold); 
//  }
  
  /**
   * Computation of average precision in which a Kronecker delta, looking  
   * at whether the current document in the ranking is relevant, is considered.
   * @param predictions list of documents, ordered by relevance
   * @param gold map with documents and gold relevance
   * @return average precision for k=[1,...k]
   */
  protected static double 
  getAveragePrecision(List<String> predictions, Map<String, Boolean> gold) {
   
    double avPrec = 0.0;
    double relevants=0;
    double[] precisions = Precision.computePrecisions(predictions, gold, thres);

    for (int i=0; i < precisions.length; i++) {
      if (gold.get(predictions.get(i))) {
        //System.out.println(predictions.get(i) + " " + gold.get(predictions.get(i)));
        avPrec+=precisions[i];
        relevants++;
      }
    }
    if (relevants==0) {
      //System.out.println("No relevant in the current ranking; first doc: " + predictions.get(0));
      if (! ACCEPT_RANKINKS_WITHOUT_RELEVANTS) {
        System.out.println("The current ranking has no relevant instances. The process stops, as set by the user");
        System.exit(-1);
      }
      avPrec = P_FOR_RANKINGS_WITHOUT_RELEVANTS;
    } else {
      avPrec /=relevants;
    }
    //logger.debug("Average precision: " +avPrec);
    return avPrec;
  }

  
}
