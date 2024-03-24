package com.example.oxeanbits_challenge.database

import androidx.room.Embedded
import androidx.room.Relation
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

data class ForecastResponseWithDetails(
    @Embedded val forecastResponseEntity: ForecastResponseEntity,
    @Relation(
        entity = CurrentUnitsEntity::class,
        parentColumn = "currentUnitsId",
        entityColumn = "id"
    )
    val currentUnitsEntity: CurrentUnitsEntity,
    @Relation(
        entity = CurrentEntity::class,
        parentColumn = "currentId",
        entityColumn = "id"
    )
    val currentEntity: CurrentEntity,
    @Relation(
        entity = HourlyUnitsEntity::class,
        parentColumn = "hourlyUnitsId",
        entityColumn = "id"
    )
    val hourlyUnitsEntity: HourlyUnitsEntity,
    @Relation(
        entity = HourlyEntity::class,
        parentColumn = "hourlyId",
        entityColumn = "id"
    )
    val hourlyEntity: HourlyEntity,
    @Relation(
        entity = DailyUnitsEntity::class,
        parentColumn = "dailyUnitsId",
        entityColumn = "id"
    )
    val dailyUnitsEntity: DailyUnitsEntity,
    @Relation(
        entity = DailyEntity::class,
        parentColumn = "dailyId",
        entityColumn = "id"
    )
    val dailyEntity: DailyEntity,
    @Relation(
        entity = PrecipitationHourlyEntity::class,
        parentColumn = "hourlyId",
        entityColumn = "hourlyId"
    )
    val precipitationHourlyEntity: List<PrecipitationHourlyEntity>,
    @Relation(
        entity = RelativeHumidityHourlyEntity::class,
        parentColumn = "hourlyId",
        entityColumn = "hourlyId"
    )
    val relativeHumidityHourlyEntity: List<RelativeHumidityHourlyEntity>,
    @Relation(
        entity = WindSpeedHourlyEntity::class,
        parentColumn = "hourlyId",
        entityColumn = "hourlyId"
    )
    val windSpeedHourlyEntity: List<WindSpeedHourlyEntity>,
    @Relation(
        entity = SunriseEntity::class,
        parentColumn = "dailyId",
        entityColumn = "dailyEntityId"
    )
    val sunriseEntity: List<SunriseEntity>,
    @Relation(
        entity = SunsetEntity::class,
        parentColumn = "dailyId",
        entityColumn = "dailyEntityId"
    )
    val sunsetEntity: List<SunsetEntity>,
    @Relation(
        entity = UVEntity::class,
        parentColumn = "dailyId",
        entityColumn = "dailyEntityId"
    )
    val uvEntity: List<UVEntity>,
    @Relation(
        entity = TimeDailyEntity::class,
        parentColumn = "dailyId",
        entityColumn = "dailyEntityId"
    )
    val timeDailyEntity: List<TimeDailyEntity>,
    @Relation(
        entity = TimeHourlyEntity::class,
        parentColumn = "hourlyId",
        entityColumn = "hourlyId"
    )
    val timeHourlyEntity: List<TimeHourlyEntity>,
    @Relation(
        entity = TemperatureHourlyEntity::class,
        parentColumn = "hourlyId",
        entityColumn = "hourlyId"
    )
    val temperatureHourlyEntity: List<TemperatureHourlyEntity>,
    @Relation(
        entity = WeatherCodeHourlyEntity::class,
        parentColumn = "hourlyId",
        entityColumn = "hourlyId"
    )
    val weatherCodeHourly: List<WeatherCodeHourlyEntity>,
    @Relation(
        entity = WeatherCodeDailyEntity::class,
        parentColumn = "dailyId",
        entityColumn = "dailyEntityId"
    )
    val weatherCodeDaily: List<WeatherCodeDailyEntity>,
    @Relation(
        entity = TemperatureMaxDailyEntity::class,
        parentColumn = "dailyId",
        entityColumn = "dailyEntityId"
    )
    val temperatureDailyMax: List<TemperatureMaxDailyEntity>,
    @Relation(
        entity = TemperatureMinDailyEntity::class,
        parentColumn = "dailyId",
        entityColumn = "dailyEntityId"
    )
    val temperatureDailyMin: List<TemperatureMinDailyEntity>,


    /*@Embedded(prefix = "hourly_units_") val hourlyUnitsWithDetails: HourlyUnitsWithDetails,
    @Embedded(prefix = "temperature_hourly_") val temperatureHourlyWithDetails: TemperatureHourlyWithDetails,
    @Embedded(prefix = "relative_humidity_hourly_") val relativeHumidityHourlyWithDetails: RelativeHumidityHourlyWithDetails,
    @Embedded(prefix = "wind_speed_hourly_") val windSpeedHourlyWithDetails: WindSpeedHourlyWithDetails,
    @Embedded(prefix = "precipitation_hourly_") val precipitationHourlyWithDetails: PrecipitationHourlyWithDetails,
    @Embedded(prefix = "daily_units_") val dailyUnitsWithDetails: DailyUnitsWithDetails,
    @Embedded(prefix = "sunrise_") val sunriseWithDetails: SunriseWithDetails,
    @Embedded(prefix = "sunset_") val sunsetWithDetails: SunsetWithDetails,
    @Embedded(prefix = "uv_") val uvWithDetails: UVWithDetails,
    @Embedded(prefix = "time_daily_") val timeDailyWithDetails: TimeDailyWithDetails,
    @Embedded(prefix = "time_hourly_") val timeHourlyWithDetails: TimeHourlyWithDetails*/
)

data class HourlyUnitsWithDetails(
    @Embedded val hourlyUnitsEntity: HourlyUnitsEntity,
    @Relation(
        entity = HourlyEntity::class,
        parentColumn = "hourlyId",
        entityColumn = "id"
    )
    val hourlyEntity: HourlyEntity
)

data class TemperatureHourlyWithDetails(
    @Embedded val temperatureHourlyEntity: TemperatureHourlyEntity,
    @Relation(
        entity = HourlyEntity::class,
        parentColumn = "hourlyId",
        entityColumn = "id"
    )
    val hourlyEntity: HourlyEntity
)

data class RelativeHumidityHourlyWithDetails(
    @Embedded val relativeHumidityHourlyEntity: RelativeHumidityHourlyEntity,
    @Relation(
        entity = HourlyEntity::class,
        parentColumn = "hourlyId",
        entityColumn = "id"
    )
    val hourlyEntity: HourlyEntity
)

data class WindSpeedHourlyWithDetails(
    @Embedded val windSpeedHourlyEntity: WindSpeedHourlyEntity,
    @Relation(
        entity = HourlyEntity::class,
        parentColumn = "hourlyId",
        entityColumn = "id"
    )
    val hourlyEntity: HourlyEntity
)

data class PrecipitationHourlyWithDetails(
    @Embedded val precipitationHourlyEntity: PrecipitationHourlyEntity,
    @Relation(
        entity = HourlyEntity::class,
        parentColumn = "hourlyId",
        entityColumn = "id"
    )
    val hourlyEntity: HourlyEntity
)

data class WeatherCodeHourlyWithDetails(
    @Embedded val weatherCodeHourlyEntity: WeatherCodeHourlyEntity,
    @Relation(
        entity = HourlyEntity::class,
        parentColumn = "hourlyId",
        entityColumn = "id"
    )
    val hourlyEntity: HourlyEntity
)

data class DailyUnitsWithDetails(
    @Embedded val dailyUnitsEntity: DailyUnitsEntity,
    @Relation(
        entity = DailyEntity::class,
        parentColumn = "dailyEntityId",
        entityColumn = "id"
    )
    val dailyEntity: DailyEntity
)

data class WeatherCodeDailyWithDetails(
    @Embedded val weatherCodeDailyEntity: WeatherCodeDailyEntity,
    @Relation(
        entity = DailyEntity::class,
        parentColumn = "dailyEntityId",
        entityColumn = "id"
    )
    val dailyEntity: DailyEntity
)

data class SunriseWithDetails(
    @Embedded val sunriseEntity: SunriseEntity,
    @Relation(
        entity = DailyEntity::class,
        parentColumn = "dailyEntityId",
        entityColumn = "id"
    )
    val dailyEntity: DailyEntity
)

data class SunsetWithDetails(
    @Embedded val sunsetEntity: SunsetEntity,
    @Relation(
        entity = DailyEntity::class,
        parentColumn = "dailyEntityId",
        entityColumn = "id"
    )
    val dailyEntity: DailyEntity
)

data class UVWithDetails(
    @Embedded val uvEntity: UVEntity,
    @Relation(
        entity = DailyEntity::class,
        parentColumn = "dailyEntityId",
        entityColumn = "id"
    )
    val dailyEntity: DailyEntity
)

data class TimeDailyWithDetails(
    @Embedded val timeDailyEntity: TimeDailyEntity,
    @Relation(
        entity = DailyEntity::class,
        parentColumn = "dailyEntityId",
        entityColumn = "id"
    )
    val dailyEntity: DailyEntity
)

data class TimeHourlyWithDetails(
    @Embedded val timeHourlyEntity: TimeHourlyEntity,
    @Relation(
        entity = HourlyEntity::class,
        parentColumn = "hourlyId",
        entityColumn = "id"
    )
    val hourlyEntity: HourlyEntity
)
