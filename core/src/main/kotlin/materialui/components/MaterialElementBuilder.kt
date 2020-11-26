package materialui.components

import kotlinext.js.Object
import kotlinext.js.jsObject
import kotlinx.css.CSSBuilder
import kotlinx.css.RuleSet
import kotlinx.html.Tag
import kotlinx.html.TagConsumer
import react.RClass
import react.ReactElement
import react.createElement
import react.dom.RDOMBuilder
import styled.Styled

abstract class MaterialElementBuilder<T : Tag, Props : StandardProps>(
    val type: RClass<Props>,
    classMap: List<Pair<Enum<*>, String>>,
    factory: (TagConsumer<Unit>) -> T
) : RDOMBuilder<T>(factory) {
    protected val materialProps: Props = jsObject { }

    val muiCss = CSSBuilder()
        get() = field.also {
            useCSSBuilder = true
        }

    private var useCSSBuilder = false

    fun props(p: Props) {
        Object.assign(materialProps, p)
    }

    fun Tag.classes(rootStyle: String) {
        classes(listOf(MaterialStyle.root to rootStyle))
    }

    fun Tag.classes(vararg classMap: Pair<Enum<*>, String>) {
        classes(classMap.map { (key, value) -> key.toString() to value })
    }

    fun Tag.classes(classMap: List<Pair<Enum<*>, String>>) {
        classes(classMap.map { (key, value) -> key.toString() to value })
    }

    fun Tag.classes(vararg classMap: Pair<String, String>) {
        classes(classMap.map { (key, value) -> key to value })
    }

    fun Tag.classes(classMap: List<Pair<String, String>>) {
        if (classMap.isEmpty()) {
            return
        }

        val classesObj: dynamic = jsObject { }

        classMap.forEach { (key, value) -> classesObj[key] = value }

        classes = classesObj as Any
    }

    var Tag.classes: Any? by materialProps
    var Tag.className: String? by materialProps
    var Tag.component: String? by materialProps

    init {
        attrs.classes(classMap)
        attrs.component = attrs.tagName
    }

    override fun create(): ReactElement {
        Object.keys(materialProps).forEach { key -> setProp(key, materialProps[key]) }
        return if (useCSSBuilder) {
            Styled.createElement(type, muiCss, props, childList)
        }
        else {
            createElement(type, props, *childList.toTypedArray())
        }
    }

}

inline fun MaterialElementBuilder<*, *>.muiCss(handler: RuleSet) = muiCss.handler()

