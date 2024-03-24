package com.example.oxeanbits_challenge.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.oxeanbits_challenge.database.dao.CurrentDao
import com.example.oxeanbits_challenge.database.dao.CurrentUnitsDao
import com.example.oxeanbits_challenge.database.dao.DailyDao
import com.example.oxeanbits_challenge.database.dao.DailyUnitsDao
import com.example.oxeanbits_challenge.database.dao.ForecastResponseDao
import com.example.oxeanbits_challenge.database.dao.HourlyDao
import com.example.oxeanbits_challenge.database.dao.HourlyUnitsDao
import com.example.oxeanbits_challenge.database.dao.PrecipitationHourlyDao
import com.example.oxeanbits_challenge.database.dao.RelativeHumidityHourlyDao
import com.example.oxeanbits_challenge.database.dao.SunriseDao
import com.example.oxeanbits_challenge.database.dao.SunsetDao
import com.example.oxeanbits_challenge.database.dao.TemperatureHourlyDao
import com.example.oxeanbits_challenge.database.dao.TemperatureMaxDailyDao
import com.example.oxeanbits_challenge.database.dao.TemperatureMinDailyDao
import com.example.oxeanbits_challenge.database.dao.TimeDailyDao
import com.example.oxeanbits_challenge.database.dao.TimeHourlyDao
import com.example.oxeanbits_challenge.database.dao.UvDao
import com.example.oxeanbits_challenge.database.dao.WeatherCodeDailyDao
import com.example.oxeanbits_challenge.database.dao.WeatherCodeHourlyDao
import com.example.oxeanbits_challenge.database.dao.WindSpeedHourlyDao
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

@Database(
    entities = [
        ForecastResponseEntity::class,
        CurrentUnitsEntity::class,
        CurrentEntity::class,
        HourlyUnitsEntity::class,
        HourlyEntity::class,
        DailyUnitsEntity::class,
        DailyEntity::class,
        TimeHourlyEntity::class,
        TemperatureHourlyEntity::class,
        RelativeHumidityHourlyEntity::class,
        WindSpeedHourlyEntity::class,
        PrecipitationHourlyEntity::class,
        TimeDailyEntity::class,
        SunriseEntity::class,
        SunsetEntity::class,
        UVEntity::class,
        WeatherCodeDailyEntity::class,
        WeatherCodeHourlyEntity::class,
        TemperatureMaxDailyEntity::class,
        TemperatureMinDailyEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class Database : RoomDatabase() {

    abstract fun forecastResponseDao(): ForecastResponseDao
    abstract fun currentUnitsDao(): CurrentUnitsDao
    abstract fun currentDao(): CurrentDao
    abstract fun hourlyUnitsDao(): HourlyUnitsDao
    abstract fun hourlyDao(): HourlyDao
    abstract fun dailyUnitsDao(): DailyUnitsDao
    abstract fun dailyDao(): DailyDao
    abstract fun sunriseDao(): SunriseDao
    abstract fun sunsetDao(): SunsetDao
    abstract fun uvDao(): UvDao
    abstract fun timeDailyDao(): TimeDailyDao
    abstract fun precipitationHourlyDao(): PrecipitationHourlyDao
    abstract fun windSpeedHourlyDao(): WindSpeedHourlyDao
    abstract fun relativeHumidityHourlyDao(): RelativeHumidityHourlyDao
    abstract fun temperatureHourlyDao(): TemperatureHourlyDao
    abstract fun timeHourlyDao(): TimeHourlyDao
    abstract fun weatherCodeHourly(): WeatherCodeHourlyDao
    abstract fun weatherCodeDaily(): WeatherCodeDailyDao
    abstract fun temperatureDailyMax(): TemperatureMaxDailyDao
    abstract fun temperatureDailyMin(): TemperatureMinDailyDao

}