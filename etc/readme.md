README
======

# How to call rest services using the windows command shell

### prerequisites 

curl installed, https://sourceforge.net/projects/getgnuwin32/  [GnuWin32]
jq installed,  https://stedolan.github.io/jq/ [jq]

### use

curl -H "Content-Type: application/json" -X POST -d "{\"examPeriod\" : {\"id\": 8} }}" http://localhost:8080/posy-rest/rest/posy-rest-service/find-exam-registrations | jq -r

curl -H "Content-Type: application/json" -X POST 
	-d "{\"examPeriod\" : {\"id\": 8} }}" 
	http://localhost:8080/posy-rest/rest/posy-rest-service/find-exam-registrations 
	| jq -r
	
-- "pipe" output to file
curl -H "Content-Type: application/json" -X POST -d "{\"examPeriod\" : {\"id\": 8}, \"faculty\": {\"id\":\"FIW\"}}" http://localhost:8080/posy-rest/rest/posy-rest-service/find-exam-registrations | jq -r "." > d:\tmp\testcurl.json
curl -H "Content-Type: application/json" -X POST -d "{\"examPeriod\" : {\"semestercode\": 172}, \"faculty\": {\"id\":\"FIW\"}}" http://localhost:8080/posy-rest/rest/posy-rest-service/find-exam-registrations | jq -r "." > d:\tmp\testcurl.json

the curl request {"examPeriod": {"semestercode": 172}, "faculty": {"id": "FIW"}} has 4658k response size, we could try to reduce it


# POST find-exam-registrations

# POST record-many-grades

### using localhost
curl -H "Content-Type: application/json" -X POST -d @json-many-grades.json  http://localhost:8080/posy-rest/rest/posy-rest-service/record-many-grades

### using java-test.fhws.de
curl -u RESTful:_eyIn23n -H "Content-Type: application/json" -X POST -d @json-many-grades.json  https://java-test.fhws.de/posy-rest/rest/posy-rest-service/record-many-grades




```javascript
{
	"examPeriod": {
		"semestercode": 172
	},
	"examGrades": [
		{
			"course": "BWI",
			"examCatalogId": "5000010",
			"gradesWorkflow": {
				
			},
			"grades": [
				{
					"matnr": "6114074",
					"examGradeType": "DN",
					"averageGradeString": "1,3"
				}
			]
		}
	]
}
```
