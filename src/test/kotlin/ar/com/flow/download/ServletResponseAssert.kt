package ar.com.flow.download

import assertk.Assert
import assertk.assertions.isEqualTo
import assertk.assertions.prop
import org.springframework.mock.web.MockHttpServletResponse

fun Assert<MockHttpServletResponse>.hasContent(expectedContent: String) {
    return prop(MockHttpServletResponse::getContentAsString).isEqualTo(expectedContent)
}