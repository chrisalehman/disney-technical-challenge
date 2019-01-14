clean:
	./gradlew clean

build:
	./gradlew build -x test

test:
	rm -rf build/test-results
	./gradlew test

run:
	bin/pg-start.sh
	java -jar build/libs/disney-technical-challenge-0.0.1-SNAPSHOT.jar

docker-run:
	docker-compose up --build --remove-orphans

shutdown:
	docker-compose down
	bin/pg-stop.sh
