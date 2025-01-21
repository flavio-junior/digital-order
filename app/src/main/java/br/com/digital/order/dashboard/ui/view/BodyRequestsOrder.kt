package br.com.digital.order.dashboard.ui.view

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.digital.order.R
import br.com.digital.order.common.dto.ObjectRequestDTO
import br.com.digital.order.common.dto.TypeItem
import br.com.digital.order.food.ui.view.SelectFoods
import br.com.digital.order.item.ui.view.SelectItems
import br.com.digital.order.networking.resources.AlternativesRoutes
import br.com.digital.order.product.ui.view.SelectProducts
import br.com.digital.order.ui.components.Description
import br.com.digital.order.ui.components.OptionButton
import br.com.digital.order.ui.theme.Themes
import br.com.digital.order.utils.NumbersUtils.NUMBER_ZERO
import br.com.digital.order.utils.StringsUtils.ADD_FOOD
import br.com.digital.order.utils.StringsUtils.ADD_ITEM
import br.com.digital.order.utils.StringsUtils.ADD_PRODUCT
import br.com.digital.order.utils.StringsUtils.SELECTED_ITEMS

@Composable
fun BodyRequestsOrder(
    objectsToSave: MutableList<ObjectRequestDTO>,
    contentObjects: MutableList<BodyObject>,
    verifyObjects: Boolean,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onResult: (List<ObjectRequestDTO>) -> Unit = {}
) {
    var addNewProduct: Boolean by remember { mutableStateOf(value = false) }
    var addNewFood: Boolean by remember { mutableStateOf(value = false) }
    var addNewItem: Boolean by remember { mutableStateOf(value = false) }
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
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
        modifier = Modifier.horizontalScroll(state = rememberScrollState())
    ) {
        objectsToSave.forEach { objectResult ->
            val quantity = contentObjects.find { it.name == objectResult.name }?.quantity
                ?: NUMBER_ZERO
            CardObjectSelect(
                objectRequestDTO = objectResult,
                verifyObject = verifyObjects,
                quantity = quantity,
                onQuantityChange = {
                    objectResult.quantity = it
                },
                onItemSelected = {
                    if (objectsToSave.contains(element = it)) {
                        objectsToSave.remove(element = it)
                    }
                }
            )
        }
    }
    if (addNewProduct) {
        SelectProducts(
            onDismiss = {
                addNewProduct = false
            },
            onResult = {
                it.forEach { product ->
                    val productSelected = ObjectRequestDTO(
                        name = product.name,
                        identifier = product.id,
                        quantity = 0,
                        type = TypeItem.PRODUCT
                    )
                    if (!objectsToSave.contains(element = productSelected)) {
                        objectsToSave.add(productSelected)
                    }
                }
                addNewFood = false
            },
            goToAlternativeRoutes = goToAlternativeRoutes
        )
    }
    if (addNewFood) {
        SelectFoods(
            onDismiss = {
                addNewFood = false
            },
            onResult = {
                it.forEach { product ->
                    val foodSelected = ObjectRequestDTO(
                        name = product.name,
                        identifier = product.id,
                        quantity = 0,
                        type = TypeItem.FOOD
                    )
                    if (!objectsToSave.contains(element = foodSelected)) {
                        objectsToSave.add(foodSelected)
                    }
                }
                addNewFood = false
            },
            goToAlternativeRoutes = goToAlternativeRoutes
        )
    }
    if (addNewItem) {
        SelectItems(
            onDismiss = {
                addNewItem = false
            },
            onResult = {
                it.forEach { item ->
                    val itemSelected = ObjectRequestDTO(
                        name = item.name,
                        identifier = item.id,
                        quantity = 0,
                        type = TypeItem.ITEM
                    )
                    if (!objectsToSave.contains(element = itemSelected)) {
                        objectsToSave.add(itemSelected)
                    }
                }
                addNewFood = false
            },
            goToAlternativeRoutes = goToAlternativeRoutes
        )
    }
    onResult(objectsToSave)
}
