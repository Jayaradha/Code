/**
  * Created by Jayaradha on 7/30/16.
  * Question
  * And events.txt Names has two columns Name and birthday like roy, 04/12/78 And events is 04/12/56,
  * "moon landing” You have to disregard the yearAnd return a list of names and the number of events that happened on their birthday,
  * return the top 10 And you can have 2 ppl with the same bday
  */
import scala.io.Source

object UserEvents {
  def main(args: Array[String]) = {
    println("Hello, world!")
    val names1 = Source.fromFile("/Users/arivolitirouvingadame/Documents/IdeaProjects/scala/src/names.txt").mkString
    val names2 = names1.split(",")

    val nameList = Source.fromFile("/Users/arivolitirouvingadame/Documents/IdeaProjects/scala/src/names.txt")
      .getLines.toList.map(x => x.split(",").toList)
    val namesmap = nameList.map(x => (x(1).substring(0,5),x(0)))

    val events =  Source.fromFile("/Users/arivolitirouvingadame/Documents/IdeaProjects/scala/src/events.txt")
      .getLines.toList.map(x => x.split(",").toList)
    val eventsmap = events.map(x => (x(0).substring(0,5),x(1))).groupBy(_._1).toList

    val eventsmap1 = events.map(x => (x(0).substring(0,5),x(1))).groupBy(_._1).map{case(k, v) => k -> v.map(_._2).size}

    val c = namesmap.toList ++ eventsmap1.toList
    val d = c.groupBy(_._1).map{case(k, v) => k -> v.map(_._2)}
    d.foreach(println)


  }

}
