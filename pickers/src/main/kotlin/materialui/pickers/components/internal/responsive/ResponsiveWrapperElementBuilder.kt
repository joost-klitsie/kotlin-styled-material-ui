package materialui.pickers.components.internal.responsive

import kotlinext.js.jsObject
import materialui.pickers.components.BasePickerElementBuilder
import materialui.pickers.components.internal.desktop.DesktopDelegate
import materialui.pickers.components.internal.desktop.DesktopElement
import materialui.pickers.components.internal.desktoppopper.DesktopPopperDelegate
import materialui.pickers.components.internal.desktoppopper.DesktopPopperElement
import materialui.pickers.components.internal.modal.ModalDelegate
import materialui.pickers.components.internal.modal.ModalElement
import react.RClass

abstract class ResponsiveWrapperElementBuilder<P: ResponsiveWrapperProps> internal constructor(
    type: RClass<P>, className: String?, props: P = jsObject { }
) : BasePickerElementBuilder<P>(type, className, props),
        DesktopElement<P> by DesktopDelegate(),
        DesktopPopperElement<P> by DesktopPopperDelegate(),
        ModalElement<P> by ModalDelegate()
