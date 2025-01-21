package br.com.digital.order.dashboard.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.digital.order.R
import br.com.digital.order.networking.resources.AlternativesRoutes
import br.com.digital.order.product.ui.view.SelectProducts
import br.com.digital.order.ui.components.ActionButton
import br.com.digital.order.ui.components.Description
import br.com.digital.order.ui.components.LoadingButton
import br.com.digital.order.ui.components.OptionButton
import br.com.digital.order.ui.theme.Themes
import br.com.digital.order.utils.StringsUtils.ADD_FOOD
import br.com.digital.order.utils.StringsUtils.ADD_ITEM
import br.com.digital.order.utils.StringsUtils.ADD_PRODUCT
import br.com.digital.order.utils.StringsUtils.CREATE_NEW_ORDER
import br.com.digital.order.utils.StringsUtils.SELECTED_ITEMS

@Composable
fun OrderScreen(
    goToBack: () -> Unit = { },
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    var addNewProduct: Boolean by remember { mutableStateOf(value = false) }
    var addNewFood: Boolean by remember { mutableStateOf(value = false) }
    var addNewItem: Boolean by remember { mutableStateOf(value = false) }
    Column(
        modifier = Modifier
            .background(color = Themes.colors.background)
            .fillMaxSize()
    ) {
        ActionButton(
            title = R.string.order_to_pickup,
            goToBack = goToBack
        )
        Column(
            modifier = Modifier
                .background(color = Themes.colors.background)
                .fillMaxSize()
                .padding(horizontal = Themes.size.spaceSize36)
                .verticalScroll(state = rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
        ) {
            OptionButton(
                icon = R.drawable.add,
                label = ADD_PRODUCT,
                onClick = {
                    addNewProduct = true
                }
            )
            OptionButton(
                icon = R.drawable.add,
                label = ADD_FOOD,
                onClick = {
                    addNewFood = true
                }
            )
            OptionButton(
                icon = R.drawable.add,
                label = ADD_ITEM,
                onClick = {
                    addNewItem = true
                }
            )
            Description(description = SELECTED_ITEMS)
            LoadingButton(
                label = CREATE_NEW_ORDER,
                onClick = { }
            )
        }
    }
    if (addNewProduct) {
        SelectProducts(
            onDismiss = {
                addNewProduct = false
            },
            onResult = {
                addNewProduct = false
            },
            goToAlternativeRoutes = goToAlternativeRoutes
        )
    }
}
