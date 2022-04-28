package fr.mary.naturecollection


//Ici on donne toutes les caractéristiques d une plante
class PlantModel(
    //val = const qui ne change pas au cours de l excécution du prog
    //var = valeur peut changer au cours de l execution du prog
    val id : String = "plant0",
    val name : String = "Tulipe",
    val description : String = "Petite description",
    val imageUrl : String = "C://Users//mary-//AndroidStudioProjects//NatureCollection//app//src//main//res//drawable-mdpi//trending1.jpg",
    var liked : Boolean = false

)
