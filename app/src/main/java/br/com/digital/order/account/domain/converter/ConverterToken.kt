package br.com.digital.order.account.domain.converter

import br.com.digital.order.account.data.dto.TokenResponseDTO
import br.com.digital.order.account.data.vo.TokenResponseVO

class ConverterToken {

    fun converterTokenRequestDTOToTokenResponseVO(token: TokenResponseDTO): TokenResponseVO {
        return TokenResponseVO(
            user = token.user,
            authenticated = token.authenticated,
            created = token.created,
            type = token.type.name,
            expiration = token.expiration,
            accessToken = token.accessToken,
            refreshToken = token.refreshToken
        )
    }
}