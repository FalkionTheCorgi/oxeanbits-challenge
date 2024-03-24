package com.example.oxeanbits_challenge.weather

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oxeanbits_challenge.api.model.response.Current
import com.example.oxeanbits_challenge.api.model.response.CurrentUnits
import com.example.oxeanbits_challenge.api.model.response.Daily
import com.example.oxeanbits_challenge.api.model.response.DailyUnits
import com.example.oxeanbits_challenge.api.model.response.ForecastResponse
import com.example.oxeanbits_challenge.api.model.response.Hourly
import com.example.oxeanbits_challenge.api.model.response.HourlyUnits
import com.example.oxeanbits_challenge.api.model.response.ResponseError
import com.example.oxeanbits_challenge.api.repository.GetRepository
import com.example.oxeanbits_challenge.compareHours
import com.example.oxeanbits_challenge.database.Database
import com.example.oxeanbits_challenge.database.entity.CurrentEntity
import com.example.oxeanbits_challenge.database.entity.CurrentUnitsEntity
import com.example.oxeanbits_challenge.database.entity.DailyEntity
import com.example.oxeanbits_challenge.database.entity.DailyUnitsEntity
import com.example.oxeanbits_challenge.database.entity.ForecastResponseEntity
import com.example.oxeanbits_challenge.database.entity.HourlyEntity
import com.example.oxeanbits_challenge.database.entity.HourlyUnitsEntity
import com.example.oxeanbits_challenge.database.entity.PrecipitationHourlyEntity
import com.example.oxeanbits_challenge.database.entity.RelativeHumidityHourlyEntity
import com.example.oxeanbits_challenge.database.entity.SunriseEntity
import com.example.oxeanbits_challenge.database.entity.SunsetEntity
import com.example.oxeanbits_challenge.database.entity.TemperatureHourlyEntity
import com.example.oxeanbits_challenge.database.entity.TemperatureMaxDailyEntity
import com.example.oxeanbits_challenge.database.entity.TemperatureMinDailyEntity
import com.example.oxeanbits_challenge.database.entity.TimeDailyEntity
import com.example.oxeanbits_challenge.database.entity.TimeHourlyEntity
import com.example.oxeanbits_challenge.database.entity.UVEntity
import com.example.oxeanbits_challenge.database.entity.WeatherCodeDailyEntity
import com.example.oxeanbits_challenge.database.entity.WeatherCodeHourlyEntity
import com.example.oxeanbits_challenge.database.entity.WindSpeedHourlyEntity
import com.example.oxeanbits_challenge.getArrayHours
import com.example.oxeanbits_challenge.getCurrentDate
import com.example.oxeanbits_challenge.getFormattedDoubleTwoDecimalCases
import com.example.oxeanbits_challenge.getIndexByDate
import com.example.oxeanbits_challenge.getSunriseOrSunset
import com.example.oxeanbits_challenge.hourToInt
import com.example.oxeanbits_challenge.medianDouble
import com.example.oxeanbits_challenge.medianInt
import com.example.oxeanbits_challenge.weather.model.DaysOfWeek
import com.example.oxeanbits_challenge.weather.model.ForecastData
import com.example.oxeanbits_challenge.weather.model.LocationData
import com.example.oxeanbits_challenge.weather.model.WeatherCondition
import com.example.oxeanbits_challenge.weather.model.forecastDataError
import com.example.oxeanbits_challenge.weather.model.forecastResponseError
import com.example.oxeanbits_challenge.weather.model.mapIntWithWeather
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val database: Database,
    private val get: GetRepository
): ViewModel(){

    private val locationData = mutableStateOf<LocationData?>(null)
    private val fullForecastData = mutableStateOf<ForecastResponse?>(null)
    private val forecastData = mutableStateOf<ForecastData?>(null)
    private val hour = mutableStateOf<String?>(null)
    private val startDate = mutableStateOf<String?>(null)
    private val endDate = mutableStateOf<String?>(null)
    private val isLoading = mutableStateOf(false)
    private val errorMessage = mutableStateOf("")
    private val indexHour = mutableIntStateOf(0)
    private val indexDay = mutableIntStateOf(0)
    private val resetActualDay = mutableStateOf(false)

    fun setLocationData(newLocation: LocationData?){ locationData.value = newLocation }
    private fun setForecastData(newForecast: ForecastData){ forecastData.value = newForecast }
    private fun setStartDate(newDate: String){ startDate.value = newDate }
    private fun setEndDate(newDate: String){ endDate.value = newDate }
    private fun setFullForecastData(newForecastResponse: ForecastResponse) { fullForecastData.value = newForecastResponse }
    private fun setHour(newHour: String){ hour.value = newHour }
    private fun setIsLoading(isLoad: Boolean){ isLoading.value = isLoad }
    fun setErrorMessage(newMessage: String) { errorMessage.value = newMessage }
    private fun resetIndexHour() { indexHour.intValue = 0 }
    fun setIndexHour(newIndex: Int){ indexHour.intValue = newIndex }
    fun setDay(newIndex: Int) { indexDay.intValue = newIndex }
    private fun setResetActualDay() { resetActualDay.value = !resetActualDay.value }

    private fun getLocationData() = locationData.value
    fun getForecastData() = forecastData.value
    private fun getStartDate() = startDate.value
    private fun getEndDate() = endDate.value
    private fun getHour() = hour.value
    private fun getFullForecast() = fullForecastData.value
    fun getIsLoading() = isLoading.value
    fun getErrorMessage() = errorMessage.value
    fun getIndexHour() = indexHour
    fun getIndexDay() = indexDay
    fun getResetActualDay() = resetActualDay.value

    private suspend fun callForecast(): Pair<ForecastResponse?, ResponseError?> {
        return get.getForecast(
            latitude = getLocationData()?.latitude ?: 0.00,
            longitude = getLocationData()?.longitude ?: 0.00,
            currentParams = "weather_code,temperature_2m,relative_humidity_2m,apparent_temperature,is_day",
            hourlyParams = "weather_code,temperature_2m,precipitation_probability,relative_humidity_2m&daily=uv_index_max",
            timezone = "auto",
            dailyParams = "weather_code,uv_index_max,sunrise,sunset,temperature_2m_max,temperature_2m_min,precipitation_probability_max",
            startDate = getStartDate() ?: "",
            endDate = getEndDate() ?: ""
        )
    }

    fun applyHour(before: Int, after: Int){
        if (!getIsLoading()) {
            if (after != 0) {
                setHour(getArrayHours()[after])
                getForecastDataByHour()
            }
        } else {
            setIndexHour(before)
        }
    }

    suspend fun applyDay(
        before: Int,
        after: Int,
        array: List<DaysOfWeek>,
        city: String,
        latitude: String,
        longitude: String
    ){
        if (!getIsLoading()) {
            resetIndexHour()
            if (array[after].day != getCurrentDate()) {
                setStartDate(array[after].day)
                setEndDate(array[after].day)
                getFilteredForecastByDate(
                    location = if (city == "WeatherApp")
                        "$latitude $longitude"
                    else
                        city
                )
            } else {
                getCurrentForecast()
            }
        } else {
            setDay(before)
        }
    }

    fun getCurrentForecast() = viewModelScope.launch {
        setIsLoading(true)
        val response = callForecast()
        if (response.first != null) {
            val data = response.first
            setFullForecastData(data!!)
            val forecastData = ForecastData(
                temperature = "${getFormattedDoubleTwoDecimalCases(data.current?.temperature2m ?: 0.00)} ${data.currentUnits?.temperature2m}",
                maxTemperature = "${data.hourly?.temperature2m?.maxOrNull() ?: "0.00"} ${data.hourlyUnits?.temperature2m}",
                minTemperature = "${data.hourly?.temperature2m?.minOrNull() ?: "0.00"} ${data.hourlyUnits?.temperature2m}",
                relativeHumidity = "${data.current?.relativeHumidity2m ?: ""}${data.currentUnits?.relativeHumidity2m}",
                probabilityPrecipitation = "${medianInt(data.hourly?.precipitation)}${data.hourlyUnits?.precipitation}",
                ultravioletIndex = medianDouble(
                    array = data.daily?.uvIndexMax ?: arrayListOf()
                ),
                dayOrNight = if (data.current?.isDay == 0) {
                    "Noite"
                } else {
                    "Dia"
                },
                sunrise = getSunriseOrSunset(
                    data.daily?.sunrise ?: arrayListOf(),
                    getStartDate() ?: getCurrentDate()
                ) ?: "",
                sunset = getSunriseOrSunset(
                    data.daily?.sunset ?: arrayListOf(),
                    getStartDate() ?: getCurrentDate()
                ) ?: "",
                weatherCode = data.current?.weatherCode ?: 0,
                timezone = data.timezone ?: ""
            )
            setForecastData(
                forecastData
            )
        } else {
            setForecastData(forecastDataError)
            setFullForecastData(forecastResponseError)
            setErrorMessage(
                response.second?.reason ?: "Erro desconhecido, por favor contactar o suporte"
            )
        }
        setIsLoading(false)
    }
    private suspend fun getFilteredForecastByDate(location: String) {
        setIsLoading(true)
        if (!importForecastByDate(date = getStartDate() ?: "", location = location)) {
            val response = callForecast()
            if (response.first != null) {
                val data = response.first
                val index = getIndexByDate(
                    date = getStartDate() ?: getEndDate() ?: getCurrentDate(),
                    array = data?.daily?.time ?: arrayListOf()
                )
                setFullForecastData(data!!)
                val forecastData = ForecastData(
                    temperature = "${getFormattedDoubleTwoDecimalCases(medianDouble(data.daily?.temperature2mMax))} ${data.dailyUnits?.temperature2mMax}",
                    maxTemperature = "${data.daily?.temperature2mMax?.maxOrNull() ?: "0.00"} ${data.dailyUnits?.temperature2mMax}",
                    minTemperature = "${data.daily?.temperature2mMin?.minOrNull() ?: "0.00"} ${data.dailyUnits?.temperature2mMin}",
                    relativeHumidity = "${medianInt(data.hourly?.relativeHumidity2m)}${data.hourlyUnits?.relativeHumidity2m}",
                    probabilityPrecipitation = "${data.daily?.precipitationProbabilityMax?.get(index) ?: 0}${data.dailyUnits?.precipitationProbabilityMax}",
                    ultravioletIndex = data.daily?.uvIndexMax?.get(index) ?: 0.00,
                    dayOrNight = null,
                    sunrise = getSunriseOrSunset(
                        data.daily?.sunrise ?: arrayListOf(),
                        getStartDate() ?: getCurrentDate()
                    ) ?: "",
                    sunset = getSunriseOrSunset(
                        data.daily?.sunset ?: arrayListOf(),
                        getStartDate() ?: getCurrentDate()
                    ) ?: "",
                    timezone = data.timezone ?: "",
                    weatherCode = data.daily?.weatherCode?.get(index) ?: 0
                )
                setForecastData(
                    forecastData
                )
                saveForecast(
                    forecast = data,
                    date = getStartDate() ?: "",
                    location = location
                )
            } else {
                setForecastData(forecastDataError)
                setFullForecastData(forecastResponseError)
                setErrorMessage(
                    response.second?.reason ?: "Erro desconhecido, por favor contactar o suporte"
                )
            }
        }
        setIsLoading(false)
    }
    private fun getForecastDataByHour(){
        setIsLoading(true)
        if (getForecastData() != forecastDataError) {
            val index = hourToInt(getHour() ?: "00:00")
            val forecast = getFullForecast()
            val sunrise = getSunriseOrSunset(
                forecast?.daily?.sunrise ?: arrayListOf(),
                getStartDate() ?: getCurrentDate()
            ) ?: ""
            val sunset = getSunriseOrSunset(
                forecast?.daily?.sunset ?: arrayListOf(),
                getStartDate() ?: getCurrentDate()
            ) ?: ""
            val compareHours = compareHours(getHour() ?: "00:00", sunrise, sunset)
            setForecastData(
                ForecastData(
                    temperature = "${
                        getFormattedDoubleTwoDecimalCases(
                            forecast?.hourly?.temperature2m?.get(
                                index
                            ) ?: 0.00
                        )
                    } ${forecast?.hourlyUnits?.temperature2m}",
                    maxTemperature = "${forecast?.hourly?.temperature2m?.maxOrNull() ?: "0.00"} ${forecast?.hourlyUnits?.temperature2m}",
                    minTemperature = "${forecast?.hourly?.temperature2m?.minOrNull() ?: "0.00"} ${forecast?.hourlyUnits?.temperature2m}",
                    relativeHumidity = "${forecast?.hourly?.relativeHumidity2m?.get(index)}${forecast?.hourlyUnits?.relativeHumidity2m}",
                    probabilityPrecipitation = "${forecast?.hourly?.precipitation?.get(index)}${forecast?.hourlyUnits?.precipitation}",
                    ultravioletIndex = forecast?.daily?.uvIndexMax?.first() ?: 0.00,
                    dayOrNight = if (compareHours == 1 || compareHours == -1) "Noite" else "Dia",
                    sunrise = sunrise,
                    sunset = sunset,
                    timezone = forecast?.timezone ?: "",
                    weatherCode = forecast?.hourly?.weatherCode?.get(index) ?: 0
                )
            )
        } else {
            setErrorMessage(
                "Sem conexão com a internet"
            )
        }
        setIsLoading(false)
    }

    fun defineClimate(state: Int, isDay: Boolean?): WeatherCondition {
        return mapIntWithWeather(state, isDay)
    }

    private suspend fun saveForecast(
        forecast: ForecastResponse,
        date: String,
        location: String
    ) {

        val dailyId = createDailyEntity()
        saveTimeDaily(forecast.daily?.time ?: listOf(), dailyId)
        saveUvDaily(forecast.daily?.uvIndexMax ?: arrayListOf(), dailyId)
        saveSunsetDaily(forecast.daily?.sunset ?: arrayListOf(), dailyId)
        saveSunriseDaily(forecast.daily?.sunrise ?: arrayListOf(), dailyId)
        saveWeatherCodeDaily(forecast.daily?.weatherCode ?: arrayListOf(), dailyId)
        saveTemperatureMaxDaily(forecast.daily?.temperature2mMax ?: arrayListOf(), dailyId)
        saveTemperatureMinDaily(forecast.daily?.temperature2mMin ?: arrayListOf(), dailyId)
        val dailyUnitsId = saveDailyUnits(
            forecast.dailyUnits?.time ?: "",
            forecast.dailyUnits?.uvIndexMax ?: "",
            forecast.dailyUnits?.weatherCode ?: "",
            forecast.dailyUnits?.temperature2mMax ?: "",
            forecast.dailyUnits?.temperature2mMin ?: "",
            dailyId
        )

        val hourlyId = createHourlyEntity()
        savePrecipitationHourly(forecast.hourly?.precipitation ?: arrayListOf(), hourlyId)
        saveWindSpeedHourly(forecast.hourly?.windSpeed10m ?: arrayListOf(), hourlyId)
        saveRelativeHumidityHourly(forecast.hourly?.relativeHumidity2m ?: arrayListOf(), hourlyId)
        saveTemperatureHourly(forecast.hourly?.temperature2m ?: arrayListOf(), hourlyId)
        saveTimeHourly(forecast.hourly?.time ?: arrayListOf(), hourlyId)
        saveWeatherCodeHourly(forecast.hourly?.weatherCode  ?: arrayListOf(), hourlyId)
        val hourlyUnitsId = saveHourlyUnits(
            time = forecast.hourlyUnits?.time ?: "",
            precipitation = forecast.hourlyUnits?.precipitation ?: "",
            relativeHumidity2m = forecast.hourlyUnits?.relativeHumidity2m ?:"",
            temperature2m = forecast.hourlyUnits?.temperature2m ?: "",
            windSpeed10m = forecast.hourlyUnits?.windSpeed10m ?: "",
            weatherCode = forecast.hourlyUnits?.weatherCode ?: "",
            hourlyId = hourlyId
        )

        val currentId = saveCurrent(
            time = forecast.current?.time,
            interval = forecast.current?.interval,
            temperature2m = forecast.current?.temperature2m,
            relativeHumidity2m = forecast.current?.relativeHumidity2m,
            apparentTemperature = forecast.current?.apparentTemperature,
            weatherCode = forecast.current?.weatherCode,
            isDay = forecast.current?.isDay
        )

        val currentUnitsId = saveCurrentUnits(
            time = forecast.currentUnits?.time,
            interval = forecast.currentUnits?.interval,
            temperature2m = forecast.currentUnits?.temperature2m,
            relativeHumidity2m = forecast.currentUnits?.relativeHumidity2m,
            apparentTemperature = forecast.currentUnits?.apparentTemperature,
            weatherCode = forecast.currentUnits?.weatherCode,
            isDay = forecast.currentUnits?.isDay
        )

        saveForecastResponse(
            date = date,
            latitude = forecast.latitude,
            longitude = forecast.longitude,
            location = location,
            generationtimeMs = forecast.generationtimeMs,
            utcOffsetSeconds = forecast.utcOffsetSeconds,
            timezone = forecast.timezone,
            timezoneAbbreviation = forecast.timezoneAbbreviation,
            elevation = forecast.elevation,
            currentUnitsId = currentUnitsId,
            currentId = currentId,
            hourlyUnitsId = hourlyUnitsId,
            hourlyId = hourlyId,
            dailyUnitsId = dailyUnitsId,
            dailyId = dailyId
        )


    }

    private suspend fun createDailyEntity(): Long{
        database.dailyDao().insert(
            DailyEntity()
        )
        return database.dailyDao().getAllDaily().first().last().id
    }

    private suspend fun createHourlyEntity(): Long{
        database.hourlyDao().insert(
            HourlyEntity()
        )
        return database.hourlyDao().getAllHourly().first().last().id
    }

    private suspend fun saveTimeDaily(time: List<String>, dailyEntityId: Long){
        time.forEach {
            database.timeDailyDao().insert(
                TimeDailyEntity(
                    time = it,
                    dailyEntityId = dailyEntityId
                )
            )
        }
    }

    private suspend fun saveUvDaily(uv: ArrayList<Double>, dailyEntityId: Long){
        uv.forEach {
            database.uvDao().insert(
                UVEntity(
                    uv = it,
                    dailyEntityId = dailyEntityId
                )
            )
        }
    }

    private suspend fun saveSunsetDaily(sunset: ArrayList<String>, dailyEntityId: Long){
        sunset.forEach {
            database.sunsetDao().insert(
                SunsetEntity(
                    sunset = it,
                    dailyEntityId = dailyEntityId
                )
            )
        }
    }

    private suspend fun saveSunriseDaily(sunrise: ArrayList<String>, dailyEntityId: Long){
        sunrise.forEach {
            database.sunriseDao().insert(
                SunriseEntity(
                    sunrise = it,
                    dailyEntityId = dailyEntityId
                )
            )
        }
    }

    private suspend fun saveDailyUnits(
        time: String,
        uvIndex: String,
        weatherCode: String,
        temperatureMax: String,
        temperatureMin: String,
        dailyEntityId: Long
    ): Long {
        database.dailyUnitsDao().insert(
            DailyUnitsEntity(
                time = time,
                uvIndexMax = uvIndex,
                weatherCode = weatherCode,
                dailyEntityId = dailyEntityId,
                temperatureMax = temperatureMax,
                temperatureMin = temperatureMin
            )
        )

        return database.dailyUnitsDao().getAllDailyUnits().first().last().id
    }

    private suspend fun savePrecipitationHourly(precipitation: ArrayList<Int>, hourlyId: Long){
        precipitation.forEach{
            database.precipitationHourlyDao().insert(
                PrecipitationHourlyEntity(
                    precipitation = it,
                    hourlyId = hourlyId
                )
            )
        }
    }

    private suspend fun saveWindSpeedHourly(precipitation: ArrayList<Double>, hourlyId: Long){
        precipitation.forEach{
            database.windSpeedHourlyDao().insert(
                WindSpeedHourlyEntity(
                    windSpeed10m = it,
                    hourlyId = hourlyId
                )
            )
        }
    }

    private suspend fun saveRelativeHumidityHourly(relativeHumidity: ArrayList<Int>, hourlyId: Long){
        relativeHumidity.forEach{
            database.relativeHumidityHourlyDao().insert(
                RelativeHumidityHourlyEntity(
                    relativeHumidity2m = it,
                    hourlyId = hourlyId
                )
            )
        }
    }

    private suspend fun saveTemperatureHourly(temperature: ArrayList<Double>, hourlyId: Long){
        temperature.forEach{
            database.temperatureHourlyDao().insert(
                TemperatureHourlyEntity(
                    temperature2m = it,
                    hourlyId = hourlyId
                )
            )
        }
    }

    private suspend fun saveTimeHourly(time: ArrayList<String>, hourlyId: Long){
        time.forEach{
            database.timeHourlyDao().insert(
                TimeHourlyEntity(
                    time = it,
                    hourlyId = hourlyId
                )
            )
        }
    }

    private suspend fun saveHourlyUnits(
        time: String,
        temperature2m: String,
        relativeHumidity2m: String,
        windSpeed10m: String,
        precipitation: String,
        weatherCode: String,
        hourlyId: Long
    ): Long {
        database.hourlyUnitsDao().insert(
            HourlyUnitsEntity(
                time = time,
                temperature2m = temperature2m,
                relativeHumidity2m = relativeHumidity2m,
                windSpeed10m = windSpeed10m,
                precipitation = precipitation,
                weatherCode = weatherCode,
                hourlyId = hourlyId
            )
        )

        return database.hourlyUnitsDao().getAllHourlyUnits().first().last().id
    }

    private suspend fun saveCurrent(
        time: String?,
        interval: Int?,
        temperature2m: Double?,
        relativeHumidity2m: Int?,
        weatherCode: Int?,
        apparentTemperature: Double?,
        isDay: Int?
    ): Long {

        database.currentDao().insert(
            CurrentEntity(
                time = time,
                interval = interval,
                temperature2m = temperature2m,
                relativeHumidity2m = relativeHumidity2m,
                apparentTemperature = apparentTemperature,
                weatherCode = weatherCode,
                isDay = isDay
            )
        )

        return database.currentDao().getAllCurrent().first().last().id

    }

    private suspend fun saveCurrentUnits(
        time: String?,
        interval: String?,
        temperature2m: String?,
        relativeHumidity2m: String?,
        apparentTemperature: String?,
        weatherCode : String?,
        isDay: String?
    ): Long {

        database.currentUnitsDao().insert(
            CurrentUnitsEntity(
                time = time,
                interval = interval,
                temperature2m = temperature2m,
                relativeHumidity2m = relativeHumidity2m,
                apparentTemperature = apparentTemperature,
                weatherCode = weatherCode,
                isDay = isDay
            )
        )

        return database.currentUnitsDao().getAllCurrentUnits().first().last().id

    }

    private suspend fun saveForecastResponse(
        date: String,
        latitude: Double?,
        longitude: Double?,
        location: String?,
        generationtimeMs: Double?,
        utcOffsetSeconds: Int?,
        timezone: String?,
        timezoneAbbreviation: String?,
        elevation: Int?,
        currentUnitsId: Long,
        currentId: Long,
        hourlyUnitsId: Long,
        hourlyId: Long,
        dailyUnitsId: Long,
        dailyId: Long
    ){

        database.forecastResponseDao().insert(
            ForecastResponseEntity(
                date = date,
                latitude = latitude,
                longitude = longitude,
                location = location,
                generationtimeMs = generationtimeMs,
                utcOffsetSeconds = utcOffsetSeconds,
                timezone = timezone,
                timezoneAbbreviation = timezoneAbbreviation,
                elevation = elevation,
                currentUnitsId = currentUnitsId,
                currentId = currentId,
                hourlyUnitsId = hourlyUnitsId,
                hourlyId = hourlyId,
                dailyUnitsId = dailyUnitsId,
                dailyId = dailyId
            )
        )

    }

    private suspend fun saveWeatherCodeHourly(weatherCode: ArrayList<Int>, hourlyId: Long){
        weatherCode.forEach {
            database.weatherCodeHourly().insertWeatherCode(
                WeatherCodeHourlyEntity(
                    weatherCode = it,
                    hourlyId = hourlyId
                )
            )
        }
    }
    private suspend fun saveWeatherCodeDaily(weatherCode: ArrayList<Int>, dailyId: Long){
        weatherCode.forEach{
            database.weatherCodeDaily().insertWeatherCode(
                WeatherCodeDailyEntity(
                    weatherCode = it,
                    dailyEntityId = dailyId
                )
            )
        }
    }
    private suspend fun saveTemperatureMaxDaily(temperature: ArrayList<Double>, dailyId: Long){
        temperature.forEach{
            database.temperatureDailyMax().insertTemperatureMax(
                TemperatureMaxDailyEntity(
                    temperature = it,
                    dailyEntityId = dailyId
                )
            )
        }
    }
    private suspend fun saveTemperatureMinDaily(temperature: ArrayList<Double>, dailyId: Long){
        temperature.forEach {
            database.temperatureDailyMin().insertTemperatureMin(
                TemperatureMinDailyEntity(
                    temperature = it,
                    dailyEntityId = dailyId
                )
            )
        }
    }

    private suspend fun importForecastByDate(date: String, location: String): Boolean{
        val forecast = database.forecastResponseDao().getForecastWithRelationsByDate(date = date, location = location).firstOrNull()

        forecast?.let {

            val index = getIndexByDate(
                getStartDate() ?: getEndDate() ?: getCurrentDate(),
                ArrayList(forecast.timeDailyEntity.map { it.time })
            )

            val forecastData = ForecastData(
                temperature = "${getFormattedDoubleTwoDecimalCases(forecast.temperatureHourlyEntity.map { it.temperature2m }[index])} ${forecast.dailyUnitsEntity.temperatureMax}",
                maxTemperature = "${forecast.temperatureDailyMax.map { it.temperature }[index]} ${forecast.dailyUnitsEntity.temperatureMax}",
                minTemperature = "${forecast.temperatureDailyMin.map { it.temperature }[index]} ${forecast.dailyUnitsEntity.temperatureMin}",
                relativeHumidity = "${medianInt(ArrayList(forecast.relativeHumidityHourlyEntity.map { it.relativeHumidity2m }))}${forecast.hourlyUnitsEntity.relativeHumidity2m}",
                probabilityPrecipitation = "${medianInt(ArrayList(forecast.precipitationHourlyEntity.map { it.precipitation }))}${forecast.hourlyUnitsEntity.precipitation}",
                ultravioletIndex = forecast.uvEntity.map { it.uv }[index],
                dayOrNight = if (forecast.currentEntity.isDay == 0) {
                    "Noite"
                } else {
                    "Dia"
                },
                sunrise = getSunriseOrSunset(
                    ArrayList(forecast.sunriseEntity.map { it.sunrise }),
                    getStartDate() ?: getCurrentDate()
                ) ?: "",
                sunset = getSunriseOrSunset(
                    ArrayList(forecast.sunsetEntity.map { it.sunset }),
                    getStartDate() ?: getCurrentDate()
                ) ?: "",
                weatherCode = forecast.weatherCodeDaily.map { it.weatherCode }[index],
                timezone = forecast.forecastResponseEntity.timezone ?: ""
            )
            setFullForecastData(
                ForecastResponse(
                    latitude = forecast.forecastResponseEntity.latitude,
                    longitude = forecast.forecastResponseEntity.longitude,
                    generationtimeMs = forecast.forecastResponseEntity.generationtimeMs,
                    utcOffsetSeconds = forecast.forecastResponseEntity.utcOffsetSeconds,
                    timezone = forecast.forecastResponseEntity.timezone,
                    timezoneAbbreviation = forecast.forecastResponseEntity.timezoneAbbreviation,
                    elevation = forecast.forecastResponseEntity.elevation,
                    currentUnits = CurrentUnits(
                        time = forecast.currentUnitsEntity.time,
                        interval = forecast.currentUnitsEntity.interval,
                        temperature2m = forecast.currentUnitsEntity.temperature2m,
                        relativeHumidity2m = forecast.currentUnitsEntity.relativeHumidity2m,
                        apparentTemperature = forecast.currentUnitsEntity.apparentTemperature,
                        isDay = forecast.currentUnitsEntity.isDay
                    ),
                    current = Current(
                        time = forecast.currentEntity.time,
                        interval = forecast.currentEntity.interval,
                        temperature2m = forecast.currentEntity.temperature2m,
                        relativeHumidity2m = forecast.currentEntity.relativeHumidity2m,
                        apparentTemperature = forecast.currentEntity.apparentTemperature,
                        isDay = forecast.currentEntity.isDay
                    ),
                    hourlyUnits = HourlyUnits(
                        time = forecast.hourlyUnitsEntity.time,
                        temperature2m = forecast.hourlyUnitsEntity.temperature2m,
                        relativeHumidity2m = forecast.hourlyUnitsEntity.relativeHumidity2m,
                        windSpeed10m = forecast.hourlyUnitsEntity.windSpeed10m,
                        precipitation = forecast.hourlyUnitsEntity.precipitation,
                        weatherCode = forecast.hourlyUnitsEntity.weatherCode
                    ),
                    hourly = Hourly(
                        time = ArrayList(forecast.timeHourlyEntity.map { it.time }),
                        temperature2m = ArrayList(forecast.temperatureHourlyEntity.map { it.temperature2m }),
                        relativeHumidity2m = ArrayList(forecast.relativeHumidityHourlyEntity.map { it.relativeHumidity2m }),
                        windSpeed10m = ArrayList(forecast.windSpeedHourlyEntity.map { it.windSpeed10m }),
                        precipitation = ArrayList(forecast.precipitationHourlyEntity.map { it.precipitation }),
                        weatherCode = ArrayList(forecast.weatherCodeHourly.map { it.weatherCode })
                    ),
                    dailyUnits = DailyUnits(
                        time = forecast.dailyUnitsEntity.time,
                        uvIndexMax = forecast.dailyUnitsEntity.uvIndexMax,
                        temperature2mMin = forecast.dailyUnitsEntity.temperatureMin,
                        temperature2mMax = forecast.dailyUnitsEntity.temperatureMax
                    ),
                    daily = Daily(
                        time = ArrayList(forecast.timeDailyEntity.map { it.time }),
                        uvIndexMax = ArrayList(forecast.uvEntity.map { it.uv }),
                        sunrise = ArrayList(forecast.sunriseEntity.map { it.sunrise }),
                        sunset = ArrayList(forecast.sunsetEntity.map { it.sunset }),
                        weatherCode = ArrayList(forecast.weatherCodeDaily.map { it.weatherCode }),
                        temperature2mMax = ArrayList(forecast.temperatureDailyMax.map { it.temperature }),
                        temperature2mMin = ArrayList(forecast.temperatureDailyMin.map { it.temperature })
                    )
                )
            )
            setForecastData(
                forecastData
            )
            return true
        }
        return false
    }

    fun setFilterLocation(latitude: Double, longitude: Double){
        setLocationData(
            LocationData(
                latitude = latitude,
                longitude = longitude
            )
        )
        setStartDate(getCurrentDate())
        setEndDate(getCurrentDate())
        setResetActualDay()
        getCurrentForecast()
    }

}