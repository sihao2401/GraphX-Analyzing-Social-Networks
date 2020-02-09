# Analyzing Social Networks using GraphX

### Running Environment

java version  "1.8.0_181"

sbt version      1.3.2

### Instruction

- These files don't have jar file, you should go to root of your project and build by the following commands:

  ```powershell
  > sbt package
  ```

  This will create a fat jar under target/scala directory.

- Create bucket for Analyzing Social Networks application, including jar file and dataset

- Run the jar file on AWS as following configuration:

  ```powershell
  spark-submit --deploy-mode cluster --packages graphframes:graphframes:0.7.0-spark2.4-s_2.11 --class socialNetwork <Input File Path> <Output File Path>
  ```

  

