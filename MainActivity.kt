@file:OptIn(ExperimentalMaterial3Api::class)

package ru.hse.mobilepractice.birthdays

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

val AppAccent = Color(0xFF4682B4)  // цвет системы

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AppRoot()
            }
        }
    }
}

@Composable
fun AppRoot() {
    val birthdays = listOf(
        Birthday(id = 1, name = "Мама", date = "15 июля", isCongratulated = true),
        Birthday(id = 2, name = "Антон", date = "3 августа", isCongratulated = false),
        Birthday(id = 3, name = "Лена", date = "22 июня", isCongratulated = false),
        Birthday(id = 4, name = "Папа", date = "8 сентября", isCongratulated = true)
    )
    var selected by remember { mutableStateOf<Birthday?>(null) }
    val current = selected
    if (current == null) {
        ListScreen(birthdays, onSelect = { selected = it }, onAdd = {})
    } else {
        DetailScreen(current, onBack = { selected = null })
    }
}

@Composable
fun ListScreen(birthdays: List<Birthday>, onSelect: (Birthday) -> Unit, onAdd: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Дни рождения") },
                actions = {
                    IconButton(onClick = onAdd) {
                        Icon(Icons.Default.Add, contentDescription = "Добавить")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(birthdays, key = { it.id }) { birthday ->
                RowItem(birthday) { onSelect(birthday) }
            }
        }
    }
}

@Composable
fun RowItem(birthday: Birthday, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),  // расстояние между слотами
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)  // размер иконки на главном экране
                .clip(RoundedCornerShape(10.dp))  // закругление углов иконки
                .background(AppAccent),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Cake, contentDescription = null, tint = Color.White)  // настройки иконки(изображения)
        }
        Spacer(Modifier.width(12.dp))  // интервал между иконкой и надписями
        Column(modifier = Modifier.weight(1f)) {
            Text(birthday.name, fontWeight = FontWeight.SemiBold)  // жирность имён
            Text(birthday.date, color = Color.Gray)  // подпись(дата)
        }
        Icon(
            Icons.Default.WineBar,  // иконка, отвечающая за статус подарка
            contentDescription = null,
            tint = if (birthday.isCongratulated) AppAccent else Color.LightGray  // цвет иконки, обозначающей некупленный подарок
        )
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp)) {  // интервал между датой рождения и статусом подарка(стр.2)
        Text(label, color = Color.Gray)  // цвет надписи "Дата рождения"
        Spacer(Modifier.weight(1f))
        Text(value)
    }
}

@Composable
fun DetailScreen(birthday: Birthday, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(birthday.name) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ThumbDownOffAlt, contentDescription = "Назад")  // иконка стрелочки возвращения на главную страницу
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).fillMaxWidth().padding(24.dp),  // отступы от краев страницы
            horizontalAlignment = Alignment.CenterHorizontally  // расположение на странице
        ) {
            Box(
                modifier = Modifier.size(76.dp).clip(RoundedCornerShape(22.dp)).background(AppAccent),
                contentAlignment = Alignment.Center // параметры иконки на стр.2
            ) {
                Icon(Icons.Default.Cake, contentDescription = null, tint = Color.White, modifier = Modifier.size(36.dp))  // параметры изображения внутри иконки
            }
            Spacer(Modifier.height(24.dp))  // отступ между иконкой и текстом
            InfoRow("Дата рождения", birthday.date)
            Spacer(Modifier.height(8.dp))  // отступ между строками
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text("Подарок куплен")
                Spacer(Modifier.weight(1f))
                Icon(
                    Icons.Default.WineBar,  // иконка, отвечающая за статус подарка
                    contentDescription = null,
                    tint = if (birthday.isCongratulated) AppAccent else Color.LightGray  // цвет иконки, отвечающей за статус подарка
                )
            }
        }
    }
}
