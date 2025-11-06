package com.bcit.lab7jerryxing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bcit.lab7jerryxing.ui.theme.Lab7JerryXingTheme

/**
 * Jerry Xing, A01354731
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab7JerryXingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(Modifier.padding(innerPadding)) {
                        CharacterGallery()
                    }
                }
            }
        }
    }
}

data class CharacterCard(
    val name: String,
    @DrawableRes val imageRes: Int
)

@Composable
fun CharacterGallery() {
    val pool = remember {
        listOf(
            CharacterCard("Allay", R.drawable.allay),
            CharacterCard("Armadillo", R.drawable.armadillo),
            CharacterCard("Axolotl", R.drawable.axolotl),
            CharacterCard("Bat", R.drawable.bat),
            CharacterCard("Camel", R.drawable.camel),
            CharacterCard("Cat", R.drawable.cat),
            CharacterCard("Chicken", R.drawable.chicken),
            CharacterCard("Cod", R.drawable.cod),
            CharacterCard("Copper Golem", R.drawable.copper_golem),
            CharacterCard("Cow", R.drawable.cow),
            CharacterCard("Donkey", R.drawable.donkey),
            CharacterCard("Frog", R.drawable.frog),
            CharacterCard("Glow Squid", R.drawable.glow_squid),
            CharacterCard("Happy Ghast", R.drawable.happy_ghast),
            CharacterCard("Horse", R.drawable.horse),
            CharacterCard("Mooshroom", R.drawable.mooshroom),
            CharacterCard("Mule", R.drawable.mule),
            CharacterCard("Ocelot", R.drawable.ocelot),
            CharacterCard("Parrot", R.drawable.parrot),
            CharacterCard("Pig", R.drawable.pig),
            CharacterCard("Rabbit", R.drawable.rabbit),
            CharacterCard("Salmon", R.drawable.salmon),
            CharacterCard("Sheep", R.drawable.sheep),
            CharacterCard("Sniffer", R.drawable.sniffer),
            CharacterCard("Snow Golem", R.drawable.snow_golem),
            CharacterCard("Squid", R.drawable.squid),
            CharacterCard("Strider", R.drawable.strider),
            CharacterCard("Tadpole", R.drawable.tadpole),
            CharacterCard("Tropical Fish", R.drawable.tropical_fish),
            CharacterCard("Turtle", R.drawable.turtle),
            CharacterCard("Villager", R.drawable.villager),
            CharacterCard("Wandering Trader", R.drawable.wandering_trader),
            CharacterCard("Bee", R.drawable.bee),
            CharacterCard("Cave Spider", R.drawable.cave_spider),
            CharacterCard("Dolphin", R.drawable.dolphin),
            CharacterCard("Drowned", R.drawable.drowned),
            CharacterCard("Enderman", R.drawable.enderman),
            CharacterCard("Fox", R.drawable.fox),
            CharacterCard("Goat", R.drawable.goat),
            CharacterCard("Iron Golem", R.drawable.iron_golem),
            CharacterCard("Llama", R.drawable.llama),
            CharacterCard("Panda", R.drawable.panda),
            CharacterCard("Piglin", R.drawable.piglin),
            CharacterCard("Polar Bear", R.drawable.polar_bear),
            CharacterCard("Pufferfish", R.drawable.pufferfish),
            CharacterCard("Spider", R.drawable.spider),
            CharacterCard("Trader Llama", R.drawable.trader_llama),
            CharacterCard("Wolf", R.drawable.wolf),
            CharacterCard("Zombie Piglin", R.drawable.zombie_piglin),
            CharacterCard("Blaze", R.drawable.blaze),
            CharacterCard("Bogged", R.drawable.bogged),
            CharacterCard("Breeze", R.drawable.breeze),
            CharacterCard("Creaking", R.drawable.creaking),
            CharacterCard("Creeper", R.drawable.creeper),
            CharacterCard("Elder Guardian", R.drawable.elder_guardian),
            CharacterCard("Endermite", R.drawable.endermite),
            CharacterCard("Evoker", R.drawable.evoker),
            CharacterCard("Ghast", R.drawable.ghast),
            CharacterCard("Guardian", R.drawable.guardian),
            CharacterCard("Hoglin", R.drawable.hoglin),
            CharacterCard("Husk", R.drawable.husk),
            CharacterCard("Magma Cube", R.drawable.magma_cube),
            CharacterCard("Phantom", R.drawable.phantom),
            CharacterCard("Piglin Brute", R.drawable.piglin_brute),
            CharacterCard("Pillager", R.drawable.pillager),
            CharacterCard("Ravager", R.drawable.ravager),
            CharacterCard("Shulker", R.drawable.shulker),
            CharacterCard("Silverfish", R.drawable.silverfish),
            CharacterCard("Skeleton", R.drawable.skeleton),
            CharacterCard("Slime", R.drawable.slime),
            CharacterCard("Stray", R.drawable.stray),
            CharacterCard("Vex", R.drawable.vex),
            CharacterCard("Vindicator", R.drawable.vindicator),
            CharacterCard("Warden", R.drawable.warden),
            CharacterCard("Witch", R.drawable.witch),
            CharacterCard("Wither Skeleton", R.drawable.wither_skeleton),
            CharacterCard("Zoglin", R.drawable.zoglin),
            CharacterCard("Zombie", R.drawable.zombie),
            CharacterCard("Zombie Villager", R.drawable.zombie_villager),
            CharacterCard("Ender Dragon", R.drawable.ender_dragon),
            CharacterCard("Wither", R.drawable.wither),
            CharacterCard("Illusioner", R.drawable.illusioner),
            CharacterCard("Herobrine", R.drawable.herobrine),
            CharacterCard("Giant", R.drawable.giant),
            CharacterCard("Skeleton Horse", R.drawable.skeleton_horse),
            CharacterCard("Zombie Horse", R.drawable.zombie_horse),
            CharacterCard("Chicken Jockey", R.drawable.chicken_jockey),
            CharacterCard("Spider Jockey", R.drawable.spider_jockey),
        )
    }

    val cards = remember { mutableStateListOf<CharacterCard>().also { it.addAll(pool) } }

    val rowCount = 3

    fun bucketize(list: List<CharacterCard>, rows: Int): List<List<CharacterCard>> {
        val buckets = List(rows) { mutableListOf<CharacterCard>() }
        list.forEachIndexed { index, item ->
            buckets[index % rows].add(item)
        }
        return buckets.map { it.toList() }
    }

    val rows by remember(cards) { derivedStateOf { bucketize(cards, rowCount) } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Button(
            onClick = {
                val shuffled = pool.shuffled()
                cards.clear()
                cards.addAll(shuffled)
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 12.dp)
        ) {
            Text("Shuffle", fontSize = 18.sp)
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(rows.size) { rowIndex ->
                val rowItems = rows[rowIndex]
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(rowItems.size) { i ->
                        CharacterCardItem(card = rowItems[i])
                    }
                }
            }
        }
    }
}

@Composable
fun CharacterCardItem(card: CharacterCard) {
    var expanded by remember { mutableStateOf(false) }
    val imageHeight = if (expanded) 180.dp else 120.dp
    val showName = !expanded

    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 160.dp)
            .clickable { expanded = !expanded }
            .animateContentSize()
            .padding(2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (showName) {
                Text(
                    text = card.name,
                    fontSize = 18.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            Image(
                painter = painterResource(id = card.imageRes),
                contentDescription = card.name,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageHeight)
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 720)
@Composable
fun PreviewGallery() {
    Lab7JerryXingTheme {
        CharacterGallery()
    }
}
