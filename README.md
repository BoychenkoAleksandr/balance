## Инструкция для запуска приложения
1. Все настройки проекта лежат в application.yml.
2. Для работы с базой данной был подключён flyway и написаны 5 скриптов с созданием и заполнением таблиц.
3. Запустить TestTaskApplication.java
4. Проект готов к работе.
## Инструкция для тестирования
### Аутентификация (/api/auth)
  
#### Вход в систему
Метод: POST  
Эндпоинт: /api/auth/loginByEmail 
Доступ: Публичный  
Описание: Аутентифицирует пользователя по почте и возвращает JWT-токен  
Параметры запроса:  
  - login (логин)  
  - password (пароль)

#### Вход в систему
Метод: POST  
Эндпоинт: /api/auth/loginByPhone
Доступ: Публичный  
Описание: Аутентифицирует пользователя по телефону и возвращает JWT-токен  
Параметры запроса:  
  - login (логин)  
  - password (пароль)
