# test-api
Репозиторий для домашнего задания по тестированию

## Smoke test
Для запуска только smoke test необходимо в bash выполнить скрпит smoke_test.sh:
```console
$ /smoke_test.sh
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
Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 3.296 sec

Results :

Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
```
