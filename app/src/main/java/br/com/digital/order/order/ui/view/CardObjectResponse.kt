package br.com.digital.order.order.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.digital.order.order.domain.factory.statusObject
import br.com.digital.order.product.domain.factory.typeOrderFactory
import br.com.digital.order.reservation.data.vo.ObjectResponseVO
import br.com.digital.order.ui.components.Description
import br.com.digital.order.ui.components.Title
import br.com.digital.order.ui.theme.Themes
import br.com.digital.order.utils.onBorder

@Composable
fun CardObjectResponse(
    objectResponseVO: ObjectResponseVO,
    onItemSelected: (ObjectResponseVO) -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .onBorder(
                onClick = {
                    onItemSelected(objectResponseVO)
                },
                color = Themes.colors.primary,
                spaceSize = Themes.size.spaceSize12,
                width = Themes.size.spaceSize2
            )
            .background(color = Themes.colors.background)
            .padding(all = Themes.size.spaceSize16)
            .width(width = Themes.size.spaceSize200)
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        Title(title = typeOrderFactory(type = objectResponseVO.type))
        Description(description = objectResponseVO.name, maxLines = 1)
        Description(description = statusObject(status = objectResponseVO.status))
        Spacer(modifier = Modifier.height(height = Themes.size.spaceSize1))
    }
}
