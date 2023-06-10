# Тестовое задание CNETT.RU
[![Build status](https://ci.appveyor.com/api/projects/status/cuwnmvykhqfhtbx8?svg=true)](https://ci.appveyor.com/project/Yuditskiy-o/cnett-test-task)

Ссылка на [тестовое задание](https://github.com/Yuditskiy-o/CNETT-test-task/blob/main/src/test/java/app/documents/Task.md)

## Запуск тестов.
1. Для запуска отдельно позитивных тестов необходимо ввести команду:

```
./gradlew clean positiveTest
```

2. Для запуска отдельно негативных тестов необходимо ввести команду:

```
./gradlew clean negativeTest
```

## Генерируем отчет Allure по итогам тестирования

Используем команду:
```
./gradlew allureServe
```
