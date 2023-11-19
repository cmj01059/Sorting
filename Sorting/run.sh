#!/bin/bash

javac -d bin src/Counter.java
javac -d bin -cp bin src/SortDriver.java

java -cp bin SortDriver