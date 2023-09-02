FROM adoptopenjdk/openjdk11

EXPOSE 5500

ADD build/libs/MoneyTransferService-0.0.1-SNAPSHOT.jar MoneyTransferApp.jar

CMD ["java", "-jar", "MoneyTransferApp.jar"]