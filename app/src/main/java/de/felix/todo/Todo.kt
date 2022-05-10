package de.felix.todo

class Todo(id : Int, title : String, description : String, date : String, priority : String) {
    val _id : Int
    val _title : String
    val _description : String
    val _date : String
    val _priority : String

    init {
        _id = id
        _title = title
        _description = description
        _date = date
        _priority = priority
    }
}