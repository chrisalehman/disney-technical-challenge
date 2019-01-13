run:
	java -jar build/libs/disney-technical-challenge-0.0.1-SNAPSHOT.jar

clean:
	./gradlew clean

build:
	./gradlew build -x test

test:
	rm -rf build/test-results
	./gradlew test