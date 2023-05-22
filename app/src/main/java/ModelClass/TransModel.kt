package ModelClass

class TransModel {

    var id = 0
    var amount = 0
    var category = ""
    var notes = ""
    var isexpense = 0

    constructor(id: Int, amount: Int, category: String, notes: String, isexpense: Int) {
        this.id = id
        this.amount = amount
        this.category = category
        this.notes = notes
        this.isexpense = isexpense
    }
}