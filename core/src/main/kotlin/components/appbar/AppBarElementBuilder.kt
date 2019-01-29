package components.appbar

import components.MaterialElementBuilder
import components.appbar.enums.AppBarColor
import components.appbar.enums.AppBarPosition
import components.consumers
import kotlinx.html.Tag
import kotlinx.html.TagConsumer
import react.RComponent
import react.RProps
import react.RState
import kotlin.reflect.KClass

class AppBarElementBuilder<T: Tag> internal constructor(
    type: RComponent<RProps, RState>,
    tag: KClass<T>,
    factory: (TagConsumer<Unit>) -> T = consumers(tag)
) : MaterialElementBuilder<T>(type, factory) {

    var Tag.classes: Any
        get() = @Suppress("UnsafeCastFromDynamic") props.asDynamic()["classes"]
        set(value) { setProp("classes", value) }
    var Tag.color: AppBarColor
        get() = AppBarColor.valueOf(@Suppress("UnsafeCastFromDynamic") props.asDynamic()["color"])
        set(value) { setProp("color", value.toString()) }
    var Tag.position: AppBarPosition
        get() = AppBarPosition.valueOf(@Suppress("UnsafeCastFromDynamic") props.asDynamic()["position"])
        set(value) { setProp("position", value.toString()) }
}