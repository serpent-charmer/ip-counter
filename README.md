# ip-counter

## Сборка
```
mvn clean compile assembly:single
```
## Детали
Используется алгоритм [hyperloglog](http://algo.inria.fr/flajolet/Publications/FlFuGaMe07.pdf).



Результаты с файлом на 120ГБ

```
TIME took to count all ips 1515871ms  
FILE test\ip_addresses.txt contains ~999996780 ips
```
