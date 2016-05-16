# EvaluationMetrics

This project includes various evaluation metrics, tipically used in NLP, IR, and ML.

The current version is more focused on IR. Therefore, it includes implementations
of average precision, precision and recall at k, mean average precision, and mean
reciprocal rank. 

It can be used out of the box to evaluate systems in for the ECML 2016 cQA Challenge: 
Learning to Re-Rank Questions for Community Question Answering. Run the class
qa.qf.qcri.tasks.ecml.CqaEcml2016Scorer.java to see the necessary input. 

In case you do not have access to the jar, you can generate it with 

mvn clean compile assembly:single 

The resulting jar can be executed as follows:

java -cp QCRI-EvaluationMetrics-1.0-SNAPSHOT-jar-with-dependencies.jar qa.qf.qcri.tasks.ecml.CqaEcml2016Scorer -g [gold file] -i [predictions file]

For further information regarding the challenge, visit the Website: http://alt.qcri.org/ecml2016/
