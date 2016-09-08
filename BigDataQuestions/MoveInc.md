
###1. difference between dataset and data frame
####DataFrame
-------
- Relational
- Catalyst Query Optimization
- Tungsten direct / packed RAM
- JIT code generation
- Sorting and shuffling without deserializing

####DataSet
-------
- RDD+ Data Frame
- Functional Programming
- Typesafe
+
- Relational
- Catalyst Query Optimization
- Tungsten direct / packed RAM
- JIT code generation
- Sorting and shuffling without deserializing

Datasets provide an RDD-like API, but with all the performance advantages given by Catalyst and Tungsten. groupBy(), for example, which was always a performance no-no on RDDs, can be done efficiently with Datasets without worrying for example about some groups being too large for a single node because Catalyst can spill large groups. And it's all strongly typed and type-safe.
Reference :
http://datascienceassn.org/content/spark-16-datasets-best-rdds-and-dataframes

###2. what is map
Scala map is a collection of key/value pairs. Any value can be retrieved based on its key. Keys are unique in the Map, but values need not be unique. Maps are also called Hash tables. There are two kinds of Maps, the immutable and the mutable.

###3. Flatmap and map
map and flatMap are similar, in the sense they take a line from the input RDD and apply a function on it. The way they differ is that the function in map returns only one element, while function in flatMap can return a list of elements (0 or more) as an iterator.

- Example
In the case of word count, where the input line is split into multiple words, flatMap can be used. Also, in the case of weather data set, the extractData nethod will validate the record and might or might not return a value. In this case also, flatMap can be used

###4. what happens when spark node fails
The master considers the worker to be failure if it didnt receive the heartbeat message for past 60 sec (according to spark.worker.timeout). In that case the partition is assigned to another worker(remember partitioned RDD can be reconstructed even if its lost). 

How a new node will be introduced into spark cluster? 

the spark-master will not detect the new node addition to the cluster once the slaves are started, because before application-submit in cluster the sbin/start-master.sh starts the master and sbin/start-slaves.sh reads the conf/slaves file (contains IP address of all slaves) in spark-master machine and starts a slave instance on each machine specified. The spark-master will not read this configuration file after being started. so its not possible to add a new node once all slaves started.

http://stackoverflow.com/questions/29683025/apache-spark-behavior-when-a-node-in-a-cluster-fails

###5. What is hive 
Query language which work’s on HDFS and Local File systems

###6. How will you load file in Hive and process data
create table test_t(k string, v string) row format delimited fields terminated by '\t' stored as textfile;
load data local inpath '/tmp/input.txt' into table test_t;

###7. what is the difference btween hive and sparkql
With RDD caching enabled, SparkSQL queries run around 20 times faster than Hive queries
The response times of SparkSQL is more consistent than that 

###8. what is difference between cassandra and Hbase
####HBase:
Wide-column store based on Apache Hadoop and on concepts of BigTable.
Apache HBase is a NoSQL key/value store which runs on top of HDFS. Unlike Hive, HBase operations run in real-time on its database rather than MapReduce jobs. HBase is partitioned to tables, and tables are further split into column families. Column families, which must be declared in the schema, group together a certain set of columns (columns don’t require schema definition). For example, the "message" column family may include the columns: "to", "from", "date", "subject", and "body". Each key/value pair in HBase is defined as a cell, and each key consists of row-key, column family, column, and time-stamp. A row in HBase is a grouping of key/value mappings identified by the row-key. HBase enjoys Hadoop’s infrastructure and scales horizontally using off the shelf servers.
HBase works by storing data as key/value. It supports four primary operations: put to add or update rows, scan to retrieve a range of cells, get to return cells for a specified row, and delete to remove rows, columns or column versions from the table. Versioning is available so that previous values of the data can be fetched (the history can be deleted every now and then to clear space via HBase compactions). Although HBase includes tables, a schema is only required for tables and column families, but not for columns, and it includes increment/counter functionality.

HBase queries are written in a custom language that needs to be learned. SQL-like functionality can be achieved via Apache Phoenix, though it comes at the price of maintaining a schema. Furthermore, HBase isn’t fully ACID compliant, although it does support certain properties. Last but not least - in order to run HBase, ZooKeeper is required - a server for distributed coordination such as configuration, maintenance, and naming.

HBase is perfect for real-time querying of Big Data. Facebook use it for messaging and real-time analytics. They may even be using it to count Facebook likes.
Hbase has centralized architecture where The Master server is responsible for monitoring all RegionServer(responsible for serving and managing regions) instances in the cluster, and is the interface for all metadata changes. It provides CP(Consistency, Availability) form CAP theorem.


HBase is optimized for reads, supported by single-write master, and resulting strict consistency model, as well as use of Ordered Partitioning which supports row-scans. HBase is well suited for doing Range based scans.

Linear Scalability for large tables and range scans -
Due to Ordered Partitioning, HBase will easily scale horizontally while still supporting rowkey range scans.

- Secondary Indexes - 
Hbase does not natively support secondary indexes, but one use-case of Triggers is that a trigger on a ""put"" can automatically keep a secondary index up-to-date, and therefore not put the burden on the application (client)."

- Simple Aggregation- 
Hbase Co Processors support out-of-the-box simple aggregations in HBase. SUM, MIN, MAX, AVG, STD. Other aggregations can be built by defining java-classes to perform the aggregation

- Real Usages: Facebook Messanger


### Cassandra:

Wide-column store based on ideas of BigTable and DynamoDB

Apache Cassandra is the leading NoSQL, distributed database management system driving many of today's modern business applications by offering continuous availability, high scalability and performance, strong security, and operational simplicity while lowering overall cost of ownership.

Cassandra has decentralized architecture. Any node can perform any operation. It provides AP(Availability,Partition-Tolerance) from CAP theorem.

Cassandra has excellent single-row read performance as long as eventual consistency semantics are sufficient for the use-case. Cassandra quorum reads, which are required for strict consistency will naturally be slower than Hbase reads. Cassandra does not support Range based row-scans which may be limiting in certain use-cases. Cassandra is well suited for supporting single-row queries, or selecting multiple rows based on a Column-Value index.

If data is stored in columns in Cassandra to support range scans, the practical limitation of a row size in Cassandra is 10's of Megabytes. Rows larger than that causes problems with compaction overhead and time.

Cassandra supports secondary indexes on column families where the column name is known. (Not on dynamic columns).

Aggregations in Cassandra are not supported by the Cassandra nodes - client must provide aggregations. When the aggregation requirement spans multiple rows, Random Partitioning makes aggregations very difficult for the client. Recommendation is to use Storm or Hadoop for aggregations.

-Real Usages: Twitter

https://www.linkedin.com/pulse/real-comparison-nosql-databases-hbase-cassandra-mongodb-sahu

###9. what is the difference between parquet and Avro 
- Avro is a row-based storage format for Hadoop.
- Parquet is a column-based storage format for Hadoop.
- If your use case typically scans or retrieves all of the fields in a row in each query, Avro is usually the best choice.
- If your dataset has many columns, and your use case typically involves working with a subset of those columns rather than entire records, Parquet is optimized for that kind of work.

Avro
- Widely used as serialization platform
- Row-based, offered a compact and fast binary format
- Schema is encoded on the file so the data can be untagged
- File support block compression and splittable
- Supports schema evolution

Parquet
- Column-oriented binary file format
- Uses the records shredding and assembly algorithm
- Each data file contains the values for a set of rows
- Efficient in terms of disk I/O when specific columns need to be queried
