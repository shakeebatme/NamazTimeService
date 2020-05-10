# NamazTimeService

This is Springboot rest service which calcluates Prayer time for the day. 

### Request 

URL: http://localhost:8080/todayPrayerTime

```
{
	"latitude": 42.479064,
	"longitude": -83.377222,
	"timeZone": -4
}
```

### Response
```
{
    "fajar": "05:34 am",
    "sunrise": "06:56 am",
    "dhuhr": "01:34 pm",
    "asr": "06:15 pm",
    "sunset": "08:13 pm",
    "maghrib": "08:13 pm",
    "isha": "09:35 pm"
}
```
