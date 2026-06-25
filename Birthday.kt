package ru.hse.mobilepractice.birthdays

// Карточка данных одного элемента.
data class Birthday(
    val id: Int,
    val name: String,
    val date: String,
    val isCongratulated: Boolean = false,
    val iconType: Int = 0 // 0 = WineBar, 1 = Cake, 2 = DateRange
)
