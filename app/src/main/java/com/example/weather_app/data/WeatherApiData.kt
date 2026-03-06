import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    @SerialName("current")
    val current: Current? = null,
    @SerialName("location")
    val location: Location? = null
)

@Serializable
data class Location(
    @SerialName("country")
    val country: String? = null,
    @SerialName("lat")
    val lat: Double? = null,
    @SerialName("localtime")
    val localtime: String? = null,
    @SerialName("localtime_epoch")
    val localTimeEpoch: String? = null,
    @SerialName("lon")
    val lon: Double? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("region")
    val region: String? = null,
    @SerialName("tz_id")
    val tzId: String? = null
)

@Serializable
data class Current(
    @SerialName("cloud")
    val cloud: Int? = null,
    @SerialName("condition")
    val condition: Condition? = null,
    @SerialName("feelslike_c")
    val feelsLikeC: Double? = null,
    @SerialName("feelslike_f")
    val feelsLikeF: Double? = null,
    @SerialName("gust_kph")
    val gustKph: Double? = null,
    @SerialName("gust_mph")
    val gustMph: Double? = null,
    @SerialName("humidity")
    val humidity: Int? = null,
    @SerialName("is_day")
    val isDay: Int? = null,
    @SerialName("last_updated")
    val lastUpdated: String? = null,
    @SerialName("last_updated_epoch")
    val lastUpdatedEpoch: Int? = null,
    @SerialName("precip_in")
    val precipIn: Double? = null,
    @SerialName("precip_mm")
    val precipMm: Double? = null,
    @SerialName("pressure_in")
    val pressureIn: Double? = null,
    @SerialName("pressure_mb")
    val pressureMb: Double? = null,
    @SerialName("temp_c")
    val tempC: String? = null,
    @SerialName("temp_f")
    val tempF: Double? = null,
    @SerialName("uv")
    val uv: Double? = null,
    @SerialName("vis_km")
    val visKm: Double? = null,
    @SerialName("vis_miles")
    val visMiles: Double? = null,
    @SerialName("wind_degree")
    val windDegree: Int? = null,
    @SerialName("wind_dir")
    val windDir: String? = null,
    @SerialName("wind_kph")
    val windKph: Double? = null,
    @SerialName("wind_mph")
    val windMph: Double? = null
)

@Serializable
data class Condition(
    @SerialName("code")
    val code: Int? = null,
    @SerialName("icon")
    val icon: String? = null,
    @SerialName("text")
    val text: String? = null
)
