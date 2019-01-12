run:
	echo "TODO..."	

clean:
	./gradlew clean

build:
	./gradlew build -x test

test:
	rm -rf build/test-results
	./gradlew test