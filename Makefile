clean:
	./gradlew clean
	docker-compose down
	bin/db-stop.sh

build:
	./gradlew build -x test

test:
	rm -rf build/test-results
	./gradlew test

run:
	bin/db-stop.sh
	bin/db-start.sh
	java -jar build/libs/disney-technical-challenge-*.jar

docker-up:
	docker-compose down
	docker-compose up --build --remove-orphans
