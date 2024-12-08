package ie.setu.models

/**
 * Represents a player in a football team.
 *
 * @property name The name of the player.
 * @property number The jersey number of the player. Defaults to 0 if not specified.
 * @property height The height of the player in meters.
 * @property weight The weight of the player in kilograms.
 * @property position The playing position of the player on the field.
 * @property nationality The nationality of the player.
 * @property value The market value of the player in millions of euros. Defaults to 0.0 if not specified.
 */
data class Player(
    var name: String,
    var number: Int = 0,
    var height: Double,
    var weight: Double,
    var position: String,
    var nationality: String,
    var value: Double = 0.0

)
