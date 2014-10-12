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

tests: build
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
