package com.badoo.ribs.template.node_dagger_build_param.foo_bar

import com.badoo.ribs.core.modality.BuildParams
import com.badoo.ribs.routing.source.backstack.BackStackFeature
import com.badoo.ribs.template.node_dagger_build_param.foo_bar.feature.FooBarFeature
import com.badoo.ribs.template.node_dagger_build_param.foo_bar.routing.FooBarRouter.Configuration
import com.nhaarman.mockitokotlin2.mock
import org.junit.After
import org.junit.Before
import org.junit.Test

class FooBarInteractorTest {

    private val buildParams: BuildParams<Nothing?> = mock()
    private val feature: FooBarFeature = mock()
    private val backStack: BackStackFeature<Configuration> = mock()
    private lateinit var interactor: FooBarInteractor

    @Before
    fun setup() {
        interactor = FooBarInteractor(
            buildParams = buildParams,
            feature = feature,
            backStack = backStack
        )
    }

    @After
    fun tearDown() {
    }

    /**
     * TODO: Add real tests.
     */
    @Test
    fun `an example test with some conditions should pass`() {
        throw RuntimeException("Add real tests.")
    }
}
