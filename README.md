# test-api
Репозиторий для домашнего задания по тестированию

## Smoke test
Для запуска только smoke test необходимо в bash выполнить скрпит smoke_test.sh:
```console
$ ./smoke_test.sh
SMOKE TEST PASSED
```

## Функциональные тесты
Для запуска функциональных тестов необходимо выполнить команду maven test:
```console
$ mvn test
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running TestApi
Tests run: 5, Failures: 1, Errors: 0, Skipped: 0, Time elapsed: 3.296 sec

Results :

Tests run: 5, Failures: 1, Errors: 0, Skipped: 0
```

## Результаты тестирования
Результаты выполнения тестирования находятся в target/surefire-reports.
Для получения наглядной картинки необходимо установить Allure:

### Linux
```
$ sudo apt-add-repository ppa:qameta/allure
$ sudo apt-get update 
$ sudo apt-get install allure
```
### Mac
```
$ brew install allure
```
### Windows
```
$ scoop install allure
```

### Запуск Allure
```
$ allure serve target/surefire-reports
```
