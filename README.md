
## Описание  решения

Это архитектура основанная на Spring Web MVC, состоящая из трех ключевых компонентов: 

Controller: `TransferController` 

Service: `TransferService`

Repository: `TransactionRepository`

А так же реализован сервис логгирования `LogService` и сервис заглушка `AuthBillingService`


В файле журнала транзакций записывается информация в формате json:<br>
`id=0, date=02.09.2023, 20:57, fromCardNumber=3425678235789432, toCardNumber=2314675434789756, amount=Amount(value=6000000, currency=RUR), fee=60000.0, status=SUCCESS
`


### Сборка и запуск RESTful сервиса, разработанного в рамках данного проекта
- Собрать приложение, выполнив `gradle clean build`
- Перейти корневую папку проекта и собрать docker образ выполнив команду в корневой папке проекта<br>
  `docker build -t restapp:latest .`<br>
  Полученный образ можно запустить без docker-compose командой<br>
  `docker run -it --rm -p 5500:5500 --name restapp restapp`<br>

## Описание тестов
Для тестирования приложения разработаны unit тесты, проверяющие работу методов с использованием mock,
а так же интеграционные тесты с использованием testcontainers. Интеграционные тесты используют docker образ
приложения.<br>
Интеграционные тесты находятся в классе `MoneyTransferServiceApplicationTests.java`.

