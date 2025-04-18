package br.com.digital.order.order.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.digital.order.order.data.vo.OrderResponseVO
import br.com.digital.order.order.utils.OrderUtils.NUMBER_ITEMS
import br.com.digital.order.ui.components.SimpleText
import br.com.digital.order.ui.theme.Themes
import br.com.digital.order.utils.NumbersUtils.NUMBER_ONE
import br.com.digital.order.utils.StringsUtils.VALUE_TOTAL
import br.com.digital.order.utils.formatterMaskToMoney
import br.com.digital.order.utils.onBorder

@Composable
fun CardOrder(
    orderResponseVO: OrderResponseVO,
    onItemSelected: (Pair<OrderResponseVO, Int>) -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize4),
        modifier = Modifier
            .onBorder(
                onClick = { onItemSelected(Pair(first = orderResponseVO, second = NUMBER_ONE)) },
                color = Themes.colors.primary,
                spaceSize = Themes.size.spaceSize12,
                width = Themes.size.spaceSize2
            )
            .background(color = Themes.colors.background)
            .fillMaxWidth()
            .padding(all = Themes.size.spaceSize12)
    ) {
        ItemOrder(
            label = NUMBER_ITEMS,
            body = {
                SimpleText(text = orderResponseVO.quantity.toString())
            }
        )
        ItemOrder(
            label = VALUE_TOTAL,
            body = {
                SimpleText(text = formatterMaskToMoney(price = orderResponseVO.total))
            }
        )
    }
}

@Composable
private fun ItemOrder(
    label: String,
    body: @Composable () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize4)
    ) {
        SimpleText(text = label)
        body()
    }
}
