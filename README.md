# cs4248
Assignment 2

## Building
```sh
javac -d bin/ -cp src src/build_tagger.java
javac -d bin/ -cp src src/run_tagger.java
javac -d bin/ -cp src src/verify_tagger.java
```

## Training the POS Tagger
```sh
java -cp bin build_tagger data/sents.train data/sents.devt model_file
```

## Running the POS Tagger
```sh
java -cp bin run_tagger data/sents.test model_file data/sents1.out
```

## Verify the POS Tagger
```sh
java -cp bin verify_tagger data/sents.train data/sents.devt
```

## Running tests
```sh
javac -cp jar/mockito-all-1.9.5.jar:jar/junit-4.8.1.jar:src -d tests/ \
    tests/ViterbiTest.java \
    tests/UtilsTest.java \
    tests/BasicModelTest.java \
    tests/LanguageTest.java

java -cp tests:bin:jar/mockito-all-1.9.5.jar:jar/junit-4.8.1.jar \
    org.junit.runner.JUnitCore \
    ViterbiTest \
    UtilsTest \
    BasicModelTest \
    LanguageTest
```
