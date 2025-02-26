package com.example.bloclink.model.classes

data class Particular(
    val particularId: String = "",
    val particularName: String = "",
    val description: String = "",
    val image: Any = ""
){
    fun serialize(): String {
        return "$particularId|$particularName|$description|$image|"
    }

}
fun deserializeParticular(data: String): Particular {
    val parts = data.split("|")
    return Particular(
        particularId = parts[0],
        particularName = parts[1],
        description = parts[2],
        image = parts[3])
}