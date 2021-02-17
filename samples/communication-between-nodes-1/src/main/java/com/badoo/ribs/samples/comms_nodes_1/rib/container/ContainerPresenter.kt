package com.badoo.ribs.samples.comms_nodes_1.rib.container

import com.badoo.ribs.core.Node
import com.badoo.ribs.core.plugin.SubtreeChangeAware
import com.badoo.ribs.routing.source.backstack.BackStack
import com.badoo.ribs.routing.source.backstack.operation.push
import com.badoo.ribs.samples.comms_nodes_1.rib.container.routing.ContainerRouter.Configuration
import com.badoo.ribs.samples.comms_nodes_1.rib.container.routing.ContainerRouter.Configuration.Content
import com.badoo.ribs.samples.comms_nodes_1.rib.menu.Menu
import io.reactivex.functions.Consumer

interface ContainerPresenter

internal class ContainerPresenterImpl(
    private val backStack: BackStack<Configuration>
) : ContainerPresenter,
    SubtreeChangeAware {

    override fun onChildBuilt(child: Node<*>) {
        when (child) {
            is Menu -> child.output.subscribe(menuOutputConsumer)
        }
    }

    override fun onChildAttached(child: Node<*>) {
        when (child) {
            is Menu -> backStack.activeConfiguration.observe {
                updateMenuItem(it, child)
            }
        }
    }

    private val menuOutputConsumer: Consumer<Menu.Output> = Consumer {
        when (it) {
            is Menu.Output.MenuItemSelected -> updateContent(it.item)
        }
    }

    private fun updateContent(item: Menu.MenuItem) {
        backStack.push(item.toContent())
    }

    private fun updateMenuItem(content: Configuration, child: Menu) {
        content.toMenuItem()?.let { child.input.accept(Menu.Input.SelectMenuItem(it)) }
    }

    private fun Menu.MenuItem.toContent() =
        when (this) {
            Menu.MenuItem.Child1 -> Content.Child1
            Menu.MenuItem.Child2 -> Content.Child2
            Menu.MenuItem.Child3 -> Content.Child3
        }

    private fun Configuration.toMenuItem() =
        when (this) {
            is Content.Child1 -> Menu.MenuItem.Child1
            is Content.Child2 -> Menu.MenuItem.Child2
            is Content.Child3 -> Menu.MenuItem.Child3
            else -> null
        }
}
