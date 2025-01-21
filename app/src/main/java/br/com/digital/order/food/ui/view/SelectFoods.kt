package br.com.digital.order.food.ui.view

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import br.com.digital.order.food.data.dto.FoodResponseDTO
import br.com.digital.order.food.ui.viewmodel.FoodViewModel
import br.com.digital.order.food.utils.FoodUtils.NO_FOODS_SELECTED
import br.com.digital.order.networking.resources.AlternativesRoutes
import br.com.digital.order.networking.resources.ObserveNetworkStateHandler
import br.com.digital.order.ui.components.Description
import br.com.digital.order.ui.components.LoadingButton
import br.com.digital.order.ui.components.ObserveNetworkStateHandler
import br.com.digital.order.ui.components.Search
import br.com.digital.order.ui.components.SelectObject
import br.com.digital.order.ui.components.Tag
import br.com.digital.order.ui.components.Title
import br.com.digital.order.ui.theme.Themes
import br.com.digital.order.utils.OrdersUtils.EMPTY_TEXT
import br.com.digital.order.utils.StringsUtils.ADD_FOODS
import br.com.digital.order.utils.StringsUtils.CANCEL
import br.com.digital.order.utils.StringsUtils.CONFIRM
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun SelectFoods(
    onDismiss: () -> Unit = {},
    onResult: (List<FoodResponseDTO>) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    val viewModel: FoodViewModel = koinViewModel()
    var isLoading: Boolean by remember { mutableStateOf(value = false) }
    var name: String by remember { mutableStateOf(value = EMPTY_TEXT) }
    val Foods = remember { mutableStateListOf<FoodResponseDTO>() }
    var FoodsSelected = remember { mutableStateListOf<FoodResponseDTO>() }
    var observer: Triple<Boolean, Boolean, String> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    SelectObject(
        body = {
            Title(
                title = ADD_FOODS,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Search(
                value = name,
                onValueChange = { name = it },
                isError = observer.second,
                message = observer.third,
                onGo = {
                    viewModel.findFoodByName(name = name)
                    isLoading = true
                }
            )
            if (isLoading) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            if (Foods.isNotEmpty()) {
                SelectFoods(
                    Foods = Foods,
                    FoodsSelected = FoodsSelected,
                    onResult = {
                        FoodsSelected = it.toMutableStateList()
                    }
                )
                if (FoodsSelected.isNotEmpty()) {
                    ListFoodsAvailable(foods = FoodsSelected)
                }
            }
            LoadingButton(
                background = Themes.colors.success,
                label = CONFIRM,
                onClick = {
                    if (FoodsSelected.isNotEmpty()) {
                        viewModel.resetFood()
                        onResult(FoodsSelected)
                        onDismiss()
                    } else {
                        observer =
                            Triple(first = false, second = true, third = NO_FOODS_SELECTED)
                    }
                }
            )
            LoadingButton(
                background = Themes.colors.error,
                label = CANCEL,
                onClick = {
                    viewModel.resetFood()
                    onDismiss()
                }
            )
        },
        onDismiss = {
            viewModel.resetFood()
            onDismiss()
        }
    )
    ObserveNetworkStateHandlerFindFoodByName(
        viewModel = viewModel,
        onError = {
            observer = it
            isLoading = it.first
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccessful = { result ->
            observer = Triple(first = false, second = false, third = EMPTY_TEXT)
            isLoading = false
            Foods.clear()
            result.forEach { Food ->
                if (!Foods.contains(element = Food)) {
                    Foods.add(element = Food)
                }
            }
        }
    )
}

@Composable
private fun SelectFoods(
    Foods: List<FoodResponseDTO>,
    FoodsSelected: MutableList<FoodResponseDTO>,
    onResult: (List<FoodResponseDTO>) -> Unit = {}
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8),
        modifier = Modifier
    ) {
        items(Foods) { food ->
            Tag(
                text = food.name,
                value = food,
                onCheck = { isChecked ->
                    if (isChecked) {
                        FoodsSelected.add(food)
                    } else {
                        FoodsSelected.remove(food)
                    }
                    onResult(FoodsSelected)
                }
            )
        }
    }
}

@Composable
private fun ListFoodsAvailable(
    foods: List<FoodResponseDTO>
) {
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8),
        state = scrollState,
        modifier = Modifier
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    coroutineScope.launch {
                        scrollState.scrollBy(-delta)
                    }
                }
            )
    ) {
        items(foods) { foods ->
            Description(description = foods.name)
        }
    }
}

@Composable
private fun ObserveNetworkStateHandlerFindFoodByName(
    viewModel: FoodViewModel,
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onSuccessful: (List<FoodResponseDTO>) -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<List<FoodResponseDTO>> by remember { viewModel.findFoodByName }
    ObserveNetworkStateHandler(
        state = state,
        onLoading = {},
        onError = {
            it?.let {
                onError(Triple(first = false, second = true, third = it))
            }
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
            it.result?.let { result -> onSuccessful(result) }
        }
    )
}
