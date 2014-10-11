.PHONY: build build_train build_run train run

build: build_train build_run

build_train:
	javac -d bin/ -cp src src/build_tagger.java

build_run:
	javac -d bin/ -cp src src/run_tagger.java

train: build_train
	java -cp bin build_tagger data/sents.train data/sents.devt model_file

run: build_run
	java -cp bin run_tagger data/sents.test model_file data/sents.out
