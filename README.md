# Утилита для парсинга данных из спецификации в необходимые форматы

### 1. парсинг в Json-схему
Для правильного парсинга необходимо соблюсти корректность спецификации
Пример:
| Уровень  	| Наименование    	| Категория 	| Тип     	| Повторение       	| Описание       	|
|----------	|-----------------	|-----------	|---------	|------------------	|----------------	|
| 1        	| mt_ClassName_RQ 	| Element   	|         	|                  	|                	|
| 1.1      	| InnerObject_1   	| Element   	|         	| 0..1             	| Description_1  	|
| 1.1.1    	| field_11        	| Element   	| string  	| 1                	| Description_2  	|
| 1.1.2    	| field_12        	| Element   	| string  	| 1                	| Description_3  	|
| 1.2      	| InnerObject_2   	| Element   	|         	| 0..1             	| Description_4  	|
| 1.2.1    	| field_21        	| Element   	| string  	| 1                	| Description_5  	|
| 1.2.2    	| field_22        	| Element   	| string  	| 1                	| Description_6  	|
| 1.2.10   	| field_23_array  	| Element   	|         	| 0..неограниченно 	| Description_7  	|
| 1.2.10.1 	| field_231       	| Element   	| string  	| 1                	| Description_8  	|
| 1.2.10.2 	| field_232       	| Element   	| integer 	| 1                	| Description_9  	|
| 1.2.11   	| field_24        	| Element   	| string  	| 0..1             	| Description_10 	|
| 1.2.12   	| field_25        	| Element   	| string  	| 0..1             	| Description_11 	|
| 1.2.13   	| field_26        	| Element   	| integer 	| 1                	| Description_12 	|

