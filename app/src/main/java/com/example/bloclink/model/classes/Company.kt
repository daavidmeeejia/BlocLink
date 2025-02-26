package com.example.bloclink.model.classes

data class Company(
    val companyId: String = "",
    val companyName: String = "",
    val description: String = "",
    val image: Any = ""
){
    fun serialize(): String {
        return "$companyId|$companyName|$description|$image|"
    }

}
fun deserializeCompany(data: String): Company {
    val parts = data.split("|")
    return Company(
        companyId = parts[0],
        companyName = parts[1],
        description = parts[2],
        image = parts[3])
}