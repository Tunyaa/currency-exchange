Currency Exchange REST API
REST API для работы с валютами и обменными курсами. Позволяет просматривать и редактировать список валют и курсов, а также конвертировать произвольные суммы из одной валюты в другую. Веб-интерфейс не предусмотрен — только HTTP/JSON.

Демо: http://193.124.80.247:8080/exchange

Стек
Java 25 (OpenJDK)
Jakarta Servlet 6.1 (без Spring)
Apache Tomcat 11
SQLite через JDBC (sqlite-jdbc)
Jackson для JSON
Maven для сборки

Требования
JDK 25
Maven 3.9+
Tomcat 11

Установка и запуск
1. Клонировать репозиторий
bash
git clone https://github.com/USERNAME/currency-exchange.git
cd currency-exchange
2. Собрать WAR
bash
mvn clean package
В target/ появится exchange.war.

3. !!! Настроить Tomcat !!! — обязательно
В conf/server.xml в блоке <Connector port="8080" ...> добавить атрибут:

xml
parseBodyMethods="POST,PATCH"
Без этого PATCH-запросы не будут парсить поля формы, rate всегда будет null.

4. Задеплоить
Скопировать target/exchange.war в TOMCAT_HOME/webapps/ и запустить Tomcat:

5. Проверить
bash
curl http://193.124.80.247:8080/exchange/currencies
# []

Приложение доступно по адресу http://193.124.80.247:8080/exchange/.

База данных: файл exchange.db создаётся автоматически в рабочей директории Tomcat (Tomcat 11.0\bin). Схема создаётся классом DatabaseInitializer при старте приложения.

Endpoints
Метод	Путь	Тело (form-urlencoded)	Успех	Ошибки

GET	/currencies	—	200 JSON-массив	—

GET	/currency/{code}	—	200 JSON-объект	404 если не найдено

POST	/currencies	name, code, sign	201 JSON-объект	400 (валидация), 409 (дубликат code)

GET	/exchangeRates	—	200 JSON-массив	—

GET	/exchangeRate/{pair}	—	200 JSON-объект	404 если пары нет

POST	/exchangeRates	baseCurrencyCode, targetCurrencyCode, rate	201 JSON-объект	400, 409 (дубликат пары)

PATCH	/exchangeRate/{pair}	rate	200 JSON-объект	400, 404

GET	/exchange?from=&to=&amount=	—	200 JSON-объект	400 (валидация), 404 (курс не найден)


Примеры запросов:
Создать валюту
curl -i -X POST http://193.124.80.247:8080/exchange/currencies -d "name=US Dollar&code=USD&sign=$"

Список валют
curl -i http://193.124.80.247:8080/exchange/currencies

Одна валюта
curl -i http://193.124.80.247:8080/exchange/currency/USD

Создать курс
curl -i -X POST http://193.124.80.247:8080/exchange/exchangeRates -d "baseCurrencyCode=USD&targetCurrencyCode=RUB&rate=90"

Список курсов
curl -i http://193.124.80.247:8080/exchange/exchangeRates

Один курс
curl -i http://193.124.80.247:8080/exchange/exchangeRate/USDRUB

Обновить курс
curl -i -X PATCH http://193.124.80.247:8080/exchange/exchangeRate/USDRUB -d "rate=95"

Конвертация
curl -i "http://193.124.80.247:8080/exchange/exchange?from=USD&to=RUB&amount=100"

Ответ:

json
{
  "baseCurrency": {"id": 1, "name": "US Dollar", "code": "USD", "sign": "$"},
  "targetCurrency": {"id": 2, "name": "Russian Ruble", "code": "RUB", "sign": "₽"},
  "rate": 95,
  "amount": 100,
  "convertedAmount": 9500.000000
}

Логика конвертации
Если прямой пары нет — алгоритм ищет в порядке приоритета:

Прямой курс — есть from→to.

Обратный курс — есть to→from, тогда коэффициент = 1 / rate(to→from).

Транзитный курс — есть общая валюта X, такая что from→X и X→to (или их обратные). Коэффициент = произведение двух плеч.

Если ни один сценарий не подходит — 404.

Коды ответов

Код	Когда

200	Успешный GET/PATCH

201	Успешное создание (POST)

400	Неверные параметры: отсутствуют поля формы, пустые, невалидный rate/amount, отрицательный rate, отрицательный amount, from == to

404	Сущность не найдена (валюта, курс, пара)

409	Дубликат (code валюты или пара курса уже существует)

500	Внутренняя ошибка сервера (недоступна БД, непойманное исключение)
