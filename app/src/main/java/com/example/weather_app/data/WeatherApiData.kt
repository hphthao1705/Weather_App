import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    @SerializedName("current")
    val current: Current? = null,
    @SerializedName("location")
    val location: Location? = null
)

@Serializable
data class Location(
    @SerializedName("country")
    val country: String? = null,
    @SerializedName("lat")
    val lat: Double? = null,
    @SerializedName("localtime")
    val localtime: String? = null,
    @SerializedName("localtime_epoch")
    val localTimeEpoch: String? = null,
    @SerializedName("lon")
    val lon: Double? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("region")
    val region: String? = null,
    @SerializedName("tz_id")
    val tzId: String? = null
)

@Serializable
data class Current(
    @SerializedName("cloud")
    val cloud: Int? = null,
    @SerializedName("condition")
    val condition: Condition? = null,
    @SerializedName("feelslike_c")
    val feelsLikeC: Double? = null,
    @SerializedName("feelslike_f")
    val feelsLikeF: Double? = null,
    @SerializedName("gust_kph")
    val gustKph: Double? = null,
    @SerializedName("gust_mph")
    val gustMph: Double? = null,
    @SerializedName("humidity")
    val humidity: Int? = null,
    @SerializedName("is_day")
    val isDay: Int? = null,
    @SerializedName("last_updated")
    val lastUpdated: String? = null,
    @SerializedName("last_updated_epoch")
    val lastUpdatedEpoch: Int? = null,
    @SerializedName("precip_in")
    val precipIn: Double? = null,
    @SerializedName("precip_mm")
    val precipMm: Double? = null,
    @SerializedName("pressure_in")
    val pressureIn: Double? = null,
    @SerializedName("pressure_mb")
    val pressureMb: Double? = null,
    @SerializedName("temp_c")
    val tempC: Double? = null,
    @SerializedName("temp_f")
    val tempF: Double? = null,
    @SerializedName("uv")
    val uv: Double? = null,
    @SerializedName("vis_km")
    val visKm: Double? = null,
    @SerializedName("vis_miles")
    val visMiles: Double? = null,
    @SerializedName("wind_degree")
    val windDegree: Int? = null,
    @SerializedName("wind_dir")
    val windDir: String? = null,
    @SerializedName("wind_kph")
    val windKph: Double? = null,
    @SerializedName("wind_mph")
    val windMph: Double? = null
)

@Serializable
data class Condition(
    @SerializedName("code")
    val code: Int? = null,
    @SerializedName("icon")
    val icon: String? = null,
    @SerializedName("text")
    val text: String? = null
)
