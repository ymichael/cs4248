.PHONY: build build_train build_run build_verify train run verify sunfire

markmon:
	markmon ./README.md

sunfire:
	rsync -avz . michael@sunfire:~/cs4248

build: build_train build_run

build_train:
	javac -d bin/ -cp src src/cs4248/build_tagger.java

build_run:
	javac -d bin/ -cp src src/cs4248/run_tagger.java

build_verify:
	javac -d bin/ -cp src src/cs4248/verify_tagger.java

verify: build_verify
	java -cp bin cs4248.verify_tagger data/sents.train data/sents.devt

train: build_train
	java -cp bin cs4248.build_tagger data/sents.train data/sents.devt model_file

run: build_run
	java -cp bin cs4248.run_tagger data/sents.test model_file data/sents1.out

tests: build
	javac -cp jar/mockito-all-1.9.5.jar:jar/junit-4.8.1.jar:src -d tests/ \
		tests/cs4248/ViterbiTest.java \
		tests/cs4248/UtilsTest.java \
		tests/cs4248/BasicModelTest.java \
		tests/cs4248/LanguageTest.java

	java -cp tests:bin:jar/mockito-all-1.9.5.jar:jar/junit-4.8.1.jar \
		org.junit.runner.JUnitCore \
		cs4248.ViterbiTest \
		cs4248.UtilsTest \
		cs4248.BasicModelTest \
		cs4248.LanguageTest
