import org.apache.spark.sql.{Dataset, Row, SQLContext}
import org.apache.spark.{SparkConf, SparkContext}
import org.graphframes._

object socialNetwork {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("socialNetwork")
    val sc = new SparkContext(conf)
    sc.setCheckpointDir(args(1)+"/checkingpoint")
    val sqlC = new SQLContext(sc)
    val df = sqlC.read.format("com.databricks.spark.csv").option("header", "true").option("delimiter", "\t").load(args(0))
    val namedDF = df.select("FromNodeId", ("ToNodeId")).withColumnRenamed("FromNodeId","src").withColumnRenamed("ToNodeId","dst")
    val nodesDFFrom = namedDF.select("src").distinct()
    val nodesDFTo = namedDF.select("dst").distinct()
    val nodesDFUnique = nodesDFFrom.union(nodesDFTo).distinct()
    val finalNodesDFUnique = nodesDFUnique.select("src").withColumnRenamed("src","id")
    val g = GraphFrame(finalNodesDFUnique, namedDF).persist()
    //Question1
    val outDegreeSorted = g.outDegrees
    val TopOutDegreeSorted: Dataset[Row] = outDegreeSorted.sort(outDegreeSorted("outDegree").desc).limit(5)
    val mark1 = List("Question1 result: col1: nodeID col2 : OutDegree")
    val question1 = TopOutDegreeSorted.collect()
    val con1 = mark1++question1
    //Question2
    val inDegreeSorted = g.inDegrees
    val TopInDegreeSorted: Dataset[Row] = inDegreeSorted.sort(inDegreeSorted("inDegree").desc).limit(5)
    val mark2 = List("Question2 result: col1: nodeID col2 : InDegree")
    val question2 = TopInDegreeSorted.collect()
    val con2 = con1++mark2++question2
    //Question3
    val nodesPR = g.pageRank.resetProbability(0.15).tol(0.1).run()
    val TopNodesPR = nodesPR.vertices
    val top: Dataset[Row] = TopNodesPR.sort(TopNodesPR("pagerank").desc).limit(5)
    val mark3 = List("Question3 result: col1: nodeID col2: val of PageRank")
    val question3 = top.collect()
    val con3 = con2++mark3++question3
    //Question4
    val result = g.connectedComponents.setCheckpointInterval(1).run()
    val TopResult: Dataset[Row] = result.sort(result("component").desc).limit(5)
    val mark4 = List("Question4 result: col1:NodeID col2: Component")
    val question4 = TopResult.collect()
    val con4 = con3++mark4++question4
    //Question5
    val triangleResults = g.triangleCount.run()
    val TopTriangleResults: Dataset[Row] = triangleResults.sort(triangleResults("count").desc).limit(5)
    val mark5 = List("Question5 result: col1: the largest triangle count col2: NodeID")
    val question5 = TopTriangleResults.collect()
    val finalResult = con4++mark5++question5
    sc.parallelize(finalResult).coalesce(1).saveAsTextFile(args(1)+"/result")
  }
}
