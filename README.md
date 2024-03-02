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

Эта таблица будет представлена в виде Json-схемы:
```json
{
  "$schema": "http://json-schema.org/draft-04/schema#",
  "title": "ClassNameRQ",
  "type": "object",
  "properties": {
    "InnerObject_2": {
      "description": "Description_4",
      "type": "object",
      "properties": {
        "field_21": {
          "description": "Description_5",
          "type": "string"
        },
        "field_23_array": {
          "description": "Description_7",
          "type": "array",
          "items": {
            "type": "object",
            "properties": {
              "field_232": {
                "description": "Description_9",
                "type": "integer"
              },
              "field_231": {
                "description": "Description_8",
                "type": "string"
              }
            },
            "additionalProperties": false,
            "required": [
              "field_231",
              "field_232"
            ]
          }
        },
        "field_22": {
          "description": "Description_6",
          "type": "string"
        },
        "field_24": {
          "description": "Description_10",
          "type": "string"
        },
        "field_25": {
          "description": "Description_11",
          "type": "string"
        },
        "field_26": {
          "description": "Description_12",
          "type": "integer"
        }
      },
      "additionalProperties": false,
      "required": [
        "field_21",
        "field_22",
        "field_26"
      ]
    },
    "InnerObject_1": {
      "description": "Description_1",
      "type": "object",
      "properties": {
        "field_11": {
          "description": "Description_2",
          "type": "string"
        },
        "field_12": {
          "description": "Description_3",
          "type": "string"
        }
      },
      "additionalProperties": false,
      "required": [
        "field_11",
        "field_12"
      ]
    }
  },
  "additionalProperties": false,
  "required": []
}
```
#### Как пользоваться:
1. Запустить в среде разработки из Main
2. Выбрать пункт 1 введя в консоль 1
3. Вставить таблицу спецификации без строки заголовков (скопировав ее из ексель или ворд, чтобы в качестве разделителей были ТАБы; при копировании из Конфли - табов нет, и не работает соответственно)
4. С новой строки напечатать "end" , нажать Enter
5. Получить в консоли готовую схему, скопировать к себе в файл

#### Конвертация типов:
1. String 
```json
   "type": "string"
```
2. Integer
```json
"type": "integer"
```
3. Date
```json
   "type": "string",
   "format": "date"
```
4. Long
```json
   "type": "integer",
   "format": "int64"
```
5. Decimal
```json
   "type": "number",
   "format": "decimal"
```


