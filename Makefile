.PHONY: build train run

build:
	javac -d bin/ -cp src src/build_tagger.java
	javac -d bin/ -cp src src/run_tagger.java

train:
	java -cp bin build_tagger data/sents.train data/sents.devt model_file

run:
	java -cp bin run_tagger data/sents.test model_file data/sents.out
