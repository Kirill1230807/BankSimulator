package com.example.banksimulator.presentation.screens.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.example.banksimulator.core.ui.theme.BankSimulatorTheme
import com.example.banksimulator.data.local.entity.CardEntity
import com.example.banksimulator.data.local.entity.utils.CardStatus
import kotlin.math.absoluteValue

@Composable
fun CardCarousel(
    cards: List<CardEntity>,
    onCardSettingsClick: (CardEntity) -> Unit,
    onAddNewCardClick: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { cards.size + 1 })
    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 32.dp),
        pageSpacing = 2.dp
    ) { page ->


        val pageOffset = (
                (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                ).absoluteValue

        val scale = lerp(
            start = 0.85f,
            stop = 1f,
            fraction = 1f - pageOffset.coerceIn(0f, 1f)
        )
        val alpha = lerp(
            start = 0.5f,
            stop = 1f,
            fraction = 1f - pageOffset.coerceIn(0f, 1f)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    this.alpha = alpha
                },
            contentAlignment = Alignment.Center
        ) {
            if (page < cards.size) {
                val currentCard = cards[page]
                BankCard(
                    cardNumber = currentCard.cardNumber,
                    expirationDate = currentCard.expirationDate,
                    onCardSettingsClick = { onCardSettingsClick(currentCard) }
                )
            } else {
                AddNewCardItem(onClick = onAddNewCardClick)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CardCarouselPreview() {
    val fakeCards = listOf(
        CardEntity(
            cardId = "1",
            accountOwnerId = "1",
            cardNumber = "4444 4444 4444 4444",
            cardColor = 0xFF1A1A1A,
            cardHolderName = "IVAN IVANOV",
            expirationDate = "12/26",
            status = CardStatus.ACTIVE
        ),
        CardEntity(
            cardId = "2",
            accountOwnerId = "1",
            cardNumber = "5555 5555 5555 5555",
            cardColor = 0xFF2D2D2D,
            cardHolderName = "IVAN IVANOV",
            expirationDate = "10/25",
            status = CardStatus.ACTIVE
        ),
        CardEntity(
            cardId = "3",
            accountOwnerId = "1",
            cardNumber = "6666 6666 6666 6666",
            cardColor = 0xFF3D3D3D,
            cardHolderName = "IVAN IVANOV",
            expirationDate = "08/24",
            status = CardStatus.ACTIVE
        )
    )

    BankSimulatorTheme {
        CardCarousel(
            cards = fakeCards,
            onCardSettingsClick = {},
            onAddNewCardClick = {}
        )
    }
}
