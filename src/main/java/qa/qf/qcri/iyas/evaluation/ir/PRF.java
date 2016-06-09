package qa.qf.qcri.iyas.evaluation.ir;

import java.util.List;
import java.util.Map;

import qa.qf.qcri.iyas.utils.U;

/**
 * Implementation of standard Precision, Recall, and F-measure. Refer to 
 * {@link https://en.wikipedia.org/wiki/Precision_and_recall} for details
 * 
 * @author albarron
 * Qatar Computing Research Institute, 2016
 */
public class PRF {

  /** Default value for beta in F_{beta}   */
  private static final double BETA_ONE = 1.0;
  
  /** True positives */
  private static double tp;
  
  /** False positives */
  private static double fp;
  
  /** True negatives */
  private static double tn;
  
  /** False negatives */
  private static double fn;
  
  /**
   * Compares predicted vs gold values to compute the number of true positives, 
   * true negatives, false positives, and false negatives.
   *  
   * Once this method is called, the rest in the class can be called to compute
   * Precision, Recall, and F-measure.
   * 
   * @param preds predicted values as a list of maps
   * @param gold gold values
   */
  public static void load(List<Map<String, Boolean>> preds, Map<String, Boolean> gold) {
    tp = 0.0;
    fp = 0.0;
    tp = 0.0;
    fp = 0.0;
    
    for (int i = 0; i < preds.size(); i++) {
      for (String k : preds.get(i).keySet()) {
        if (preds.get(i).get(k) && gold.get(k)) {
          tp +=1;
        } else if (! preds.get(i).get(k) && gold.get(k)) {
          tn +=1;
        } else if (preds.get(i).get(k) && ! gold.get(k)) {
          fp +=1;
        } else if (! preds.get(i).get(k) && gold.get(k)) {
          fn +=1;
        }
      }
    }
  }

  
  /**
   *  Compares predicted vs gold values to compute the number of true positives, 
   * true negatives, false positives, and false negatives.
   *  
   * Once this method is called, the rest in the class can be called to compute
   * Precision, Recall, and F-measure.
   * @param preds predicted values as a map
   * @param gold  gold values
   */
  public static void load(Map<String, Boolean> preds, Map<String, Boolean> gold) {
    tp = 0.0;
    fp = 0.0;
    tp = 0.0;
    fp = 0.0;
    
    for (int i = 0; i < preds.size(); i++) {
      for (String k : preds.keySet()) {
        if (preds.get(k) && gold.get(k)) {
          tp +=1;
        } else if (! preds.get(k) && gold.get(k)) {
          tn +=1;
        } else if (preds.get(k) && ! gold.get(k)) {
          fp +=1;
        } else if (! preds.get(k) && gold.get(k)) {
          fn +=1;
        }
      }
    }
  }
  
  /**
   * Compute precision for the given input. Before calling this 
   * @return precision
   */
  public static double getPrecision() {
    U.ifFalseCrash(tp+fp+tn+fn != 0, 
        "The dataset was not set properly. Use load() first");
    return tp / (tp + fp);
  }
  
  /**
   * @return recall
   */
  public static double getRecall() {
    U.ifFalseCrash(tp+fp+tn+fn != 0, 
        "The dataset was not set properly. Use load() first");
    return tp / (tp + fn);
  }
  
  /**
   * @return The F1-measure 
   */
  public static double getFmeasure() {
    return getFmeasure(BETA_ONE);
  }
  
  /**
   * @param beta parameter in F_{beta}-measure
   * @return  F_{beta} measure.
   */
  public static double getFmeasure(double beta) {
    double f = 0;
    double p = getPrecision();
    double r = getRecall();
    beta *= beta;
    if ((p + r) > 0) {
      f = (1+ beta) * p * r / (beta * (p + r));
    }
    return f;
  }
  
}
